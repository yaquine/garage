package rahile.abdelmounim.garage.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rahile.abdelmounim.garage.commun.erreur.AccessoireInexistantException;
import rahile.abdelmounim.garage.commun.erreur.VehiculeInexistantException;
import rahile.abdelmounim.garage.commun.mappeur.AccessoireMapper;
import rahile.abdelmounim.garage.domaine.Accessoire;
import rahile.abdelmounim.garage.domaine.EntiteAuditAbstraite;
import rahile.abdelmounim.garage.domaine.Vehicule;
import rahile.abdelmounim.garage.repository.AccessoireRepository;
import rahile.abdelmounim.garage.repository.VehiculeRepository;
import rahile.abdelmounim.garage.service.AccessoireService;
import rahile.abdelmounim.garage.service.dto.AccessoireEntreDto;
import rahile.abdelmounim.garage.service.dto.AccessoireSortieDto;

import java.util.List;


@Service
@Transactional
public class AccessoireServiceImpl implements AccessoireService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccessoireServiceImpl.class);

    private final AccessoireRepository accessoireRepository;
    private final VehiculeRepository vehiculeRepository;

    public AccessoireServiceImpl(
            AccessoireRepository accessoireRepository,
            VehiculeRepository vehiculeRepository
    ) {
        this.accessoireRepository = accessoireRepository;
        this.vehiculeRepository = vehiculeRepository;
    }

    @Override
    public AccessoireSortieDto ajouterAccessoire(Long vehiculeId, AccessoireEntreDto dto) {

        LOGGER.info("Service ajouterAccessoire vehiculeId: {}", vehiculeId);

        Vehicule vehicule = vehiculeRepository.findByIdAndEtat(vehiculeId, EntiteAuditAbstraite.EtatEntiteEnum.ACTIVE)
                .orElseThrow(() -> new VehiculeInexistantException(vehiculeId));

        Accessoire accessoire = AccessoireMapper.transformerEntite(dto);

        accessoire.setVehicule(vehicule);

        Accessoire saved = accessoireRepository.save(accessoire);

        LOGGER.info("Accessoire ajouté id: {}", saved.getId());

        return AccessoireMapper.transformerSortieDto(saved);
    }

    @Override
    public AccessoireSortieDto modifierAccessoire(Long accessoireId, AccessoireEntreDto dto) {

        LOGGER.info("Service modifierAccessoire id: {}", accessoireId);

        Accessoire accessoire = accessoireRepository.findByIdAndEtat(accessoireId,
                        EntiteAuditAbstraite.EtatEntiteEnum.ACTIVE)
                .orElseThrow(() -> new AccessoireInexistantException(accessoireId));

        accessoire.setNom(dto.nom());
        accessoire.setDescription(dto.description());
        accessoire.setPrix(dto.prix());
        accessoire.setType(dto.type());
        accessoire.setCode(dto.code());

        Accessoire updated = accessoireRepository.save(accessoire);

        LOGGER.info("Accessoire modifié id: {}", updated.getId());

        return AccessoireMapper.transformerSortieDto(updated);
    }

    @Override
    public void supprimerAccessoire(Long accessoireId) {

        LOGGER.info("Service supprimerAccessoire id: {}", accessoireId);

        Accessoire accessoire = accessoireRepository.findByIdAndEtat(accessoireId,
                        EntiteAuditAbstraite.EtatEntiteEnum.ACTIVE)
                .orElseThrow(() -> new AccessoireInexistantException(accessoireId));

        Vehicule vehicule = accessoire.getVehicule();

        vehicule.supprimerAccessoire(accessoire);

        vehiculeRepository.save(vehicule);

        LOGGER.info("Accessoire supprimé id: {}", accessoireId);
    }

    @Override
    public List<AccessoireSortieDto> listerAccessoiresVehicule(Long vehiculeId) {

        LOGGER.info("Service listerAccessoiresVehicule vehiculeId: {}", vehiculeId);

        List<Accessoire> accessoires =
                accessoireRepository.findByVehiculeIdAndEtat(vehiculeId, EntiteAuditAbstraite.EtatEntiteEnum.ACTIVE);

        return accessoires.stream()
                .map(AccessoireMapper::transformerSortieDto)
                .toList();
    }
}

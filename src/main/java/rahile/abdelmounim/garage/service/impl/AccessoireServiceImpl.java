package rahile.abdelmounim.garage.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rahile.abdelmounim.garage.commun.erreur.AccessoireInexistantException;
import rahile.abdelmounim.garage.commun.erreur.VehiculeInexistantException;
import rahile.abdelmounim.garage.commun.mappeur.AccessoireMappeur;
import rahile.abdelmounim.garage.domaine.Accessoire;
import rahile.abdelmounim.garage.domaine.EntiteAuditeAbstraite;
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

        LOGGER.info("Début service : ajouter accessoire, véhicule Id : {}", vehiculeId);

        Vehicule vehicule = vehiculeRepository.findByIdAndEtat(vehiculeId, EntiteAuditeAbstraite.EtatEntiteEnum.ACTIVE)
                .orElseThrow(() -> new VehiculeInexistantException(vehiculeId));

        Accessoire accessoire = AccessoireMappeur.transformerEntite(dto);

        accessoire.setVehicule(vehicule);

        Accessoire saved = accessoireRepository.save(accessoire);

        LOGGER.info("Fin service : ajouter accessoire, accessoire Id: {}", saved.getId());

        return AccessoireMappeur.transformerSortieDto(saved);
    }

    @Override
    public AccessoireSortieDto modifierAccessoire(Long accessoireId, AccessoireEntreDto dto) {

        LOGGER.info("Début service, modifier accessoire par id: {}", accessoireId);

        Accessoire accessoire = accessoireRepository.findByIdAndEtat(accessoireId,
                        EntiteAuditeAbstraite.EtatEntiteEnum.ACTIVE)
                .orElseThrow(() -> new AccessoireInexistantException(accessoireId));

        accessoire.setNom(dto.nom());
        accessoire.setDescription(dto.description());
        accessoire.setPrix(dto.prix());
        accessoire.setType(dto.type());
        accessoire.setCode(dto.code());

        Accessoire updated = accessoireRepository.save(accessoire);

        LOGGER.info("Fin service, modifier accessoire par id: {}", updated.getId());

        return AccessoireMappeur.transformerSortieDto(updated);
    }

    @Override
    public void desactiverAccessoire(Long accessoireId) {

        LOGGER.info("Début service desactiver accessoire par id : {}", accessoireId);

        Accessoire accessoire = accessoireRepository.findByIdAndEtat(accessoireId,
                        EntiteAuditeAbstraite.EtatEntiteEnum.ACTIVE)
                .orElseThrow(() -> new AccessoireInexistantException(accessoireId));

        Vehicule vehicule = accessoire.getVehicule();

        vehicule.desactiverAccessoire(accessoire);

        vehiculeRepository.save(vehicule);

        LOGGER.info("Fin service desactiver accessoire par id : {}", accessoireId);
    }

    @Override
    public List<AccessoireSortieDto> listerAccessoiresVehicule(Long vehiculeId) {

        LOGGER.info("Début du service : lister les accessoires du véhicule par véhicule. Id : {}", vehiculeId);

        List<Accessoire> accessoires =
                accessoireRepository.findByVehiculeIdAndEtat(vehiculeId, EntiteAuditeAbstraite.EtatEntiteEnum.ACTIVE);

        return accessoires.stream()
                .map(AccessoireMappeur::transformerSortieDto)
                .toList();
    }
}

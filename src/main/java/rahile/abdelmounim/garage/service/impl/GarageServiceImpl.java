package rahile.abdelmounim.garage.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rahile.abdelmounim.garage.commun.erreur.GarageInexistantException;
import rahile.abdelmounim.garage.commun.mappeur.GarageMapper;
import rahile.abdelmounim.garage.domaine.EntiteAuditAbstraite;
import rahile.abdelmounim.garage.domaine.Garage;
import rahile.abdelmounim.garage.repository.GarageRepository;
import rahile.abdelmounim.garage.repository.specification.GarageSpecification;
import rahile.abdelmounim.garage.service.GarageService;
import rahile.abdelmounim.garage.service.dto.GarageEntreDto;
import rahile.abdelmounim.garage.service.dto.GarageSortieDto;
import rahile.abdelmounim.garage.web.requete.GarageReadRequete;

@Service
public class GarageServiceImpl implements GarageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GarageServiceImpl.class);

    private final GarageRepository garageRepository;

    public GarageServiceImpl(GarageRepository garageRepository) {
        this.garageRepository = garageRepository;
    }


    @Override
    @Transactional
    public GarageSortieDto ajouterGarage(GarageEntreDto garageEntreDto) {

        LOGGER.info(" debut ajouter Garage");

        Garage garage = GarageMapper.transformerEntite(garageEntreDto);

        garage = garageRepository.saveAndFlush(garage);

        LOGGER.info(" fin ajouter Garage");

        return GarageMapper.transformerSortieDto(garage);
    }

    @Override
    @Transactional
    public GarageSortieDto modifierGarage(Long id, GarageEntreDto garageEntreDto) {

        LOGGER.info("debut modifierGarage {}", id);

        Garage garage = garageRepository.findByIdAndEtat(id, EntiteAuditAbstraite.EtatEntiteEnum.ACTIVE)
                .orElseThrow(() -> new GarageInexistantException(id));

        GarageMapper.transformerEntiteModifcation(garage, garageEntreDto);

        garage = garageRepository.saveAndFlush(garage);

        LOGGER.info("fin modifierGarage {}", id);

        return GarageMapper.transformerSortieDto(garage);
    }

    @Override
    @Transactional
    public void supprimerGarage(Long id) {

        LOGGER.info("debut supprimerGarage {}", id);

        Garage garage = garageRepository.findByIdAndEtat(id, EntiteAuditAbstraite.EtatEntiteEnum.ACTIVE)
                .orElseThrow(() -> new GarageInexistantException(id));

        garage.setEtat(EntiteAuditAbstraite.EtatEntiteEnum.INACTIVE);

        garageRepository.save(garage);

        LOGGER.info("fin supprimerGarage {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public GarageSortieDto trouverGarageParId(Long id) {

        LOGGER.info("debut trouver Garage Par Id {}", id);

        Garage garage = garageRepository.findByIdAndEtat(id, EntiteAuditAbstraite.EtatEntiteEnum.ACTIVE)
                .orElseThrow(() -> new GarageInexistantException( id));

        LOGGER.info("fin trouver Garage Par Id {}", id);

        return GarageMapper.transformerSortieDto(garage);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GarageSortieDto> chercherGarages(
            GarageReadRequete garageReadRequete,
            Pageable pageable
    ) {

        LOGGER.info("debut chercher Garages");

        Specification<Garage> specification =
                new GarageSpecification(garageReadRequete);

        Page<Garage> page = garageRepository.findAll(specification, pageable);

        LOGGER.info("fin chercher Garages");

        return page.map(GarageMapper::transformerSortieDto);
    }
}

package rahile.abdelmounim.garage.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rahile.abdelmounim.garage.commun.erreur.GarageInexistantException;
import rahile.abdelmounim.garage.commun.mappeur.GarageMappeur;
import rahile.abdelmounim.garage.domaine.EntiteAuditeAbstraite;
import rahile.abdelmounim.garage.domaine.Garage;
import rahile.abdelmounim.garage.repository.GarageRepository;
import rahile.abdelmounim.garage.repository.specification.GarageSpecification;
import rahile.abdelmounim.garage.service.GarageService;
import rahile.abdelmounim.garage.service.dto.GarageEntreDto;
import rahile.abdelmounim.garage.service.dto.GarageSortieDto;
import rahile.abdelmounim.garage.web.requete.GarageLectureRequete;

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

        LOGGER.info(" Début, ajouter garage: {}", garageEntreDto);

        Garage garage = GarageMappeur.transformerEntite(garageEntreDto);

        garage = garageRepository.saveAndFlush(garage);

        LOGGER.info(" Fin ajouter Garage, id : {}", garage.getId());

        return GarageMappeur.transformerSortieDto(garage);
    }

    @Override
    @Transactional
    public GarageSortieDto modifierGarage(Long id, GarageEntreDto garageEntreDto) {

        LOGGER.info("Début modifier Garage, id : {}", id);

        Garage garage = garageRepository.findByIdAndEtat(id, EntiteAuditeAbstraite.EtatEntiteEnum.ACTIVE)
                .orElseThrow(() -> new GarageInexistantException(id));

        GarageMappeur.transformerEntiteModifcation(garage, garageEntreDto);

        garage = garageRepository.saveAndFlush(garage);

        LOGGER.info("Fin modifier Garage, id : {}", id);

        return GarageMappeur.transformerSortieDto(garage);
    }

    @Override
    @Transactional
    public void desactiverGarage(Long id) {

        LOGGER.info("Début désactiver garage, id:  {}", id);

        Garage garage = garageRepository.findByIdAndEtat(id, EntiteAuditeAbstraite.EtatEntiteEnum.ACTIVE)
                .orElseThrow(() -> new GarageInexistantException(id));

        garageRepository.desactiverGarage(garage);

        LOGGER.info("Fin désactiver garage, id:  {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public GarageSortieDto trouverGarageParId(Long id) {

        LOGGER.info("Début trouver Garage Par Id: {}", id);

        Garage garage = garageRepository.findByIdAndEtat(id, EntiteAuditeAbstraite.EtatEntiteEnum.ACTIVE)
                .orElseThrow(() -> new GarageInexistantException(id));

        LOGGER.info("Fin trouver Garage Par Id: {}", id);

        return GarageMappeur.transformerSortieDto(garage);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GarageSortieDto> chercherGarages(
            GarageLectureRequete garageLectureRequete,
            Pageable pageable
    ) {

        LOGGER.info("Début chercher Garages: {}", garageLectureRequete);

        Specification<Garage> specification =
                new GarageSpecification(garageLectureRequete);

        Page<Garage> page = garageRepository.findAll(specification, pageable);

        LOGGER.info("Fin chercher Garages: {}");

        return page.map(GarageMappeur::transformerSortieDto);
    }
}

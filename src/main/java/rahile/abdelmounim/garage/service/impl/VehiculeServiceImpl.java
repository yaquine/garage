package rahile.abdelmounim.garage.service.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rahile.abdelmounim.garage.commun.erreur.GarageInexistantException;
import rahile.abdelmounim.garage.commun.erreur.VehiculeInexistantException;
import rahile.abdelmounim.garage.commun.mappeur.VehiculeMappeur;
import rahile.abdelmounim.garage.domaine.EntiteAuditeAbstraite;
import rahile.abdelmounim.garage.domaine.Garage;
import rahile.abdelmounim.garage.domaine.ModeleVehicule;
import rahile.abdelmounim.garage.domaine.Vehicule;
import rahile.abdelmounim.garage.evenement.dto.VehiculeAjoutEvenement;
import rahile.abdelmounim.garage.evenement.mappeur.VehiculeEvenementMappeur;
import rahile.abdelmounim.garage.evenement.producteur.VehiculeEvenementProducteur;
import rahile.abdelmounim.garage.repository.GarageRepository;
import rahile.abdelmounim.garage.repository.VehiculeRepository;
import rahile.abdelmounim.garage.service.VehiculeService;
import rahile.abdelmounim.garage.service.dto.VehiculeEntreDto;
import rahile.abdelmounim.garage.service.dto.VehiculeSortieDto;

import java.util.List;

@Service
@Transactional
public class VehiculeServiceImpl implements VehiculeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(VehiculeServiceImpl.class);


    private final VehiculeRepository vehiculeRepository;
    private final GarageRepository garageRepository;
    private final ApplicationEventPublisher evenementPublisheur;
    private final VehiculeEvenementProducteur vehiculeEvenementProducteur;

    public VehiculeServiceImpl(VehiculeRepository vehiculeRepository, GarageRepository garageRepository, ApplicationEventPublisher evenementPublisheur, VehiculeEvenementProducteur vehiculeEvenementProducteur) {
        this.vehiculeRepository = vehiculeRepository;
        this.garageRepository = garageRepository;
        this.evenementPublisheur = evenementPublisheur;
        this.vehiculeEvenementProducteur = vehiculeEvenementProducteur;
    }

    @Override
    public VehiculeSortieDto ajouterVehicule(Long garageId, VehiculeEntreDto vehiculeEntreDto) {

        LOGGER.info(" Début ajouter véhicule : {}, garage id : {}", vehiculeEntreDto, garageId);

        Garage garage = garageRepository.findByIdAndEtat(garageId, EntiteAuditeAbstraite.EtatEntiteEnum.ACTIVE)
                .orElseThrow(() -> new GarageInexistantException(garageId));

        Vehicule vehicule = VehiculeMappeur.transformerEntite(vehiculeEntreDto);

        garage.ajouterVehicule(vehicule);

        vehicule = vehiculeRepository.save(vehicule);

        evenementPublisheur.publishEvent(new VehiculeAjoutEvenement(vehicule.getId(), garageId));

        vehiculeEvenementProducteur.envoyerVehicule(VehiculeEvenementMappeur.mapperVehiculeEvenement(garageId,
                vehicule.getId()) );

        LOGGER.info(" Fin ajouter véhicule : {} ", vehicule);


        return VehiculeMappeur.transformerDto(vehicule);
    }

    @Override
    public VehiculeSortieDto modifierVehicule(Long vehiculeId, VehiculeEntreDto dto) {

        LOGGER.info(" Début modifier véhicule par id: {}", vehiculeId);

        Vehicule vehicule = vehiculeRepository.findByIdAndEtat(vehiculeId, EntiteAuditeAbstraite.EtatEntiteEnum.ACTIVE)
                .orElseThrow(() -> new VehiculeInexistantException(vehiculeId));

        VehiculeMappeur.mettreAJourEntity(vehicule, dto);

        LOGGER.info(" Fin modifier véhicule par id : {} ", vehiculeId);

        return VehiculeMappeur.transformerDto(vehicule);
    }

    @Override
    public void desactiverVehicule(Long vehiculeId) {

        LOGGER.info(" Début, désactiver véhicule par id : {}", vehiculeId);

        Vehicule vehicule = vehiculeRepository.findByIdAndEtat(vehiculeId, EntiteAuditeAbstraite.EtatEntiteEnum.ACTIVE)
                .orElseThrow(() -> new VehiculeInexistantException(vehiculeId));

        Garage garage = vehicule.getGarage();

        garage.desactiverVehicule(vehicule);

        garageRepository.save(garage);

        LOGGER.info(" Fin, désactiver véhicule par id: {}", vehiculeId);

    }

    @Override
    @Transactional(readOnly = true)
    public List<VehiculeSortieDto> listerVehiculesGarage(Long garageId) {

        LOGGER.info(" Début lister véhicules Garage pour le garage id: {}", garageId);


        return vehiculeRepository.findByGarageIdAndEtat(garageId, EntiteAuditeAbstraite.EtatEntiteEnum.ACTIVE)
                .stream()
                .map(VehiculeMappeur::transformerDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<VehiculeSortieDto> listerVehiculesParModele(String modele) {

        LOGGER.info(" Début liste véhicules par modèle : {}", modele);

        ModeleVehicule modeleEnum = ModeleVehicule.fromValue(modele);

        return vehiculeRepository.findByModeleAndEtat(modeleEnum, EntiteAuditeAbstraite.EtatEntiteEnum.ACTIVE)
                .stream()
                .map(VehiculeMappeur::transformerDto)
                .toList();
    }


}

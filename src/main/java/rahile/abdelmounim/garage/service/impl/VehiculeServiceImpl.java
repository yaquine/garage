package rahile.abdelmounim.garage.service.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rahile.abdelmounim.garage.commun.erreur.GarageInexistantException;
import rahile.abdelmounim.garage.commun.erreur.VehiculeInexistantException;
import rahile.abdelmounim.garage.commun.mappeur.VehiculeMapper;
import rahile.abdelmounim.garage.domaine.EntiteAuditAbstraite;
import rahile.abdelmounim.garage.domaine.Garage;
import rahile.abdelmounim.garage.domaine.ModeleVehicule;
import rahile.abdelmounim.garage.domaine.Vehicule;
import rahile.abdelmounim.garage.evenement.event.VehiculeAjouteEvent;
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
    private final ApplicationEventPublisher eventPublisher;

    public VehiculeServiceImpl(VehiculeRepository vehiculeRepository, GarageRepository garageRepository, ApplicationEventPublisher eventPublisher) {
        this.vehiculeRepository = vehiculeRepository;
        this.garageRepository = garageRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public VehiculeSortieDto ajouterVehicule(Long garageId, VehiculeEntreDto vehiculeEntreDto) {

        LOGGER.info(" debut ajouter vehicule: {}, garage : {}", vehiculeEntreDto, garageId);

        Garage garage = garageRepository.findByIdAndEtat(garageId, EntiteAuditAbstraite.EtatEntiteEnum.ACTIVE)
                .orElseThrow(() -> new GarageInexistantException(garageId));

        Vehicule vehicule = VehiculeMapper.transformerEntite(vehiculeEntreDto);

        garage.ajouterVehicule(vehicule);

        vehicule = vehiculeRepository.save(vehicule);

        eventPublisher.publishEvent(new VehiculeAjouteEvent(vehicule.getId(), garageId));

        LOGGER.info(" fin ajouter vehicule: {} ", vehicule);


        return VehiculeMapper.transformerDto(vehicule);
    }

    @Override
    public VehiculeSortieDto modifierVehicule(Long vehiculeId, VehiculeEntreDto dto) {

        LOGGER.info(" debut modifier Vehicule: {}", vehiculeId);

        Vehicule vehicule = vehiculeRepository.findByIdAndEtat(vehiculeId, EntiteAuditAbstraite.EtatEntiteEnum.ACTIVE)
                .orElseThrow(() -> new VehiculeInexistantException(vehiculeId));

        VehiculeMapper.mettreAJourEntity(vehicule, dto);

        LOGGER.info(" fin modifier Vehicule: {} ", vehiculeId);

        return VehiculeMapper.transformerDto(vehicule);
    }

    @Override
    public void supprimerVehicule(Long vehiculeId) {

        LOGGER.info(" debut supprimer Vehicule: {}", vehiculeId);

        Vehicule vehicule = vehiculeRepository.findByIdAndEtat(vehiculeId, EntiteAuditAbstraite.EtatEntiteEnum.ACTIVE)
                .orElseThrow(() -> new VehiculeInexistantException(vehiculeId));

        Garage garage = vehicule.getGarage();

        garage.supprimerVehicule(vehicule);

        garageRepository.save(garage);

        LOGGER.info(" fin supprimerVehicule: {}", vehiculeId);

    }

    @Override
    @Transactional(readOnly = true)
    public List<VehiculeSortieDto> listerVehiculesGarage(Long garageId) {

        LOGGER.info(" debut lister Vehicules Garage pour le garage: {}", garageId);


        return vehiculeRepository.findByGarageIdAndEtat(garageId, EntiteAuditAbstraite.EtatEntiteEnum.ACTIVE)
                .stream()
                .map(VehiculeMapper::transformerDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<VehiculeSortieDto> listerVehiculesParModele(String modele) {

        LOGGER.info(" debut listerVehiculesParModele pour le modele: {}", modele);

        ModeleVehicule modeleEnum = ModeleVehicule.fromValue(modele);

        return vehiculeRepository.findByModeleAndEtat(modeleEnum, EntiteAuditAbstraite.EtatEntiteEnum.ACTIVE)
                .stream()
                .map(VehiculeMapper::transformerDto)
                .toList();
    }
}

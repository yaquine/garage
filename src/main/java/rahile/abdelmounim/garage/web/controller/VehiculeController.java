package rahile.abdelmounim.garage.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import rahile.abdelmounim.garage.commun.mappeur.VehiculeMappeur;
import rahile.abdelmounim.garage.service.VehiculeService;
import rahile.abdelmounim.garage.service.dto.VehiculeEntreDto;
import rahile.abdelmounim.garage.service.dto.VehiculeSortieDto;
import rahile.abdelmounim.garage.web.api.VehiculeApi;
import rahile.abdelmounim.garage.web.requete.VehiculeReponse;
import rahile.abdelmounim.garage.web.requete.VehiculeRequete;

import java.util.List;

@RestController
public class VehiculeController implements VehiculeApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(VehiculeController.class);

    private final VehiculeService vehiculeService;

    public VehiculeController(VehiculeService vehiculeService) {
        this.vehiculeService = vehiculeService;
    }


    @Override
    public ResponseEntity<VehiculeReponse> ajouterVehicule(
            Long garageId,
            VehiculeRequete vehiculeRequete
    ) {

        LOGGER.info(" Début ajouter véhicule : {}, garage id : {}", vehiculeRequete, garageId);

        VehiculeEntreDto dto = VehiculeMappeur.transformerEntreDto(vehiculeRequete);

        VehiculeSortieDto sortieDto = vehiculeService.ajouterVehicule(garageId, dto);

        VehiculeReponse response = VehiculeMappeur.transformerReponse(sortieDto);

        LOGGER.info(" Fin ajouter véhicule par : {} ", response.id());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    public ResponseEntity<VehiculeReponse> modifierVehicule(
            Long vehiculeId,
            VehiculeRequete vehiculeRequete
    ) {

        LOGGER.info(" Début modifier Vehicule par id : {}", vehiculeId);

        VehiculeEntreDto dto = VehiculeMappeur.transformerEntreDto(vehiculeRequete);

        VehiculeSortieDto sortieDto = vehiculeService.modifierVehicule(vehiculeId, dto);

        VehiculeReponse response = VehiculeMappeur.transformerReponse(sortieDto);

        LOGGER.info(" Fin modifier Vehicule par id: {} ", vehiculeId);

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Void> desactiverVehicule(Long vehiculeId) {

        LOGGER.info(" Début, désactiver véhicule par id : {}", vehiculeId);

        vehiculeService.desactiverVehicule(vehiculeId);

        LOGGER.info(" Fin, désactiver véhicule par id : {}", vehiculeId);

        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<VehiculeReponse>> listerVehiculesGarage(Long garageId) {

        LOGGER.info(" Début liste véhicule de garage pour le garage id: {}", garageId);

        List<VehiculeSortieDto> dtos = vehiculeService.listerVehiculesGarage(garageId);

        List<VehiculeReponse> responses =
                dtos.stream()
                        .map(VehiculeMappeur::transformerReponse)
                        .toList();

        LOGGER.info(" Fin liste véhicule de garage pour le garage id : {}", garageId);

        return ResponseEntity.ok(responses);
    }

    @Override
    public ResponseEntity<List<VehiculeReponse>> listerVehiculeParModele(String modele) {

        LOGGER.info(" Début lister véhicules par modèle : {}", modele);

        List<VehiculeSortieDto> dtos =
                vehiculeService.listerVehiculesParModele(modele);

        List<VehiculeReponse> responses =
                dtos.stream()
                        .map(VehiculeMappeur::transformerReponse)
                        .toList();

        LOGGER.info(" Fin lister véhicules par modèle : {}", modele);

        return ResponseEntity.ok(responses);
    }
}

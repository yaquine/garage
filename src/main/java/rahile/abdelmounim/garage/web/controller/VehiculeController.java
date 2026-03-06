package rahile.abdelmounim.garage.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import rahile.abdelmounim.garage.commun.mappeur.VehiculeMapper;
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

        LOGGER.info(" debut ajouter vehicule: {}, garage : {}", vehiculeRequete, garageId);

        VehiculeEntreDto dto = VehiculeMapper.transformerEntreDto(vehiculeRequete);

        VehiculeSortieDto sortieDto = vehiculeService.ajouterVehicule(garageId, dto);

        VehiculeReponse response = VehiculeMapper.transformerReponse(sortieDto);

        LOGGER.info(" fin ajouter vehicule: {} ", response.id());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    public ResponseEntity<VehiculeReponse> modifierVehicule(
            Long vehiculeId,
            VehiculeRequete vehiculeRequete
    ) {

        LOGGER.info(" debut modifier Vehicule: {}", vehiculeId);

        VehiculeEntreDto dto = VehiculeMapper.transformerEntreDto(vehiculeRequete);

        VehiculeSortieDto sortieDto = vehiculeService.modifierVehicule(vehiculeId, dto);

        VehiculeReponse response = VehiculeMapper.transformerReponse(sortieDto);

        LOGGER.info(" fin modifier Vehicule: {} ", vehiculeId);

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Void> supprimerVehicule(Long vehiculeId) {

        LOGGER.info(" debut supprimer Vehicule: {}", vehiculeId);

        vehiculeService.supprimerVehicule(vehiculeId);

        LOGGER.info(" fin supprimer Vehicule: {}", vehiculeId);

        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<VehiculeReponse>> listerVehiculesGarage(Long garageId) {

        LOGGER.info(" debut lister Vehicules Garage pour le garage: {}", garageId);

        List<VehiculeSortieDto> dtos = vehiculeService.listerVehiculesGarage(garageId);

        List<VehiculeReponse> responses =
                dtos.stream()
                        .map(VehiculeMapper::transformerReponse)
                        .toList();

        LOGGER.info(" fin lister Vehicules Garage pour le garage: {}", garageId);

        return ResponseEntity.ok(responses);
    }

    @Override
    public ResponseEntity<List<VehiculeReponse>> listerVehiculesParModele(String modele) {

        LOGGER.info(" debut lister Vehicules Par Modele pour le modele: {}", modele);

        List<VehiculeSortieDto> dtos =
                vehiculeService.listerVehiculesParModele(modele);

        List<VehiculeReponse> responses =
                dtos.stream()
                        .map(VehiculeMapper::transformerReponse)
                        .toList();

        LOGGER.info(" fin lister Vehicules Par Modele pour le modele: {}", modele);

        return ResponseEntity.ok(responses);
    }
}

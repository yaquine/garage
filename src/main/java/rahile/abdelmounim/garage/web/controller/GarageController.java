package rahile.abdelmounim.garage.web.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import rahile.abdelmounim.garage.commun.mappeur.GarageMappeur;
import rahile.abdelmounim.garage.service.GarageService;
import rahile.abdelmounim.garage.service.dto.GarageEntreDto;
import rahile.abdelmounim.garage.service.dto.GarageSortieDto;
import rahile.abdelmounim.garage.web.api.GarageApi;
import rahile.abdelmounim.garage.web.reponse.GarageReponse;
import rahile.abdelmounim.garage.web.reponse.PageResponse;
import rahile.abdelmounim.garage.web.requete.GarageLectureRequete;
import rahile.abdelmounim.garage.web.requete.GarageRequete;

@RestController
public class GarageController implements GarageApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(GarageController.class);

    private final GarageService garageService;


    public GarageController(GarageService garageService) {
        this.garageService = garageService;
    }

    @Override
    public ResponseEntity<GarageReponse> ajouterGarage(GarageRequete garageRequete) {

        LOGGER.info(" Début ajouter Garage : {}", garageRequete);

        GarageEntreDto garageEntreDto = GarageMappeur.transformerDto(garageRequete);

        GarageSortieDto garageSortieDto = garageService.ajouterGarage(garageEntreDto);

        LOGGER.info(" Fin ajouter Garage: {}", garageSortieDto);

        return new ResponseEntity<>(GarageMappeur.transformerReponse(garageSortieDto), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<GarageReponse> modifierGarage(Long id, GarageRequete garageRequete) {

        LOGGER.info(" Début modifier Garage: {}", garageRequete);

        GarageEntreDto garageEntreDto = GarageMappeur.transformerDto(garageRequete);

        GarageSortieDto garageSortieDto = garageService.modifierGarage(id, garageEntreDto);

        LOGGER.info(" Fin modifier Garage: {}", garageRequete);

        return new ResponseEntity<>(GarageMappeur.transformerReponse(garageSortieDto), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> desactiverGarage(Long id) {

        LOGGER.info(" Début désactiver garage par id : {}", id);

        garageService.desactiverGarage(id);

        LOGGER.info(" Fin désactiver garage par id : ");

        return new ResponseEntity<>(HttpStatus.OK);
    }


    @Override
    public ResponseEntity<GarageReponse> trouverGarageParId(Long id) {

        LOGGER.info("Début trouver garage par id :{}", id);

        GarageSortieDto dto = garageService.trouverGarageParId(id);

        LOGGER.info("Fin trouver garage par id {}", id);

        return ResponseEntity.ok(GarageMappeur.transformerReponse(dto));
    }

    @Override
    public ResponseEntity<PageResponse<GarageReponse>> chercherGarage(GarageLectureRequete garageLectureRequete, Pageable pageable) {

        LOGGER.info("Début chercher Garages, requête: {} ", garageLectureRequete);

        Page<GarageReponse> page =
                garageService.chercherGarages(garageLectureRequete, pageable)
                        .map(GarageMappeur::transformerReponse);

        LOGGER.info("Fin chercher Garages, requête: {} ", garageLectureRequete);

        return ResponseEntity.ok(GarageMappeur.transformerReponse(page));
    }
}

package rahile.abdelmounim.garage.web.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import rahile.abdelmounim.garage.commun.mappeur.GarageMapper;
import rahile.abdelmounim.garage.service.GarageService;
import rahile.abdelmounim.garage.service.dto.GarageEntreDto;
import rahile.abdelmounim.garage.service.dto.GarageSortieDto;
import rahile.abdelmounim.garage.web.api.GarageApi;
import rahile.abdelmounim.garage.web.reponse.GarageReponse;
import rahile.abdelmounim.garage.web.reponse.PageResponse;
import rahile.abdelmounim.garage.web.requete.GarageReadRequete;
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

        LOGGER.info(" debut ajouter Garage: {}", garageRequete);

        GarageEntreDto garageEntreDto = GarageMapper.transformerDto(garageRequete);

        GarageSortieDto garageSortieDto  = garageService.ajouterGarage(garageEntreDto);

        LOGGER.info(" fin ajouter Garage: {}", garageSortieDto);

        return new ResponseEntity<>(GarageMapper.transformerReponse(garageSortieDto), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<GarageReponse> modifierGarage(Long id, GarageRequete garageRequete) {

        LOGGER.info(" debut modifierGarage: {}", garageRequete);

        GarageEntreDto garageEntreDto = GarageMapper.transformerDto(garageRequete);

        GarageSortieDto garageSortieDto  = garageService.modifierGarage(id, garageEntreDto);

        LOGGER.info(" fin modifierGarage: {}", garageRequete);

        return new ResponseEntity<>(GarageMapper.transformerReponse(garageSortieDto), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> supprimerGarage(Long id) {

        LOGGER.info(" debut supprimer Garage: {}", id);

        garageService.supprimerGarage(id);

        LOGGER.info(" fin supprimer Garage: ");

        return new ResponseEntity<>(HttpStatus.OK);
    }


    @Override
    public ResponseEntity<GarageReponse> trouverGarageParId(Long id) {

        LOGGER.info("debut findGarageById {}", id);

        GarageSortieDto dto = garageService.trouverGarageParId(id);

        LOGGER.info("fin findGarageById {}", id);

        return ResponseEntity.ok(GarageMapper.transformerReponse(dto));
    }

    @Override
    public ResponseEntity<PageResponse<GarageReponse>> chercherGarages(GarageReadRequete garageReadRequete, Pageable pageable) {

        LOGGER.info("debut chercher Garages {} ", garageReadRequete);

        Page<GarageReponse> page =
                garageService.chercherGarages(garageReadRequete, pageable)
                        .map(GarageMapper::transformerReponse);

        LOGGER.info("fin chercher Garages {} ", garageReadRequete);

        return ResponseEntity.ok(GarageMapper.transformerReponse(page));
    }
}

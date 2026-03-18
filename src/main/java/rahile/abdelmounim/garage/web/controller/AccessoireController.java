package rahile.abdelmounim.garage.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import rahile.abdelmounim.garage.commun.mappeur.AccessoireMappeur;
import rahile.abdelmounim.garage.service.AccessoireService;
import rahile.abdelmounim.garage.service.dto.AccessoireEntreDto;
import rahile.abdelmounim.garage.service.dto.AccessoireSortieDto;
import rahile.abdelmounim.garage.web.api.AccessoireApi;
import rahile.abdelmounim.garage.web.reponse.AccessoireReponse;
import rahile.abdelmounim.garage.web.requete.AccessoireRequete;

import java.util.List;

@RestController
public class AccessoireController implements AccessoireApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccessoireController.class);

    private final AccessoireService accessoireService;

    public AccessoireController(AccessoireService accessoireService) {
        this.accessoireService = accessoireService;
    }

    @Override
    public ResponseEntity<AccessoireReponse> ajouterAccessoire(
            Long vehiculeId,
            AccessoireRequete accessoireRequete
    ) {

        LOGGER.info("Début ajouter accessoire véhicule Id: {} accessoire: {}", vehiculeId, accessoireRequete);

        AccessoireEntreDto dto = AccessoireMappeur.transformerEntreDto(accessoireRequete);

        AccessoireSortieDto sortieDto = accessoireService.ajouterAccessoire(vehiculeId, dto);

        AccessoireReponse response = AccessoireMappeur.transformerReponse(sortieDto);

        LOGGER.info("Fin ajouter accessoire véhicule Id:: {}", response.id());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    public ResponseEntity<AccessoireReponse> modifierAccessoire(Long accessoireId, AccessoireRequete accessoireRequete) {

        LOGGER.info("Debut modifier Accessoire id: {}", accessoireId);

        AccessoireEntreDto dto = AccessoireMappeur.transformerEntreDto(accessoireRequete);

        AccessoireSortieDto sortieDto = accessoireService.modifierAccessoire(accessoireId, dto);

        AccessoireReponse response = AccessoireMappeur.transformerReponse(sortieDto);

        LOGGER.info("Fin modifier Accessoire id: {}", accessoireId);

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Void> desactiverAccessoire(Long accessoireId) {

        LOGGER.info("Debut desactiver Accessoire par id: {}", accessoireId);

        accessoireService.desactiverAccessoire(accessoireId);

        LOGGER.info("Fin desactiver Accessoire par id: {}", accessoireId);

        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<AccessoireReponse>> listerAccessoireVehicule(Long vehiculeId) {

        LOGGER.info("Debut lister Accessoires Vehicule par Id: {}", vehiculeId);

        List<AccessoireSortieDto> dtos = accessoireService.listerAccessoiresVehicule(vehiculeId);

        List<AccessoireReponse> responses =
                dtos.stream()
                        .map(AccessoireMappeur::transformerReponse)
                        .toList();

        LOGGER.info("Fin lister Accessoires Vehicule par Id: {}", vehiculeId);

        return ResponseEntity.ok(responses);
    }
}

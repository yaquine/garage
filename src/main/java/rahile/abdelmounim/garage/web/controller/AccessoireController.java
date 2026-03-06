package rahile.abdelmounim.garage.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import rahile.abdelmounim.garage.commun.mappeur.AccessoireMapper;
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

        LOGGER.info("debut ajouterAccessoire vehiculeId: {} accessoire: {}", vehiculeId, accessoireRequete);

        AccessoireEntreDto dto = AccessoireMapper.transformerEntreDto(accessoireRequete);

        AccessoireSortieDto sortieDto = accessoireService.ajouterAccessoire(vehiculeId, dto);

        AccessoireReponse response = AccessoireMapper.transformerReponse(sortieDto);

        LOGGER.info("fin ajouterAccessoire id: {}", response.id());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    public ResponseEntity<AccessoireReponse> modifierAccessoire(Long accessoireId, AccessoireRequete accessoireRequete) {

        LOGGER.info("debut modifier Accessoire id: {}", accessoireId);

        AccessoireEntreDto dto = AccessoireMapper.transformerEntreDto(accessoireRequete);

        AccessoireSortieDto sortieDto = accessoireService.modifierAccessoire(accessoireId, dto);

        AccessoireReponse response = AccessoireMapper.transformerReponse(sortieDto);

        LOGGER.info("fin modifier Accessoire id: {}", accessoireId);

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Void> supprimerAccessoire(Long accessoireId) {

        LOGGER.info("debut supprimer Accessoire id: {}", accessoireId);

        accessoireService.supprimerAccessoire(accessoireId);

        LOGGER.info("fin supprimer Accessoire id: {}", accessoireId);

        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<AccessoireReponse>> listerAccessoiresVehicule(Long vehiculeId) {

        LOGGER.info("debut lister Accessoires Vehicule vehiculeId: {}", vehiculeId);

        List<AccessoireSortieDto> dtos = accessoireService.listerAccessoiresVehicule(vehiculeId);

        List<AccessoireReponse> responses =
                dtos.stream()
                        .map(AccessoireMapper::transformerReponse)
                        .toList();

        LOGGER.info("fin lister Accessoires Vehicule vehiculeId: {}", vehiculeId);

        return ResponseEntity.ok(responses);
    }
}

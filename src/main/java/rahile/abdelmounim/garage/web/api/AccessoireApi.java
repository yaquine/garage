package rahile.abdelmounim.garage.web.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rahile.abdelmounim.garage.web.reponse.AccessoireReponse;
import rahile.abdelmounim.garage.web.requete.AccessoireRequete;

import java.util.List;

@Tag(name = "Accessoire", description = "Gestion des accessoires")
@RequestMapping("/api/v1/accessoires")
public interface AccessoireApi {


    @PostMapping("/vehicules/{vehiculeId}")
    ResponseEntity<AccessoireReponse> ajouterAccessoire(@PathVariable Long vehiculeId,
                                                        @RequestBody AccessoireRequete accessoireRequete);


    @PutMapping("/{accessoireId}")
    ResponseEntity<AccessoireReponse> modifierAccessoire(@PathVariable Long accessoireId,
                                                         @RequestBody AccessoireRequete accessoireRequete);


    @PutMapping("/{accessoireId}/desactiver")
    ResponseEntity<Void> desactiverAccessoire(@PathVariable Long accessoireId);


    @GetMapping("/vehicules/{vehiculeId}")
    ResponseEntity<List<AccessoireReponse>> listerAccessoireVehicule(@PathVariable Long vehiculeId);

}

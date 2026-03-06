package rahile.abdelmounim.garage.web.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rahile.abdelmounim.garage.web.requete.VehiculeReponse;
import rahile.abdelmounim.garage.web.requete.VehiculeRequete;

import java.util.List;

@Tag(name = "Vehicule", description = "Gestion des vehicules")
@RequestMapping("/api/v1/vehicules")
public interface VehiculeApi {

    @PostMapping("/garages/{garageId}")
    ResponseEntity<VehiculeReponse> ajouterVehicule(
            @PathVariable Long garageId,
            @RequestBody VehiculeRequete vehiculeRequete
    );

    @PutMapping("/{vehiculeId}")
    ResponseEntity<VehiculeReponse> modifierVehicule(
            @PathVariable Long vehiculeId,
            @RequestBody VehiculeRequete vehiculeRequete
    );

    @PutMapping("/{vehiculeId}/disactiver")
    ResponseEntity<Void> supprimerVehicule(
            @PathVariable Long vehiculeId
    );

    @GetMapping("/garages/{garageId}")
    ResponseEntity<List<VehiculeReponse>> listerVehiculesGarage(
            @PathVariable Long garageId
    );

    @GetMapping("/modeles/{modele}")
    ResponseEntity<List<VehiculeReponse>> listerVehiculesParModele(
            @PathVariable String modele
    );
}

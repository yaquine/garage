package rahile.abdelmounim.garage.web.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rahile.abdelmounim.garage.web.reponse.GarageReponse;
import rahile.abdelmounim.garage.web.reponse.PageResponse;
import rahile.abdelmounim.garage.web.requete.GarageReadRequete;
import rahile.abdelmounim.garage.web.requete.GarageRequete;

@Tag(name = "Garage", description = "EndPoints pour gestions des garages")
@RequestMapping("/api/v1/garages")
public interface GarageApi {

    @Operation(summary = "Creer un garage")
    @ApiResponse(responseCode = "201", description = "Garage cree avec succes")
    @PostMapping
    ResponseEntity<GarageReponse> ajouterGarage(
            @RequestBody GarageRequete garageRequete
    );

    @Operation(summary = "Modifier Garage")
    @ApiResponse(responseCode = "200", description = "Garage modifie avec succes")
    @PutMapping("/{id}")
    ResponseEntity<GarageReponse> modifierGarage(
            @PathVariable Long id, @RequestBody GarageRequete garageRequete
    );

    @Operation(summary = "Supprimer garage")
    @ApiResponse(responseCode = "204", description = "Garage supprime avec succes")
    @PutMapping("/{id}/disactiver")
    ResponseEntity<Void> supprimerGarage(
            @PathVariable Long id
    );


    @Operation(summary = "Trouver garage par id")
    @ApiResponse(responseCode = "200", description = "Garage trouve")
    @GetMapping("/{id}")
    ResponseEntity<GarageReponse> trouverGarageParId(@PathVariable Long id);


    @PostMapping("/chercher")
    @Operation(summary = "Rechercher garages avec pagination")
    ResponseEntity<PageResponse<GarageReponse>> chercherGarages(
            @RequestBody GarageReadRequete garageReadRequete,
            Pageable pageable
    );
}

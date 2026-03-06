package rahile.abdelmounim.garage.web.requete;

public record VehiculeReponse(

        Long id,
        String modele,
        Integer anneeFabrication,
        String typeCarburant,
        String matricule,
        Long garageId

) {}

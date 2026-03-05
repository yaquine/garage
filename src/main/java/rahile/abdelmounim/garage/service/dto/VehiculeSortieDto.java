package rahile.abdelmounim.garage.service.dto;


public record VehiculeSortieDto(

        Long id,

        String modele,

        Integer anneeFabrication,

        String typeCarburant,

        String matricule,

        Long garageId

) {}

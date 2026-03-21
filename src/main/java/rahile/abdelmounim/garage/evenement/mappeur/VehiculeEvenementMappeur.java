package rahile.abdelmounim.garage.evenement.mappeur;

import rahile.abdelmounim.garage.evenement.dto.VehiculeAjoutEvenement;

public interface VehiculeEvenementMappeur {

    public static VehiculeAjoutEvenement mapperVehiculeEvenement(Long vehiculeId, Long garageId) {
        return new VehiculeAjoutEvenement(vehiculeId, garageId);
    }
}

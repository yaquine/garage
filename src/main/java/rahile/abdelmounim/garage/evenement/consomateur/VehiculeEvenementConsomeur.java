package rahile.abdelmounim.garage.evenement.consomateur;


import rahile.abdelmounim.garage.evenement.dto.VehiculeAjoutEvenement;

public interface VehiculeEvenementConsomeur {


    void gererVehiculeCreationEvenement(VehiculeAjoutEvenement vehiculeAjoutEvenement);
}

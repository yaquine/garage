package rahile.abdelmounim.garage.service;

import rahile.abdelmounim.garage.service.dto.VehiculeEntreDto;
import rahile.abdelmounim.garage.service.dto.VehiculeSortieDto;

import java.util.List;

public interface VehiculeService {


    VehiculeSortieDto ajouterVehicule(Long garageId, VehiculeEntreDto dto);

    VehiculeSortieDto modifierVehicule(Long vehiculeId, VehiculeEntreDto dto);

    void desactiverVehicule(Long vehiculeId);

    List<VehiculeSortieDto> listerVehiculesGarage(Long garageId);

    List<VehiculeSortieDto> listerVehiculesParModele(String modele);


}

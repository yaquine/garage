package rahile.abdelmounim.garage.commun.mappeur;

import rahile.abdelmounim.garage.domaine.CarburantEnum;
import rahile.abdelmounim.garage.domaine.ModeleVehicule;
import rahile.abdelmounim.garage.domaine.Vehicule;
import rahile.abdelmounim.garage.service.dto.VehiculeEntreDto;
import rahile.abdelmounim.garage.service.dto.VehiculeSortieDto;
import rahile.abdelmounim.garage.web.requete.VehiculeReponse;
import rahile.abdelmounim.garage.web.requete.VehiculeRequete;

import java.time.Year;

public interface VehiculeMappeur {

    static Vehicule transformerEntite(VehiculeEntreDto vehiculeEntreDto) {

        if (vehiculeEntreDto == null) {
            return null;
        }

        Vehicule vehicule = new Vehicule();

        vehicule.setModele(ModeleVehicule.fromValue(vehiculeEntreDto.modele()));
        vehicule.setAnneeFabrication(Year.of(vehiculeEntreDto.anneeFabrication()));
        vehicule.setTypeCarburant(CarburantEnum.fromValue(vehiculeEntreDto.typeCarburant()));
        vehicule.setMatricule(vehiculeEntreDto.matricule());

        return vehicule;
    }

    static VehiculeSortieDto transformerDto(Vehicule vehicule) {

        if (vehicule == null) {
            return null;
        }

        return new VehiculeSortieDto(
                vehicule.getId(),
                vehicule.getModele().name(),
                vehicule.getAnneeFabrication().getValue(),
                vehicule.getTypeCarburant().name(),
                vehicule.getMatricule(),
                vehicule.getGarage().getId()
        );
    }

    static void mettreAJourEntity(Vehicule vehicule, VehiculeEntreDto dto) {

        if (vehicule == null || dto == null) {
            return;
        }
        if (dto.modele() != null) {
            vehicule.setModele(ModeleVehicule.fromValue(dto.modele().trim()));
        }
        if (dto.anneeFabrication() > 0) {
            vehicule.setAnneeFabrication(Year.of(dto.anneeFabrication()));
        }
        if (dto.typeCarburant() != null) {
            vehicule.setTypeCarburant(CarburantEnum.fromValue(dto.typeCarburant()));
        }

        vehicule.setMatricule(dto.matricule());
    }

    static VehiculeEntreDto transformerEntreDto(VehiculeRequete vehiculeRequete) {

        return new VehiculeEntreDto(
                vehiculeRequete.modele(),
                vehiculeRequete.anneeFabrication(),
                vehiculeRequete.typeCarburant(),
                vehiculeRequete.matricule()
        );
    }

    static VehiculeReponse transformerReponse(VehiculeSortieDto vehiculeSortieDto) {

        if (vehiculeSortieDto == null) {
            return null;
        }

        return new VehiculeReponse(
                vehiculeSortieDto.id(),
                vehiculeSortieDto.modele(),
                vehiculeSortieDto.anneeFabrication(),
                vehiculeSortieDto.typeCarburant(),
                vehiculeSortieDto.matricule(),
                vehiculeSortieDto.garageId()
        );
    }
}

package rahile.abdelmounim.garage.commun.erreur;

public class VehiculeCapacitieSurpasseException extends BusinessException {

    public VehiculeCapacitieSurpasseException(Long id) {

        super("VEHICLE_CAP_SUR_EXCEPTION",
                "Vehicule Capacitie Surpasse pour le garage" + id);
    }

}

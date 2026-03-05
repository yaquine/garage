package rahile.abdelmounim.garage.commun.erreur;

public class VehiculeInexistantException extends BusinessException {

    public VehiculeInexistantException(Long id) {

        super("VEHICULE_INEXISTANT",
                "Vehicule n'existe pas" + id);
    }

}

package rahile.abdelmounim.garage.commun.erreur;

public class GarageInexistantException extends BusinessException {

    public GarageInexistantException(Long id) {

        super("GARAGE_INEXISTENT",
                "Garage n'existe pas" + id);
    }

}

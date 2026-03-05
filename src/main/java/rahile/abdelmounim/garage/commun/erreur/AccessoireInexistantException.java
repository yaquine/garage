package rahile.abdelmounim.garage.commun.erreur;

public class AccessoireInexistantException extends BusinessException {

    public AccessoireInexistantException(Long id) {

        super("ACCESSOIRE_INEXISTANT",
                "Accessoire n'existe pas" + id);
    }

}

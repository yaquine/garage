package rahile.abdelmounim.garage.commun.erreur;

public class InvalidEnumException extends BusinessException {

    public InvalidEnumException(String code) {

        super("INVALID_ENUM_EXCEPTION",
                "Invalid Input enum for " + code);
    }

}

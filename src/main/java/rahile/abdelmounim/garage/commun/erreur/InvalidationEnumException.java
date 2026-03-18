package rahile.abdelmounim.garage.commun.erreur;

public class InvalidationEnumException extends BusinessException {

    public InvalidationEnumException(String code) {

        super("INVALID_ENUM_EXCEPTION",
                "Invalid Input enum for " + code);
    }

}

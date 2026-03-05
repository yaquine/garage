package rahile.abdelmounim.garage.domaine;

import rahile.abdelmounim.garage.commun.erreur.InvalidEnumException;

public enum CarburantEnum {

    ESSENCE("ESSENCE"),
    DIESEL("DIESEL"),
    GPL("GPL"),
    ELECTRIQUE("ELECTRIQUE"),
    HYBRIDE("HYBRIDE"),
    HYBRIDE_RECHARGEABLE("HYBRIDE_RECHARGEABLE"),
    BIOETHANOL("BIOETHANOL"),
    HYDROGENE("HYDROGENE");

    private final String value;

    CarburantEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static CarburantEnum fromValue(String value) {

        for (CarburantEnum carburant : values()) {
            if (carburant.value.equalsIgnoreCase(value)) {
                return carburant;
            }
        }

        throw new InvalidEnumException("VEHICULE_CARBURANT" );
    }
}

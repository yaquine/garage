package rahile.abdelmounim.garage.domaine;

import rahile.abdelmounim.garage.commun.erreur.InvalidationEnumException;

public enum ModeleVehicule {

    RENAULT("RENAULT"),
    PEUGEOT("PEUGEOT"),
    TOYOTA("TOYOTA");

    private final String value;

    ModeleVehicule(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static ModeleVehicule fromValue(String value) {

        for (ModeleVehicule modele : values()) {
            if (modele.value.equalsIgnoreCase(value)) {
                return modele;
            }
        }

        throw new InvalidationEnumException("MODELE_VEHICULE") {
        };
    }
    
}

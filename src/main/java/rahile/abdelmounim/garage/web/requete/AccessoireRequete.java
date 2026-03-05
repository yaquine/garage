package rahile.abdelmounim.garage.web.requete;

import java.math.BigDecimal;

public record AccessoireRequete(
        String nom,
        String description,
        BigDecimal prix,
        String type,
        String code
) {}

package rahile.abdelmounim.garage.web.reponse;

import java.math.BigDecimal;

public record AccessoireReponse(
        Long id,
        String nom,
        String description,
        BigDecimal prix,
        String type,
        String code,
        Long vehiculeId
) {
}

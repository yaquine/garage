package rahile.abdelmounim.garage.commun.erreur;

import java.time.OffsetDateTime;

public record ApiErrorResponse(
        String code,
        String message,
        OffsetDateTime timestamp
) {
}

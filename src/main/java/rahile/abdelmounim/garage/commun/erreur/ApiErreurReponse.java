package rahile.abdelmounim.garage.commun.erreur;

import java.time.OffsetDateTime;

public record ApiErreurReponse(
        String code,
        String message,
        OffsetDateTime timestamp
) {
}

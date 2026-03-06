package rahile.abdelmounim.garage.service.dto;

import java.time.LocalTime;

public record HoraireOuvertureDto(
        LocalTime tempsDebut,
        LocalTime tempsFin) {
}

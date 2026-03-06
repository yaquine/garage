package rahile.abdelmounim.garage.service.dto;

import java.time.DayOfWeek;
import java.util.Map;
import java.util.Set;

public record GarageSortieDto(
        Long id,
        String nom,
        String addresse,
        String telephone,
        String email,
        Map<DayOfWeek, Set<HoraireOuvertureDto>> horaireOuverturesMap) {
}

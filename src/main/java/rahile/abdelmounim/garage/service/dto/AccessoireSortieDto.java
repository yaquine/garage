package rahile.abdelmounim.garage.service.dto;

import java.math.BigDecimal;

public record AccessoireSortieDto(

        Long id,
        String nom,
        String description,
        BigDecimal prix,
        String type,
        String code,
        Long vehiculeId

) {
}
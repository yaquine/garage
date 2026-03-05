package rahile.abdelmounim.garage.service.dto;

import java.math.BigDecimal;

public record AccessoireEntreDto(

        String nom,

        String description,

        BigDecimal prix,

        String type,

        String code

) {}

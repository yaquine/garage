package rahile.abdelmounim.garage.web.reponse;

import rahile.abdelmounim.garage.web.requete.HoraireOuvertureRequete;

import java.time.DayOfWeek;
import java.util.Map;
import java.util.Set;

public record GarageReponse(
        Long id,
        String nom,
        String addresse,
        String telephone,
        String email,
        Map<DayOfWeek, Set<HoraireOuvertureRequete>> horaireOuverturesMap
) {
}
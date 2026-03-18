package rahile.abdelmounim.garage.web.requete;

import java.time.DayOfWeek;
import java.util.Map;
import java.util.Set;

public record GarageRequete(String nom,
                            String addresse,
                            String telephone,
                            String email,
                            Map<DayOfWeek, Set<HoraireOuvertureRequete>> horaireOuverturesMap) {


}

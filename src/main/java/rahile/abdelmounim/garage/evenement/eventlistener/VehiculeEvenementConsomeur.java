package rahile.abdelmounim.garage.evenement.eventlistener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import rahile.abdelmounim.garage.evenement.dto.VehiculeAjoutEvenement;

@Component
public class VehiculeEvenementConsomeur {

    private static final Logger LOGGER = LoggerFactory.getLogger(VehiculeEvenementConsomeur.class);

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void gererVehiculeCreationEvenement(VehiculeAjoutEvenement vehiculeAjoutEvenement) {

        LOGGER.info(
                "Gérer véhicule création événement traité après commit : vehiculeId = {}, garageId = {}",
                vehiculeAjoutEvenement.vehiculeId(),
                vehiculeAjoutEvenement.garageId()
        );
    }
}

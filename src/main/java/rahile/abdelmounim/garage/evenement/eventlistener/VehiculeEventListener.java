package rahile.abdelmounim.garage.evenement.eventlistener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import rahile.abdelmounim.garage.evenement.event.VehiculeAjouteEvent;

@Component
public class VehiculeEventListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(VehiculeEventListener.class);

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleVehiculeCreatedEvent(VehiculeAjouteEvent vehiculeAjouteEvent) {

        LOGGER.info(
                "VehiculeCreatedEvent traité après commit : vehiculeId={}, garageId={}",
                vehiculeAjouteEvent.vehiculeId(),
                vehiculeAjouteEvent.garageId()
        );
    }
}

package rahile.abdelmounim.garage.evenement.consomateur.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import rahile.abdelmounim.garage.evenement.dto.VehiculeAjoutEvenement;
import rahile.abdelmounim.garage.evenement.consomateur.VehiculeEvenementConsomeur;

@Component
public class VehiculeEvenementConsomeurImpl implements VehiculeEvenementConsomeur {

    private static final Logger LOGGER = LoggerFactory.getLogger(VehiculeEvenementConsomeurImpl.class);

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

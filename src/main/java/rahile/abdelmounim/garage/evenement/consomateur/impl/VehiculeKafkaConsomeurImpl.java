package rahile.abdelmounim.garage.evenement.consomateur.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import rahile.abdelmounim.garage.evenement.dto.VehiculeAjoutEvenement;
import rahile.abdelmounim.garage.evenement.consomateur.VehiculeKafkaConsomeur;

@Component
public class VehiculeKafkaConsomeurImpl implements VehiculeKafkaConsomeur {

    private static final Logger LOGGER = LoggerFactory.getLogger(VehiculeKafkaConsomeurImpl.class);


    @KafkaListener(topics = "${kafka.topics.main}", groupId = "${spring.kafka.consumer.group-id}", containerFactory = "kafkaListenerContainerFactory", id = "mainListener")
    public void ecouterVehiculeEvenementPrincipal(@Payload VehiculeAjoutEvenement vehiculeAjoutEvenement,
                                                  @Header(KafkaHeaders.RECEIVED_TOPIC) String topic, @Header(KafkaHeaders.GROUP_ID) String groupId,
                                                  Acknowledgment acknowledgment) {


        LOGGER.info(">>>> Consommation de l'événement Ajout Véhicule: {}", vehiculeAjoutEvenement);

        acknowledgment.acknowledge();

    }
}

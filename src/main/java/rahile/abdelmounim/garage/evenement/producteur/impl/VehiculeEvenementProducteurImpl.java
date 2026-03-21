package rahile.abdelmounim.garage.evenement.producteur.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import rahile.abdelmounim.garage.evenement.dto.VehiculeAjoutEvenement;
import rahile.abdelmounim.garage.evenement.producteur.VehiculeEvenementProducteur;

@Component
public class VehiculeEvenementProducteurImpl implements VehiculeEvenementProducteur {

    private static final Logger LOGGER = LoggerFactory.getLogger(VehiculeEvenementProducteurImpl.class);

    private final KafkaTemplate<String, VehiculeAjoutEvenement> kafkaTemplate;

    @Value("${kafka.topics.main}")
    private String topic;

    public VehiculeEvenementProducteurImpl(KafkaTemplate<String, VehiculeAjoutEvenement> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void envoyerVehicule(VehiculeAjoutEvenement evenement) {

        LOGGER.info(">>>> Envoi de l'événement Ajout Véhicule: {}", evenement);

        kafkaTemplate.send(topic, evenement);
    }
}

package rahile.abdelmounim.garage.evenement.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.kafka.autoconfigure.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ContainerProperties;
import rahile.abdelmounim.garage.evenement.dto.VehiculeAjoutEvenement;

import java.util.Map;


@Configuration
@EnableKafka
public class KafkaConfig {


    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConfig.class);
    private final KafkaProperties kafkaProperties;
    private final String VEHICULE_TOPIC_PRINCIPAL = "rahile.abdelmounim.garage.EVT_GARAGE_MAIN";


    public KafkaConfig(KafkaProperties kafkaProperties) {
        this.kafkaProperties = kafkaProperties;
    }

    @Bean
    public ConsumerFactory<String, VehiculeAjoutEvenement> consumerFactory() {
        Map<String, Object> props = kafkaProperties.buildConsumerProperties();
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, VehiculeAjoutEvenement> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, VehiculeAjoutEvenement> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        return factory;
    }

    @Bean
    public ProducerFactory<String, VehiculeAjoutEvenement> producerFactory() {
        return new DefaultKafkaProducerFactory<>(kafkaProperties.buildProducerProperties());
    }

    @Bean
    public KafkaTemplate<String, VehiculeAjoutEvenement> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }


    @Bean
    public NewTopic vehiculeTopic() {
        return new NewTopic(
                VEHICULE_TOPIC_PRINCIPAL, 3, (short) 1
        );
    }


}

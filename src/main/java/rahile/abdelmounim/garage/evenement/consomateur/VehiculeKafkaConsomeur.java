package rahile.abdelmounim.garage.evenement.consomateur;

import org.springframework.kafka.support.Acknowledgment;
import rahile.abdelmounim.garage.evenement.dto.VehiculeAjoutEvenement;


public interface VehiculeKafkaConsomeur{


    void ecouterVehiculeEvenementPrincipal(VehiculeAjoutEvenement vehiculeAjoutEvenement,
                                                  String topic, String groupId,
                                                  Acknowledgment acknowledgment);
}

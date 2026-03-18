package rahile.abdelmounim.garage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rahile.abdelmounim.garage.domaine.EntiteAuditeAbstraite;
import rahile.abdelmounim.garage.domaine.ModeleVehicule;
import rahile.abdelmounim.garage.domaine.Vehicule;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehiculeRepository extends JpaRepository<Vehicule, Long> {

    List<Vehicule> findByGarageIdAndEtat(Long garageId, EntiteAuditeAbstraite.EtatEntiteEnum etat);

    List<Vehicule> findByModeleAndEtat(ModeleVehicule modele, EntiteAuditeAbstraite.EtatEntiteEnum etat);

    Optional<Vehicule> findByIdAndEtat(Long id, EntiteAuditeAbstraite.EtatEntiteEnum etat);

}

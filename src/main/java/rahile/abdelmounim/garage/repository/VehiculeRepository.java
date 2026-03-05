package rahile.abdelmounim.garage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rahile.abdelmounim.garage.domaine.EntiteAuditAbstraite;
import rahile.abdelmounim.garage.domaine.ModeleVehicule;
import rahile.abdelmounim.garage.domaine.Vehicule;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehiculeRepository extends JpaRepository<Vehicule, Long> {

    List<Vehicule> findByGarageIdAndEtat(Long garageId,  EntiteAuditAbstraite.EtatEntiteEnum etat);

    List<Vehicule> findByModeleAndEtat(ModeleVehicule modele, EntiteAuditAbstraite.EtatEntiteEnum etat);

    Optional<Vehicule> findByIdAndEtat(Long id, EntiteAuditAbstraite.EtatEntiteEnum etat);

}

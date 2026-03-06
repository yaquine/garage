package rahile.abdelmounim.garage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rahile.abdelmounim.garage.domaine.Accessoire;
import rahile.abdelmounim.garage.domaine.EntiteAuditAbstraite;


import java.util.List;
import java.util.Optional;

public interface AccessoireRepository extends JpaRepository<Accessoire, Long> {

    List<Accessoire> findByVehiculeIdAndEtat(Long vehiculeId, EntiteAuditAbstraite.EtatEntiteEnum etat);

    Optional<Accessoire> findByIdAndEtat(Long id, EntiteAuditAbstraite.EtatEntiteEnum etat);

}

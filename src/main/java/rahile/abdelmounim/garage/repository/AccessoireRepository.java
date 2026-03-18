package rahile.abdelmounim.garage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rahile.abdelmounim.garage.domaine.Accessoire;
import rahile.abdelmounim.garage.domaine.EntiteAuditeAbstraite;


import java.util.List;
import java.util.Optional;

public interface AccessoireRepository extends JpaRepository<Accessoire, Long> {

    List<Accessoire> findByVehiculeIdAndEtat(Long vehiculeId, EntiteAuditeAbstraite.EtatEntiteEnum etat);

    Optional<Accessoire> findByIdAndEtat(Long id, EntiteAuditeAbstraite.EtatEntiteEnum etat);

}

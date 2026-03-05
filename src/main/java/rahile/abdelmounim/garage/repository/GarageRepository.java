package rahile.abdelmounim.garage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import rahile.abdelmounim.garage.domaine.EntiteAuditAbstraite;
import rahile.abdelmounim.garage.domaine.Garage;

import java.util.Optional;

@Repository
public interface GarageRepository extends JpaRepository<Garage, Long>, JpaSpecificationExecutor<Garage> {

    Optional<Garage> findByIdAndEtat(Long id, EntiteAuditAbstraite.EtatEntiteEnum etat);
}

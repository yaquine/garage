package rahile.abdelmounim.garage.repository.specification;


import jakarta.annotation.Nullable;
import org.springframework.data.jpa.domain.Specification;
import rahile.abdelmounim.garage.domaine.Accessoire;
import rahile.abdelmounim.garage.domaine.EntiteAuditAbstraite;
import rahile.abdelmounim.garage.domaine.Garage;
import rahile.abdelmounim.garage.domaine.Vehicule;
import rahile.abdelmounim.garage.web.requete.GarageReadRequete;

import jakarta.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class GarageSpecification implements Specification<Garage> {

    private final transient GarageReadRequete garageReadRequete;

    public GarageSpecification(GarageReadRequete garageReadRequete) {
        this.garageReadRequete = garageReadRequete;
    }

    @Override
    public @Nullable Predicate toPredicate(
            Root<Garage> root,
            CriteriaQuery<?> query,
            CriteriaBuilder cb
    ) {

        List<Predicate> predicates = new ArrayList<>();

        if (garageReadRequete.nom() != null && !garageReadRequete.nom().isBlank()) {
            predicates.add(
                    cb.like(
                            cb.lower(root.get("nom")),
                            "%" + garageReadRequete.nom().trim().toLowerCase() + "%"
                    )
            );
        }

        if (garageReadRequete.addresse() != null  && !garageReadRequete.addresse().isBlank()) {
            predicates.add(
                    cb.like(
                            cb.lower(root.get("addresse")),
                            "%" + garageReadRequete.addresse().trim().toLowerCase() + "%"
                    )
            );
        }

        Join<Garage, Vehicule> vehiculeJoin = null;

        if (garageReadRequete.typeVehicule() != null) {

            vehiculeJoin = root.join("vehicules", JoinType.INNER);

            predicates.add(
                    cb.equal(
                            vehiculeJoin.get("modele"),
                            garageReadRequete.typeVehicule().toUpperCase()
                    )
            );
        }

        if (garageReadRequete.typeAccessoire() != null) {

            if (vehiculeJoin == null) {
                vehiculeJoin = root.join("vehicules", JoinType.INNER);
            }

            Join<Vehicule, Accessoire> accessoireJoin =
                    vehiculeJoin.join("accessoires", JoinType.INNER);

            predicates.add(
                    cb.equal(
                            cb.upper(accessoireJoin.get("type")),
                            garageReadRequete.typeAccessoire().toUpperCase()
                    )
            );
        }


        predicates.add(
                cb.equal(
                        root.get("etat"),
                        EntiteAuditAbstraite.EtatEntiteEnum.ACTIVE.getValue()
                )
        );

        return cb.and(predicates.toArray(new Predicate[0]));
    }
}

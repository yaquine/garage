package rahile.abdelmounim.garage.repository.specification;


import jakarta.annotation.Nullable;
import org.springframework.data.jpa.domain.Specification;
import rahile.abdelmounim.garage.domaine.Accessoire;
import rahile.abdelmounim.garage.domaine.EntiteAuditeAbstraite;
import rahile.abdelmounim.garage.domaine.Garage;
import rahile.abdelmounim.garage.domaine.Vehicule;
import rahile.abdelmounim.garage.web.requete.GarageLectureRequete;
import jakarta.persistence.criteria.*;

import java.util.ArrayList;
import java.util.List;

public class GarageSpecification implements Specification<Garage> {

    private final transient GarageLectureRequete garageLectureRequete;

    public GarageSpecification(GarageLectureRequete garageLectureRequete) {
        this.garageLectureRequete = garageLectureRequete;
    }

    @Override
    public @Nullable Predicate toPredicate(Root<Garage> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<>();

        if (garageLectureRequete.nom() != null && !garageLectureRequete.nom().isBlank()) {

            predicates.add(
                    cb.like(
                            cb.lower(root.get("nom")),
                            "%" + garageLectureRequete.nom().trim().toLowerCase() + "%"
                    )
            );
        }

        if (garageLectureRequete.addresse() != null && !garageLectureRequete.addresse().isBlank()) {

            predicates.add(
                    cb.like(
                            cb.lower(root.get("addresse")),
                            "%" + garageLectureRequete.addresse().trim().toLowerCase() + "%"
                    )
            );
        }

        Join<Garage, Vehicule> vehiculeJoin = null;

        if (garageLectureRequete.typeVehicule() != null) {

            vehiculeJoin = root.join("vehicules", JoinType.INNER);

            predicates.add(
                    cb.equal(
                            vehiculeJoin.get("modele"),
                            garageLectureRequete.typeVehicule().toUpperCase()
                    )
            );
        }

        if (garageLectureRequete.typeAccessoire() != null) {

            if (vehiculeJoin == null) {

                vehiculeJoin = root.join("vehicules", JoinType.INNER);

            }

            Join<Vehicule, Accessoire> accessoireJoin =
                    vehiculeJoin.join("accessoires", JoinType.INNER);

            predicates.add(
                    cb.equal(
                            cb.upper(accessoireJoin.get("type")),
                            garageLectureRequete.typeAccessoire().toUpperCase()
                    )
            );
        }


        predicates.add(
                cb.equal(
                        root.get("etat"),
                        EntiteAuditeAbstraite.EtatEntiteEnum.ACTIVE.getValue()
                )
        );

        return cb.and(predicates.toArray(new Predicate[0]));
    }
}

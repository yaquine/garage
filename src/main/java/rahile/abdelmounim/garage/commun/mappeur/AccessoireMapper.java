package rahile.abdelmounim.garage.commun.mappeur;

import rahile.abdelmounim.garage.domaine.Accessoire;
import rahile.abdelmounim.garage.service.dto.AccessoireEntreDto;
import rahile.abdelmounim.garage.service.dto.AccessoireSortieDto;
import rahile.abdelmounim.garage.web.reponse.AccessoireReponse;
import rahile.abdelmounim.garage.web.requete.AccessoireRequete;

public interface AccessoireMapper {

    static AccessoireEntreDto transformerEntreDto(
            AccessoireRequete requete
    ) {

        if (requete == null) {
            return null;
        }

        return new AccessoireEntreDto(
                requete.nom(),
                requete.description(),
                requete.prix(),
                requete.type(),
                requete.code()
        );
    }

    static Accessoire transformerEntite(
            AccessoireEntreDto dto
    ) {

        if (dto == null) {
            return null;
        }

        Accessoire accessoire = new Accessoire();

        accessoire.setNom(dto.nom());
        accessoire.setDescription(dto.description());
        accessoire.setPrix(dto.prix());
        accessoire.setType(dto.type());
        accessoire.setCode(dto.code());

        return accessoire;
    }

    static AccessoireSortieDto transformerSortieDto(
            Accessoire accessoire
    ) {

        if (accessoire == null) {
            return null;
        }

        return new AccessoireSortieDto(
                accessoire.getId(),
                accessoire.getNom(),
                accessoire.getDescription(),
                accessoire.getPrix(),
                accessoire.getType(),
                accessoire.getCode(),
                accessoire.getVehicule() != null
                        ? accessoire.getVehicule().getId()
                        : null
        );
    }

    static AccessoireReponse transformerReponse(
            AccessoireSortieDto dto
    ) {

        if (dto == null) {
            return null;
        }

        return new AccessoireReponse(
                dto.id(),
                dto.nom(),
                dto.description(),
                dto.prix(),
                dto.type(),
                dto.code(),
                dto.vehiculeId()
        );
    }
}

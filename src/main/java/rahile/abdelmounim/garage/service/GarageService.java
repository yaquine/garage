package rahile.abdelmounim.garage.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rahile.abdelmounim.garage.service.dto.GarageEntreDto;
import rahile.abdelmounim.garage.service.dto.GarageSortieDto;
import rahile.abdelmounim.garage.web.requete.GarageLectureRequete;

public interface GarageService {

    GarageSortieDto ajouterGarage(GarageEntreDto garageEntreDto);

    GarageSortieDto modifierGarage(Long id, GarageEntreDto garageEntreDto);

    void desactiverGarage(Long id);

    GarageSortieDto trouverGarageParId(Long id);

    Page<GarageSortieDto> chercherGarages(
            GarageLectureRequete garageLectureRequete,
            Pageable pageable
    );
}

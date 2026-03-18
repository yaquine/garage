package rahile.abdelmounim.garage.service;

import rahile.abdelmounim.garage.service.dto.AccessoireEntreDto;
import rahile.abdelmounim.garage.service.dto.AccessoireSortieDto;

import java.util.List;

public interface AccessoireService {

    AccessoireSortieDto ajouterAccessoire(Long vehiculeId, AccessoireEntreDto dto);

    AccessoireSortieDto modifierAccessoire(Long accessoireId, AccessoireEntreDto dto);

    void desactiverAccessoire(Long accessoireId);

    List<AccessoireSortieDto> listerAccessoiresVehicule(Long vehiculeId);
}

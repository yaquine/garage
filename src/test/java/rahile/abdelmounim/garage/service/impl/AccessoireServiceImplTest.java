package rahile.abdelmounim.garage.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

import org.mockito.junit.jupiter.MockitoExtension;
import rahile.abdelmounim.garage.commun.erreur.AccessoireInexistantException;
import rahile.abdelmounim.garage.commun.erreur.VehiculeInexistantException;
import rahile.abdelmounim.garage.domaine.Accessoire;
import rahile.abdelmounim.garage.domaine.EntiteAuditAbstraite;
import rahile.abdelmounim.garage.domaine.Vehicule;
import rahile.abdelmounim.garage.repository.AccessoireRepository;
import rahile.abdelmounim.garage.repository.VehiculeRepository;
import rahile.abdelmounim.garage.service.dto.AccessoireEntreDto;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AccessoireServiceImplTest {

    @Mock
    private AccessoireRepository accessoireRepository;

    @Mock
    private VehiculeRepository vehiculeRepository;

    @InjectMocks
    private AccessoireServiceImpl accessoireService;

    private Vehicule vehicule;

    @BeforeEach
    void setUp() {
        vehicule = new Vehicule();
        vehicule.setId(1L);
    }

    @Test
    void ajouterAccessoire_succes() {

        AccessoireEntreDto dto = new AccessoireEntreDto("Nom", "Desc", new BigDecimal("100.0"), "Type", "Code");

        when(vehiculeRepository.findByIdAndEtat(1L, EntiteAuditAbstraite.EtatEntiteEnum.ACTIVE)).thenReturn(Optional.of(vehicule));

        when(accessoireRepository.save(any(Accessoire.class))).thenAnswer(i -> i.getArgument(0));

        var result = accessoireService.ajouterAccessoire(1L, dto);

        assertThat(result.nom()).isEqualTo(dto.nom());

        verify(accessoireRepository, times(1)).save(any());
    }

    @Test
    void ajouterAccessoire_vehicule_non_trouve_erreur() {

        when(vehiculeRepository.findByIdAndEtat(1L, EntiteAuditAbstraite.EtatEntiteEnum.ACTIVE)).thenReturn(Optional.empty());

        AccessoireEntreDto dto = new AccessoireEntreDto("Nom", "Desc", new BigDecimal("100.0"),
                "Type", "Code");

        assertThrows(VehiculeInexistantException.class,
                () -> accessoireService.ajouterAccessoire(1L, dto));
    }

    @Test
    void modifierAccessoire_accessoire_non_trouve_erreur() {

        when(accessoireRepository.findByIdAndEtat(1L, EntiteAuditAbstraite.EtatEntiteEnum.ACTIVE))
                .thenReturn(Optional.empty());

        AccessoireEntreDto dto = new AccessoireEntreDto("Nom", "Desc", new BigDecimal("100.0"),
                "Type", "Code");

        assertThrows(AccessoireInexistantException.class,
                () -> accessoireService.modifierAccessoire(1L, dto));
    }

}
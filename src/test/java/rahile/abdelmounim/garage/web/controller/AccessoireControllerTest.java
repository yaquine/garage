package rahile.abdelmounim.garage.web.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import rahile.abdelmounim.garage.domaine.*;
import rahile.abdelmounim.garage.repository.AccessoireRepository;
import rahile.abdelmounim.garage.repository.GarageRepository;
import rahile.abdelmounim.garage.repository.VehiculeRepository;
import rahile.abdelmounim.garage.service.dto.AccessoireEntreDto;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.Year;


@SpringBootTest
@AutoConfigureMockMvc
class AccessoireControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GarageRepository garageRepository;

    @Autowired
    private VehiculeRepository vehiculeRepository;

    @Autowired
    private AccessoireRepository accessoireRepository;

    private static ObjectMapper objectMapper;

    @BeforeAll()
    static void setup() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void ajouterAccessoire_Cree() throws Exception {

        Garage garage = new Garage();
        garage.setNom("Garage Test");
        garage.setEmail("garage@test.com");
        garage.setTelephone("0123456789");
        garage.setAddresse("123 Test Street");
        garage.setEtat(EntiteAuditeAbstraite.EtatEntiteEnum.ACTIVE);
        garage = garageRepository.save(garage);

        Vehicule vehicule = new Vehicule();
        vehicule.setGarage(garage);
        vehicule.setMatricule("ABC-123");
        vehicule.setModele(ModeleVehicule.PEUGEOT);
        vehicule.setTypeCarburant(CarburantEnum.ESSENCE);
        vehicule.setEtat(EntiteAuditeAbstraite.EtatEntiteEnum.ACTIVE);
        vehicule.setAnneeFabrication(Year.of(2022));
        vehicule = vehiculeRepository.save(vehicule);


        AccessoireEntreDto accessoireDto = new AccessoireEntreDto(
                "Accessoire Test",
                "Description test",
                BigDecimal.valueOf(100.00),
                "Type test",
                "ACC-001"
        );


        mockMvc.perform(post("/api/v1/accessoires/vehicules/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accessoireDto)))
                .andExpect(status().isCreated());


        Accessoire accessoirePersisted = accessoireRepository.findByIdAndEtat(1L, EntiteAuditeAbstraite.EtatEntiteEnum.ACTIVE)
                .orElseThrow(() -> new AssertionError("Accessoire not created!"));

        assertEquals("Accessoire Test", accessoirePersisted.getNom());

        assertEquals(vehicule.getId(), accessoirePersisted.getVehicule().getId());

        assertEquals(0, accessoirePersisted.getPrix().compareTo(BigDecimal.valueOf(100)), "Prix should be 100");
    }

}
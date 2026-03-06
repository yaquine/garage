package rahile.abdelmounim.garage.domaine;

import jakarta.persistence.*;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Entity
@Table(name = "horaire_ouverture")
public class HoraireOuverture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private DayOfWeek jour;

    @Column(name = "temps_debut", nullable = false)
    private LocalTime tempsDebut;

    @Column(name = "temps_fin", nullable = false)
    private LocalTime tempsFin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "garage_id", nullable = false)
    private Garage garage;


    public LocalTime getTempsDebut() {
        return tempsDebut;
    }

    public void setTempsDebut(LocalTime tempsDebut) {
        this.tempsDebut = tempsDebut;
    }

    public LocalTime getTempsFin() {
        return tempsFin;
    }

    public void setTempsFin(LocalTime tempsFin) {
        this.tempsFin = tempsFin;
    }

    public DayOfWeek getJour() {
        return jour;
    }

    public void setJour(DayOfWeek jour) {
        this.jour = jour;
    }

    public Long getId() {
        return id;
    }

    public Garage getGarage() {
        return garage;
    }

    public void setGarage(Garage garage) {
        this.garage = garage;
    }
}

package rahile.abdelmounim.garage.domaine;


import jakarta.persistence.*;
import org.hibernate.annotations.SQLDelete;
import rahile.abdelmounim.garage.commun.erreur.VehiculeCapacitieSurpasseException;

import java.time.Year;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "vehicule")
@SQLDelete(sql = "UPDATE vehicule SET etat = 'INACTIVE' WHERE id = ?")
public class Vehicule extends EntiteAuditAbstraite{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "modele", nullable = false, length = 100)
    @Enumerated(EnumType.STRING)
    private ModeleVehicule modele;

    @Column(name = "annee_fabrication", nullable = false, length = 100)
    private Year anneeFabrication;

    @Column(name = "type_carburant", nullable = false, length = 180)
    @Enumerated(EnumType.STRING)
    private CarburantEnum typeCarburant;

    @Column(name = "matricule", nullable = false, unique = true, length = 180)
    private String matricule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "garage_id", nullable = false)
    private Garage garage;

    @OneToMany(mappedBy = "vehicule", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Accessoire> accessoires = new HashSet<>();

    public Vehicule() {
    }

    public Vehicule(ModeleVehicule modele, Year anneeFabrication, CarburantEnum typeCarburant, String matricule) {
        this.modele = modele;
        this.anneeFabrication = anneeFabrication;
        this.typeCarburant = typeCarburant;
        this.matricule = matricule;
    }

    public Long getId() {
        return id;
    }

    public ModeleVehicule getModele() {
        return modele;
    }

    public void setModele(ModeleVehicule modele) {
        this.modele = modele;
    }

    public Year getAnneeFabrication() {
        return anneeFabrication;
    }

    public void setAnneeFabrication(Year anneeFabrication) {
        this.anneeFabrication = anneeFabrication;
    }

    public CarburantEnum getTypeCarburant() {
        return typeCarburant;
    }

    public void setTypeCarburant(CarburantEnum typeCarburant) {
        this.typeCarburant = typeCarburant;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public Garage getGarage() {
        return garage;
    }

    public void setGarage(Garage garage) {
        this.garage = garage;
    }

    public void ajouterAccessoire(Accessoire accessoire) {
        accessoire.setVehicule(this);
        accessoires.add(accessoire);
    }

    public void supprimerAccessoire(Accessoire accessoire) {
        accessoires.remove(accessoire);
        accessoire.setVehicule(null);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Vehicule vehicule)) return false;
        return matricule != null && matricule.equals(vehicule.getMatricule());
    }

    @Override
    public int hashCode() {
        return 31 + (matricule != null ? matricule.hashCode() : 0);
    }

    @Override
    public String toString() {
        return "Vehicule{" +
                "modele=" + modele +
                ", anneeFabrication=" + anneeFabrication +
                ", typeCarburant=" + typeCarburant +
                ", matricule='" + matricule + '\'' +
                '}';
    }
}

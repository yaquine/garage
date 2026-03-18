package rahile.abdelmounim.garage.domaine;


import jakarta.persistence.*;
import org.hibernate.annotations.SQLDelete;
import rahile.abdelmounim.garage.commun.erreur.VehiculeCapacitieSurpasseException;

import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "garage")
@SQLDelete(sql = "UPDATE garage SET etat = 'INACTIVE' WHERE id = ?")
public class Garage extends EntiteAuditeAbstraite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nom", nullable = false, length = 150)
    private String nom;

    @Column(name = "addresse", nullable = false, columnDefinition = "TEXT")
    private String addresse;

    @Column(name = "telephone" ,nullable = false, length = 20)
    private String telephone;

    @Column(name = "email", nullable = false, unique = true, length = 180)
    private String email;

    @OneToMany(mappedBy = "garage", cascade = CascadeType.ALL, orphanRemoval = true)
    private  Set<HoraireOuverture> horaireOuverturesSet = new HashSet<>();


    @OneToMany(mappedBy = "garage", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Vehicule> vehicules = new HashSet<>();


    public Garage() {
    }

    public Garage(String nom, String addresse, String telephone, String email,
                  Set<HoraireOuverture> horaireOuverturesSet, Set<Vehicule> vehicules) {
        this.nom = nom;
        this.addresse = addresse;
        this.telephone = telephone;
        this.email = email;
        this.horaireOuverturesSet = horaireOuverturesSet;
        this.vehicules = vehicules;
    }

    public Long getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAddresse() {
        return addresse;
    }

    public void setAddresse(String addresse) {
        this.addresse = addresse;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<HoraireOuverture> getHoraireOuverturesSet() {
        return horaireOuverturesSet;
    }

    public void setHoraireOuverturesSet(Set<HoraireOuverture> horaireOuverturesSet) {
        this.horaireOuverturesSet = horaireOuverturesSet;
    }

    public Set<Vehicule> getVehicules() {
        return vehicules;
    }

    public void setVehicules(Set<Vehicule> vehicules) {
        this.vehicules = vehicules;
    }

    public void ajouterVehicule(Vehicule vehicule) {
        if (vehicules.size() >= 50) {
            throw new VehiculeCapacitieSurpasseException(id);
        }
        vehicule.setGarage(this);
        vehicules.add(vehicule);
    }

    public void desactiverVehicule(Vehicule vehicule) {
        vehicules.remove(vehicule);
        vehicule.setGarage(null);
    }

    public void ajouterHoraireOuverture(HoraireOuverture horaireOuverture) {
        horaireOuverture.setGarage(this);
        horaireOuverturesSet.add(horaireOuverture);
    }

    public void supprimerHoraireOuverture(HoraireOuverture horaireOuverture) {
        horaireOuverturesSet.remove(horaireOuverture);
        horaireOuverture.setGarage(null);
    }

    @Override
    public String toString() {
        return "Garage{" +
                "nom='" + nom + '\'' +
                '}';
    }
}

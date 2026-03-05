package rahile.abdelmounim.garage.domaine;


import jakarta.persistence.*;
import org.hibernate.annotations.SQLDelete;


import java.math.BigDecimal;



@Entity
@Table(name = "accessoire")
@SQLDelete(sql = "UPDATE accessoire SET etat = 'INACTIVE' WHERE id = ?")
public class Accessoire extends EntiteAuditAbstraite{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nom", nullable = false, length = 150)
    private String nom;

    @Column(name = "description", nullable = false, length = 300)
    private String description;

    @Column(name = "prix", nullable = false)
    private BigDecimal prix;

    @Column(name = "type", nullable = false, length = 150)
    private String type;


    @Column(name = "code", nullable = false, unique = true, length = 30)
    private String code;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicule_id", nullable = false)
    private Vehicule vehicule;

    public Accessoire() {
    }

    public Accessoire(String nom, String description, BigDecimal prix, String type, String code, Vehicule vehicule) {
        this.nom = nom;
        this.description = description;
        this.prix = prix;
        this.type = type;
        this.code = code;
        this.vehicule = vehicule;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrix() {
        return prix;
    }

    public void setPrix(BigDecimal prix) {
        this.prix = prix;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Vehicule getVehicule() {
        return vehicule;
    }

    public void setVehicule(Vehicule vehicule) {
        this.vehicule = vehicule;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Vehicule vehicule)) return false;
        return code != null && code.equals(vehicule.getMatricule());
    }

    @Override
    public int hashCode() {
        return 31 + (code != null ? code.hashCode() : 0);
    }

    @Override
    public String toString() {
        return "Accessoire{" +
                "nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", prix=" + prix +
                ", type='" + type + '\'' +
                '}';
    }
}

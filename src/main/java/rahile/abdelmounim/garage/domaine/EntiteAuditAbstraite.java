package rahile.abdelmounim.garage.domaine;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.OffsetDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class EntiteAuditAbstraite implements Serializable {
    
    @CreatedDate
    @Column(name = "date_creation", updatable = false)
    protected OffsetDateTime dateCreation;
    
    @LastModifiedDate
    @Column(name = "date_modification")
    protected OffsetDateTime dateModification;
    
    @CreatedBy
    @Column(name = "cree_par", updatable = false)
    private String creePar;
    
    @LastModifiedBy
    @Column(name = "modifier_par")
    private String modifierPar;
    
    @Column(name = "etat", nullable = false)
    @Enumerated(EnumType.STRING)
    protected EtatEntiteEnum etat = EtatEntiteEnum.ACTIVE;
    
    protected EntiteAuditAbstraite() {
        this.dateCreation = OffsetDateTime.now();
        this.dateModification = OffsetDateTime.now();
        this.etat = EtatEntiteEnum.ACTIVE;
    }

    public static enum EtatEntiteEnum {

        ACTIVE("ACTIVE"), INACTIVE("INACTIVE");


        private final String value;

        EtatEntiteEnum(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
    
    public OffsetDateTime getDateCreation() {
        return dateCreation;
    }
    
    public OffsetDateTime getDateModification() {
        return dateModification;
    }
    
    public String getCreePar() {
        return creePar;
    }
    
    public String getModifierPar() {
        return modifierPar;
    }
    
    public EtatEntiteEnum getEtat() {
        return etat;
    }
    
    public void setDateCreation(OffsetDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }
    
    public void setDateModification(OffsetDateTime dateModification) {
        this.dateModification = dateModification;
    }
    
    public void setCreePar(String creePar) {
        this.creePar = creePar;
    }
    
    public void setModifierPar(String modifierPar) {
        this.modifierPar = modifierPar;
    }
    
    public void setEtat(EtatEntiteEnum etat) {
        this.etat = etat;
    }
}

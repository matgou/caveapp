package info.kapable.caveapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Vin.
 */
@Entity
@Table(name = "vin")
public class Vin implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 3, max = 255)
    @Column(name = "appellation", length = 255, nullable = false)
    private String appellation;

    @Column(name = "cuvee")
    private String cuvee;

    @Column(name = "cepage")
    private String cepage;

    @Column(name = "region")
    private String region;

    @Column(name = "domaine")
    private String domaine;

    @Column(name = "temperature")
    private String temperature;

    @Column(name = "taux_alcool")
    private String tauxAlcool;

    @Column(name = "code_bare")
    private String codeBare;

    @Column(name = "annee_debut")
    private Integer anneeDebut;

    @Column(name = "annee_fin")
    private Integer anneeFin;

    @Size(min = 0)
    @Column(name = "description")
    private String description;

    @Lob
    @Column(name = "photo_etiquette")
    private byte[] photoEtiquette;

    @Column(name = "photo_etiquette_content_type")
    private String photoEtiquetteContentType;

    @ManyToOne
    private TypeVin typeVin;

    @ManyToOne
    private Millesime millesime;

    @OneToMany(mappedBy = "vin")
    @JsonIgnore
    private Set<Stock> stocks = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "vin_met",
               joinColumns = @JoinColumn(name="vins_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="mets_id", referencedColumnName="id"))
    private Set<Met> mets = new HashSet<>();

    @ManyToOne
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAppellation() {
        return appellation;
    }

    public Vin appellation(String appellation) {
        this.appellation = appellation;
        return this;
    }

    public void setAppellation(String appellation) {
        this.appellation = appellation;
    }

    public String getCuvee() {
        return cuvee;
    }

    public Vin cuvee(String cuvee) {
        this.cuvee = cuvee;
        return this;
    }

    public void setCuvee(String cuvee) {
        this.cuvee = cuvee;
    }

    public String getCepage() {
        return cepage;
    }

    public Vin cepage(String cepage) {
        this.cepage = cepage;
        return this;
    }

    public void setCepage(String cepage) {
        this.cepage = cepage;
    }

    public String getRegion() {
        return region;
    }

    public Vin region(String region) {
        this.region = region;
        return this;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getDomaine() {
        return domaine;
    }

    public Vin domaine(String domaine) {
        this.domaine = domaine;
        return this;
    }

    public void setDomaine(String domaine) {
        this.domaine = domaine;
    }

    public String getTemperature() {
        return temperature;
    }

    public Vin temperature(String temperature) {
        this.temperature = temperature;
        return this;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getTauxAlcool() {
        return tauxAlcool;
    }

    public Vin tauxAlcool(String tauxAlcool) {
        this.tauxAlcool = tauxAlcool;
        return this;
    }

    public void setTauxAlcool(String tauxAlcool) {
        this.tauxAlcool = tauxAlcool;
    }

    public String getCodeBare() {
        return codeBare;
    }

    public Vin codeBare(String codeBare) {
        this.codeBare = codeBare;
        return this;
    }

    public void setCodeBare(String codeBare) {
        this.codeBare = codeBare;
    }

    public Integer getAnneeDebut() {
        return anneeDebut;
    }

    public Vin anneeDebut(Integer anneeDebut) {
        this.anneeDebut = anneeDebut;
        return this;
    }

    public void setAnneeDebut(Integer anneeDebut) {
        this.anneeDebut = anneeDebut;
    }

    public Integer getAnneeFin() {
        return anneeFin;
    }

    public Vin anneeFin(Integer anneeFin) {
        this.anneeFin = anneeFin;
        return this;
    }

    public void setAnneeFin(Integer anneeFin) {
        this.anneeFin = anneeFin;
    }

    public String getDescription() {
        return description;
    }

    public Vin description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getPhotoEtiquette() {
        return photoEtiquette;
    }

    public Vin photoEtiquette(byte[] photoEtiquette) {
        this.photoEtiquette = photoEtiquette;
        return this;
    }

    public void setPhotoEtiquette(byte[] photoEtiquette) {
        this.photoEtiquette = photoEtiquette;
    }

    public String getPhotoEtiquetteContentType() {
        return photoEtiquetteContentType;
    }

    public Vin photoEtiquetteContentType(String photoEtiquetteContentType) {
        this.photoEtiquetteContentType = photoEtiquetteContentType;
        return this;
    }

    public void setPhotoEtiquetteContentType(String photoEtiquetteContentType) {
        this.photoEtiquetteContentType = photoEtiquetteContentType;
    }

    public TypeVin getTypeVin() {
        return typeVin;
    }

    public Vin typeVin(TypeVin typeVin) {
        this.typeVin = typeVin;
        return this;
    }

    public void setTypeVin(TypeVin typeVin) {
        this.typeVin = typeVin;
    }

    public Millesime getMillesime() {
        return millesime;
    }

    public Vin millesime(Millesime millesime) {
        this.millesime = millesime;
        return this;
    }

    public void setMillesime(Millesime millesime) {
        this.millesime = millesime;
    }

    public Set<Stock> getStocks() {
        return stocks;
    }

    public Vin stocks(Set<Stock> stocks) {
        this.stocks = stocks;
        return this;
    }

    public Vin addStock(Stock stock) {
        this.stocks.add(stock);
        stock.setVin(this);
        return this;
    }

    public Vin removeStock(Stock stock) {
        this.stocks.remove(stock);
        stock.setVin(null);
        return this;
    }

    public void setStocks(Set<Stock> stocks) {
        this.stocks = stocks;
    }

    public Set<Met> getMets() {
        return mets;
    }

    public Vin mets(Set<Met> mets) {
        this.mets = mets;
        return this;
    }

    public Vin addMet(Met met) {
        this.mets.add(met);
        met.getVins().add(this);
        return this;
    }

    public Vin removeMet(Met met) {
        this.mets.remove(met);
        met.getVins().remove(this);
        return this;
    }

    public void setMets(Set<Met> mets) {
        this.mets = mets;
    }

    public User getUser() {
        return user;
    }

    public Vin user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Vin vin = (Vin) o;
        if (vin.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), vin.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Vin{" +
            "id=" + getId() +
            ", appellation='" + getAppellation() + "'" +
            ", cuvee='" + getCuvee() + "'" +
            ", cepage='" + getCepage() + "'" +
            ", region='" + getRegion() + "'" +
            ", domaine='" + getDomaine() + "'" +
            ", temperature='" + getTemperature() + "'" +
            ", tauxAlcool='" + getTauxAlcool() + "'" +
            ", codeBare='" + getCodeBare() + "'" +
            ", anneeDebut='" + getAnneeDebut() + "'" +
            ", anneeFin='" + getAnneeFin() + "'" +
            ", description='" + getDescription() + "'" +
            ", photoEtiquette='" + getPhotoEtiquette() + "'" +
            ", photoEtiquetteContentType='" + photoEtiquetteContentType + "'" +
            "}";
    }
}

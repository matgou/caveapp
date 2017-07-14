package info.kapable.caveapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Met.
 */
@Entity
@Table(name = "met")
public class Met implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Size(min = 3, max = 255)
    @Column(name = "categorie", length = 255)
    private String categorie;

    @Column(name = "description")
    private String description;

    @ManyToMany(mappedBy = "mets")
    @JsonIgnore
    private Set<Vin> vins = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategorie() {
        return categorie;
    }

    public Met categorie(String categorie) {
        this.categorie = categorie;
        return this;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getDescription() {
        return description;
    }

    public Met description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Vin> getVins() {
        return vins;
    }

    public Met vins(Set<Vin> vins) {
        this.vins = vins;
        return this;
    }

    public Met addVin(Vin vin) {
        this.vins.add(vin);
        vin.getMets().add(this);
        return this;
    }

    public Met removeVin(Vin vin) {
        this.vins.remove(vin);
        vin.getMets().remove(this);
        return this;
    }

    public void setVins(Set<Vin> vins) {
        this.vins = vins;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Met met = (Met) o;
        if (met.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), met.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Met{" +
            "id=" + getId() +
            ", categorie='" + getCategorie() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}

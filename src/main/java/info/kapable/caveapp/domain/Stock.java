package info.kapable.caveapp.domain;


import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Stock.
 */
@Entity
@Table(name = "stock")
public class Stock implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nb_bouteille")
    private Integer nbBouteille;

    @ManyToOne
    private Vin vin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNbBouteille() {
        return nbBouteille;
    }

    public Stock nbBouteille(Integer nbBouteille) {
        this.nbBouteille = nbBouteille;
        return this;
    }

    public void setNbBouteille(Integer nbBouteille) {
        this.nbBouteille = nbBouteille;
    }

    public Vin getVin() {
        return vin;
    }

    public Stock vin(Vin vin) {
        this.vin = vin;
        return this;
    }

    public void setVin(Vin vin) {
        this.vin = vin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Stock stock = (Stock) o;
        if (stock.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), stock.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Stock{" +
            "id=" + getId() +
            ", nbBouteille='" + getNbBouteille() + "'" +
            "}";
    }
}

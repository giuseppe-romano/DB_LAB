package it.unina.dblab.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "TRAINS")
public class Train implements Serializable, JpaEntity<Train> {
    @Id
    @SequenceGenerator(name="train_generator", sequenceName = "TRAINS_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "train_generator")
    @Column(name = "ID", unique = true)
    private Integer id;

    @Column(name = "CATEGORY", nullable = false)
    private String category;

    @Column(name = "CODE")
    private String code;

    @Column(name = "NOMINAL_SPEED")
    private Integer nominalSpeed;

    @Column(name = "CARRIAGES")
    private Integer carriages;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getNominalSpeed() {
        return nominalSpeed;
    }

    public void setNominalSpeed(Integer nominalSpeed) {
        this.nominalSpeed = nominalSpeed;
    }

    public Integer getCarriages() {
        return carriages;
    }

    public void setCarriages(Integer carriages) {
        this.carriages = carriages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Train train = (Train) o;
        return Objects.equals(category, train.category) &&
                Objects.equals(code, train.code);
    }

    @Override
    public int hashCode() {

        return Objects.hash(category, code);
    }

    @Override
    public Train copy() {
        Train newObject = new Train();
        newObject.setId(this.getId());
        newObject.setCode(this.getCode());
        newObject.setCategory(this.getCategory());
        newObject.setCarriages(this.getCarriages());
        newObject.setNominalSpeed(this.getNominalSpeed());

        return newObject;
    }
}

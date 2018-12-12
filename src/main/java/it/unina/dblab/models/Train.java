package it.unina.dblab.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "TRAINS")
public class Train implements Serializable {
    @Id
    @Column(name = "ID", unique = true)
    private int id;

    @Column(name = "CATEGORY", nullable = false)
    private String category;

    @Column(name = "CODE")
    private String code;

    @Column(name = "NOMINAL_SPEED")
    private BigDecimal nominalSpeed;

    @Column(name = "CARRIAGES")
    private Integer carriages;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public BigDecimal getNominalSpeed() {
        return nominalSpeed;
    }

    public void setNominalSpeed(BigDecimal nominalSpeed) {
        this.nominalSpeed = nominalSpeed;
    }

    public Integer getCarriages() {
        return carriages;
    }

    public void setCarriages(Integer carriages) {
        this.carriages = carriages;
    }
}

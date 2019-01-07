package it.unina.dblab.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "STATIONS")
public class Station implements Serializable {
    @Id
    @SequenceGenerator(name="station_generator", sequenceName = "STATIONS_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "station_generator")
    @Column(name = "ID", unique = true)
    private int id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "ADDRESS", nullable = false)
    private String address;

    @Column(name = "TELEPHONE")
    private String telephone;

    @Column(name = "DISABLED_ACCESS")
    private Boolean disabledAccess;

    @Column(name = "NUM_PLATFORMS")
    private Integer numberOfPlatforms;

    @Column(name = "RESTAURANT")
    private Boolean restaurant;

    @Column(name = "TAXI_SERVICE")
    private Boolean taxiService;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Boolean getDisabledAccess() {
        return disabledAccess;
    }

    public void setDisabledAccess(Boolean disabledAccess) {
        this.disabledAccess = disabledAccess;
    }

    public Integer getNumberOfPlatforms() {
        return numberOfPlatforms;
    }

    public void setNumberOfPlatforms(Integer numberOfPlatforms) {
        this.numberOfPlatforms = numberOfPlatforms;
    }

    public Boolean getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Boolean restaurant) {
        this.restaurant = restaurant;
    }

    public Boolean getTaxiService() {
        return taxiService;
    }

    public void setTaxiService(Boolean taxiService) {
        this.taxiService = taxiService;
    }
}

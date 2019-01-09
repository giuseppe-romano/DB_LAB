package it.unina.dblab.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "STATIONS")
public class Station implements Serializable, JpaEntity {
    @Id
    @SequenceGenerator(name="station_generator", sequenceName = "STATIONS_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "station_generator")
    @Column(name = "ID", unique = true)
    private Integer id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "ADDRESS", nullable = false)
    private String address;

    @Column(name = "TELEPHONE")
    private String telephone;

    @Column(name = "DISABLED_ACCESS")
    private boolean disabledAccess;

    @Column(name = "NUM_PLATFORMS")
    private Integer numberOfPlatforms;

    @Column(name = "RESTAURANT")
    private boolean restaurant;

    @Column(name = "TAXI_SERVICE")
    private boolean taxiService;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public boolean getDisabledAccess() {
        return disabledAccess;
    }

    public void setDisabledAccess(boolean disabledAccess) {
        this.disabledAccess = disabledAccess;
    }

    public Integer getNumberOfPlatforms() {
        return numberOfPlatforms;
    }

    public void setNumberOfPlatforms(Integer numberOfPlatforms) {
        this.numberOfPlatforms = numberOfPlatforms;
    }

    public boolean getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(boolean restaurant) {
        this.restaurant = restaurant;
    }

    public boolean getTaxiService() {
        return taxiService;
    }

    public void setTaxiService(boolean taxiService) {
        this.taxiService = taxiService;
    }
}

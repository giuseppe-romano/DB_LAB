package it.unina.dblab.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "STATIONS")
public class Station implements Serializable, JpaEntity<Station> {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Station station = (Station) o;
        return Objects.equals(name, station.name) &&
                Objects.equals(address, station.address);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, address);
    }

    @Override
    public Station copy() {
        Station newObject = new Station();
        newObject.setId(this.getId());
        newObject.setName(this.getName());
        newObject.setAddress(this.getAddress());
        newObject.setTelephone(this.getTelephone());
        newObject.setDisabledAccess(this.getDisabledAccess());
        newObject.setNumberOfPlatforms(this.getNumberOfPlatforms());
        newObject.setRestaurant(this.getRestaurant());
        newObject.setTaxiService(this.getTaxiService());

        return newObject;
    }
}

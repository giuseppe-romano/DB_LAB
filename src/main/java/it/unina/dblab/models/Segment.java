package it.unina.dblab.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "SEGMENTS")
public class Segment implements Serializable, JpaEntity<Segment> {
    @Id
    @SequenceGenerator(name="segment_generator", sequenceName = "SEGMENTS_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "segment_generator")
    @Column(name = "ID", unique = true)
    private Integer id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "DEPARTURE_STATION_ID")
    private Station departureStation;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ARRIVAL_STATION_ID")
    private Station arrivalStation;

    @Column(name = "DISTANCE", nullable = false)
    private Integer distance;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Station getDepartureStation() {
        return departureStation;
    }

    public void setDepartureStation(Station departureStation) {
        this.departureStation = departureStation;
    }

    public Station getArrivalStation() {
        return arrivalStation;
    }

    public void setArrivalStation(Station arrivalStation) {
        this.arrivalStation = arrivalStation;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Segment that = (Segment) o;
        return Objects.equals(departureStation, that.departureStation) &&
                Objects.equals(arrivalStation, that.arrivalStation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(departureStation, arrivalStation);
    }

    @Override
    public Segment copy() {
        Segment newObject = new Segment();
        newObject.setId(this.getId());
        newObject.setDepartureStation(this.getDepartureStation());
        newObject.setArrivalStation(this.getArrivalStation());
        newObject.setDistance(this.getDistance());

        return newObject;
    }
}

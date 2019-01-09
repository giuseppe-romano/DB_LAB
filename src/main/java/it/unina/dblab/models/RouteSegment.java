package it.unina.dblab.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ROUTE_SEGMENTS")
public class RouteSegment implements Serializable, JpaEntity {
    @Id
    @SequenceGenerator(name="route_segment_generator", sequenceName = "ROUTE_SEGMENTS_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "route_segment_generator")
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
}

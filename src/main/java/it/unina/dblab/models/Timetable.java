package it.unina.dblab.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "TIMETABLE")
public class Timetable implements Serializable, JpaEntity<Timetable> {

    @Id
    @SequenceGenerator(name = "timetable_generator", sequenceName = "TIMETABLE_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "timetable_generator")
    @Column(name = "ID", unique = true)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "TRAIN_ID")
    private Train train;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ROUTE_ID")
    private Route route;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "SCHEDULED_DATE", nullable = false)
    private Date scheduledDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DEPARTURE_DATE")
    private Date departureDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ARRIVAL_DATE")
    private Date arrivalDate;

    @Column(name = "DEPARTURE_PLATFORM")
    private Integer departurePlatform;

    @Column(name = "ARRIVAL_PLATFORM")
    private Integer arrivalPlatform;

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public Date getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(Date scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public Integer getDeparturePlatform() {
        return departurePlatform;
    }

    public void setDeparturePlatform(Integer departurePlatform) {
        this.departurePlatform = departurePlatform;
    }

    public Integer getArrivalPlatform() {
        return arrivalPlatform;
    }

    public void setArrivalPlatform(Integer arrivalPlatform) {
        this.arrivalPlatform = arrivalPlatform;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Timetable timetable = (Timetable) o;
        return Objects.equals(train, timetable.train) &&
                Objects.equals(route, timetable.route) &&
                Objects.equals(scheduledDate, timetable.scheduledDate);
    }

    @Override
    public int hashCode() {

        return Objects.hash(train, route, scheduledDate);
    }

    @Override
    public Timetable copy() {
        Timetable newObject = new Timetable();
        newObject.setId(this.getId());
        if (this.getRoute() != null) {
            newObject.setRoute(this.getRoute().copy());
        }
        if (this.getTrain() != null) {
            newObject.setTrain(this.getTrain().copy());
        }
        newObject.setScheduledDate(this.getScheduledDate());
        newObject.setDepartureDate(this.getDepartureDate());
        newObject.setArrivalDate(this.getArrivalDate());
        newObject.setDeparturePlatform(this.getDeparturePlatform());
        newObject.setArrivalPlatform(this.getArrivalPlatform());

        return newObject;
    }
}

package it.unina.dblab.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "ROUTES")
public class Route implements Serializable, JpaEntity<Route> {

    @Id
    @SequenceGenerator(name="route_generator", sequenceName = "ROUTES_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "route_generator")
    @Column(name = "ID", unique = true)
    private Integer id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ROUTE_SEGMENT_ID")
    private RouteSegment segment;

    @Column(name = "STOPS_AT_ARRIVAL")
    private boolean stopsAtArrival;

    @OneToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "NEXT_ID")
    private Route nextRoute;

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public RouteSegment getSegment() {
        return segment;
    }

    public void setSegment(RouteSegment segment) {
        this.segment = segment;
    }

    public boolean getStopsAtArrival() {
        return stopsAtArrival;
    }

    public void setStopsAtArrival(boolean stopsAtArrival) {
        this.stopsAtArrival = stopsAtArrival;
    }

    public Route getNextRoute() {
        return nextRoute;
    }

    public void setNextRoute(Route nextRoute) {
        this.nextRoute = nextRoute;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Route route = (Route) o;
        return Objects.equals(id, route.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }

    @Override
    public Route copy() {
        Route newObject = new Route();
        newObject.setId(this.getId());
        newObject.setNextRoute(this.getNextRoute());
        newObject.setSegment(this.getSegment());
        newObject.setStopsAtArrival(this.getStopsAtArrival());

        return newObject;
    }
}

package it.unina.dblab.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "ROUTES_2_SEGMENTS")
public class Route2RouteSegment implements Serializable, JpaEntity<Route2RouteSegment> {

    @EmbeddedId
    private Route2RouteSegmentId id;

    @Column(name = "PERFORM_STOP")
    private boolean performStop;

    @Column(name = "SEQUENCE_NUMBER")
    private Integer sequence;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ROUTE_ID", insertable = false, updatable = false)
    private Route route;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ROUTE_SEGMENT_ID", insertable = false, updatable = false)
    private RouteSegment segment;

    @Override
    public Route2RouteSegmentId getId() {
        return id;
    }

    public void setId(Route2RouteSegmentId id) {
        this.id = id;
    }

    public boolean isPerformStop() {
        return performStop;
    }

    public void setPerformStop(boolean performStop) {
        this.performStop = performStop;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public RouteSegment getSegment() {
        return segment;
    }

    public void setSegment(RouteSegment segment) {
        this.segment = segment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Route2RouteSegment that = (Route2RouteSegment) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }

    @Override
    public Route2RouteSegment copy() {
        Route2RouteSegment newObject = new Route2RouteSegment();
        newObject.setId(this.getId().copy());
        newObject.setPerformStop(this.isPerformStop());
        newObject.setSequence(this.getSequence());
        newObject.setRoute(this.getRoute());
        newObject.setSegment(this.getSegment().copy());

        return newObject;
    }
}

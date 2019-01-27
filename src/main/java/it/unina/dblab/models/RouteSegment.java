package it.unina.dblab.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "ROUTES_2_SEGMENTS")
public class RouteSegment implements Serializable, JpaEntity<RouteSegment> {

    @EmbeddedId
    private RouteSegmentId id;

    @Column(name = "SEQUENCE_NUMBER")
    private Integer sequence;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ROUTE_ID", insertable = false, updatable = false)
    private Route route;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "SEGMENT_ID", insertable = false, updatable = false)
    private Segment segment;

    @Column(name = "IS_TERMINAL")
    private boolean terminal;

    @Override
    public RouteSegmentId getId() {
        return id;
    }

    public void setId(RouteSegmentId id) {
        this.id = id;
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

    public Segment getSegment() {
        return segment;
    }

    public void setSegment(Segment segment) {
        this.segment = segment;
    }

    public boolean isTerminal() {
        return terminal;
    }

    public void setTerminal(boolean terminal) {
        this.terminal = terminal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RouteSegment that = (RouteSegment) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }

    @Override
    public RouteSegment copy() {
        RouteSegment newObject = new RouteSegment();
        newObject.setId(this.getId().copy());
        newObject.setSequence(this.getSequence());
        newObject.setRoute(this.getRoute());
        newObject.setSegment(this.getSegment().copy());
        newObject.setTerminal(this.isTerminal());

        return newObject;
    }
}

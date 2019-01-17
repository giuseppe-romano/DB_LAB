package it.unina.dblab.models;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class RouteSegmentId implements Serializable {

    @Column(name = "ROUTE_ID")
    private Integer routeId;

    @Column(name = "SEGMENT_ID")
    private Integer segmentId;

    public Integer getRouteId() {
        return routeId;
    }

    public void setRouteId(Integer routeId) {
        this.routeId = routeId;
    }

    public Integer getSegmentId() {
        return segmentId;
    }

    public void setSegmentId(Integer segmentId) {
        this.segmentId = segmentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RouteSegmentId that = (RouteSegmentId) o;
        return Objects.equals(routeId, that.routeId) &&
                Objects.equals(segmentId, that.segmentId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(routeId, segmentId);
    }

    public RouteSegmentId copy() {
        RouteSegmentId newObject = new RouteSegmentId();
        newObject.setRouteId(this.getRouteId());
        newObject.setSegmentId(this.getSegmentId());

        return newObject;
    }
}

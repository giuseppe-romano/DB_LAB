package it.unina.dblab.models;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class Route2RouteSegmentId implements Serializable {

    @Column(name = "ROUTE_ID")
    private Integer routeId;

    @Column(name = "ROUTE_SEGMENT_ID")
    private Integer routeSegmentId;

    public Integer getRouteId() {
        return routeId;
    }

    public void setRouteId(Integer routeId) {
        this.routeId = routeId;
    }

    public Integer getRouteSegmentId() {
        return routeSegmentId;
    }

    public void setRouteSegmentId(Integer routeSegmentId) {
        this.routeSegmentId = routeSegmentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Route2RouteSegmentId that = (Route2RouteSegmentId) o;
        return Objects.equals(routeId, that.routeId) &&
                Objects.equals(routeSegmentId, that.routeSegmentId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(routeId, routeSegmentId);
    }

    public Route2RouteSegmentId copy() {
        Route2RouteSegmentId newObject = new Route2RouteSegmentId();
        newObject.setRouteId(this.getRouteId());
        newObject.setRouteSegmentId(this.getRouteSegmentId());

        return newObject;
    }
}

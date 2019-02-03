package it.unina.dblab.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Entity
@Table(name = "ROUTES")
@NamedStoredProcedureQuery(
        name="CHECK_ROUTE_LINKING",
        procedureName="CHECK_ROUTE_LINKING",
        parameters={
                @StoredProcedureParameter(name="routeId", type=Integer.class, mode=ParameterMode.IN)
        }
)
public class Route implements Serializable, JpaEntity<Route> {

    @Id
    @SequenceGenerator(name="route_generator", sequenceName = "ROUTES_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "route_generator")
    @Column(name = "ID", unique = true)
    private Integer id;

    @OneToMany(fetch = FetchType.EAGER,
            mappedBy = "route",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @OrderBy("sequence ASC")
    private List<RouteSegment> routeSegments;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "ACTIVE")
    private boolean active;

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<RouteSegment> getRouteSegments() {
        return routeSegments;
    }

    public void setRouteSegments(List<RouteSegment> routeSegments) {
        this.routeSegments = routeSegments;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        newObject.setActive(this.isActive());
        newObject.setName(this.getName());

        List<RouteSegment> routeSegments =
        Optional.ofNullable(this.getRouteSegments()).orElse(new ArrayList<>())
                .stream()
                .map(route2RouteSegment -> route2RouteSegment.copy())
                .collect(Collectors.toList());

        newObject.setRouteSegments(routeSegments);

        return newObject;
    }
}

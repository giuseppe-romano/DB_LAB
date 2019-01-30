package it.unina.dblab.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@SqlResultSetMapping(name = "SearchResult", classes = {
        @ConstructorResult(targetClass = SearchResult.class,
                columns = {
                        @ColumnResult(name = "ID", type = Integer.class),
                        @ColumnResult(name = "DEPARTURE_STATION_ID", type = Integer.class),
                        @ColumnResult(name = "ARRIVAL_STATION_ID", type = Integer.class),
                        @ColumnResult(name = "TRAIN_ID", type = Integer.class),
                        @ColumnResult(name = "ROUTE_ID", type = Integer.class),
                        @ColumnResult(name = "DISTANCE", type = Integer.class),
                        @ColumnResult(name = "DEPARTURE_DATE", type = Date.class),
                        @ColumnResult(name = "ARRIVAL_DATE", type = Date.class),
                        @ColumnResult(name = "DEPARTURE_PLATFORM", type = Integer.class),
                        @ColumnResult(name = "ARRIVAL_PLATFORM", type = Integer.class),
                        @ColumnResult(name = "SEQUENCE_NUMBER", type = Integer.class),
                        @ColumnResult(name = "IS_TERMINAL", type = boolean.class),
                        @ColumnResult(name = "PATHS", type = String.class),
                        @ColumnResult(name = "LEVEL", type = Integer.class)
                })
})
@Entity
public class SearchResult implements Serializable {

    @Id
    private Integer id;
    private Integer departureStationId;
    private Integer arrivalStationId;
    private Integer trainId;
    private Integer routeId;
    private Integer distance;
    private Date departureDate;
    private Date arrivalDate;
    private Integer departurePlatform;
    private Integer arrivalPlatform;
    private Integer sequence;
    private boolean terminal;

    private String paths;
    private Integer level;

    public SearchResult(Integer id, Integer departureStationId, Integer arrivalStationId, Integer trainId, Integer routeId, Integer distance, Date departureDate, Date arrivalDate, Integer departurePlatform, Integer arrivalPlatform, Integer sequence, boolean terminal, String paths, Integer level) {
        this.id = id;
        this.departureStationId = departureStationId;
        this.arrivalStationId = arrivalStationId;
        this.trainId = trainId;
        this.routeId = routeId;
        this.distance = distance;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.departurePlatform = departurePlatform;
        this.arrivalPlatform = arrivalPlatform;
        this.sequence = sequence;
        this.terminal = terminal;
        this.paths = paths;
        this.level = level;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDepartureStationId() {
        return departureStationId;
    }

    public void setDepartureStationId(Integer departureStationId) {
        this.departureStationId = departureStationId;
    }

    public Integer getArrivalStationId() {
        return arrivalStationId;
    }

    public void setArrivalStationId(Integer arrivalStationId) {
        this.arrivalStationId = arrivalStationId;
    }

    public Integer getTrainId() {
        return trainId;
    }

    public void setTrainId(Integer trainId) {
        this.trainId = trainId;
    }

    public Integer getRouteId() {
        return routeId;
    }

    public void setRouteId(Integer routeId) {
        this.routeId = routeId;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
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

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public boolean isTerminal() {
        return terminal;
    }

    public void setTerminal(boolean terminal) {
        this.terminal = terminal;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getPaths() {
        return paths;
    }

    public void setPaths(String paths) {
        this.paths = paths;
    }
}

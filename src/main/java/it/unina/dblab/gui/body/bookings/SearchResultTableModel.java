package it.unina.dblab.gui.body.bookings;

import it.unina.dblab.gui.utility.DatabaseUtil;
import it.unina.dblab.models.Route;
import it.unina.dblab.models.SearchResult;
import it.unina.dblab.models.Station;
import it.unina.dblab.models.Train;

import javax.swing.table.DefaultTableModel;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SearchResultTableModel extends DefaultTableModel {
    private List<Train> trains = DatabaseUtil.listEntities(Train.class);
    private List<Station> stations = DatabaseUtil.listEntities(Station.class);

    private final String[] COLUMN_NAMES = {
            "Partenza", "Arrivo", "Durata", "Treno"
    };

    private List<SearchResult> searchList;

    public SearchResultTableModel() {
    }

    public void search(Integer departureStationId, Integer arrivalStationId, Date startDate, Date endDate) {
        try {

            searchList = DatabaseUtil.searchBooking(departureStationId, arrivalStationId, startDate, endDate);

            Map<Integer, List<SearchResult>> studlistGrouped =
                    searchList.stream().collect(Collectors.groupingBy(w -> w.getRouteId()));

            this.fireTableDataChanged();
            this.fireTableRowsUpdated(0, searchList.size());
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public int getRowCount() {
        return (this.searchList != null ? this.searchList.size() : 0);
    }

    @Override
    public int getColumnCount() {
        return COLUMN_NAMES.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return COLUMN_NAMES[columnIndex];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        SearchResult searchResult = this.searchList.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return stations.stream().filter(station -> station.getId().equals(searchResult.getDepartureStationId())).findFirst().get().getName();
            case 1:
                return stations.stream().filter(station -> station.getId().equals(searchResult.getArrivalStationId())).findFirst().get().getName();
            case 2:
                return searchResult.getDepartureDate();
            case 3:
                Train train = trains.stream().filter(tr -> tr.getId().equals(searchResult.getTrainId())).findFirst().get();
                return train.getCategory() + " (" + train.getCode() + ")";
        }
        return null;
    }

    public Route getEntityAt(int rowIndex) {
        return null;//this.routes.get(rowIndex);
    }
}

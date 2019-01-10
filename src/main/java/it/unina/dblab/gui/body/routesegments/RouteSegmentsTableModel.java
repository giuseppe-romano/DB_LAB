package it.unina.dblab.gui.body.routesegments;

import it.unina.dblab.gui.utility.DatabaseUtil;
import it.unina.dblab.models.RouteSegment;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RouteSegmentsTableModel implements TableModel {

    private final String[] COLUMN_NAMES = {
            "#ID", "Statzione di Partenza", "Stazione di Arrivo", "Distanza"
    };

    private List<RouteSegment> routeSegments;

    public RouteSegmentsTableModel() {
        this.reload();
    }

    public void reload() {
        routeSegments = DatabaseUtil.listEntities(RouteSegment.class);
        Collections.sort(routeSegments, Comparator.comparing(e -> e.getDepartureStation().getName()));
    }

    @Override
    public int getRowCount() {
        return this.routeSegments.size();
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
        switch (columnIndex) {
            case 0:
            case 1:
                return String.class;
            case 2:
                return Integer.class;
        }
        return null;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        RouteSegment routeSegment = this.routeSegments.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return routeSegment.getId();
            case 1:
                return routeSegment.getDepartureStation().getName();
            case 2:
                return routeSegment.getArrivalStation().getName();
            case 3:
                return routeSegment.getDistance();
        }
        return null;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

    }

    @Override
    public void addTableModelListener(TableModelListener l) {

    }

    @Override
    public void removeTableModelListener(TableModelListener l) {

    }

    public RouteSegment getEntityAt(int rowIndex) {
        return this.routeSegments.get(rowIndex);
    }
}

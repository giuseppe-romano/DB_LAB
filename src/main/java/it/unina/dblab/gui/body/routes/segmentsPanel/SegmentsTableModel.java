package it.unina.dblab.gui.body.routes.segmentsPanel;

import it.unina.dblab.models.Route2RouteSegment;
import it.unina.dblab.models.RouteSegment;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.util.*;

public class SegmentsTableModel implements TableModel {

    private final String[] COLUMN_NAMES = {
            "Segmento", "Ferma?"
    };

    private java.util.List<Route2RouteSegment> routeSegments;

    public SegmentsTableModel(List<Route2RouteSegment> routeSegments) {
        this.routeSegments = Optional.ofNullable(routeSegments).orElse(new ArrayList<>());
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
            case 1:
                return Boolean.class;
            case 2:
                return Integer.class;
        }
        return String.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Route2RouteSegment routeSegment = this.routeSegments.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return routeSegment.getSegment();
            case 1:
                return routeSegment.isPerformStop();
            case 2:
                return routeSegment.getSequence();
        }
        return null;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if(rowIndex >= routeSegments.size()) {
            return;
        }

        switch (columnIndex) {
            case 0:
                RouteSegment segment = (RouteSegment) aValue;
                routeSegments.get(rowIndex).setSegment(segment);
                if(segment != null && routeSegments.get(rowIndex).getId() != null) {
                    routeSegments.get(rowIndex).getId().setRouteSegmentId(segment.getId());
                }
                break;
            case 1:
                routeSegments.get(rowIndex).setPerformStop((Boolean) aValue);
                break;
            case 2:
                routeSegments.get(rowIndex).setSequence((Integer) aValue);
                break;
        }
    }

    @Override
    public void addTableModelListener(TableModelListener l) {

    }

    @Override
    public void removeTableModelListener(TableModelListener l) {

    }
}

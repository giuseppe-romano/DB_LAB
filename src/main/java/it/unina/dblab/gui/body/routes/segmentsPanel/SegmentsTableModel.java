package it.unina.dblab.gui.body.routes.segmentsPanel;

import it.unina.dblab.models.RouteSegment;
import it.unina.dblab.models.Segment;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.util.*;

public class SegmentsTableModel implements TableModel {

    private final String[] COLUMN_NAMES = {
            "Segmento", "Sequenza"
    };

    private java.util.List<RouteSegment> routeSegments;

    public SegmentsTableModel(List<RouteSegment> routeSegments) {
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
        RouteSegment routeSegment = this.routeSegments.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return routeSegment.getSegment();
            case 1:
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
                Segment segment = (Segment) aValue;
                routeSegments.get(rowIndex).setSegment(segment);
                if(segment != null && routeSegments.get(rowIndex).getId() != null) {
                    routeSegments.get(rowIndex).getId().setSegmentId(segment.getId());
                }
                break;
            case 1:
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

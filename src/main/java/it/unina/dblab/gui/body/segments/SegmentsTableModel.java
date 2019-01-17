package it.unina.dblab.gui.body.segments;

import it.unina.dblab.gui.utility.DatabaseUtil;
import it.unina.dblab.models.Segment;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SegmentsTableModel implements TableModel {

    private final String[] COLUMN_NAMES = {
            "#ID", "Statzione di Partenza", "Stazione di Arrivo", "Distanza"
    };

    private List<Segment> segments;

    public SegmentsTableModel() {
        this.reload();
    }

    public void reload() {
        segments = DatabaseUtil.listEntities(Segment.class);
        Collections.sort(segments, Comparator.comparing(e -> e.getDepartureStation().getName()));
    }

    @Override
    public int getRowCount() {
        return this.segments.size();
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
        Segment segment = this.segments.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return segment.getId();
            case 1:
                return segment.getDepartureStation().getName();
            case 2:
                return segment.getArrivalStation().getName();
            case 3:
                return segment.getDistance();
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

    public Segment getEntityAt(int rowIndex) {
        return this.segments.get(rowIndex);
    }
}

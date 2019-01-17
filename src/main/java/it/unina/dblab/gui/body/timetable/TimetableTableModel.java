package it.unina.dblab.gui.body.timetable;

import it.unina.dblab.gui.utility.DatabaseUtil;
import it.unina.dblab.models.Timetable;

import javax.swing.table.DefaultTableModel;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class TimetableTableModel extends DefaultTableModel {

    private final String[] COLUMN_NAMES = {
            "#ID", "Treno", "Tratta di Percorrenza", "Orario di Partenza", "Bin. Partenza", "Bin. Arrivo"
    };

    private List<Timetable> timetableList;

    public TimetableTableModel() {

        this.reload();
    }

    public void reload() {
        timetableList = DatabaseUtil.listEntities(Timetable.class);
        Collections.sort(timetableList, Comparator.comparing(e -> e.getScheduledDate()));

        this.fireTableDataChanged();
        this.fireTableRowsUpdated(0, timetableList.size());
    }

    @Override
    public int getRowCount() {
        return (this.timetableList != null ? this.timetableList.size() : 0);
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
            case 4:
            case 5:
                return Integer.class;
            case 1:
            case 2:
                return String.class;
            case 3:
                return Date.class;
        }
        return null;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Timetable timetable = this.timetableList.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return timetable.getId();
            case 1:
                return timetable.getTrain().getCategory() + "(" + timetable.getTrain().getCode() + ")";
            case 2:
                return timetable.getRoute().getName();
            case 3:
                return timetable.getScheduledDate();
            case 4:
                return timetable.getDeparturePlatform();
            case 5:
                return timetable.getArrivalPlatform();
        }
        return null;
    }

    public Timetable getEntityAt(int rowIndex) {
        return this.timetableList.get(rowIndex);
    }
}

package it.unina.dblab.gui.body.trains;

import it.unina.dblab.gui.utility.DatabaseUtil;
import it.unina.dblab.models.Train;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TrainsTableModel implements TableModel {

    private final String[] COLUMN_NAMES = {
            "#ID", "Categoria", "Codice", "Velocita' Nominale", "N. Carrozze"
    };

    private List<Train> trains;

    public TrainsTableModel() {
        this.reload();
    }

    public void reload() {
        trains = DatabaseUtil.listEntities(Train.class);
        Collections.sort(trains, Comparator.comparing(e -> e.getId()));
    }

    @Override
    public int getRowCount() {
        return this.trains.size();
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
                return Integer.class;
            case 1:
                return String.class;
            case 2:
                return String.class;
            case 3:
                return Double.class;
            case 4:
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
        Train train = this.trains.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return train.getId();
            case 1:
                return train.getCategory();
            case 2:
                return train.getCode();
            case 3:
                return train.getNominalSpeed();
            case 4:
                return train.getCarriages();
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

    public Train getEntityAt(int rowIndex) {
        return this.trains.get(rowIndex);
    }
}

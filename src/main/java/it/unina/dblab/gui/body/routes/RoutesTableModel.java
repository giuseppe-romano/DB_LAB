package it.unina.dblab.gui.body.routes;

import it.unina.dblab.gui.utility.DatabaseUtil;
import it.unina.dblab.models.Route;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RoutesTableModel implements TableModel {

    private final String[] COLUMN_NAMES = {
            "#ID", "Tratta di Percorrenza"
    };

    private List<Route> routes;

    public RoutesTableModel() {
        this.reload();
    }

    public void reload() {
        routes = DatabaseUtil.listEntities(Route.class);
        Collections.sort(routes, Comparator.comparing(e -> e.getId()));
    }

    @Override
    public int getRowCount() {
        return this.routes.size();
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
        }
        return null;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Route route = this.routes.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return route.getId();
            case 1:
                return route;
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

    public Route getEntityAt(int rowIndex) {
        return this.routes.get(rowIndex);
    }
}

package it.unina.dblab.gui.body.bookings;

import it.unina.dblab.gui.utility.DatabaseUtil;
import it.unina.dblab.models.Route;

import javax.swing.table.DefaultTableModel;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SearchResultTableModel extends DefaultTableModel {

    private final String[] COLUMN_NAMES = {
            "Partenza", "Arrivo", "Durata", "Treno"
    };

    private List<Route> routes;

    public SearchResultTableModel() {

        this.reload();
    }

    public void reload() {
        routes = DatabaseUtil.listEntities(Route.class);
        Collections.sort(routes, Comparator.comparing(e -> e.getId()));

        this.fireTableDataChanged();
        this.fireTableRowsUpdated(0, routes.size());
    }

    @Override
    public int getRowCount() {
        return (this.routes != null ? this.routes.size() : 0);
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
        return columnIndex == 1;
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

    public Route getEntityAt(int rowIndex) {
        return this.routes.get(rowIndex);
    }
}

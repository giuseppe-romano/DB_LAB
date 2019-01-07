package it.unina.dblab.gui.body.stations;

import it.unina.dblab.HeavenRail;
import it.unina.dblab.models.Station;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.util.List;

public class StationsTableModel implements TableModel {

    private final String[] COLUMN_NAMES = {
            "#ID", "Nome", "Indirizzo", "N. Telefono", "N. piattaforme", "Acc. Disabili", "Ristorante", "Servizio Taxi"
    };

    private List<Station> stations;

    public StationsTableModel() {
        this.reload();
    }

    public void reload() {

        EntityManager manager = HeavenRail.entityManagerFactory.createEntityManager();

        EntityTransaction transaction = null;

        try {
            // Get a transaction
            transaction = manager.getTransaction();
            // Begin the transaction
            transaction.begin();

            // Get a List of Trains
            stations = manager.createQuery("SELECT s FROM it.unina.dblab.models.Station s",
                    Station.class).getResultList();

            // Commit the transaction
            transaction.commit();
        } catch (Exception ex) {
            // If there are any exceptions, roll back the changes
            if (transaction != null) {
                transaction.rollback();
            }
            // Print the Exception
            ex.printStackTrace();
        }
    }

    @Override
    public int getRowCount() {
        return this.stations.size();
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
            case 6:
            case 7:
                return Integer.class;
            case 1:
            case 2:
            case 3:
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
        Station station = this.stations.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return station.getId();
            case 1:
                return station.getName();
            case 2:
                return station.getAddress();
            case 3:
                return station.getTelephone();
            case 4:
                return station.getNumberOfPlatforms();
            case 5:
                return station.getDisabledAccess();
            case 6:
                return station.getRestaurant();
            case 7:
                return station.getTaxiService();
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

    public Station getEntityAt(int rowIndex) {
        return this.stations.get(rowIndex);
    }
}

package it.unina.dblab.gui.body.trains;

import it.unina.dblab.HeavenRail;
import it.unina.dblab.models.Train;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.util.List;

public class TrainsTableModel implements TableModel {

    private final String[] COLUMN_NAMES = {
        "#ID", "Categoria", "Acronimo", "Velocita' Media", "Numero di Carrozze"
    };

    private List<Train> trains;

    public TrainsTableModel() {

        EntityManager manager = HeavenRail.entityManagerFactory.createEntityManager();

        EntityTransaction transaction = null;

        try {
            // Get a transaction
            transaction = manager.getTransaction();
            // Begin the transaction
            transaction.begin();

            // Get a List of Trains
            trains = manager.createQuery("SELECT s FROM it.unina.dblab.models.Train s",
                    Train.class).getResultList();

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
        return this.trains.size();
    }

    @Override
    public int getColumnCount() {
        return 5;
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
}

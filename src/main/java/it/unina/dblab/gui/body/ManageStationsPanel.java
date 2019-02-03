package it.unina.dblab.gui.body;

import it.unina.dblab.gui.body.stations.EditStationDialog;
import it.unina.dblab.gui.body.stations.StationsCellRenderer;
import it.unina.dblab.gui.body.stations.StationsTableModel;
import it.unina.dblab.gui.utility.DatabaseUtil;
import it.unina.dblab.models.Station;
import org.hibernate.exception.ConstraintViolationException;

import javax.persistence.RollbackException;
import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Optional;

public class ManageStationsPanel extends JPanel {
    public static final String NAME = "MANAGE_STATIONS";

    private BodyContainer parent;

    private JTable stationsTable;

    public ManageStationsPanel(BodyContainer parent) {
        this.parent = parent;

        this.setBackground(Color.WHITE);
        this.setLayout(new BorderLayout());

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEADING));

        JLabel titleLabel = new JLabel("Gestione delle Stazioni");
        titlePanel.setBackground(Color.WHITE);

        titleLabel.setFont(new Font("Candara", Font.PLAIN, 26));
        titlePanel.add(titleLabel);

        this.add(titlePanel, BorderLayout.PAGE_START);

        this.add(createLeftMenu(), BorderLayout.LINE_START);
        this.add(createTablePanel(), BorderLayout.CENTER);
    }

    private JPanel createLeftMenu() {
        JPanel panel = new LeftMenuPanel();
        panel.setPreferredSize(new Dimension(300, 100));
        panel.setBackground(new Color(211, 212, 222, 255));
        panel.setLayout(new GridLayout(10, 1));

        LeftMenuButton addNew = new LeftMenuButton("Aggiungi Nuova");
        addNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // create a dialog Box
                Station stationModel = new Station();
                stationModel.setName("");
                stationModel.setAddress("");
                stationModel.setTelephone("");

                JDialog dialog = new EditStationDialog(stationModel);
                dialog.addComponentListener(new ComponentAdapter() {
                    @Override
                    public void componentHidden(ComponentEvent e) {
                        super.componentHidden(e);
                        ((StationsTableModel) stationsTable.getModel()).reload();
                        stationsTable.revalidate();
                        stationsTable.repaint();
                    }
                });
                // setsize of dialog
                dialog.pack();
                dialog.setSize(450, 300);
                dialog.setLocationRelativeTo(null);
                dialog.setResizable(false);
                // set visibility of dialog
                dialog.setVisible(true);
            }
        });
        panel.add(addNew);

        LeftMenuButton modify = new LeftMenuButton("Modifica");
        modify.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = stationsTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(parent, "Seleziona un elemento da modificare!");
                } else {
                    Station stationModel = ((StationsTableModel) stationsTable.getModel()).getEntityAt(selectedRow);
                    // create a dialog Box
                    JDialog dialog = new EditStationDialog(stationModel);
                    dialog.addComponentListener(new ComponentAdapter() {
                        @Override
                        public void componentHidden(ComponentEvent e) {
                            super.componentHidden(e);
                            ((StationsTableModel) stationsTable.getModel()).reload();
                            stationsTable.revalidate();
                            stationsTable.repaint();
                        }
                    });
                    // setsize of dialog
                    dialog.pack();
                    dialog.setSize(450, 300);
                    dialog.setLocationRelativeTo(null);
                    dialog.setResizable(false);
                    // set visibility of dialog
                    dialog.setVisible(true);
                }
            }
        });
        panel.add(modify);

        LeftMenuButton delete = new LeftMenuButton("Elimina");
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = stationsTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(parent, "Seleziona un elemento da eliminare!", "Attenzione", JOptionPane.WARNING_MESSAGE);
                }
                else {
                    Station stationModel = ((StationsTableModel) stationsTable.getModel()).getEntityAt(selectedRow);

                    try {
                        DatabaseUtil.removeEntity(stationModel);

                        stationsTable.clearSelection();
                        ((StationsTableModel) stationsTable.getModel()).reload();
                        stationsTable.revalidate();
                        stationsTable.repaint();

                    } catch (RollbackException rex) {
                        String errorMessage = Optional.ofNullable(rex.getCause())
                                .map(cause -> cause.getCause())
                                .filter(deepCause -> deepCause instanceof ConstraintViolationException)
                                .map(deepCause -> ((ConstraintViolationException)deepCause))
                                .map(constraintViolation -> constraintViolation.getSQLException())
                                .filter(sqlException -> sqlException != null)
                                .map(sqlException -> sqlException.getMessage())
                                .orElse("Impossibile effettuare l'operazione");
                        JOptionPane.showMessageDialog(ManageStationsPanel.this, errorMessage, "Violazione del vincolo", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        panel.add(delete);

        return panel;
    }

    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel();
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setPreferredSize(new Dimension(100, 100));

        stationsTable = new JTable(new StationsTableModel());
        stationsTable.setOpaque(false);
        stationsTable.setPreferredScrollableViewportSize(new Dimension(860, 600));
        stationsTable.setFillsViewportHeight(true);
        stationsTable.setRowHeight(35);
        stationsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        TableCellRenderer cellRenderer = new StationsCellRenderer();
        //column ID
        TableColumn col = stationsTable.getColumnModel().getColumn(0);
        col.setCellRenderer(cellRenderer);
        col.setMinWidth(30);
        col.setMaxWidth(30);

        //column Nome
        col = stationsTable.getColumnModel().getColumn(1);
        col.setCellRenderer(cellRenderer);
        col.setMinWidth(150);
        col.setMaxWidth(150);

        //column Indirizzo
        col = stationsTable.getColumnModel().getColumn(2);
        col.setCellRenderer(cellRenderer);
        col.setMinWidth(100);

        //column Telefono
        col = stationsTable.getColumnModel().getColumn(3);
        col.setCellRenderer(cellRenderer);
        col.setMinWidth(110);
        col.setMaxWidth(110);

        //column Numero piattaforme
        col = stationsTable.getColumnModel().getColumn(4);
        col.setCellRenderer(cellRenderer);
        col.setMinWidth(90);
        col.setMaxWidth(90);

        //column Accesso disabili
        col = stationsTable.getColumnModel().getColumn(5);
        col.setCellRenderer(cellRenderer);
        col.setMinWidth(75);
        col.setMaxWidth(75);

        //column Servizio Ristorante
        col = stationsTable.getColumnModel().getColumn(6);
        col.setCellRenderer(cellRenderer);
        col.setMinWidth(85);
        col.setMaxWidth(85);

        //column Servizio Taxi
        col = stationsTable.getColumnModel().getColumn(7);
        col.setCellRenderer(cellRenderer);
        col.setMinWidth(75);
        col.setMaxWidth(75);

        JScrollPane scrollPane = new JScrollPane(stationsTable);
        tablePanel.add(scrollPane);

        return tablePanel;
    }
}

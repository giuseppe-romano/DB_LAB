package it.unina.dblab.gui.body;

import it.unina.dblab.gui.body.routes.EditRouteDialog;
import it.unina.dblab.gui.body.routes.RoutesCellRenderer;
import it.unina.dblab.gui.body.routes.RoutesTableModel;
import it.unina.dblab.gui.utility.DatabaseUtil;
import it.unina.dblab.models.Route;
import org.hibernate.exception.ConstraintViolationException;

import javax.persistence.RollbackException;
import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Optional;

public class ManageTimetablePanel extends JPanel {
    public static final String NAME = "MANAGE_TIMETABLE";

    private BodyContainer parent;

    private JTable routesTable;

    public ManageTimetablePanel(BodyContainer parent) {
        this.parent = parent;

        this.setBackground(Color.WHITE);
        this.setLayout(new BorderLayout());

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEADING));

        JLabel titleLabel = new JLabel("Gestione degli Orari");
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

        LeftMenuButton addNewSegment = new LeftMenuButton("Aggiungi Nuova");
        addNewSegment.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // create a dialog Box
                Route routeModel = new Route();

                JDialog dialog = new EditRouteDialog(routeModel);
                dialog.addComponentListener(new ComponentAdapter() {
                    @Override
                    public void componentHidden(ComponentEvent e) {
                        super.componentHidden(e);
                        ((RoutesTableModel) routesTable.getModel()).reload();
                        routesTable.removeEditor();
                        routesTable.revalidate();
                        routesTable.repaint();
                    }
                });
                // setsize of dialog
                dialog.pack();
                dialog.setSize(550, 500);
                dialog.setResizable(false);
                // set visibility of dialog
                dialog.setVisible(true);
            }
        });
        panel.add(addNewSegment);

        LeftMenuButton modify = new LeftMenuButton("Modifica");
        modify.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = routesTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(parent, "Seleziona un elemento da modificare!");
                } else {
                    Route routeModel = ((RoutesTableModel) routesTable.getModel()).getEntityAt(selectedRow);
                    // create a dialog Box
                    JDialog dialog = new EditRouteDialog(routeModel);
                    dialog.addComponentListener(new ComponentAdapter() {
                        @Override
                        public void componentHidden(ComponentEvent e) {
                            super.componentHidden(e);
                            ((RoutesTableModel) routesTable.getModel()).reload();

                            ((RoutesTableModel) routesTable.getModel()).fireTableRowsUpdated(0, routesTable.getModel().getRowCount());
                            routesTable.removeEditor();
                            routesTable.revalidate();
                            routesTable.repaint();
                        }
                    });
                    // setsize of dialog
                    dialog.pack();
                    dialog.setSize(550, 500);
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
                int selectedRow = routesTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(parent, "Seleziona un elemento da eliminare!", "Attenzione", JOptionPane.WARNING_MESSAGE);
                } else {
                    Route routeModel = ((RoutesTableModel) routesTable.getModel()).getEntityAt(selectedRow);

                    try {
                        DatabaseUtil.removeEntity(routeModel);

                        routesTable.clearSelection();
                        ((RoutesTableModel) routesTable.getModel()).reload();
                        ((RoutesTableModel) routesTable.getModel()).fireTableRowsDeleted(selectedRow, selectedRow);
                        routesTable.removeEditor();
                        routesTable.revalidate();
                        routesTable.repaint();

                    } catch (RollbackException rex) {
                        String errorMessage = Optional.ofNullable(rex.getCause())
                                .map(cause -> cause.getCause())
                                .filter(deepCause -> deepCause instanceof ConstraintViolationException)
                                .map(deepCause -> ((ConstraintViolationException) deepCause))
                                .map(constraintViolation -> constraintViolation.getSQLException())
                                .filter(sqlException -> sqlException != null)
                                .map(sqlException -> sqlException.getMessage())
                                .orElse("Impossibile effettuare l'operazione");
                        JOptionPane.showMessageDialog(ManageTimetablePanel.this, errorMessage, "Violazione del vincolo", JOptionPane.ERROR_MESSAGE);
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

        routesTable = new JTable(new RoutesTableModel());
        routesTable.setOpaque(false);
        routesTable.setPreferredScrollableViewportSize(new Dimension(860, 600));
        routesTable.setFillsViewportHeight(true);
        routesTable.setRowHeight(130);
        routesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


        RoutesCellRenderer cellRenderer = new RoutesCellRenderer();
        //column ID
        TableColumn col = routesTable.getColumnModel().getColumn(0);
        col.setCellRenderer(cellRenderer);
        col.setMinWidth(30);
        col.setMaxWidth(30);

        //column Tratta di percorrenza
        col = routesTable.getColumnModel().getColumn(1);
        col.setCellRenderer(cellRenderer);
        col.setCellEditor(cellRenderer);
        col.setMinWidth(150);

        JScrollPane scrollPane = new JScrollPane(routesTable);
        tablePanel.add(scrollPane);

        return tablePanel;
    }
}

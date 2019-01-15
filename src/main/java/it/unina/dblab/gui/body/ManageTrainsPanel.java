package it.unina.dblab.gui.body;

import it.unina.dblab.gui.body.trains.EditTrainDialog;
import it.unina.dblab.gui.body.trains.TrainsCellRenderer;
import it.unina.dblab.gui.body.trains.TrainsTableModel;
import it.unina.dblab.gui.utility.DatabaseUtil;
import it.unina.dblab.models.Train;
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

public class ManageTrainsPanel extends JPanel {
    public static final String NAME = "MANAGE_TRAINS";

    private BodyContainer parent;

    private JTable trainsTable;

    public ManageTrainsPanel(BodyContainer parent) {
        this.parent = parent;

        this.setBackground(Color.WHITE);
        this.setLayout(new BorderLayout());

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEADING));

        JLabel titleLabel = new JLabel("Gestione dei Treni");
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

        LeftMenuButton addNew = new LeftMenuButton("Aggiungi Nuovo");
        addNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // create a dialog Box
                Train trainModel = new Train();
                trainModel.setNominalSpeed(150);
                trainModel.setCarriages(1);
                trainModel.setCategory("");
                trainModel.setCode("");

                JDialog dialog = new EditTrainDialog(trainModel);
                dialog.addComponentListener(new ComponentAdapter() {
                    @Override
                    public void componentHidden(ComponentEvent e) {
                        super.componentHidden(e);
                        ((TrainsTableModel) trainsTable.getModel()).reload();
                        trainsTable.revalidate();
                        trainsTable.repaint();
                    }
                });
                // setsize of dialog
                dialog.pack();
                dialog.setSize(400, 220);
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
                int selectedRow = trainsTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(parent, "Seleziona un elemento da modificare!");
                } else {
                    Train trainModel = ((TrainsTableModel) trainsTable.getModel()).getEntityAt(selectedRow);
                    // create a dialog Box
                    JDialog dialog = new EditTrainDialog(trainModel);
                    dialog.addComponentListener(new ComponentAdapter() {
                        @Override
                        public void componentHidden(ComponentEvent e) {
                            super.componentHidden(e);
                            ((TrainsTableModel) trainsTable.getModel()).reload();
                            trainsTable.revalidate();
                            trainsTable.repaint();
                        }
                    });
                    // setsize of dialog
                    dialog.pack();
                    dialog.setSize(400, 220);
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
                int selectedRow = trainsTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(parent, "Seleziona un elemento da eliminare!", "Attenzione", JOptionPane.WARNING_MESSAGE);
                }
                else {
                    Train trainModel = ((TrainsTableModel) trainsTable.getModel()).getEntityAt(selectedRow);

                    try {
                        DatabaseUtil.removeEntity(trainModel);

                        trainsTable.clearSelection();
                        ((TrainsTableModel) trainsTable.getModel()).reload();
                        trainsTable.revalidate();
                        trainsTable.repaint();

                    }
                    catch (RollbackException rex) {
                        String errorMessage = Optional.ofNullable(rex.getCause())
                                .map(cause -> cause.getCause())
                                .filter(deepCause -> deepCause instanceof ConstraintViolationException)
                                .map(deepCause -> ((ConstraintViolationException)deepCause))
                                .map(constraintViolation -> constraintViolation.getSQLException())
                                .filter(sqlException -> sqlException != null)
                                .map(sqlException -> sqlException.getMessage())
                                .orElse("Impossibile effettuare l'operazione");
                        JOptionPane.showMessageDialog(ManageTrainsPanel.this, errorMessage, "Violazione del vincolo", JOptionPane.ERROR_MESSAGE);
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

        trainsTable = new JTable(new TrainsTableModel());
        trainsTable.setOpaque(false);
        trainsTable.setPreferredScrollableViewportSize(new Dimension(860, 600));
        trainsTable.setFillsViewportHeight(true);
        trainsTable.setRowHeight(35);
        trainsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        TableCellRenderer cellRenderer = new TrainsCellRenderer();
        //column ID
        TableColumn col = trainsTable.getColumnModel().getColumn(0);
        col.setCellRenderer(cellRenderer);
        col.setMinWidth(30);
        col.setMaxWidth(30);

        //column Categoria
        col = trainsTable.getColumnModel().getColumn(1);
        col.setCellRenderer(cellRenderer);
        col.setMinWidth(300);

        //column Acronimo
        col = trainsTable.getColumnModel().getColumn(2);
        col.setCellRenderer(cellRenderer);
        col.setMinWidth(100);
        col.setMaxWidth(100);

        //column Velocità
        col = trainsTable.getColumnModel().getColumn(3);
        col.setCellRenderer(cellRenderer);
        col.setMinWidth(170);
        col.setMaxWidth(170);

        //column Carrozze
        col = trainsTable.getColumnModel().getColumn(4);
        col.setCellRenderer(cellRenderer);
        col.setMinWidth(130);
        col.setMaxWidth(130);

        JScrollPane scrollPane = new JScrollPane(trainsTable);
        tablePanel.add(scrollPane);

        return tablePanel;
    }
}

package it.unina.dblab.gui.body;

import it.unina.dblab.gui.body.routes.EditRouteSegmentDialog;
import it.unina.dblab.gui.body.routes.RouteSegmentsCellRenderer;
import it.unina.dblab.gui.body.routes.RouteSegmentsTableModel;
import it.unina.dblab.gui.utility.DatabaseUtil;
import it.unina.dblab.models.RouteSegment;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class ManageRouteSegmentsPanel extends JPanel {
    public static final String NAME = "MANAGE_ROUTES";

    private BodyContainer parent;

    private JTable routeSegmentsTable;

    public ManageRouteSegmentsPanel(BodyContainer parent) {
        this.parent = parent;

        this.setBackground(Color.WHITE);
        this.setLayout(new BorderLayout());

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEADING));

        JLabel titleLabel = new JLabel("Gestione dei Segmenti");
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

        LeftMenuButton addNewSegment = new LeftMenuButton("Aggiungi Nuovo");
        addNewSegment.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // create a dialog Box
                RouteSegment routeSegmentModel = new RouteSegment();

                JDialog dialog = new EditRouteSegmentDialog(routeSegmentModel);
                dialog.addComponentListener(new ComponentAdapter() {
                    @Override
                    public void componentHidden(ComponentEvent e) {
                        super.componentHidden(e);
                        ((RouteSegmentsTableModel) routeSegmentsTable.getModel()).reload();
                        routeSegmentsTable.revalidate();
                        routeSegmentsTable.repaint();
                    }
                });
                // setsize of dialog
                dialog.pack();
                dialog.setSize(450, 300);
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
                int selectedRow = routeSegmentsTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(parent, "Seleziona un elemento da modificare!");
                } else {
                    RouteSegment routeSegmentModel = ((RouteSegmentsTableModel) routeSegmentsTable.getModel()).getEntityAt(selectedRow);
                    // create a dialog Box
                    JDialog dialog = new EditRouteSegmentDialog(routeSegmentModel);
                    dialog.addComponentListener(new ComponentAdapter() {
                        @Override
                        public void componentHidden(ComponentEvent e) {
                            super.componentHidden(e);
                            ((RouteSegmentsTableModel) routeSegmentsTable.getModel()).reload();
                            routeSegmentsTable.revalidate();
                            routeSegmentsTable.repaint();
                        }
                    });
                    // setsize of dialog
                    dialog.pack();
                    dialog.setSize(450, 300);
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
                int selectedRow = routeSegmentsTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(parent, "Seleziona un elemento da eliminare!", "Attenzione", JOptionPane.WARNING_MESSAGE);
                } else {
                    routeSegmentsTable.clearSelection();

                    RouteSegment routeSegmentModel = ((RouteSegmentsTableModel) routeSegmentsTable.getModel()).getEntityAt(selectedRow);

                    try {
                        DatabaseUtil.removeEntity(routeSegmentModel);

                        ((RouteSegmentsTableModel) routeSegmentsTable.getModel()).reload();
                        routeSegmentsTable.revalidate();
                        routeSegmentsTable.repaint();

                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(parent, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        panel.add(delete);

        return panel;
    }

    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel();
        Color color1 = new Color(253, 255, 227);
        tablePanel.setBackground(color1);
        tablePanel.setPreferredSize(new Dimension(100, 100));

        routeSegmentsTable = new JTable(new RouteSegmentsTableModel());
        routeSegmentsTable.setOpaque(false);
        routeSegmentsTable.setPreferredScrollableViewportSize(new Dimension(860, 700));
        routeSegmentsTable.setFillsViewportHeight(true);
        routeSegmentsTable.setRowHeight(35);
        routeSegmentsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        TableCellRenderer cellRenderer = new RouteSegmentsCellRenderer();
        //column ID
        TableColumn col = routeSegmentsTable.getColumnModel().getColumn(0);
        col.setCellRenderer(cellRenderer);
        col.setMinWidth(30);
        col.setMaxWidth(30);

        //column Stazione di partenza
        col = routeSegmentsTable.getColumnModel().getColumn(1);
        col.setCellRenderer(cellRenderer);
        col.setMinWidth(150);

        //column Stazione di arrivo
        col = routeSegmentsTable.getColumnModel().getColumn(2);
        col.setCellRenderer(cellRenderer);
        col.setMinWidth(150);

        //column Distanza
        col = routeSegmentsTable.getColumnModel().getColumn(3);
        col.setCellRenderer(cellRenderer);
        col.setMinWidth(110);
        col.setMaxWidth(110);

        JScrollPane scrollPane = new JScrollPane(routeSegmentsTable);
        tablePanel.add(scrollPane);

        return tablePanel;
    }
}

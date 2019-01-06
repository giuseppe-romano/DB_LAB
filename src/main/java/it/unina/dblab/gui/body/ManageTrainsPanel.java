package it.unina.dblab.gui.body;

import it.unina.dblab.HeavenRail;
import it.unina.dblab.gui.body.trains.TrainsCellRenderer;
import it.unina.dblab.gui.body.trains.TrainsTableModel;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManageTrainsPanel extends JPanel {
    public static final String NAME = "MANAGE_TRAINS";

    private BodyContainer parent;

    public ManageTrainsPanel(BodyContainer parent) {
        this.parent = parent;

        this.setBackground(Color.WHITE);
        this.setLayout(new BorderLayout());

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEADING));

        JLabel titleLabel = new JLabel("Trains Management");
        titlePanel.setBackground(Color.WHITE);

        titleLabel.setFont(new Font("Candara", Font.PLAIN, 26));
        titlePanel.add(titleLabel);

        this.add(titlePanel, BorderLayout.PAGE_START);

        this.add(createLeftMenu(), BorderLayout.LINE_START);
        this.add(createTablePanel(), BorderLayout.CENTER);

    }

    private JPanel createLeftMenu() {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(300, 100));
        panel.setBackground(new Color(211, 212, 222, 255));
        panel.setLayout(new GridLayout(10, 1));

        JButton addNew = new JButton("Aggiungi Nuovo");
        addNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // create a dialog Box
                JDialog d = new JDialog(HeavenRail.getFrame(), "Aggiungi nuovo treno");
                d.setModal(true);
                d.setLocationRelativeTo(HeavenRail.getFrame());
                // create a label
                JLabel l = new JLabel("this is a dialog box");

                d.add(l);

                // setsize of dialog
                d.setSize(100, 100);

                // set visibility of dialog
                d.setVisible(true);
            }
        });
        panel.add(addNew);

        JButton modify = new JButton("Modifica");
        modify.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        panel.add(modify);

        return panel;
    }

    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel();
        tablePanel.setPreferredSize(new Dimension(100, 100));

        JTable table = new JTable(new TrainsTableModel());
        table.setPreferredScrollableViewportSize(new Dimension(860, 700));
        table.setFillsViewportHeight(true);
        table.setRowHeight(35);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        TableCellRenderer cellRenderer = new TrainsCellRenderer();
        //column ID
        TableColumn col = table.getColumnModel().getColumn(0);
        col.setCellRenderer(cellRenderer);
        col.setMinWidth(50);
        col.setMaxWidth(50);

        //column Categoria
        col = table.getColumnModel().getColumn(1);
        col.setCellRenderer(cellRenderer);
        col.setMinWidth(300);

        //column Acronimo
        col = table.getColumnModel().getColumn(2);
        col.setCellRenderer(cellRenderer);
        col.setMinWidth(100);
        col.setMaxWidth(100);

        //column Velocità
        col = table.getColumnModel().getColumn(3);
        col.setCellRenderer(cellRenderer);
        col.setMinWidth(170);
        col.setMaxWidth(170);

        //column Carrozze
        col = table.getColumnModel().getColumn(4);
        col.setCellRenderer(cellRenderer);
        col.setMinWidth(130);
        col.setMaxWidth(130);

        JScrollPane scrollPane = new JScrollPane(table);
        tablePanel.add(scrollPane);

        return tablePanel;
    }
}

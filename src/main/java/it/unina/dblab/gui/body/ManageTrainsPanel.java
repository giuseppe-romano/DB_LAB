package it.unina.dblab.gui.body;

import it.unina.dblab.gui.body.trains.TrainsTableModel;

import javax.swing.*;
import java.awt.*;

public class ManageTrainsPanel extends JPanel {
    public static final String NAME = "MANAGE_TRAINS";

    private BodyContainer parent;

    public ManageTrainsPanel(BodyContainer parent) {
        this.parent = parent;

        this.setBackground(Color.WHITE);
        this.setLayout(new BorderLayout());

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
        titlePanel.add(new JLabel("Trains Management"));


        this.add(titlePanel, BorderLayout.PAGE_START);

        this.add(createLeftMenu(), BorderLayout.LINE_START);
        this.add(createTablePanel(), BorderLayout.CENTER);

    }

    private JPanel createLeftMenu() {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(300, 100));
        panel.setBackground(Color.BLACK);

        return panel;
    }

    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel();
        tablePanel.setPreferredSize(new Dimension(100, 100));

        JTable table = new JTable(new TrainsTableModel());
        table.setPreferredScrollableViewportSize(new Dimension(660, 700));
        table.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(table);
        tablePanel.add(scrollPane);

        return tablePanel;
    }
}

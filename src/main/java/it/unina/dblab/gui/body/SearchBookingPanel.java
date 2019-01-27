package it.unina.dblab.gui.body;

import it.unina.dblab.gui.body.bookings.SearchCriteriaPanel;
import it.unina.dblab.gui.body.bookings.SearchResultTableModel;

import javax.swing.*;
import java.awt.*;

public class SearchBookingPanel extends JPanel {
    public static final String NAME = "SEARCH_BOOKING";

    private BodyContainer parent;

    private SearchResultTableModel searchResultTableModel = new SearchResultTableModel();

    private JTable resultTable;

    public SearchBookingPanel(BodyContainer parent) {
        this.parent = parent;

        this.setBackground(Color.BLACK);
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEADING));

        JLabel titleLabel = new JLabel("Ricerca Viaggio");
        titlePanel.setBackground(Color.WHITE);

        titleLabel.setFont(new Font("Candara", Font.PLAIN, 26));
        titlePanel.setMaximumSize(new Dimension(1250, 50));
        titlePanel.add(titleLabel);

        this.add(titlePanel);

        SearchCriteriaPanel searchCriteriaPanel = new SearchCriteriaPanel(searchResultTableModel);
        searchCriteriaPanel.setMaximumSize(new Dimension(1250, 90));

        this.add(searchCriteriaPanel);

        this.add(createTablePanel());
    }

    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel();
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setPreferredSize(new Dimension(100, 100));

        resultTable = new JTable(searchResultTableModel);
        resultTable.setOpaque(false);
        resultTable.setPreferredScrollableViewportSize(new Dimension(1200, 570));
        resultTable.setFillsViewportHeight(true);
        resultTable.setRowHeight(130);
        resultTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


        JScrollPane scrollPane = new JScrollPane(resultTable);
        tablePanel.add(scrollPane);

        return tablePanel;
    }

}

package it.unina.dblab.gui.body;

import it.unina.dblab.gui.body.bookings.SearchCriteriaPanel;
import it.unina.dblab.gui.body.bookings.SearchResultListCellRenderer;
import it.unina.dblab.models.SearchResult;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SearchBookingPanel extends JPanel {
    public static final String NAME = "SEARCH_BOOKING";

    private BodyContainer parent;

    private JList resultList = new JList<List<SearchResult>>();

    public SearchBookingPanel(BodyContainer parent) {
        this.parent = parent;

        this.setBackground(Color.WHITE);
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEADING));

        JLabel titleLabel = new JLabel("Ricerca Viaggio");
        titlePanel.setBackground(Color.WHITE);

        titleLabel.setFont(new Font("Candara", Font.PLAIN, 26));
        titlePanel.setMaximumSize(new Dimension(1250, 50));
        titlePanel.add(titleLabel);

        this.add(titlePanel);

        SearchCriteriaPanel searchCriteriaPanel = new SearchCriteriaPanel(resultList);
        searchCriteriaPanel.setMaximumSize(new Dimension(1250, 90));

        this.add(searchCriteriaPanel);

        this.add(createTablePanel());
    }

    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel();
        tablePanel.setBackground(Color.WHITE);

        resultList.setOpaque(false);
        resultList.setFixedCellWidth(1150);
        resultList.setVisibleRowCount(5);
        resultList.setCellRenderer(new SearchResultListCellRenderer());

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(resultList);
        scrollPane.setPreferredSize(new Dimension(1200, 580));
        tablePanel.add(scrollPane);

        return tablePanel;
    }

}

package it.unina.dblab.gui.body.bookings;

import it.unina.dblab.gui.body.segments.StationListCellRenderer;
import it.unina.dblab.gui.utility.DatabaseUtil;
import it.unina.dblab.gui.utility.SpringUtilities;
import it.unina.dblab.models.Station;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class SearchCriteriaPanel extends JPanel implements FocusListener, ActionListener, DocumentListener {

    private List<Station> stations = DatabaseUtil.listEntities(Station.class);

    private SearchResultTableModel searchResultTableModel;

    private JComboBox<Station> departureStationComboBox;
    private JComboBox<Station> arrivalStationComboBox;
    private JFormattedTextField departureDateTextField;
    private JFormattedTextField arrivalDateTextField;

    private JButton search;

    public SearchCriteriaPanel(SearchResultTableModel searchResultTableModel) {
        this.searchResultTableModel = searchResultTableModel;

        Collections.sort(stations, Comparator.comparing(e -> e.getName()));

        this.setLayout(new GridLayout(1, 5, 16, 16));
        this.setSize(800, 250);
        this.setForeground(Color.BLUE);

        JPanel departureStationPanel = composeDepartureStationPanel();

        JPanel arrivalStationPanel = composeArrivalStationPanel();

        JPanel departureDatePanel = composeDepartureDatePanel();

        JPanel arrivalDatePanel = composeArrivalDatePanel();

        this.add(departureStationPanel);
        this.add(arrivalStationPanel);
        this.add(departureDatePanel);
        this.add(arrivalDatePanel);

        search = new JButton("Cerca");
        search.addActionListener(this);
        this.add(search);
    }

    private JPanel composeDepartureStationPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new SpringLayout());

        JLabel departureStationLabel = new JLabel("Stazione di Partenza");
        panel.add(departureStationLabel);

        departureStationComboBox = new JComboBox<>(this.stations.toArray(new Station[0]));
        departureStationComboBox.setRenderer(new StationListCellRenderer());
        departureStationComboBox.setBackground(Color.WHITE);
        departureStationComboBox.addFocusListener(this);
        departureStationComboBox.addActionListener(this);
        departureStationLabel.setLabelFor(departureStationComboBox);
        panel.add(departureStationComboBox);

        //Lay out the panel.
        SpringUtilities.makeCompactGrid(panel,
                2, 1, //rows, cols
                6, 6,        //initX, initY
                0, 5);       //xPad, yPad

        return panel;
    }

    private JPanel composeArrivalStationPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new SpringLayout());

        JLabel arrivalStationLabel = new JLabel("Stazione di Arrivo");
        panel.add(arrivalStationLabel);

        arrivalStationComboBox = new JComboBox<>(this.stations.toArray(new Station[0]));
        arrivalStationComboBox.setRenderer(new StationListCellRenderer());
        arrivalStationComboBox.setBackground(Color.WHITE);
        arrivalStationComboBox.addFocusListener(this);
        arrivalStationComboBox.addActionListener(this);
        arrivalStationLabel.setLabelFor(arrivalStationComboBox);
        panel.add(arrivalStationComboBox);

        //Lay out the panel.
        SpringUtilities.makeCompactGrid(panel,
                2, 1, //rows, cols
                6, 6,        //initX, initY
                0, 5);       //xPad, yPad

        return panel;
    }

    private JPanel composeDepartureDatePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new SpringLayout());

        //Orario di partenza
        JLabel departureDateLabel = new JLabel("Dalle ore");
        panel.add(departureDateLabel);

        departureDateTextField = new JFormattedTextField(new SimpleDateFormat("dd/MM/yyyy HH:mm"));
        departureDateTextField.addFocusListener(this);
        departureDateTextField.getDocument().addDocumentListener(this);
        departureDateLabel.setLabelFor(departureDateTextField);
        panel.add(departureDateTextField);

        //Lay out the panel.
        SpringUtilities.makeCompactGrid(panel,
                2, 1, //rows, cols
                6, 6,        //initX, initY
                0, 5);       //xPad, yPad

        return panel;
    }

    private JPanel composeArrivalDatePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new SpringLayout());

        //Orario di partenza
        JLabel arrivalDateLabel = new JLabel("Alle ore");
        panel.add(arrivalDateLabel);

        arrivalDateTextField = new JFormattedTextField(new SimpleDateFormat("dd/MM/yyyy HH:mm"));
        arrivalDateTextField.addFocusListener(this);
        arrivalDateTextField.getDocument().addDocumentListener(this);
        arrivalDateLabel.setLabelFor(arrivalDateTextField);
        panel.add(arrivalDateTextField);

        //Lay out the panel.
        SpringUtilities.makeCompactGrid(panel,
                2, 1, //rows, cols
                6, 6,        //initX, initY
                0, 5);       //xPad, yPad

        return panel;
    }

    @Override
    public void focusGained(FocusEvent e) {

    }

    @Override
    public void focusLost(FocusEvent e) {
        this.setEnabledButton();
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        this.setEnabledButton();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        this.setEnabledButton();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        this.setEnabledButton();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.setEnabledButton();

        if(e.getSource() == search) {
            Integer departureStationId = ((Station) departureStationComboBox.getSelectedItem()).getId();
            Integer arrivalStationId = ((Station) arrivalStationComboBox.getSelectedItem()).getId();
            Date startDate = (Date) departureDateTextField.getValue();
            Date endDate = (Date) arrivalDateTextField.getValue();

            this.searchResultTableModel.search(departureStationId, arrivalStationId, startDate, endDate);
        }
    }

    private void setEnabledButton() {

        search.setEnabled(true);
    }
}

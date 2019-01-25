package it.unina.dblab.gui.body.bookings;

import it.unina.dblab.gui.body.segments.StationListCellRenderer;
import it.unina.dblab.gui.utility.DatabaseUtil;
import it.unina.dblab.models.Station;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SearchCriteriaPanel extends JPanel {

    private List<Station> stations = DatabaseUtil.listEntities(Station.class);

    private JComboBox<Station> departureStationComboBox;
    private JComboBox<Station> arrivalStationComboBox;
    private JFormattedTextField departureDateTextField;

    private JButton search;

    public SearchCriteriaPanel() {
        Collections.sort(stations, Comparator.comparing(e -> e.getName()));

        this.setLayout(new FlowLayout(FlowLayout.LEADING));
        this.setSize(800, 150);
        this.setForeground(Color.BLUE);

        JPanel departureStationPanel = composeDepartureStationPanel();

        JPanel arrivalStationPanel = composeArrivalStationPanel();

        JPanel departureDatePanel = composeDepartureDatePanel();

        this.add(departureStationPanel);
        this.add(new JLabel("     "));
        this.add(arrivalStationPanel);
        this.add(new JLabel("     "));
        this.add(departureDatePanel);

        this.add(new JLabel("                       "));

        search = new JButton("Cerca");
        this.add(search);
    }

    private JPanel composeDepartureStationPanel() {
        JPanel panel = new JPanel();
   //     panel.setMinimumSize(new Dimension(200, 60));
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        JLabel departureStationLabel = new JLabel("Stazione di Partenza");
        panel.add(departureStationLabel);

        departureStationComboBox = new JComboBox<>(this.stations.toArray(new Station[0]));
        departureStationComboBox.setRenderer(new StationListCellRenderer());
        departureStationComboBox.setBackground(Color.WHITE);
        departureStationLabel.setLabelFor(departureStationComboBox);
        panel.add(departureStationComboBox);

        return panel;
    }

    private JPanel composeArrivalStationPanel() {
        JPanel panel = new JPanel();
   //     panel.setMinimumSize(new Dimension(200, 60));
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        JLabel arrivalStationLabel = new JLabel("Stazione di Arrivo");
        panel.add(arrivalStationLabel);

        arrivalStationComboBox = new JComboBox<>(this.stations.toArray(new Station[0]));
        arrivalStationComboBox.setRenderer(new StationListCellRenderer());
        arrivalStationComboBox.setBackground(Color.WHITE);
        arrivalStationLabel.setLabelFor(arrivalStationComboBox);
        panel.add(arrivalStationComboBox);

        return panel;
    }

    private JPanel composeDepartureDatePanel() {
        JPanel panel = new JPanel();
   //    panel.setMinimumSize(new Dimension(200, 60));
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        //Orario di partenza
        JLabel departureDateLabel = new JLabel("Orario di Partenza", JLabel.TRAILING);
        panel.add(departureDateLabel);

        departureDateTextField = new JFormattedTextField(new SimpleDateFormat("dd/MM/yyyy HH:mm"));
        departureDateLabel.setLabelFor(departureDateTextField);
        panel.add(departureDateTextField);

        return panel;
    }
}

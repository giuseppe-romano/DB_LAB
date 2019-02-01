package it.unina.dblab.gui.body.bookings;

import it.unina.dblab.gui.utility.SpringUtilities;
import it.unina.dblab.models.SearchResult;
import it.unina.dblab.models.Station;
import it.unina.dblab.models.Train;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SearchResultComponent extends JPanel {
    private List<Station> stations;
    private List<Train> trains;

    private List<SearchResult> paths;

    public SearchResultComponent(List<Station> stations, List<Train> trains) {
        this.stations = stations;
        this.trains = trains;

        this.setLayout(new BorderLayout());
        this.setOpaque(false);
        this.setBorder(BorderFactory.createLineBorder(Color.lightGray));
    }

    public void composeIt(List<SearchResult> paths) {
        try {
            this.removeAll();

            this.paths = paths;

            JPanel container = new JPanel();
            container.setOpaque(false);
            container.setLayout(new FlowLayout(FlowLayout.LEADING));

            container.add(composeStationPanel(true));

            ImageIcon image = new ImageIcon(ImageIO.read(getClass().getClassLoader().getResource("icons/arrow-right.png")));
            JLabel arrow = new JLabel(image);

            container.add(arrow);

            container.add(composeStationPanel(false));

            container.add(composeDurationPanel());

            container.add(composeTrainPanel());

            this.add(container, BorderLayout.CENTER);

            this.add(composeIntermediateStops(), BorderLayout.SOUTH);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private JPanel composeStationPanel(boolean departure) throws Exception {
        Station station;
        SearchResult searchResult;
        if (departure) {
            searchResult = paths.get(0);
            station = stations.stream().filter(st -> st.getId().equals(searchResult.getDepartureStationId())).findFirst().get();
        } else {
            searchResult = paths.get(paths.size() - 1);
            station = stations.stream().filter(st -> st.getId().equals(searchResult.getArrivalStationId())).findFirst().get();
        }

        JPanel panel = new JPanel();
        panel.setMinimumSize(new Dimension(300, 100));
        panel.setPreferredSize(new Dimension(300, 100));
        panel.setLayout(new SpringLayout());

        panel.setAlignmentX(0);
        panel.setOpaque(false);

        JLabel departureStationLabel = new JLabel(station.getName());
        departureStationLabel.setPreferredSize(new Dimension(Integer.MAX_VALUE, 30));
        departureStationLabel.setFont(new Font("Candara", Font.BOLD, 18));
        departureStationLabel.setHorizontalAlignment(JLabel.CENTER);
        departureStationLabel.setVerticalAlignment(JLabel.CENTER);
        panel.add(departureStationLabel);

        JPanel servicePanel = new JPanel();
        servicePanel.setOpaque(false);
        servicePanel.setPreferredSize(new Dimension(Integer.MAX_VALUE, 22));
        servicePanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        if(station.getDisabledAccess()) {
            ImageIcon image = new ImageIcon(ImageIO.read(getClass().getClassLoader().getResource("icons/disabled-access.png")));
            JLabel label = new JLabel(image);
            label.setBorder(BorderFactory.createLineBorder(Color.lightGray));
            servicePanel.add(label);
        }
        if(station.getRestaurant()) {
            ImageIcon image = new ImageIcon(ImageIO.read(getClass().getClassLoader().getResource("icons/restaurant.png")));
            JLabel label = new JLabel(image);
            label.setBorder(BorderFactory.createLineBorder(Color.lightGray));
            servicePanel.add(label);
        }
        if(station.getTaxiService()) {
            ImageIcon image = new ImageIcon(ImageIO.read(getClass().getClassLoader().getResource("icons/taxi.png")));
            JLabel label = new JLabel(image);
            label.setBorder(BorderFactory.createLineBorder(Color.lightGray));
            servicePanel.add(label);
        }

        panel.add(servicePanel);

        Date time = departure ? searchResult.getDepartureDate() : searchResult.getArrivalDate();

        JLabel departureTimeLabel = new JLabel(new SimpleDateFormat("HH:mm").format(time));
        departureTimeLabel.setPreferredSize(new Dimension(Integer.MAX_VALUE, 35));
        departureTimeLabel.setFont(new Font("Candara", Font.BOLD, 24));
        departureTimeLabel.setHorizontalAlignment(JLabel.CENTER);
        departureTimeLabel.setVerticalAlignment(JLabel.CENTER);
        panel.add(departureTimeLabel);

        //Lay out the panel.
        SpringUtilities.makeCompactGrid(panel,
                3, 1, //rows, cols
                6, 6,        //initX, initY
                0, 5);       //xPad, yPad

        return panel;
    }


    private JPanel composeDurationPanel() throws Exception {
        SearchResult first = paths.get(0);
        SearchResult last = paths.get(paths.size() - 1);

        JPanel panel = new JPanel();
        panel.setMinimumSize(new Dimension(200, 80));
        panel.setPreferredSize(new Dimension(200, 80));
        panel.setLayout(new SpringLayout());

        panel.setAlignmentX(0);
        panel.setOpaque(false);

        ImageIcon image = new ImageIcon(ImageIO.read(getClass().getClassLoader().getResource("icons/clock.png")));
        JLabel clock = new JLabel(image);
        clock.setPreferredSize(new Dimension(80, Integer.MAX_VALUE));
        panel.add(clock);

        long diff = last.getArrivalDate().getTime() - first.getDepartureDate().getTime();


        JLabel durationLabel = new JLabel(new SimpleDateFormat("HH:mm").format(new Date(diff)));
        durationLabel.setPreferredSize(new Dimension(120, Integer.MAX_VALUE));
        durationLabel.setFont(new Font("Candara", Font.BOLD, 24));
        durationLabel.setHorizontalAlignment(JLabel.LEFT);
        durationLabel.setVerticalAlignment(JLabel.CENTER);
        panel.add(durationLabel);

        //Lay out the panel.
        SpringUtilities.makeCompactGrid(panel,
                1, 2, //rows, cols
                6, 6,        //initX, initY
                0, 5);       //xPad, yPad

        return panel;
    }

    private JPanel composeTrainPanel() {
        Set<Train> routeTrains = new HashSet<>();
        for (SearchResult path : paths) {

            Train train = trains.stream().filter(tr -> tr.getId().equals(path.getTrainId())).findFirst().get();
            routeTrains.add(train);
        }

        JPanel container = new JPanel();
        container.setOpaque(false);
        container.setLayout(new GridLayout(routeTrains.size(), 1));


        for (Train train : routeTrains) {
            JLabel trainLabel = new JLabel(train.getCategory() + " (" + train.getCode() + ")");
            trainLabel.setPreferredSize(new Dimension(300, 40));
            trainLabel.setFont(new Font("Candara", Font.BOLD, 16));
            trainLabel.setHorizontalAlignment(JLabel.CENTER);
            trainLabel.setVerticalAlignment(JLabel.CENTER);

            container.add(trainLabel);
        }

        return container;
    }

    private JPanel composeIntermediateStops() {

        JPanel container = new JPanel();
        container.setOpaque(false);
        container.setLayout(new FlowLayout(FlowLayout.LEADING));

        Date departureDate = paths.get(0).getDepartureDate();
        container.add(new JLabel("In data: " +  new SimpleDateFormat("dd/MM/yyyy").format(departureDate)));

        int currentTrainId = -1;
        long currentArrivalTime = -1;
        container.add(new JLabel(" - Ferma in:  "));
        for (int i = 0, n = paths.size(); i < n; i++) {
            SearchResult path = paths.get(i);
            if (currentTrainId > 0 && currentTrainId != path.getTrainId()) {
                long waitingTime = path.getDepartureDate().getTime() - currentArrivalTime;

                JLabel label = new JLabel("Cambio (Attesa di: " + new SimpleDateFormat("HH:mm").format(new Date(waitingTime)) + ")");
                label.setForeground(Color.RED);
                container.add(label);

            }
            currentTrainId = path.getTrainId();
            currentArrivalTime = path.getArrivalDate().getTime() + (1000 * 60 * 60);

            if (i != 0) {
                container.add(new JLabel(" - "));
            }
            Station station = stations.stream().filter(st -> st.getId().equals(path.getArrivalStationId())).findFirst().get();
            JLabel label = new JLabel(station.getName() + " (" + new SimpleDateFormat("HH:mm").format(path.getArrivalDate()) + ")");
            label.setForeground(Color.BLUE);
            container.add(label);
        }

        return container;
    }
}

package it.unina.dblab.gui.body.bookings;

import it.unina.dblab.gui.utility.DatabaseUtil;
import it.unina.dblab.gui.utility.SpringUtilities;
import it.unina.dblab.models.SearchResult;
import it.unina.dblab.models.Station;
import it.unina.dblab.models.Train;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.*;
import java.util.List;

public class SearchResultComponent extends JPanel {
    private List<Station> stations = DatabaseUtil.listEntities(Station.class);
    private List<Train> trains = DatabaseUtil.listEntities(Train.class);

    private List<SearchResult> paths;

    private JPanel title;
    private JPanel body;
    private JScrollPane scrollPane;

    public SearchResultComponent() {
        this.setLayout(new FlowLayout(FlowLayout.LEADING));

        this.setBorder(BorderFactory.createLineBorder(Color.lightGray));
    }

    public void composeIt(List<SearchResult> paths) {
        try {
            this.removeAll();

            this.paths = paths;

            this.add(composeStationPanel(true));

            ImageIcon image = new ImageIcon(ImageIO.read(getClass().getClassLoader().getResource("icons/arrow-right.png")));
            JLabel arrow = new JLabel(image);

            this.add(arrow);

            this.add(composeStationPanel(false));

            this.add(composeDurationPanel());

            this.add(composeTrainPanel());
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private JPanel composeStationPanel(boolean departure) {
        Station station;
        SearchResult searchResult;
        if(departure) {
            searchResult = paths.get(0);
            station = stations.stream().filter(st -> st.getId().equals(searchResult.getDepartureStationId())).findFirst().get();
        }
        else {
            searchResult = paths.get(paths.size() - 1);
            station = stations.stream().filter(st -> st.getId().equals(searchResult.getArrivalStationId())).findFirst().get();
        }

        JPanel panel = new JPanel();
        panel.setMinimumSize(new Dimension(300, 80));
        panel.setPreferredSize(new Dimension(300, 80));
        panel.setLayout(new SpringLayout());

        panel.setAlignmentX(0);
        panel.setOpaque(true);
        panel.setBackground(Color.CYAN);

        JLabel departureStationLabel = new JLabel(station.getName());
        departureStationLabel.setPreferredSize(new Dimension(Integer.MAX_VALUE, 35));
        departureStationLabel.setFont(new Font("Candara", Font.BOLD, 18));
        departureStationLabel.setHorizontalAlignment(JLabel.CENTER);
        departureStationLabel.setVerticalAlignment(JLabel.CENTER);
        panel.add(departureStationLabel);

        Date time = departure ? searchResult.getDepartureDate() : searchResult.getArrivalDate();

        JLabel departureTimeLabel = new JLabel(new SimpleDateFormat("HH:mm").format(time));
        departureTimeLabel.setPreferredSize(new Dimension(Integer.MAX_VALUE, 35));
        departureTimeLabel.setFont(new Font("Candara", Font.BOLD, 24));
        departureTimeLabel.setHorizontalAlignment(JLabel.CENTER);
        departureTimeLabel.setVerticalAlignment(JLabel.CENTER);
        panel.add(departureTimeLabel);

        //Lay out the panel.
        SpringUtilities.makeCompactGrid(panel,
                2, 1, //rows, cols
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
        panel.setOpaque(true);
        panel.setBackground(Color.CYAN);

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

    private JPanel composeTrainPanel() throws Exception {
        Set<Train> routeTrains = new HashSet<>();
        for(SearchResult path : paths) {

            Train train = trains.stream().filter(tr -> tr.getId().equals(path.getTrainId())).findFirst().get();
            routeTrains.add(train);
        }

        JPanel container = new JPanel();
        container.setOpaque(false);
        container.setLayout(new GridLayout(routeTrains.size(), 1));


        for (Train train : routeTrains) {
            JPanel panel = new JPanel();
            panel.setMinimumSize(new Dimension(330, 40));
            panel.setPreferredSize(new Dimension(330, 40));
            panel.setLayout(new SpringLayout());

            panel.setAlignmentX(0);
            panel.setOpaque(false);


            JLabel trainLabel = new JLabel(train.getCategory() + " (" + train.getCode() + ")");
            trainLabel.setPreferredSize(new Dimension(150, 40));
            trainLabel.setFont(new Font("Candara", Font.BOLD, 16));
            trainLabel.setHorizontalAlignment(JLabel.CENTER);
            trainLabel.setVerticalAlignment(JLabel.CENTER);
            panel.add(trainLabel);



            ImageIcon image = new ImageIcon(ImageIO.read(getClass().getClassLoader().getResource("icons/info.png")));
            JLabel infoLabel = new JLabel(image);
            infoLabel.setPreferredSize(new Dimension(140, 40));
            panel.add(infoLabel);

            //Lay out the panel.
            SpringUtilities.makeCompactGrid(panel,
                    1, 2, //rows, cols
                    6, 6,        //initX, initY
                    0, 5);       //xPad, yPad

            container.add(panel);
        }

        return container;
    }
}

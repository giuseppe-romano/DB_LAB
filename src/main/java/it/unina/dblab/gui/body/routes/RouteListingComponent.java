package it.unina.dblab.gui.body.routes;

import it.unina.dblab.models.Route;
import it.unina.dblab.models.RouteSegment;
import it.unina.dblab.models.Station;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class RouteListingComponent extends JPanel {

    private Route route;

    public RouteListingComponent(Route route) {
        this.route = route;

        this.setLayout(new BorderLayout());
        this.setMaximumSize(new Dimension(100, 300));


        JPanel title = new JPanel();
        title.add(new JLabel("Da: " + route.getSegment().getDepartureStation().getName() + "        A: " + getTerminalStationName(route) + "           Distanza totale: " + getTotalDistance(route) + " Km"));
        this.add(title, BorderLayout.NORTH);

        JPanel body = new JPanel();
        body.setBackground(Color.WHITE);
        body.setMaximumSize(new Dimension(100, 300));

        body.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 0));
        body.setComponentOrientation(
                ComponentOrientation.LEFT_TO_RIGHT);

        List<JPanel> panels = this.composePath(route);
        panels.forEach(panel -> body.add(panel));

        JScrollPane scrollPane = new JScrollPane(body, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        this.add(scrollPane, BorderLayout.CENTER);
    }

    private List<JPanel> composePath(Route route) {
        List<JPanel> segments = new ArrayList<>();
        while (route != null) {
            segments.add(this.createSegment(route));

            route = route.getNextRoute();
        }
        JPanel addButtonPanel = new JPanel();

        addButtonPanel.setBackground(Color.WHITE);
        addButtonPanel.setMinimumSize(new Dimension(40, 83));
        addButtonPanel.setMaximumSize(new Dimension(40, 83));
        addButtonPanel.setPreferredSize(new Dimension(40, 83));

        JButton addButton = new AddRouteButton(route);
        addButtonPanel.add(addButton);

        segments.add(addButtonPanel);

        return segments;
    }

    private JPanel createSegment(Route route) {
        JPanel segment = new JPanel();
        segment.setBackground(new Color(176, 218, 255));
        segment.setMinimumSize(new Dimension(120, 83));
        segment.setMaximumSize(new Dimension(120, 83));
        segment.setPreferredSize(new Dimension(120, 83));

        Border border = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
        segment.setBorder(border);

        segment.setLayout(new BoxLayout(segment, BoxLayout.PAGE_AXIS));

        segment.add(new JLabel("Da: " + route.getSegment().getDepartureStation().getName()));
        segment.add(new JLabel("A: " + route.getSegment().getArrivalStation().getName()));
        segment.add(new JLabel("Distanza: " + route.getSegment().getDistance() + " Km"));
        segment.add(new JLabel("Fermata: " + (route.getStopsAtArrival() ? "SI" : "NO")));

        return segment;
    }

    private String getTerminalStationName(Route route) {
        if(route.getNextRoute() != null) {
            return this.getTerminalStationName(route.getNextRoute());
        }
        return Optional.ofNullable(route)
                .map(Route::getSegment)
                .map(RouteSegment::getArrivalStation)
                .map(Station::getName)
                .orElse("??????");
    }

    private Integer getTotalDistance(Route route) {
        if(route.getNextRoute() != null) {
            Integer currentDistance = Optional.ofNullable(route)
                    .map(Route::getSegment)
                    .map(RouteSegment::getDistance)
                    .orElse(0);
            return currentDistance + this.getTotalDistance(route.getNextRoute());
        }

        return Optional.ofNullable(route)
                .map(Route::getSegment)
                .map(RouteSegment::getDistance)
                .orElse(0);
    }
}

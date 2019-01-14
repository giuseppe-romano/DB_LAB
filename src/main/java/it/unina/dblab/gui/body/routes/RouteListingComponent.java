package it.unina.dblab.gui.body.routes;

import it.unina.dblab.models.Route;
import it.unina.dblab.models.Route2RouteSegment;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class RouteListingComponent extends JPanel {

    private Route route;

    private JPanel title;
    private JPanel body;

    public RouteListingComponent(Route route) {
        this.route = route;

        this.setLayout(new BorderLayout());
        this.setMaximumSize(new Dimension(100, 300));

        title = new JPanel();

        title.add(new JLabel(route.getName()));
        this.add(title, BorderLayout.NORTH);

        body = new JPanel();
        body.setBackground(this.getBackground());
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
        for (Route2RouteSegment routeSegment : route.getRouteSegments()) {
            segments.add(this.createSegment(routeSegment));
        }
        return segments;
    }

    private JPanel createSegment(Route2RouteSegment routeSegment) {
        JPanel segment = new JPanel();
        segment.setBackground(new Color(176, 218, 255));
        segment.setMinimumSize(new Dimension(120, 83));
        segment.setMaximumSize(new Dimension(120, 83));
        segment.setPreferredSize(new Dimension(120, 83));

        Border border = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
        segment.setBorder(border);

        segment.setLayout(new BoxLayout(segment, BoxLayout.PAGE_AXIS));

        segment.add(new JLabel("Da: " + routeSegment.getSegment().getDepartureStation().getName()));
        segment.add(new JLabel("A: " + routeSegment.getSegment().getArrivalStation().getName()));
        segment.add(new JLabel("Distanza: " + routeSegment.getSegment().getDistance() + " Km"));
        segment.add(new JLabel("Fermata: " + (routeSegment.isPerformStop() ? "SI" : "NO")));

        return segment;
    }

    private Integer getTotalDistance(Route route) {
        Integer totalDistance = 0;
        for (Route2RouteSegment routeSegment : route.getRouteSegments()) {
            totalDistance += routeSegment.getSegment().getDistance();
        }
        return totalDistance;
    }

    @Override
    public void setBackground(Color bg) {
        super.setBackground(bg);

        if(title != null) {
            title.setBackground(bg);
        }
        if(body != null) {
            body.setBackground(bg);
        }
    }
}

package it.unina.dblab.gui.body.routes;

import it.unina.dblab.models.Route;
import it.unina.dblab.models.RouteSegment;

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
    private JScrollPane scrollPane;

    public RouteListingComponent() {

        this.setLayout(new BorderLayout());
        this.setMaximumSize(new Dimension(100, 300));
    }

    public void composeIt(Route route) {
        this.removeAll();

        this.route = route;


        title = new JPanel();
        title.setLayout(new GridLayout(1, 2));

        Color invalidColor = new Color(255, 156, 161);
        Color validColor = new Color(138, 255, 120);
        JPanel title1 = new JPanel();
        title1.setBackground(invalidColor);
        title1.setLayout(new FlowLayout(FlowLayout.LEADING));
        title1.add(new JLabel(route.getName()));
        title1.add(new JLabel("(Effettua " + getTotalStops(route) + " fermate)"));
        title.add(title1);

        JPanel title2 = new JPanel();
        title2.setBackground(invalidColor);
        title2.setLayout(new FlowLayout(FlowLayout.TRAILING));
        String labelText = "(La tratta non e' completa o presenta dei cicli)";
        if(route.isActive()) {
            title1.setBackground(validColor);
            title2.setBackground(validColor);
            labelText = "Distanza totale: " + this.getTotalDistance(route) + "Km";
        }
        title2.add(new JLabel(labelText));
        title.add(title2);

        this.add(title, BorderLayout.NORTH);

        body = new JPanel();
        body.setBackground(this.getBackground());
        body.setMaximumSize(new Dimension(100, 300));

        body.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 0));
        body.setComponentOrientation(
                ComponentOrientation.LEFT_TO_RIGHT);

        List<JPanel> panels = this.composePath(route);
        panels.forEach(panel -> body.add(panel));

        scrollPane = new JScrollPane(body, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        this.add(scrollPane, BorderLayout.CENTER);

    }

    private List<JPanel> composePath(Route route) {
        List<JPanel> segments = new ArrayList<>();
        for (RouteSegment routeSegment : route.getRouteSegments()) {
            segments.add(this.createSegment(routeSegment));
        }
        return segments;
    }

    private JPanel createSegment(RouteSegment routeSegment) {
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
        for (RouteSegment routeSegment : route.getRouteSegments()) {
            totalDistance += routeSegment.getSegment().getDistance();
        }
        return totalDistance;
    }

    private Integer getTotalStops(Route route) {
        Integer totalStops = 0;
        for (RouteSegment routeSegment : route.getRouteSegments()) {
            totalStops += (routeSegment.isPerformStop() ? 1 : 0);
        }
        return totalStops;
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
        if(scrollPane != null) {
            scrollPane.setBackground(bg);
        }
    }
}

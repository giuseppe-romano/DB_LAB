package it.unina.dblab.gui.body.timetable;

import it.unina.dblab.models.Route;
import it.unina.dblab.models.RouteSegment;
import it.unina.dblab.models.Station;

import javax.swing.*;
import java.awt.*;
import java.util.Comparator;

public class RouteListCellRenderer extends JLabel implements ListCellRenderer<Route> {

    public RouteListCellRenderer() {
        setOpaque(true);
    }


    @Override
    public Component getListCellRendererComponent(JList<? extends Route> list, Route value, int index, boolean isSelected, boolean cellHasFocus) {
        setText("");
        if (value != null) {
            Station firstStation = value.getRouteSegments()
                    .stream()
                    .min(Comparator.comparing(RouteSegment::getSequence))
                    .get().getSegment().getDepartureStation();

            Station lastStation = value.getRouteSegments()
                    .stream()
                    .max(Comparator.comparing(RouteSegment::getSequence))
                    .get().getSegment().getArrivalStation();

            setText(value.getName() + " (" + firstStation.getName() + " - " + lastStation.getName() + ")");
        }

        Color background;
        Color foreground;
        if (isSelected) {
            background = new Color(176, 218, 255);
            foreground = Color.WHITE;

        } else if (cellHasFocus) {
            background = Color.WHITE;

        } else {
            background = Color.WHITE;
            foreground = Color.BLACK;
        }

        setBackground(background);
        //   setForeground(foreground);

        return this;
    }
}

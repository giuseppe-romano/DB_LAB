package it.unina.dblab.gui.body.segments;

import it.unina.dblab.models.Station;

import javax.swing.*;
import java.awt.*;

public class StationListCellRenderer extends JLabel implements ListCellRenderer<Station> {

    public StationListCellRenderer() {
        setOpaque(true);
    }


    @Override
    public Component getListCellRendererComponent(JList<? extends Station> list, Station value, int index, boolean isSelected, boolean cellHasFocus) {
        setText("");
        if(value != null) {
            setText(value.getName());
        }

        Color background;
        Color foreground;
        if (isSelected) {
            background = new Color(176, 218, 255);
            foreground = Color.WHITE;

        }
        else if (cellHasFocus) {
            background = Color.WHITE;

        }
        else {
            background = Color.WHITE;
            foreground = Color.BLACK;
        }

        setBackground(background);
     //   setForeground(foreground);

        return this;
    }
}

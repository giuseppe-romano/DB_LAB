package it.unina.dblab.gui.body.timetable;

import it.unina.dblab.models.Train;

import javax.swing.*;
import java.awt.*;

public class TrainListCellRenderer extends JLabel implements ListCellRenderer<Train> {

    public TrainListCellRenderer() {
        setOpaque(true);
    }


    @Override
    public Component getListCellRendererComponent(JList<? extends Train> list, Train value, int index, boolean isSelected, boolean cellHasFocus) {
        setText("");
        if (value != null) {
            setText(value.getCategory() + " (" + value.getCode() + ")");
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

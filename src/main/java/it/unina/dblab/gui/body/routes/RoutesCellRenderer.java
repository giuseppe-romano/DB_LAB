package it.unina.dblab.gui.body.routes;


import it.unina.dblab.models.Route;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class RoutesCellRenderer implements TableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        switch (column) {

            case 0:
                //column ID
                JLabel label = new JLabel(value.toString());
                label.setBorder(new EmptyBorder(0, 5, 0, 5));//top,left,bottom,right
                label.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
                if (isSelected) {
                    label.setBackground(new Color(176, 218, 255));
                    label.setForeground(Color.WHITE);
                    label.setOpaque(true);
                }

                return label;
            case 1:
                //column Tratta di percorrenza
                JPanel panel = new RouteListingComponent((Route) value);
                if (isSelected) {
                    panel.setBackground(new Color(176, 218, 255));
                    panel.setForeground(Color.WHITE);
                    panel.setOpaque(true);
                }

                return panel;
        }
        return null;
    }
}
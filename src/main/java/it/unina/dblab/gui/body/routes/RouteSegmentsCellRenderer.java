package it.unina.dblab.gui.body.routes;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class RouteSegmentsCellRenderer implements TableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JLabel label = new JLabel (value.toString());
        label.setBorder(new EmptyBorder(0, 5, 0, 5));//top,left,bottom,right

        switch (column) {

            case 0:
                //column ID
                label.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
                break;
            case 1:
                //column Stazione di partenza
                break;
            case 2:
                //column Stazione di arrivo
                break;
            case 3:
                //column Distanza
                label.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
                break;
        }

        if (isSelected) {
            label.setBackground(new Color(33, 58, 255, 255));
            label.setForeground(Color.WHITE);
            label.setOpaque(true);
        }

        return label;
    }
}
package it.unina.dblab.gui.body.timetable;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

public class TimetableCellRenderer implements TableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JLabel label = new JLabel(value.toString());
        label.setBorder(new EmptyBorder(0, 5, 0, 5));//top,left,bottom,right
        switch (column) {

            case 0:
                //column ID
                label.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
                break;
            case 1:
                //column Treno
                break;
            case 2:
                //column Tratta di percorrenza
                break;
            case 3:
                String text = Optional.ofNullable(value)
                        .map(obj -> (Date) obj)
                        .map(departureDate -> new SimpleDateFormat("dd/MM/yyyy HH:mm").format(departureDate))
                        .orElse("");
                label.setText(text);
                label.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
                break;
            case 4:
            case 5:
                //column Binario partenza e arrivo
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
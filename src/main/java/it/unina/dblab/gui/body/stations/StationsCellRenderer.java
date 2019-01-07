package it.unina.dblab.gui.body.stations;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class StationsCellRenderer implements TableCellRenderer {
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
                //column Nome
                break;
            case 2:
                //column Indirizzo
                JTextArea textArea = new JTextArea (value.toString());
                textArea.setBorder(new EmptyBorder(0, 5, 0, 5));//top,left,bottom,right
                textArea.setOpaque(false);
                textArea.setLineWrap(true);
                textArea.setWrapStyleWord(true);
                textArea.setFont(new Font("Candara", Font.BOLD, 14));

                if (isSelected) {
                    textArea.setBackground(new Color(33, 58, 255, 255));
                    textArea.setForeground(Color.WHITE);
                    textArea.setOpaque(true);
                }

                return textArea;
            case 3:
                //column Telefono
                label.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
                break;
            case 4:
                //column N. Piattaforme
                label.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
                break;
            case 5:
            case 6:
            case 7:
                //column Acc. disabili
                label.setHorizontalAlignment(SwingConstants.CENTER);
                label.setText(value.equals(Boolean.TRUE) ? "X" : "");
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
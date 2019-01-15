package it.unina.dblab.gui.body.routes;


import it.unina.dblab.models.Route;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.EventObject;

public class RoutesCellRenderer implements TableCellRenderer, TableCellEditor {
  //  private RouteListingComponent panel = new RouteListingComponent();

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        switch (column) {

            case 0:
                //column ID
                JLabel label = new JLabel(value.toString());
                label.setBorder(new EmptyBorder(0, 5, 0, 5));//top,left,bottom,right
                label.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
                if (isSelected || hasFocus) {
                    label.setBackground(new Color(176, 218, 255));
                    label.setForeground(Color.WHITE);
                    label.setOpaque(true);
                }

                return label;
            case 1:
                RouteListingComponent panel = new RouteListingComponent();
                //column Tratta di percorrenza
                panel.composeIt((Route) value);
                panel.revalidate();
                panel.repaint();
                if (isSelected || hasFocus) {
                    panel.setBackground(new Color(176, 218, 255));
                    panel.setForeground(Color.WHITE);
                    panel.setOpaque(true);
                }

                return panel;
        }
        return null;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {

        return getTableCellRendererComponent(table, value, isSelected, false, row, column);
    }

    @Override
    public Object getCellEditorValue() {
        return null;
    }

    @Override
    public boolean isCellEditable(EventObject anEvent) {

        return true;
    }

    @Override
    public boolean shouldSelectCell(EventObject anEvent) {
        return true;
    }

    @Override
    public boolean stopCellEditing() {
        return true;
    }

    @Override
    public void cancelCellEditing() {

    }

    @Override
    public void addCellEditorListener(CellEditorListener l) {

    }

    @Override
    public void removeCellEditorListener(CellEditorListener l) {

    }
}
package it.unina.dblab.gui.body.routes;


import it.unina.dblab.models.Route;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.EventObject;

public class RoutesCellRenderer implements TableCellRenderer, TableCellEditor {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        switch (column) {

            case 0:
                //column ID
                JLabel label = new JLabel (value.toString());
                label.setBorder(new EmptyBorder(0, 5, 0, 5));//top,left,bottom,right
                label.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

                return label;
            case 1:
                //column Tratta di percorrenza
                JPanel panel = new RouteListingComponent((Route)value);

                return panel;
        }
        return null;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        return this.getTableCellRendererComponent(table, value, isSelected, false, row, column);
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
        return false;
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
package it.unina.dblab.gui.body.bookings;

import it.unina.dblab.models.SearchResult;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SearchResultListCellRenderer implements ListCellRenderer<List<SearchResult>> {

    @Override
    public Component getListCellRendererComponent(JList<? extends List<SearchResult>> list, List<SearchResult> value, int index, boolean isSelected, boolean cellHasFocus) {
        SearchResultComponent panel = new SearchResultComponent();
        //column Tratta di percorrenza
        panel.composeIt(value);

        if (isSelected || cellHasFocus) {
            panel.setBackground(new Color(176, 218, 255));
            panel.setForeground(Color.WHITE);
            panel.setOpaque(true);
        }

        return panel;
    }
}

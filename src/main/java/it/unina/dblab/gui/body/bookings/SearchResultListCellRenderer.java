package it.unina.dblab.gui.body.bookings;

import it.unina.dblab.models.SearchResult;
import it.unina.dblab.models.Station;
import it.unina.dblab.models.Train;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SearchResultListCellRenderer implements ListCellRenderer<List<SearchResult>> {

    private List<Station> stations;
    List<Train> trains;

    public SearchResultListCellRenderer(List<Station> stations, List<Train> trains) {
        this.stations = stations;
        this.trains = trains;
    }
    
    @Override
    public Component getListCellRendererComponent(JList<? extends List<SearchResult>> list, List<SearchResult> value, int index, boolean isSelected, boolean cellHasFocus) {
        SearchResultComponent panel = new SearchResultComponent(this.stations, this.trains);

        panel.composeIt(value);
        if (isSelected || cellHasFocus) {
            panel.setBackground(new Color(176, 218, 255));
            panel.setForeground(Color.WHITE);
            panel.setOpaque(true);
        }

        return panel;
    }
}

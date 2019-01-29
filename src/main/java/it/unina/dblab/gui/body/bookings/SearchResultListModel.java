package it.unina.dblab.gui.body.bookings;

import it.unina.dblab.gui.utility.DatabaseUtil;
import it.unina.dblab.models.SearchResult;
import it.unina.dblab.models.Station;
import it.unina.dblab.models.Train;

import javax.swing.*;
import java.util.Date;
import java.util.List;

public class SearchResultListModel extends AbstractListModel<List<SearchResult>> {
    private List<Train> trains = DatabaseUtil.listEntities(Train.class);
    private List<Station> stations = DatabaseUtil.listEntities(Station.class);

    private final String[] COLUMN_NAMES = {
            "Partenza", "Arrivo", "Durata", "Treno"
    };

    private List<List<SearchResult>> searchList;

    private JList list;

    public SearchResultListModel(JList list) {
        this.list = list;
    }

    public void search(Integer departureStationId, Integer arrivalStationId, Date startDate, Date endDate) {
        try {

            searchList = DatabaseUtil.searchBooking(departureStationId, arrivalStationId, startDate, endDate);
            list.revalidate();
            list.setListData(searchList.toArray());
            //this.fireContentsChanged();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public int getSize() {
        return searchList != null ? searchList.size() : 0;
    }

    @Override
    public List<SearchResult> getElementAt(int index) {
        return searchList.get(index);
    }
}

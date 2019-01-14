package it.unina.dblab.gui.body.routes.segmentsPanel;

import it.unina.dblab.gui.utility.DatabaseUtil;
import it.unina.dblab.models.Route;
import it.unina.dblab.models.Route2RouteSegment;
import it.unina.dblab.models.Route2RouteSegmentId;
import it.unina.dblab.models.RouteSegment;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class SegmentsPanel extends JPanel implements ActionListener, DocumentListener {
    private List<RouteSegment> segments = DatabaseUtil.listEntities(RouteSegment.class);

    private Route routeModel;

    private JTable segmentsTable;
    private JComboBox segmentCombobox;

    private JButton addSegmentButton;
    private JButton deleteSegmentButton;


    public SegmentsPanel(Route routeModel) {
        this.routeModel = routeModel;

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        Border border = BorderFactory.createTitledBorder("Lista dei segmenti");
        this.setBorder(border);

        segmentsTable = new JTable(new SegmentsTableModel(routeModel.getRouteSegments()));
        segmentsTable.setOpaque(false);
        segmentsTable.setPreferredScrollableViewportSize(new Dimension(860, 700));
        segmentsTable.setFillsViewportHeight(true);
        segmentsTable.setRowHeight(30);
        segmentsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        //column Segmento
        segmentCombobox = new JComboBox(segments.toArray());
        segmentCombobox.addActionListener(this);
        segmentCombobox.setRenderer(new ListCellRenderer<RouteSegment>() {
            @Override
            public Component getListCellRendererComponent(JList list, RouteSegment value, int index, boolean isSelected, boolean cellHasFocus) {
                String text = Optional.ofNullable(value)
                        .map(segment -> segment.getDepartureStation().getName() + " - " + segment.getArrivalStation().getName())
                        .orElse("");
                JLabel label = new JLabel(text);
                label.setOpaque(true);
                label.setBackground(Color.WHITE);

                if(isSelected) {
                    label.setBackground(new Color(176, 218, 255));
                }

                return label;
            }
        });
        TableColumn col = segmentsTable.getColumnModel().getColumn(0);
        col.setCellRenderer(new TableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                String text = Optional.ofNullable(value)
                        .map(obj -> (RouteSegment)obj)
                        .map(segment -> segment.getDepartureStation().getName() + " - " + segment.getArrivalStation().getName())
                        .orElse("");

                JLabel label = new JLabel(text);
                label.setOpaque(true);
                label.setBackground(Color.WHITE);

                if(isSelected) {
                    label.setBackground(new Color(176, 218, 255));
                }

                return label;
            }
        });
        col.setCellEditor(new DefaultCellEditor(segmentCombobox));


        //column Ferma in stazione?
        col = segmentsTable.getColumnModel().getColumn(1);
        col.setMaxWidth(220);
//
//        //column Sequenza
//        col = segmentsTable.getColumnModel().getColumn(2);
//        col.setMinWidth(100);
//        col.setMaxWidth(100);

        JScrollPane scrollPane = new JScrollPane(segmentsTable);
        this.add(scrollPane);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        addSegmentButton = new AddSegmentButton();
        addSegmentButton.addActionListener(this);
        buttons.add(addSegmentButton);

        deleteSegmentButton = new DeleteSegmentButton();
        deleteSegmentButton.addActionListener(this);
        buttons.add(deleteSegmentButton);

        this.add(buttons);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == addSegmentButton) {
            Route2RouteSegmentId id = new Route2RouteSegmentId();
            id.setRouteId(routeModel.getId());

            Route2RouteSegment newRecord = new Route2RouteSegment();
            newRecord.setId(id);
            newRecord.setPerformStop(true);
            newRecord.setRoute(routeModel);
            newRecord.setSequence(routeModel.getRouteSegments().size() + 1);

            routeModel.getRouteSegments().add(newRecord);
            segmentsTable.revalidate();
            segmentsTable.repaint();
        }
        else if(e.getSource() == deleteSegmentButton) {
            int selectedRow = segmentsTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Seleziona un segmento da eliminare!");
            }
            else {
                routeModel.getRouteSegments().remove(selectedRow);
                segmentsTable.clearSelection();
                segmentsTable.revalidate();
                segmentsTable.repaint();
            }
        }
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        Collections.sort(routeModel.getRouteSegments(), Comparator.comparing(el -> el.getSequence()));

        segmentsTable.revalidate();
        segmentsTable.repaint();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        Collections.sort(routeModel.getRouteSegments(), Comparator.comparing(el -> el.getSequence()));

        segmentsTable.revalidate();
        segmentsTable.repaint();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        Collections.sort(routeModel.getRouteSegments(), Comparator.comparing(el -> el.getSequence()));

        segmentsTable.revalidate();
        segmentsTable.repaint();
    }
}

package it.unina.dblab.gui.body.routes.segmentsPanel;

import it.unina.dblab.gui.body.routes.EditRouteDialog;
import it.unina.dblab.gui.utility.DatabaseUtil;
import it.unina.dblab.models.Route;
import it.unina.dblab.models.RouteSegment;
import it.unina.dblab.models.RouteSegmentId;
import it.unina.dblab.models.Segment;

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
    private List<Segment> segments = DatabaseUtil.listEntities(Segment.class);

    private EditRouteDialog editRouteDialog;
    private Route routeModel;

    private JTable segmentsTable;
    private JComboBox segmentCombobox;

    private JButton addSegmentButton;
    private JButton deleteSegmentButton;


    public SegmentsPanel(EditRouteDialog editRouteDialog) {
        this.editRouteDialog = editRouteDialog;
        this.routeModel = editRouteDialog.getRouteModel();

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        Border border = BorderFactory.createTitledBorder("Lista dei segmenti");
        this.setBorder(border);

        segmentsTable = new JTable(new SegmentsTableModel(routeModel.getRouteSegments()));
        segmentsTable.setOpaque(false);
        segmentsTable.setPreferredScrollableViewportSize(new Dimension(860, 600));
        segmentsTable.setFillsViewportHeight(true);
        segmentsTable.setRowHeight(30);
        segmentsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        //column Segmento
        segmentCombobox = new JComboBox(segments.toArray());
        segmentCombobox.addActionListener(this);
        segmentCombobox.setRenderer(new ListCellRenderer<Segment>() {
            @Override
            public Component getListCellRendererComponent(JList list, Segment value, int index, boolean isSelected, boolean cellHasFocus) {
                String text = Optional.ofNullable(value)
                        .map(segment -> segment.getDepartureStation().getName() + " - " + segment.getArrivalStation().getName())
                        .orElse("");
                JLabel label = new JLabel(text);
                label.setOpaque(true);
                label.setBackground(Color.WHITE);

                if (isSelected) {
                    label.setBackground(new Color(176, 218, 255));
                }

                return label;
            }
        });
        TableColumn col = segmentsTable.getColumnModel().getColumn(0);
        col.setCellRenderer((table, value, isSelected, hasFocus, row, column) -> {
            String text = Optional.ofNullable(value)
                    .map(obj -> (Segment) obj)
                    .map(segment -> segment.getDepartureStation().getName() + " - " + segment.getArrivalStation().getName())
                    .orElse("");

            JLabel label = new JLabel(text);
            label.setOpaque(true);
            label.setBackground(Color.WHITE);

            if (isSelected) {
                label.setBackground(new Color(176, 218, 255));
            }

            return label;
        });
        col.setCellEditor(new DefaultCellEditor(segmentCombobox));


        //column Sequenza
        col = segmentsTable.getColumnModel().getColumn(1);
        col.setMaxWidth(220);

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
        if (e.getSource() == addSegmentButton) {
            RouteSegmentId id = new RouteSegmentId();
            id.setRouteId(routeModel.getId());

            RouteSegment newRecord = new RouteSegment();
            newRecord.setId(id);
            newRecord.setRoute(routeModel);
            newRecord.setSequence(routeModel.getRouteSegments().size() + 1);

            routeModel.getRouteSegments().add(newRecord);
            segmentsTable.revalidate();
            segmentsTable.repaint();

        } else if (e.getSource() == deleteSegmentButton) {
            int selectedRow = segmentsTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Seleziona un segmento da eliminare!");
            } else {
                routeModel.getRouteSegments().remove(selectedRow);
                segmentsTable.clearSelection();
                segmentsTable.revalidate();
                segmentsTable.repaint();

            }
        }
        editRouteDialog.setModel();
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

    public JTable getSegmentsTable() {
        return this.segmentsTable;
    }
}

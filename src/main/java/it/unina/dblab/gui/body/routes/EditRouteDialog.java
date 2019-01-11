package it.unina.dblab.gui.body.routes;

import it.unina.dblab.HeavenRail;
import it.unina.dblab.gui.utility.DatabaseUtil;
import it.unina.dblab.models.Route;
import it.unina.dblab.models.Station;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.List;

public class EditRouteDialog extends JDialog implements FocusListener, ActionListener, ChangeListener {

    private List<Station> stations = DatabaseUtil.listEntities(Station.class);
    private Route routeModel;

    private JComboBox<Station> departureStationComboBox;
    private JComboBox<Station> arrivalStationComboBox;
    private JSpinner distanceSpinner;

    private JButton addButton;
    private JButton cancelButton;

    public EditRouteDialog(Route routeModel) {
        super(HeavenRail.getFrame(), (routeModel.getId() != null ? "Modifica " : "Aggiungi Nuovo ") + "Segmento");
        this.routeModel = routeModel.copy();
//
//        this.setModal(true);
//        this.setLocationRelativeTo(HeavenRail.getFrame());
//        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
//
//        //Create and populate the panel.
//        JPanel contentPanel = new JPanel(new SpringLayout());
//
//        //Stazione di partenza
//        JLabel departureStationLabel = new JLabel("Stazione di Partenza", JLabel.TRAILING);
//        contentPanel.add(departureStationLabel);
//
//        departureStationComboBox = new JComboBox<>(this.stations.toArray(new Station[0]));
//        departureStationComboBox.setSelectedItem(this.routeModel.getDepartureStation());
//        departureStationComboBox.setRenderer(new StationListCellRenderer());
//        departureStationComboBox.setBackground(Color.WHITE);
//        departureStationComboBox.addFocusListener(this);
//        departureStationComboBox.addActionListener(this);
//        departureStationLabel.setLabelFor(departureStationComboBox);
//        contentPanel.add(departureStationComboBox);
//
//
//        //Stazione di arrivo
//        JLabel arrivalStationLabel = new JLabel("Stazione di Arrivo", JLabel.TRAILING);
//        contentPanel.add(arrivalStationLabel);
//
//        arrivalStationComboBox = new JComboBox<>();
//        arrivalStationComboBox.setRenderer(new StationListCellRenderer());
//        arrivalStationComboBox.setBackground(Color.WHITE);
//        arrivalStationComboBox.addFocusListener(this);
//        arrivalStationComboBox.addActionListener(this);
//        arrivalStationLabel.setLabelFor(arrivalStationComboBox);
//        contentPanel.add(arrivalStationComboBox);
//
//
//        //Distanza
//        JLabel distanceLabel = new JLabel("Distanza", JLabel.TRAILING);
//        contentPanel.add(distanceLabel);
//
//        SpinnerModel distanceSpinnerModel = new SpinnerNumberModel((this.routeSegmentModel.getDistance() != null ? this.routeSegmentModel.getDistance().intValue() : 0), //initial value
//                0, //min
//                5000, //max
//                5);                //step
//        distanceSpinner = new JSpinner(distanceSpinnerModel);
//        distanceSpinner.addChangeListener(this);
//        JSpinner.DefaultEditor speedSpinnerEditor = (JSpinner.DefaultEditor) distanceSpinner.getEditor();
//        speedSpinnerEditor.getTextField().addFocusListener(this);
//        distanceLabel.setLabelFor(distanceSpinner);
//        contentPanel.add(distanceSpinner);
//
//
//        //Lay out the panel.
//        SpringUtilities.makeCompactGrid(contentPanel,
//                3, 2, //rows, cols
//                6, 6,        //initX, initY
//                6, 6);       //xPad, yPad
//
//        this.getContentPane().add(contentPanel);
//
//        JPanel buttonPanel = new JPanel();
//        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
//        addButton = new JButton(this.routeSegmentModel.getId() != null ? "Modifica" : "Aggiungi");
//        addButton.addActionListener(this);
//
//        cancelButton = new JButton("Annulla");
//        cancelButton.addActionListener(this);
//        buttonPanel.add(addButton);
//        buttonPanel.add(cancelButton);
//
//        buttonPanel.setLayout(new FlowLayout(FlowLayout.TRAILING));
//        this.getContentPane().add(buttonPanel);
//
//        this.setArrivalStationComboBoxModel();
//
//
//        arrivalStationComboBox.setSelectedItem(this.routeSegmentModel.getArrivalStation());
//
//        addButton.setEnabled(this.routeSegmentModel.getDepartureStation() != null &&
//                this.routeSegmentModel.getArrivalStation() != null &&
//                this.routeSegmentModel.getDistance() > 0);
    }

    @Override
    public void focusGained(FocusEvent e) {

    }

    @Override
    public void focusLost(FocusEvent e) {
        this.setModel();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
//
//
//        if(e.getSource() == departureStationComboBox) {
//            this.setArrivalStationComboBoxModel();
//        }
//
//        this.setModel();
//        if (e.getSource() == cancelButton || e.getSource() == addButton) {
//            if (e.getSource() == addButton) {
//                try {
//                    DatabaseUtil.mergeEntity(this.routeSegmentModel);
//                    this.setVisible(false);
//                }
//                catch (RollbackException rex) {
//                    String errorMessage = Optional.ofNullable(rex.getCause())
//                            .map(cause -> cause.getCause())
//                            .filter(deepCause -> deepCause instanceof ConstraintViolationException)
//                            .map(deepCause -> ((ConstraintViolationException)deepCause))
//                            .map(constraintViolation -> constraintViolation.getSQLException())
//                            .filter(sqlException -> sqlException != null)
//                            .map(sqlException -> sqlException.getMessage())
//                            .orElse("Impossibile effettuare l'operazione");
//                    JOptionPane.showMessageDialog(this, errorMessage, "Violazione del vincolo", JOptionPane.ERROR_MESSAGE);
//
//                }
//            }
//            else {
//                this.setVisible(false);
//            }
//        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        this.setModel();
    }

    private void setModel() {
//        this.routeSegmentModel.setDepartureStation((Station)departureStationComboBox.getSelectedItem());
//        this.routeSegmentModel.setArrivalStation((Station)arrivalStationComboBox.getSelectedItem());
//        this.routeSegmentModel.setDistance(Integer.valueOf(distanceSpinner.getValue().toString()));
//
//        addButton.setEnabled(this.routeSegmentModel.getDepartureStation() != null &&
//                this.routeSegmentModel.getArrivalStation() != null &&
//                this.routeSegmentModel.getDistance() > 0);
    }

    private void setArrivalStationComboBoxModel() {
//        //Filters out all the stations already set as arrival for that departure station
//        List<RouteSegment> routeSegments = DatabaseUtil.listEntities(RouteSegment.class);
//
//        List<Station> result = this.stations.stream()
//                //Filters out the selected station
//                .filter(station -> !station.equals(departureStationComboBox.getSelectedItem()))
//                //Filters out route segments having the same departure station as the one selected and arrival station already set
//                .filter(station -> routeSegments.stream()
//                        .noneMatch(routeSegment ->
//                                routeSegment.getDepartureStation().equals(departureStationComboBox.getSelectedItem()) &&
//                                        routeSegment.getArrivalStation().equals(station) &&
//                                        !routeSegment.equals(this.routeSegmentModel)))
//                .collect(Collectors.toList());
//
//        arrivalStationComboBox.setModel(new DefaultComboBoxModel<>(result.toArray(new Station[0])));

    }
}

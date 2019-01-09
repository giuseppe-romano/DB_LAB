package it.unina.dblab.gui.body.routes;

import it.unina.dblab.HeavenRail;
import it.unina.dblab.gui.utility.DatabaseUtil;
import it.unina.dblab.models.RouteSegment;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class EditRouteSegmentDialog extends JDialog implements FocusListener, ActionListener {

    private RouteSegment routeSegmentModel;

    private JComboBox departureStationComboBox;

    private JButton addButton;
    private JButton cancelButton;

    public EditRouteSegmentDialog(RouteSegment routeSegmentModel) {
        super(HeavenRail.getFrame(), (routeSegmentModel.getId() != null ? "Modifica " : "Aggiungi Nuovo ") + "Segmento");
        this.routeSegmentModel = routeSegmentModel;
//
//        this.setModal(true);
//        this.setLocationRelativeTo(HeavenRail.getFrame());
//        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
//
//        //Create and populate the panel.
//        JPanel contentPanel = new JPanel(new SpringLayout());
//
//        //Categoria
//        JLabel departureStationLabel = new JLabel("Stazione di Partenza", JLabel.TRAILING);
//        contentPanel.add(departureStationLabel);
//
//        departureStationComboBox = new JComboBox(new String[]{"", "Frecciarossa", "Frecciarossa 1000", "Frecciargento", "Frecciabianca", "Intercity", "Regionale Veloce"});
//        departureStationComboBox.setSelectedItem(this.routeSegmentModel.getCategory() != null ? this.routeSegmentModel.getCategory() : "");
//        departureStationComboBox.addFocusListener(this);
//        departureStationLabel.setLabelFor(departureStationComboBox);
//        contentPanel.add(departureStationComboBox);
//
//
//        //Codice (Acronimo)
//        JLabel acronymLabel = new JLabel("Codice", JLabel.TRAILING);
//        contentPanel.add(acronymLabel);
//
//        acronymTextField = new JTextField(10);
//        acronymTextField.setText(this.trainModel.getCode());
//        acronymTextField.addFocusListener(this);
//
//
//        acronymLabel.setLabelFor(acronymTextField);
//        contentPanel.add(acronymTextField);
//
//        //Velocita Nominale
//        JLabel speedLabel = new JLabel("Velocita' Nominale", JLabel.TRAILING);
//        contentPanel.add(speedLabel);
//
//        SpinnerModel speedSpinnerModel = new SpinnerNumberModel((this.trainModel.getNominalSpeed() != null ? this.trainModel.getNominalSpeed().intValue() : 0), //initial value
//                0, //min
//                500, //max
//                5);                //step
//        speedSpinner = new JSpinner(speedSpinnerModel);
//        JSpinner.DefaultEditor speedSpinnerEditor = (JSpinner.DefaultEditor) speedSpinner.getEditor();
//        speedSpinnerEditor.getTextField().addFocusListener(this);
//        speedLabel.setLabelFor(speedSpinner);
//        contentPanel.add(speedSpinner);
//
//
//        //Numero carrozze
//        JLabel carriageLabel = new JLabel("N. Carrozze", JLabel.TRAILING);
//        contentPanel.add(carriageLabel);
//
//        SpinnerModel carriageSpinnerModel = new SpinnerNumberModel((this.trainModel.getCarriages() != null ? this.trainModel.getCarriages().intValue() : 1), //initial value
//                1, //min
//                50, //max
//                1);                //step
//        carriageSpinner = new JSpinner(carriageSpinnerModel);
//        JSpinner.DefaultEditor carriageSpinnerEditor = (JSpinner.DefaultEditor) carriageSpinner.getEditor();
//        carriageSpinnerEditor.getTextField().addFocusListener(this);
//        carriageLabel.setLabelFor(carriageSpinner);
//        contentPanel.add(carriageSpinner);
//
//
//        //Lay out the panel.
//        SpringUtilities.makeCompactGrid(contentPanel,
//                4, 2, //rows, cols
//                6, 6,        //initX, initY
//                6, 6);       //xPad, yPad
//
//        this.getContentPane().add(contentPanel);
//
//        JPanel buttonPanel = new JPanel();
//        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
//        addButton = new JButton(this.trainModel.getId() > 0 ? "Modifica" : "Aggiungi");
//        addButton.setEnabled(!this.trainModel.getCategory().isEmpty() &&
//                !this.trainModel.getCode().isEmpty() &&
//                this.trainModel.getNominalSpeed() > 0 &&
//                this.trainModel.getCarriages() > 0);
//
//        addButton.addActionListener(this);
//        cancelButton = new JButton("Annulla");
//        cancelButton.addActionListener(this);
//        buttonPanel.add(addButton);
//        buttonPanel.add(cancelButton);
//
//        buttonPanel.setLayout(new FlowLayout(FlowLayout.TRAILING));
//        this.getContentPane().add(buttonPanel);
    }

    @Override
    public void focusGained(FocusEvent e) {

    }

    @Override
    public void focusLost(FocusEvent e) {
//        this.stationModel.setName(nameTextField.getText());
//        this.stationModel.setAddress(addressTextField.getText());
//        this.stationModel.setTelephone(telephoneTextField.getText());
//        this.stationModel.setNumberOfPlatforms(Integer.valueOf(platformSpinner.getValue().toString()));
//        this.stationModel.setDisabledAccess(disabledCheckBox.isSelected());
//        this.stationModel.setRestaurant(restaurantCheckBox.isSelected());
//        this.stationModel.setTaxiService(taxiCheckBox.isSelected());
//
//        addButton.setEnabled(!this.stationModel.getName().isEmpty() &&
//                !this.stationModel.getAddress().isEmpty() &&
//                !this.stationModel.getTelephone().isEmpty() &&
//                this.stationModel.getNumberOfPlatforms() > 0);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            DatabaseUtil.mergeEntity(this.routeSegmentModel);
        }
        this.setVisible(false);
    }
}

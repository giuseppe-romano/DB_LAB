package it.unina.dblab.gui.body.stations;

import it.unina.dblab.HeavenRail;
import it.unina.dblab.gui.utility.DatabaseUtil;
import it.unina.dblab.gui.utility.SpringUtilities;
import it.unina.dblab.models.Station;
import org.hibernate.exception.ConstraintViolationException;

import javax.persistence.RollbackException;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Optional;

public class EditStationDialog extends JDialog implements FocusListener, ActionListener, ChangeListener, DocumentListener {

    private Station stationModel;

    private JTextField nameTextField;
    private JTextField addressTextField;
    private JTextField telephoneTextField;
    private JSpinner platformSpinner;

    private JCheckBox disabledCheckBox;
    private JCheckBox restaurantCheckBox;
    private JCheckBox taxiCheckBox;

    private JButton addButton;
    private JButton cancelButton;

    public EditStationDialog(Station stationModel) {
        super(HeavenRail.getFrame(), (stationModel.getId() != null ? "Modifica " : "Aggiungi Nuova ") + "Stazione");
        this.stationModel = stationModel.copy();

        this.setModal(true);
        this.setLocationRelativeTo(HeavenRail.getFrame());
        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));

        //Create and populate the panel.
        JPanel contentPanel = new JPanel(new SpringLayout());

        //Nome
        JLabel nameLabel = new JLabel("Nome", JLabel.TRAILING);
        contentPanel.add(nameLabel);

        nameTextField = new JTextField(10);
        nameTextField.setText(this.stationModel.getName());
        nameTextField.addFocusListener(this);
        nameTextField.getDocument().addDocumentListener(this);

        nameLabel.setLabelFor(nameTextField);
        contentPanel.add(nameTextField);


        //Indirizzo
        JLabel addressLabel = new JLabel("Indirizzo", JLabel.TRAILING);
        contentPanel.add(addressLabel);

        addressTextField = new JTextField(10);
        addressTextField.setText(this.stationModel.getAddress());
        addressTextField.addFocusListener(this);
        addressTextField.getDocument().addDocumentListener(this);

        addressLabel.setLabelFor(addressTextField);
        contentPanel.add(addressTextField);


        //Telefono
        JLabel telephoneLabel = new JLabel("Telefono", JLabel.TRAILING);
        contentPanel.add(telephoneLabel);

        telephoneTextField = new JTextField(10);
        telephoneTextField.setText(this.stationModel.getTelephone());
        telephoneTextField.addFocusListener(this);
        telephoneTextField.getDocument().addDocumentListener(this);

        telephoneLabel.setLabelFor(telephoneTextField);
        contentPanel.add(telephoneTextField);


        //Numero piattaforme
        JLabel carriageLabel = new JLabel("N. Piattaforme", JLabel.TRAILING);
        contentPanel.add(carriageLabel);

        SpinnerModel platformSpinnerModel = new SpinnerNumberModel((this.stationModel.getNumberOfPlatforms() != null ? this.stationModel.getNumberOfPlatforms() : 1), //initial value
                1, //min
                99, //max
                1);                //step
        platformSpinner = new JSpinner(platformSpinnerModel);
        platformSpinner.addChangeListener(this);
        JSpinner.DefaultEditor platformSpinnerEditor = (JSpinner.DefaultEditor) platformSpinner.getEditor();
        platformSpinnerEditor.getTextField().addFocusListener(this);
        carriageLabel.setLabelFor(platformSpinner);
        contentPanel.add(platformSpinner);


        //Lay out the panel.
        SpringUtilities.makeCompactGrid(contentPanel,
                4, 2, //rows, cols
                6, 6,        //initX, initY
                6, 6);       //xPad, yPad

        this.getContentPane().add(contentPanel);

        JPanel servicesPanel = new JPanel(new GridLayout(0, 1));
        Border border = BorderFactory.createTitledBorder("Servizi in stazione");
        servicesPanel.setBorder(border);
        disabledCheckBox = new JCheckBox("Accesso per Disabili");
        disabledCheckBox.setSelected(this.stationModel.getDisabledAccess());
        disabledCheckBox.addFocusListener(this);
        disabledCheckBox.addActionListener(this);
        servicesPanel.add(disabledCheckBox);
        restaurantCheckBox = new JCheckBox("Servizio Ristorante");
        restaurantCheckBox.setSelected(this.stationModel.getRestaurant());
        restaurantCheckBox.addFocusListener(this);
        restaurantCheckBox.addActionListener(this);
        servicesPanel.add(restaurantCheckBox);
        taxiCheckBox = new JCheckBox("Servizio Taxi");
        taxiCheckBox.setSelected(this.stationModel.getTaxiService());
        taxiCheckBox.addFocusListener(this);
        taxiCheckBox.addActionListener(this);
        servicesPanel.add(taxiCheckBox);

        this.getContentPane().add(servicesPanel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
        addButton = new JButton(this.stationModel.getId() != null ? "Modifica" : "Aggiungi");

        addButton.addActionListener(this);
        cancelButton = new JButton("Annulla");
        cancelButton.addActionListener(this);
        buttonPanel.add(addButton);
        buttonPanel.add(cancelButton);

        buttonPanel.setLayout(new FlowLayout(FlowLayout.TRAILING));
        this.getContentPane().add(buttonPanel);

        this.setModel();
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
        this.setModel();

        if (e.getSource() == cancelButton || e.getSource() == addButton) {
            if (e.getSource() == addButton) {
                try {
                    DatabaseUtil.mergeEntity(this.stationModel);
                    this.setVisible(false);
                }
                catch (RollbackException rex) {
                    String errorMessage = Optional.ofNullable(rex.getCause())
                            .map(cause -> cause.getCause())
                            .filter(deepCause -> deepCause instanceof ConstraintViolationException)
                            .map(deepCause -> ((ConstraintViolationException)deepCause))
                            .map(constraintViolation -> constraintViolation.getSQLException())
                            .filter(sqlException -> sqlException != null)
                            .map(sqlException -> sqlException.getMessage())
                            .orElse("Impossibile effettuare l'operazione");
                    JOptionPane.showMessageDialog(this, errorMessage, "Violazione del vincolo", JOptionPane.ERROR_MESSAGE);

                }
            }
            else {
                this.setVisible(false);
            }
        }
    }


    @Override
    public void insertUpdate(DocumentEvent e) {
        this.setModel();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        this.setModel();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        this.setModel();
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        this.setModel();
    }

    private void setModel() {
        this.stationModel.setName(nameTextField.getText());
        this.stationModel.setAddress(addressTextField.getText());
        this.stationModel.setTelephone(telephoneTextField.getText());
        this.stationModel.setNumberOfPlatforms(Integer.valueOf(platformSpinner.getValue().toString()));
        this.stationModel.setDisabledAccess(disabledCheckBox.isSelected());
        this.stationModel.setRestaurant(restaurantCheckBox.isSelected());
        this.stationModel.setTaxiService(taxiCheckBox.isSelected());

        addButton.setEnabled(!this.stationModel.getName().isEmpty() &&
                !this.stationModel.getAddress().isEmpty() &&
                !this.stationModel.getTelephone().isEmpty() &&
                this.stationModel.getNumberOfPlatforms() > 0);
    }

}

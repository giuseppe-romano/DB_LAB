package it.unina.dblab.gui.body.trains;

import it.unina.dblab.HeavenRail;
import it.unina.dblab.gui.utility.DatabaseUtil;
import it.unina.dblab.gui.utility.SpringUtilities;
import it.unina.dblab.models.Train;
import org.hibernate.exception.ConstraintViolationException;

import javax.persistence.RollbackException;
import javax.swing.*;
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

public class EditTrainDialog extends JDialog implements FocusListener, ActionListener, ChangeListener, DocumentListener {

    private Train trainModel;

    private JComboBox categoryComboBox;
    private JTextField acronymTextField;
    private JSpinner speedSpinner;
    private JSpinner carriageSpinner;

    JButton addButton;
    JButton cancelButton;

    public EditTrainDialog(Train trainModel) {
        super(HeavenRail.getFrame(), (trainModel.getId() != null ? "Modifica " : "Aggiungi Nuovo ") + "Treno");
        this.trainModel = trainModel.copy();

        this.setModal(true);
        this.setLocationRelativeTo(HeavenRail.getFrame());
        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));

        //Create and populate the panel.
        JPanel contentPanel = new JPanel(new SpringLayout());

        //Categoria
        JLabel categoryLabel = new JLabel("Categoria", JLabel.TRAILING);
        contentPanel.add(categoryLabel);

        categoryComboBox = new JComboBox(new String[]{"", "Frecciarossa", "Frecciarossa 1000", "Frecciargento", "Frecciabianca", "Intercity", "Regionale Veloce"});
        categoryComboBox.setSelectedItem(this.trainModel.getCategory() != null ? this.trainModel.getCategory() : "");
        categoryComboBox.setBackground(Color.WHITE);
        categoryComboBox.addFocusListener(this);
        categoryComboBox.addActionListener(this);
        categoryLabel.setLabelFor(categoryComboBox);
        contentPanel.add(categoryComboBox);


        //Codice (Acronimo)
        JLabel acronymLabel = new JLabel("Codice", JLabel.TRAILING);
        contentPanel.add(acronymLabel);

        acronymTextField = new JTextField(10);
        acronymTextField.setText(this.trainModel.getCode());
        acronymTextField.addFocusListener(this);
        acronymTextField.getDocument().addDocumentListener(this);


        acronymLabel.setLabelFor(acronymTextField);
        contentPanel.add(acronymTextField);

        //Velocita Nominale
        JLabel speedLabel = new JLabel("Velocita' Nominale", JLabel.TRAILING);
        contentPanel.add(speedLabel);

        SpinnerModel speedSpinnerModel = new SpinnerNumberModel((this.trainModel.getNominalSpeed() != null ? this.trainModel.getNominalSpeed().intValue() : 0), //initial value
                0, //min
                500, //max
                5);                //step
        speedSpinner = new JSpinner(speedSpinnerModel);
        speedSpinner.addChangeListener(this);
        JSpinner.DefaultEditor speedSpinnerEditor = (JSpinner.DefaultEditor) speedSpinner.getEditor();
        speedSpinnerEditor.getTextField().addFocusListener(this);
        speedLabel.setLabelFor(speedSpinner);
        contentPanel.add(speedSpinner);


        //Numero carrozze
        JLabel carriageLabel = new JLabel("N. Carrozze", JLabel.TRAILING);
        contentPanel.add(carriageLabel);

        SpinnerModel carriageSpinnerModel = new SpinnerNumberModel((this.trainModel.getCarriages() != null ? this.trainModel.getCarriages().intValue() : 1), //initial value
                1, //min
                50, //max
                1);                //step
        carriageSpinner = new JSpinner(carriageSpinnerModel);
        carriageSpinner.addChangeListener(this);
        JSpinner.DefaultEditor carriageSpinnerEditor = (JSpinner.DefaultEditor) carriageSpinner.getEditor();
        carriageSpinnerEditor.getTextField().addFocusListener(this);
        carriageLabel.setLabelFor(carriageSpinner);
        contentPanel.add(carriageSpinner);


        //Lay out the panel.
        SpringUtilities.makeCompactGrid(contentPanel,
                4, 2, //rows, cols
                6, 6,        //initX, initY
                6, 6);       //xPad, yPad

        this.getContentPane().add(contentPanel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
        addButton = new JButton(this.trainModel.getId() != null ? "Modifica" : "Aggiungi");
        addButton.setEnabled(!this.trainModel.getCategory().isEmpty() &&
                !this.trainModel.getCode().isEmpty() &&
                this.trainModel.getNominalSpeed() > 0 &&
                this.trainModel.getCarriages() > 0);

        addButton.addActionListener(this);
        cancelButton = new JButton("Annulla");
        cancelButton.addActionListener(this);
        buttonPanel.add(addButton);
        buttonPanel.add(cancelButton);

        buttonPanel.setLayout(new FlowLayout(FlowLayout.TRAILING));
        this.getContentPane().add(buttonPanel);
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
                    DatabaseUtil.mergeEntity(this.trainModel);
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
        this.trainModel.setCategory(categoryComboBox.getSelectedItem().toString());
        this.trainModel.setCode(acronymTextField.getText());
        this.trainModel.setNominalSpeed(Integer.valueOf(speedSpinner.getValue().toString()));
        this.trainModel.setCarriages(Integer.valueOf(carriageSpinner.getValue().toString()));

        addButton.setEnabled(!this.trainModel.getCategory().isEmpty() &&
                !this.trainModel.getCode().isEmpty() &&
                this.trainModel.getNominalSpeed() > 0 &&
                this.trainModel.getCarriages() > 0);
    }
}

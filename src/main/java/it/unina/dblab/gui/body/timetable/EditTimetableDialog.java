package it.unina.dblab.gui.body.timetable;

import it.unina.dblab.HeavenRail;
import it.unina.dblab.gui.utility.DatabaseUtil;
import it.unina.dblab.gui.utility.SpringUtilities;
import it.unina.dblab.models.Route;
import it.unina.dblab.models.Timetable;
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
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class EditTimetableDialog extends JDialog implements FocusListener, ActionListener, ChangeListener, DocumentListener {

    private List<Train> trains = DatabaseUtil.listEntities(Train.class);
    private List<Route> routes = DatabaseUtil.listEntities(Route.class);

    private Timetable timetableModel;

    private JComboBox<Train> trainComboBox;
    private JComboBox<Route> routeComboBox;
    private JFormattedTextField departureDateTextField;
    private JSpinner departurePlatformSpinner;
    private JSpinner arrivalPlatformSpinner;

    private JComboBox<String> optionComboBox;

    private JButton addButton;
    private JButton cancelButton;

    public EditTimetableDialog(Timetable timetableModel) {
        super(HeavenRail.getFrame(), (timetableModel.getId() != null ? "Modifica " : "Aggiungi Nuova ") + "Tratta di Percorrenza");
        this.timetableModel = timetableModel.copy();

        this.setModal(true);
        this.setLocationRelativeTo(HeavenRail.getFrame());
        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));

        //Create and populate the panel.
        JPanel contentPanel = new JPanel(new SpringLayout());

        //Treno
        JLabel trainLabel = new JLabel("Treno", JLabel.TRAILING);
        contentPanel.add(trainLabel);

        trainComboBox = new JComboBox<>(this.trains.toArray(new Train[0]));
        trainComboBox.setSelectedItem(this.timetableModel.getTrain());
        trainComboBox.setRenderer(new TrainListCellRenderer());
        trainComboBox.setBackground(Color.WHITE);
        trainComboBox.addFocusListener(this);
        trainComboBox.addActionListener(this);
        trainLabel.setLabelFor(trainComboBox);
        contentPanel.add(trainComboBox);


        //Tratta di percorrenza
        JLabel routeLabel = new JLabel("Tratta di Percorrenza", JLabel.TRAILING);
        contentPanel.add(routeLabel);

        routeComboBox = new JComboBox<>(this.routes.toArray(new Route[0]));
        routeComboBox.setSelectedItem(this.timetableModel.getRoute());
        routeComboBox.setRenderer(new RouteListCellRenderer());
        routeComboBox.setBackground(Color.WHITE);
        routeComboBox.addFocusListener(this);
        routeComboBox.addActionListener(this);
        routeLabel.setLabelFor(routeComboBox);
        contentPanel.add(routeComboBox);

        //Orario di partenza
        JLabel departureDateLabel = new JLabel("Orario di Partenza", JLabel.TRAILING);
        contentPanel.add(departureDateLabel);

        departureDateTextField = new JFormattedTextField(new SimpleDateFormat("dd/MM/yyyy HH:mm"));
        departureDateTextField.setValue(this.timetableModel.getScheduledDate());
        departureDateTextField.addFocusListener(this);
        departureDateTextField.getDocument().addDocumentListener(this);
        departureDateTextField.addPropertyChangeListener("value", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                setModel();
            }
        });
        departureDateLabel.setLabelFor(departureDateTextField);
        contentPanel.add(departureDateTextField);

        //Binario di partenza
        JLabel departurePlatformLabel = new JLabel("Binario di Partenza", JLabel.TRAILING);
        contentPanel.add(departurePlatformLabel);

        SpinnerModel departurePlatformSpinnerModel = new SpinnerNumberModel((this.timetableModel.getDeparturePlatform() != null ? this.timetableModel.getDeparturePlatform().intValue() : 0), //initial value
                0, //min
                25, //max
                1);                //step
        departurePlatformSpinner = new JSpinner(departurePlatformSpinnerModel);
        departurePlatformSpinner.addChangeListener(this);
        JSpinner.DefaultEditor departurePlatformSpinnerEditor = (JSpinner.DefaultEditor) departurePlatformSpinner.getEditor();
        departurePlatformSpinnerEditor.getTextField().addFocusListener(this);
        departurePlatformLabel.setLabelFor(departurePlatformSpinner);
        contentPanel.add(departurePlatformSpinner);

        //Binario di arrivo
        JLabel arrivalPlatformLabel = new JLabel("Binario di Arrivo", JLabel.TRAILING);
        contentPanel.add(arrivalPlatformLabel);

        SpinnerModel arrivalPlatformSpinnerModel = new SpinnerNumberModel((this.timetableModel.getArrivalPlatform() != null ? this.timetableModel.getArrivalPlatform().intValue() : 0), //initial value
                0, //min
                25, //max
                1);                //step
        arrivalPlatformSpinner = new JSpinner(arrivalPlatformSpinnerModel);
        arrivalPlatformSpinner.addChangeListener(this);
        JSpinner.DefaultEditor arrivalPlatformSpinnerEditor = (JSpinner.DefaultEditor) arrivalPlatformSpinner.getEditor();
        arrivalPlatformSpinnerEditor.getTextField().addFocusListener(this);
        arrivalPlatformLabel.setLabelFor(arrivalPlatformSpinner);
        contentPanel.add(arrivalPlatformSpinner);

        int layoutRows = 5;
        //Solo se nuovo record
        if (this.timetableModel.getId() == null) {
            //Opzione di ricorrenza settimanale
            JLabel optionLabel = new JLabel("Ripeti per", JLabel.TRAILING);
            contentPanel.add(optionLabel);

            optionComboBox = new JComboBox<>(new String[]{"Solo la data specificata", "Ogni giorno della settimana", "Una volta a settimana", "Una volta al mese"});
            optionComboBox.setSelectedIndex(0);
            optionComboBox.setBackground(Color.WHITE);
            optionComboBox.addFocusListener(this);
            optionComboBox.addActionListener(this);
            optionLabel.setLabelFor(optionComboBox);
            contentPanel.add(optionComboBox);

            layoutRows = 6;
        }
        //Lay out the panel.
        SpringUtilities.makeCompactGrid(contentPanel,
                layoutRows, 2, //rows, cols
                6, 6,        //initX, initY
                6, 6);       //xPad, yPad

        this.getContentPane().add(contentPanel);


        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
        addButton = new JButton(this.timetableModel.getId() != null ? "Modifica" : "Aggiungi");
        addButton.addActionListener(this);

        cancelButton = new JButton("Annulla");
        cancelButton.addActionListener(this);
        buttonPanel.add(addButton);
        buttonPanel.add(cancelButton);

        buttonPanel.setLayout(new FlowLayout(FlowLayout.TRAILING));
        this.getContentPane().add(buttonPanel);

        addButton.setEnabled(this.timetableModel.getTrain() != null &&
                this.timetableModel.getRoute() != null &&
                this.timetableModel.getScheduledDate() != null &&
                this.timetableModel.getDeparturePlatform() > 0 &&
                this.timetableModel.getArrivalPlatform() > 0);
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

                    DatabaseUtil.mergeEntity(this.timetableModel);

                    this.setVisible(false);
                } catch (RollbackException rex) {
                    String errorMessage = Optional.ofNullable(rex.getCause())
                            .map(cause -> cause.getCause())
                            .filter(deepCause -> deepCause instanceof ConstraintViolationException)
                            .map(deepCause -> ((ConstraintViolationException) deepCause))
                            .map(constraintViolation -> constraintViolation.getSQLException())
                            .filter(sqlException -> sqlException != null)
                            .map(sqlException -> sqlException.getMessage())
                            .orElse("Impossibile effettuare l'operazione");
                    JOptionPane.showMessageDialog(this, errorMessage, "Violazione del vincolo", JOptionPane.ERROR_MESSAGE);

                }
            } else {
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

    public void setModel() {
        System.out.println("departureDateTextField.getValue() " + departureDateTextField.getValue());
        this.timetableModel.setTrain((Train) trainComboBox.getSelectedItem());
        this.timetableModel.setRoute((Route) routeComboBox.getSelectedItem());
        this.timetableModel.setScheduledDate((Date) departureDateTextField.getValue());
        this.timetableModel.setDeparturePlatform(Integer.valueOf(departurePlatformSpinner.getValue().toString()));
        this.timetableModel.setArrivalPlatform(Integer.valueOf(arrivalPlatformSpinner.getValue().toString()));

        addButton.setEnabled(this.timetableModel.getTrain() != null &&
                this.timetableModel.getRoute() != null &&
                this.timetableModel.getScheduledDate() != null &&
                this.timetableModel.getDeparturePlatform() > 0 &&
                this.timetableModel.getArrivalPlatform() > 0);
    }

    public Timetable getTimetableModel() {
        return timetableModel;
    }

}

package it.unina.dblab.gui.body.routes;

import it.unina.dblab.HeavenRail;
import it.unina.dblab.gui.body.routes.segmentsPanel.SegmentsPanel;
import it.unina.dblab.gui.utility.DatabaseUtil;
import it.unina.dblab.gui.utility.SpringUtilities;
import it.unina.dblab.models.Route;
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

public class EditRouteDialog extends JDialog implements FocusListener, ActionListener, ChangeListener, DocumentListener {

    private Route routeModel;

    private JTextField nameTextField;

    private JButton addButton;
    private JButton cancelButton;

    public EditRouteDialog(Route routeModel) {
        super(HeavenRail.getFrame(), (routeModel.getId() != null ? "Modifica " : "Aggiungi Nuova ") + "Tratta di Percorrenza");
        this.routeModel = routeModel.copy();

        this.setModal(true);
        this.setLocationRelativeTo(HeavenRail.getFrame());
        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));

        //Create and populate the panel.
        JPanel contentPanel = new JPanel(new SpringLayout());

        //Nome mnemonico della tratta
        JLabel nameLabel = new JLabel("Nome", JLabel.TRAILING);
        contentPanel.add(nameLabel);

        nameTextField = new JTextField(10);
        nameTextField.setText(this.routeModel.getName());
        nameTextField.addFocusListener(this);
        nameTextField.getDocument().addDocumentListener(this);
        contentPanel.add(nameTextField);

        //Lay out the panel.
        SpringUtilities.makeCompactGrid(contentPanel,
                1, 2, //rows, cols
                6, 6,        //initX, initY
                6, 6);       //xPad, yPad

        this.getContentPane().add(contentPanel);


        SegmentsPanel segmentsPanel = new SegmentsPanel(this);
        segmentsPanel.addFocusListener(this);
        this.getContentPane().add(segmentsPanel);


        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
        addButton = new JButton(this.routeModel.getId() != null ? "Modifica" : "Aggiungi");
        addButton.addActionListener(this);

        cancelButton = new JButton("Annulla");
        cancelButton.addActionListener(this);
        buttonPanel.add(addButton);
        buttonPanel.add(cancelButton);

        buttonPanel.setLayout(new FlowLayout(FlowLayout.TRAILING));
        this.getContentPane().add(buttonPanel);

        addButton.setEnabled(this.routeModel.getName() != null &&
                this.routeModel.getRouteSegments() != null && this.routeModel.getRouteSegments().size() > 0);
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
                    //When is a new entity
                    if (this.routeModel.getId() == null) {
                        Route entity = new Route();
                        entity.setName(this.routeModel.getName());
                        entity.setActive(true);
                        entity = DatabaseUtil.mergeEntity(entity);

                        this.routeModel.setId(entity.getId());

                        this.routeModel.getRouteSegments()
                                .forEach(routeSegment -> routeSegment.getId().setRouteId(routeModel.getId()));
                    }

                    checkSegmentLinking(this.routeModel);

                    DatabaseUtil.mergeEntity(this.routeModel);

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
        this.routeModel.setName(nameTextField.getText());

        addButton.setEnabled(this.routeModel.getName() != null &&
                this.routeModel.getRouteSegments() != null &&
                this.routeModel.getRouteSegments().size() > 0 &&
                this.routeModel.getRouteSegments()
                        .stream()
                        .noneMatch(routeSegment -> routeSegment.getSegment() == null));
    }

    public Route getRouteModel() {
        return routeModel;
    }


    private void checkSegmentLinking(Route routeModel) {
        routeModel.setActive(true);

        Integer arrivalStationId = routeModel.getRouteSegments().get(0).getSegment().getArrivalStation().getId();
        routeModel.getRouteSegments().get(0).setSequence(1);
        routeModel.getRouteSegments().get(0).setTerminal(false);
        for(int i = 1; i < routeModel.getRouteSegments().size(); i++) {
            Integer nextDepartureStationId = routeModel.getRouteSegments().get(i).getSegment().getDepartureStation().getId();
            if(!arrivalStationId.equals(nextDepartureStationId)) {
                routeModel.setActive(false);
                break;
            }
            routeModel.getRouteSegments().get(i).setSequence(i + 1);
            routeModel.getRouteSegments().get(i).setTerminal(false);
            arrivalStationId = routeModel.getRouteSegments().get(i).getSegment().getArrivalStation().getId();
        }
        //Force to stop at the end
        routeModel.getRouteSegments().get(routeModel.getRouteSegments().size() - 1).setTerminal(true);
    }
}

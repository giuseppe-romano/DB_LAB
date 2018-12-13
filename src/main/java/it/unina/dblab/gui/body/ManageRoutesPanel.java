package it.unina.dblab.gui.body;

import javax.swing.*;
import java.awt.*;

public class ManageRoutesPanel extends JPanel {
    public static final String NAME = "MANAGE_ROUTES";

    private BodyContainer parent;

    public ManageRoutesPanel(BodyContainer parent) {
        this.parent = parent;

        this.setBackground(Color.WHITE);

        this.add(new JLabel("Trains"));
    }
}

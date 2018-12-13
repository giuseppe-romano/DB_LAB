package it.unina.dblab.gui.body;

import javax.swing.*;
import java.awt.*;

public class ManageStationsPanel extends JPanel {
    public static final String NAME = "MANAGE_STATIONS";

    private BodyContainer parent;

    public ManageStationsPanel(BodyContainer parent) {
        this.parent = parent;

        this.setBackground(Color.WHITE);

        this.add(new JLabel("Stations"));
    }
}

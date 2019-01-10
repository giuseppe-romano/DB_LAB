package it.unina.dblab.gui.body;

import it.unina.dblab.HeavenRail;

import javax.swing.*;
import java.awt.*;

public class BodyContainer extends JPanel {

    private HeavenRail parent;

    public BodyContainer(HeavenRail parent) {
        this.parent = parent;

        this.setLayout(new CardLayout());
        this.setBackground(Color.WHITE);


        this.add(ManageTrainsPanel.NAME, new ManageTrainsPanel(this));
        this.add(ManageStationsPanel.NAME, new ManageStationsPanel(this));
        this.add(ManageRouteSegmentsPanel.NAME, new ManageRouteSegmentsPanel(this));
        this.add(ManageRoutesPanel.NAME, new ManageRoutesPanel(this));
    }

}

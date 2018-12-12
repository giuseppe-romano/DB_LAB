package it.unina.dblab.gui.footer;

import it.unina.dblab.HeavenRail;

import javax.swing.*;
import java.awt.*;

public class FooterContainer extends JPanel {

    private HeavenRail parent;

    public FooterContainer(HeavenRail parent) {
        this.parent = parent;

        this.setBackground(Color.DARK_GRAY);
        this.setPreferredSize(new Dimension(100, 30));
    }
}

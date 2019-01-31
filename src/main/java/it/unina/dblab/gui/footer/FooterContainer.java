package it.unina.dblab.gui.footer;

import it.unina.dblab.HeavenRail;

import javax.swing.*;
import java.awt.*;

public class FooterContainer extends JPanel {

    private HeavenRail parent;

    public FooterContainer(HeavenRail parent) {
        this.parent = parent;

        this.setBackground(new Color(7, 51, 147));
        this.setPreferredSize(new Dimension(100, 30));

        JLabel label = new JLabel("PROGETTO LABORATORIO BASI DI DATI ~ Giuseppe Romano (matr. 566/1739)");
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Candara", Font.BOLD, 16));
        this.add(label);
    }
}

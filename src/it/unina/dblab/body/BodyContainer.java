package it.unina.dblab.body;

import javax.swing.*;
import java.awt.*;

public class BodyContainer extends JPanel {

    public BodyContainer() {
     //   this.setLayout(new SpringLayout());
this.setBackground(Color.WHITE);
this.setPreferredSize(new Dimension(100, 80));
        this.add(new JButton("first"));
        this.add(new JButton("second"));
    }
}

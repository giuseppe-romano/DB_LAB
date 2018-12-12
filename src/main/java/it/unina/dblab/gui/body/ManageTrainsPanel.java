package it.unina.dblab.gui.body;

import javax.swing.*;
import java.awt.*;

public class ManageTrainsPanel extends JPanel {
    public static final String NAME = "MANAGE_TRAINS";

    private Container parent;

    public ManageTrainsPanel(Container parent) {
        this.parent = parent;

        this.setBackground(Color.WHITE);

        FlowLayout flowLayout = new FlowLayout(FlowLayout.LEADING);
        this.setLayout(flowLayout);

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
        titlePanel.add(new JLabel("Trains Management"));
        titlePanel.add(new JSeparator(SwingConstants.HORIZONTAL));

        this.add(titlePanel, BorderLayout.PAGE_START);

       // this.add(new JPanel(), BorderLayout.LINE_START);
     //   this.add(new JTable(), BorderLayout.CENTER);

    }
}

package it.unina.dblab.gui.header;

import it.unina.dblab.HeavenRail;
import it.unina.dblab.gui.body.BodyContainer;
import it.unina.dblab.gui.body.ManageRoutesPanel;
import it.unina.dblab.gui.body.ManageStationsPanel;
import it.unina.dblab.gui.body.ManageTrainsPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HeaderContainer extends JPanel implements ActionListener {

    private HeavenRail parent;

    private HeaderButton manageTrainsButton = new HeaderButton("Gestisci Treni");
    private HeaderButton manageStationsButton = new HeaderButton("Gestisci Stazioni");
    private HeaderButton manageRoutesButton = new HeaderButton("Gestisci Percorsi");

    public HeaderContainer(HeavenRail parent) {
        this.parent = parent;

        this.setPreferredSize(new Dimension(100, 80));

        FlowLayout flowLayout = new FlowLayout(FlowLayout.LEADING);
        this.setLayout(flowLayout);

        manageTrainsButton.addActionListener(this);
        manageStationsButton.addActionListener(this);
        manageRoutesButton.addActionListener(this);
        this.add(manageTrainsButton);
        this.add(manageStationsButton);
        this.add(manageRoutesButton);

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(manageTrainsButton);
        buttonGroup.add(manageStationsButton);
        buttonGroup.add(manageRoutesButton);

        manageTrainsButton.setSelected(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        int w = getWidth();
        int h = getHeight();
        Color color1 = new Color(51, 153, 255);
        Color color2 = Color.WHITE;
        GradientPaint gp = new GradientPaint(0, 0, color1, 0, h, color2);
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, w, h);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        BodyContainer bodyContainer = parent.getBodyContainer();
        if(e.getSource() == manageTrainsButton) {
            ((CardLayout)bodyContainer.getLayout()).show(bodyContainer, ManageTrainsPanel.NAME);
        }
        else if(e.getSource() == manageStationsButton) {
            ((CardLayout)bodyContainer.getLayout()).show(bodyContainer, ManageStationsPanel.NAME);
        }
        else if(e.getSource() == manageRoutesButton) {
            ((CardLayout)bodyContainer.getLayout()).show(bodyContainer, ManageRoutesPanel.NAME);
        }
    }

}

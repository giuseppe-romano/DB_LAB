package it.unina.dblab.header;

import javax.swing.*;
import java.awt.*;

public class HeaderContainer extends JPanel {

    public HeaderContainer() {
        this.setPreferredSize(new Dimension(100, 80));

      //  GridLayout experimentLayout = new GridLayout(1,1);
        FlowLayout experimentLayout = new FlowLayout(FlowLayout.LEADING);
        this.setLayout(experimentLayout);

        this.add(new JButton("Manage Routes"));
        this.add(new JButton("second"));
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

}

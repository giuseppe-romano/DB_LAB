package it.unina.dblab.gui.header;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class HeaderButton extends JToggleButton implements MouseListener {
    private static final long serialVersionUID = 1L;

    private Color circleColor = Color.BLACK;

    public HeaderButton(String label) {
        super(label);

        setContentAreaFilled(false);
        setOpaque(false);
        setFont(new Font("Candara", Font.PLAIN, 16));

        this.addMouseListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        setBorderPainted(false);
        setFont(new Font("Candara", Font.PLAIN, 16));
        if(this.isSelected()) {
            setBorderPainted(true);
            setFont(new Font("Candara", Font.BOLD, 18));
        }

        Dimension originalSize = super.getPreferredSize();
        int gap = (int) (originalSize.height * 0.2);
        int x = originalSize.width + gap;
        int y = gap;
        int diameter = originalSize.height - (gap * 2);

        g.setColor(circleColor);
        g.fillOval(x, y, diameter, diameter);
    }

    @Override
    public Dimension getPreferredSize() {
        Dimension size = super.getPreferredSize();
        size.height = 75;
        return size;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    @Override
    public void mouseExited(MouseEvent e) {
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }
}

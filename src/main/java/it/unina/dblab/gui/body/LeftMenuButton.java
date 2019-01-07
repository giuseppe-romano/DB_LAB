package it.unina.dblab.gui.body;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class LeftMenuButton extends JButton implements MouseListener {
    private static final long serialVersionUID = 1L;

    private Color circleColor = Color.BLACK;

    public LeftMenuButton(String label) {
        super(label);

        setContentAreaFilled(false);
        setOpaque(false);
        setFont(new Font("Candara", Font.PLAIN, 16));

        this.addMouseListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        setBorderPainted(true);
        setFont(new Font("Candara", Font.PLAIN, 16));
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

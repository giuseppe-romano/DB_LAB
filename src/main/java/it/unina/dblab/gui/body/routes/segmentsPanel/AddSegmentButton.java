package it.unina.dblab.gui.body.routes.segmentsPanel;

import it.unina.dblab.models.Route;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

public class AddSegmentButton extends JButton implements MouseListener {

    public AddSegmentButton() {

        BufferedImage buttonIcon = null;
        try {
            // Get the image and set it to the imageicon
            buttonIcon = ImageIO.read(getClass().getClassLoader().getResource("icons/add.png"));
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }

        this.setIcon(new ImageIcon(buttonIcon));
        this.setContentAreaFilled(false);
        this.setFocusPainted(false);
        this.setOpaque(false);
        this.setToolTipText("Aggiungi un nuovo segmento");
        this.addMouseListener(this);
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

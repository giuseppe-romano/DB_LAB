package it.unina.dblab;

import it.unina.dblab.gui.body.BodyContainer;
import it.unina.dblab.gui.footer.FooterContainer;
import it.unina.dblab.gui.header.HeaderContainer;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.*;
import java.awt.*;


public class HeavenRail extends JFrame {

    private static HeavenRail instance;

    public static EntityManagerFactory entityManagerFactory = Persistence
            .createEntityManagerFactory("DB_LAB");

    private HeavenRail() {
        super("Heaven's Rail Demo Application");
        instance = this;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    private static void createAndShowGUI() {

        HeavenRail heavenRail = new HeavenRail();
        Container contentPane = heavenRail.getContentPane();
        contentPane.add(new HeaderContainer(heavenRail), BorderLayout.PAGE_START);
        contentPane.add(new BodyContainer(heavenRail), BorderLayout.CENTER);
        contentPane.add(new FooterContainer(heavenRail), BorderLayout.PAGE_END);

        heavenRail.pack();
        heavenRail.setSize(1250, 850);
        heavenRail.setVisible(true);
    }

    public HeaderContainer getHeaderContainer() {
        Component c = this.getContentPane().getComponent(0);
        assert c instanceof HeaderContainer;

        return (HeaderContainer) c;
    }

    public BodyContainer getBodyContainer() {
        Component c = this.getContentPane().getComponent(1);
        assert c instanceof BodyContainer;

        return (BodyContainer) c;
    }

    public FooterContainer getFooterContainer() {
        Component c = this.getContentPane().getComponent(2);
        assert c instanceof FooterContainer;

        return (FooterContainer) c;
    }

    public static JFrame getFrame() {
        return instance;
    }
}

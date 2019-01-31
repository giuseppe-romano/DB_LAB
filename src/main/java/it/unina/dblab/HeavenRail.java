package it.unina.dblab;

import it.unina.dblab.gui.body.BodyContainer;
import it.unina.dblab.gui.footer.FooterContainer;
import it.unina.dblab.gui.header.HeaderContainer;

import javax.swing.*;
import java.awt.*;


public class HeavenRail extends JFrame {

    private static HeavenRail instance;

    private static JPanel content = new JPanel();

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
        contentPane.setLayout(new CardLayout());


        LoginScreen loginScreen = new LoginScreen(contentPane);
        contentPane.add(loginScreen, "LOGIN");



        content.setLayout(new BorderLayout());


        contentPane.add(content, "CONTENT");

        heavenRail.pack();
        heavenRail.setSize(1250, 850);
        heavenRail.setVisible(true);
    }

    public HeaderContainer getHeaderContainer() {
        Component c = content.getComponent(0);
        assert c instanceof HeaderContainer;

        return (HeaderContainer) c;
    }

    public BodyContainer getBodyContainer() {
        Component c = content.getComponent(1);
        assert c instanceof BodyContainer;

        return (BodyContainer) c;
    }

    public FooterContainer getFooterContainer() {
        Component c = content.getComponent(2);
        assert c instanceof FooterContainer;

        return (FooterContainer) c;
    }

    public static JFrame getFrame() {
        return instance;
    }

    public static void showApplication() {
        content.add(new HeaderContainer(instance), BorderLayout.PAGE_START);
        content.add(new BodyContainer(instance), BorderLayout.CENTER);
        content.add(new FooterContainer(instance), BorderLayout.PAGE_END);
    }
}

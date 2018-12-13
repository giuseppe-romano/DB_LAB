package it.unina.dblab;

import it.unina.dblab.gui.body.BodyContainer;
import it.unina.dblab.gui.footer.FooterContainer;
import it.unina.dblab.gui.header.HeaderContainer;
import it.unina.dblab.models.Train;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.swing.*;
import java.awt.*;
import java.util.List;


public class HeavenRail extends JFrame {

    public static EntityManagerFactory entityManagerFactory = Persistence
            .createEntityManagerFactory("DB_LAB");

    private HeavenRail() {
        super("Heaven's Rail Demo Application");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        EntityManager manager = entityManagerFactory.createEntityManager();

        EntityTransaction transaction = null;

        try {
            // Get a transaction
            transaction = manager.getTransaction();
            // Begin the transaction
            transaction.begin();

            // Get a List of Students
            List<Train> trains = manager.createQuery("SELECT s FROM it.unina.dblab.models.Train s",
                    Train.class).getResultList();

            // Commit the transaction
            transaction.commit();
        } catch (Exception ex) {
            // If there are any exceptions, roll back the changes
            if (transaction != null) {
                transaction.rollback();
            }
            // Print the Exception
            ex.printStackTrace();
        }
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
        heavenRail.setSize(650,250);
        heavenRail.setVisible(true);
    }

    public HeaderContainer getHeaderContainer() {
        Component c = this.getContentPane().getComponent(0);
        assert c instanceof HeaderContainer;

        return (HeaderContainer)c;
    }

    public BodyContainer getBodyContainer() {
        Component c = this.getContentPane().getComponent(1);
        assert c instanceof BodyContainer;

        return (BodyContainer)c;
    }

    public FooterContainer getFooterContainer() {
        Component c = this.getContentPane().getComponent(2);
        assert c instanceof FooterContainer;

        return (FooterContainer)c;
    }
}

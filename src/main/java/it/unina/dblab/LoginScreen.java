package it.unina.dblab;

import it.unina.dblab.gui.utility.DatabaseUtil;
import it.unina.dblab.gui.utility.SpringUtilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;


public class LoginScreen extends JPanel implements ActionListener {

    private Image image;
    private int iWidth2;
    private int iHeight2;

    private JTextField databaseHostTextField;
    private JTextField databasePortTextField;
    private JTextField databaseServiceTextField;
    private JTextField databaseUserTextField;
    private JPasswordField databasePasswordTextField;

    private Container contentPane;

    public LoginScreen(Container contentPane) {
        this.contentPane = contentPane;
        image = new ImageIcon(getClass().getClassLoader().getResource("icons/splash.jpeg")).getImage();

        this.iWidth2 = image.getWidth(this) / 2;
        this.iHeight2 = image.getHeight(this) / 2;

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JPanel loginPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics graphics) {
                super.paintComponent(graphics);
                Graphics2D g2d = (Graphics2D) graphics;
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
            }
        };
        loginPanel.setOpaque(false);

        loginPanel.setPreferredSize(new Dimension(500, 600));
        loginPanel.setMaximumSize(new Dimension(500, 600));
        loginPanel.setSize(new Dimension(500, 600));
        loginPanel.setLayout(new BorderLayout());

        JPanel titlePanel = new JPanel();
        titlePanel.setOpaque(false);
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        JPanel spacePanel = new JPanel();
        spacePanel.setOpaque(false);
        spacePanel.setPreferredSize(new Dimension(500, 300));

        titlePanel.add(spacePanel);

        JLabel title = new JLabel("Connessione al database");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Candara", Font.BOLD, 24));
        titlePanel.add(title);


        loginPanel.add(titlePanel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new SpringLayout());

        //DB Type
        JLabel databaseTypeLabel = new JLabel("Database Type", JLabel.TRAILING);
        formPanel.add(databaseTypeLabel);

        JComboBox databaseTypeComboBox = new JComboBox<>(new String[]{"Oracle"});
        databaseTypeComboBox.setBackground(Color.WHITE);
        databaseTypeLabel.setLabelFor(databaseTypeComboBox);
        formPanel.add(databaseTypeComboBox);

        //DB Host
        JLabel databaseHostLabel = new JLabel("Database Host", JLabel.TRAILING);
        formPanel.add(databaseHostLabel);

        databaseHostTextField = new JTextField();
        databaseHostTextField.setBackground(Color.WHITE);
        databaseHostLabel.setLabelFor(databaseHostTextField);
        formPanel.add(databaseHostTextField);

        //DB Port
        JLabel databasePortLabel = new JLabel("Database Port", JLabel.TRAILING);
        formPanel.add(databasePortLabel);

        databasePortTextField = new JTextField();
        databasePortTextField.setBackground(Color.WHITE);
        databasePortLabel.setLabelFor(databasePortTextField);
        formPanel.add(databasePortTextField);

        //DB Service Name
        JLabel databaseServiceLabel = new JLabel("Database SID/Service Name", JLabel.TRAILING);
        formPanel.add(databaseServiceLabel);

        databaseServiceTextField = new JTextField();
        databaseServiceTextField.setBackground(Color.WHITE);
        databaseServiceLabel.setLabelFor(databaseServiceTextField);
        formPanel.add(databaseServiceTextField);

        //DB User Name
        JLabel databaseUserLabel = new JLabel("Database User", JLabel.TRAILING);
        formPanel.add(databaseUserLabel);

        databaseUserTextField = new JTextField();
        databaseUserTextField.setBackground(Color.WHITE);
        databaseUserLabel.setLabelFor(databaseUserTextField);
        formPanel.add(databaseUserTextField);

        //DB User Pwd
        JLabel databasePasswordLabel = new JLabel("Database Password", JLabel.TRAILING);
        formPanel.add(databasePasswordLabel);

        databasePasswordTextField = new JPasswordField();
        databasePasswordTextField.setBackground(Color.WHITE);
        databasePasswordLabel.setLabelFor(databasePasswordTextField);
        formPanel.add(databasePasswordTextField);

        formPanel.add(new JLabel());
        JPanel connect = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        connect.setOpaque(false);
        JButton connectButton = new JButton("Connetti");
        connectButton.addActionListener(this);
        connect.add(connectButton);
        formPanel.add(connect);


        //Lay out the panel.
        SpringUtilities.makeCompactGrid(formPanel,
                7, 2, //rows, cols
                6, 6,        //initX, initY
                6, 6);       //xPad, yPad

        loginPanel.add(formPanel, BorderLayout.CENTER);

        this.add(loginPanel);

        this.databaseHostTextField.setText("localhost");
        this.databasePortTextField.setText("1521");
        this.databaseServiceTextField.setText("XE");
        this.databaseUserTextField.setText("db_lab");
        this.databasePasswordTextField.setText("db_lab");
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            int x = this.getParent().getWidth() / 2 - iWidth2;
            int y = this.getParent().getHeight() / 2 - iHeight2;
            g.drawImage(image, x, y, this);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        try {
            String databaseHost = databaseHostTextField.getText();
            Integer databasePort = Integer.parseInt(databasePortTextField.getText());
            String databaseService = databaseServiceTextField.getText();
            String databaseUser = databaseUserTextField.getText();
            String databasePassword = new String(databasePasswordTextField.getPassword());

            Class.forName("oracle.jdbc.OracleDriver");

            String url = "jdbc:oracle:thin:@//" + databaseHost + ":" + databasePort + "/" + databaseService;

            Connection conn = DriverManager.getConnection(url, databaseUser, databasePassword);
            conn.close();

            Map<String, Object> properties = new HashMap<>();
            properties.put("javax.persistence.jdbc.url", url);
            properties.put("javax.persistence.jdbc.user", databaseUser);
            properties.put("javax.persistence.jdbc.password", databasePassword);
            DatabaseUtil.setConnectionSettings(properties);

            HeavenRail.showApplication();

            ((CardLayout) contentPane.getLayout()).show(contentPane, "CONTENT");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Errore di connessione", JOptionPane.ERROR_MESSAGE);
        }
    }
}

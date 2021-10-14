package GUI;

import DB.DBConnection;
import main.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class is the main frame in GUI which controls all the panels.
 * @author Belle Zhang
 */


public class MainFrame extends JFrame {
    private Player player;
    private JPanel contPanel;
    private MainMenuPanel mainMenuPanel;
    private RegisterPanel registerPanel;
    private LoginPanel loginPanel;
    private CardLayout card;
    private String username, password;
    private DBConnection conn;

    //Constructor for mainFrame
    //When program is run the first panel will be registerPanel
    public MainFrame() {
        setSize(new Dimension(500, 400));
        player = new Player();
        contPanel = new JPanel();
        mainMenuPanel = new MainMenuPanel(player, this);
        registerPanel = new RegisterPanel();
        loginPanel = new LoginPanel();
        
        //Card layout for switching planes
        card = new CardLayout();
        conn = new DBConnection();
        
        setTitle("Pet Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Content panel which contains all the panels that can be switched
        //in CardLayout
        contPanel.setLayout(card);
        //Adds registerPanel to contPanel
        contPanel.add(registerPanel, "register");
        add(contPanel);
        
        //Sets location of screen
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension screenDimension = tk.getScreenSize();
        Dimension frameDimension = getSize();
        setLocation((screenDimension.width-frameDimension.width)/2,
                (screenDimension.height-frameDimension.height)/2);
        setVisible(true);
    }

    //Main method runs the frame
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainFrame();
            }
        });
    }

    //Method for getting cardLayout
    public CardLayout getCard() {
        return card;
    }

    //Method for getting contPanel
    public JPanel getContPanel() {
        return contPanel;
    }
    
    //Method for getting DBConnection
    public DBConnection getConnection() {
        return conn;
    }
    
    //Method for getting username
    public String getUsername() {
        return username;
    }

    //Register panel which allows a user to register an account by entering
    //a valid username that doesn't yet exist in the database
    private class RegisterPanel extends JPanel implements ActionListener {
        private GroupLayout gp;
        private JLabel username, password;
        private JTextField nameField, passField;
        private JButton ok, login;
        private JLabel warning;

        //Constructor for RegisterPanel
        public RegisterPanel() {
            gp = new GroupLayout(this);
            setLayout(gp);

            username = new JLabel("Register\n Username:");
            password = new JLabel("Password:");

            nameField = new JTextField();
            passField = new JTextField();

            ok = new JButton("Ok");
            ok.addActionListener(this);
            login = new JButton("Login");
            login.addActionListener(this);

            //A warning label which appears when the input is invalid
            warning = new JLabel("Please enter a valid username/password");
            warning.setForeground(Color.RED);
            warning.setVisible(false);

            //Using group layout to set position of components
            gp.setHorizontalGroup(gp.createSequentialGroup()
                    .addGroup(gp.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(username)
                            .addComponent(nameField)
                            .addComponent(password)
                            .addComponent(passField)
                            .addComponent(warning)
                            .addComponent(ok))
                    .addGroup(gp.createParallelGroup().addComponent(login))
            );

            gp.setVerticalGroup(gp.createSequentialGroup()
                    .addGroup(gp.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(username))
                    .addGroup(gp.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(nameField))
                    .addGroup(gp.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(password))
                    .addGroup(gp.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(passField))
                    .addGroup(gp.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(ok).addComponent(login))
                    .addGroup(gp.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(warning))
            );

        }

        @Override
        public void actionPerformed(ActionEvent e) {
            warning.setVisible(false);
            //When ok is clicked, check to see if the inputs are valid.
            //If yes, attempt to insert username and password into the database,
            //if the username already exists in the database, display warning.
            //If the username is unique, add username and password to database, 
            //and switch panel to mainMenuPanel.
            
            //If the one or both of the text fields are empty, display warning.
            if (e.getSource() == ok) {
                if (nameField.getText().equals("") || passField.getText().equals("")) {
                    warning.setVisible(true);
                } else if (!nameField.getText().equals("") && !passField.getText().equals("")) {
                    boolean success = conn.insertPlayer(nameField.getText(), passField.getText());
                    System.out.println(success);
                    if (success) {
                        MainFrame.this.username = nameField.getText();
                        MainFrame.this.password = passField.getText();
                        contPanel.add(mainMenuPanel, "1");
                        card.show(contPanel, "1");
                    } else {
                        warning.setVisible(true);
                    }
                }//If login button is clicked, switch to loginPanel where the user
                //can login if they have an existing set of username and password
            } else if (e.getSource() == login) {
                contPanel.add(loginPanel, "login");
                card.show(contPanel, "login");
            }
        }
    }

    //Login panel which allows user to login if they have an existing set of
    //username and password in the database
    private class LoginPanel extends JPanel implements ActionListener {
        private GroupLayout gp;
        private JLabel username, password;
        private JTextField nameField, passField;
        private JButton ok, register;
        private JLabel warning;

        //Constructor for loginPanel
        public LoginPanel() {
            gp = new GroupLayout(this);
            setLayout(gp);

            username = new JLabel("Login\n Username:");
            password = new JLabel("Password:");

            nameField = new JTextField();
            passField = new JTextField();

            ok = new JButton("Ok");
            ok.addActionListener(this);
            register = new JButton("Register");
            register.addActionListener(this);

            //A warning label which appears if the inputs are invalid, or when
            //the username doesn't exist in the database
            warning = new JLabel("Please enter a valid username/password");
            warning.setForeground(Color.RED);
            warning.setVisible(false);

            //Using group layout to set the locations of the components
            gp.setHorizontalGroup(gp.createSequentialGroup()
                    .addGroup(gp.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(username)
                            .addComponent(nameField)
                            .addComponent(password)
                            .addComponent(passField)
                            .addComponent(warning)
                            .addComponent(ok))
                    .addGroup(gp.createParallelGroup().addComponent(register))
            );

            gp.setVerticalGroup(gp.createSequentialGroup()
                    .addGroup(gp.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(username))
                    .addGroup(gp.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(nameField))
                    .addGroup(gp.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(password))
                    .addGroup(gp.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(passField))
                    .addGroup(gp.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(ok).addComponent(register))
                    .addGroup(gp.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(warning))
            );

        }

        @Override
        public void actionPerformed(ActionEvent e) {
            warning.setVisible(false);
            //If ok button is clicked, and the text fields are not empty,
            //check to see if username and password combinations are correct.
            
            //If username and password combination doesn't exist in the database,
            //display warning.
            
            //If username and password are correct, change panel to mainMenuPanel.
            if (e.getSource() == ok) {
                if (nameField.getText().equals("") || passField.getText().equals("")) {
                    warning.setVisible(true);
                } else if (!nameField.getText().equals("") && !passField.getText().equals("")) {
                    boolean success = conn.verifyPassword(nameField.getText(), passField.getText());
                    if (success) {
                        MainFrame.this.username = nameField.getText();
                        MainFrame.this.password = passField.getText();
                        contPanel.add(mainMenuPanel, "1");
                        card.show(contPanel, "1");
                    } else {
                        warning.setVisible(true);
                    }
                }//If register button is clicked, change panel to register panel
            } else if (e.getSource() == register) {
                card.show(contPanel, "register");
            }
        }
    }
}

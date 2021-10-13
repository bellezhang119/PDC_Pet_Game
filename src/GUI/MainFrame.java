package GUI;

import DB.DBConnection;
import main.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {
    private Player player;
    private JPanel contPanel;
    private MainMenuPanel mainMenuPanel;
    private RegisterPanel registerPanel;
    private LoginPanel loginPanel;
    private CardLayout card;
    private String username, password;
    private DBConnection conn;

    public MainFrame() {
        setSize(new Dimension(500, 400));
        player = new Player();
        contPanel = new JPanel();
        mainMenuPanel = new MainMenuPanel(player, this);
        registerPanel = new RegisterPanel();
        loginPanel = new LoginPanel();
        
        card = new CardLayout();
        conn = new DBConnection();
        
        setTitle("Pet Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        contPanel.setLayout(card);
        contPanel.add(registerPanel, "register");
        add(contPanel);
        
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension screenDimension = tk.getScreenSize();
        Dimension frameDimension = getSize();
        setLocation((screenDimension.width-frameDimension.width)/2,
                (screenDimension.height-frameDimension.height)/2);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainFrame();
            }
        });
    }

    public CardLayout getCard() {
        return card;
    }

    public JPanel getContPanel() {
        return contPanel;
    }
    
    public DBConnection getConnection() {
        return conn;
    }
    
    public String getUsername() {
        return username;
    }

    private class RegisterPanel extends JPanel implements ActionListener {
        private GroupLayout gp;
        private JLabel username, password;
        private JTextField nameField, passField;
        private JButton ok, login;
        private JLabel warning;

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

            warning = new JLabel("Please enter a valid username/password");
            warning.setForeground(Color.RED);
            warning.setVisible(false);

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
                }
            } else if (e.getSource() == login) {
                
                contPanel.add(loginPanel, "login");
                card.show(contPanel, "login");
            }
        }
    }

    private class LoginPanel extends JPanel implements ActionListener {
        private GroupLayout gp;
        private JLabel username, password;
        private JTextField nameField, passField;
        private JButton ok, register;
        private JLabel warning;

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

            warning = new JLabel("Please enter a valid username/password");
            warning.setForeground(Color.RED);
            warning.setVisible(false);

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
                }
            } else if (e.getSource() == register) {
                card.show(contPanel, "register");
            }
        }
    }
}

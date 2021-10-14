package GUI;

import main.Player;
import misc.FileIO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * This panel is the main menu panel in GUI which controls creating new games,
 * loading saves, going into game panel, and quitting the program
 * @author Belle Zhang
 */

public class MainMenuPanel extends JPanel implements ActionListener, FileIO {
    private GroupLayout gp;
    private JButton newGame, loadGame, quit;
    private int width = 600;
    private int height = 500;
    private NewGamePanel newGamePanel;
    private LoadGamePanel loadGamePanel;
    private GamePanel gamePanel;
    private MainFrame frame;
    private Player player;

    //Constructor for mainMenuPanel
    public MainMenuPanel(Player player, MainFrame frame) {
        gp = new GroupLayout(this);
        setLayout(gp);
        setBorder(new EmptyBorder(100, 100, 100, 100));
        setPreferredSize(new Dimension(width, height));
        newGame = new JButton("New Game");
        newGame.addActionListener(this);
        loadGame = new JButton("Load Game");
        loadGame.addActionListener(this);
        quit = new JButton("Quit");
        quit.addActionListener(this);

        newGamePanel = new NewGamePanel();
        loadGamePanel = new LoadGamePanel();
        this.frame = frame;
        this.player = player;
        newGame.setSize(60, 40);
        loadGame.setSize(110, 40);
        quit.setSize(110, 40);

        //Using group layout to set locations of components
        gp.setHorizontalGroup(gp.createSequentialGroup()
                .addGroup(gp.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(newGame, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(loadGame, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(quit, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        gp.setVerticalGroup(gp.createSequentialGroup()
                .addGroup(gp.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(newGame))
                .addGroup(gp.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(loadGame))
                .addGroup(gp.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(quit))
        );
    }

    //When newGame button is clicked, switch to new game panel
    //When loadGame button is clicked, switch to load game panel
    //When quit button is clicked, quit program
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == newGame) {
            frame.getContPanel().add(newGamePanel, "2");
            frame.getCard().show(frame.getContPanel(), "2");
        } else if (e.getSource() == loadGame) {
            frame.getContPanel().add(loadGamePanel, "3");
            frame.getCard().show(frame.getContPanel(), "3");
        } else {
            frame.dispose();
        }
    }

    //This panel is for creating new games
    private class NewGamePanel extends JPanel implements ActionListener {
        private GroupLayout gp;
        private JButton ok, back;
        private JTextField nameField;
        private JLabel message, warning;

        //Constructor for creating newGamePanel
        public NewGamePanel() {
            gp = new GroupLayout(this);
            setLayout(gp);
            gp.setAutoCreateGaps(true);
            gp.setAutoCreateContainerGaps(true);
            setPreferredSize(new Dimension(width, height));

            ok = new JButton("OK");
            ok.addActionListener(this);
            back = new JButton("Back");
            back.addActionListener(this);
            nameField = new JTextField();
            message = new JLabel("Enter a name for new player");
            warning = new JLabel("Please enter a valid name");
            warning.setForeground(Color.RED);
            
            //Using group layout to set locations of components
            gp.setHorizontalGroup(gp.createSequentialGroup()
                    .addGroup(gp.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(message)
                            .addComponent(nameField)
                            .addComponent(warning)
                            .addComponent(ok))
                    .addGroup(gp.createParallelGroup().addComponent(back))
            );

            gp.setVerticalGroup(gp.createSequentialGroup()
                    .addGroup(gp.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(message))
                    .addGroup(gp.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(nameField))
                    .addGroup(gp.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(ok).addComponent(back))
                    .addGroup(gp.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(warning))
            );



            warning.setVisible(false);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            //If ok button is clicked, and nameField is not empty, make a new
            //player with the name, and switch panel to gamePanel.
            //If nameField is empty, display warning.
            if (e.getSource() == ok) {
                String name = "";
                name = nameField.getText();
                if (name.equals("")) {
                    warning.setVisible(true);
                } else {
                    player = new Player(name);
                    nameField.setText("");
                    warning.setVisible(false);
                    gamePanel = new GamePanel(frame, player);
                    frame.getContPanel().add(gamePanel, "4");
                    frame.getCard().show(frame.getContPanel(), "4");
                }//If back button is clicked, switch panel to mainMenuPanel
            } else if (e.getSource() == back) {
                nameField.setText("");
                warning.setVisible(false);
                frame.getCard().show(frame.getContPanel(), "1");
            }
        }
    }

    //This panel is for loading games from database
    private class LoadGamePanel extends JPanel implements ActionListener {
        private GroupLayout gp;
        private JButton ok, back;
        private JTextField nameField;
        private JLabel message, warning;

        //Constructor for loadGamePanel
        public LoadGamePanel() {
            gp = new GroupLayout(this);
            setLayout(gp);
            gp.setAutoCreateGaps(true);
            gp.setAutoCreateContainerGaps(true);
            setPreferredSize(new Dimension(width, height));

            ok = new JButton("OK");
            ok.addActionListener(this);
            back = new JButton("Back");
            back.addActionListener(this);
            nameField = new JTextField();
            message = new JLabel("Enter name of save");
            warning = new JLabel("Please enter a valid name");
            warning.setForeground(Color.RED);

            //Using group layout to set locations for components
            gp.setHorizontalGroup(gp.createSequentialGroup()
                    .addGroup(gp.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(message)
                            .addComponent(nameField)
                            .addComponent(warning)
                            .addComponent(ok))
                    .addGroup(gp.createParallelGroup().addComponent(back))
            );

            gp.setVerticalGroup(gp.createSequentialGroup()
                    .addGroup(gp.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(message))
                    .addGroup(gp.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(nameField))
                    .addGroup(gp.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(ok).addComponent(back))
                    .addGroup(gp.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(warning))
            );



            warning.setVisible(false);
        }

        //If ok button is clicked, load a save from the database with the corresponding
        //username and save name and switch panel to gamePanel
        //If nameField is empty display warning
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if (e.getSource() == ok) {
                    String name = "";
                    name = nameField.getText();
                    if (name.equals("")) {
                        warning.setVisible(true);
                    } else {
                        player = frame.getConnection().loadSave(frame.getUsername(), name);
                        nameField.setText("");
                        warning.setVisible(false);
                        gamePanel = new GamePanel(frame, player);
                        frame.getContPanel().add(gamePanel, "4");
                        frame.getCard().show(frame.getContPanel(), "4");
                    }//If back button is clicked, switch panel to mainMenuPanel
                } else if (e.getSource() == back) {
                    nameField.setText("");
                    warning.setVisible(false);
                    frame.getCard().show(frame.getContPanel(), "1");
                }
            } catch (IndexOutOfBoundsException a) {
                warning.setVisible(true);
            }
        }
    }
}

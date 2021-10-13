/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 *
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


    private class NewGamePanel extends JPanel implements ActionListener {
        private GroupLayout gp;
        private JButton ok, back;
        private JTextField nameField;
        private JLabel message, warning;

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

        public void displayWarning() {
            warning.setVisible(true);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == ok) {
                String name = "";
                name = nameField.getText();
                if (name.equals("")) {
                    displayWarning();
                } else {
                    player = new Player(name);
                    nameField.setText("");
                    warning.setVisible(false);
                    gamePanel = new GamePanel(frame, player);
                    frame.getContPanel().add(gamePanel, "4");
                    frame.getCard().show(frame.getContPanel(), "4");
                }
            } else if (e.getSource() == back) {
                nameField.setText("");
                warning.setVisible(false);
                frame.getCard().show(frame.getContPanel(), "1");
            }
        }
    }

    private class LoadGamePanel extends JPanel implements ActionListener {
        private GroupLayout gp;
        private JButton ok, back;
        private JTextField nameField;
        private JLabel message, warning;

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

        public void displayWarning() {
            warning.setVisible(true);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if (e.getSource() == ok) {
                    String name = "";
                    name = nameField.getText();
                    if (name.equals("")) {
                        displayWarning();
                    } else {
                        player = frame.getConnection().loadSave(frame.getUsername(), name);
                        nameField.setText("");
                        warning.setVisible(false);
                        gamePanel = new GamePanel(frame, player);
                        frame.getContPanel().add(gamePanel, "4");
                        frame.getCard().show(frame.getContPanel(), "4");
                    }
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

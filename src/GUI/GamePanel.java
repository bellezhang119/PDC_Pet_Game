package GUI;

import Items.*;
import Pets.*;
import main.Player;
import misc.FileIO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * This panel is the main game panel which is the actual game content, controlling
 * interactions with player pets and inventories while having some other
 * functions like saving the game.
 * 
 * @author Belle Zhang
 */

public class GamePanel extends JPanel implements FileIO {
    private MainFrame frame;
    private JPanel westPanel, southPanel, eastPanel;
    private Player player;
    private JLabel playerInfo;
    private JTextArea petInfo;
    private JComboBox<Pet> petBox;
    private JComboBox<Item> inventoryBox;
    private JButton use, shop, managePet, save, quit, pet, scold, play;
    private GeneralListener generalListener;
    private ManageListener manageListener;
    private InteractListener interactListener;
    private ItemChangeListener changeListener;
    GroupLayout westGP;
    GroupLayout eastGP;
    private ShopPanel shopPanel;
    private SavePanel savePanel;
    private ManagePetPanel managePetPanel;

    //Constructor for gamePanel
    public GamePanel(MainFrame frame, Player player) {
        this.frame = frame;
        this.player = player;
        setLayout(new BorderLayout(10, 10));
        setPreferredSize(new Dimension(600, 500));
        changeListener = new ItemChangeListener();
        
        //Container panels for borderLayout
        westPanel = new JPanel();
        southPanel = new JPanel();
        eastPanel = new JPanel();

        //petBox which is a JComboBox that contains the petList of player
        playerInfo = new JLabel();
        petBox = new JComboBox<>();
        for (Pet pet : player.getPetList()) {
            petBox.addItem(pet);
        }

        //inventoryBox which is a JComboBox that contains the inventory of player
        petInfo = new JTextArea();
        petInfo.setSize(50, 50);
        inventoryBox = new JComboBox<>();
        
        for (Item item : player.getInventory()) {
            inventoryBox.addItem(item);
        }
        
        petBox.addItemListener(changeListener);
        inventoryBox.addItemListener(changeListener);
        
        //Custom ActionListener classes for different sets of buttons
        generalListener = new GeneralListener();
        manageListener = new ManageListener();
        interactListener = new InteractListener();
        use = new JButton("Use");
        use.addActionListener(generalListener);
        shop = new JButton("Shop");
        shop.addActionListener(manageListener);
        managePet = new JButton("Manage Pets");
        managePet.addActionListener(manageListener);
        save = new JButton("Save");
        save.addActionListener(generalListener);
        quit = new JButton("Quit");
        quit.addActionListener(generalListener);
        pet = new JButton("Pet");
        pet.addActionListener(interactListener);
        scold = new JButton("Scold");
        scold.addActionListener(interactListener);
        play = new JButton("Play");
        play.addActionListener(interactListener);

        shopPanel = new ShopPanel();
        savePanel = new SavePanel();
        managePetPanel = new ManagePetPanel();

        add(playerInfo, BorderLayout.NORTH);
        add(petInfo, BorderLayout.CENTER);
        
        //Using group layout to set locations of components in westPanel and eastPanel
        westGP = new GroupLayout(westPanel);
        westPanel.setLayout(westGP);
        westGP.setHorizontalGroup(westGP.createSequentialGroup()
                    .addGroup(westGP.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(inventoryBox)
                            .addComponent(petBox)
                            .addComponent(use))
        );
        
        westGP.setVerticalGroup(westGP.createSequentialGroup()
                    .addGroup(westGP.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(inventoryBox))
                    .addGroup(westGP.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(petBox))
                    .addGroup(westGP.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(use))
        );
        
        southPanel.add(shop);
        southPanel.add(managePet);
        southPanel.add(save);
        southPanel.add(quit);
        
        eastGP = new GroupLayout(eastPanel);
        eastPanel.setLayout(eastGP);
        eastGP.setHorizontalGroup(eastGP.createSequentialGroup()
                    .addGroup(eastGP.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(pet)
                            .addComponent(scold)
                            .addComponent(play))
        );
        
        eastGP.setVerticalGroup(eastGP.createSequentialGroup()
                    .addGroup(eastGP.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(pet))
                    .addGroup(eastGP.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(scold))
                    .addGroup(eastGP.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(play))
        );
        
        add(westPanel, BorderLayout.WEST);
        add(southPanel, BorderLayout.SOUTH);
        add(eastPanel, BorderLayout.EAST);
        
        update();
    }

    //Update method for updating information in playerInfo, petInfo and JComboBoxes
    //when there's an action
    public void update() {
        String text = "Player: " + player.getName() + " Balance: " + player.getMoney();
        playerInfo.setText(text);

        if (petBox.getSelectedIndex() != -1) {
            Pet pet = (Pet) petBox.getSelectedItem();
            petInfo.setText(pet.petStats());
        }
        
        westGP.setHorizontalGroup(westGP.createSequentialGroup()
                    .addGroup(westGP.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(inventoryBox)
                            .addComponent(petBox)
                            .addComponent(use))
        );
        
        westGP.setVerticalGroup(westGP.createSequentialGroup()
                    .addGroup(westGP.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(inventoryBox))
                    .addGroup(westGP.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(petBox))
                    .addGroup(westGP.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(use))
        );
    }
    
    //ItemListener for petBox and inventoryBox
    private class ItemChangeListener implements ItemListener {

        @Override
        public void itemStateChanged(ItemEvent e) {
            update();
        }
    }

    //ActionListener for buttons use, save and quit
    private class GeneralListener implements ActionListener {
        //If use button is clicked, and both petBox and inventoryBox have valid
        //selections, use item on pet
        //Is save button is clicked, switch to savePanel
        //If quit button is clicked, switch to mainMenuPanel
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == use) {
                if (inventoryBox.getSelectedIndex() != -1 && petBox.getSelectedIndex() != -1) {
                    if (inventoryBox.getSelectedItem() instanceof Toy) {
                        player.play((Pet) petBox.getSelectedItem(), (Toy) inventoryBox.getSelectedItem());
                        inventoryBox.removeItemAt(inventoryBox.getSelectedIndex());
                    } else if (inventoryBox.getSelectedItem() instanceof Food) {
                        player.feed((Pet) petBox.getSelectedItem(), (Food) inventoryBox.getSelectedItem());
                        inventoryBox.removeItemAt(inventoryBox.getSelectedIndex());
                    }
                }
            } else if (e.getSource() == save) {
                frame.getContPanel().add(savePanel, "5");
                frame.getCard().show(frame.getContPanel(), "5");
            } else if (e.getSource() == quit) {
                frame.getCard().show(frame.getContPanel(), "1");
            }
            update();
        }
    }

    //ActionListener for buttons shop, managePet
    private class ManageListener implements ActionListener {
        //If shop is clicked, switch panel to shopPanel
        //If managePet is clicked, switch panel to managePetPanel
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == shop) {
                frame.getContPanel().add(shopPanel, "6");
                frame.getCard().show(frame.getContPanel(), "6");
            } else if (e.getSource() == managePet) {
                frame.getContPanel().add(managePetPanel, "7");
                frame.getCard().show(frame.getContPanel(), "7");
            }
            update();
        }
    }

    //ActionListener for buttons pet, scold and play
    private class InteractListener implements ActionListener {
        //If petBox has valid selection
        //If pet button is clicked, using player pet() on selected pet
        //If scold button is clicked, using player scold() on seleted pet
        //Is play button is clicked, using player play() on selected pet
        @Override
        public void actionPerformed(ActionEvent e) {
            if (petBox.getSelectedIndex() != -1) {
                Pet pet = (Pet) petBox.getSelectedItem();
                if (e.getSource() == GamePanel.this.pet) {
                    player.pet(pet);
                } else if (e.getSource() == scold) {
                    player.scold(pet);
                } else if (e.getSource() == play) {
                    player.play(pet);
                }
            }
            update();
        }
    }

    //Shop panel for player purchasing items
    private class ShopPanel extends JPanel implements ActionListener {
        private GroupLayout gp;
        private JLabel playerInfo;
        private JComboBox<String> itemBox;
        private JButton buy, back;
        private JLabel warning;

        //Constructor for shopPanel
        public ShopPanel() {
            gp = new GroupLayout(this);
            setLayout(gp);
            String text = "Player: " + player.getName() + " Balance: " + player.getMoney();
            playerInfo = new JLabel(text);
            
            //itemBox with a selection of all the items available for purchase
            itemBox = new JComboBox<>();
            itemBox.addItem("1. Ball");
            itemBox.addItem("2. Cookie");
            itemBox.addItem("3. Dry food");
            itemBox.addItem("4. Wet food");
            itemBox.addItem("5. Stuffed toy");
            itemBox.addItem("6. Teaser");
            buy = new JButton("Buy");
            buy.addActionListener(this);
            back = new JButton("Back");
            back.addActionListener(this);
            warning = new JLabel("You don't have enough money or your inventory is full");
            warning.setForeground(Color.RED);
            
            //Using group layout to set locations of components
            gp.setHorizontalGroup(gp.createSequentialGroup()
                    .addGroup(gp.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(playerInfo)
                            .addComponent(itemBox)
                            .addComponent(warning)
                            .addComponent(buy))
                    .addGroup(gp.createParallelGroup().addComponent(back))
            );

            gp.setVerticalGroup(gp.createSequentialGroup()
                    .addGroup(gp.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(playerInfo))
                    .addGroup(gp.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(itemBox))
                    .addGroup(gp.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(buy).addComponent(back))
                    .addGroup(gp.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(warning))
            );

            warning.setVisible(false);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String text = "Player: " + player.getName() + " Balance: " + player.getMoney();
            playerInfo.setText(text);
            //If buy button is clicked, check selected index of itemBox
            //Using switch case to add new item to player inventory
            if (e.getSource() == buy) {
                int index = itemBox.getSelectedIndex();
                Item item = new Ball();
                switch (index) {
                    case 0:
                        break;
                    case 1:
                        item = new Cookie();
                        break;
                    case 2:
                        item = new DryFood();
                        break;
                    case 3:
                        item = new WetFood();
                        break;
                    case 4:
                        item = new StuffedToy();
                        break;
                    case 5:
                        item = new Teaser();
                        break;
                }
                boolean purchase = player.purchaseItem(item);
                //If purchase is successful undisplay warning
                //Else display warning
                if (purchase) {
                    warning.setVisible(false);
                    inventoryBox.addItem(item);
                    text = "Player: " + player.getName() + " Balance: " + player.getMoney();
                    playerInfo.setText(text);
                } else {
                    warning.setVisible(true);
                }//If back button is clicked, switch panel to gamePanel
            } else if (e.getSource() == back) {
                frame.getCard().show(frame.getContPanel(), "4");
            }
            GamePanel.this.update();
        }
    }

    //ManagePetPanel is for managing pets (removing and adding)
    private class ManagePetPanel extends JPanel implements ActionListener {
        private GroupLayout gp;
        private JLabel managePet;
        private JComboBox<String> addPetBox;
        private JTextField nameField;
        private JButton add, remove, back;
        private JLabel warning1, warning2;

        //Constructor for managePetPanel
        public ManagePetPanel() {
            gp = new GroupLayout(this);
            setLayout(gp);

            //addPetBox is a JComboBox which contains every available pet
            //that can be added to player's pet list
            managePet = new JLabel("Remove current selected pet:");
            addPetBox = new JComboBox<>();
            addPetBox.addItem("1. Dog");
            addPetBox.addItem("2. Cat");
            addPetBox.addItem("3. Hamster");
            addPetBox.addItem("4. Horse");
            addPetBox.addItem("5. Chinchilla");

            nameField = new JTextField();
            add = new JButton("Add");
            add.addActionListener(this);
            remove = new JButton("Remove");
            remove.addActionListener(this);
            back = new JButton("Back");
            back.addActionListener(this);
            warning1 = new JLabel("Please enter a valid name");
            warning1.setForeground(Color.RED);
            warning1.setVisible(false);
            warning2 = new JLabel("You have reached the limit for number of pets");
            warning2.setForeground(Color.RED);
            warning2.setVisible(false);
            
            //Using group layout to set locations of 
            gp.setHorizontalGroup(gp.createSequentialGroup()
                    .addGroup(gp.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(managePet)
                            .addComponent(remove)
                            .addComponent(addPetBox)
                            .addComponent(nameField)
                            .addComponent(add)
                            .addComponent(back)
                            .addComponent(warning1)
                            .addComponent(warning2))
            );

            gp.setVerticalGroup(gp.createSequentialGroup()
                    .addGroup(gp.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(managePet))
                    .addGroup(gp.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(remove))
                    .addGroup(gp.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(addPetBox))
                    .addGroup(gp.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(nameField))
                    .addGroup(gp.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(add))
                    .addGroup(gp.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(back))
                    .addGroup(gp.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(warning1))
                    .addGroup(gp.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(warning2))
            );
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            //If add button is clicked, get index of selected item, 
            //using switch case to add new pet to player's petList
            if (e.getSource() == add) {
                warning1.setVisible(false);
                warning2.setVisible(false);
                if (!nameField.getText().equals("")) {
                    int index = addPetBox.getSelectedIndex();
                    Pet pet = new Dog(nameField.getText());
                    switch (index) {
                        case 0:
                            break;
                        case 1:
                            pet = new Cat(nameField.getText());
                            break;
                        case 2:
                            pet = new Hamster(nameField.getText());
                            break;
                        case 3:
                            pet = new Horse(nameField.getText());
                            break;
                        case 4:
                            pet = new Chinchilla(nameField.getText());
                            break;
                    }
                    boolean success = player.addPet(pet);
                    //If pet is successfully added, switch to gamePanel
                    //Else display warning
                    if (success) {
                        petBox.addItem(pet);
                        frame.getCard().show(frame.getContPanel(), "4");
                        GamePanel.this.update();
                    } else {
                        warning2.setVisible(true);
                    }
                } else {
                    warning1.setVisible(true);
                }//If remove button is clicked and petBox selection is not null
                //Remove pet from petList and petBox
            } else if (e.getSource() == remove) {
                if (petBox.getSelectedIndex() != -1) {
                    Pet pet = (Pet) petBox.getSelectedItem();
                    player.removePet(pet);
                    petBox.removeItemAt(petBox.getSelectedIndex());
                    frame.getCard().show(frame.getContPanel(), "4");
                    GamePanel.this.update();
                }//If back button is clicked, switch panel to gamePanel
            } else if (e.getSource() == back) {
                
                frame.getCard().show(frame.getContPanel(), "4");
                GamePanel.this.update();
            }
        }
    }

    //SavePanel for player to save their current game to the database
    private class SavePanel extends JPanel implements ActionListener {
        private GroupLayout gp;
        private JButton save, back;
        private JTextField nameField;
        private JLabel message, warning;

        //Constructor for savePanel
        public SavePanel() {
            gp = new GroupLayout(this);
            setLayout(gp);
            gp.setAutoCreateGaps(true);
            gp.setAutoCreateContainerGaps(true);

            save = new JButton("OK");
            save.addActionListener(this);
            back = new JButton("Back");
            back.addActionListener(this);
            nameField = new JTextField();
            message = new JLabel("Enter name of save");
            warning = new JLabel("Please enter a valid name");
            warning.setForeground(Color.RED);

            //Using group layout to set locations of components
            gp.setHorizontalGroup(gp.createSequentialGroup()
                    .addGroup(gp.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(message)
                            .addComponent(nameField)
                            .addComponent(warning)
                            .addComponent(save))
                    .addGroup(gp.createParallelGroup().addComponent(back))
            );

            gp.setVerticalGroup(gp.createSequentialGroup()
                    .addGroup(gp.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(message))
                    .addGroup(gp.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(nameField))
                    .addGroup(gp.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(save).addComponent(back))
                    .addGroup(gp.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(warning))
            );
            warning.setVisible(false);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            //If save button is clicked, and nameField is no empty, get player's
            //username, save name and the game to save to database
            //If name field is empty, display warning
            try {
                if (e.getSource() == save) {
                    String name = "";
                    name = nameField.getText();
                    if (name.equals("")) {
                        warning.setVisible(true);
                    } else {
                        frame.getConnection().save(frame.getUsername(), name, player);
                        nameField.setText("");
                        warning.setVisible(false);
                        frame.getCard().show(frame.getContPanel(), "4");
                    }//If back button is pressed, switch panel to gamePanel
                } else if (e.getSource() == back) {
                    nameField.setText("");
                    warning.setVisible(false);
                    frame.getCard().show(frame.getContPanel(), "4");
                }
            } catch (IndexOutOfBoundsException a) {
                warning.setVisible(true);
            }
        }
    }
}

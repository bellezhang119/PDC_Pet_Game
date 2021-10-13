package GUI;

import Items.*;
import Pets.*;
import main.Player;
import misc.FileIO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    GroupLayout westGP;
    GroupLayout eastGP;
    private ShopPanel shopPanel;
    private SavePanel savePanel;
    private ManagePetPanel managePetPanel;


    public GamePanel(MainFrame frame, Player player) {
        this.frame = frame;
        this.player = player;
        setLayout(new BorderLayout(10, 10));
        setPreferredSize(new Dimension(600, 500));

        westPanel = new JPanel();
        southPanel = new JPanel();
        eastPanel = new JPanel();

        playerInfo = new JLabel();
        petBox = new JComboBox<>();

        petInfo = new JTextArea();
        petInfo.setSize(50, 50);
        inventoryBox = new JComboBox<>();
        
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

    public void update() {
        String text = "Player: " + player.getName() + " Balance: " + player.getMoney();
        playerInfo.setText(text);
        petBox.removeAllItems();
        for (Pet p : player.getPetList()) {
            petBox.addItem(p);
        }
        inventoryBox.removeAllItems();
        for (Item i : player.getInventory()) {
            inventoryBox.addItem(i);
        }
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

    private class GeneralListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == use) {
                if (inventoryBox.getSelectedIndex() != -1 && petBox.getSelectedIndex() != -1) {
                    if (inventoryBox.getSelectedItem() instanceof Toy) {
                        player.play((Pet) petBox.getSelectedItem(), (Toy) inventoryBox.getSelectedItem());
                    } else if (inventoryBox.getSelectedItem() instanceof Food) {
                        player.feed((Pet) petBox.getSelectedItem(), (Food) inventoryBox.getSelectedItem());
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

    private class ManageListener implements ActionListener {
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

    private class InteractListener implements ActionListener {
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


    private class ShopPanel extends JPanel implements ActionListener {
        private GroupLayout gp;
        private JLabel playerInfo;
        private JComboBox<String> itemBox;
        private JButton buy, back;
        private JLabel warning;

        public ShopPanel() {
            gp = new GroupLayout(this);
            setLayout(gp);
            String text = "Player: " + player.getName() + " Balance: " + player.getMoney();
            playerInfo = new JLabel(text);
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
                if (purchase) {
                    warning.setVisible(false);
                } else {
                    warning.setVisible(true);
                }
            } else if (e.getSource() == back) {
                frame.getCard().show(frame.getContPanel(), "4");
            }
            GamePanel.this.update();
        }
    }

    private class ManagePetPanel extends JPanel implements ActionListener {
        private GroupLayout gp;
        private JComboBox<Pet> managePetBox;
        private JComboBox<String> addPetBox;
        private JTextField nameField;
        private JButton add, remove, back;
        private JLabel warning1, warning2;

        public ManagePetPanel() {
            gp = new GroupLayout(this);
            setLayout(gp);

            managePetBox = GamePanel.this.petBox;
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
            
            gp.setHorizontalGroup(gp.createSequentialGroup()
                    .addGroup(gp.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(managePetBox)
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
                            .addComponent(managePetBox))
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
            this.managePetBox = GamePanel.this.petBox;
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
                    if (success) {
                        frame.getCard().show(frame.getContPanel(), "4");
                        GamePanel.this.update();
                    } else {
                        warning2.setVisible(true);
                    }
                } else {
                    warning1.setVisible(true);
                }
            } else if (e.getSource() == remove) {
                if (managePetBox.getSelectedIndex() != -1) {
                    Pet pet = (Pet) managePetBox.getSelectedItem();
                    player.removePet(pet);
                    frame.getCard().show(frame.getContPanel(), "4");
                    GamePanel.this.update();
                }
            }    else if (e.getSource() == back) {
                
                frame.getCard().show(frame.getContPanel(), "4");
                GamePanel.this.update();
            }
        }
    }

    private class SavePanel extends JPanel implements ActionListener {
        private GroupLayout gp;
        private JButton save, back;
        private JTextField nameField;
        private JLabel message, warning;

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

        public void displayWarning() {
            warning.setVisible(true);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if (e.getSource() == save) {
                    String name = "";
                    name = nameField.getText();
                    if (name.equals("")) {
                        displayWarning();
                    } else {
                        frame.getConnection().save(frame.getUsername(), name, player);
                        nameField.setText("");
                        warning.setVisible(false);
                        frame.getCard().show(frame.getContPanel(), "4");
                    }
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

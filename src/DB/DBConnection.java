package DB;


import GUI.MainFrame;
import Items.Cookie;
import Items.DryFood;
import Items.Item;
import Items.StuffedToy;
import Items.Teaser;
import Items.WetFood;
import Pets.Cat;
import Pets.Chinchilla;
import Pets.Dog;
import Pets.Hamster;
import Pets.Horse;
import Pets.Pet;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.SwingUtilities;
import main.Player;


/*
 * This class is for connecting to derby embedded database, creating tables 
 * and inserting data.
 */

/**
 *
 * @author Belle Zhang
 */
public class DBConnection {
    public static Connection conn;
    public static String url="jdbc:derby:GameDB;create=true";
    Statement statement;
    
    //Constructor for DBConnection class, also connects to the database
    public DBConnection() {
        try {
            conn=DriverManager.getConnection(url);
            System.out.println("Connected");
            statement = conn.createStatement();
        } catch (SQLException e) {
            System.out.println(e);
        }    
    }
    
    //Creates a player table with a username and password column
    public void createPlayerTable() {
        try {
            String s = "CREATE TABLE player (username VARCHAR(20), password VARCHAR(20))";
            statement.executeUpdate(s);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    //Creates a save table with a username, name and file column
    public void createSaveTable() {
        try {
            String s = "CREATE TABLE save (username VARCHAR(20), name VARCHAR(20), file VARCHAR(10000))";
            statement.executeUpdate(s);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }    
    
    //Returns a list of usernames of players in the database
    public ArrayList<String> getUsernames() {
        ArrayList<String> usernames = new ArrayList<>();        
        try {
            ResultSet rs = statement.executeQuery("SELECT * FROM player");
            while (rs.next()) {
                usernames.add(rs.getString("username"));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return usernames;
    }
    
    //Takes a username and password and verifies if it matches
    public boolean verifyPassword(String username, String password) {
        try {
            ResultSet rs = statement.executeQuery("SELECT * FROM player");
            while (rs.next()) {
                //If the username and the password belongs to the same player
                //Return true
                if (rs.getString("username").equals(username) && rs.getString("password").equals(password)) {
                    return true;
                }
            }            
        } catch (SQLException e) {
            System.out.println(e);
        }
        //Returns false if no matches are found
        return false;
    }
    
    //Inserts data into the player table
    public boolean insertPlayer(String username, String password) {
        try { 
            if (!getUsernames().contains(username)) {
                String s = "INSERT INTO player VALUES ('" + username + "', '" + password + "')";
                statement.executeUpdate(s);
                return true;
            }
        
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }
    
    //Takes in a username, saveName and player in parameter and inserts
    //this data into the save table
    public void save(String username, String saveName, Player player) {
        //The string save belongs to the file column in the save table
        String save = player.getName() + "\n";
        save += player.getMoney() + "\n";
        
        //Inserting pet information
        for (Pet pet : player.getPetList()) {
            save += "[pet]" + " " + pet.getID() + " " + pet.getName() + " " + pet.getHealth() + " "
                        + pet.getHunger() + " " + pet.getBond() + " " + pet.getMoodCount() + "\n";
        }
        
        //Inserting inventory information
        for (Item item : player.getInventory()) {
            save += "[item]" + " " + item.getID() + " " + item.getDurability() + "\n";
        }
        
        try {
            //Inserts data
            String s = "INSERT INTO save VALUES ('" + username + "', '" + saveName + "', '" + save + "')";
            statement.executeUpdate(s);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    //Takes in a username and saveName and loads a save if a save with the same name
    //under a player with the same username exists
    public Player loadSave(String username, String saveName) {
        String save = "";
        
        try {
            ResultSet rs = statement.executeQuery("SELECT * FROM save");
            while (rs.next()) {
                //If input username exists in database
                if (rs.getString("username").equals(username)) {
                    //Get save
                    save = rs.getString("file");
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        
        //Unpacking save
        ArrayList<String> content = new ArrayList<>(Arrays.asList(save.split("\\r?\\n")));
        Player player = new Player(content.get(0));
        content.remove(0);
        player.setMoney(Double.parseDouble(content.get(0)));
        content.remove(0);
        
        for (String s : content) {
            String[] line = s.split(" ");
            if (line[0].equals("[pet]")) {
                switch (Integer.parseInt(line[1])) {
                    //Creating new pets according to ID with the information in txt file
                    //and adds to player petList
                    case 1:
                        player.getPetList().add(new Dog(line[2], Integer.parseInt(line[3]),
                                Integer.parseInt(line[4]), Integer.parseInt(line[5]), Integer.parseInt(line[6])));
                        break;
                    case 2:
                        player.getPetList().add(new Cat(line[2], Integer.parseInt(line[3]),
                                Integer.parseInt(line[4]), Integer.parseInt(line[5]), Integer.parseInt(line[6])));
                        break;
                    case 3:
                        player.getPetList().add(new Hamster(line[2], Integer.parseInt(line[3]),
                                Integer.parseInt(line[4]), Integer.parseInt(line[5]), Integer.parseInt(line[6])));
                        break;
                    case 4:
                        player.getPetList().add(new Horse(line[2], Integer.parseInt(line[3]),
                                Integer.parseInt(line[4]), Integer.parseInt(line[5]), Integer.parseInt(line[6])));
                        break;
                    case 5:
                        player.getPetList().add(new Chinchilla(line[2], Integer.parseInt(line[3]),
                                Integer.parseInt(line[4]), Integer.parseInt(line[5]), Integer.parseInt(line[6])));
                        break;
                }
            } else if (line[0].equals("[item]")) {
                switch(Integer.parseInt(line[1])) {
                    //Creating new items according to ID with the information in txt file
                    //and adds to player inventory
                    case 1:
                        player.getInventory().add(new WetFood(Integer.parseInt(line[2])));
                        break;
                    case 2:
                        player.getInventory().add(new DryFood(Integer.parseInt(line[2])));
                        break;
                    case 3:
                        player.getInventory().add(new StuffedToy(Integer.parseInt(line[2])));
                        break;
                    case 4:
                        player.getInventory().add(new Teaser(Integer.parseInt(line[2])));
                        break;
                    case 5:
                        player.getInventory().add(new Cookie(Integer.parseInt(line[2])));
                        break;
                    case 6:
                        player.getInventory().add(new Cookie(Integer.parseInt(line[2])));
                        break;
                 
                }
            }
        }
        
        return player;
    }

    //Main method for testing connectivity of database and inserting/retrieving data
    public static void main(String[] args) throws SQLException {
        DBConnection connection = new DBConnection();
        connection.insertPlayer("John", "123");
        System.out.println(connection.getUsernames());
        System.out.println(connection.verifyPassword("John", "123"));
        String s = "Drop table save";
        connection.statement.executeUpdate(s);
        connection.createSaveTable();
    }
}

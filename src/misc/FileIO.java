/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package misc;

import Items.*;
import Pets.*;
import main.*;
import java.io.*;
import java.util.ArrayList;

/**
 *
 * @author Belle Zhang
 */

public interface FileIO {

    //Method for turning a txt file into an arrayList of array of strings
    static ArrayList<String[]> readSave(String fileName) {
        ArrayList<String[]> content = new ArrayList<>();

        try {
            //Creating BufferedReader to read file
            BufferedReader inStream = new BufferedReader(new FileReader("./resources/" + fileName + ".txt"));

            String line;
            //Separates words in each line by space into an array of strings
            while ((line = inStream.readLine()) != null) {
                String[] s = line.split(" ");
                content.add(s);
            }
            inStream.close();
        } catch (IOException e) {
            //Catches IOException and prints out a warning that file does not exist
            System.out.println("This save does not exist");
        }
        return content;
    }

    //Method for saving player information into a txt file
    static void save(String fileName, Player player) {
        try {
            File file = new File("./resources/" + fileName + ".txt");
            file.createNewFile(); //Creates file with fileName if file does not exist
            //Creating PrintWriter to print to file
            PrintWriter pw = new PrintWriter("./resources/" + fileName + ".txt");

            //Writes player name into file
            pw.println(player.getName());
            //Writes player money into file
            pw.println(player.getMoney());

            //Writes pet details into file for each pet in player petList
            for (Pet pet : player.getPetList()) {
                pw.println("[pet]" + " " + pet.getID() + " " + pet.getName() + " " + pet.getHealth() + " "
                        + pet.getHunger() + " " + pet.getBond() + " " + pet.getMoodCount());
            }

            //Writes item details into file for each item in player inventory
            for (Item item : player.getInventory()) {
                pw.println("[item]" + " " + item.getID() + " " + item.getDurability());
            }

            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Method for loading save by reading an arrayList of arrays of strings and
    //loading information onto an instance of a player
    static Player loadSave(ArrayList<String[]> content) throws IndexOutOfBoundsException, NumberFormatException {
        //Sets player name to first line of txt file
        Player player = new Player(content.get(0)[0]);
        content.remove(0); //Removing first line
        //Sets player money to first line of txt file
        player.setMoney(Double.parseDouble(content.get(0)[0]));
        content.remove(0); //Removeing first line again

        //For each remaining line in txt file after the first two line
        for (String[] line : content) {
            //If line is information about pet
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
                }
            }
        }

        return player;
    }
}

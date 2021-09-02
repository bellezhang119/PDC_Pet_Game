/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package misc;

import Items.*;
import Pets.*;
import main
import java.io.*;
import java.util.ArrayList;

/**
 *
 * @author Belle Zhang
 */

public interface FileIO {

    static ArrayList<String[]> readSave(String fileName) {
        ArrayList<String[]> content = new ArrayList<>();

        try {
            BufferedReader inStream = new BufferedReader(new FileReader("./resources/" + fileName + ".txt"));

            String line;
            while ((line = inStream.readLine()) != null) {
                String[] s = line.split(" ");
                content.add(s);
            }
            inStream.close();
        } catch (IOException e) {
            System.out.println("This save does not exist");
        }
        return content;
    }

    static void save(String fileName, Player player) {
        try {
            File file = new File("./resources/" + fileName + ".txt");
            file.createNewFile();
            PrintWriter pw = new PrintWriter("./resources/" + fileName + ".txt");

            pw.println(player.getName());
            pw.println(player.getMoney());

            for (Pet pet : player.getPetList()) {
                pw.println("[pet]" + " " + pet.getID() + " " + pet.getName() + " " + pet.getHealth() + " "
                        + pet.getHunger() + " " + pet.getBond() + " " + pet.getMoodCount());
            }

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

    static Player loadSave(ArrayList<String[]> content) {
        Player player = new Player(content.get(0)[0]);
        content.remove(0);
        player.setMoney(Double.parseDouble(content.get(0)[0]));
        content.remove(0);

        for (String[] line : content) {
            if (line[0].equals("[pet]")) {
                switch (Integer.parseInt(line[1])) {
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

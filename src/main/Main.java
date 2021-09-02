/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import Items.*;
import Pets.*;
import misc.*;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author Belle Zhang
 */
public class Main implements FileIO {
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        int menu = 0;

        //Main menu
        loop: while(menu != 3) {
            try {
                //Prompting player to choose an option
                System.out.println("1. [Create new player] 2. [Load file] 3. [Quit]");
                menu = input.nextInt();
                input.nextLine();
                Player player;
                switch(menu) {
                    //If player enters 1, initialises player and prompts
                    //player to enter a name
                    case 1:
                        System.out.println("Enter new player name:");
                        String name = input.nextLine();
                        player = new Player(name);
                        gameMenu(player);
                        break;
                    //If player enters 2, prompts player for a file name, and loads
                    //information on that file to the instance of Player using FileIO    
                    case 2:
                        System.out.println("Enter file name (case sensitive):");
                        String fileName = input.nextLine();
                        player = FileIO.loadSave(FileIO.readSave(fileName));
                        gameMenu(player);
                        break;
                    //If player enters 3, quits program    
                    case 3:
                        break loop;
                }

            } catch (InputMismatchException e) {
                System.out.println("Invalid input");
                input.nextLine();
            } catch (IndexOutOfBoundsException | NumberFormatException e) {
                //If the file player entered is empty or unreadable, or does not exist,
                //this warning is printed
                System.out.println("Could not read save, please try another name or create a new player.");
            }
        }
    }

    //Game menu
    public static void gameMenu(Player player) {
        int menu = 0;

        //Game menu
        while (menu != 7) {
            try {
                //Prompting player to choose an option
                System.out.println("Name: " + player.getName() + " Balance: " + player.getMoney());
                System.out.println("1. [Interact with pet] 2. [Add new pet] 3. [Display pet list] " +
                        "4. [Display inventory] 5. [Purchase item] 6. [Save player] 7. [Quit to main menu]");
                menu = input.nextInt();

                switch (menu) {
                    //If player enters 1, calls petInteract()
                    case 1:
                        petInteract(player);
                        break;
                    //If player enters 2, calls addNewPet()    
                    case 2:
                        addNewPet(player);
                        break;
                    //If player enters 3, player petList is printed    
                    case 3:
                        System.out.println(player.getPetList());
                        break;
                    //If player enters 4, player inventory is printed    
                    case 4:
                        System.out.println(player.getInventory());
                        break;
                    //If player enters 5, purchaseItem() is called   
                    case 5:
                        purchaseItem(player);
                        break;
                    //If player enters 6, prompts player to enter save name,
                    //and FileIO.save() is called to save the game
                    case 6:
                        input.nextLine();
                        System.out.println("Please enter save name:");
                        String fileName = input.nextLine();
                        FileIO.save(fileName, player);
                        break;
                    case 7:
                        break;
                    default:
                        throw new InputMismatchException();
                }

            } catch (InputMismatchException e) {
                System.out.println("Invalid input");
                input.nextLine();
            }
        }
    }

    //Adds new pet to player's petList
    public static void addNewPet(Player player) {
        loop: while (true) {
            try {
                //Prompts player to choose an option
                System.out.println("Please select pet you want to add:");
                System.out.println("1. [Dog] 2. [Cat] 3. [Hamster]");
                int animal = input.nextInt();
                input.nextLine();
                System.out.println("Please enter pet name:");
                String name = input.nextLine();

                //A new pet is added according to the number player enters
                switch (animal) {
                    case 1:
                        player.addPet(new Dog(name));
                        break loop;
                    case 2:
                        player.addPet(new Cat(name));
                        break loop;
                    case 3:
                        player.addPet(new Hamster(name));
                        break loop;
                    default:
                       throw new InputMismatchException();
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input");
                input.nextLine();
            }
        }
    }

    //Adds new item to player's inventory if player has enough money
    public static void purchaseItem(Player player) {
        boolean success = false;
        loop: while (true) {
            try {
                //Prompts player to choose an option
                System.out.println("Balance: " + player.getMoney());
                System.out.println("Please select item you want to purchase:");
                System.out.println("1. [Stuffed toy] 2. [Dry food] 3. [Wet food] 4. [Quit to game menu]");
                int selection = input.nextInt();

                //Calls purchaseItem() according to number player enters
                //If the purchase is successful, success = true and method will end
                switch (selection) {
                    case 1:
                        success = player.purchaseItem(new StuffedToy());
                        break;
                    case 2:
                        success = player.purchaseItem(new DryFood());
                        break;
                    case 3:
                        success = player.purchaseItem(new WetFood());
                        break;
                    case 4:
                        break loop;
                }

                //If success = false (purcahse is unsuccessful) prints out this
                //message and program will end
                if (!success) {
                    System.out.println("You do not have enough money to purchase this item");
                }

            } catch (InputMismatchException e) {
                System.out.println("Invalid input");
                input.nextLine();
            }

        }
    }

    public static void petInteract(Player player) throws IndexOutOfBoundsException, InputMismatchException {
        if (player.getPetList().isEmpty()) {
            System.out.println("You don't have any pets to interact with.");
            return;
        }
        System.out.println("Please enter index to select pet to interact with:");
        int count = 0;
        for (Pet pet : player.getPetList()) {
            System.out.print(count + ": " + pet + " ");
            count++;
        }
        System.out.println();
        int index = input.nextInt();
        loop: while (true) {
            try {
                Pet pet = player.getPetList().get(index);
                System.out.println("Please select what you want to do with the pet:");
                System.out.println("1. [Pet] 2. [Scold] 3. [Play] 4. [Use item] 5. [Display stats] " +
                        "6. [Remove pet] 7. [Quit to pet menu]");
                int selection = input.nextInt();
                switch (selection) {
                    case 1:
                        player.pet(pet);
                        break;
                    case 2:
                        player.scold(pet);
                        break;
                    case 3:
                        player.play(pet);
                        break;
                    case 4:
                        petUseItem(player, pet);
                        break;
                    case 5:
                        pet.printStats();
                        break;
                    case 6:
                        player.getPetList().remove(pet);
                        break loop;
                    case 7:
                        break loop;
                    default:
                        throw new InputMismatchException();
                }
            } catch (IndexOutOfBoundsException | InputMismatchException e) {
                System.out.println("Invalid input");
                input.nextLine();
            }
        }
    }

    public static void petUseItem(Player player, Pet pet) throws IndexOutOfBoundsException, InputMismatchException {
        System.out.println("Please enter index to select item to use:");
        int count = 0;
        for (Item item : player.getInventory()) {
            System.out.print(count + ": " + item + " ");
            count++;
        }
        System.out.println();
        int index = input.nextInt();
        try {
            Item item = player.getInventory().get(index);
            if (item instanceof Toy) {
                player.play(pet, (Toy) item);
            } else if (item instanceof Food) {
                player.feed(pet, (Food) item);
            }
        } catch (IndexOutOfBoundsException | InputMismatchException e) {
            System.out.println("Invalid input");
            input.nextLine();
        }
    }
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import Items.*;
import Pets.*;

/**
 *
 * @author Belle Zhang
 */
import java.util.ArrayList;

public class Player {
    private String name;
    private ArrayList<Pet> petList; //A list of pets player owns
    private ArrayList<Item> inventory; //A list of items player owns
    private double money;

//    public Player() {
//        this.name = "UNKNOWN";
//        this.petList = new ArrayList<>();
//        this.inventory = new ArrayList<>();
//        this.money = 10.0;
//    }
    
    //Constructor
    public Player(String name) {
        this.name = name;
        this.petList = new ArrayList<>();
        this.inventory = new ArrayList<>();
        this.money = 10.0;
    }

    //Getter for player name
    public String getName() {
        return name;
    }

    //Setter for player name
    public void setName(String name) {
        this.name = name;
    }

    //Getter for player petList
    public ArrayList<Pet> getPetList() {
        return petList;
    }

    //Setter for player petList
    public void setPetList(ArrayList<Pet> petList) {
        this.petList = petList;
    }

    //Get method for petList using index
    public Pet getPet(int index) {
        return petList.get(index);
    }

    //Add method for petList
    public void addPet(Pet pet) {
        petList.add(pet);
    }

    //Remove method for petList using index
    public void removePet(int index) {
        this.petList.remove(index);
    }

    //Remove method for petList using instance
    public void removePet(Pet pet) {
        this.petList.remove(pet);
    }

    //Getter for player inventory
    public ArrayList<Item> getInventory() {
        return inventory;
    }

    //Setter for player inventory
    public void setInventory(ArrayList<Item> inventory) {
        this.inventory = inventory;
    }

    //Get method for inventory using index
    public Item getItem(int index) {
        return this.inventory.get(index);
    }

    //Add method for inventory
    public void addItem(Item item) {
        this.inventory.add(item);
    }

    //Remove method for inventory using index
    public void removeItem(int index) {
        this.inventory.remove(index);
    }

    //Remove method for inventory using instance
    public void removeItem(Item item) {
        this.inventory.remove(item);
    }

    //Getter for player money
    public double getMoney() {
        return money;
    }

    //Setter for player money
    public void setMoney(double money) {
        this.money = money;
    }

    //Method for purchasing item for player
    public boolean purchaseItem(Item item) {
        //Checks if player has enough money to buy item
        if (getMoney() >= item.getPrice()) {
            setMoney(getMoney() - item.getPrice());
            addItem(item);
            return true; //Returns true if item is bought
        } else {
            return false; //Returns false if item cannot be bought
        }
    }

    //Pets a pet
    public void pet(Pet pet) {
        pet.setBond(pet.getBond() + 5);
        pet.setMoodCount(pet.getMoodCount() + 5);
        this.money+=5; //Adds money of player
    }

    //Scolds a pet
    public void scold(Pet pet) {
        pet.setBond(pet.getBond() - 10);
        pet.setMoodCount(pet.getMoodCount() - 5);
        pet.setHunger(pet.getHunger() - 5);
    }

    //Plays with pet
    public void play(Pet pet) {
        pet.setBond(pet.getBond() + 5);
        pet.setMoodCount(pet.getMoodCount() + 5);
        pet.setHunger(pet.getHunger() - 5);
        this.money+=5;
    }

    //Plays with pet with a toy
    public void play(Pet pet, Toy toy) {
        pet.setMoodCount(pet.getMoodCount() + toy.getMoodEffect());
        pet.setBond(pet.getBond() + toy.getBondEffect());
        toy.setDurability(toy.getDurability() - 1);
        pet.setHunger(pet.getHunger() - 10);
        if (toy.getDurability() <= 0) {
            inventory.remove(toy);
        }
        this.money+=20;
    }

    //Feeds pet with a food
    public void feed(Pet pet, Food food) {
        pet.setHunger(pet.getHunger() + food.getHungerEffect());
        pet.setMoodCount(pet.getMoodCount() + food.getMoodEffect());
        pet.setBond(pet.getBond() + food.getBondEffect());
        food.setDurability(food.getDurability() - 1);
        if (food.getDurability() <= 0) {
            inventory.remove(food);
        }
        this.money+=10;
    }

    //Prints stats of player
    public void printStats() {
        System.out.println("Player: " + name);
        System.out.println("Pet list:");
        System.out.println(petList);
        System.out.println("Inventory:");
        System.out.println(inventory);
    }
}


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pets;

/**
 *
 * @author Belle Zhang
 */
public class Cat extends Pet{

    //Constructor for creating a new cat
    public Cat(String name) {
        this.name = name;
        this.health = 100;
        this.hunger = 100;
        this.bond = 0;
        this.moodCount = 50;
        this.ID = 2;
    }

    //Constructor for creating a new cat
    public Cat() {
        this.name = "UNKNOWN";
        this.health = 100;
        this.hunger = 100;
        this.bond = 0;
        this.moodCount = 50;
        this.ID = 2;
    }

    //Constructor for loading save file
    public Cat(String name, int health, int hunger, int bond, int moodCount) {
        super(name, health, hunger, bond, moodCount);
        this.ID = 2;
    }
}

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
public class Dog extends Pet {

    //Constructor for creating a new dog
    public Dog(String name) {
        this.name = name;
        this.health = 100;
        this.hunger = 80;
        this.bond = 10;
        this.moodCount = 70;
        this.ID = 1;
    }

    //Constructor for creating a new dog
    public Dog() {
        this.name = "UNKNOWN";
        this.health = 100;
        this.hunger = 80;
        this.bond = 10;
        this.moodCount = 70;
        this.ID = 1;
    }

    //Constructor for loading save file
    public Dog (String name, int health, int hunger, int bond, int moodCount) {
        super(name, health, hunger, bond, moodCount);
        this.ID = 1;
    }

}

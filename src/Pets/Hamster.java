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
public class Hamster extends Pet {

    //Constructor for creating a new hamster
    public Hamster(String name) {
        this.name = name;
        this.health = 100;
        this.hunger = 80;
        this.bond = 0;
        this.moodCount = 50;
        this.ID = 3;
    }

    //Constructor for loading save file
    public Hamster(String name, int health, int hunger, int bond, int moodCount) {
        super(name, health, hunger, bond, moodCount);
        this.ID = 3;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pets;

import misc.Mood;

/**
 *
 * @author Belle Zhang
 */

public abstract class Pet {
    protected String name;
    protected int health;
    protected int hunger;
    protected int bond;
    protected Mood mood;
    protected int moodCount;
    protected boolean alive;
    protected int ID;

    //Default contructor
    public Pet () {
    }

    //Contructor for loading save file
    public Pet(String name, int health, int hunger, int bond, int moodCount) {
        this.name = name;
        this.health = health;
        this.hunger = hunger;
        this.bond = bond;
        this.moodCount = moodCount;
        for (Mood mood : Mood.values()) {
            if (moodCount >= mood.getMoodCount()) {
                this.mood = mood;
            }
        }
    }

    //Getter for pet name
    public String getName() {
        return name;
    }

    //Setter for pet name
    public void setName(String name) {
        this.name = name;
    }

    //Getter for pet health
    public int getHealth() {
        return health;
    }

    //Setter for pet health
    public void setHealth(int health) {
        this.health = health;
        //Ensures pet health is always within 0 and 100
        if (this.health > 100) {
            this.health = 100;
        } else if (this.health < 0) {
            this.health = 0;
            setMood(Mood.DEPRESSED);
            setAlive(false);
        }
    }

    //Getter for pet hunger
    public int getHunger() {
        return hunger;
    }

    //Setter for pet hunger
    public void setHunger(int hunger) {
        this.hunger = hunger;
        //Ensures pet hunger is always within 0 and 100
        if (this.hunger > 100) {
            this.hunger = 100;
        } else if (this.hunger < 0) {
            this.hunger = 0;
            setHealth(getHealth()-10);
            setMoodCount(getMoodCount() - 30);
        }
    }

    //Getter for pet bond
    public int getBond() {
        return bond;
    }

    //Setter for pet bond
    public void setBond(int bond) {
        this.bond = bond;
        //Ensures pet bond is always within 0 and 100
        if (this.bond > 100) {
            this.bond = 100;
        } else if (this.bond < 0) {
            this.bond = 0;
        }
    }

    //Getter for pet mood
    public Mood getMood() {
        return mood;
    }

    //Setter for pet mood
    public void setMood(Mood mood) {
        this.mood = mood;
    }

    //Updates pet mood according to moodCount
    public void updateMood() {
        for (Mood mood : Mood.values()) {
            if (moodCount >= mood.getMoodCount()) {
                this.mood = mood;
            }
        }
    }

    //Getter for moodCount
    public int getMoodCount() {
        return moodCount;
    }

    //Setter for moodCount
    public void setMoodCount(int moodCount) {
        this.moodCount = moodCount;
        //Ensures moodCount is always within 0 and 100
        if (this.moodCount > 100) {
            this.moodCount = 100;
        } else if (this.moodCount < 0) {
            this.moodCount = 0;
        }
        //Calls updateMood() to update pet mood whenever moodCount is changed
        updateMood();
    }

    //Getter for pet alive
    public boolean isAlive() {
        return alive;
    }

    //Setter for pet alive
    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    //Getter for pet ID
    public int getID() {
        return ID;
    }

    //Prints pet stats including name, health, hunger, bond and mood
    public void printStats() {
        System.out.println(name + " (" + this.getClass().getSimpleName() + "):");
        System.out.println("Health: (" + getHealth() + "%)");
        for (int i = 0; i < getHealth(); i+=2) {
            System.out.print("|");
        }
        System.out.println();

        System.out.println("Hunger: (" + getHunger() + "%)");
        for (int i = 0; i < getHunger(); i+=2) {
            System.out.print("|");
        }
        System.out.println();

        System.out.println("Bond: (" + getBond() + "%)");
        for (int i = 0; i < getBond(); i+=2) {
            System.out.print("|");
        }
        System.out.println();

        System.out.println("Mood: " + getMood());
    }

    //toString for pet including pet name
    @Override
    public String toString() {
        return this.name + " (" + this.getClass().getSimpleName() + ")";
    }
}
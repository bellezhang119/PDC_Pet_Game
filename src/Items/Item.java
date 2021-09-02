/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Items;

/**
 *
 * @author Belle Zhang
 */
public abstract class Item {

    protected int price;
    protected int moodEffect;
    protected int bondEffect;
    protected int durability;
    protected int ID;

    //Constructor for new item
    public Item() {
        this.price = 10;
        this.moodEffect = 10;
        this.bondEffect = 10;
        this.durability = 10;
    }

    //Contructor for loading save file
    public Item(int durability) {
        this.price = 10;
        this.moodEffect = 10;
        this.bondEffect = 10;
        this.durability = durability;
    }

    //Getter for item price
    public int getPrice() {
        return price;
    }

    //Getter for item moodEffect
    public int getMoodEffect() {
        return moodEffect;
    }

    //Getter for item bondEffect
    public int getBondEffect() {
        return bondEffect;
    }

    //Getter for item durability
    public int getDurability() {
        return durability;
    }

    //Setter for item durability
    public void setDurability(int durability) {
        this.durability = durability;
    }

    //Getter for item ID
    public int getID() {
        return ID;
    }

    //toString for item
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}

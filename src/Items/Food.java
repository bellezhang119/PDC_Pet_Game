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
public abstract class Food extends Item {
    protected int hungerEffect;

    //Constructor for new food
    public Food() {
        super();
        this.hungerEffect = 10;
        this.durability = 1;
    }

    //Constructor for loading save file
    public Food(int durability) {
        super(durability);
        this.hungerEffect = 10;
    }

    //Getter for hungerEffect
    public int getHungerEffect() {
        return hungerEffect;
    }
}

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
public class WetFood extends Food{

    //Constructor for creating a new Wet food
    public WetFood() {
        super();
        this.hungerEffect = 20;
        this.durability = 2;
        this.ID = 1;
    }

    //Constructor for loading save file
    public WetFood(int durability) {
        super(durability);
        this.hungerEffect = 20;
        this.ID = 1;
    }

    //toString for Wet food
    @Override
    public String toString() {
        return "Wet Food";
    }
}

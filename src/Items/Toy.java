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
public abstract class Toy extends Item {

    //Constructor for new toy
    public Toy() {
        super();
        this.durability = 10;
    }

    //Constructor for loading save file
    public Toy(int durability) {
        super(durability);
    }
}

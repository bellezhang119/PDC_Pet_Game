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
public class StuffedToy extends Toy{

    //Constructor for creating a new Stuffed toy
    public StuffedToy() {
        this.moodEffect = 20;
        this.ID = 3;
    }

    //Constructor for loading save file
    public StuffedToy(int durability) {
        super(durability);
        this.moodEffect = 20;
        this.ID = 3;
    }

    //toString for Stuffed toy
    @Override
    public String toString() {
        return "Stuffed Toy";
    }
}

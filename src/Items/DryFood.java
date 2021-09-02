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
public class DryFood extends Food {

    //Constructor for creating a new Dry food
    public DryFood() {
        super();
        this.ID = 2;
    }

    //Constructor for loading save file
    public DryFood(int durability) {
        super(durability);
        this.ID = 2;
    }

    //toString for Dry food
    @Override
    public String toString() {
        return "Dry Food";
    }
}

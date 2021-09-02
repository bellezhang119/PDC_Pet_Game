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
public class Teaser extends Toy {
    
    //Constructor for creating a new Teaser
    public Teaser() {
        super();
        this.moodEffect = 20;
        this.durability = 30;
        this.ID = 4;
    }
    
    //Constructor for loading save file
    public Teaser(int durability) {
        super(durability);
        this.moodEffect = 20;
        this.ID = 4;
    }
    
    //toString for Teaser
    @Override
    public String toString() {
        return "Teaser";
    }
}

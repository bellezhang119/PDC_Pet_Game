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
public class Ball extends Toy {
    
    //Constructor for creating a new Ball
    public Ball() {
        super();
        this.ID = 6;
    }
    
    //COnstructor for loading save file
    public Ball(int durability) {
        super(durability);
        this.ID = 6;
    }
    
    //toString for Ball
    @Override
    public String toString() {
        return "Ball";
    }
}

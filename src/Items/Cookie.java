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
public class Cookie extends Food {
    
    //Constructor for creating a new Cookie
    public Cookie() {
        super();
        this.price = 1;
        this.hungerEffect = 5;
        this.durability = 1;
        this.ID = 5;
    }
    
    //Constructor for loading save file
    public Cookie(int durability) {
        super(durability);
        this.price = 1;
        this.hungerEffect = 5;
        this.ID = 5;
    }
    
    //toString for Cookie
    @Override
    public String toString() {
        return "Cookie";
    }
}

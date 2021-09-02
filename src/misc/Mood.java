/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package misc;

/**
 *
 * @author Belle Zhang
 */
public enum Mood {
    //Constants for mood used by pet
    DEPRESSED(10), SAD(30), NEUTRAL(50), HAPPY(70), CHEERFUL(100);

    //moodCount determins which mood pet has
    private final int moodCount;

    //Constructor
    Mood(int moodCount) {
        this.moodCount = moodCount;
    }

    //Getter for moodCount
    public int getMoodCount() {
        return moodCount;
    }
}

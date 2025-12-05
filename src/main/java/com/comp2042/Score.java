package com.comp2042;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Represents the player's score in the game
 * <p>
 * The {@code Score} class encapsulates the score as a JavaFX {@link IntegerProperty}, 
 * allowing it to be bound directly to UI components (e.g., {@link javafx.scene.control.Label}) for automatic when the score changes.
 * 
 * <p>
 * Provides methods to increments and reset the score
 * 
 */
public final class Score {
    private final IntegerProperty score = new SimpleIntegerProperty(0);

    /** Creates a new {@code Score} instance with the score initialized to zero */
    public Score() {}

    /**
     * Returns the score property
     * <p>
     * This property can be bound to UI elements to reflect score changes automatically.
     * 
     * @return the {@link IntegerProperty} representing the score
     */
    public IntegerProperty scoreProperty() {
        return score;
    }

    /**
     * Adds the specified number of points to the score
     * @param i the number of points to add
     */
    public void add(int i){
        score.setValue(score.getValue() + i);
    }

    /**
     * Resets the score to zero
     */
    public void reset() {
        score.setValue(0);
    }
}

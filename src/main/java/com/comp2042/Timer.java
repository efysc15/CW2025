package com.comp2042;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Represents a simple countdown timer for the game
 * <p>
 * The {@code Timer} class tracks remaining time in seconds using a JavaFX {@link IntegerProperty},
 * allowing it to be bound directly to UI components (e.g., {@link javafx.scene.control.Label}) for automatic updated when the timer changes
 * 
 * <p>
 * Provides methods to start, tick (decrement), and reset the timer
 */
public class Timer {
    private final IntegerProperty seconds = new SimpleIntegerProperty(0);

    /** Creates a new {@code Timer} instance with the countdown initialized to zero */
    public Timer() {}
    
    /**
     * Returns the seconds property
     * <p>
     * This property can be bound to UI elements to reflect countdown changes automatically
     * 
     * @return the {@link IntegerProperty} representing the remaining seconds
     */
    public IntegerProperty secondsProperty() {
        return seconds;
    }

    /**
     * Starts the timer with the given number of seconds
     * @param startSeconds the initial countdown value in seconds
     */
    public void start (int startSeconds) {
        seconds.set(startSeconds);
    }

    /**
     * Decrements the timer by one second if greater than zero
     * <p>
     * This method should be called periodically (e.g., once per second) to update the countdown
     * 
     */
    public void tick() {
        if (seconds.get() > 0) {
            seconds.set(seconds.get() - 1);
        }
    }

    /**
     * Resets the timer to zero
     */
    public void reset() {
        seconds.set(0);
    }
}

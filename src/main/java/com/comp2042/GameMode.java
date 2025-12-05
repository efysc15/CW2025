package com.comp2042;

/**
 * Defines the available game mode 
 * <p> 
 * Each mode provides a different style of gameplay: 
 * <ul>
 *  <li> {@link #CLASSIC} - Endless play without a time limit </li>
 *  <li> {@link #TWO_MINUTES} - Timed mode where the player must survive within 120 seconds </li>
 * </ul>
 */
public enum GameMode {
    
    /** Classic endless mode where the player continues until the game is over */
    CLASSIC ("Classic Mode - Endless play"),
    /** Timed mode where the player must survive as long as possible within 120 seconds */
    TWO_MINUTES ("2 Minutes Mode - Survive as long as possible within 120 seconds");

    /** A human-readable description of the game Mode */
    private final String description;

    /**
     * Constructs a new {@code GameMode} with the given description
     * @param description a short description of the mode
     */
    GameMode(String description) {
        this.description = description;
    }

    /**
     * Returns the description of the game mode
     * @return the description string
     */
    public String getDescription() {
        return description;
    }
}

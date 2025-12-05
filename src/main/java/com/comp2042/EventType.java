package com.comp2042;

/**
 * Represents the type of an event in the game 
 * <p>
 * The {@code EventType} enum defines the possible actions that can be triggered during gameplay:
 * <ul>
 *  <li>{@link #DOWN} - Move the current brick downwards</li>
 *  <li>{@link #LEFT} - Move the current brick to the left</li>
 *  <li>{@link #RIGHT} - Move the current brick to the right</li>
 *  <li>{@link #ROTATE} - Rotate the current brick</li>
 * </ul>
 */
public enum EventType {
    /** Event indicating the brick should move downward */
    DOWN, 
    /** Event indicating the brick should move one step to the left */
    LEFT, 
    /** Event indicating the brick should move one step to the right */
    RIGHT, 
    /** Event indicating the brick should rotate to its next orientation */
    ROTATE
}

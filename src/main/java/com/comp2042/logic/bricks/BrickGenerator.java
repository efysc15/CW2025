package com.comp2042.logic.bricks;

import java.util.List;

/**
 * Defines the contract for generating bricks in the game
 * <p>
 * A {@code BrickGenerator} is responsible for supplying new bricks to the board and providing previews of upcoming bricks.
 * Implementations (e.g., {@link RandomBrickGenerator}) determine the sequence of bricks.
 * 
 */
public interface BrickGenerator {

    /**
     * Returns the next brick to be placed on the board
     * <p> This method consumes the brick from the generator's sequence. 
     * @return the next {@link Brick} to spawn
     */
    Brick getBrick();

    /**
     * Returns the immediate upcoming brick without consuming it
     * <p> Useful for previewing the next bricks in the UI 
     * @return the next {@link Brick} in the queue
     */
    Brick getNextBrick();

    /**
     * Returns the queue of upcoming bricks
     * <p> Typically used to display a preview of the next few bricks (e.g., the next 4 bricks)
     * @return a list of {@link Brick} objects representing the upcoming sequence
     */
    List<Brick> getNextBricksQueue();
}

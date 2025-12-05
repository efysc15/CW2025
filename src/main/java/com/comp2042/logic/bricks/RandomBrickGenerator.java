package com.comp2042.logic.bricks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * A random brick generator for the game
 * <p>
 * The {@code RandomBrickGenerator} is a concrete implementation of {@link BrickGenerator} that supplies random tetromino bricks to the board
 * It maintains a queue of upcoming bricks to support both the current brick and preview functionality.
 * 
 * <p> The generator initializes with a queue of 5 bricks:
 * <ul>
 *  <li>1 current brick</li>
 *  <li>4 preview brick</li>
 * </ul>
 * <p> Each time a brick is consumed, a new random brick is appended to the queue.
 */
public class RandomBrickGenerator implements BrickGenerator {

    /** List of all possible types (I, J, L, O, S, T, Z) */
    private final List<Brick> brickList;
    /** Queue of upcoming bricks, including the current and preview bricks */
    private final List<Brick> nextBricks = new ArrayList<>();

    /**
     * Constructs a new {@code RandomBrickGenerator} and initializes the queue with a current brick and 4 preview bricks
     */
    public RandomBrickGenerator() {
        brickList = new ArrayList<>();
        brickList.add(new IBrick());
        brickList.add(new JBrick());
        brickList.add(new LBrick());
        brickList.add(new OBrick());
        brickList.add(new SBrick());
        brickList.add(new TBrick());
        brickList.add(new ZBrick());

        // Initialize queue with 5 bricks (1 current + 4 preview) 
        for (int i = 0; i < 5; i++) {
            nextBricks.add(brickList.get(ThreadLocalRandom.current().nextInt(brickList.size())));
        }
    }

    /**
     * Retrieves and removes the current brick from the queue
     * <p>
     * After removal, a new random brick is appended to the end of the queue to maintain preview list.
     * 
     * @return the current {@link Brick} to be places on the board
     */
    @Override
    public Brick getBrick() {
        // Get the head of the queue (current brick) and remove it 
        Brick currentBrick = nextBricks.remove(0);

        // Add new random brick to end of queue
        nextBricks.add(brickList.get(ThreadLocalRandom.current().nextInt(brickList.size())));

        return currentBrick;
    }

    /**
     * Returns the next immediate brick in the queue without removing it
     * @return the upcoming {@link Brick} after the current one
     */
    @Override
    public Brick getNextBrick() {
        // Peek at the next immediate brick (index 0 after the current brick is returned)
        return nextBricks.get(0);
    }

    /**
     * Returns an unmodifiable view of the next 4 bricks in the queue
     * <p>
     * This list of typically used for previewing upcoming bricks in the game UI.
     * 
     * @return a list of up to 4 {@link Brick} objects representing the preview queue
     */
    @Override
    public List<Brick> getNextBricksQueue() {
        // Returns a safe, unmodifiable list of the next 4 bricks in the queue 
        return Collections.unmodifiableList(nextBricks.subList(0, Math.min(nextBricks.size(), 4)));
    }
}

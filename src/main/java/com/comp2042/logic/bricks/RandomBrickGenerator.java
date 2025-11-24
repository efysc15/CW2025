package com.comp2042.logic.bricks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class RandomBrickGenerator implements BrickGenerator {

    private final List<Brick> brickList;

    // Using ArrayList ensures it implements the List interface for compatibility
    private final List<Brick> nextBricks = new ArrayList<>();

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

    @Override
    public Brick getBrick() {
        // Get the head of the queue (current brick) and remove it 
        Brick currentBrick = nextBricks.remove(0);

        // Add new random brick to end of queue
        nextBricks.add(brickList.get(ThreadLocalRandom.current().nextInt(brickList.size())));

        return currentBrick;
    }

    @Override
    public Brick getNextBrick() {
        // Peek at the next immediate brick (index 0 after the current brick is returned)
        return nextBricks.get(0);
    }

    @Override
    public List<Brick> getNextBricksQueue() {
        // Returns a safe, unmodifiable list of the next 4 bricks in the queue 
        return Collections.unmodifiableList(nextBricks.subList(0, Math.min(nextBricks.size(), 4)));
    }
}

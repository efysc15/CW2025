package com.comp2042.logic.bricks;

import java.util.List;

public interface BrickGenerator {

    Brick getBrick();

    Brick getNextBrick();

    // Get the list of next 4 bricks 
    List<Brick> getNextBricksQueue();
}

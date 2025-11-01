package com.comp2042.logic.bricks;

import java.util.ArrayList;
import java.util.List;

public final class OBrick extends Brick {

    public OBrick() {
        super(4, createShape());
    }

    private static List <int[][]> createShape() { 
    List<int[][]> brickMatrix = new ArrayList<>();

        brickMatrix.add(new int[][]{
                {0, 0, 0, 0},
                {0, 4, 4, 0},
                {0, 4, 4, 0},
                {0, 0, 0, 0}
        });

        return brickMatrix;
    }
}

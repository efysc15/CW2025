package com.comp2042.logic.bricks;

import java.util.ArrayList;
import java.util.List;

public final class SBrick extends Brick {

    public SBrick() {
        super(5, createShape());
    }

    private static List <int[][]> createShape() { 
    List<int[][]> brickMatrix = new ArrayList<>();

        brickMatrix.add(new int[][]{
                {0, 0, 0, 0},
                {0, 5, 5, 0},
                {5, 5, 0, 0},
                {0, 0, 0, 0}
        });
        brickMatrix.add(new int[][]{
                {5, 0, 0, 0},
                {5, 5, 0, 0},
                {0, 5, 0, 0},
                {0, 0, 0, 0}
        });

        return brickMatrix;
    }
}

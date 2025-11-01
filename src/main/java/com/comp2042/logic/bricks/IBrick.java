package com.comp2042.logic.bricks;

import java.util.ArrayList;
import java.util.List;

public final class IBrick extends Brick {

    public IBrick() {
        super(1, createShape());
    }

    private static List <int[][]> createShape() {
    List<int[][]> brickMatrix = new ArrayList<>();

        brickMatrix.add(new int[][]{
                {0, 0, 0, 0},
                {1, 1, 1, 1},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        });
        brickMatrix.add(new int[][]{
                {0, 1, 0, 0},
                {0, 1, 0, 0},
                {0, 1, 0, 0},
                {0, 1, 0, 0}
        });

        return brickMatrix;
    }
}

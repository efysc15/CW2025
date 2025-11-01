package com.comp2042.logic.bricks;

import java.util.ArrayList;
import java.util.List;

public final class ZBrick extends Brick {

    public ZBrick() {
        super(7, createShape());
    }

    private static List <int[][]> createShape() { 
    List<int[][]> brickMatrix = new ArrayList<>();

        brickMatrix.add(new int[][]{
                {0, 0, 0, 0},
                {7, 7, 0, 0},
                {0, 7, 7, 0},
                {0, 0, 0, 0}
        });
        brickMatrix.add(new int[][]{
                {0, 7, 0, 0},
                {7, 7, 0, 0},
                {7, 0, 0, 0},
                {0, 0, 0, 0}
        });

        return brickMatrix;
    }
}

package com.comp2042.logic.bricks;

import java.util.ArrayList;
import java.util.List;

public final class LBrick extends Brick {

        public LBrick() {
                super(3, createShape());
        }

        private static List <int[][]> createShape() {
        List<int[][]> brickMatrix = new ArrayList<>();

                brickMatrix.add(new int[][]{
                        {0, 0, 0, 0},
                        {0, 3, 3, 3},
                        {0, 3, 0, 0},
                        {0, 0, 0, 0}
                });
                brickMatrix.add(new int[][]{
                        {0, 0, 0, 0},
                        {0, 3, 3, 0},
                        {0, 0, 3, 0},
                        {0, 0, 3, 0}
                });
                brickMatrix.add(new int[][]{
                        {0, 0, 0, 0},
                        {0, 0, 3, 0},
                        {3, 3, 3, 0},
                        {0, 0, 0, 0}
                });
                brickMatrix.add(new int[][]{
                        {0, 3, 0, 0},
                        {0, 3, 0, 0},
                        {0, 3, 3, 0},
                        {0, 0, 0, 0}
                });

                return brickMatrix;
        }
}

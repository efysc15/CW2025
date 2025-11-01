package com.comp2042.logic.bricks;

import java.util.ArrayList;
import java.util.List;

public final class TBrick extends Brick {
        public TBrick() {
                super(6, createShape());
        }

        private static List <int[][]> createShape() {
        final List<int[][]> brickMatrix = new ArrayList<>();

                brickMatrix.add(new int[][]{
                        {0, 0, 0, 0},
                        {6, 6, 6, 0},
                        {0, 6, 0, 0},
                        {0, 0, 0, 0}
                });
                brickMatrix.add(new int[][]{
                        {0, 6, 0, 0},
                        {0, 6, 6, 0},
                        {0, 6, 0, 0},
                        {0, 0, 0, 0}
                });
                brickMatrix.add(new int[][]{
                        {0, 6, 0, 0},
                        {6, 6, 6, 0},
                        {0, 0, 0, 0},
                        {0, 0, 0, 0}
                });
                brickMatrix.add(new int[][]{
                        {0, 6, 0, 0},
                        {6, 6, 0, 0},
                        {0, 6, 0, 0},
                        {0, 0, 0, 0}
                });
                
                return brickMatrix;
        }
}

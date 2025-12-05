package com.comp2042.logic.bricks;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the "J" shaped brick (tetromino)
 * <p> 
 * The {@code JBrick} is a concrete subclass of {@link Brick} that defines the shape and rotations of the J-shaped tetromino: 
 * <ul>
 *      <li>Horizontal line of 3 blocks with 1 block attached below on the right</li>
 *      <li>Vertical line of 3 blocks with 1 block attached to the left at the bottom</li>
 *      <li>Horizontal line of 3 blocks with 1 block attached above on the left</li>
 *      <li>Vertical line of 3 blocks with 1 block attached to the right at the top</li>
 * </ul>
 * <p>
 * The shape is stored as a list of 2D integer matrices, each representing one of the 4 rotation states 
 * 
 */
public final class JBrick extends Brick {

        /**
         * Constructs a new {@code JBrick} with its predefined shape and rotation states
         */
        public JBrick() {
                super(2, createShape());
        }

        /**
         * Creates the shape matrics for the J-shaped brick
         * <p>
         * The list contains 4 rotation states, covering all possible orientations of the J tetromino
         * 
         * @return a list of 2D integer arrays representing the JBrick's rotations
         */
        private static List <int[][]> createShape() { 
        List<int[][]> brickMatrix = new ArrayList<>();

        // Horizontal line with block on the right 
        brickMatrix.add(new int[][]{
                {0, 0, 0, 0},
                {2, 2, 2, 0},
                {0, 0, 2, 0},
                {0, 0, 0, 0}
        });
        // Vertical line with block at the bottom left
        brickMatrix.add(new int[][]{
                {0, 0, 0, 0},
                {0, 2, 2, 0},
                {0, 2, 0, 0},
                {0, 2, 0, 0}
        });
        // Horizontal line with block on the left
        brickMatrix.add(new int[][]{
                {0, 0, 0, 0},
                {0, 2, 0, 0},
                {0, 2, 2, 2},
                {0, 0, 0, 0}
        });
        // Vertical line with block at the top right
        brickMatrix.add(new int[][]{
                {0, 0, 2, 0},
                {0, 0, 2, 0},
                {0, 2, 2, 0},
                {0, 0, 0, 0}
        });

        return brickMatrix;
    }
}

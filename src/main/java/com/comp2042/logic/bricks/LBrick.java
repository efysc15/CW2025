package com.comp2042.logic.bricks;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the "L" shaped brick (tetromino)
 * <p>
 * The {@code LBrick} is a concrete subclass of {@link Brick} that defines the shape and rotations of the L-shaped tetromino:
 * <ul>
 *      <li>Horizontal line of 3 blocks with 1 block attached below on the left</li>
 *      <li>Vertical line of 3 blocks with 1 block attached to the right at the bottom</li>
 *      <li>Horizontal line of 3 blocks with 1 block attached above on the right</li>
 *      <li>Vertical line of 3 blocks with 1 block attached to the left at the top</li>
 * </ul>
 * <p>
 * The shape is stored as a list of 2D integer matrices, each representing one of the 4 states
 * 
 */
public final class LBrick extends Brick {

        /**
         * Constructs a new {@code LBrick} with its predefined shape and rotation states
         */
        public LBrick() {
                super(3, createShape());
        }

        /**
         * Creates the shape matrices for the L-shaped brick
         * <p>
         * The list contains 4 rotation states, covering all possible orientations of the L tetromino.
         * 
         * @return a list of 2D integer arrays representing the LBrick's rotation
         */
        private static List <int[][]> createShape() {
        List<int[][]> brickMatrix = new ArrayList<>();

                // Horizontal line with block on the left
                brickMatrix.add(new int[][]{
                        {0, 0, 0, 0},
                        {0, 3, 3, 3},
                        {0, 3, 0, 0},
                        {0, 0, 0, 0}
                });
                // Vertical line with block at the bottom right
                brickMatrix.add(new int[][]{
                        {0, 0, 0, 0},
                        {0, 3, 3, 0},
                        {0, 0, 3, 0},
                        {0, 0, 3, 0}
                });
                // Horizontal line with block on the right
                brickMatrix.add(new int[][]{
                        {0, 0, 0, 0},
                        {0, 0, 3, 0},
                        {3, 3, 3, 0},
                        {0, 0, 0, 0}
                });
                // Vertical line with block at the top left
                brickMatrix.add(new int[][]{
                        {0, 3, 0, 0},
                        {0, 3, 0, 0},
                        {0, 3, 3, 0},
                        {0, 0, 0, 0}
                });

                return brickMatrix;
        }
}

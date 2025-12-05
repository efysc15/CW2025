package com.comp2042.logic.bricks;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the "T" shaped brick (tetromino)
 * <p>
 * The {@code TBrick} is a concrete subclass of {@link Brick} that defines the shape and rotations of the T-shaped tetromino: 
 * <ul>
 *      <li>Horizontal line of 3 blocks with 1 block centered below</li>
 *      <li>Vertical line of 3 blocks with 1 block centered to the right</li>
 *      <li>Horizontal line of 3 blocks with 1 block centered above</li>
 *      <li>Vertical line of 3 blocks with 1 block centered on the left</li>
 * </ul>
 * <p>The shape is stored as a list of 2D integer matrices, each representing one of the 4 rotation states
 */
public final class TBrick extends Brick {
        /**
         * Constructs a new {@code TBrick} with its predefined shape and rotation states
         */
        public TBrick() {
                super(6, createShape());
        }

        /**
         * Creates the shape matrices for the T-shaped brick.
         * <p>
         * The list contains 4 rotation states, covering all possible orientations of the T tetromino.
         * 
         * @return a list of 2D integer arrays representing the TBrick's rotations
         */
        private static List <int[][]> createShape() {
        final List<int[][]> brickMatrix = new ArrayList<>();

                // Horizontal line with block below center
                brickMatrix.add(new int[][]{
                        {0, 0, 0, 0},
                        {6, 6, 6, 0},
                        {0, 6, 0, 0},
                        {0, 0, 0, 0}
                });
                // Vertical line with block to the right
                brickMatrix.add(new int[][]{
                        {0, 6, 0, 0},
                        {0, 6, 6, 0},
                        {0, 6, 0, 0},
                        {0, 0, 0, 0}
                });
                // Horizontal line with block above center
                brickMatrix.add(new int[][]{
                        {0, 6, 0, 0},
                        {6, 6, 6, 0},
                        {0, 0, 0, 0},
                        {0, 0, 0, 0}
                });
                // Vertical line with block to the left
                brickMatrix.add(new int[][]{
                        {0, 6, 0, 0},
                        {6, 6, 0, 0},
                        {0, 6, 0, 0},
                        {0, 0, 0, 0}
                });
                
                return brickMatrix;
        }
}

package com.comp2042.logic.bricks;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the "I" shaped brick (tetromino)
 * <p>
 * The {@code IBrick} is a concrete subclass of {@link Brick} that defines the shape and rotations of the I-shaped tetromino: 
 * <ul>
 *  <li>Horizontal line of 4 blocks</li>
 *  <li>Vertical line of 4 blocks</li>
 * </ul>
 * <p>
 * The shape is stored as a list of 2D integer matrices, each representing one rotation state
 * 
 */
public final class IBrick extends Brick {

    /**
     * Constructs a new {@code IBrick} with its predefined shape and rotation states
     */
    public IBrick() {
        super(1, createShape());
    }

    /**
     * Creates the shape matrices for the I-shaped brick
     * <p> The list contains 2 rotation states: 
     * <ul>
     *  <li>Horizontal line (1x4)</li>
     *  <li>Vertical line (4x1)</li>
     * </ul>
     * @return a list of 2D integer arrays representing the IBrick's rotations
     */
    private static List <int[][]> createShape() {
    List<int[][]> brickMatrix = new ArrayList<>();

        // Horizontal line
        brickMatrix.add(new int[][]{
                {0, 0, 0, 0},
                {1, 1, 1, 1},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        });
        // Vertical line
        brickMatrix.add(new int[][]{
                {0, 1, 0, 0},
                {0, 1, 0, 0},
                {0, 1, 0, 0},
                {0, 1, 0, 0}
        });

        return brickMatrix;
    }
}

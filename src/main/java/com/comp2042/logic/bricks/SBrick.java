package com.comp2042.logic.bricks;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the "S" shaped brick (tetromino)
 * <p>
 * The {@code SBrick} is a concrete subclass of {@link Brick} that defines the shape and rotations of the S-shaped tetromino: 
 * <ul>
 *  <li>Horizontal zig-zag (2 blocks stacked diagonally) </li>
 *  <li>Vertical zig-zag (rotated orientation)</li>
 * </ul>
 * <p>
 * The shape is stored as a list of 2D integer matrices, each representing one of 2 rotation states.
 * 
 */
public final class SBrick extends Brick {

    /**
     * Constructs a new {@code SBrick} with its predefined shape and rotation states
     */
    public SBrick() {
        super(5, createShape());
    }

    /**
     * Creates the shape matrices for the S-shaped brick
     * <p> The list contains 2 rotation states: 
     * <ul>
     *  <li>Horizontal zig-zag</li>
     *  <li>Vertical zig-zag</li>
     * </ul>
     * @return a list of 2D integer arrays representing the SBrick's rotations
     */
    private static List <int[][]> createShape() { 
    List<int[][]> brickMatrix = new ArrayList<>();

        // Horizontal zig-zag
        brickMatrix.add(new int[][]{
                {0, 0, 0, 0},
                {0, 5, 5, 0},
                {5, 5, 0, 0},
                {0, 0, 0, 0}
        });
        // Vertical zig-zag
        brickMatrix.add(new int[][]{
                {5, 0, 0, 0},
                {5, 5, 0, 0},
                {0, 5, 0, 0},
                {0, 0, 0, 0}
        });

        return brickMatrix;
    }
}

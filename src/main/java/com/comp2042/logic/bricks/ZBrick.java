package com.comp2042.logic.bricks;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the "Z" shaped brick (tetromino)
 * <p>
 * The {@code ZBrick} is a concrete subclass of {@link Brick} that defines the shape and rotations of the Z-shaped tetromino: 
 * <ul>
 *  <li>Horizontal zig-zag (2 blocks stacked diagonally)</li>
 *  <li>Vertical zig-zag (rotated orientation)</li>
 * </ul>
 * <p>
 * The shape is stored as a list of 2D integer matrices, each representing one of the 2 rotation states/
 * 
 */
public final class ZBrick extends Brick {

    /**
     * Constructs a new {@code ZBrick} with its predefined shape and rotation states.
     */
    public ZBrick() {
        super(7, createShape());
    }

    /**
     * Creates the shape matrices for the Z-shaped brick
     * <p> The list contains 2 rotation states: 
     * <ul>
     *  <li>Horizontal zig-zag</li>
     *  <li>Vertical zig-zag</li>
     * </ul>
     * @return a list of 2D integer arrays representing the ZBrick's rotations
     */
    private static List <int[][]> createShape() { 
    List<int[][]> brickMatrix = new ArrayList<>();
        // Horizontal zig-zag
        brickMatrix.add(new int[][]{
                {0, 0, 0, 0},
                {7, 7, 0, 0},
                {0, 7, 7, 0},
                {0, 0, 0, 0}
        });
        // Vertical zig-zag
        brickMatrix.add(new int[][]{
                {0, 7, 0, 0},
                {7, 7, 0, 0},
                {7, 0, 0, 0},
                {0, 0, 0, 0}
        });

        return brickMatrix;
    }
}

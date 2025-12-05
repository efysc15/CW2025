package com.comp2042.logic.bricks;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the "O" shaped brick (tetromino)
 * <p>
 * The {@code OBrick} is a concrete subclass of {@link Brick} that defines the shape of the O-shaped tetromino: 
 * <ul>
 *  <li>A 2x2 square block</li>
 * </ul>
 * <p> 
 * Unlike other tetrominos, the OBrick has only one rotation state because its shape is symmetrical
 * 
 */
public final class OBrick extends Brick {

    /**
     * Constructs a new {@code OBrick} with its predefined shape
     */
    public OBrick() {
        super(4, createShape());
    }

    /**
     * Creates the shape matrix for the O-shaped brick
     * <p>
     * The list contains a single rotation state representing the 2x2 square block
     * 
     * @return a list containing one 2D integer array representing the OBrick's shape
     */
    private static List <int[][]> createShape() { 
    List<int[][]> brickMatrix = new ArrayList<>();

        // 2x2 square brick
        brickMatrix.add(new int[][]{
                {0, 0, 0, 0},
                {0, 4, 4, 0},
                {0, 4, 4, 0},
                {0, 0, 0, 0}
        });

        return brickMatrix;
    }
}

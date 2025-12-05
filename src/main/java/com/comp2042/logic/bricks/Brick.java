package com.comp2042.logic.bricks;

import java.util.List;

/**
 * Abstract base class representing a brick in the game
 * <p> A {@code Brick} defines: 
 * <ul>
 *  <li>An indentifier ({@code id}) for distinguishing brick types</li>
 *  <li>A list of shape matrices representing the brick in different rotations</li>
 * </ul>
 * <p>
 * Concrete subclasses (e.g., {@link IBrick}, {@link JBrick}, {@link LBrick}) provides specific brick shapes used in gameplay
 * 
 */
public abstract class Brick {
    /** Unique identifier for the brick type */
    protected int id;
    /** List of 2D matrices representing the brick's shape in different rotations */
    protected List <int[][]> shapeMatrix;
    
    /**
     * Constructs a new {@code Brick}
     * @param id the unique identifier for the brick type
     * @param shapeMatrix the list of 2D integer arrays representing the brick's rotations
     */
    public Brick (int id, List <int[][]> shapeMatrix) {
        this.id = id;
        this.shapeMatrix = shapeMatrix;
    }

    /**
     * Returns the brick's unique identifier
     * @return the brick ID
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the list of shape matrices for this brick
     * <p> Each matrix represents the brick in a different rotation. 
     * @return a list of 2D integer arrays representing the brick's rotations 
     */
    public List<int[][]> getShapeMatrix() {
        return shapeMatrix;
    }
}

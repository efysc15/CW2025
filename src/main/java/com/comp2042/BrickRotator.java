package com.comp2042;

import com.comp2042.logic.bricks.Brick;

/**
 * Handles rotation logic for a {@link Brick}
 * <p>
 * Maintains the current shape index and provides methods to retrieve and update brick orientations
 */
public class BrickRotator {

    /** The brick currently being rotated */
    private Brick brick;
    /** Index of the current shape orientation */
    private int currentShape = 0;

    /**
     * Creates a new {@code BrickRotator} with no initial brick assigned
     * <p>
     * The brick can be set later before rotation methods are used.
     */
    public BrickRotator() {}
    
    /**
     * Calculates and returns information about the next shape orientation of the current brick
     * @return a {@link NextShapeInfo} object containing the next shape matrix and its index
     */
    public NextShapeInfo getNextShape() {
        int nextShape = currentShape;
        nextShape = (++nextShape) % brick.getShapeMatrix().size();
        return new NextShapeInfo(brick.getShapeMatrix().get(nextShape), nextShape);
    }

    /**
     * Retrieves the current shape matrix of the brick
     * @return a 2D array representing the current brick shape
     */
    public int[][] getCurrentShape() {
        return brick.getShapeMatrix().get(currentShape);
    }

    /**
     * Sets the current shape index for the brick
     * @param currentShape the indes of the desired shape orientation
     */
    public void setCurrentShape(int currentShape) {
        this.currentShape = currentShape;
    }

    /**
     * Assigns a new brick to the rotator and resets the shape index
     * @param brick brick the {@link Brick} to be rotated
     */
    public void setBrick(Brick brick) {
        this.brick = brick;
        currentShape = 0;
    }

    /**
     * Returns the brick currently managed by this rotator
     * @return the {@link Brick} object
     */
    public Brick getBrick() {
        return brick;
    }
}

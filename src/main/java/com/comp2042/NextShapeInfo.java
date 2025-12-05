package com.comp2042;

/**
 * Immutable data holder for information about the next brick to appear
 * <p>
 * The {@code NextShapeInfo} class encapsulates both: 
 * <ul>
 *  <li>The shape matrix of the upcoming brick</li>
 *  <li>The initial spawn position on the game board</li>
 * </ul>
 * <p>
 * This class is used by the game logic to preview and place the next brick.
 * The shape matrix is defensively copied when accessed to preserve immuntability.
 * 
 */
public final class NextShapeInfo {

    /** The 2D matrix representing the brick's shape */
    private final int[][] shape;
    /** The initial spawn position (column index) of the brick */
    private final int position;

    /**
     * Constructs a new {@code NextShapeInfo}
     * @param shape the 2D integer array representing the brick's shape
     * @param position the initial spawn position (column index) of the brick
     */
    public NextShapeInfo(final int[][] shape, final int position) {
        this.shape = shape;
        this.position = position;
    }

    /**
     * Returns a copy of the brick's shape matrix
     * <p>
     * A defensive copy is returned to ensure immutability and preven external modification of the internal state.
     * 
     * @return a copied 2D integer array representing the brick's shape
     */
    public int[][] getShape() {
        return MatrixOperations.copy(shape);
    }

    /**
     * Returns the initial spawn position of the brick
     * @return the column index where the brick should appear
     */
    public int getPosition() {
        return position;
    }
}

package com.comp2042;

/**
 * Immutable snapshot of the current game view
 * <p>
 * The {@code ViewData} class encapsulates: 
 * <ul>
 *  <li>The shape matrix of the current brick </li>
 *  <li>The brick's X and Y position on the board</li>
 *  <li>The shape matrix of the next brick (for preview)</li>
 * </ul>
 * <p>
 * This class is used by the GUI layer to render the current brick and preview the upcoming brick.
 * Shape matrices are defensively copied when accessed to preserve immutability
 * 
 */
public final class ViewData {

    /** The 2D matrix representing the current brick's shape */
    private final int[][] brickData;
    /** The current brick's X position (column index) */
    private final int xPosition;
    /** The current brick's Y position (row index) */
    private final int yPosition;
    /** The 2D matrix representing the next brick's shape (for preview) */
    private final int[][] nextBrickData;

    /**
     * Constructs a new {@code ViewData} snapshot
     * @param brickData the 2D integer array representing the current brick's shape
     * @param xPosition the X position (column index) of the current brick
     * @param yPosition the Y position (row index) of the current brick
     * @param nextBrickData the 2D integer array representing the next brick's shape 
     */
    public ViewData(int[][] brickData, int xPosition, int yPosition, int[][] nextBrickData) {
        this.brickData = brickData;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.nextBrickData = nextBrickData;
    }

    /**
     * Returns a copy of the current brick's shape matrix
     * @return a copied 2D integer array representing the current brick's shape
     */
    public int[][] getBrickData() {
        return MatrixOperations.copy(brickData);
    }

    /**
     * Returns the current brick's X position
     * @return the column index of the brick 
     */
    public int getxPosition() {
        return xPosition;
    }

    /**
     * Returns the current brick's Y position
     * @return the row index of the brick
     */
    public int getyPosition() {
        return yPosition;
    }

    /**
     * Returns a copy of the next brick's shape matrix
     * @return a copied 2D integer array representing the next brick's shape
     */
    public int[][] getNextBrickData() {
        return MatrixOperations.copy(nextBrickData);
    }
}

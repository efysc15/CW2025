package com.comp2042;

/**
 * Immutable value object representing the result of the clearing rows from the gameboard
 * Stores the number of lines removed, the updated board matrix, and any score bonus awarded
 */
public final class ClearRow {

    /** The number of lines thaat were removed */
    private final int linesRemoved;
    /** The updated board matrix after rows were cleared */
    private final int[][] newMatrix;
    /** The score bonus awarded for clearing rows */
    private final int scoreBonus;

    /**
     * Construcs a new {@code ClearRow} result 
     * @param linesRemoved the number of lines cleared
     * @param newMatrix the updated board matrix after clearing
     * @param scoreBonus the bonus points awarded
     */
    public ClearRow(int linesRemoved, int[][] newMatrix, int scoreBonus) {
        this.linesRemoved = linesRemoved;
        this.newMatrix = newMatrix;
        this.scoreBonus = scoreBonus;
    }

    /**
     * Returns the number of lines removed
     * @return the number of cleared lines
     */
    public int getLinesRemoved() {
        return linesRemoved;
    }

    /**
     * Returns a copy of the updated board matrix after rows were cleared
     * @return a 2D array representing the new board state
     */
    public int[][] getNewMatrix() {
        return MatrixOperations.copy(newMatrix);
    }

    /**
     * Returns the score bonus awarded for clearing rows
     * @return the bonus points
     */
    public int getScoreBonus() {
        return scoreBonus;
    }
}

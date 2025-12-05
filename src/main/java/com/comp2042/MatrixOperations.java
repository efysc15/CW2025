package com.comp2042;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Utility class providing matrix operations for the game board
 * <p>
 * The {@code MatrixOperations} class contains static methods used to manipulate the game board matrix and brick matrices.
 * These include: 
 * <ul>
 *  <li>Collision detection between bricks and the board</li>
 *  <li>Copying and merging matrices</li>
 *  <li>Clearing completed rows and calculating score bonuses<li>
 *  <li>Deep copying lists of matrices</li>
 * </ul>
 * <p>
 * This class is non-instantiable and serves purely as a helper for board and brick operations.
 * 
 */
public class MatrixOperations {
    /** Private constructor to prevent instantiation */
    private MatrixOperations(){

    }

    /**
     * Checks whether a brick intersects with the game board at a given position
     * <p>
     * An intersection occurs if any non-zero brick cell overlaps with an occupied board cell or lies outside the board boundaries.
     * 
     * @param matrix the game board matrix
     * @param brick the brick matrix
     * @param x the X position (column offset)
     * @param y the Y position (row offset)
     * @return {@code true} if the brick intersects with the board, {@code false} otherwise
     */
    public static boolean intersect(final int[][] matrix, final int[][] brick, int x, int y) {
        // i iterates over rows (Y offset), j iterates over columns (X offset)
        for (int i = 0; i < brick.length; i++) {
            for (int j = 0; j < brick[i].length; j++) {
                
                // Calculate target coordinates on the game board
                int targetY = y + i;
                int targetX = x + j;
                
                // If the brick cell is part of the shape (not 0)
                if (brick[i][j] != 0) { // Corrected: use brick[i][j]
                    
                    // Check for out of bounds OR collision with an existing block
                    if (checkOutOfBound(matrix, targetX, targetY) || 
                        (targetY < matrix.length && targetX < matrix[targetY].length && matrix[targetY][targetX] != 0)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Checks if a target coordinate (X, Y) is outside the bounds of the matrix 
     * <p>
     * Matrix indexing convention: {@code matrix[row][column]} -> {@code [Y][X]}.
     * 
     * @param matrix the game board matrix
     * @param targetX the X coordinate
     * @param targetY the Y coordinate
     * @return {@code true} if the coordinate is out of bounds, {@code false} otherwise
     */
    private static boolean checkOutOfBound(int[][] matrix, int targetX, int targetY) {
        // targetY is out of bounds (below row 0 or below the last row index)
        if (targetY < 0 || targetY >= matrix.length) {
            return true;
        }
        
        // targetX is out of bounds (less than 0 or greater than or equal to column length)
        if (targetX < 0 || targetY < matrix.length && targetX >= matrix[targetY].length) {
            return true;
        }
        
        return false;
    }

    /**
     * Creates a deep copy of a matrix
     * @param original the original matrix
     * @return a new matrix with copied values 
     */
    public static int[][] copy(int[][] original) {
        int[][] myInt = new int[original.length][];
        for (int i = 0; i < original.length; i++) {
            int[] aMatrix = original[i];
            int aLength = aMatrix.length;
            myInt[i] = new int[aLength];
            System.arraycopy(aMatrix, 0, myInt[i], 0, aLength);
        }
        return myInt;
    }

    /**
     * Merges a brick into the game board matrix at the specified position
     * <p>
     * Non-zero brick cells overwrite the corresponding board cells
     * 
     * @param filledFields the current board matrix
     * @param brick the brick matrix
     * @param x the X position (column offset)
     * @param y the Y position (row offset)
     * @return a new matrix with the brick merged into the board
     */
    public static int[][] merge(int[][] filledFields, int[][] brick, int x, int y) {
        int[][] copy = copy(filledFields);
        // i iterates over rows (Y offset), j iterates over columns (X offset)
        for (int i = 0; i < brick.length; i++) {
            for (int j = 0; j < brick[i].length; j++) {
                int targetY = y + i;
                int targetX = x + j;
                
                // If the brick cell is part of the shape (not 0) and is within bounds
                if (brick[i][j] != 0 && 
                    targetY >= 0 && targetY < copy.length && 
                    targetX >= 0 && targetX < copy[targetY].length) { 
                    
                    copy[targetY][targetX] = brick[i][j];
                }
            }
        }
        return copy;
    }

    /**
     * Checks for completed rows in the board and removes them
     * <p>
     * Rows that are fully filled are cleared, and the remaining rows are shifted downward.
     * A score bonus is calculated based on the number of cleared rows using the formula {@code 50 * lines^2}.
     * 
     * @param matrix the game board matrix
     * @return a {@link ClearRow} object containing:
     *         <ul>
     *              <li>the number of lines cleared</li>
     *              <li>the updated board matrix</li>
     *              <li>the score bonus</li>
     *         </ul>
     */
    public static ClearRow checkRemoving(final int[][] matrix) {
        // Initialize temporary matrix for the new state
        int rows = matrix.length;
        int cols = matrix[0].length;
        int[][] tmp = new int[rows][cols]; 
        
        // Use a Deque to collect rows that are NOT cleared, preserving order
        Deque<int[]> nonClearedRows = new ArrayDeque<>();
        List<Integer> clearedRows = new ArrayList<>();

        for (int i = 0; i < rows; i++) {
            boolean rowToClear = true;
            for (int j = 0; j < cols; j++) {
                if (matrix[i][j] == 0) {
                    rowToClear = false;
                    break;
                }
            }
            
            if (rowToClear) {
                clearedRows.add(i);
            } else {
                // If not cleared, copy the row and add it to the front of the Deque
                int[] tmpRow = new int[cols];
                System.arraycopy(matrix[i], 0, tmpRow, 0, cols);
                nonClearedRows.add(tmpRow); 
            }
        }
        
        // Fill the temporary matrix from the bottom up using the non-cleared rows.
        int currentRowIndex = rows - 1; 
        while(!nonClearedRows.isEmpty()){
            // We use removeLast() because ArrayDeque implements Deque, 
            // and we added rows sequentially (top to bottom).
            tmp[currentRowIndex] = nonClearedRows.removeLast(); 
            currentRowIndex--;
        }
        
        // Calculate score based on number of cleared lines (e.g., 50 * lines^2)
        int scoreBonus = 50 * clearedRows.size() * clearedRows.size(); 
        
        // ClearRow should return the lines cleared, the new matrix, and the score bonus
        return new ClearRow(clearedRows.size(), tmp, scoreBonus); 
    }

    /**
     * Creates a deep copy of a list of matrices
     * @param list the list of matrices to copy
     * @return a new list containing deep copies of matrices
     */
    public static List<int[][]> deepCopyList(List<int[][]> list){
        return list.stream().map(MatrixOperations::copy).collect(Collectors.toList());
    }

}

package com.comp2042;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

public class MatrixOperations {
    private MatrixOperations(){

    }

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
     * Checks if a target coordinate (X, Y) is outside the bounds of the matrix.
     * Matrix is [Row][Column] -> [Y][X].
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
     * Merges the falling brick into the game matrix.
     * NOTE: Corrected indexing.
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
     * Checks for completed rows and creates a new matrix with those rows cleared, 
     * shifting the remaining rows down.
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

    public static List<int[][]> deepCopyList(List<int[][]> list){
        return list.stream().map(MatrixOperations::copy).collect(Collectors.toList());
    }

}

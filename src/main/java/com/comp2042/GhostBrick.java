package com.comp2042;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Represents the "ghost" brick in the game
 * <p>
 * A ghost brick is a visual aid that shows where the current falling brick would land if dropped straight down
 * It is rendered as a set of transparent rectangles with white outlines
 * 
 */
public class GhostBrick {
    /** The size of each brick cell in pixels */
    private static final int BRICK_SIZE = 20;
    /** 2D array of rectangles representing the ghost brick grid */
    private final Rectangle[][] ghostRectangles;
    
    /**
     * Constructs a new {@code GhostBrick} with the given dimensions
     * <p>
     * Initializes a grid of transparent rectangles that can later be styled to show ghost outlines
     * 
     * @param rows the number of rows in the ghost grid
     * @param cols the number of columns in the ghost grid
     */
    public GhostBrick (int rows, int cols) {
        ghostRectangles = new Rectangle[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Rectangle rect = new Rectangle(BRICK_SIZE, BRICK_SIZE);
                rect.setFill(Color.TRANSPARENT);
                rect.setStrokeWidth(1.5);
                rect.setStroke(Color.TRANSPARENT);
                ghostRectangles[i][j] = rect;
            }
        }
    }

    /**
     * Returns the grid of rectangles representing the ghost brick
     * @return a 2D array of {@link Rectangle} objects
     */
    public Rectangle[][] getRectangles() {
        return ghostRectangles;
    }

    /**
     * Updates the ghost brick position based on the current board state and the active brick
     * Clears old outlines and redraws the ghost at the lowest possible position
     * @param boardMatrix the current game board matrix
     * @param brick the link {@link ViewData} representing the active brick
     */
    public void updateGhost (int[][] boardMatrix, ViewData brick) {
        // Clear old outlines
        for (Rectangle[] row : ghostRectangles) {
            for (Rectangle rect : row) {
                rect.setStroke(Color.TRANSPARENT);
                rect.setFill(Color.TRANSPARENT);
            }
        }

        int offsetY = 0;
        while (canMoveDown(boardMatrix, brick, offsetY)) {
            offsetY++;
        }

        int[][] brickData = brick.getBrickData();
        for (int i = 0; i < brickData.length; i++) {
            for (int j = 0; j < brickData[i].length; j++) {
                if (brickData[i][j] != 0) {
                    int y = brick.getyPosition() + i + offsetY;
                    int x = brick.getxPosition() + j;
                    if (y >= 2 && y < ghostRectangles.length && x >= 0 && x < ghostRectangles[0].length) {
                        ghostRectangles[y][x].setStroke(Color.WHITE);
                    }
                }
            }
        }
    }

    /**
     * Determines whether the brick can move further down without colliding with the bottom edge or existing blocks
     * @param boardMatrix the current game board matrix
     * @param brick the link {@link ViewData} representing the active brick 
     * @param offset the vertical offset applied to the brick
     * @return {@code true} if the brick can move down, {@code false} otherwise 
     */
    private boolean canMoveDown(int[][] boardMatrix, ViewData brick, int offset) {
        int[][] brickData = brick.getBrickData();
        int x = brick.getxPosition();
        int y = brick.getyPosition() + offset;
        for (int i = 0; i < brickData.length; i++) {
            for (int j = 0; j < brickData[i].length; j++) {
                if (brickData[i][j] != 0) {
                    int boardY = y + i + 1;
                    int boardX = x + j;

                    // If touching bottom edge
                    if (boardY >= boardMatrix.length) {
                        return false;
                    }

                    // If touching another brick
                    if (boardMatrix[boardY][boardX] != 0) {
                        return false;
                    } 
                }
            }
        }

        return true;
    }
}

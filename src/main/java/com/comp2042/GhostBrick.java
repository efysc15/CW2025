package com.comp2042;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GhostBrick {
    private static final int BRICK_SIZE = 20;
    private final Rectangle[][] ghostRectangles;

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

    public Rectangle[][] getRectangles() {
        return ghostRectangles;
    }

    // Update ghost position and draw white outlines
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

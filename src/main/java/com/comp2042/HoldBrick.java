package com.comp2042;

import com.comp2042.logic.bricks.Brick;

import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

/**
 * Manages the "Hold Brick" feature
 * Allows the player to temporarily store a brick
 * and swap it back during gameplay
 */
public class HoldBrick {
    private Brick heldBrick;    // Stores the current held brick
    private boolean hasHeldThisTurn = false;    // Prevent holding multiple times in one drop
    private final int BRICK_SIZE = 18;
    private final GridPane holdPanel;   // The display panel for the held brick

    public HoldBrick (GridPane holdPanel) {
        this.holdPanel = holdPanel;
        holdPanel.setAlignment(Pos.CENTER);
    }

    // Returns the current held brick
    public Brick getHeldBrick() {
        return heldBrick;
    }

    // Returns whether the player has already held this turn
    public boolean hasHeldThisTurn() {
        return this.hasHeldThisTurn;
    }

    // Sets whether the player can hold again
    public void setHasHeldThisTurn(boolean value) {
        this.hasHeldThisTurn = value;
    }

    // Resets the 'held this turn' after a new brick spawns
    public void resetTurn() {
        hasHeldThisTurn = false;
    }

    /**
     * Handles holding or swapping the current brick
     * @param currentBrick The brick currently being controlled by the player
     * @return The previously held brick (if any)
     */
    public Brick holdBrick (Brick currentBrick) {
        Brick temp = heldBrick; // Temporarily store the prev held brick
        heldBrick = currentBrick;   // Replace with the new one
        hasHeldThisTurn = true; // Disallow multiple holds this turn
        updateHoldPanel(currentBrick);  // Update hold display
        return temp;    // Return the previously held brick 
    }

    /**
     * Updates the visual representation of the held brick in the hold panel
     */
    private void updateHoldPanel(Brick brick) {
        holdPanel.getChildren().clear();
        if (brick == null) return;

        int[][] shape = brick.getShapeMatrix().get(0);
        int brickId = brick.getId();
        Paint color = getFillColor(brickId);

        int shapeHeight = shape.length;
        int shapeWidth = shape[0].length;

        // Center the brick in a 6 x 6 grid
        int panelCols = 6;
        int panelRows = 6;

        int offsetX = (panelCols - shapeWidth) / 2;
        int offsetY = (panelRows - shapeHeight) / 2;

        // Draw the brick
        for (int i = 0; i < shapeHeight; i++) {
            for (int j = 0; j < shapeWidth; j++) {
                if (shape[i][j] != 0) {
                    Rectangle rect = new Rectangle (BRICK_SIZE, BRICK_SIZE, color);
                    rect.setStroke(Color.BLACK);
                    holdPanel.add(rect, j + offsetX, i + offsetY);
                }
            }
        }
    }

    private Paint getFillColor(int i) {
        Paint returnPaint;
        switch (i) {
            case 0:
                returnPaint = Color.TRANSPARENT;
                break;
            case 1:
                returnPaint = Color.AQUA;
                break;
            case 2:
                returnPaint = Color.BLUEVIOLET;
                break;
            case 3:
                returnPaint = Color.DARKGREEN;
                break;
            case 4:
                returnPaint = Color.YELLOW;
                break;
            case 5:
                returnPaint = Color.RED;
                break;
            case 6:
                returnPaint = Color.BEIGE;
                break;
            case 7:
                returnPaint = Color.BURLYWOOD;
                break;
            default:
                returnPaint = Color.WHITE;
                break;
        }
        return returnPaint;
    }
}

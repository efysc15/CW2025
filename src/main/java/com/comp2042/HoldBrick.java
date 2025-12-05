package com.comp2042;

import com.comp2042.logic.bricks.Brick;

import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

/**
 * Manages the "hold brick" feature in the game
 * <p>
 * The {@code HoldBrick} class allows the player to store the current brick for later use/
 * A held brick can be swapped back into play, but only one per turn to prevent repeated holding.
 * 
 * <p>
 * This class also updates {@link GridPane} UI panel to visually display the currently held brick.
 * 
 */
public class HoldBrick {
    /** The brick currently held by the player */
    private Brick heldBrick;
    /** Flag to prevent holding multiple times in a single turn */
    private boolean hasHeldThisTurn = false;    // Prevent holding multiple times in one drop
    /** The size of each brick cell in pixels */
    private final int BRICK_SIZE = 18;
    /** The panel used to display the held brick */
    private final GridPane holdPanel;

    /**
     * Constructs a new {@code HoldBrick} manager
     * @param holdPanel the {@link GridPane} used to render the held brick
     */
    public HoldBrick (GridPane holdPanel) {
        this.holdPanel = holdPanel;
        holdPanel.setAlignment(Pos.CENTER);
    }

    /**
     * Returns the currently held brick
     * @return the held {@link Brick}, or {@code null} if none is held
     */
    public Brick getHeldBrick() {
        return heldBrick;
    }

    /**
     * Checks whether the player has already held a brick this turn
     * @return {@code true} if a brick has been held this turn, {@code false} otherwise
     */
    public boolean hasHeldThisTurn() {
        return this.hasHeldThisTurn;
    }

    /**
     * Sets the flag indicating whether a brick has been held this turn
     * @param value {@code true} if a brick has been held, {@code false} otherwise
     */
    public void setHasHeldThisTurn(boolean value) {
        this.hasHeldThisTurn = value;
    }

    /**
     * Resets the hold flag at the start of a new turn 
     */
    public void resetTurn() {
        hasHeldThisTurn = false;
    }

    /**
     * Holds the current brick and updates the hold panel
     * <p>
     * If a brick was already held, it is returned so it can be swapped back into play.
     * Otherwise, {@code null} is returned.
     * 
     * @param currentBrick the {@link Brick} to hold
     * @return the previously held {@link Brick}, or {@code null} if none was held
     */
    public Brick holdBrick (Brick currentBrick) {
        Brick temp = heldBrick;
        heldBrick = currentBrick;
        hasHeldThisTurn = true;
        updateHoldPanel(currentBrick);
        return temp;    // Return the previously held brick 
    }

    /**
     * Updates the hold panel to visually display the given brick
     * @param brick the {@link Brick} to render in the hold panel
     */
    private void updateHoldPanel(Brick brick) {
        holdPanel.getChildren().clear();
        if (brick == null) return;

        int[][] shape = brick.getShapeMatrix().get(0);
        int brickId = brick.getId();
        Paint color = getFillColor(brickId);

        int shapeHeight = shape.length;
        int shapeWidth = shape[0].length;

        int panelCols = 6;
        int panelRows = 6;

        int offsetX = (panelCols - shapeWidth) / 2;
        int offsetY = (panelRows - shapeHeight) / 2;

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

    /**
     * Maps a brick ID to its corresponding color
     * @param i the brick ID
     * @return the {@link Paint} color associated with the brick ID
     */
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

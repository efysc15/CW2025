package com.comp2042;

import com.comp2042.logic.bricks.Brick;

import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class HoldBrick {
    private Brick heldBrick;
    private boolean hasHeldThisTurn = false;    // Prevent holding multiple times in one drop
    private final int BRICK_SIZE = 18;
    private final GridPane holdPanel;

    public HoldBrick (GridPane holdPanel) {
        this.holdPanel = holdPanel;
        holdPanel.setAlignment(Pos.CENTER);
    }

    public Brick getHeldBrick() {
        return heldBrick;
    }

    public boolean hasHeldThisTurn() {
        return this.hasHeldThisTurn;
    }

    public void setHasHeldThisTurn(boolean value) {
        this.hasHeldThisTurn = value;
    }

    public void resetTurn() {
        hasHeldThisTurn = false;
    }

    public Brick holdBrick (Brick currentBrick) {
        Brick temp = heldBrick;
        heldBrick = currentBrick;
        hasHeldThisTurn = true;
        updateHoldPanel(currentBrick);
        return temp;    // Return the previously held brick 
    }

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

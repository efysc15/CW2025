package com.comp2042;

/** 
 * Immutable value object representing the result of a downward move event in the game
 * Stores information bout cleared rows, the current view state of the board, and whether the falling brick has landed
 */
public final class DownData {
    /** Information about cleared rows, if any, after the move */
    private final ClearRow clearRow;
    /** Data used to render the current state of the board */
    private final ViewData viewData;
    /** Flag indicating whether the falling brick has landed */
    private final boolean landed;

    /**
     * Constructs a new {@code DownData} object with the given state
     * @param clearRow the {@link ClearRow} result of the move, or {@code null} if no rows were cleared
     * @param viewData the {@link ViewData} representing the board state after the move
     * @param landed {@code true} if the brick has landed and merged into the background, {@code false} otherwise
     */
    public DownData(ClearRow clearRow, ViewData viewData, boolean landed) {
        this.clearRow = clearRow;
        this.viewData = viewData;
        this.landed = landed;
    }

    /**
     * Returns information about cleared rows after the move
     * @return a {@link ClearRow} object, or {@code null} if no rows were cleared
     */
    public ClearRow getClearRow() {
        return clearRow;
    }

    /**
     * Returns the view data representing the board state after the move
     * @return a {@link ViewData} object containing rendering information
     */
    public ViewData getViewData() {
        return viewData;
    }

    /**
     * Indicates whether the falling brick has landed
     * @return {@code true} if the brick has landed, {@code false} otherwise 
     */
    public boolean isLanded() {
        return landed;
    }
}

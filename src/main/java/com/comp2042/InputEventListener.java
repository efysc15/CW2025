package com.comp2042;

/**
 * Defines the contract for handling input events in the game
 * <p>
 * The {@code InputEventListener} interface is implemented by the game logic (typically {@link GameController}) and 
 * used by the GUI layer ({@link GuiController}) to delegate user actions such as moving rotating, dropping, or holding bricks.
 * 
 */
public interface InputEventListener {

    /**
     * Handles a downward movement event
     * @param event the {@link MoveEvent} representing the down action
     * @return a {@link DownData} object containing the updated game state, including cleared rows and brick landing status
     */
    DownData onDownEvent(MoveEvent event);

    /**
     * Handles a left movement event
     * @param event the {@link MoveEvent} representing the left action
     * @return a {@link ViewData} object representing the updated brick position
     */
    ViewData onLeftEvent(MoveEvent event);

    /**
     * Handles a right movement event
     * @param event the {@link MoveEvent} representing the right action
     * @return a {@link ViewData} object representing the updated brick position
     */
    ViewData onRightEvent(MoveEvent event);

    /**
     * Handles a rotation event
     * @param event the {@link MoveEvent} representing the rotation action
     * @return a {@link ViewData} object representing the updated brick orientation
     */
    ViewData onRotateEvent(MoveEvent event);

    /**
     * Creates a new game instance
     * <p>
     * Called when the player starts a new game, resetting the board and initializing the first brick.
     * 
     */
    void createNewGame();

    /**
     * Returns the current board matrix
     * @return a 2D integer array representing the game board state, where non-zero values indicate occupied cells
     */
    int[][] getBoardMatrix();

    /**
     * Handles a hold event
     * <p>
     * Called when the player chooses to hold the current brick, swapping it with the previously held brick is available.
     * 
     */
    void onHoldEvent();
}

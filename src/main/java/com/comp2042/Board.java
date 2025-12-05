package com.comp2042;

/**
 * Defines the contract for a game board in the Tetrix-like application
 * <p>
 * The {@code Board} interface specifies core operations for managing the game state,
 * including brick movement, rotation, merging into the background, clearing rows, tracking score, and starting new games
 */
public interface Board {

    /**
     * Attempts to move the current brick one row downward
     * @return {@code true} if the brick was succcessfully moved down,
     *         {@code false} if blocked by collision or boundary
     */       
    boolean moveBrickDown();

    /**
     * Attempts to move the current brick one column to the left
     * @return {@code true} if the brick was successfully moved left,
     *         {@code false} if blocked by collision or boundary
     */
    boolean moveBrickLeft();

    /**
     * Attempts to move the current brick one column to the right
     * @return {@code true} if the brick was successfully moved right
     *         {@code false} if blocked by collision or boundary
     */
    boolean moveBrickRight();

    /**
     * Attempts to rotate the current brick to its next orientation
     * @return {@code true} if the brick was successfully rotated,
     *         {@code false} if blocked by collision or boundary
     */
    boolean rotateLeftBrick();

    /**
     * Creates and spawns a new brick at the starting position
     * @return {@code true} if the new brick immediately collides (indicating game over),
     *         {@code false} otherwise
     */
    boolean createNewBrick();

    /**
     * Returns the current board matrix
     * @return a 2D integer array representing the board state
     */
    int[][] getBoardMatrix();

    /**
     * Returns a snapshot of the current view data
     * @return a {@link ViewData} object representing the current brick and the next brick preview
     */
    ViewData getViewData();

    /**
     * Merges the current brick into the background board matrix
     * <p>
     * Typically called with the brick can no longer move downward
     */
    void mergeBrickToBackground();

    /**
     * Clears any completed rows from the board
     * @return a {@link ClearRow} object containing the number of rows cleared, the updated matrix, and any score bonus
     */
    ClearRow clearRows();

    /**
     * Returns the score tracker for the board
     * @return the {@link Score} object
     */
    Score getScore();

    /**
     * Resets the board for a new game 
     * <p>
     * Clears the matrix, resets the score, and spawns a new brick
     */
    void newGame();
}

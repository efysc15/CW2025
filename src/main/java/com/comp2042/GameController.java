package com.comp2042;

import com.comp2042.logic.bricks.Brick;

/**
 * Main controller class for the game. 
 * Implements {@link InputEventListener} to handle user and system events such as moving, rotating, and holding bricks
 * <p>
 * The {@code GameController} manages the game board, scoring, timers, and interactions with the {@link GuiController} for updating the view
 * 
 */

public class GameController implements InputEventListener {
    /** The game board implementation */
    private Board board = new SimpleBoard(25, 10);
    /** GUI controller responsible for rendering the game view */
    private final GuiController viewGuiController;
    /** Manager for handling held bricks */
    private HoldBrick holdBrickManager;
    /** Flag indicating whether a brick has been held during the current turn */
    private boolean hasHeldThisTurn = false;
    /** The selected game mode (e.g., Two-minute, Classic) */
    private final GameMode gameMode;

    /**
     * Constructs a new {@code GameController} with the given GUI controller and game mode
     * Initializes the board, sets up event listeners, and prepares the game view
     * @param c the {@link GuiController} used to render the game
     * @param mode the {@link GameMode} defining gameplay rules
     */
    public GameController(GuiController c, GameMode mode) {
        viewGuiController = c;
        this.holdBrickManager = new HoldBrick(c.getHoldBrickPanel());
        this.gameMode = mode;

        // Set up next brick display (if the board is SimpleBoard)
        if (board instanceof SimpleBoard simpleBoard) {
            simpleBoard.setNextBricksQueueConsumer(viewGuiController::showNextBricksQueue);
            
            viewGuiController.showNextBricksQueue(simpleBoard.getNextBricksQueue());    // Display the next brick preview
            simpleBoard.setGameOverRow(0);
        }

        board.createNewBrick();
        viewGuiController.setEventListener(this);
        viewGuiController.initGameView(board.getBoardMatrix(), board.getViewData());
        viewGuiController.bindScore(board.getScore().scoreProperty());
        viewGuiController.bindTimer(viewGuiController.getRemainingSecondsProperty());

        setupGameMode();
    }

    /**
     * Configures the game mode. For Two-Minutes mode, starts the countdown timer
     */
    private void setupGameMode() {
        if (gameMode == GameMode.TWO_MINUTES) {
            viewGuiController.startCountdown(120);
        }
    }

    /** Timer used for countdown or tracking elapsed time */
    private final Timer timer = new Timer();

    /**
     * Returns the game timer
     * @return the {@link Timer} instance
     */
    public Timer getTimer() {
        return timer;
    }

    /**
     * Handles the down movement event
     * Moves the brick down if possible, merges it into the background if it cannot move further, clears rows, and updates the score accordingly
     * @param event the {@link MoveEvent} representing the down action
     * @return a {@link DownData} object containing the result of the move
     */
    @Override
    public DownData onDownEvent(MoveEvent event) {
        boolean canMove = board.moveBrickDown();
        ClearRow clearRow = null;
        boolean landed = false;
        if (!canMove) {
            board.mergeBrickToBackground();
            clearRow = board.clearRows();
            landed = true;
            if (clearRow.getLinesRemoved() > 0) {
                int points = clearRow.getScoreBonus();
                board.getScore().add(points);
            }
            if (board.createNewBrick()) {
                viewGuiController.gameOver();
            }

            viewGuiController.refreshGameBackground(board.getBoardMatrix());
            holdBrickManager.setHasHeldThisTurn(false);
        } 
        return new DownData(clearRow, board.getViewData(), landed);
    }

    /**
     * Handles the left movement event
     * @param event the {@link MoveEvent} representing the left action
     * @return the updated {@link ViewData} after the move
     */
    @Override
    public ViewData onLeftEvent(MoveEvent event) {
        board.moveBrickLeft();
        return board.getViewData();
    }

    /**
     * Handles the right movement event
     * @param event the {@link MoveEvent} representing the right action
     * @return the updated {@link ViewData} after the move
     */
    @Override
    public ViewData onRightEvent(MoveEvent event) {
        board.moveBrickRight();
        return board.getViewData();
    }

    /**
     * Handles the rotate event
     * @param event the {@link MoveEvent} representing the rotate action
     * @return the updated {@link ViewData} after rotation
     */
    @Override
    public ViewData onRotateEvent(MoveEvent event) {
        board.rotateLeftBrick();
        return board.getViewData();
    }

    /**
     * Starts a new game by resetting the board and refreshing the view
     * Resets or restarts the timer depending on the game mode
     */
    @Override
    public void createNewGame() {
        board.newGame();
        viewGuiController.refreshGameBackground(board.getBoardMatrix());

        if (gameMode == GameMode.TWO_MINUTES) {
        viewGuiController.startCountdown(120); // restart timer
        } else {
        viewGuiController.resetTimer();        // keep at 00:00
        }
    }

    /**
     * Returns the current board matrix
     * @return a 2D array representing the board state
     */
    @Override
    public int[][] getBoardMatrix() {
        return board.getBoardMatrix();
    }

    /**
     * Handles the hold event
     * Allows the player to hold the current brick and swap it with a previously held brick, if available
     */
    @Override
    public void onHoldEvent() {
        if (!(board instanceof SimpleBoard simpleBoard)) { 
            return;
        }

        if (holdBrickManager.hasHeldThisTurn()) {
            return; 
        }

            Brick currentBrick = simpleBoard.getBrickRotator().getBrick();
            Brick newCurrent = holdBrickManager.holdBrick(currentBrick);
            if (newCurrent != null) {
                simpleBoard.getBrickRotator().setBrick(newCurrent);
            } else {
                simpleBoard.createNewBrick();   // If no brick was previously held, get a new one
            }

            holdBrickManager.setHasHeldThisTurn(true);

            viewGuiController.refreshBrick(board.getViewData());
            viewGuiController.refreshGameBackground(board.getBoardMatrix());
        }
}

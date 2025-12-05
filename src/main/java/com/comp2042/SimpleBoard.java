package com.comp2042;

import java.awt.Point;
import java.util.List;
import java.util.function.Consumer;

import com.comp2042.logic.bricks.Brick;
import com.comp2042.logic.bricks.BrickGenerator;
import com.comp2042.logic.bricks.RandomBrickGenerator;

/**
 * A simple implementation of the {@link Board} interface
 * <p>
 * The {@code SimpleBoard} class manages the game state, including: 
 * <ul>
 *  <li>The current game matrix (board)</li>
 *  <li>The active brick and its position</li>
 *  <li>Brick generation and rotation</li>
 *  <li>Row clearing and score updates</li>
 *  <li>Preview of upcoming bricks</li>
 * </ul>
 * <p>
 * It coordinates with {@link BrickGenerator}, {@link BrickRotator}, and {@link MatrixOperations} to handle gameplay mechanics
 *  
 */
public class SimpleBoard implements Board {
    /** Width of the game board (columns) */
    private final int width;
    /** Height of the game board (rows) */
    private final int height;
    /** Brick generator used to supply new bricks */
    private final BrickGenerator brickGenerator;
    /** Handles rotation of the current brick */
    private final BrickRotator brickRotator;
    /** The current game matrix representing the board state */
    private int[][] currentGameMatrix;
    /** The current brick's offset (position on the board) */
    private Point currentOffset;
    /** Tracks the player's score */
    private final Score score;
    /** Consumer used to update the GUI with the next bricks queue preview */
    private Consumer<List<Brick>> nextBricksQueueConsumer; // Updated consumer to handle a List of Bricks for the queue display
    /** Row index used to determine the spawn position and game over threshold */
    private int gameOverRow = 2;

    /**
     * Constructs a new {@code SimpleBoard}
     * @param width the width of the board (columns)
     * @param height the height of the board (rows)
     */
    public SimpleBoard(int width, int height) {
        this.width = width;
        this.height = height;
        currentGameMatrix = new int[width][height];
        brickGenerator = new RandomBrickGenerator();
        brickRotator = new BrickRotator();
        score = new Score();
    }

    /**
     * Sets the consumer used to update the next bricks queue to preview in the GUI
     * @param nextBricksQueueConsumer a consumer that accepts a list of upcoming bricks
     */
    public void setNextBricksQueueConsumer (Consumer<List<Brick>> nextBricksQueueConsumer) {
        this.nextBricksQueueConsumer = nextBricksQueueConsumer;
    }

    /**
     * Returns a queue of upcoming bricks
     * @return a list of {@link Brick} objects representing the next bricks
     */
    public List<Brick> getNextBricksQueue() {
        return brickGenerator.getNextBricksQueue();
    }

    /**
     * Returns the immediate next brick
     * @return the next {@link Brick}
     */
    public Brick getNextBrick() {
        return brickGenerator.getNextBrick();
    }

    /**
     * Sets the row index used to determine the game-over threshold
     * @param row the row index
     */
    public void setGameOverRow(int row) {
        this.gameOverRow = row;
    }

    /**
     * Returns the brick rotator
     * @return the {@link BrickRotator}
     */
    public BrickRotator getBrickRotator() {
        return brickRotator;
    }

    /**
     * Sets the current brick and resets its spawn position
     * @param brick the {@link Brick} to set as the current brick
     */
    public void setCurrentBrick(Brick brick) {
        brickRotator.setBrick(brick);
        currentOffset = new Point (4, gameOverRow); // Reset spawn position
    }

    /**
     * Attempts to move the current brick one row downward
     * @return {@code true} if the brick was successfully moved down
     *         {@code false} if a collision or boundary prevents movement 
     */
    @Override
    public boolean moveBrickDown() {
        int[][] currentMatrix = MatrixOperations.copy(currentGameMatrix);
        Point p = new Point(currentOffset);
        p.translate(0, 1);
        boolean conflict = MatrixOperations.intersect(currentMatrix, brickRotator.getCurrentShape(), (int) p.getX(), (int) p.getY());
        if (conflict) {
            return false;
        } else {
            currentOffset = p;
            return true;
        }
    }

    /**
     * Attempts to move the current brick one column to the left
     * @return {@code true} if the brick was successfully moved left,
     *         {@code false} if a collision or boundary prevents movement
     */
    @Override
    public boolean moveBrickLeft() {
        int[][] currentMatrix = MatrixOperations.copy(currentGameMatrix);
        Point p = new Point(currentOffset);
        p.translate(-1, 0);
        boolean conflict = MatrixOperations.intersect(currentMatrix, brickRotator.getCurrentShape(), (int) p.getX(), (int) p.getY());
        if (conflict) {
            return false;
        } else {
            currentOffset = p;
            return true;
        }
    }

    /**
     * Attempts to move the current brick one column to the right
     * @return {@code true} if the brick was successfully moved right
     *         {@code false} if a collision or boundary prevents movement
     */
    @Override
    public boolean moveBrickRight() {
        int[][] currentMatrix = MatrixOperations.copy(currentGameMatrix);
        Point p = new Point(currentOffset);
        p.translate(1, 0);
        boolean conflict = MatrixOperations.intersect(currentMatrix, brickRotator.getCurrentShape(), (int) p.getX(), (int) p.getY());
        if (conflict) {
            return false;
        } else {
            currentOffset = p;
            return true;
        }
    }

    /**
     * Attempts to rotate the current brick to its next orientation
     * <p>
     * Uses {@link BrickRotator#getNextShape()} to preview the rotation and checks for collisions before applying it
     * 
     * @return {@code true} if the brick was successfully rotated,
     *         {@code false} if a collision prevents rotation
     */
    @Override
    public boolean rotateLeftBrick() {
        int[][] currentMatrix = MatrixOperations.copy(currentGameMatrix);
        NextShapeInfo nextShape = brickRotator.getNextShape();
        boolean conflict = MatrixOperations.intersect(currentMatrix, nextShape.getShape(), (int) currentOffset.getX(), (int) currentOffset.getY());
        if (conflict) {
            return false;
        } else {
            brickRotator.setCurrentShape(nextShape.getPosition());
            return true;
        }
    }

    /**
     * Creates and spawns a new brick at a starting position
     * <p>
     * Also notifies the GUI of the updated next-bricks queue if a consumer has been set
     * 
     * @return {@code true} if the new brick immediately collides (indicating game over), {@code false} otherwise
     */
    @Override
    public boolean createNewBrick() {
        Brick currentBrick = brickGenerator.getBrick();
        brickRotator.setBrick(currentBrick);
        currentOffset = new Point(4, gameOverRow);

        // Notify the GUI with the new queue
        if (nextBricksQueueConsumer != null) {
            nextBricksQueueConsumer.accept(brickGenerator.getNextBricksQueue());
        }

        return MatrixOperations.intersect(currentGameMatrix, brickRotator.getCurrentShape(), (int) currentOffset.getX(), (int) currentOffset.getY());
    }

    /**
     * Returns the current board matrix
     * @return a 2D integer array representing the board state
     */
    @Override
    public int[][] getBoardMatrix() {
        return currentGameMatrix;
    }

    /**
     * Returns a snapshot of the current view data
     * <p>
     * Includes the current brick shape, its position, and the next brick preview shape
     * 
     * @return a {@link ViewData} object representing the current state
     */
    @Override
    public ViewData getViewData() {
        return new ViewData(brickRotator.getCurrentShape(), (int) currentOffset.getX(), (int) currentOffset.getY(), brickGenerator.getNextBrick().getShapeMatrix().get(0));
    }

    /**
     * Merges the current brick into the background board matrix
     * <p>
     * This is typically called when the brick can no longer move down
     * 
     */
    @Override
    public void mergeBrickToBackground() {
        currentGameMatrix = MatrixOperations.merge(currentGameMatrix, brickRotator.getCurrentShape(), (int) currentOffset.getX(), (int) currentOffset.getY());
    }

    /**
     * Clears any completed rows from the board
     * <p>
     * Uses {@link MatrixOperations#checkRemoving(int[][])} to detect and remove full rows, shifting remaining rows downward
     * 
     * @return a {@link ClearRow} object containing the number of rows cleared, the updated matrix, and the score bonus
     */
    @Override
    public ClearRow clearRows() {
        ClearRow clearRow = MatrixOperations.checkRemoving(currentGameMatrix);
        currentGameMatrix = clearRow.getNewMatrix();
        return clearRow;

    }

    /**
     * Returns the score tracker for the board
     * @return the {@link Score} object
     */
    @Override
    public Score getScore() {
        return score;
    }

    /**
     * Resets the board for a new game
     * <p>
     * Clears the matrix, resets the score, spawns a new brick
     * 
     */
    @Override
    public void newGame() {
        currentGameMatrix = new int[width][height];
        score.reset();
        createNewBrick();
    }
}

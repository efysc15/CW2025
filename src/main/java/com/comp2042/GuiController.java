package com.comp2042;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.comp2042.logic.bricks.Brick;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button; 
import javafx.scene.control.Label;
import javafx.scene.effect.Reflection;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * JavaFX controller class responsible for managing the game interface
 * <p>
 * The {@code GuiController} handles rendering of the game board, bricks, ghost bricks, notifications, score, timer, and control buttons
 * It also manages keyboard iput and delegates gameplay actions to the {@link InputEventListener} (typically the {@link GameController})
 * 
 * <p>
 * This class is linked to the FXML layout and initializes UI components such as panels, overlays, and labels
 * 
 */
public class GuiController implements Initializable {

    /** The size of each brick cell in pixels */
    private static final int BRICK_SIZE = 20;

    // --- FXML-injected UI components ---
    @FXML private GridPane gamePanel;
    @FXML private StackPane groupNotification;
    @FXML private GridPane brickPanel;
    @FXML private GameOverPanel gameOverPanel; 
    @FXML private GridPane nextBrickPanel;
    @FXML private Label scoreLabel;
    @FXML private GridPane holdBrickPanel;
    @FXML private VBox buttonBar;
    @FXML private StackPane pauseOverlay;
    @FXML private VBox resumeContainer;
    
    @FXML private VBox holdBrickContainer;
    @FXML private VBox nextBoxContainer;
    @FXML private VBox scoreContainer;
    @FXML private BorderPane gameBoard;
    @FXML private Label nextLabel;
    @FXML private StackPane centerStack;
    @FXML private Label timerLabel;

    // --- Internal state ---
    private Rectangle[][] displayMatrix;
    private InputEventListener eventListener;
    private Rectangle[][] rectangles;
    private Timeline timeLine;
    private final BooleanProperty isPause = new SimpleBooleanProperty();
    private final BooleanProperty isGameOver = new SimpleBooleanProperty();
    private GhostBrick ghostBrick;
    private Board board; 
    private Timeline countdownTimer;
    private final IntegerProperty remainingSecondsProperty = new SimpleIntegerProperty();
    private Scene menuScene;

    /** 
     * Creates a new {@code GuiController} instance
     * <p>
     * This constructor is invoked automatically by the JavaFX FXML loader when associated layout file is initialized
     */
    public GuiController() {}
    
    /**
     * Initializes the GUI controller after FXML loading
     * <p>
     * Sets up fonts, keyboard input handling, notification layers, game over panel, and side bar buttons (pause, resume, exit, restart)
     * 
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            Font.loadFont(getClass().getResource("/digital.ttf").toExternalForm(), 38);
        } catch (Exception e) {
            System.err.println("Font loading failed: " + e.getMessage());
        }
        
        gamePanel.setFocusTraversable(true);
        gamePanel.requestFocus();
        
        // Setup Notification Layer
        if (groupNotification != null) {
             groupNotification.setVisible(true); 
             groupNotification.setStyle("-fx-background-color: transparent;");
             groupNotification.setMouseTransparent(true);
             groupNotification.setPickOnBounds(false); 
        }
        
        // Setup Game Over Panel
        if (gameOverPanel != null) {
            gameOverPanel.setVisible(false); 
            gameOverPanel.setup(this);
            gameOverPanel.setMouseTransparent(false);
        }

        // --- Keyboard Event Handling ---
        gamePanel.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (isPause.getValue() == Boolean.FALSE && isGameOver.getValue() == Boolean.FALSE) {
                    if (keyEvent.getCode() == KeyCode.LEFT || keyEvent.getCode() == KeyCode.A) {
                        refreshBrick(eventListener.onLeftEvent(new MoveEvent(EventType.LEFT, EventSource.USER)));
                        keyEvent.consume();
                    }
                    if (keyEvent.getCode() == KeyCode.RIGHT || keyEvent.getCode() == KeyCode.D) {
                        refreshBrick(eventListener.onRightEvent(new MoveEvent(EventType.RIGHT, EventSource.USER)));
                        keyEvent.consume();
                    }
                    if (keyEvent.getCode() == KeyCode.UP || keyEvent.getCode() == KeyCode.W) {
                        refreshBrick(eventListener.onRotateEvent(new MoveEvent(EventType.ROTATE, EventSource.USER)));
                        keyEvent.consume();
                    }
                    if (keyEvent.getCode() == KeyCode.DOWN || keyEvent.getCode() == KeyCode.S) {
                        moveDown(new MoveEvent(EventType.DOWN, EventSource.USER));
                        keyEvent.consume();
                    }
                    if (keyEvent.getCode() == KeyCode.ENTER || keyEvent.getCode() == KeyCode.SPACE) {
                        hardDrop();
                        keyEvent.consume();
                    }
                    if (keyEvent.getCode() == KeyCode.SHIFT) {
                        eventListener.onHoldEvent();
                        keyEvent.consume();
                    }
                }
                if (keyEvent.getCode() == KeyCode.N) {
                    newGame(null);
                }
            }
        });

        final Reflection reflection = new Reflection();
        reflection.setFraction(0.8);
        reflection.setTopOpacity(0.9);
        reflection.setTopOffset(-12);

        // --- Sidebar Button Setup ---
        GameControls buttons = new GameControls();
        
        Button pauseBtn = buttons.getPauseButton();
        Button resumeBtn = buttons.getResumeButton();
        Button exitBtn = buttons.getExitButton();
        Button restartBtn = buttons.getRestartButton();
        
        if (buttonBar != null) {
            buttonBar.getChildren().clear();
            buttonBar.getChildren().addAll(pauseBtn, exitBtn, restartBtn);
        }

        if (resumeContainer != null) {
            resumeContainer.getChildren().clear();
            resumeContainer.getChildren().add(resumeBtn);
        }

        // PAUSE Action
        pauseBtn.setOnAction(e -> {
            isPause.set(true);
            if (pauseOverlay != null) pauseOverlay.setVisible(true);
            if (timeLine != null) timeLine.pause();
            gamePanel.requestFocus(); 
        });

        // RESUME Action
        resumeBtn.setOnAction(e -> {
            isPause.set(false);
            if (pauseOverlay != null) pauseOverlay.setVisible(false);
            gamePanel.requestFocus();
            if (timeLine != null) timeLine.play();
        });

        // EXIT Action
        exitBtn.setOnAction(e -> {
            Stage currentStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            if (currentStage != null) {
                currentStage.setScene(getMenuScene());;
            }
        });

        // RESTART Action
        restartBtn.setOnAction(e -> {
            newGame(null);
        });
    }

    /**
     * Displays a notification when rows are cleared
     * @param clearRow the {@link ClearRow} result containing cleared lines and score bonus
     */
    public void clearRow(ClearRow clearRow) {
        if (clearRow == null || clearRow.getLinesRemoved() == 0) return;

        int lines = clearRow.getLinesRemoved();
        String notificationText;
        switch (lines) {
            case 1: notificationText = "SINGLE"; break;
            case 2: notificationText = "DOUBLE"; break;
            case 3: notificationText = "TRIPLE"; break;
            case 4: notificationText = "TETRIS!"; break;
            default: notificationText = "+" + clearRow.getScoreBonus(); break;
        }

        NotificationPanel notificationPanel = new NotificationPanel(notificationText);
        
        if (groupNotification != null) {
            groupNotification.setVisible(true);
            groupNotification.setStyle("-fx-background-color: transparent;"); 
            groupNotification.getChildren().add(notificationPanel);
            notificationPanel.showScore(groupNotification.getChildren());
        }
    }

    /**
     * Displays the upcoming bricks queue in the preview panel
     * @param nextBricksQueue the list of upcoming {@link Brick} objects
     */
    public void showNextBricksQueue (List<Brick> nextBricksQueue) {
        if (nextBrickPanel == null) return;
        nextBrickPanel.getChildren().clear();

        if (nextBricksQueue == null || nextBricksQueue.isEmpty()) return;

        int previewSize = 18;
        // Vertical accumulator to stack bricks 
        int yAccumulator = 0;

        for (Brick brick : nextBricksQueue) {
            int[][] shape = brick.getShapeMatrix().get(0);
            int brickId = brick.getId();
            Paint color = getFillColor(brickId);

            int shapeHeight = shape.length;
            int shapeWidth = shape[0].length;
            int offsetX = (4 - shapeWidth) / 2; // Center in a 4-wide panel

            // Add brick to grid
            for (int i = 0; i < shapeHeight; i++) {
                for (int j = 0; j < shapeWidth; j++) {
                    if (shape[i][j] != 0) {
                        Rectangle rect = new Rectangle(previewSize, previewSize, color);
                        rect.setStroke(Color.BLACK);
                        nextBrickPanel.add(rect, j + offsetX, i + yAccumulator);
                    }
                }
            }
            // Move down for the next brick (height + 1 spacing)
            yAccumulator += shapeHeight + 30;
        }
        nextBrickPanel.setAlignment(Pos.CENTER);
    }

    /**
     * Initializes the game view with the board matrix and active brick
     * @param boardMatrix the current board state
     * @param brick the active {@link ViewData} brick
     */
    public void initGameView(int[][] boardMatrix, ViewData brick) {
        gamePanel.getChildren().clear(); 
        
        displayMatrix = new Rectangle[boardMatrix.length][boardMatrix[0].length];
        for (int i = 2; i < boardMatrix.length; i++) {
            for (int j = 0; j < boardMatrix[i].length; j++) {
                Rectangle rectangle = new Rectangle(BRICK_SIZE, BRICK_SIZE);
                rectangle.setFill(Color.TRANSPARENT);
                displayMatrix[i][j] = rectangle;
                gamePanel.add(rectangle, j, i - 2); 
            }
        }

        // --- FIX START: Reparent brickPanel to stable container ---
        // This moves brickPanel out of the dynamic 'Group' (which resizes and causes jumping)
        // and into the stable 'centerStack'.
        if (brickPanel != null && centerStack != null) {
             if (brickPanel.getParent() != centerStack) {
                 if (brickPanel.getParent() instanceof Pane oldParent) {
                     oldParent.getChildren().remove(brickPanel);
                 }
                 centerStack.getChildren().add(brickPanel);
                 brickPanel.setManaged(false); // Floating, does not affect layout
                 brickPanel.setMouseTransparent(true);
             }
        }

        // Initialize ghost brick
        ghostBrick = new GhostBrick(boardMatrix.length, boardMatrix[0].length);

        for (int i = 2; i < boardMatrix.length; i++) {
            for (int j = 0; j < boardMatrix[i].length; j++) {
                Rectangle ghostRect = ghostBrick.getRectangles()[i][j];
                ghostRect.setManaged(false);
                ghostRect.setMouseTransparent(true);
                
                // NOTE: We add ghost bricks to centerStack as well for stable positioning
                if (centerStack != null && !centerStack.getChildren().contains(ghostRect)) {
                    centerStack.getChildren().add(ghostRect);
                }
            }
        }

        rectangles = new Rectangle[brick.getBrickData().length][brick.getBrickData()[0].length];
        for (int i = 0; i < brick.getBrickData().length; i++) {
            for (int j = 0; j < brick.getBrickData()[i].length; j++) {
                Rectangle rectangle = new Rectangle(BRICK_SIZE, BRICK_SIZE);
                rectangle.setFill(getFillColor(brick.getBrickData()[i][j]));
                rectangles[i][j] = rectangle;
                brickPanel.add(rectangle, j, i);
            }
        }

        // Initial positioning update
        updateLayerPositions(brick);

        timeLine = new Timeline(new KeyFrame(
                Duration.millis(400),
                ae -> moveDown(new MoveEvent(EventType.DOWN, EventSource.THREAD))
        ));
        timeLine.setCycleCount(Timeline.INDEFINITE);
        timeLine.play();
    }

    /**
     * Updates the ghost brick view based on the current board state and active brick
     * <p>
     * Delegates to {@link GhostBrick#updateGhost(int[][], ViewData)} to redraw
     * The ghost outline at the lowest possible landing position
     * 
     * @param boardMatrix the current game board matrix
     * @param brick the {@link ViewData} representing the active brick 
     */
    private void updateGhostView(int[][] boardMatrix, ViewData brick) {
        if (ghostBrick != null && boardMatrix != null && brick != null) {
            ghostBrick.updateGhost(boardMatrix, brick);
        }
    }
    
    /**
     * Helper method to calculate absolute positions for floating layers (BrickPanel & GhostBrick) relative to the stable
     * {@code centerStack} container
     * <p>
     * This prevents jitter by ensuring that the brick and ghost overlays are aligned correctly with the underlying {@code gamePanel}
     * 
     * @param brick the {@link ViewData} representing the active brick
     */
    private void updateLayerPositions(ViewData brick) {
        if (centerStack == null || gamePanel == null) return;
        
        // Ensure we have bounds to work with. If scene isn't ready, this might be 0, 
        // but refreshBrick calls this constantly so it will correct itself.
        Bounds gameBounds = gamePanel.getBoundsInParent();
        // If gamePanel is wrapped in other containers inside centerStack, we might need recursive calculation.
        // But since gamePanel is inside BorderPane inside Group inside VBox inside centerStack...
        // Using localToScene is the safest way to find the visual difference.
        
        if (gamePanel.getScene() == null) return;
        
        Bounds gameToScene = gamePanel.localToScene(gamePanel.getLayoutBounds());
        Bounds stackToScene = centerStack.localToScene(centerStack.getLayoutBounds());
        
        double offsetX = gameToScene.getMinX() - stackToScene.getMinX();
        double offsetY = gameToScene.getMinY() - stackToScene.getMinY();

        double cellWidth = BRICK_SIZE + gamePanel.getHgap();
        double cellHeight = BRICK_SIZE + gamePanel.getVgap();
        
        // 1. Update Falling Brick Panel
        if (brickPanel != null) {
            brickPanel.setTranslateX(offsetX + brick.getxPosition() * cellWidth);
            // -42 (approx 2 rows) offset logic preserved but adapted to translation
            brickPanel.setTranslateY(offsetY + (brick.getyPosition() * cellHeight) - (2 * cellHeight));
        }
        
        // 2. Update Ghost Brick positions
        // GhostBrick update logic handles its own internal structure, we just need to ensure 
        // the individual rectangles are translated to the right spot on the screen.
        // However, GhostBrick implementation usually sets properties on the rectangles directly.
        // We iterate to update them relative to centerStack.
        
        // Note: To avoid iterating the whole grid every frame just for offsets, 
        // usually ghost bricks are updated via 'updateGhostView' which changes their stroke.
        // We just need to ensure their TRANSLATION is correct.
        // Since GhostBrick logic sets offsets assuming (0,0), we add the centerStack offset.
        
        if (ghostBrick != null) {
             Rectangle[][] gRects = ghostBrick.getRectangles();
             for (int i = 0; i < gRects.length; i++) {
                for (int j = 0; j < gRects[0].length; j++) {
                    // Logic from GhostBrick usually sets position? 
                    // If not, we set it here:
                    // Ghost brick grid logic uses full matrix (including hidden top rows)
                    // so row 'i' corresponds to actual grid row 'i'.
                    // We subtract 2 rows height to align with visible grid starting at row 2.
                    
                    double gX = offsetX + j * cellWidth;
                    double gY = offsetY + (i - 2) * cellHeight;
                    
                    gRects[i][j].setTranslateX(gX);
                    gRects[i][j].setTranslateY(gY);
                }
             }
        }
    }

    /**
     * Maps an integer value to a fill color for rendering bricks
     * <p>
     * Each integer corresponds to a specific color used in the game 
     * <ul>
     *  <li> 0 -> Transparent </li>
     *  <li> 1 -> Aqua </li>
     *  <li> 2 -> BlueViolet </li>
     *  <li> 3 -> DarkGreen </li>
     *  <li> 4 -> Yellow </li>
     *  <li> 5 -> Red </li>
     *  <li> 6 -> Beige </li>
     *  <li> 7 -> BurlyWood </li>
     *  <li> Default -> White </li>
     * </ul>
     * 
     * @param i the integer representing a brick type
     * @return the {@link Paint} color associated with the brick type
     */
    private Paint getFillColor(int i) {
        Paint returnPaint;
        switch (i) {
            case 0: returnPaint = Color.TRANSPARENT; break;
            case 1: returnPaint = Color.AQUA; break;
            case 2: returnPaint = Color.BLUEVIOLET; break;
            case 3: returnPaint = Color.DARKGREEN; break;
            case 4: returnPaint = Color.YELLOW; break;
            case 5: returnPaint = Color.RED; break;
            case 6: returnPaint = Color.BEIGE; break;
            case 7: returnPaint = Color.BURLYWOOD; break;
            default: returnPaint = Color.WHITE; break;
        }
        return returnPaint;
    }

    /**
     * Refreshes the active brick rendering
     * @param brick {@link ViewData} representing the current brick
     */
    public void refreshBrick(ViewData brick) {
        if (isPause.getValue() == Boolean.FALSE) {
            
            // Update absolute positions based on current layout
            updateLayerPositions(brick);
            
            for (int i = 0; i < brick.getBrickData().length; i++) {
                for (int j = 0; j < brick.getBrickData()[i].length; j++) {
                    setRectangleData(brick.getBrickData()[i][j], rectangles[i][j]);
                }
            }
            updateGhostView(eventListener.getBoardMatrix(), brick);
        }
    }

    /**
     * Refreshes the background board rendering
     * @param board the board matrix
     */
    public void refreshGameBackground(int[][] board) {
        for (int i = 2; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                setRectangleData(board[i][j], displayMatrix[i][j]);
            }
        }
    }

    /**
     * Updates the visual properties of a rectangle representing a brick cell
     * <p>
     * Sets the fill color based on the given integer value using {@link #getFillcolor(int)}, applies rounded corners for a smoother visual appearance
     * 
     * @param color the integer representing the brick type or state
     * @param rectangle the {@link Rectangle} to update
     */
    private void setRectangleData(int color, Rectangle rectangle) {
        rectangle.setFill(getFillColor(color));
        rectangle.setArcHeight(9);
        rectangle.setArcWidth(9);
    }

    /**
     * Handles the downward movement of the brick
     * @param event the {@link MoveEvent} representing the down action
     */
    private void moveDown(MoveEvent event) {
        if (isPause.getValue() == Boolean.FALSE) {
            DownData downData = eventListener.onDownEvent(event);
            if (downData.getClearRow() != null && downData.getClearRow().getLinesRemoved() > 0) {
                clearRow(downData.getClearRow());
            }
            refreshBrick(downData.getViewData());
        }
        gamePanel.requestFocus();
    }

    /**
     * Sets the event listener that handles gameplay input events
     * <p>
     * The {@link InputEventListener} is typically implemented by the {@link GameController}, which processes user actions such as moving, rotating, holding, or dropping bricks
     * This allows the {@code GuiController} to delegate input handling to the game logic 
     * 
     * @param eventListener the {@link InputEventListener} to receive and process input events 
     */
    public void setEventListener(InputEventListener eventListener) {
        this.eventListener = eventListener;
    }

    /**
     * Binds the score label to the given property
     * @param integerProperty the score property
     */
    public void bindScore(IntegerProperty integerProperty) {
        scoreLabel.textProperty().bind(integerProperty.asString("%d"));
    }

    /**
     * Binds the timer label to the given property
     * @param secondsProperty the countdown property
     */
    public void bindTimer(IntegerProperty secondsProperty) {
        timerLabel.textProperty().bind(
            javafx.beans.binding.Bindings.createStringBinding(
                () -> formatTime(secondsProperty.get()), secondsProperty
                )
        );
    }

    /**
     * Handles game over state: stops timers, shows overlay, and displays final score
     * <p>
     * Stops the active timeline, displays the game over overlay, shows the final score on the {@link GameOverPanel}, and marks the game as over
     * 
     */
    public void gameOver() {
        if (timeLine != null) {
            timeLine.stop();
        }
        if (groupNotification != null) {
            groupNotification.setVisible(true);
            groupNotification.setStyle("-fx-background-color: rgba(0, 0, 0, 0.85);");
            groupNotification.setMouseTransparent(false);
            groupNotification.setPickOnBounds(true);
        } 
        if (gameOverPanel != null) {
            int finalScore = Integer.parseInt(scoreLabel.getText());
            gameOverPanel.showFinalScore(finalScore);
            gameOverPanel.setVisible(true);
        } 
        isGameOver.setValue(Boolean.TRUE);
    }

    /**
     * Starts a new game by resetting the board, overlays and timers
     * <p> 
     * Hides the game over panel, resets pause state, and delegates to the {@link InputEventListener} to create a new game instance
     * 
     * @param actionEvent the triggering event 
     */
    public void newGame(ActionEvent actionEvent) {
        if (timeLine != null) timeLine.stop();
        if (groupNotification != null) {
            groupNotification.setVisible(true);
            groupNotification.setStyle("-fx-background-color: transparent;");
            groupNotification.setPickOnBounds(false);
            groupNotification.setMouseTransparent(true);
        }
        if (gameOverPanel != null) {
            gameOverPanel.setVisible(false);
            gameOverPanel.setMouseTransparent(false);
        }

        if (eventListener != null) {
            eventListener.createNewGame();
        }

        gamePanel.requestFocus();
        if (timeLine != null) timeLine.playFromStart();
        isPause.setValue(Boolean.FALSE);
        isGameOver.setValue(Boolean.FALSE);

        if (pauseOverlay != null) {
            pauseOverlay.setVisible(false);
            pauseOverlay.setMouseTransparent(false);
        }
    }

    /**
     * Pauses the game by shifting focus back to the game panel
     * <p>
     * This method does not alter timers or overlays directly, but ensures keyboard input remains focused on the game
     * 
     * @param actionEvent the triggering event
     */
    public void pauseGame(ActionEvent actionEvent) {
        gamePanel.requestFocus();
    }

    /**
     * Performs a hard drop of the active brick
     * <p>
     * Continuosly moves the brick downward until it lands, refreshing the view at each step
     * Updates the background and clears rows if necessary
     * 
     */
    public void hardDrop() {
        if (isPause.get() || isGameOver.get()) {
            return;
        }

        DownData downData;
        boolean landed = false;

        do { 
            downData = eventListener.onDownEvent(new MoveEvent (EventType.DOWN, EventSource.USER));
            landed = downData.isLanded();
            refreshBrick(downData.getViewData());
        } while (!landed);

        if (eventListener.getBoardMatrix() != null) {
            refreshGameBackground(eventListener.getBoardMatrix());
        }

        if (downData.getClearRow() != null && downData.getClearRow().getLinesRemoved() > 0) {
            clearRow(downData.getClearRow());
        }
        gamePanel.requestFocus();
    }

    /**
     * Sets the active game board instance
     * @param board the {@link Board} to associate with this controller
     */
    public void setBoard (Board board) {
        this.board = board;
    }

    /**
     * Returns the panel used to display the held brick
     * @return the {@link GridPane} representing the hold brick panel
     */
    public GridPane getHoldBrickPanel() {
        return holdBrickPanel;
    }

    /**
     * Sets the panel used to display the held brick
     * @param panel the {@link GridPane} to assign as the hold brick panel
     */
    public void setHoldBrickPanel(GridPane panel) {
        this.holdBrickPanel = panel;
    }

    /**
     * Sets the main game panel where the board is rendered
     * @param panel the {@link GridPane} representing the game panel
     */
    public void setGamePanel(GridPane panel) {
        this.gamePanel = panel;
    }

    /**
     * Sets the panel used to render the active brick
     * @param panel the {@link GridPane} representing the brick panel
     */
    public void setBrickPanel(GridPane panel) {
        this.brickPanel = panel;
    }

    /**
     * Sets the label used to display the score
     * @param label the {@link Label} to bind to the score
     */
    public void setScoreLabel(Label label) {
        this.scoreLabel = label;
    }
    
    /**
     * Sets the label used to display the timer
     * @param label the {@link Label} to bind to the timer
     */
    public void setTimerLabel(Label label) {
        this.timerLabel = label;
    }

    /**
     * Starts a countdown timer for timed game mode
     * <p>
     * Updated the {@code remainingSecondsProperty} every second and trigger {@link #gameOver()} when the timer reaches zero
     * 
     * @param startSeconds the starting time in seconds
     */
    public void startCountdown(int startSeconds) {
        if (countdownTimer != null) {
            countdownTimer.stop();
        }
        
        remainingSecondsProperty.set(startSeconds);

        countdownTimer = new Timeline(
            new KeyFrame(Duration.seconds(1), e -> {
                remainingSecondsProperty.set(remainingSecondsProperty.get() - 1);

                if (remainingSecondsProperty.get() <= 0) {
                    countdownTimer.stop();
                    gameOver();
                }
            })
        );    
        countdownTimer.setCycleCount(startSeconds);
        countdownTimer.playFromStart();
    }

    /**
     * Formats a time value in seconds into a {@code mm:ss} string
     * @param totalSeconds the total time in seconds
     * @return a formatted string in {@code mm:ss} format
     */
    private String formatTime(int totalSeconds) {
        int mins = totalSeconds / 60;
        int secs = totalSeconds % 60;
        return String.format("%02d:%02d", mins, secs);
    }

    /**
     * Returns the property representing the remaining countdown seconds
     * @return the {@link IntegerProperty} for remaining seconds
     */
    public IntegerProperty getRemainingSecondsProperty() {
        return remainingSecondsProperty;
    }

    /**
     * Resets the countdown timer to zero and stops it if running
     */
    public void resetTimer() {
        if (countdownTimer != null) {
            countdownTimer.stop();
        }
        remainingSecondsProperty.set(0);
    }

    /**
     * Sets the menu scene to be displayed when exiting the game
     * @param menuScene the {@link Scene} representing the menu
     */
    public void setMenuScene(Scene menuScene) {
        this.menuScene = menuScene;
    }

    /**
     * Returns the menu scene associated with this controller
     * @return the {@link Scene} representing the menu
     */
    public Scene getMenuScene() {
        return menuScene;
    }

    /**
     * Sets the notification overlay container
     * @param groupNotification the {@link StackPane} used for notifications 
     */
    public void setGroupNotification(StackPane groupNotification) {
        this.groupNotification = groupNotification;
    }

    /**
     * Sets the pause overlay container
     * @param pauseOverlay the {@link StackPane} used for pause overlay
     */
    public void setPauseOverlay(StackPane pauseOverlay) {
        this.pauseOverlay = pauseOverlay;
    }

    /**
     * Sets the sidebar button container
     * @param buttonBar the {@link VBox} containing control buttons
     */
    public void setButtonBar(VBox buttonBar) {
        this.buttonBar = buttonBar;
    }

    /**
     * Sets the resume button container
     * @param resumeContainer the {@link VBox} containing the resume button
     */
    public void setResumeContainer(VBox resumeContainer) {
        this.resumeContainer = resumeContainer;
    }

    /**
     * Returns the label used to display the current score
     * @return the {@link Label} bound to the score property
     */
    public Label getScoreLabel() {
        return scoreLabel;
    }

    /**
     * Returns the label used to display the game timer
     * @return the {@link Label} bound to the timer property
     */
    public Label getTimerLabel() {
        return timerLabel;
    }
}

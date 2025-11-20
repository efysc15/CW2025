package com.comp2042;

import java.net.URL;
import java.util.ResourceBundle;

import com.comp2042.logic.bricks.Brick;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button; 
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GuiController implements Initializable {

    private static final int BRICK_SIZE = 20;

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

    private Rectangle[][] displayMatrix;
    private InputEventListener eventListener;
    private Rectangle[][] rectangles;
    private Timeline timeLine;
    private final BooleanProperty isPause = new SimpleBooleanProperty();
    private final BooleanProperty isGameOver = new SimpleBooleanProperty();
    private GhostBrick ghostBrick;
    private Board board; 

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Load font and set focus
        Font.loadFont(getClass().getClassLoader().getResource("digital.ttf").toExternalForm(), 38);
        gamePanel.setFocusTraversable(true);
        gamePanel.requestFocus();
        
        if (groupNotification != null) {
             groupNotification.setVisible(false);
        }
        
        // CRITICAL FIX: Self-contained initialization of Game Over Panel buttons.
        if (gameOverPanel != null) {
            // Pass the local newGame method (as a Runnable action) to the setup function.
            gameOverPanel.setup(() -> newGame(null));
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

        // Helper class for sidebar buttons, using inline styling (green neon)
        class GameControls {
            private Button createButton(String text) {
                Button btn = new Button(text);
                // Inline style for green neon theme (assuming the main button theme is neon blue)
                String neonStyle = 
                    "-fx-background-color: black; " + 
                    "-fx-text-fill: white; " + 
                    "-fx-border-color: #00FF00; " + // Green Border
                    "-fx-border-width: 2px; " +
                    "-fx-border-radius: 5px; " +
                    "-fx-padding: 8px 15px; " +
                    "-fx-font-size: 14px; " +
                    "-fx-font-weight: bold; " +
                    "-fx-effect: dropshadow(gaussian, #00FF00, 10, 0.5, 0, 0);";
                btn.setStyle(neonStyle);
                return btn;
            }
            public Button getPauseButton() { return createButton("PAUSE"); }
            public Button getResumeButton() { return createButton("RESUME"); }
            public Button getExitButton() { return createButton("EXIT"); }
        }
        
        // --- Sidebar Button Setup (Pause/Exit/Resume) ---
        GameControls buttons = new GameControls();
        
        buttonBar.getChildren().clear();
        buttonBar.getChildren().addAll(
            buttons.getPauseButton(),
            buttons.getExitButton()
        );

        if (resumeContainer != null) {
            resumeContainer.getChildren().clear();
            resumeContainer.getChildren().add(buttons.getResumeButton());
        }

        // PAUSE Action
        buttons.getPauseButton().setOnAction(e -> {
            isPause.set(true);
            if (pauseOverlay != null) pauseOverlay.setVisible(true);
            if (timeLine != null) timeLine.pause();
        });

        // RESUME Action
        buttons.getResumeButton().setOnAction(e -> {
            isPause.set(false);
            if (pauseOverlay != null) pauseOverlay.setVisible(false);
            gamePanel.requestFocus();
            if (timeLine != null) timeLine.play();
        });

        // EXIT Action
        buttons.getExitButton().setOnAction(e -> {
            // Find the current Stage using the button's scene reference
            Stage currentStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            if (currentStage != null) {
                currentStage.close();
            }
        });
    }

    public void showNextBrick (Brick nextBricks) {
        if (nextBrickPanel == null) return;
        nextBrickPanel.getChildren().clear();

        if (nextBricks == null) return;

        int[][] shape = nextBricks.getShapeMatrix().get(0);
        int brickId = nextBricks.getId();
        Paint color = getFillColor(brickId);

        int previewSize = 18;
        
        int shapeHeight = shape.length;
        int shapeWidth = shape[0].length;

        int panelCols = 6;
        int panelRows = 6;

        int offsetX = (panelCols - shapeWidth) / 2;
        int offsetY = (panelRows - shapeHeight) / 2;

        for (int i = 0; i < shapeHeight; i++) {
            for (int j = 0; j < shapeWidth; j++) {
                if (shape[i][j] != 0) {
                    Rectangle rect = new Rectangle (previewSize, previewSize, color);
                    rect.setStroke(Color.BLACK);
                    nextBrickPanel.add(rect, j + offsetX, i + offsetY);
                }
            }
        }

        nextBrickPanel.setAlignment(Pos.CENTER);
    }

    public void initGameView(int[][] boardMatrix, ViewData brick) {
        // Initialize the main game grid
        displayMatrix = new Rectangle[boardMatrix.length][boardMatrix[0].length];
        for (int i = 2; i < boardMatrix.length; i++) {
            for (int j = 0; j < boardMatrix[i].length; j++) {
                Rectangle rectangle = new Rectangle(BRICK_SIZE, BRICK_SIZE);
                rectangle.setFill(Color.TRANSPARENT);
                displayMatrix[i][j] = rectangle;
                gamePanel.add(rectangle, j, i - 2);
            }
        }

        // Initialize ghost brick
        ghostBrick = new GhostBrick(boardMatrix.length, boardMatrix[0].length);

        // --- GHOST BRICK ALIGNMENT FIX APPLIED HERE ---
        // The display grid starts at matrix row 2. We need to translate the ghost 
        // rectangles (which are direct children of gamePanel) by (i - 2) rows to align 
        // with the visible portion of the GridPane.
        for (int i = 2; i < boardMatrix.length; i++) {
            for (int j = 0; j < boardMatrix[i].length; j++) {
                Rectangle ghostRect = ghostBrick.getRectangles()[i][j];
                ghostRect.setManaged(false);
                ghostRect.setMouseTransparent(true);
                
                // Translate X: column 'j'
                // FIX: Added parentheses to getHgap()
                ghostRect.setTranslateX(j * (BRICK_SIZE + gamePanel.getHgap()));
                
                // Use i - 2 for Y translation, accounting for the hidden rows and grid spacing
                ghostRect.setTranslateY((i - 2) * (BRICK_SIZE + gamePanel.getVgap())); 
                
                gamePanel.getChildren().add(ghostRect);
            }
        }
        // --- END GHOST BRICK ALIGNMENT FIX ---

        // Initialize the current falling brick view
        rectangles = new Rectangle[brick.getBrickData().length][brick.getBrickData()[0].length];
        for (int i = 0; i < brick.getBrickData().length; i++) {
            for (int j = 0; j < brick.getBrickData()[i].length; j++) {
                Rectangle rectangle = new Rectangle(BRICK_SIZE, BRICK_SIZE);
                rectangle.setFill(getFillColor(brick.getBrickData()[i][j]));
                rectangles[i][j] = rectangle;
                brickPanel.add(rectangle, j, i);
            }
        }

        brickPanel.setLayoutX(gamePanel.getLayoutX() + brick.getxPosition() * brickPanel.getVgap() + brick.getxPosition() * BRICK_SIZE);
        brickPanel.setLayoutY(-42 + gamePanel.getLayoutY() + brick.getyPosition() * brickPanel.getHgap() + brick.getyPosition() * BRICK_SIZE);

        // Start the game loop
        timeLine = new Timeline(new KeyFrame(
                Duration.millis(400),
                ae -> moveDown(new MoveEvent(EventType.DOWN, EventSource.THREAD))
        ));
        timeLine.setCycleCount(Timeline.INDEFINITE);
        timeLine.play();
    }

    private void updateGhostView(int[][] boardMatrix, ViewData brick) {
        if (ghostBrick != null && boardMatrix != null && brick != null) {
            ghostBrick.updateGhost(boardMatrix, brick);
        }
    }

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


    public void refreshBrick(ViewData brick) {
        if (isPause.getValue() == Boolean.FALSE) {
            // Update the position of the brick view
            brickPanel.setLayoutX(gamePanel.getLayoutX() + brick.getxPosition() * brickPanel.getVgap() + brick.getxPosition() * BRICK_SIZE);
            brickPanel.setLayoutY(-42 + gamePanel.getLayoutY() + brick.getyPosition() * brickPanel.getHgap() + brick.getyPosition() * BRICK_SIZE);
            
            // Update the color/shape of the brick view
            for (int i = 0; i < brick.getBrickData().length; i++) {
                for (int j = 0; j < brick.getBrickData()[i].length; j++) {
                    setRectangleData(brick.getBrickData()[i][j], rectangles[i][j]);
                }
            }
            // Update ghost piece position
            updateGhostView(eventListener.getBoardMatrix(), brick);
        }
    }

    public void refreshGameBackground(int[][] board) {
        // Update the static pieces on the board
        for (int i = 2; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                setRectangleData(board[i][j], displayMatrix[i][j]);
            }
        }
    }

    private void setRectangleData(int color, Rectangle rectangle) {
        rectangle.setFill(getFillColor(color));
        rectangle.setArcHeight(9);
        rectangle.setArcWidth(9);
    }

    private void moveDown(MoveEvent event) {
        if (isPause.getValue() == Boolean.FALSE) {
            DownData downData = eventListener.onDownEvent(event);
            // Handle line clear notification
            if (downData.getClearRow() != null && downData.getClearRow().getLinesRemoved() > 0) {
                NotificationPanel notificationPanel = new NotificationPanel("+" + downData.getClearRow().getScoreBonus());
                groupNotification.getChildren().add(notificationPanel);
                notificationPanel.showScore(groupNotification.getChildren());
            }
            refreshBrick(downData.getViewData());
        }
        gamePanel.requestFocus();
    }

    public void setEventListener(InputEventListener eventListener) {
        this.eventListener = eventListener;
    }

    public void bindScore(IntegerProperty integerProperty) {
        scoreLabel.textProperty().bind(integerProperty.asString("%d"));
    }

    public void gameOver() {
        if (timeLine != null) timeLine.stop();
        if (groupNotification != null) groupNotification.setVisible(true);
        if (gameOverPanel != null) gameOverPanel.setVisible(true);
        isGameOver.setValue(Boolean.TRUE);
    }

    /**
     * Resets the game state and UI to start a new game.
     * This method is executed when the New Game button (or N key) is pressed.
     */
    public void newGame(ActionEvent actionEvent) {
        if (timeLine != null) timeLine.stop();
        if (groupNotification != null) groupNotification.setVisible(false);
        if (gameOverPanel != null) gameOverPanel.setVisible(false);
        
        // Tells the GameController logic to start over
        if (eventListener != null) {
            eventListener.createNewGame();
        }
        
        gamePanel.requestFocus();
        if (timeLine != null) timeLine.play();
        isPause.setValue(Boolean.FALSE);
        isGameOver.setValue(Boolean.FALSE);
    }

    public void pauseGame(ActionEvent actionEvent) {
        gamePanel.requestFocus();
    }

    public void hardDrop() {
        if (isPause.get() || isGameOver.get()) {
            return;
        }

        DownData downData;
        boolean landed = false;

        // Force the brick down until it lands
        do { 
            downData = eventListener.onDownEvent(new MoveEvent (EventType.DOWN, EventSource.USER));
            landed = downData.isLanded();
            refreshBrick(downData.getViewData());
        } while (!landed);

        // Update the static background after landing
        if (eventListener.getBoardMatrix() != null) {
            refreshGameBackground(eventListener.getBoardMatrix());
        }

        // Handle scoring for the hard drop
        if (downData.getClearRow() != null && downData.getClearRow().getLinesRemoved() > 0) {
            int lines = downData.getClearRow().getLinesRemoved();
            int bonus = switch (lines) {
                case 1 -> 50;
                case 2 -> 120;
                case 3 -> 360;
                case 4 -> 1500;
                default -> 0;
            };

            if (bonus > 0) {
                NotificationPanel notificationPanel = new NotificationPanel("+" + bonus);
                groupNotification.getChildren().add(notificationPanel);
                notificationPanel.showScore(groupNotification.getChildren());
                
                if (board != null) {
                    board.getScore().add(bonus);
                }
            }   
        }
        
        gamePanel.requestFocus();
    }

    public void setBoard (Board board) {
        this.board = board;
    }

    public GridPane getHoldBrickPanel() {
        return holdBrickPanel;
    }
}

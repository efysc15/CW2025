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
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.Reflection;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
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
        try {
            Font.loadFont(getClass().getClassLoader().getResource("digital.ttf").toExternalForm(), 38);
        } catch (Exception e) {
            System.out.println("Font loading failed");
        }
        gamePanel.setFocusTraversable(true);
        gamePanel.requestFocus();
        
        if (groupNotification != null) {
             groupNotification.setVisible(false);
        }
        
        if (gameOverPanel != null) {
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

        // --- Sidebar Button Setup ---
        // Instantiate your external GameControls class
        GameControls buttons = new GameControls();
        
        // Add the buttons to the FXML - defined HBox
        buttonBar.getChildren().addAll(
            buttons.getPauseButton(),
            buttons.getResumeButton(),
            buttons.getExitButton()
        );

        // Button actions 
        buttons.getPauseButton().setOnAction(e -> {
            isPause.set(true);
        });

        buttons.getResumeButton().setOnAction(e -> {
            isPause.set(false);
            gamePanel.requestFocus();
        });

        buttons.getExitButton().setOnAction(e -> {
            Stage currentStage;
            if (stage != null) {
                currentStage = stage;
            } else {
                currentStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            }
            currentStage.close();
        });
    }

    /**
     * Displays the 'Next Brick' preview in the side panel
     * This method clears the current preview and draws the upcoming brick
     * centered inside the next brick panel
     * @param nextBricks the next brick object to be displayed in the preview
     */
    public void showNextBrick (Brick nextBricks) {
        // Safety check: if panel or brick is missing, do nothing
        if (nextBrickPanel == null) return;
        nextBrickPanel.getChildren().clear();

        if (nextBricks == null) return;

        // Get the brick's shape and color based on its ID
        int[][] shape = nextBricks.getShapeMatrix().get(0);
        int brickId = nextBricks.getId();
        Paint color = getFillColor(brickId);

        // Each small square (block) size in pixels
        int previewSize = 18;
        
        // Get dimensions of the brick's current shape
        int shapeHeight = shape.length;
        int shapeWidth = shape[0].length;

        // Define the grid dimensions for the preview box
        int panelCols = 6;
        int panelRows = 6;

        // Calculate offset values to center the brick in the preview panel
        int offsetX = (panelCols - shapeWidth) / 2;
        int offsetY = (panelRows - shapeHeight) / 2;

        // Draw each filled block of the brick on the panel
        for (int i = 0; i < shapeHeight; i++) {
            for (int j = 0; j < shapeWidth; j++) {
                if (shape[i][j] != 0) {
                    Rectangle rect = new Rectangle (previewSize, previewSize, color);
                    rect.setStroke(Color.BLACK);
                    nextBrickPanel.add(rect, j + offsetX, i + offsetY);
                }
            }
        }

        // Center the grid content within the panel visually
        nextBrickPanel.setAlignment(Pos.CENTER);
    }

    public void initGameView(int[][] boardMatrix, ViewData brick) {
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

        for (int i = 2; i < boardMatrix.length; i++) {
            for (int j = 0; j < boardMatrix[i].length; j++) {
                Rectangle ghostRect = ghostBrick.getRectangles()[i][j];
                ghostRect.setManaged(false);
                ghostRect.setMouseTransparent(true);
                ghostRect.setTranslateX(j * (BRICK_SIZE + gamePanel.getVgap()));
                ghostRect.setTranslateY((i - 1) * BRICK_SIZE);
                gamePanel.getChildren().add(ghostRect);
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

        brickPanel.setLayoutX(gamePanel.getLayoutX() + brick.getxPosition() * brickPanel.getVgap() + brick.getxPosition() * BRICK_SIZE);
        brickPanel.setLayoutY(-42 + gamePanel.getLayoutY() + brick.getyPosition() * brickPanel.getHgap() + brick.getyPosition() * BRICK_SIZE);

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


    public void refreshBrick(ViewData brick) {
        if (isPause.getValue() == Boolean.FALSE) {
            brickPanel.setLayoutX(gamePanel.getLayoutX() + brick.getxPosition() * brickPanel.getVgap() + brick.getxPosition() * BRICK_SIZE);
            brickPanel.setLayoutY(-42 + gamePanel.getLayoutY() + brick.getyPosition() * brickPanel.getHgap() + brick.getyPosition() * BRICK_SIZE);
            for (int i = 0; i < brick.getBrickData().length; i++) {
                for (int j = 0; j < brick.getBrickData()[i].length; j++) {
                    setRectangleData(brick.getBrickData()[i][j], rectangles[i][j]);
                }
            }
            updateGhostView(eventListener.getBoardMatrix(), brick);
        }
    }

    public void refreshGameBackground(int[][] board) {
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

    public void newGame(ActionEvent actionEvent) {
        if (timeLine != null) timeLine.stop();
        if (groupNotification != null) groupNotification.setVisible(false);
        if (gameOverPanel != null) gameOverPanel.setVisible(false);
        
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

        // Drop the brick all the way down
        do { 
            downData = eventListener.onDownEvent(new MoveEvent (EventType.DOWN, EventSource.USER));
            landed = downData.isLanded();
            refreshBrick(downData.getViewData());
        } while (!landed);

        if (board != null) {
            refreshGameBackground(boardMatrixFromDownData(downData));
        }

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

    private int[][] boardMatrixFromDownData(DownData data) {
        ViewData view = data.getViewData();
        return view != null ? view.getBrickData() : new int[0][0];
    }

    private Board board;
    public void setBoard (Board board) {
        this.board = board;
    }

    public GridPane getHoldBrickPanel() {
        return holdBrickPanel;
    }
}

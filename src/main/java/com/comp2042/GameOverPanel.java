package com.comp2042;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color; 

/**
 * A JavaFX UI component representing the "Game Over" panel
 * <p>
 * The {@code GameOverPanel} displays a "GAME OVER" message, the player's final score, and provides buttons to start a new game or exit back to the menu
 * 
 * <p>
 * This panel is designed to be loaded via FXML and initialized by the {@link GuiController}
 * 
 */
public class GameOverPanel extends VBox {

    /** Label used to display the player's final score */    
    private Label scoreLabel;

    /**
     * Constructs a new {@code GameOverPanel}
     * <p>
     * Initializes the "GAME OVER" label and an empty score label, styled with a neon theme
     * This parameterless constructor is required for FXML loading
     * 
     */
    public GameOverPanel() {
        Label gameOverLabel = new Label("GAME OVER");
        gameOverLabel.setTextFill(Color.WHITE); 
        gameOverLabel.getStyleClass().add("gameOverStyle");

        // Score label starts empty
        scoreLabel = new Label();
        scoreLabel.setTextFill(Color.YELLOW);
        scoreLabel.setStyle("-fx-font-size: 18px; -fx-font-weight:bold;");
        
        setAlignment(Pos.CENTER);
        setSpacing(20);
        // Only the label is added here initially
        getChildren().addAll(gameOverLabel, scoreLabel);
    }

    /**
     * Update the score shown on the panel
     * @param score the final score to display
     */
    public void showFinalScore(int score) {
        scoreLabel.setText("Final Score: " + score);
    }
    
    /**
     * Performs appplication-specific initialization after the FXML component is loaded
     * <p>
     * Adds the "New Game" and "Exit" buttons to the panel by instantiating a {@link GameOverButton} component
     * Ensures buttons are only added once
     * 
     * @param guiController the {@link GuiController} used to handle button actions
     */
    public void setup(GuiController guiController) {
        // Only create and add the button container once to prevent duplicates
        if (this.lookup("#gameOverButtons") == null) {
            // Instantiate the GameOverButton component, passing the required action
            GameOverButton buttons = new GameOverButton(guiController);
            buttons.setId("gameOverButtons"); 
            getChildren().add(buttons); 
        }
    }
}

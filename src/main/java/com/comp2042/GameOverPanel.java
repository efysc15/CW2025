package com.comp2042;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color; 

public class GameOverPanel extends VBox {

    private Label scoreLabel;

    // REQUIRED: Parameterless constructor for FXML Loader
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
     */

    public void showFinalScore(int score) {
        scoreLabel.setText("Final Score: " + score);
    }
    
    /**
     * Public method to perform application-specific initialization, 
     * called by the GuiController after the FXML component is loaded.
     * This method adds the actual "New Game" and "Exit" buttons.
     * @param newGameAction The Runnable action to start a new game (typically GuiController::newGame).
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

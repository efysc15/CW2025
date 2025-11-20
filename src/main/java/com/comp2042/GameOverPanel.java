package com.comp2042;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color; 


public class GameOverPanel extends VBox {

    // REQUIRED: Parameterless constructor for FXML Loader
    public GameOverPanel() {
        Label gameOverLabel = new Label("GAME OVER");
        gameOverLabel.setTextFill(Color.WHITE); 
        gameOverLabel.getStyleClass().add("gameOverStyle");
        
        setAlignment(Pos.CENTER);
        setSpacing(20);
        // Only the label is added here initially
        getChildren().addAll(gameOverLabel);
    }
    
    /**
     * Public method to perform application-specific initialization, 
     * called by the GuiController after the FXML component is loaded.
     * This method adds the actual "New Game" and "Exit" buttons.
     * @param newGameAction The Runnable action to start a new game (typically GuiController::newGame).
     */
    public void setup(Runnable newGameAction) {
        // Only create and add the button container once to prevent duplicates
        if (this.lookup("#gameOverButtons") == null) {
            // Instantiate the GameOverButton component, passing the required action
            GameOverButton buttons = new GameOverButton(newGameAction);
            buttons.setId("gameOverButtons"); 
            getChildren().add(buttons); 
        }
    }
}

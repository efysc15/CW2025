package com.comp2042;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class GameOverPanel extends VBox {

    public GameOverPanel() {
        Label gameOverLabel = new Label("GAME OVER");
        gameOverLabel.getStyleClass().add("gameOverStyle");

        // Center everything and add some space between
        setAlignment(Pos.CENTER);
        setSpacing(20);
        getChildren().addAll(gameOverLabel);

        // Hide it by default until the game is over
        setVisible(false);       
    }

    public GameOverPanel (Stage stage, Runnable newGameAction) {
        this(); // Reuse the basic layout setup above
        // Create "New Game" and "Exit" buttons
        GameOverButton buttons = new GameOverButton (stage, newGameAction);

        getChildren().add(buttons); // Add buttons below the "GAME OVER" label 
    }

}

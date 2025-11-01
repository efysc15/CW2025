package com.comp2042;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
 
public class GameOverButton extends VBox{

    public GameOverButton(Stage stage, Runnable newGameAction) {
        Button newGameButton = new Button ("New Game");
        Button exitButton = new Button ("Exit Game");

        newGameButton.setId("newGameButton");
        exitButton.setId("exitButton");

        // Button Actions
        newGameButton.setOnAction(e -> newGameAction.run());
        exitButton.setOnAction(e -> {
            Stage currentStage;
            if (stage != null) {
                currentStage = stage;
            } else {
                currentStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            }
            currentStage.close();
        });

        // Layout settings
        setAlignment (Pos.CENTER);
        setSpacing (15);
        getChildren().addAll(newGameButton, exitButton);
    }
    
}

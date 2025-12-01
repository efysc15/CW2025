package com.comp2042;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
 
public class GameOverButton extends VBox{
    private final GuiController guiController;

    /**
     * Creates the buttons and attaches their actions, applying inline styles.
     * @param newGameAction The Runnable action to be executed when 'New Game' is clicked.
     */
    public GameOverButton(GuiController guiController) {
        this.guiController = guiController;
        Button newGameButton = new Button ("NEW GAME (N)"); 
        Button exitButton = new Button ("EXIT GAME");

        // Define the inline style for the neon theme.
        String neonStyle = 
            "-fx-background-color: black; " +             // Black Background
            "-fx-text-fill: white; " +                    // White Text
            "-fx-border-color: #00FFFF; " +               // Neon Blue/Cyan Border
            "-fx-border-width: 2px; " +
            "-fx-border-radius: 5px; " +
            "-fx-padding: 8px 15px; " +
            "-fx-font-size: 14px; " +
            "-fx-font-weight: bold; " +
            // Add a dropshadow effect for the neon glow
            "-fx-effect: dropshadow(gaussian, #00FFFF, 10, 0.5, 0, 0);"; 

        // Apply the inline style to both buttons
        newGameButton.setStyle(neonStyle);
        exitButton.setStyle(neonStyle);
        
        // Set IDs for easy styling/lookup if needed (still useful for other purposes)
        newGameButton.setId("newGameButton");
        exitButton.setId("exitButton");

        // --- Button Actions ---
        
        // 1. New Game action simply runs the callback provided by the GuiController
        newGameButton.setOnAction(e -> guiController.newGame(e));
        
        // 2. Exit action finds the current Stage from the button's scene
        exitButton.setOnAction(e -> Platform.exit());

        // --- Layout settings ---
        setAlignment (Pos.CENTER);
        setSpacing (15);
        getChildren().addAll(newGameButton, exitButton);
    }
    
}

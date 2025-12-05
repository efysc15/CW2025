package com.comp2042;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * A JavaFX UI component that displays the "Game Over" menu
 * <p>The {@code GameOverButton} provides two buttons: 
 * <ul>
 *  <li><b> New Game </b> - Starts a new game by invoking the {@link GuiController} </li>
 *  <li><b> Exit Game </b> - Returns the player to the main menu scene </li>
 * </ul>
 * <p> Both buttons are styled with a neon theme and arranged vertically in the center of the layout.
 */
public class GameOverButton extends VBox{
    /** Reference to the GUI controller for handling button actions */
    private final GuiController guiController;

    /**
     * Constructs a new {@code GameOverButton} layout with "New Game" and "Exit Game" buttons
     * <p>
     * The buttons are styled with a neon theme and aligned vertically with spacing
     * 
     * @param guiController the {@link GuiController} used to handle button actions
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
        exitButton.setOnAction(e -> {
            Stage stage = (Stage) exitButton.getScene().getWindow();
            stage.setScene(guiController.getMenuScene());
        });

        // --- Layout settings ---
        setAlignment (Pos.CENTER);
        setSpacing (15);
        getChildren().addAll(newGameButton, exitButton);
    }
    
}

package com.comp2042;

import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * A JavaFX overlay component that displays help information for the game
 * <p>
 * The {@code HelpOverlay} provides three sections 
 * <ul>
 *  <li> <b>Controls</b> - Explains how to move, rotate, drop and hold piece </li>
 *  <li> <b>Rules</b> - Describes the objective of clearing line and ending conditions </li>
 *  <li> <b>Scoring</b> - Details the poins awarded for clearing lines </li>
 * </ul>
 * <p>
 * The overlay is styled with a semi-transparent background and cyan border and includes a "Back to Menu" button to return to the main menu
 * 
 */
public class HelpOverlay extends StackPane {

    /**
     * Constructs a new {@code HelpOverlay}
     * <p>
     * Initializes the layout with section buttons, a description area, and a back button.
     * Each section button updates the description with detailed text when clicked
     * 
     * @param menuScene the {@link Scene} representing the main menu of the application
     *                  This is the scene that the user will be returned to when clicking the "Back to Menu" button.
     *                  It provides the navigation target for exiting the help overlay and restoring the menu interface.
     * @param backToMenuAction a {@link Runnable} action executed when the "Back to Menu" button is clicked.
     *                         This allows additional cleanup or state-reset logic to run before switching back to menu scene.
     */
    public HelpOverlay(Scene menuScene, Runnable backToMenuAction) {
        setStyle("-fx-background-color: rgba(0,0,0,0.85);");

        // Section buttons
        Button controlsBtn = createPlainButton("Controls");
        Button rulesBtn    = createPlainButton("Rules");
        Button scoringBtn  = createPlainButton("Scoring");

        VBox leftMenu = new VBox(15, controlsBtn, rulesBtn, scoringBtn);
        leftMenu.setAlignment(Pos.TOP_LEFT);

        // Vertical separator
        Separator separator = new Separator(Orientation.VERTICAL);
        separator.setPrefHeight(200);
        separator.setStyle("-fx-background-color: white;");

        // Description area
        Label description = new Label("Select a section to learn more.");
        description.setFont(new Font("Arial", 16));
        description.setTextFill(Color.WHITE);
        description.setWrapText(true);

        VBox rightBox = new VBox(description);
        rightBox.setAlignment(Pos.TOP_LEFT);
        rightBox.setPrefWidth(350);
        rightBox.setStyle("-fx-padding: 10;");

        // Back button
        Button backBtn = createPlainButton("BACK TO MENU");
        backBtn.setFont(new Font("Arial", 18));
        backBtn.setOnAction(e -> {
            if (backToMenuAction != null) backToMenuAction.run();
        });

        // Main content box
        HBox contentBox = new HBox(20, leftMenu, separator, rightBox);
        contentBox.setAlignment(Pos.CENTER_LEFT);

        VBox overlayBox = new VBox(20, contentBox, backBtn);
        overlayBox.setAlignment(Pos.CENTER);
        overlayBox.setPrefSize(600, 400);
        overlayBox.setMaxSize(600, 400);
        overlayBox.setStyle(
            "-fx-padding: 20;" +
            "-fx-background-color: rgba(0,0,0,0.95);" +
            "-fx-border-color: cyan;" +
            "-fx-border-width: 2;" +
            "-fx-background-radius: 15;" +
            "-fx-border-radius: 15;"
        );

        StackPane.setAlignment(overlayBox, Pos.CENTER);
        getChildren().add(overlayBox);

        // Button actions with fuller text
        controlsBtn.setOnAction(e -> description.setText(
            "Controls:\n\n" +
            "Use the arrow keys or WASD to move and rotate pieces. " +
            "Press SPACE or ENTER to drop a piece instantly, and SHIFT to hold a piece for later. " +
            "These controls give you flexibility to plan ahead and react quickly."
        ));

        rulesBtn.setOnAction(e -> description.setText(
            "Rules:\n\n" +
            "The goal is to clear lines by filling every block in a row. " +
            "The game ends when the stack of pieces reaches the top of the board. " +
            "Stay focused and keep the board as clear as possible to survive longer."
        ));

        scoringBtn.setOnAction(e -> description.setText(
            "Scoring:\n\n" +
            "Points are awarded based on how many lines you clear at once:\n" +
            " - Single Line: (50 * 1) points\n" +
            " - Double Line: (50 * 2) points\n" +
            " - Triple Line: (50 * 3)) points\n" +
            " - Tetris (4 lines): (50 * 4) points\n\n" +
            "Clearing multiple lines at once gives you a big bonus, so aim for Tetrises!"
        ));
    }

    /**
     * Creates a plain-styled button with white text and transparent background 
     * @param text the label text for the button
     * @return a styled {@link Button} instance
     */
    private Button createPlainButton(String text) {
        Button btn = new Button(text);
        btn.setFont(new Font("Arial", 16));
        btn.setTextFill(Color.WHITE);
        btn.setStyle("-fx-background-color: transparent; -fx-padding: 8 15;");
        return btn;
    }
}







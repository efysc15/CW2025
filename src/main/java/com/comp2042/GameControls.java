package com.comp2042;

import javafx.scene.control.Button;

/**
 * Provides styled control buttons for the game interface
 * <p> 
 * The {@code GameControls} class creates and manages common buttons such as pause, restart, exit and resume
 * 
 */
public class GameControls {

    /** The border color used for button styling */
    private static final String BORDER_COLOR = "lime"; // Spring Green
    /** The text color used for button labels */
    private static final String TEXT_COLOR = "white";
    /** The background color used for buttons */
    private static final String BG_COLOR = "black";

    /** Common style properties applied to all buttons */
    private static final String BASE_PROPERTIES = 
        "-fx-font-size: 14px;" +
        "-fx-font-weight: bold;" +
        "-fx-background-radius: 8;" +
        "-fx-border-radius: 8;" +
        "-fx-border-width: 2px;" +
        "-fx-padding: 8px 12px;" +
        "-fx-min-width: 120px;" +
        "-fx-alignment: center;";

    /** Button used to pause the game */
    private final Button pauseBtn = createButton ("PAUSE");
    /** Button used to restart the game */
    private final Button restartBtn =createButton("RESTART");   // New Button Added
    /** Button used to exit the game */
    private final Button exitBtn = createButton ("EXIT");
    /** Button used to resume the game */
    private final Button resumeBtn = createButton("RESUME");

    /** Creates a new {@code GameControls} instance with all buttons initialized and styled */
    public GameControls() {}
    
    /**
     * Creates a new button with the given label and applies the unified style properties
     * @param text the label text for the button
     * @return a styled {@link Button} instance
     */
    private Button createButton(String text) {
        Button btn = new Button(text);

        String style = BASE_PROPERTIES +
                        "-fx-text-fill: " + TEXT_COLOR + ";" +
                        "-fx-background-color: " + BG_COLOR + ";" +
                        "-fx-border-color: " + BORDER_COLOR + ";";
        btn.setStyle(style);
                
        return btn;
    }

    /**
     * Returns the pause button
     * @return the {@link Button} used to pause the game
     */
    public Button getPauseButton() {
        return pauseBtn;
    }

    /**
     * Returns the restart button
     * @return the {@link Button} used to restart the game
     */
    public Button getRestartButton() {
        return restartBtn;
    }

    /**
     * Returns the exit button
     * @return the {@link Button} used to exit the game
     */
    public Button getExitButton() {
        return exitBtn;
    }

    /**
     * Returns the resume button
     * @return the {@link Button} used to resume the game
     */
    public Button getResumeButton() {
        return resumeBtn;
    }
}
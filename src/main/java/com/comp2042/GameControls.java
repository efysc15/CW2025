package com.comp2042;

import javafx.scene.control.Button;

public class GameControls {

    // Define the unified neon color (Green)
    private static final String BORDER_COLOR = "lime"; // Spring Green
    private static final String TEXT_COLOR = "white";
    private static final String BG_COLOR = "black";

    // Define the common base style properties (No dropshadow/effect, clean look)
    private static final String BASE_PROPERTIES = 
        "-fx-font-size: 14px;" +
        "-fx-font-weight: bold;" +
        "-fx-background-radius: 8;" +
        "-fx-border-radius: 8;" +
        "-fx-border-width: 2px;" +
        "-fx-padding: 8px 12px;" +
        "-fx-min-width: 120px;" +
        "-fx-alignment: center;";

    private final Button pauseBtn = createButton ("PAUSE");
    private final Button resumeBtn = createButton ("RESUME");
    private final Button exitBtn = createButton ("EXIT");

    /**
     * Helper method to create and apply style to a button
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

    public Button getPauseButton() {
        return pauseBtn;
    }

    public Button getResumeButton() {
        return resumeBtn;
    }

    public Button getExitButton() {
        return exitBtn;
    }
}
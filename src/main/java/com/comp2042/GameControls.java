package com.comp2042;

import javafx.scene.control.Button;

public class GameControls {
    
    private final Button pauseBtn = new Button ("Pause");
    private final Button resumeBtn = new Button ("Resume");
    private final Button exitBtn = new Button ("Exit");

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

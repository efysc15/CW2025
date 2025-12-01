package com.comp2042;

public enum GameMode {
    
    CLASSIC ("Classic Mode - Endless play"),
    TWO_MINUTES ("2 Minutes Mode - Survive as long as possible within 120 seconds");

    private final String description;

    GameMode(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}

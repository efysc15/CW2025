package com.comp2042;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Timer {
    private final IntegerProperty seconds = new SimpleIntegerProperty(0);

    public IntegerProperty secondsProperty() {
        return seconds;
    }

    public void start (int startSeconds) {
        seconds.set(startSeconds);
    }

    public void tick() {
        if (seconds.get() > 0) {
            seconds.set(seconds.get() - 1);
        }
    }

    public void reset() {
        seconds.set(0);
    }
}

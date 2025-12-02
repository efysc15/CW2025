package com.comp2042;

import javafx.application.Platform;

public class FxToolkitInitializer {
    private static boolean initialized = false;

    public static void initToolkit() {
        if (!initialized) {
            Platform.startup(() -> {});
            initialized = true;
        }
    }
}

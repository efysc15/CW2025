package com.comp2042;

import javafx.application.Platform;

/**
 * Utility class responsible for initializing the JavaFX toolkit
 * Ensures that the JavaFX runtime is started only once during the application's lifecycyle
 */
public class FxToolkitInitializer {
    /** Flag indicating whether the JavaFX toolkit has already been initializied */
    private static boolean initialized = false;

    /**
     * Private constructor to prevent instantiation of this utility class
     */
    private FxToolkitInitializer() {}
    
    /**
     * Initializes the JavaFX toolkit if is has not already been started
     * <p>
     * This method calls {@link Platform#startup(Runnable)} to start the JavaFX runtime environment
     * It is safe to call multiple times, but the toolkit will be only be initialized once
     * 
     */
    public static void initToolkit() {
        if (!initialized) {
            Platform.startup(() -> {});
            initialized = true;
        }
    }
}

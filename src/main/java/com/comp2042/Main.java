package com.comp2042;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Entry point for the TetrisJFX application
 * <p>
 * The {@code Main} class initializes the JavaFX application by displaying the menu scene.
 * From the menu, the player can choose a game mode (e.g., Classic or Two Minutes), which triggers the loading of the game layout and start of the {@link GameController}.
 * 
 */
public class Main extends Application {
    /** Tracks the current score (passed to the menu for display) */
    private int currentScore = 0;
    /** The main menu scene, used for navigation back to the menu */
    private Scene menuScene;
    
    /** 
     * Creates a new {@code Main} instance
     * <p>
     * This constructor is required by the JavaFX runtime when launching the application.
     */
    public Main() {}

    /**
     * Starts the JavaFX application
     * <p>
     * Loads the menu scene and sets up callbacks for starting different game modes.
     * The primary is initialized with the menu scene and displayed to the user.
     * 
     * @param primaryStage the main application window
     */
    @Override
    public void start(Stage primaryStage) {

        // Load and show the menu first
        menuScene = Menu.getMenuScene(
            () -> {
                try {
                    startGame(primaryStage, GameMode.CLASSIC);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            },
            () -> {
                try {
                    startGame(primaryStage, GameMode.TWO_MINUTES);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            },
            currentScore
        );
        primaryStage.setScene(menuScene);
        primaryStage.setTitle("TetrisJFX");
        primaryStage.show();
    }

    /**
     * Starts a new game with a specified mode
     * <p>
     * Loads the {@code gameLayout.fxml}, initializes the {@link GuiController}, and creates a new {@link GameController} to manage gameplay.
     * The menu scene is passed to the controller for navigation back to the menu
     * 
     * @param stage the stage to display the game scene
     * @param mode the {@link GameMode} to start (e.g., Classic, Two Minutes)
     * @throws Exception Exception if the FXML layout cannot be loaded
     */
    private void startGame(Stage stage, GameMode mode) throws Exception {
        URL location = getClass().getClassLoader().getResource("gameLayout.fxml");
        ResourceBundle resources = null;
        FXMLLoader fxmlLoader = new FXMLLoader(location, resources);
        Parent root = fxmlLoader.load();
        GuiController c = fxmlLoader.getController();

        c.setMenuScene(menuScene);

        // Create the game scene
        Scene scene = new Scene(root, 1100, 650);
        stage.setScene(scene);
        // Start the GameController
        new GameController(c, mode);
    }
    
    /**
     * Launches the JavaFX application
     * @param args command-line arguments 
     */
    public static void main(String[] args) {
        launch(args);
    }
}

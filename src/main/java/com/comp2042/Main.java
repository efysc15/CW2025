package com.comp2042;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    private int currentScore = 0;

    @Override
    public void start(Stage primaryStage) {

        // Load and show the menu first
        Scene menuScene = Menu.getMenuScene(
            () -> {
                try {
                    startGame(primaryStage);
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

    // Method to start the actual game
    private void startGame(Stage stage) throws Exception {
        URL location = getClass().getClassLoader().getResource("gameLayout.fxml");
        ResourceBundle resources = null;
        FXMLLoader fxmlLoader = new FXMLLoader(location, resources);
        Parent root = fxmlLoader.load();
        GuiController c = fxmlLoader.getController();

        // Create the game scene
        Scene scene = new Scene(root, 900, 600);
        stage.setScene(scene);
        // Start the GameController
        new GameController(c);
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}

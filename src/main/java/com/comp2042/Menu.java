package com.comp2042;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Menu {
    private static HelpOverlay overlay;

    public static Scene getMenuScene(Runnable startClassic, Runnable startTwoMinutes, int currentScore) {
        // Game Title
        Label title = new Label("TETRIS");
        title.setFont(new Font("Georgia", 80));
        title.setTextFill(Color.WHITE);
        title.setStyle("-fx-effect: dropShadow(gaussian, white, 20, 0.5, 0, 0); -fx-font-style: italic;");

        double buttonWidth = 250;

        // Start Classic Mode Button
        Button classicBtn = new Button("CLASSIC");
        classicBtn.setFont(new Font("Arial Black", 20));
        classicBtn.setStyle(
            "-fx-padding: 10 35;" +
            "-fx-text-fill: black;" +
            "-fx-background-color: lime;" +
            "-fx-background-radius: 10;" 
        );
        classicBtn.setPrefWidth(buttonWidth);
        classicBtn.setOnAction(e -> startClassic.run());

        // Start 2 Minutes Mode Button
        Button timedBtn = new Button("2 MINUTES");
        timedBtn.setFont(new Font("Arial Black", 20));
        timedBtn.setStyle(
            "-fx-padding: 10 35;" +
            "-fx-text-fill: black;" +
            "-fx-background-color: cyan;" +
            "-fx-background-radius: 10;" 
        );
        timedBtn.setPrefWidth(buttonWidth);
        timedBtn.setOnAction(e -> startTwoMinutes.run());

        // Exit Button
        Button exitBtn = new Button("EXIT");
        exitBtn.setFont(new Font("Arial", 20));
        exitBtn.setStyle(
            "-fx-padding: 10 25;" +
            "-fx-text-fill: white;" +
            "-fx-background-color: transparent;" +
            "-fx-background-radius: 10;" 
        );
        exitBtn.setPrefWidth(buttonWidth);
        exitBtn.setOnAction(e -> {
            Stage stage = (Stage) exitBtn.getScene().getWindow();
            stage.close();
        });

        // Center layout
        VBox topBtns = new VBox (20, title, classicBtn, timedBtn);
        topBtns.setAlignment(Pos.CENTER);

        VBox bottomBtn = new VBox (20, exitBtn);
        bottomBtn.setAlignment(Pos.CENTER);

        VBox centerBox = new VBox(40, topBtns, bottomBtn);
        centerBox.setAlignment(Pos.CENTER);

        // Question mark button (top-right)
        Button helpBtn = new Button("?");
        helpBtn.setFont(new Font("Arial Black", 20));
        helpBtn.setStyle(
            "-fx-text-fill: cyan;" +
            "-fx-background-color: transparent;" +
            "-fx-border-color: lime;" +
            "-fx-border-width: 2;" +
            "-fx-background-radius: 50%;" +
            "-fx-border-radius: 50%;" +
            "-fx-padding: 5 10;"
        );

        helpBtn.setOnAction(e -> {
            Scene currentScene = helpBtn.getScene();
            overlay = new HelpOverlay(currentScene, () -> {
                ((StackPane) currentScene.getRoot()).getChildren().remove(overlay);
                overlay = null;
            });
            ((StackPane) currentScene.getRoot()).getChildren().add(overlay);
        });

        // Root layout
        StackPane root = new StackPane();
        BorderPane menuPane = new BorderPane();
        menuPane.setCenter(centerBox);
        menuPane.setTop(helpBtn);
        BorderPane.setAlignment(helpBtn, Pos.TOP_RIGHT);

        root.getChildren().add(menuPane);
        root.setStyle("-fx-background-color: black; -fx-padding: 40;");

        return new Scene(root, 1100, 650);
    }
}




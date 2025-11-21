package com.comp2042;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Menu {
    
    public static Scene getMenuScene(Runnable startGame, int currentScore) {
        // Game Title
        Label title = new Label ("TETRIS");
        title.setFont(new Font("Arial Black", 48));
        title.setTextFill(Color.WHITE);
        title.setStyle("-fx-effect: dropShadow(gaussian, cyan, 20, 0,5, 0, 0);");

        // Drawer Content (instructions + scoring)

        // Controls Drawer
        VBox controlsBox = new VBox (5);
        controlsBox.getChildren().addAll(
            new Label (" - Left Arrow OR Key A: Move left"),
            new Label (" - Right Arrow OR Key D: Move right"), 
            new Label (" - Up Arrow or Key W: Rotate"),
            new Label (" - Down Arrow or Key S : Soft Drop"),
            new Label (" - Key SPACE or Key ENTER : Hard Drop"),
            new Label (" - Key SHIFT : Hold brick")
        );

        TitledPane controlsDrawer = new TitledPane("Controls", controlsBox);
        controlsDrawer.setExpanded(false);  // Keep closed initially

        // Scoring Drawer
        VBox scoringBox = new VBox (5);
        scoringBox.getChildren().addAll(
            new Label (" - Single Line Clear: 50 pts"),
            new Label (" - Double Line Clear: 120 pts"),
            new Label (" - Triple Line Clear: 360 pts"),
            new Label (" - Tetris Line Clear (4 lines with a I - shape): 1500 pts")
        );

        TitledPane scoringDrawer = new TitledPane("Scoring", scoringBox);
        scoringDrawer.setExpanded(false);

        VBox drawerContent = new VBox(12, controlsDrawer, scoringDrawer);
        drawerContent.setStyle(
            "-fx-padding: 18;" +
            "-fx-background-color: rgba(255, 255, 255, 0.08);" +
            "-fx-border-color: cyan;" +
            "-fx-border-width: 2;" +
            "-fx-background-radius: 12;" +
            "-fx-border-radius: 12;"
        );
        drawerContent.setVisible(false);    // hidden until menu button clicked

        // Titled Pane acts like a drawer
        TitledPane gameMenuDrawer = new TitledPane("GAME MENU", drawerContent);
        gameMenuDrawer.setExpanded(false);  // collapsed by default
        gameMenuDrawer.setStyle("-fx-font-size:18px");

        // Game Menu Button
        Button menuBtn = new Button ("GAME MENU");
        menuBtn.setFont(new Font("Arial", 20));
        menuBtn.setStyle(
            "-fx-paddind: 10 25;" + 
            "-fx-text-fill: white;" +
            "-fx-background-color: transparent;" +
            "-fx-border-color: cyan;" +
            "-fx-border-width: 2;" +
            "-fx-background-radius: 10;" +
            "-fx-border-radius: 10;"
        );

        menuBtn.setOnAction(e -> {
            drawerContent.setVisible(!drawerContent.isVisible());
        });

        // Start Game Button
        Button startButton = new Button("START GAME");
        startButton.setFont(new Font("Arial", 20));
        startButton.setStyle(
            "-fx-paddind: 10 35;" + 
            "-fx-text-fill: white;" +
            "-fx-background-color: transparent;" +
            "-fx-border-color: lime;" +
            "-fx-border-width: 2;" +
            "-fx-background-radius: 10;" +
            "-fx-border-radius: 10;"
        );
        startButton.setOnAction(e -> startGame.run());

        // Layout
        VBox root = new VBox (15);
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(title, startButton, menuBtn, drawerContent);
        root.setStyle("-fx-background-color: black; -fx-padding: 40;");

        return new Scene (root, 1100, 650);
    }
}

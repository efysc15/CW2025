package com.comp2042;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Glow;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/**
 * A panel that displays animated score notification.
 * <p>
 * The {@code NotificationPanel} is used to show temporary messages (such as bonus points) with visual effects.
 * It fades out and translates upward before being removed from the scene graph.
 * 
 * <p>
 * The panel is styled with a glowing label and white text, and automatically cleans itself up after the animation finishes.
 * 
 */
public class NotificationPanel extends BorderPane {

    /**
     * Constructs a new {@code NotificationPanel} with the given text
     * <p>
     * The text is displayed in a centered label with a glow effect and styled using {@code bonusStyle} CSS class.
     * 
     * @param text the message or score string to display
     */
    public NotificationPanel(String text) {
        setMinHeight(200);
        setMinWidth(220);
        final Label score = new Label(text);
        score.getStyleClass().add("bonusStyle");
        final Effect glow = new Glow(0.6);
        score.setEffect(glow);
        score.setTextFill(Color.WHITE);
        setCenter(score);

    }

    /**
     * Plays the score notification animation and removes the panel from the given node list once finished.
     * <p>
     * The animation consists of:
     * <ul>
     *  <li>A fade transition (opacity from 1 -> 0 over 2000ms)</li>
     *  <li>A translate transition (moving upward by 40px over 2500ms)</li>
     * </ul>
     * Both transitions run in parallel, and the panel is removed from the provided list when the animation completes.
     * 
     * @param list the {@link ObservableList} of nodes from which this panel should be removed after the animation
     */
    public void showScore(ObservableList<Node> list) {
        FadeTransition ft = new FadeTransition(Duration.millis(2000), this);
        TranslateTransition tt = new TranslateTransition(Duration.millis(2500), this);
        tt.setToY(this.getLayoutY() - 40);
        ft.setFromValue(1);
        ft.setToValue(0);
        ParallelTransition transition = new ParallelTransition(tt, ft);
        transition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                list.remove(NotificationPanel.this);
            }
        });
        transition.play();
    }
}

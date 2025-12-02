import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.comp2042.FxToolkitInitializer;
import com.comp2042.GuiController;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class GuiControllerTest {

    @BeforeAll
    static void initToolkit() {
        FxToolkitInitializer.initToolkit();
    }

    private GuiController createDummyGuiController() {
        GuiController gui = new GuiController();

        // Inject dummy FXML fields
        gui.setGamePanel(new GridPane());
        gui.setBrickPanel(new GridPane());
        gui.setHoldBrickPanel(new GridPane());
        gui.setScoreLabel(new Label("0"));
        gui.setTimerLabel(new Label("00:00"));
        gui.setGroupNotification(new StackPane());
        gui.setPauseOverlay(new StackPane());
        gui.setButtonBar(new VBox());
        gui.setResumeContainer(new VBox());

        return gui;
    }

    @Test
    void testBindScoreUpdatesLabel() {
        GuiController gui = createDummyGuiController();
        javafx.beans.property.SimpleIntegerProperty score = new javafx.beans.property.SimpleIntegerProperty(123);

        gui.bindScore(score);
        assertEquals("123", gui.getScoreLabel().getText(), "Score label should reflect bound property");
    }

    @Test
    void testBindTimerUpdatesLabel() {
        GuiController gui = createDummyGuiController();
        javafx.beans.property.SimpleIntegerProperty seconds = new javafx.beans.property.SimpleIntegerProperty(65);

        gui.bindTimer(seconds);
        // 65 seconds = 01:05
        assertEquals("01:05", gui.getTimerLabel().getText(), "Timer label should format seconds into mm:ss");
    }

    @Test
    void testResetTimerSetsZero() {
        GuiController gui = createDummyGuiController();
        gui.startCountdown(5);
        gui.resetTimer();
        assertEquals(0, gui.getRemainingSecondsProperty().get(), "Reset timer should set remaining seconds to 0");
    }

    @Test
    void testSetAndGetMenuScene() {
        GuiController gui = createDummyGuiController();
        assertNull(gui.getMenuScene(), "Menu scene should be null initially");
        gui.setMenuScene(null);
        assertNull(gui.getMenuScene(), "Menu scene should remain null when set to null");
    }
}

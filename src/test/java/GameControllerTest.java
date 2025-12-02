import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.comp2042.EventSource;
import com.comp2042.EventType;
import com.comp2042.GameController;
import com.comp2042.GameMode;
import com.comp2042.GuiController;
import com.comp2042.MoveEvent;
import com.comp2042.ViewData;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class GameControllerTest {

    @BeforeAll
    static void initToolkit() {
        // This ensures JavaFX runtime is initialized for controls like Label, GridPane
        Platform.startup(() -> {});
    }

    private GuiController createDummyGuiController() {
        GuiController gui = new GuiController();
        gui.setHoldBrickPanel(new GridPane());
        gui.setGamePanel(new GridPane());
        gui.setBrickPanel(new GridPane());
        gui.setScoreLabel(new Label("0"));
        gui.setTimerLabel(new Label("00:00"));
        return gui;
    }

    @Test
    void testTimerIsAvailable() {
        GuiController gui = createDummyGuiController();
        GameController controller = new GameController(gui, GameMode.TWO_MINUTES);

        assertNotNull(controller.getTimer(), "Timer should be initialized");
    }

    @Test
    void testCreateNewGameResetsBoard() {
        GuiController gui = createDummyGuiController();
        GameController controller = new GameController(gui, GameMode.TWO_MINUTES);

        int[][] before = controller.getBoardMatrix();
        controller.createNewGame();
        int[][] after = controller.getBoardMatrix();

        assertNotNull(after, "Board matrix should not be null after new game");
        assertEquals(before.length, after.length,
            "Board matrix should keep same dimensions after reset");
    }

    @Test
    void testOnLeftEventReturnsViewData() {
        GuiController gui = createDummyGuiController();
        GameController controller = new GameController(gui, GameMode.TWO_MINUTES);

        ViewData viewData = controller.onLeftEvent(new MoveEvent(EventType.LEFT, EventSource.USER));
        assertNotNull(viewData, "onLeftEvent should return a ViewData object");
    }

    @Test
    void testOnRightEventReturnsViewData() {
        GuiController gui = createDummyGuiController();
        GameController controller = new GameController(gui, GameMode.TWO_MINUTES);

        ViewData viewData = controller.onRightEvent(new MoveEvent(EventType.RIGHT, EventSource.USER));
        assertNotNull(viewData, "onRightEvent should return a ViewData object");
    }

    @Test
    void testOnRotateEventReturnsViewData() {
        GuiController gui = createDummyGuiController();
        GameController controller = new GameController(gui, GameMode.TWO_MINUTES);

        ViewData viewData = controller.onRotateEvent(new MoveEvent(EventType.ROTATE, EventSource.USER));
        assertNotNull(viewData, "onRotateEvent should return a ViewData object");
    }
}

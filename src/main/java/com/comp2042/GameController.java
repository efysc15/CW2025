package com.comp2042;

public class GameController implements InputEventListener {

    private Board board = new SimpleBoard(25, 10);

    private final GuiController viewGuiController;

    public GameController(GuiController c) {
        viewGuiController = c;

        // Set up next brick display (if the board is SimpleBoard)
        if (board instanceof SimpleBoard simpleBoard) {
            simpleBoard.setNextBrickConsumer(viewGuiController::showNextBrick); // Show next brick in GUI
            viewGuiController.showNextBrick(simpleBoard.getNextBrick());    // Display the next brick preview
        }

        board.createNewBrick();
        viewGuiController.setEventListener(this);
        viewGuiController.initGameView(board.getBoardMatrix(), board.getViewData());
        viewGuiController.bindScore(board.getScore().scoreProperty());
    }

    @Override
    public DownData onDownEvent(MoveEvent event) {
        boolean canMove = board.moveBrickDown();
        ClearRow clearRow = null;
        boolean landed = false;
        if (!canMove) {
            board.mergeBrickToBackground();
            clearRow = board.clearRows();
            landed = true;
            if (clearRow.getLinesRemoved() > 0) {
                int lines = clearRow.getLinesRemoved();
                int points = switch (lines) {
                    case 1 -> 50;   // Single Line Clear + 50
                    case 2 -> 120;  // Doublie Line Clear + 120
                    case 3 -> 360;  // Triple Line Clear + 360
                    case 4 -> 1500; // Tetris Line Clear (4 lines with a I-shape) + 1500
                    default -> 0;
                };
                board.getScore().add(points);
            }
            if (board.createNewBrick()) {
                viewGuiController.gameOver();
            }

            viewGuiController.refreshGameBackground(board.getBoardMatrix());
        } 
        return new DownData(clearRow, board.getViewData(), landed);
    }

    @Override
    public ViewData onLeftEvent(MoveEvent event) {
        board.moveBrickLeft();
        return board.getViewData();
    }

    @Override
    public ViewData onRightEvent(MoveEvent event) {
        board.moveBrickRight();
        return board.getViewData();
    }

    @Override
    public ViewData onRotateEvent(MoveEvent event) {
        board.rotateLeftBrick();
        return board.getViewData();
    }


    @Override
    public void createNewGame() {
        board.newGame();
        viewGuiController.refreshGameBackground(board.getBoardMatrix());
    }
}

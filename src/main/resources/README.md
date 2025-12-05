# Tetris Game - Coursework README

## GitHub Repository
[GitHub Link](https://github.com/efysc15/CW2025.git)

---

## Compilation Instructions

This guide provides the necessary steps to clone, compile, and run the application.

### Prerequisites
* **Java Development Kit (JDK):** Version 17 or higher is recommended.
* **Build Tool:** This project assumes standard Java compilation or use of a simple IDE (like VS Code, Intellij).
* **Apache Maven:** Ensure Maven is installed and configured on your system (version 3.6+)

### Step-by-Step Guide
1. **Clone the Repository:**
    ```bash 
    git clone [PLACEHOLDER: https://github.com/efysc15/CW2025.git]
    cd [CW2025.git]
    ```
2. **Make the Wrapper Executable:** Grant execution permissions to the Maven Wrapper Script:
    ```bash
    chmod+x mvnw
    ```
3. **Clean and Run the Application:** Execure the appplication directly using the Maven Wrapper's javafx:run goal. This command will automaticall compile code and launch the JavaFX application.
    ```bash
    ./mvnw clean javafx.run
    ```
4. **The game window should launch automatically.**

--- 

## Implemented and Working Properly
The following feature were successcully implemented and are fully functional, as documented by the commit history:

| Feature | Description | Reference (from commits) |
| :--- | :--- | :--- |
| **Core Game Logic** | The fundamental game loop, piece movement (left/right/down), rotation, collision detection, and line clearing are all fully operational. | (Inferred / Initial Code) |
| **Scoring System** | A dedicated system to calculate and display the score upon line clears. | *add scoring system for line clears* |
| **Hard Drop** | Allows the player to instantly drop the current place to the landing position using a single key press (**Spacebar or ENTER**). | *add hard drop feature...* |
| **Hold/Swap Brick** | Implements a mechanism to hold one piece in reserve, allowing the player to swap it with the currently falling piece. | *add hold brick feature...* |
| **Next Brick Preview** | Displays a preview of the next one or more incoming pieces, aiding player strategy. | *add next brick preview box* / *feature/next-four-bricks* branch |
| **Ghost Brick Feature** | A transparent or outlined projection of where the current piece will land, displayed at the bottom of the column. | *add ghost brick preview feature* |
| **Pause/Resume** | Functionality to pause the game, displaying an overlay, and resuming play with button input. | *implements pause/exit controls and overlay* |
| **Game Over Panel** | A dedicated UI screen displayed upon game completion, offering options to **Start New Game** or **Exit**. | *Ensure New Game and Exit button on Game Over Panel* |
| **Timer Display** | Countdown timer for 2-minute mode, visible during gameplay. | *feature/gamemode-and-overlay-fix* |
| **Help Menu Overlay** | Compact, centered panel with cyan border and section for Controls, Rules, Scoring. | *menu-help-overlay-update* |
| **Menu Page** | Newly added main maenu with bold buttons (Classic, 2 Minutes, Exit), consistent widths, and HelpOverlay integration. |  *added menu and improved its design / menu-help-overlay-update* |
| **Game Controls Button (Pause, Restart, Exit)** | In-game buttons for Pause, Restart and Exit, accessible during gameplay. | *Implement Pause/Resume Toggle and Refactor Restart Button* |
| **Game Modes** | Classic endless mode and 2-Minute timed mode implemented. | *feature/gamemode-and-overlay-fix* |


---

## Implemented but Not Working Properly

At this stage, there are **no feature** that were implemented but are not working properly.
All implemented features are funcioning as expected. 
---

## Features Not Implemented
* **Feature:** Level progression system
    * **Reason Not Implemented:** Time constraints led me to prioritize core mechanics (Hold/Ghost) over the progressive difficulty implementation.
* **Feature:** Sound Effects and Music
    * **Reason Not Implemented:** Focus was kept entirely on game logic and UI display; audio integration was defered. 
* **Feature:** T-Spin Detection and Scoring
    * **Reason Not Implemented:** Advanced scoring mechanics like T-spins were not implemented due to complexity and time constraints. Focus was placed on core line-clear scoring and UI features instead. 
--- 

## New Java Classes 
The following new classes were introduced to implement the required features and maintain separation of concerns:

| Class Name | Purpose |
| :--- | :--- |
| `Menu` | Creates the main menu UI and drawers |
| `ScoreManager` | Manages the score state, provides methods for line clear scoring , and notifies the UI for display updates
| `GameControls` | Implemented side buttons (Pause, Restart, Exit) for in-game control. |
| `GameOverButtons` | Provides styled buttons for te Game Over Panel (New Game, Exit)|
| `GhostBrick` | An object responsible for calculating and holding the coordinates of the current piece's shadow projection |
| `HoldBrick` | Handles the logic for storing and swapping the reserved piece (**Hold/Swap Brick** feature). |
| `GameMode` | Define Classic and Timed modes, managing rules and timers |
| `HelpOverlay` | Displays a centered overlay with sections for Controls, Rules, Scoring |
| `Timer` | Implements countdown timer for the 2-Minute mode |


## Modified Java Classes
The following classes from the provided starter code were modified:

| Class Name | Description of Changes | Reason for Modification | 
| :--- | :--- | :--- | 
| `Main` | Integrated new UI elements (Menu, HelpOverlay) and updated point logic | To support lauching the game with new overlays and menu navigation |
| `GameController` | Added logic for new game modes, scoring updates, pause/resume handling, and next brick preview integration | To manage Classic vs Timed modes, expand gameplay features, and display upcoming bricks |
| `GuiController` | Extended to handle Hard Drop, Next Brick previews, Side Buttons (Pause/Restart/Exit), Ghost Brick rendering, Timer Countdown and more | To unify all new gameplay mechanics with UI, ensuring features are visible, responsive and accessible |
| `SimpleBoard` | Added support for the Next Brick preview, adjusted Game Over line position, and integrated Game Controls side buttons | To align board rendering with new gameplay features and ensure proper UI placement |
| `BrickRotator` | Implemented logic to support the Hold Brick feature, enabling correct swapping and orientation handling | To ensure hold bricks can be rotated and swapped seamlessly during gameplay |
| `InputEventListener` | Added new function to handle Hold event and implemented `getBoardMatrix` for Ghost Brick support | To capture player input for advanced mechanics and provide board state data for Ghost Brick rendering |
| `DownData` | Extended to handle Hard Drop scoring, alongside line clear tracking | To ensure Hard Drop contributes correctly to score calculations |
| `GameOverPanel` | Added overlay with buttons (New Game, Exit) for end-of-game interaction | To provide clear options for restarting or exiting whent the game ends |
| `Brick` | Updated to support Next Brick preview colors, ensuring each upcoming piece is displayed with its correct color | To visually distinguish upcoming bricks in the preview box and improve player strategy |
| `gameLayout` | Changed background and realigned all elements, including overlays, side buttons, and preview boxes | To improve oevrall visual clarity, ensure consistency alignment, and enhance user experience |
| `Brickgenerator` | Implemented Next Brick queue logic to generate and preview 4 upcoming bricks | To allow players to plan ahead by showing multiple upcoming pieces in the preview box |
| `randombrickgenerator` | Synced with `Brickgenerator` to randomize the Next Brick queue while supporting 4 - brick preview | To ensure fair distribution of pieces while maintaining accurate preview of upcoming bricks |
| `I, J, L, O, S, T, Z Brick` | Synced with `Brick.java` to ensure Next Brick preview colors display correctly for each specific piece type | To maintain consistency across all brick types in the preview box and gameplay |

## Unexpected Problems
This section details significant unexpected challenges encountered during development and how they were addressed. 

* **Problem 1: Ghost Brick not rendering**
    * **Description:** Initially the ghost brick did not appear correctly on the board. 
    * **Resolution:** Added `getBoardMatrix` in `InputEventListener`.
* **Problem 2: UI Overlapping in Game States** 
    * **Description:** When implementing the **Pause Overlay** and **Game Over Panel**, they often conflicted with the main game board rendering, leading to visual artifacts or inconsistent display. 
    * **Resolution:** Implemented a clean **Game State** (`PLAYING`, `PAUSED`, `GAMEOVER`) and used a `switch` statement in the main rendering function

## Author
Chia Sze Lum
20590531
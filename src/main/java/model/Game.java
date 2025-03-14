package model;

import lombok.Getter;
import service.MovingService;
import service.impl.MovingServiceImpl;

/**
 * The Game class is a singleton that manages the game state and services.
 * It provides a centralized access point for game-related functionality.
 */

@Getter
public class Game {
    // Singleton instance of the game
    private static Game game;
    // Service responsible for handling movement logic within the game
    private MovingService movingService;

    /**
     * Private constructor to prevent direct instantiation.
     * Ensures that the Game instance is managed as a singleton.
     */
    private Game() {
    }

    /**
     * Initializes the game instance and sets up necessary services.
     * This method should be called only once during the application's lifecycle.
     *
     * @return the initialized Game instance
     */

    public static Game init() {
        game = new Game();
        game.movingService = new MovingServiceImpl();
        return game;
    }

    /**
     * Retrieves the singleton instance of the Game.
     * If the instance is not yet created, it initializes it.
     *
     * @return the singleton Game instance
     */

    public static Game getInstance() {
        if (game == null) {
            init();
        }
        return game;
    }
}

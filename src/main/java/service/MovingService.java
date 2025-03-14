package service;

/**
 * The MovingService interface defines the operations related to scene movement
 * and choice selection in the game.
 */
public interface MovingService {

    /**
     * Retrieves the text description of a given scene.
     *
     * @param scene the scene number
     * @return the text description of the scene
     */
    String getSceneText(int scene);

    /**
     * Retrieves the text for a specific button (choice) in a given scene.
     *
     * @param scene  the scene number
     * @param button the button index (1, 2, or 3)
     * @return the text associated with the specified button
     */
    String getButtonText(int scene, int button);

    /**
     * Processes the player's move based on the chosen action in a given scene.
     *
     * @param scene  the current scene number
     * @param choose the player's chosen action
     * @return a message indicating the result of the move
     */
    String move(int scene, String choose);

    /**
     * Checks if the last move made by the player was incorrect.
     *
     * @return true if the last move was wrong, false otherwise
     */
    boolean isWrongMove();
}

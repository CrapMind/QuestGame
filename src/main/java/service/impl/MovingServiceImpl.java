package service.impl;

import lombok.extern.slf4j.Slf4j;
import repository.MovingRepository;
import service.MovingService;

/**
 * The MovingServiceImpl class implements the MovingService interface
 * and acts as a bridge between the game logic and the MovingRepository.
 */
@Slf4j
public class MovingServiceImpl implements MovingService {

    // Singleton instance of MovingRepository to handle movement logic
    MovingRepository movingRepository = MovingRepository.getInstance();

    /**
     * Processes the player's move by delegating to the MovingRepository.
     *
     * @param scene  the current scene number
     * @param choose the player's chosen action
     * @return a message indicating the result of the move
     */
    @Override
    public String move(int scene, String choose) {
        log.info("Processing move: scene={}, choice={}", scene, choose);
        return movingRepository.move(scene, choose);
    }

    /**
     * Retrieves the text description of a given scene.
     *
     * @param scene the scene number
     * @return the text description of the scene
     */
    @Override
    public String getSceneText(int scene) {
        String text = movingRepository.getSceneText(scene);
        log.debug("Fetched scene text: {}", text);
        return text;
    }

    /**
     * Retrieves the text for a specific button (choice) in a given scene.
     *
     * @param scene  the scene number
     * @param button the button index (1, 2, or 3)
     * @return the text associated with the specified button
     */
    @Override
    public String getButtonText(int scene, int button) {
        String buttonText = movingRepository.getButtonText(scene, button);
        log.debug("Fetched button text: scene={}, button={}, text={}", scene, button, buttonText);
        return buttonText;
    }

    /**
     * Checks if the last move made by the player was incorrect.
     *
     * @return true if the last move was wrong, false otherwise
     */
    @Override
    public boolean isWrongMove() {
        return movingRepository.isWrongMove();
    }
}

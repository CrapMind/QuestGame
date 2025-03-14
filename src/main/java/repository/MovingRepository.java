package repository;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import model.util.Choices;
import java.util.Map;

/**
 * The MovingRepository class stores the scenes, choices, and game logic for handling player moves.
 * It follows the singleton pattern to ensure a single instance manages game state.
 */
@Slf4j
public class MovingRepository {
    // Flag to indicate whether the last move was incorrect
    @Getter
    private boolean isWrongMove;
    // Mapping of scene numbers to their corresponding descriptions
    private final Map<Integer, String> SCENES = Map.of(
            1, "You come to, your head is buzzing, there is a dense jungle around you. There is a bag in front of you.",
            2, "In the bag you find: a knife, a flask of water and a piece of rope. What do you take?",
            3, "You hear a rustling sound in the bushes.",
            4, "A small monkey comes out of the bushes. It holds something in its paws.",
            5, "The monkey places a compass in front of you. You take it. There is a fork in the road ahead: dense thickets and a narrow path.",
            6, "You see a river with a fast current. Nearby there is a broken raft.",
            7, "You are floating down a river and you see a waterfall ahead.",
            8, "On the shore you find a map, but it is torn.",
            9, "You hear the noise of a motor - there is a boat nearby.",
            10, "A boat is approaching. There is a man in it with a radio."
    );
    // Mapping of scene numbers to possible choices
    private final Map<Integer, Choices> CHOICES_MAP = Map.of(
            1, new Choices("Look into the bag", "Get up and look for a way out"),
            2, new Choices("All items", "Only knife", "Nothing"),
            3, new Choices("Hide and watch", "Scream for help", "Run in the opposite direction"),
            4, new Choices("Extend your hand carefully", "Chase the monkey away", "Try to catch a monkey (use rope)"),
            5, new Choices("Follow the path", "Cut your way through the thicket (use knife)"),
            6, new Choices("Try to fix the raft (use rope)", "Swim across the river"),
            7, new Choices("Direct the raft to the shore", "Try jumping into the water in front of the waterfall", "Try to catch hold of a nearby tree (use a knife)"),
            8, new Choices("Use a compass and guess the way", "Go at random"),
            9, new Choices("Wave your clothes to attract attention", "Run straight to the boat"),
            10, new Choices("Say that you have lost your memory and ask for help", "Try to take the boat by force")
    );
    // Singleton instance of MovingRepository
    private static MovingRepository movingRepository = new MovingRepository();

    /**
     * Private constructor to enforce the singleton pattern.
     */
    private MovingRepository() {}

    /**
     * Retrieves the singleton instance of MovingRepository.
     *
     * @return the singleton instance
     */
    public static MovingRepository getInstance() {
        if (movingRepository == null) {
            movingRepository = new MovingRepository();
        }
        return movingRepository;
    }

    /**
     * Processes the player's move based on the scene and chosen option.
     *
     * @param scene  the current scene number
     * @param choice the player's selected choice
     * @return a message indicating the result of the move
     */
    public String move(int scene, String choice) {
        log.info("Processing move: scene={}, choice={}", scene, choice);
        Choices currentChoice = CHOICES_MAP.get(scene);

        if (currentChoice == null) {
            log.warn("Invalid scene number: {}", scene);
            return "Invalid scene number.";
        }

        if (choice.equals(currentChoice.getRightChoice())) {
            isWrongMove = false;
            log.info("Player made the correct choice in scene {}", scene);
            return "✅ Correct choice! Moving to the next scene.";
        } else {
            isWrongMove = true;
            log.warn("Player made the wrong choice: {} in scene {}", choice, scene);
            return getDefeatMessage(scene, choice, currentChoice);
        }
    }

    /**
     * Returns a defeat message based on the wrong choice the player made.
     *
     * @param scene         the scene number
     * @param choice        the incorrect choice
     * @param currentChoice the available choices in the scene
     * @return a defeat message indicating the outcome of the wrong move
     */

    private String getDefeatMessage(int scene, String choice, Choices currentChoice) {
        if (choice.equals(currentChoice.getWrongChoice())) {
            return "❌ Wrong choice! " + getFailureText(scene, 1);
        } else if (choice.equals(currentChoice.getAnotherWrongChoice())) {
            return "❌ Wrong choice! " + getFailureText(scene, 2);
        } else {
            return "❌ Invalid choice.";
        }
    }

    /**
     * Retrieves a failure text message based on the scene and failure option.
     *
     * @param scene  the scene number
     * @param option the incorrect choice option (1 or 2)
     * @return the corresponding failure message
     */

    private String getFailureText(int scene, int option) {
        return switch (scene) {
            case 1 -> "While you were looking for a way out, something attacked you from behind.";
            case 2 -> option == 1 ? "Not enough resources." : "Too risky.";
            case 3 -> option == 1 ? "You got into a swamp." : "You attracted a predator.";
            case 4 -> "The monkey ran away with an important item.";
            case 5 -> "You ran into snakes.";
            case 6 -> "The strong current carried you away.";
            case 7 -> option == 1 ? "You stumbled upon rocks." : "The knife got stuck in the tree and you flew out of the boat.";
            case 8 -> "You got lost.";
            case 9 -> "You were swept away by the current.";
            case 10 -> "You were stunned.";
            default -> "Unknown failure.";
        };
    }

    /**
     * Retrieves the scene text for a given scene number.
     *
     * @param scene the scene number
     * @return the corresponding scene text or a default message if not found
     */
    public String getSceneText(int scene) {
        String text = SCENES.getOrDefault(scene, "Scene not found.");
        log.debug("Fetching text for scene {}: {}", scene, text);
        return text;
    }

    /**
     * Retrieves the button text for a given scene and button index.
     *
     * @param scene  the scene number
     * @param button the button index (1, 2, or 3)
     * @return the corresponding button text or an invalid message if not found
     */
    public String getButtonText(int scene, int button) {
        Choices choices = CHOICES_MAP.get(scene);
        if (choices == null) return "Unknown";

        return switch (button) {
            case 1 -> choices.getRightChoice();
            case 2 -> choices.getWrongChoice();
            case 3 -> choices.getAnotherWrongChoice() != null ? choices.getAnotherWrongChoice() : "";
            default -> "Invalid button";
        };
    }
}

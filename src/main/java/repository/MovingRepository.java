package repository;

import model.util.Choices;
import java.util.Map;

public class MovingRepository {

    private boolean isWrongChoice;

    private final Map<Integer, String> FIRST_SCENE = Map.of(1, "You come to, your head is buzzing, there is a dense jungle around you. There is a bag in front of you.");
    private final Map<Integer, String> SECOND_SCENE = Map.of(2, "In the bag you find: a knife, a flask of water and a piece of rope. What do you take?");
    private final Map<Integer, String> THIRD_SCENE = Map.of(3, "You hear a rustling sound in the bushes.");
    private final Map<Integer, String> FOURTH_SCENE = Map.of(4, "A small monkey comes out of the bushes. It holds something in its paws.");
    private final Map<Integer, String> FIVE_SCENE = Map.of(5, "The monkey places a compass in front of you. You take it. There is a fork in the road ahead: dense thickets and a narrow path.");
    private final Map<Integer, String> SIX_SCENE = Map.of(6, "You see a river with a fast current. Nearby there is a broken raft.");
    private final Map<Integer, String> SEVEN_SCENE = Map.of(7, "You are floating down a river and you see a waterfall ahead.");
    private final Map<Integer, String> EIGHT_SCENE = Map.of(8, "On the shore you find a map, but it is torn.");
    private final Map<Integer, String> NINE_SCENE = Map.of(9, "You hear the noise of a motor - there is a boat nearby.");
    private final Map<Integer, String> TEN_SCENE = Map.of(10, "A boat is approaching. There is a man in it with a radio.");

    private final Map<Map<Integer, String>, Choices> MOVES = Map.of(
            FIRST_SCENE, new Choices("Look into the bag", "Get up and look for a way out"),
            SECOND_SCENE, new Choices("All items", "Only knife", "Nothing"),
            THIRD_SCENE, new Choices("Hide and watch", "Scream for help", "Run in the opposite direction"),
            FOURTH_SCENE, new Choices("Extend your hand carefully", "Chase the monkey away", "Try to catch a monkey(use rope)"),
            FIVE_SCENE, new Choices("Follow the path", "Cut your way through the thicket(use knife)"),
            SIX_SCENE, new Choices("Try to fix the raft (use rope)", "Swim across the river"),
            SEVEN_SCENE, new Choices("Direct the raft to the shore", "Try jumping into the water in front of the waterfall", "Try to catch hold of a nearby tree (use a knife)"),
            EIGHT_SCENE, new Choices("Use a compass and guess the way", "Go at random"),
            NINE_SCENE, new Choices("Wave your clothes to attract attention", "Run straight to the boat"),
            TEN_SCENE, new Choices("Say that you have lost your memory and ask for help", "Try to take the boat by force"));

    private static MovingRepository movingRepository = new MovingRepository();

    private MovingRepository() {}

    public static MovingRepository getInstance() {
        if (movingRepository == null) {
            movingRepository = new MovingRepository();
        }
        return movingRepository;
    }

    public String move(int scene, String choice) {
        Map<Integer, String> currentScene = getScene(scene);
        Choices currentChoice = MOVES.get(currentScene);

        if (currentChoice == null) {
            return "Invalid scene number.";
        }

        if (choice.equals(currentChoice.getRightChoice())) {
            isWrongChoice = false;
            return "✅ Correct choice! Moving to the next scene.";
        } else {
            isWrongChoice = true;
            return getDefeatMessage(scene, choice, currentChoice);
        }
    }


    private Map<Integer, String> getScene(int scene) {
        return switch (scene) {
            case 1 -> FIRST_SCENE;
            case 2 -> SECOND_SCENE;
            case 3 -> THIRD_SCENE;
            case 4 -> FOURTH_SCENE;
            case 5 -> FIVE_SCENE;
            case 6 -> SIX_SCENE;
            case 7 -> SEVEN_SCENE;
            case 8 -> EIGHT_SCENE;
            case 9 -> NINE_SCENE;
            case 10 -> TEN_SCENE;
            default -> null;
        };
    }


    private String getDefeatMessage(int scene, String choice, Choices currentChoice) {
        if (choice.equals(currentChoice.getWrongChoice())) {
            return "❌ Wrong choice! " + getFailureText(scene, 1);
        } else if (choice.equals(currentChoice.getAnotherWrongChoice())) {
            return "❌ Wrong choice! " + getFailureText(scene, 2);
        } else {
            return "❌ Invalid choice.";
        }
    }


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
    public String getSceneText(int scene) {
        return switch (scene) {
            case 1 -> FIRST_SCENE.get(1);
            case 2 -> SECOND_SCENE.get(2);
            case 3 -> THIRD_SCENE.get(3);
            case 4 -> FOURTH_SCENE.get(4);
            case 5 -> FIVE_SCENE.get(5);
            case 6 -> SIX_SCENE.get(6);
            case 7 -> SEVEN_SCENE.get(7);
            case 8 -> EIGHT_SCENE.get(8);
            case 9 -> NINE_SCENE.get(9);
            case 10 -> TEN_SCENE.get(10);
            default -> throw new IllegalStateException("Unexpected value: " + scene);
        };
    }

    public String getButtonText(int scene, int button) {
        String result = "";
        switch (button) {
            case 1 -> result = MOVES.get(getScene(scene)).getRightChoice();
            case 2 -> result = MOVES.get(getScene(scene)).getWrongChoice();
            case 3 -> {
                if (MOVES.get(getScene(scene)).getAnotherWrongChoice() != null) {
                    result = MOVES.get(getScene(scene)).getAnotherWrongChoice();
                }
            }
            default -> throw new IllegalStateException("Unexpected value: " + button);
        }
        return result;
    }

    public String isWrongChoice() {
        return isWrongChoice ? "wrong" : "right";
    }
}

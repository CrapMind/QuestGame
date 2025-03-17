package model.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The Choices class represents a set of possible choices in the game,
 * including one correct choice and up to two incorrect choices.
 */
@Getter
@AllArgsConstructor
public class Choices {

    // The correct choice
    private String rightChoice;
    // The first incorrect choice
    private String wrongChoice;
    // The second incorrect choice (optional)
    private String anotherWrongChoice;

    /**
     * Constructor for a scenario with only one correct and one incorrect choice.
     *
     * @param rightChoice  the correct choice
     * @param wrongChoice  the incorrect choice
     */
    public Choices(String rightChoice, String wrongChoice) {
        this.rightChoice = rightChoice;
        this.wrongChoice = wrongChoice;
    }
}

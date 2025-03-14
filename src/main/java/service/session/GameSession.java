package service.session;

import lombok.Getter;
import model.Player;

import java.io.Serializable;

/**
 * The GameSession class represents a player's session within the game.
 * It tracks the player's information and their correct and incorrect moves.
 */

@Getter
public class GameSession implements Serializable {
    // The player associated with this session
    private final Player player;
    // The number of correct moves made by the player
    private int correctMoves;
    // The number of incorrect moves made by the player
    private int wrongMoves;

    /**
     * Constructs a new game session for the given player.
     *
     * @param player the player associated with this session
     */

    public GameSession(Player player) {
        this.player = player;
        this.correctMoves = 0;
    }

    /**
     * Increments the count of correct moves.
     */

    public void incrementCorrectMoves() {
        this.correctMoves++;
    }

    /**
     * Increments the count of wrong moves.
     */

    public void incrementWrongMoves() {
        this.wrongMoves++;
    }
}

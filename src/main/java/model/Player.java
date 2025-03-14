package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * The Player class represents a player in the game with a unique ID and a name.
 */
@AllArgsConstructor
@Getter
public class Player {
    // Unique identifier for the player
    private Long id;
    // Name of the player, which can be updated
    @Setter
    private String name;
}

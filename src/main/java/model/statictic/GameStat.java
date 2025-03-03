package model.statictic;

import lombok.Getter;
import model.Player;

import java.util.Map;
import java.util.stream.Collectors;

@Getter
public class GameStat {
    Player player;
    Map<String, Boolean> moves;

    @Override
    public String toString() {
        return "Player: " + player.getName() + "\n Moves: " + moves.entrySet().stream()
                .filter(Map.Entry::getValue)
                .map(Map.Entry::getKey)
                .collect(Collectors.joining(" | "));
    }
}

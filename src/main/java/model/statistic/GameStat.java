package model.statistic;

import lombok.Getter;
import model.Player;

import java.util.Map;
import java.util.stream.Collectors;

@Getter
public class GameStat {
    private Player player;
    private Map<String, Boolean> moves;

    @Override
    public String toString() {
        return "Player: " + player.name() + "\n Moves: " + moves.entrySet().stream()
                .filter(Map.Entry::getValue)
                .map(Map.Entry::getKey)
                .collect(Collectors.joining(" | "));
    }
}

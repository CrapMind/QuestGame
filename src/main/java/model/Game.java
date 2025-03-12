package model;

import lombok.Getter;
import model.statictic.GameStat;
@Getter
public class Game {

    private static final Game GAME = new Game(new GameStat());
    private final GameStat GAME_STAT = new GameStat();
    private Game(GameStat statistic) {
    }

    public static Game getInstance() {
        return GAME;
    }
}

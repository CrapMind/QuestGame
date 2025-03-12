package model;

import lombok.Getter;
import service.MovingService;
import service.impl.MovingServiceImpl;

@Getter
public class Game {

    private static Game game;
    private MovingService movingService;

    private Game() {
    }

    public static Game init() {
        game = new Game();
        game.movingService = new MovingServiceImpl();
        return game;
    }

    public static Game getInstance() {
        if (game == null) {
            init();
        }
        return game;
    }
}

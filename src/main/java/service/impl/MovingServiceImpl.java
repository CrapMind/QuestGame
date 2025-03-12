package service.impl;

import model.Game;
import repository.MovesRepository;
import service.MovingService;

public class MovingServiceImpl implements MovingService {

    MovesRepository movesRepository = MovesRepository.getInstance();
    Game game = Game.getInstance();

    @Override
    public String move(int scene, String choose) {
        return movesRepository.move(scene, choose);
    }
}

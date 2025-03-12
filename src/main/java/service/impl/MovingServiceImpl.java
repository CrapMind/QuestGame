package service.impl;

import repository.MovingRepository;
import service.MovingService;

public class MovingServiceImpl implements MovingService {

    MovingRepository movingRepository = MovingRepository.getInstance();

    @Override
    public String move(int scene, String choose) {
        return movingRepository.move(scene, choose);
    }

    @Override
    public String getSceneText(int scene) {
        return movingRepository.getSceneText(scene);
    }
    @Override
    public String getButtonText(int scene, int button) {
        return movingRepository.getButtonText(scene, button);
    }
    @Override
    public String isWrongChoice() {
        return movingRepository.isWrongChoice();
    }
}

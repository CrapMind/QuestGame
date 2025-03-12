package service.impl;

import lombok.extern.slf4j.Slf4j;
import repository.MovingRepository;
import service.MovingService;
@Slf4j
public class MovingServiceImpl implements MovingService {

    MovingRepository movingRepository = MovingRepository.getInstance();

    @Override
    public String move(int scene, String choose) {
        log.info("Processing move: scene={}, choice={}", scene, choose);
        return movingRepository.move(scene, choose);
    }

    @Override
    public String getSceneText(int scene) {
        String text = movingRepository.getSceneText(scene);
        log.debug("Fetched scene text: {}", text);
        return text;
    }
    @Override
    public String getButtonText(int scene, int button) {
        String buttonText = movingRepository.getButtonText(scene, button);
        log.debug("Fetched button text: scene={}, button={}, text={}", scene, button, buttonText);
        return buttonText;
    }
    @Override
    public String isWrongChoice() {
        return movingRepository.isWrongChoice();
    }
}

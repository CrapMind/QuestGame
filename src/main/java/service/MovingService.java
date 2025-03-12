package service;

public interface MovingService {
    String getSceneText(int scene);
    String getButtonText(int scene, int button);
    String move(int scene, String choose);
    String isWrongChoice();
}

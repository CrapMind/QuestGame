package service.session;

import dto.PlayerDto;
import lombok.Getter;

import java.io.Serializable;
@Getter
public class GameSession implements Serializable {
    private PlayerDto player;
}

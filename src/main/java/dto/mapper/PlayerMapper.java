package dto.mapper;

import dto.PlayerDto;
import lombok.AllArgsConstructor;
import model.Player;
@AllArgsConstructor
public class PlayerMapper {

    public Player fromDto(PlayerDto playerDto) {
        return new Player(playerDto.getId(), playerDto.getName());
    }

    public PlayerDto toDto(Player player) {
        return new PlayerDto(player.id(), player.name());
    }
}

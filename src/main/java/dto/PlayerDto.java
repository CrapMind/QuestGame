package dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class PlayerDto {

    private final Long id;
    private final String name;
    private int correctMoves;
    private int retryAttempts;

}

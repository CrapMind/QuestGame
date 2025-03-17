package session;

import model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.session.GameSession;

import static org.junit.jupiter.api.Assertions.*;


public class GameSessionTest {
    private GameSession gameSession;

    @BeforeEach
    void setUp() {
        gameSession = new GameSession(new Player(1L, "TestPlayer"));
    }

    @Test
    void testGameSessionCorrectMoves() {
        gameSession.incrementCorrectMoves();
        assertEquals(1, gameSession.getCorrectMoves());
    }

    @Test
    void testGameSessionWrongMoves() {
        gameSession.incrementWrongMoves();
        assertEquals(1, gameSession.getWrongMoves());
    }
}

package controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import model.Player;
import service.session.GameSession;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;
/**
 * The SetPlayerNameServlet class handles HTTP POST requests to assign a player name,
 * create a new player session, and store it in the HTTP session.
 */
@Slf4j
@WebServlet("/rest/setPlayerName")
public class SetPlayerNameServlet extends HttpServlet {

    // Atomic counter for generating unique player IDs
    private static final AtomicLong playerIdGenerator = new AtomicLong(1);

    /**
     * Handles POST requests to assign a player name and create a new game session.
     *
     * @param request  the HTTP request containing the player name
     * @param response the HTTP response indicating success or failure
     * @throws IOException if an input or output error occurs while handling the request
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // Retrieve the "playerName" parameter from the request
        String playerName = request.getParameter("playerName");

        // Validate the player name input
        if (playerName == null || playerName.trim().isEmpty()) {
            log.warn("Received empty or null playerName from IP={}", request.getRemoteAddr());
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Player name cannot be empty");
            return;
        }
        // Retrieve or create the HTTP session
        HttpSession session = request.getSession();
        if (session == null) {
            log.warn("Session is null for request from IP={}", request.getRemoteAddr());
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Session is required");
            return;
        }

        // Create a new Player instance with a unique ID
        Player player = new Player(playerIdGenerator.getAndIncrement(), playerName);

        // Create a new GameSession associated with the player
        GameSession gameSession = new GameSession(player);

        // Store the GameSession in the HTTP session
        session.setAttribute("gameSession", gameSession);
        log.info("Game session created for player '{}' with ID={} (IP={})", playerName, player.getId(), request.getRemoteAddr());

        // Respond with HTTP 200 OK status
        response.setStatus(HttpServletResponse.SC_OK);
    }
}

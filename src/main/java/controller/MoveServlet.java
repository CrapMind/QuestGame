package controller;

import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import model.Game;
import service.MovingService;
import service.session.GameSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
/**
 * The MoveServlet class handles HTTP GET requests for processing a player's move
 * within the game based on the provided scene and choice parameters.
 */
@Slf4j
@WebServlet(value = "/rest/move")
public class MoveServlet extends HttpServlet {

    /**
     * Handles GET requests to process a player's move by updating the game state
     * and returning the new scene and move statistics in JSON format.
     *
     * @param request  the HTTP request containing scene and choice parameters
     * @param response the HTTP response containing the updated game state
     * @throws IOException if an input or output error occurs while handling the request
     */

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // Retrieve the session associated with the request
        HttpSession session = request.getSession();

        // Check if the session exists
        if (session == null) {
            log.warn("Session is null for request from IP={}", request.getRemoteAddr());
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Session is required");
            return;
        }

        // Retrieve the GameSession object from the session
        GameSession gameSession = (GameSession) session.getAttribute("gameSession");

        // Check if a game session exists for the current user
        if (gameSession == null) {
            log.warn("No game session found for request from IP={}", request.getRemoteAddr());
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Game session not found");
            return;
        }

        // Retrieve the scene and choice parameters from the request
        String sceneParam = request.getParameter("scene");
        String choiceParam = request.getParameter("choice");
        int scene;

        log.info("Received move request for scene={} with choice={} from IP={}", sceneParam, choiceParam, request.getRemoteAddr());

        // Validate scene and choice parameters
        if (sceneParam == null || choiceParam == null || sceneParam.isEmpty() || choiceParam.isEmpty()) {
            log.warn("Invalid parameters in request: scene={}, choice={}", sceneParam, choiceParam);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid parameters");
            return;
        }
        try {
            // Convert the scene parameter to an integer
            scene = Integer.parseInt(sceneParam);

        } catch (NumberFormatException e) {
            log.error("Invalid scene parameter received: {}", sceneParam, e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid parameters");
            return;
        }

        // Retrieve the MovingService instance from the Game singleton
        MovingService movingService = Game.getInstance().getMovingService();

        // Process the move and determine the new scene
        String newScene = movingService.move(scene, choiceParam);

        boolean isWrongMove = movingService.isWrongMove();

        // Update the game session based on the move outcome
        if (isWrongMove) {
            gameSession.incrementWrongMoves();
        } else gameSession.incrementCorrectMoves();

        // Prepare the JSON response
        Map<String, Object> jsonResponse = new HashMap<>();
        jsonResponse.put("scene", newScene);
        jsonResponse.put("moves", isWrongMove);
        jsonResponse.put("correctMoves", gameSession.getCorrectMoves());
        jsonResponse.put("wrongMoves", gameSession.getWrongMoves());
        jsonResponse.put("playerName", gameSession.getPlayer().getName());

        log.debug("Move result: scene={}, moves={}", newScene, isWrongMove);

        // Set response content and send the JSON response
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new Gson().toJson(jsonResponse));
    }
}

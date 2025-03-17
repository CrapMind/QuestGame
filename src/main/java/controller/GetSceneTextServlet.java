package controller;

import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import model.Game;
import service.MovingService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The GetSceneTextServlet class handles HTTP GET requests to fetch scene text
 * and related choices from the game.
 */

@Slf4j
@WebServlet(value = "/rest/scene")
public class GetSceneTextServlet extends HttpServlet {

    /**
     * Handles GET requests by retrieving the scene text and corresponding choices
     * based on the provided "scene" parameter.
     *
     * @param request  the HTTP request containing the scene parameter
     * @param response the HTTP response to send the JSON response
     * @throws IOException if an input or output error occurs while handling the request
     */

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // Retrieve the "scene" parameter from the request
        String sceneParameter = request.getParameter("scene");
        int scene;

        log.info("Received request for scene={} from IP={}", sceneParameter, request.getRemoteAddr());

        // Validate the "scene" parameter
        if (sceneParameter == null || sceneParameter.isEmpty()) {
            log.warn("Missing 'scene' parameter from request: {}", request.getRemoteAddr());
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "'scene' parameter is missing or empty");
            return;
        }

        try {
            // Convert the parameter to an integer
            scene = Integer.parseInt(sceneParameter);
        } catch (NumberFormatException e) {
            log.error("Invalid 'scene' parameter received: {}", sceneParameter, e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid 'scene' parameter");
            return;
        }


        Map<String, Object> jsonResponse = getJsonResponse(scene);

        log.debug("Sending response: {}", jsonResponse);

        // Set response headers and send the JSON response
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new Gson().toJson(jsonResponse));
    }

    private static Map<String, Object> getJsonResponse(int scene) {
        // Retrieve the MovingService instance from the Game singleton
        MovingService movingService = Game.getInstance().getMovingService();

        // Get the scene text and possible choices
        String sceneText = movingService.getSceneText(scene);
        List<String> choices = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            choices.add(movingService.getButtonText(scene, i));
        }

        // Prepare the JSON response
        Map<String, Object> jsonResponse = new HashMap<>();
        jsonResponse.put("text", sceneText);
        jsonResponse.put("choices", choices);
        return jsonResponse;
    }
}

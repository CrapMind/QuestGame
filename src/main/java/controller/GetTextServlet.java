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

@Slf4j
@WebServlet(value = "/rest/scene")
public class GetTextServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String sceneParameter = request.getParameter("scene");
        int scene;

        log.info("Received request for scene={} from IP={}", sceneParameter, request.getRemoteAddr());

        if (sceneParameter == null || sceneParameter.isEmpty()) {
            log.warn("Missing 'scene' parameter from request: {}", request.getRemoteAddr());
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "'scene' parameter is missing or empty");
            return;
        }

        try {
            scene = Integer.parseInt(sceneParameter);
        } catch (NumberFormatException e) {
            log.error("Invalid 'scene' parameter received: {}", sceneParameter, e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid 'scene' parameter");
            return;
        }

        MovingService movingService = Game.getInstance().getMovingService();

        String sceneText = movingService.getSceneText(scene);
        List<String> choices = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            choices.add(movingService.getButtonText(scene, i));
        }

        Map<String, Object> jsonResponse = new HashMap<>();
        jsonResponse.put("text", sceneText);
        jsonResponse.put("choices", choices);

        log.debug("Sending response: {}", jsonResponse);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new Gson().toJson(jsonResponse));
    }
}

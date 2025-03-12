package controller;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Game;
import service.MovingService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(value = "/rest/scene")
public class GetTextServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String sceneParameter = request.getParameter("scene");
        int scene;

        if (sceneParameter == null || sceneParameter.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "'scene' parameter is missing or empty");
            return;
        }

        try {
            scene = Integer.parseInt(sceneParameter);
        } catch (NumberFormatException e) {
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

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new Gson().toJson(jsonResponse));
    }
}

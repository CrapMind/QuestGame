package controller;

import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Game;
import service.MovingService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(value = "/rest/move")
public class MoveServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String sceneParam = request.getParameter("scene");
        String choiceParam = request.getParameter("choice");
        int scene;

        if (sceneParam == null || choiceParam == null || sceneParam.isEmpty() || choiceParam.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid parameters");
            return;
        }
        try {
            scene = Integer.parseInt(sceneParam);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid parameters");
            return;
        }

        MovingService movingService = Game.getInstance().getMovingService();
        String newScene = movingService.move(scene, choiceParam);

        Map<String, Object> jsonResponse = new HashMap<>();
        jsonResponse.put("scene", newScene);
        jsonResponse.put("choice", movingService.isWrongChoice());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new Gson().toJson(jsonResponse));
    }
}

package controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
/**
 * The newGameServlet class handles HTTP GET requests to start a new game session.
 * It invalidates any existing session and responds with a success status.
 */
@Slf4j
@WebServlet (value = "/rest/newGame")
public class newGameServlet extends HttpServlet {
    /**
     * Handles GET requests by invalidating the current session (if any)
     * and responding with a success message to indicate a new game session can be started.
     *
     * @param request  the HTTP request object
     * @param response the HTTP response object
     * @throws IOException if an input or output error occurs while handling the request
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // Retrieve the existing session without creating a new one
        HttpSession session = request.getSession(false);

        // Check if there is an active session and invalidate it
        if (session != null) {
            log.info("Invalidating session for client IP: {}", request.getRemoteAddr());
            session.invalidate();
        }
        else {
            log.warn("No active session found for client IP: {}", request.getRemoteAddr());
        }

        // Set response content type and send a success status as JSON
        response.setContentType("text/html");
        response.getWriter().write("{\"status\": \"success\"}");

        log.info("New game initialized for client IP: {}", request.getRemoteAddr());
    }
}

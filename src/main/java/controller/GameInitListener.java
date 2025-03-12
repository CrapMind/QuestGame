package controller;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import lombok.extern.slf4j.Slf4j;
import model.Game;
@Slf4j
@WebListener
public class GameInitListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent event) {
        log.info("Initializing game...");
        Game game = Game.init();
        event.getServletContext().setAttribute("game", game);
        log.info("Game initialized successfully.");
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        log.info("Shutting down game...");
        event.getServletContext().removeAttribute("game");
        log.info("Game shut down successfully.");
    }

}

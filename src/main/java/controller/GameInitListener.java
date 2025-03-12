package controller;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import model.Game;

@WebListener
public class GameInitListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent event) {
        Game game = Game.init();
        event.getServletContext().setAttribute("game", game);
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        event.getServletContext().removeAttribute("game");
    }

}

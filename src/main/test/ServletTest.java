import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ServletTest {
    private HttpServletRequest request;
    private HttpSession session;

    @BeforeEach
    void setUp() {
        request = mock(HttpServletRequest.class);
        session = mock(HttpSession.class);
    }

    @Test
    void testInvalidSession() {
        when(request.getSession(false)).thenReturn(null);
        assertNull(request.getSession(false));
    }

    @Test
    void testValidSession() {
        when(request.getSession()).thenReturn(session);
        assertNotNull(request.getSession());
    }

    @Test
    void testSetPlayerNameServlet() {
        when(request.getParameter("playerName")).thenReturn("TestUser");
        assertEquals("TestUser", request.getParameter("playerName"));
    }
}

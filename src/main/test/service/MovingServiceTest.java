package service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import repository.MovingRepository;
import service.impl.MovingServiceImpl;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MovingServiceTest {

    private MovingRepository mockRepository;
    private MovingService testService;

    @BeforeEach
    public void setUp() {
        testService = new MovingServiceImpl();
        mockRepository = mock(MovingRepository.class);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 5, 10, 9999})
    void testSceneValidation(int scene) {
        when(mockRepository.getSceneText(scene)).thenReturn(scene > 10 ? "Scene not found." : "Valid scene text");
        String text = testService.getSceneText(scene);
        if (scene > 10) {
            assertEquals("Scene not found.", text);
        } else {
            assertNotNull(text);
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"Look into the bag", "Random choice", "", "Get up and look for a way out"})
    void testChoiceValidation(String choice) {
        String result = testService.move(1, choice);
        if (choice.equals("Look into the bag")) {
            assertEquals("✅ Correct choice! Moving to the next scene.", result);
        } else {
            assertTrue(result.contains("❌ Wrong choice!") || result.equals("❌ Invalid choice."));
        }
    }

    @Test
    void testGetSceneText() {
        when(mockRepository.getSceneText(1)).thenReturn("You come to, your head is buzzing, there is a dense jungle around you. There is a bag in front of you.");
        String text = testService.getSceneText(1);
        assertEquals("You come to, your head is buzzing, there is a dense jungle around you. There is a bag in front of you.", text);
    }
}

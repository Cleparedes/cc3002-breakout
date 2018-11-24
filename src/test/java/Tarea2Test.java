import controller.Game;
import facade.HomeworkTwoFacade;
import logic.brick.*;
import logic.level.Level;
import logic.level.NullLevel;
import logic.level.PlayableLevel;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class Tarea2Test {
    private HomeworkTwoFacade hw2;
    private final int glassScore = 50;
    private final int woodenScore = 200;
    private final int seed = 0;
    private final int initialBalls = 3;
    private Brick glassBrick;
    private Brick woodenBrick;
    private Brick metalBrick;
    private Brick nullBrick;
    private Level level0;
    private Level level1;
    private Level level2;
    private Level level3;
    private Level level4;
    private Game game;

    @Before
    public void setUp() {
        hw2 = new HomeworkTwoFacade();
        glassBrick = new GlassBrick();
        woodenBrick = new WoodenBrick();
        metalBrick = new MetalBrick();
        nullBrick = new NullBrick();
        level0 = new NullLevel();
        level1 = new PlayableLevel("Level 1", 10, 0.5, 0.5, seed);
        level2 = new PlayableLevel("Level 2", 10, 0.5, 0.0, seed);
        level3 = new PlayableLevel("Level 3", 10, 1, 0.5, seed);
        level4 = new PlayableLevel("Level 4", 10, 1, 0.0, seed);
        game = new Game(initialBalls);
    }

    @Test
    public void testBrick() {
        assertFalse(glassBrick.isDestroyed());
        assertFalse(woodenBrick.isDestroyed());
        assertFalse(metalBrick.isDestroyed());
        assertTrue(nullBrick.isDestroyed());
        glassBrick.hit();
        assertTrue(glassBrick.isDestroyed());
        int i = 0;
        for(; i<2; i++) {
            woodenBrick.hit();
            metalBrick.hit();
            assertFalse(woodenBrick.isDestroyed());
            assertFalse(metalBrick.isDestroyed());
            assertEquals(2-i, woodenBrick.remainingHits());
            assertEquals(9-i, metalBrick.remainingHits());
        }
        woodenBrick.hit();
        assertTrue(woodenBrick.isDestroyed());
        for(; i<9; i++) {
            metalBrick.hit();
            assertFalse(metalBrick.isDestroyed());
            assertEquals(9-i, metalBrick.remainingHits());
        }
        metalBrick.hit();
        assertTrue(metalBrick.isDestroyed());
        assertEquals(glassScore, glassBrick.getScore());
        assertEquals(woodenScore, woodenBrick.getScore());
        assertEquals(0, metalBrick.getScore());
    }

    @Test
    public void testLevel(){
        assertEquals("Level 1", level1.getName());
        assertEquals(0, level0.getNumberOfBricks());
        assertEquals(16, level1.getNumberOfBricks());
        assertEquals(10, level2.getNumberOfBricks());
        assertEquals(16, level3.getNumberOfBricks());
        assertEquals(10, level4.getNumberOfBricks());
        assertEquals(new ArrayList<>(), level0.getBricks());
        assertFalse(level0.isPlayableLevel());
        assertTrue(level1.isPlayableLevel());
        assertFalse(level0.hasNextLevel());
        assertFalse(level1.hasNextLevel());
        level0.addPlayingLevel(level1).addPlayingLevel(level2);
        assertFalse(level0.hasNextLevel());
        assertTrue(level1.hasNextLevel());
        assertEquals("Level 2", level1.getNextLevel().getName());
        assertEquals("", level2.getNextLevel().getName());
    }

    @Test
    public void testGame(){
        assertEquals(initialBalls, game.getBallsLeft());
    }
}

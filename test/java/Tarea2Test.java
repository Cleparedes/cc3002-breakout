import facade.HomeworkTwoFacade;
import logic.brick.Brick;
import logic.brick.GlassBrick;
import logic.brick.MetalBrick;
import logic.brick.WoodenBrick;
import logic.level.Level;
import logic.level.PlayableLevel;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
    private Level level1;
    private Level level2;
    private Level level3;
    private Level level4;

    @Before
    public void setUp() {
        hw2 = new HomeworkTwoFacade();
        glassBrick = new GlassBrick();
        woodenBrick = new WoodenBrick();
        metalBrick = new MetalBrick();
        level1 = new PlayableLevel("Level 1", 10, 0.5, 0.5, seed);
        level2 = new PlayableLevel("Level 2", 10, 0.5, 0.0, seed);
        level3 = new PlayableLevel("Level 3", 10, 1, 0.5, seed);
        level4 = new PlayableLevel("Level 4", 10, 1, 0.0, seed);
    }

    @org.junit.Test
    public void testBrick() {
        assertFalse(glassBrick.isDestroyed());
        assertFalse(woodenBrick.isDestroyed());
        assertFalse(metalBrick.isDestroyed());
        glassBrick.hit();
        assertTrue(glassBrick.isDestroyed());
        int i = 0;
        for(; i<2; i++) {
            woodenBrick.hit();
            metalBrick.hit();
            assertFalse(woodenBrick.isDestroyed());
            assertFalse(metalBrick.isDestroyed());
        }
        woodenBrick.hit();
        assertTrue(woodenBrick.isDestroyed());
        for(; i<9; i++) {
            metalBrick.hit();
            assertFalse(metalBrick.isDestroyed());
        }
        metalBrick.hit();
        assertTrue(metalBrick.isDestroyed());
    }
}

import controller.Game;
import facade.HomeworkTwoFacade;
import logic.brick.Brick;
import logic.brick.GlassBrick;
import logic.brick.MetalBrick;
import logic.brick.WoodenBrick;
import logic.level.Level;
import logic.level.NullLevel;
import logic.level.PlayableLevel;
import logic.visitor.GetsDestroyed;
import logic.visitor.IsPlayable;
import logic.visitor.IsWinner;
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
    private Level level0;
    private Level level1;
    private Level level2;
    private Level level3;
    private Level level4;
    private Game game;
    private GetsDestroyed gd;
    private IsWinner iw;
    private IsPlayable ip;

    @Before
    public void setUp() {
        hw2 = new HomeworkTwoFacade();
        glassBrick = new GlassBrick();
        woodenBrick = new WoodenBrick();
        metalBrick = new MetalBrick();
        level0 = new NullLevel();
        level1 = new PlayableLevel("Level 1", 10, 0.5, 0.5, seed);
        level2 = new PlayableLevel("Level 2", 10, 0.5, 0.0, seed);
        level3 = new PlayableLevel("Level 3", 10, 1, 0.5, seed);
        level4 = new PlayableLevel("Level 4", 10, 1, 0.0, seed);
        game = new Game(initialBalls);
        gd = new GetsDestroyed();
        iw = new IsWinner();
        ip = new IsPlayable();
    }

    @Test
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
        assertEquals(0, level0.getPoints());
        assertEquals(3*glassScore+7*woodenScore, level1.getPoints());
        assertEquals(3*glassScore+7*woodenScore, level2.getPoints());
        assertEquals(10*glassScore, level3.getPoints());
        assertEquals(10*glassScore, level4.getPoints());
        assertEquals(new ArrayList<>(), level0.getBricks());
        assertFalse(level0.isPlayableLevel());
        assertTrue(level1.isPlayableLevel());
        assertFalse(level0.hasNextLevel());
        assertFalse(level1.hasNextLevel());
        level0.addPlayingLevel(level1).addPlayingLevel(level2);
        assertFalse(level0.hasNextLevel());
        assertTrue(level1.hasNextLevel());
        assertFalse(level2.hasNextLevel());
        assertEquals("Level 2", level1.getNextLevel().getName());
        assertEquals("", level2.getNextLevel().getName());
        assertEquals(level0, level0.getNextLevel());
        level0.setNextLevel(level1);
        assertFalse(level0.hasNextLevel());
    }

    @Test
    public void testGame(){
        assertEquals(initialBalls, game.getBallsLeft());
        assertEquals(initialBalls-1, game.dropBall());
        assertFalse(game.winner());
        assertFalse(game.hasCurrentLevel());
        assertEquals(new ArrayList<>(), game.getBricks());
        assertEquals(0, game.getLevelPoints());
        game.addPlayingLevel(level4);
        assertTrue(game.hasCurrentLevel());
        assertEquals(10*glassScore, game.getLevelPoints());
        assertFalse(game.hasNextLevel());
        assertEquals("Level 4", game.getLevelName());
        assertEquals(10, game.numberOfBricks());
        assertEquals(level4, game.getCurrentLevel());
        game.getBricks().forEach(Brick::hit);
        assertEquals(10*glassScore, game.getCurrentPoints());
        assertEquals("", game.getLevelName());
        assertTrue(game.winner());
        game.addPlayingLevel(level1);
        assertEquals(16, game.numberOfBricks());
        assertEquals("Level 1", game.getLevelName());
        for(int i=0; i<3; i++)
            game.getBricks().forEach(Brick::hit);
        assertTrue(game.winner());
        game.addPlayingLevel(level2);
        game.addPlayingLevel(level3);
        assertEquals("Level 2", game.getLevelName());
        game.goNextLevel();
        assertEquals("Level 3", game.getLevelName());
        game.setCurrentLevel(level2);
        assertEquals("Level 2", game.getLevelName());
        while(game.dropBall() > 0);
        assertTrue(game.isGameOver());
    }

    @Test
    public void testVisitor(){
        glassBrick.accept(gd);
        assertEquals(50, gd.getScore());
        woodenBrick.accept(gd);
        assertEquals(200, gd.getScore());
        metalBrick.accept(gd);
        assertEquals(0, gd.getScore());
        level0.accept(iw);
        assertFalse(iw.getValue());
        level1.accept(iw);
        assertFalse(iw.getValue());
        level0.accept(ip);
        assertFalse(ip.getValue());
        level2.accept(ip);
        assertTrue(ip.getValue());
    }

    @Test
    public void testFacade(){
        assertEquals(initialBalls, hw2.getBallsLeft());
        assertEquals(initialBalls-1, hw2.dropBall());
        assertFalse(hw2.winner());
        assertFalse(hw2.isGameOver());
        Level level5 = hw2.newLevelWithBricksFull("Level 5", 10, 0.5, 0.5, seed);
        Level level6 = hw2.newLevelWithBricksNoMetal("Level 6", 10, 0.5, seed);
        assertFalse(hw2.hasCurrentLevel());
        assertEquals(new ArrayList<>(), hw2.getBricks());
        assertEquals(0, hw2.getLevelPoints());
        hw2.addPlayingLevel(level5);
        assertTrue(hw2.hasCurrentLevel());
        assertEquals("Level 5", hw2.getLevelName());
        assertEquals(level5, hw2.getCurrentLevel());
        hw2.setCurrentLevel(level6);
        assertEquals(level6, hw2.getCurrentLevel());
        assertFalse(hw2.hasNextLevel());
        hw2.addPlayingLevel(level5);
        assertTrue(hw2.hasNextLevel());
        assertEquals(level6, hw2.getCurrentLevel());
        assertEquals(10, hw2.numberOfBricks());
        hw2.goNextLevel();
        assertEquals(level5, hw2.getCurrentLevel());
        assertEquals(16, hw2.numberOfBricks());
        hw2.addPlayingLevel(level6);
        hw2.goNextLevel();
        assertEquals(level6, hw2.getCurrentLevel());

    }
}

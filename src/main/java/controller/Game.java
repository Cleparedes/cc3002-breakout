package controller;

import logic.brick.Brick;
import logic.level.Level;
import logic.level.NullLevel;
import logic.visitor.IsPlayable;
import logic.visitor.IsWinner;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Game logic controller class.
 *
 * @author Clemente Paredes G.
 */
public class Game implements Observer {

    /**
     *  Current number of balls in game (lives).
     */
    private int balls;
    /**
     * Current level being displayed.
     */
    private Level currentLevel;
    /**
     * Current points obtained across all levels played.
     */
    private int currentPoints;

    /**
     * Game constructor.
     *
     * @param balls number of initial balls of the game.
     */
    public Game(int balls){
        this.balls = balls;
        currentLevel = new NullLevel();
        currentLevel.subscribe(this);
        currentPoints = 0;
    }

    /**
     * Gets the number of {@link Brick} in the current level, that are still not destroyed.
     *
     * @return the number of intact bricks in the current level.
     */
    public int numberOfBricks(){
        return currentLevel.getNumberOfBricks();
    }

    /**
     * Gets the list of {@link Brick}s from the current level.
     *
     * @return  list of {@link Brick}s.
     */
    public List<Brick> getBricks() {
        return currentLevel.getBricks();
    }

    /**
     * Whether or not the next {@link Level} is playable or not.
     *
     * @return true if the next {@link Level} is playable, false if otherwise.
     */
    public boolean hasNextLevel() {
        IsPlayable ip = new IsPlayable();
        currentLevel.getNextLevel().accept(ip);
        return ip.getValue();
    }

    /**
     * Sets the next {@link Level} as the current level and turns it into an observed by Game.
     */
    public void goNextLevel() {
        currentLevel = currentLevel.getNextLevel();
        currentLevel.subscribe(this);
    }

    /**
     * Whether the current {@link Level} is playable or not.
     *
     * @return  true if the {@link Level} is playable, false otherwise.
     */
    public boolean hasCurrentLevel() {
        return currentLevel.isPlayableLevel();
    }

    /**
     * Gets the name of the current {@link Level}.
     *
     * @return  name of the current {@link Level}.
     */
    public String getLevelName() {
        return currentLevel.getName();
    }

    /**
     * Gets the current {@link Level}.
     *
     * @return  current {@link Level}.
     */
    public Level getCurrentLevel() {
        return currentLevel;
    }

    /**
     * Sets the current as the one given ignoring any conditions.
     *
     * @param level {@link Level} to be set as current {@link Level}.
     */
    public void setCurrentLevel(Level level) {
        currentLevel = level;
        level.subscribe(this);
    }

    /**
     * Adds a {@link Level} to the end of the queue to be played in the game.
     *
     * @param level {@link Level} to be put in the queue.
     */
    public void addPlayingLevel(Level level) {
        currentLevel = currentLevel.addPlayingLevel(level);
    }

    /**
     * Gets the total number of points obtainable in a level
     *
     * @return  points to win the level.
     */
    public int getLevelPoints() {
        return currentLevel.getPoints();
    }

    public int getCurrentPoints() {
        return currentPoints;
    }

    public int getBallsLeft() {
        return balls;
    }

    public int dropBall() {
        if(balls == 0)
            return 0;
        balls--;
        return balls;
    }

    public boolean isGameOver() {
        return balls == 0;
    }

    /**
     * Checks whether the game has a winner or not
     *
     * @return true if the game has a winner, false otherwise
     */
    public boolean winner() {
        IsWinner iw = new IsWinner();
        currentLevel.accept(iw);
        return iw.getValue();
    }

    @Override
    public void update(Observable o, Object arg) {
        if(arg == null)
            this.goNextLevel();
        else{
            int score = (int) arg;
            if(score == 0)
                balls++;
            else
                currentPoints += score;
        }
    }
}

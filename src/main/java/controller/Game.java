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

    private int balls;
    private Level currentLevel;
    private int currentPoints;

    public Game(int balls){
        this.balls = balls;
        currentLevel = new NullLevel();
        currentLevel.subscribe(this);
        currentPoints = 0;
    }

    /**
     * Gets the number of {@link Brick} in the current level, that are still not destroyed
     *
     * @return the number of intact bricks in the current level
     */
    public int numberOfBricks(){
        return currentLevel.getNumberOfBricks();
    }

    public List<Brick> getBricks() {
        return currentLevel.getBricks();
    }

    public boolean hasNextLevel() {
        IsPlayable ip = new IsPlayable();
        currentLevel.getNextLevel().accept(ip);
        return ip.getValue();
    }

    public void goNextLevel() {
        currentLevel = currentLevel.getNextLevel();
        currentLevel.subscribe(this);
    }

    public boolean hasCurrentLevel() {
        return currentLevel.isPlayableLevel();
    }

    public String getLevelName() {
        return currentLevel.getName();
    }

    public Level getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(Level level) {
        currentLevel = level;
        level.subscribe(this);
    }

    public void addPlayingLevel(Level level) {
        currentLevel = currentLevel.addPlayingLevel(level);
    }

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

package logic.level;

import logic.brick.*;
import logic.visitor.*;


import java.util.*;

public class PlayableLevel extends Observable implements Level, Observer {

    /**
     * Name of the level.
     */
    private String name;
    /**
     * List of {@Link Brick}s of the level.
     */
    private List<Brick> bricks;
    /**
     * Next level, null level if the current level is the last one in queue.
     */
    private Level nextLevel;
    /**
     * Points obtained in the level so far.
     */
    private int currentPoints;

    /**
     * Constructor of a playable level.
     *
     * @param name              name of the level.
     * @param numberOfBricks    total amount of normal {@Link Brick}s (Glass or Wooden).
     * @param probOfGlass       probability of a normal {@Link Brick} to be Glass or not.
     * @param probOfMetal       probability of a metal {@Link Brick} to exist.
     * @param seed              seed to apply the above probabilities.
     */
    public PlayableLevel(String name, int numberOfBricks, double probOfGlass, double probOfMetal, int seed){
        this.name = name;
        Random generator = new Random(seed);
        List<Brick> bricks = new ArrayList<>();
        for(int i=0; i<numberOfBricks; i++){
            if(generator.nextDouble() < probOfGlass)
                bricks.add(new GlassBrick());
            else
                bricks.add(new WoodenBrick());
        }
        for(int i=0; i<numberOfBricks; i++){
            if(generator.nextDouble() < probOfMetal)
                bricks.add(new MetalBrick());
        }
        bricks.forEach(brick -> brick.subscribe(this));
        this.bricks = bricks;
        nextLevel = new NullLevel();
        currentPoints = 0;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getNumberOfBricks() {
        return bricks.size();
    }

    @Override
    public List<Brick> getBricks() {
        return bricks;
    }

    @Override
    public Level getNextLevel() {
        return nextLevel;
    }

    @Override
    public boolean isPlayableLevel() {
        return true;
    }

    @Override
    public boolean hasNextLevel() {
        IsPlayable ip = new IsPlayable();
        nextLevel.accept(ip);
        return ip.getValue();
    }

    @Override
    public int getPoints() {
        int p = 0;
        for(Brick b : bricks)
            p += b.getScore();
        return p;
    }

    @Override
    public Level addPlayingLevel(Level level) {
        nextLevel = nextLevel.addPlayingLevel(level);
        return this;
    }

    @Override
    public void setNextLevel(Level level) {
        nextLevel = level;
    }


    @Override
    public void update(Observable o, Object arg) {
        Brick brick = (Brick) o;
        GetsDestroyed gd = new GetsDestroyed();
        brick.accept(gd);
        currentPoints += gd.getScore();
        setChanged();
        notifyObservers(gd.getScore());
        if(getPoints() == currentPoints){
            setChanged();
            notifyObservers(null);
        }
    }

    @Override
    public void subscribe(Observer game){
        addObserver(game);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visitPlayableLevel(this);
    }
}

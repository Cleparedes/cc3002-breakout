package logic.level;

import java.util.*;

import controller.Game;
import logic.brick.Brick;
import logic.brick.GlassBrick;
import logic.brick.MetalBrick;
import logic.brick.WoodenBrick;

public class LevelClass extends Observable implements Level, Observer {

    private String name;
    private List<Brick> bricks;
    private Level nextLevel;
    private boolean playable;
    private int numberOfBricks;

    public LevelClass(String name, int numberOfBricks, double probOfGlass, double probOfMetal, int seed){
        this.name = name;
        Random generator = new Random(seed);
        int numberOfGlass = numberOfBricks*(int) (probOfGlass*generator.nextDouble());
        int numberOfMetal = numberOfBricks*(int) (probOfMetal*generator.nextDouble()) + numberOfGlass;
        List<Brick> bricks = new ArrayList<>();
        int i = 0;
        for(; i<numberOfGlass; i++)
            bricks.add(new GlassBrick());
        for(i=numberOfGlass; i<numberOfMetal; i++)
            bricks.add(new MetalBrick());
        for(i=numberOfMetal; i<numberOfBricks; i++)
            bricks.add(new WoodenBrick());
        this.bricks = bricks;
        nextLevel = null;
        playable = true;
        this.numberOfBricks = numberOfBricks;
    }

    public LevelClass(){
        this(null, Integer.MAX_VALUE, 0.0, 0.0, 0);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getNumberOfBricks() {
        return numberOfBricks;
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
        return playable;
    }

    @Override
    public boolean hasNextLevel() {
        if(nextLevel != null)
            return nextLevel.isPlayableLevel();
        return false;
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
        if(nextLevel == null)
            nextLevel = level;
        else
            nextLevel.addPlayingLevel(level);
        return this;
    }

    @Override
    public void setNextLevel(Level level) {
        nextLevel = level;
    }

    @Override
    public void update(Observable o, Object arg) {
        if(arg instanceof Brick){
            numberOfBricks--;
            if(((Brick) arg).isMetal())
                notifyObservers(arg);
            if(numberOfBricks == 0){
                notifyObservers(this);
            }
        }
    }

    public void subscribe(Observer game){
        addObserver(game);
    }
}

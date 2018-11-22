package logic.level;

import java.util.*;

import logic.brick.*;
import logic.visitor.AbstractVisitor;

public class PlayableLevel extends Observable implements Level, Observer {

    private String name;
    private List<Brick> bricks;
    private Level nextLevel;
    private int numberOfBricks;
    private int numberOfMetalBricks;

    public PlayableLevel(String name, int numberOfBricks, double probOfGlass, double probOfMetal, int seed){
        this.name = name;
        this.numberOfBricks = numberOfBricks;
        Random generator = new Random(seed);
        List<Brick> bricks = new ArrayList<>();
        for(int i=0; i<numberOfBricks; i++){
            if(generator.nextDouble() < probOfGlass)
                bricks.add(new GlassBrick());
            else
                bricks.add(new WoodenBrick());
        }
        for(int i=0; i<numberOfBricks; i++){
            if(generator.nextDouble() < probOfMetal) {
                bricks.add(new MetalBrick());
                this.numberOfMetalBricks++;
            }
        }
        bricks.forEach(brick -> {
            brick.subscribe(this);
        });
        this.bricks = bricks;
        nextLevel = null;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getNumberOfBricks() {
        return numberOfBricks + numberOfMetalBricks;
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
        return nextLevel != null;
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
            if(((Brick) arg).isMetal()) {
                numberOfMetalBricks--;
            }
            else
                numberOfBricks--;
            setChanged();
            notifyObservers(arg);
            int index = bricks.indexOf(arg);
            bricks.set(index, new NullBrick());
            if(numberOfBricks == 0){
                setChanged();
                notifyObservers(this);
            }
        }
    }

    public void subscribe(Observer game){
        addObserver(game);
    }

    @Override
    public void accept(AbstractVisitor visitor) {
        visitor.visitPlayableLevel(this);
    }
}

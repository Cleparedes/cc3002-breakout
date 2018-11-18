package logic.level;

import logic.brick.Brick;

import java.util.List;

public class LevelClass implements Level {

    private final String name;
    private int numberOfBricks;
    private List<Brick> bricks;
    private LevelClass nextLevel;
    private boolean playable;

    LevelClass(final String name){
        this.name = name;
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
        if(hasNextLevel()){
            return nextLevel.playable;
        }
        return false;
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
        return null;
    }

    @Override
    public void setNextLevel(Level level) {

    }
}

package logic.level;

import logic.brick.Brick;
import logic.visitor.AbstractVisitor;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

public class NullLevel implements Level{

    @Override
    public String getName() {
        return "";
    }

    @Override
    public int getNumberOfBricks() {
        return 0;
    }

    @Override
    public List<Brick> getBricks() {
        return new ArrayList<Brick>();
    }

    @Override
    public Level getNextLevel() {
        return this;
    }

    @Override
    public boolean isPlayableLevel() {
        return false;
    }

    @Override
    public boolean hasNextLevel() {
        return false;
    }

    @Override
    public int getPoints() {
        return 0;
    }

    @Override
    public Level addPlayingLevel(Level level) {
        return null;
    }

    @Override
    public void setNextLevel(Level level) {

    }

    @Override
    public void subscribe(Observer game) {

    }

    @Override
    public void accept(AbstractVisitor visitor) {
        visitor.visitNullLevel(this);
    }
}

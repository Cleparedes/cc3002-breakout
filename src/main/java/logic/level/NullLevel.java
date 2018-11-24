package main.java.logic.level;

import main.java.logic.brick.Brick;
import main.java.logic.visitor.Visitor;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

public class NullLevel implements Level{

    private boolean isFinal;

    private NullLevel(boolean isFinal){
        this.isFinal = isFinal;
    }

    public NullLevel(){
        this(false);
    }

    public boolean isFinal(){
        return isFinal;
    }

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
        level.setNextLevel(new NullLevel(true));
        return level;
    }

    @Override
    public void setNextLevel(Level level) {

    }

    @Override
    public void subscribe(Observer game) {

    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visitNullLevel(this);
    }
}

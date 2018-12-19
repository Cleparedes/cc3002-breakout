package logic.level;

import logic.brick.Brick;
import logic.visitor.Visitor;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

public class NullLevel implements Level{

    /**
     * Whether the null level is the tail of the level list or not.
     */
    private boolean isFinal;

    /**
     * Constructor of a null level.
     *
     * @param isFinal whether the level is at the end or not.
     */
    private NullLevel(boolean isFinal){
        this.isFinal = isFinal;
    }

    public NullLevel(){
        this(false);
    }

    /**
     * Void constructor, by default is not at the tail of the queue.
     *
     * @return a not final null level
     */
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
        return new ArrayList<>();
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

    @Override
    public int getCurrentPoints() {
        return 0;
    }
}

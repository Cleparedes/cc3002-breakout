package logic.brick;

import java.util.Observer;

public class NullBrick implements Brick {
    @Override
    public void hit() {

    }

    @Override
    public boolean isDestroyed() {
        return true;
    }

    @Override
    public int getScore() {
        return 0;
    }

    @Override
    public int remainingHits() {
        return 0;
    }

    @Override
    public boolean isMetal() {
        return false;
    }

    @Override
    public void subscribe(Observer level) {

    }
}

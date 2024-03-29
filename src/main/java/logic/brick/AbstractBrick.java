package logic.brick;


import java.util.Observable;
import java.util.Observer;

/**
 * Represents a general state of a brick determined by {@Link Brick}.
 *
 * @author Clemente Paredes G.
 */
public abstract class AbstractBrick extends Observable implements Brick {

    /**
     * Remaining hits of the brick.
     */
    private int hp;

    /**
     * Constructor for a general Brick
     *
     * @param hp    the remaining hit points of the brick.
     */
    AbstractBrick(int hp){
        this.hp = hp;
    }

    @Override
    public void hit(){
        if(hp > 0) {
            hp--;
            if (hp == 0) {
                setChanged();
                notifyObservers();
            }
        }
    }

    @Override
    public boolean isDestroyed() {
        return hp == 0;
    }

    @Override
    public int remainingHits() {
        return hp;
    }

    @Override
    public void subscribe(Observer level){
        addObserver(level);
    }
}

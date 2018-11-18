package logic.brick;

import java.util.Observable;

/**
 * Represents a general state of a brick determined by {@Link Brick}.
 *
 * @author Clemente Paredes G.
 */
public abstract class AbstractBrick extends Observable implements Brick {

    private int hp;
    private final int pts;

    /**
     * Constructor for a general Brick
     *
     * @param hp    the remaining hit points of the brick.
     * @param pts   the points received after breaking the brick.
     */
    protected AbstractBrick(int hp, final int pts){
        this.hp = hp;
        this.pts = pts;
    }

    @Override
    public void hit(){
        if(!this.isDestroyed())
            hp--;
        if(this.isDestroyed()){
            setChanged();
            notifyObservers();
        }
    }

    @Override
    public boolean isDestroyed() {
        return hp <= 0;
    }

    @Override
    public int getScore() {
        return pts;
    }

    @Override
    public int remainingHits() {
        if(hp < 0)
            hp = 0;
        return hp;
    }
}

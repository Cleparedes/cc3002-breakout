package logic.brick;

import logic.visitor.Visitor;

import java.util.Observer;

/**
 * Interface that represents a brick object.
 * <p>
 * All bricks should implement this interface.
 *
 * @author Clemente Paredes G.
 */
public interface Brick {
    /**
     * Defines that a brick has been hit.
     * Implementations should consider the events that a hit to a brick can trigger.
     */
    void hit();

    /**
     * Gets whether the brick object is destroyed or not.
     *
     * @return true if the brick is destroyed, false otherwise
     */
    boolean isDestroyed();

    /**
     * Gets the points corresponding to the destroying of a brick object.
     *
     * @return the associated points of a brick object
     */
    int getScore();

    /**
     * Gets the remaining hits the brick has to receive before being destroyed.
     *
     * @return the remaining hits to destroy de brick
     */
    int remainingHits();

    /**
     * Sets the {@Link Level} as an Observer of the Brick.
     *
     * @param level the level containing the brick
     */
    void subscribe(Observer level);

    /**
     * Allows a {@Link Visitor} to perform an action over the Brick.
     *
     * @param visitor   a subclass of {@Link Visitor} with a designated action
     */
    void accept(Visitor visitor);
}

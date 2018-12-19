package logic.level;

import logic.brick.Brick;
import logic.visitor.Visitor;

import java.util.List;
import java.util.Observer;

/**
 * Interface that represents the basics of a level to be played on.
 *
 * @author Clemente Paredes G.
 */
public interface Level {
    /**
     * Gets the level's name. Each level must have a name.
     *
     * @return the table's name
     */
    String getName();

    /**
     * Gets the number of {@link Brick} in the level.
     *
     * @return the number of Bricks in the level
     */
    int getNumberOfBricks();

    /**
     * Gets the {@link List} of {@link Brick}s in the level.
     *
     * @return the bricks in the level
     */
    List<Brick> getBricks();

    /**
     * Gets the next level of a level object. Each level have a reference to the next level to play.
     *
     * @return the next level
     */
    Level getNextLevel();

    /**
     * Gets whether the level is playable or not.
     *
     * @return true if the level is playable, false otherwise
     */
    boolean isPlayableLevel();

    /**
     * Whether the level's next level is playable or not.
     *
     * @return true if the level's next level is playable, false otherwise
     */
    boolean hasNextLevel();

    /**
     * Gets the total number of points obtainable in level.
     *
     * @return the number of points in the current level
     */
    int getPoints();

    /**
     * Adds a level to the list of levels. This adds the level in the last position of the list.
     *
     * @param level the level to be added
     */
    Level addPlayingLevel(Level level);

    /**
     * Sets the reference for the next level of a level object.
     *
     * @param level the next level of a level object
     */
    void setNextLevel(Level level);

    /**
     * Sets the {@Link Game} as an Observer of the current level.
     *
     * @param game  the game controller
     */
    void subscribe(Observer game);

    /**
     * Allows a {@Link Visitor} to perform an action over the Level.
     *
     * @param visitor   a subclass of {@Link Visitor} with a designated action
     */
    void accept(Visitor visitor);

    /**
     * Returns current points
     *
     * @return current points
     */
    int getCurrentPoints();
}

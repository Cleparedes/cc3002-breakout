package logic.visitor;

import logic.brick.GlassBrick;
import logic.brick.MetalBrick;
import logic.brick.WoodenBrick;
import logic.level.NullLevel;
import logic.level.PlayableLevel;

/**
 * Declaration of methods for a generic Visitor.
 *
 * @author Clemente Paredes G.
 */
public abstract class Visitor {

    /**
     * Applies the action designed to the visitor to a {@Link PlyableLevel}.
     *
     * @param playableLevel the level to be visited.
     */
    public void visitPlayableLevel(PlayableLevel playableLevel){
    }

    /**
     * Applies the action designed to the visitor to a {@Link NullLevel}.
     *
     * @param nullLevel the level to be visited.
     */
    public void visitNullLevel(NullLevel nullLevel){
    }

    /**
     * Applies the action designed to the visitor to a {@Link GlassBrick}.
     *
     * @param glassBrick the brick to be visited.
     */
    public void visitGlassBrick(GlassBrick glassBrick){
    }

    /**
     * Applies the action designed to the visitor to a {@Link WoodenBrick}.
     *
     * @param woodenBrick the brick to be visited.
     */
    public void visitWoodenBrick(WoodenBrick woodenBrick){
    }

    /**
     * Applies the action designed to the visitor to a {@Link MetalBrick}.
     *
     * @param metalBrick the brick to be visited.
     */
    public void visitMetalBrick(MetalBrick metalBrick){
    }
}

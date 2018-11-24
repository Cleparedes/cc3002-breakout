package logic.visitor;

import logic.level.NullLevel;
import logic.level.PlayableLevel;

/**
 * Visit the current level to determine whether the game has a winner or not.
 */
public class IsWinner extends Visitor {

    /**
     * Whether the game has been won.
     */
    private boolean isWinner;

    @Override
    public void visitPlayableLevel(PlayableLevel playableLevel){
        isWinner = false;
    }

    @Override
    public void visitNullLevel(NullLevel nullLevel){
        isWinner = nullLevel.isFinal();
    }

    public boolean getValue(){
        return isWinner;
    }
}

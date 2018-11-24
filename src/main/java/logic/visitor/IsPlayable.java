package logic.visitor;

import logic.level.NullLevel;
import logic.level.PlayableLevel;

/**
 * Whether a level is playable or not.
 */
public class IsPlayable extends Visitor {
    /**
     * true if playable, false if not.
     */
    private boolean isPlayable;

    @Override
    public void visitPlayableLevel(PlayableLevel playableLevel){
        isPlayable = true;
    }

    @Override
    public void visitNullLevel(NullLevel nullLevel){
        isPlayable = false;
    }

    public boolean getValue(){
        return isPlayable;
    }
}

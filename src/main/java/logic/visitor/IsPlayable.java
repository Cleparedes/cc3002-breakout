package logic.visitor;

import logic.level.NullLevel;
import logic.level.PlayableLevel;

public class IsPlayable extends Visitor {

    private boolean isPlayable = false;

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

package logic.visitor;

import logic.level.NullLevel;
import logic.level.PlayableLevel;

public class IsWinner extends Visitor {

    boolean isWinner = false;

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

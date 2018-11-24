package main.java.logic.visitor;

import main.java.logic.level.NullLevel;
import main.java.logic.level.PlayableLevel;

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

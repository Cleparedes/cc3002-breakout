package logic.visitor;

import logic.level.Level;
import logic.level.NullLevel;
import logic.level.PlayableLevel;
import logic.visitor.AbstractVisitor;

public class IsWinner extends AbstractVisitor {

    boolean isWinner = false;

    @Override
    public void visitPlayableLevel(PlayableLevel playableLevel){
        isWinner = playableLevel.getNumberOfBricks() == 0;
    }

    @Override
    public void visitNullLevel(NullLevel nullLevel){
        isWinner = false;
    }

    public boolean getValue(){
        return isWinner;
    }
}

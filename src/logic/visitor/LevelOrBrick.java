package logic.visitor;

import logic.brick.GlassBrick;
import logic.brick.MetalBrick;
import logic.brick.NullBrick;
import logic.brick.WoodenBrick;
import logic.level.NullLevel;
import logic.level.PlayableLevel;

public class LevelOrBrick extends Visitor {

    private int instance;

    @Override
    public void visitNullBrick(NullBrick nullBrick){
        instance = 0;
    }

    @Override
    public void visitNullLevel(NullLevel nullLevel){
        instance = 0;
    }

    @Override
    public void visitPlayableLevel(PlayableLevel playableLevel){
        instance = 1;
    }

    @Override
    public void visitGlassBrick(GlassBrick glassBrick){
        instance = 2;
    }

    @Override
    public void visitWoodenBrick(WoodenBrick woodenBrick){
        instance = 2;
    }

    @Override
    public void visitMetalBrick(MetalBrick metalBrick) {
        instance = 3;
    }

    public int getType(){
        return instance;
    }
}

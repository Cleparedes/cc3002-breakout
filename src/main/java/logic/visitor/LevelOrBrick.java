package main.java.logic.visitor;

import main.java.logic.brick.GlassBrick;
import main.java.logic.brick.MetalBrick;
import main.java.logic.brick.NullBrick;
import main.java.logic.brick.WoodenBrick;
import main.java.logic.level.NullLevel;
import main.java.logic.level.PlayableLevel;

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

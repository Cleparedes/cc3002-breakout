package main.java.logic.visitor;

import main.java.logic.brick.GlassBrick;
import main.java.logic.brick.MetalBrick;
import main.java.logic.brick.NullBrick;
import main.java.logic.brick.WoodenBrick;
import main.java.logic.level.NullLevel;
import main.java.logic.level.PlayableLevel;

public abstract class Visitor {

    public void visitPlayableLevel(PlayableLevel playableLevel){
    }

    public void visitNullLevel(NullLevel nullLevel){
    }

    public void visitGlassBrick(GlassBrick glassBrick){
    }

    public void visitWoodenBrick(WoodenBrick woodenBrick){
    }

    public void visitMetalBrick(MetalBrick metalBrick){
    }

    public void visitNullBrick(NullBrick nullBrick){
    }
}

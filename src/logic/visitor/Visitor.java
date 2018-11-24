package logic.visitor;

import logic.brick.GlassBrick;
import logic.brick.MetalBrick;
import logic.brick.NullBrick;
import logic.brick.WoodenBrick;
import logic.level.NullLevel;
import logic.level.PlayableLevel;

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

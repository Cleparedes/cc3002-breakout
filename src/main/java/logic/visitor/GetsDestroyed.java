package logic.visitor;

import logic.brick.GlassBrick;
import logic.brick.MetalBrick;
import logic.brick.WoodenBrick;

public class GetsDestroyed extends Visitor{

    private int score;

    @Override
    public void visitGlassBrick(GlassBrick glassBrick){
        score = glassBrick.getScore();
    }

    @Override
    public void visitWoodenBrick(WoodenBrick woodenBrick){
        score = woodenBrick.getScore();
    }

    @Override
    public void visitMetalBrick(MetalBrick metalBrick){
        score = metalBrick.getScore();
    }

    public int getScore(){
        return score;
    }
}

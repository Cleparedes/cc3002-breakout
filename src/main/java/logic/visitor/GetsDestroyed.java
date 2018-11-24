package logic.visitor;

import logic.brick.GlassBrick;
import logic.brick.MetalBrick;
import logic.brick.WoodenBrick;

public class GetsDestroyed extends Visitor{

    public int score = 0;
    public boolean isMetal = false;

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
        isMetal = true;
    }

    public int getScore(){
        return score;
    }

    public boolean isMetal(){
        return isMetal;
    }
}

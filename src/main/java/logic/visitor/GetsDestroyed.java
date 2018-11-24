package main.java.logic.visitor;

import main.java.logic.brick.GlassBrick;
import main.java.logic.brick.MetalBrick;
import main.java.logic.brick.WoodenBrick;

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

package logic.brick;

import logic.visitor.Visitor;

public class MetalBrick extends AbstractBrick {

    public MetalBrick(){
        super(10);
    }

    @Override
    public int getScore() {
        return 0;
    }

    @Override
    public void accept(Visitor visitor){
        visitor.visitMetalBrick(this);
    }

    @Override
    public boolean isGlassBrick() {
        return false;
    }

    @Override
    public boolean isWoodenBrick() {
        return false;
    }

    @Override
    public boolean isMetalBrick() {
        return true;
    }
}

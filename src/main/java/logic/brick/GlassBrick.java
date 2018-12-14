package logic.brick;

import logic.visitor.Visitor;

public class GlassBrick extends AbstractBrick {

    public GlassBrick() {
        super(1);
    }

    @Override
    public int getScore(){
        return 50;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visitGlassBrick(this);
    }

    @Override
    public boolean isGlassBrick(){
        return true;
    }

    @Override
    public boolean isWoodenBrick() {
        return false;
    }

    @Override
    public boolean isMetalBrick() {
        return false;
    }
}

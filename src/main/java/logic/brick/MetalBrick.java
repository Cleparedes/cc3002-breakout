package main.java.logic.brick;

import main.java.logic.visitor.Visitor;

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
}

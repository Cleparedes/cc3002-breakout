package logic.brick;

import logic.visitor.Visitor;

public class WoodenBrick extends AbstractBrick {

    public WoodenBrick(){
        super(3);
    }

    @Override
    public int getScore() {
        return 200;
    }

    @Override
    public void accept(Visitor visitor){
        visitor.visitWoodenBrick(this);
    }
}

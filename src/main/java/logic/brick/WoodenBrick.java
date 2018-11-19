package logic.brick;

public class WoodenBrick extends AbstractBrick {

    public WoodenBrick(){
        super(3);
    }

    @Override
    public int getScore() {
        return 200;
    }

    @Override
    public boolean isMetal() {
        return false;
    }
}

package logic.brick;

public class MetalBrick extends AbstractBrick {

    public MetalBrick(){
        super(10);
    }

    @Override
    public int getScore() {
        return 0;
    }

    @Override
    public boolean isMetal() {
        return true;
    }
}

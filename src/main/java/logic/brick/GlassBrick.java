package logic.brick;

public class GlassBrick extends AbstractBrick {

    public GlassBrick() {
        super(1);
    }

    @Override
    public int getScore(){
        return 50;
    }

    @Override
    public boolean isMetal() {
        return false;
    }
}

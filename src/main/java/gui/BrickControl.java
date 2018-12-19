package gui;


import com.almasb.fxgl.entity.component.Component;
import logic.brick.Brick;

class BrickControl extends Component {

    private Brick brick;

    BrickControl(Brick brick){
        this.brick = brick;
    }

    void onHit(){
        brick.hit();
        if(brick.isWoodenBrick() && brick.remainingHits() == 1)
            entity.setViewFromTextureWithBBox("WB2.png");
        else{
            if(brick.isMetalBrick() && brick.remainingHits() == 5)
                entity.setViewFromTextureWithBBox("MB2.png");
        }
        if(brick.isDestroyed())
            entity.removeFromWorld();
    }

    public boolean isGlassBrick(){
        return brick.isGlassBrick();
    }

    public boolean isWoodenBrick(){
        return brick.isWoodenBrick();
    }

    boolean isMetalBrick(){
        return brick.isMetalBrick();
    }

    public int getScore(){
        return brick.getScore();
    }
}

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
        if(brick.isDestroyed())
            entity.removeFromWorld();
    }
}

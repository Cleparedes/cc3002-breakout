package gui;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;

public class PlayerControl extends Component {

    private Vec2 velocity = new Vec2();
    private PhysicsComponent barPhysics;
    private final float BAR_SPEED = 5;

    @Override
    public void onUpdate(double tpf){
        if(entity.getX() < 0)
            velocity.set((float)(-entity.getX()), 0);
        else{
            if(entity.getRightX() > FXGL.getAppWidth())
                velocity.set((float) (FXGL.getAppWidth()-entity.getRightX()), 0);
        }
        barPhysics.setBodyLinearVelocity(velocity);
    }

    public void left(){
        velocity.set(-BAR_SPEED, 0);
    }

    public void right(){
        velocity.set(BAR_SPEED, 0);
    }
}

package gui;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;

import static com.almasb.fxgl.core.math.FXGLMath.abs;

class BallControl extends Component {

    private Entity bar;
    private boolean released = false;
    private static final int BALL_SPEED = 5 * 60;
    private PhysicsComponent ballPhysics;

    /*public BallControl(Entity bar) {
        this.bar =bar;
        while(!released){
            entity.setPosition(bar.getX(), bar.getY());
        }
    }*/

    void release(){
        if(!released)
            ballPhysics.setLinearVelocity(BALL_SPEED, -BALL_SPEED);
        released = true;
    }

    void onHit(){
        if(released) {
            if (abs(ballPhysics.getVelocityY()) < BALL_SPEED * 0.5) {
                if (ballPhysics.getVelocityY() > 0)
                    ballPhysics.setLinearVelocity(ballPhysics.getVelocityX(), BALL_SPEED);
                else
                    ballPhysics.setLinearVelocity(ballPhysics.getVelocityX(), -BALL_SPEED);
            }
            if (abs(ballPhysics.getVelocityX()) < BALL_SPEED * 0.5) {
                if (ballPhysics.getVelocityX() > 0)
                    ballPhysics.setLinearVelocity(BALL_SPEED, ballPhysics.getVelocityY());
                else
                    ballPhysics.setLinearVelocity(-BALL_SPEED, ballPhysics.getVelocityY());
            }
        }
    }
}

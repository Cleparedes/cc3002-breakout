package gui;

import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.RenderLayer;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.physics.box2d.dynamics.FixtureDef;
import logic.brick.Brick;

final class GameFactory implements EntityFactory {

    private static final int BAR_WIDTH = 100;
    private static final int BAR_HEIGHT = 30;
    private static final int BALL_SIZE = 10;


    static Entity newBar(double x, double y){
        PhysicsComponent barPhysics = new PhysicsComponent();
        barPhysics.setBodyType(BodyType.KINEMATIC);

        return Entities.builder()
                .at(x - BAR_WIDTH / 2.0, y)
                .type(GameApp.Types.PLAYER)
                .viewFromTextureWithBBox("bar.png")
                .with(barPhysics, new PlayerControl(), new CollidableComponent(true))
                .build();
    }

    static Entity newBackground(){
        return Entities.builder()
                .viewFromTexture("background.jpg")
                .renderLayer(RenderLayer.BACKGROUND)
                .build();
    }

    static Entity newBall(double x, double y){
        PhysicsComponent ballPhysics = new PhysicsComponent();
        ballPhysics.setBodyType(BodyType.DYNAMIC);
        ballPhysics.setFixtureDef(new FixtureDef().restitution(1f).density(0.1f));
        ballPhysics.setOnPhysicsInitialized(() -> ballPhysics.setLinearVelocity(0, 0));

        return Entities.builder()
                .at(x + BAR_WIDTH / 2.0 - BALL_SIZE / 2.0, y - BALL_SIZE*2 - 1)
                .type(GameApp.Types.BALL)
                .bbox(new HitBox("Ball", BoundingShape.circle(BALL_SIZE)))
                .viewFromTexture("ball.png")
                .with(ballPhysics, new BallControl(), new CollidableComponent(true))
                .build();
    }

    static Entity newWalls(){
        Entity walls = Entities.makeScreenBounds(100);
        walls.setType(GameApp.Types.WALL);
        walls.addComponent(new CollidableComponent(true));
        return walls;
    }

    static Entity newBrick(double x, double y, Brick brick){
        String texture = "error.png";

        if(brick.isGlassBrick())
            texture = "GB1.png";
        else{
            if(brick.isWoodenBrick())
                texture = "WB1.png";
            else{
                if(brick.isMetalBrick())
                    texture = "MB1.png";
            }
        }

        return Entities.builder()
                .at(x, y)
                .type(GameApp.Types.BRICK)
                .viewFromTextureWithBBox(texture)
                .with(new BrickControl(brick), new PhysicsComponent(), new CollidableComponent(true))
                .build();
    }
}
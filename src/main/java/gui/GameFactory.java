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
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import logic.brick.Brick;

import java.util.Random;

final class GameFactory implements EntityFactory {

    private static final int BAR_WIDTH = 100;
    private static final int BAR_HEIGHT = 30;
    private static final int BALL_SIZE = 10;
    private static final int BRICK_WIDTH = 60;
    private static final int BRICK_HEIGHT = 25;
    private static final int BALL_SPEED = 5*60;
    private static Random random = new Random(123456);

    static Entity newBar(double x, double y){
        PhysicsComponent barPhysics = new PhysicsComponent();
        barPhysics.setBodyType(BodyType.KINEMATIC);

        return Entities.builder()
                .at(x - BAR_WIDTH / 2.0, y)
                .type(GameApp.Types.PLAYER)
                .viewFromNodeWithBBox(new Rectangle(BAR_WIDTH, BAR_HEIGHT, Color.BLUE))
                .with(barPhysics, new PlayerControl(), new CollidableComponent(true))
                .build();
    }

    static Entity newBackground(){
        return Entities.builder()
                .viewFromNode(new Rectangle(600, 600, Color.BLACK))
                .renderLayer(RenderLayer.BACKGROUND)
                .build();
    }


    static Entity newBall(double x, double y){
        PhysicsComponent ballPhysics = new PhysicsComponent();
        ballPhysics.setBodyType(BodyType.DYNAMIC);
        ballPhysics.setFixtureDef(new FixtureDef().restitution(1f).density(0.1f));
        ballPhysics.setOnPhysicsInitialized(() -> ballPhysics.setLinearVelocity(BALL_SPEED, -BALL_SPEED));

        return Entities.builder()
                .at(x - BALL_SIZE / 2.0, y - BALL_SIZE / 2.0)
                .type(GameApp.Types.BALL)
                .bbox(new HitBox("Ball", BoundingShape.circle(BALL_SIZE)))
                .viewFromNode(new Circle(BALL_SIZE, Color.LIGHTCORAL))
                .with(ballPhysics, new CollidableComponent(true))
                .build();
    }

    static Entity newWalls(){
        Entity walls = Entities.makeScreenBounds(100);
        walls.setType(GameApp.Types.WALL);
        walls.addComponent(new CollidableComponent(true));
        return walls;
    }

    static Entity newBrick(double x, double y, Brick brick){
        Color color = Color.RED;
        if(brick.isGlassBrick())
            color = Color.LIGHTBLUE;
        else{
            if(brick.isWoodenBrick())
                color = Color.BROWN;
            else{
                if(brick.isMetalBrick())
                    color = Color.GRAY;
            }
        }

        return Entities.builder()
                .at(x, y)
                .type(GameApp.Types.BRICK)
                .viewFromNodeWithBBox(new Rectangle(BRICK_WIDTH, BRICK_HEIGHT, color))
                .with(new BrickControl(brick), new PhysicsComponent(), new CollidableComponent(true))
                .build();
    }

    static int newX(){
        int x = random.nextInt(600 - BRICK_WIDTH)/BRICK_WIDTH;
        return x * BRICK_WIDTH;
    }

    static int newY(){
        int y = random.nextInt(300 - BRICK_HEIGHT)/BRICK_HEIGHT;
        return y * BRICK_HEIGHT;
    }
}
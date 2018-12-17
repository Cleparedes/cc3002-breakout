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

final class GameFactory implements EntityFactory {

    static Entity newBar(double x, double y, double w, double h){
        PhysicsComponent barPhysics = new PhysicsComponent();
        barPhysics.setBodyType(BodyType.KINEMATIC);

        return Entities.builder()
                .at(x, y)
                .type(GameApp.Types.PLAYER)
                .viewFromNodeWithBBox(new Rectangle(w, h, Color.BLUE))
                .with(barPhysics, new PlayerControl(), new CollidableComponent(true))
                .build();
    }

    static Entity newBackground(){
        return Entities.builder()
                .viewFromNode(new Rectangle(600, 600, Color.BLACK))
                .renderLayer(RenderLayer.BACKGROUND)
                .build();
    }

    static Entity newBall(double x, double y, double r, double v){
        PhysicsComponent ballPhysics = new PhysicsComponent();
        ballPhysics.setBodyType(BodyType.DYNAMIC);
        ballPhysics.setFixtureDef(new FixtureDef().restitution(1f).density(0.1f));
        ballPhysics.setOnPhysicsInitialized(() -> ballPhysics.setLinearVelocity(v, -v));

        return Entities.builder()
                .at(x, y)
                .type(GameApp.Types.BALL)
                .bbox(new HitBox("Ball", BoundingShape.circle(r)))
                .viewFromNode(new Circle(r, Color.LIGHTCORAL))
                .with(ballPhysics, new CollidableComponent(true))
                .build();
    }

    static Entity newWalls(){
        Entity walls = Entities.makeScreenBounds(100);
        walls.setType(GameApp.Types.WALL);
        walls.addComponent(new CollidableComponent(true));
        return walls;
    }
}
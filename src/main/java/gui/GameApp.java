package gui;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.settings.GameSettings;
import javafx.scene.input.KeyCode;

import static gui.GameFactory.*;

public class GameApp extends GameApplication {

    private static final int BAR_WIDTH = 100;
    private static final int BAR_HEIGHT = 30;
    private static final int BALL_SIZE = 10;
    private static final int BALL_SPEED = 5*60;

    public enum Types{
        PLAYER, BALL, WALL
    }

    @Override
    protected void initSettings(GameSettings gameSettings){
        gameSettings.setWidth(600);
        gameSettings.setHeight(600);
        gameSettings.setTitle("Breakout");
        gameSettings.setVersion("0.1");
    }

    @Override
    protected void initGame(){
        Entity background = newBackground();
        Entity bar = newBar(getWidth() / 2.0 - BAR_WIDTH / 2.0, getHeight() * 0.9, BAR_WIDTH, BAR_HEIGHT);
        Entity ball = newBall(getWidth() / 2.0 - BALL_SIZE / 2.0, getHeight() / 2.0 - BALL_SIZE / 2.0, BALL_SIZE, BALL_SPEED);
        Entity walls = newWalls();
        getGameWorld().addEntities(background, bar, ball, walls);
    }

    @Override
    protected void initInput(){
        Input input = getInput();

        input.addAction(new UserAction("Move Right") {
            @Override
            protected void onAction() {
                getGameWorld().getEntitiesByType(Types.PLAYER)
                        .forEach(e -> e.getComponent(PlayerControl.class).right());
            }
        }, KeyCode.D);

        input.addAction(new UserAction("Move Left") {
            @Override
            protected void onAction() {
                getGameWorld().getEntitiesByType(Types.PLAYER)
                        .forEach(e -> e.getComponent(PlayerControl.class).left());
            }
        }, KeyCode.A);
    }

    @Override
    protected void initPhysics(){
        getPhysicsWorld().setGravity(0, 0);

        getPhysicsWorld().addCollisionHandler(new CollisionHandler(Types.BALL, Types.WALL) {
            @Override
            protected void onHitBoxTrigger(Entity ball, Entity wall, HitBox boxBall, HitBox boxWall) {
                if(boxWall.getName().equals("BOT"))
                    ball.removeFromWorld();
            }
        });

        getPhysicsWorld().addCollisionHandler(new CollisionHandler(Types.PLAYER, Types.WALL) {
            @Override
            protected void onHitBoxTrigger(Entity bar, Entity ball, HitBox boxBar, HitBox boxBall) {

            }
        });

        getPhysicsWorld().addCollisionHandler(new CollisionHandler(Types.PLAYER, Types.BALL) {
            @Override
            protected void onHitBoxTrigger(Entity bar, Entity ball, HitBox boxBar, HitBox boxBall) {

            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}

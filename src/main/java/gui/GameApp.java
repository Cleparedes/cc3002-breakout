package gui;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.settings.GameSettings;
import facade.HomeworkTwoFacade;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.Map;

import static gui.GameFactory.*;

public class GameApp extends GameApplication {

    public enum Types{
        PLAYER, BALL, WALL, BRICK
    }

    private HomeworkTwoFacade hw2 = new HomeworkTwoFacade();

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
        Entity bar = newBar(getWidth() / 2.0, getHeight() * 0.9);
        Entity ball = newBall(getWidth() / 2.0, getHeight() / 2.0);
        Entity walls = newWalls();
        hw2.setCurrentLevel(hw2.newLevelWithBricksFull("Nivel 1",10,0.5, 0.5, 0));
        hw2.getBricks().forEach(brick -> getGameWorld().addEntity(newBrick(newX(), newY(), brick)));

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

        getPhysicsWorld().addCollisionHandler(new CollisionHandler(Types.BRICK, Types.BALL) {
            @Override
            protected void onHitBoxTrigger(Entity brick, Entity ball, HitBox boxBrick, HitBox boxBall) {
                brick.getComponent(BrickControl.class).onHit();
            }
        });
    }

    @Override
    protected void initGameVars(Map<String, Object> vars){
        vars.put("name", hw2.getLevelName());
        vars.put("lives", hw2.getBallsLeft());
        vars.put("score", hw2.getCurrentPoints());
    }

    @Override
    protected void initUI(){
        Text name = getUIFactory().newText("", Color.WHITE, 48);
        Text lives = getUIFactory().newText("", Color.WHITE, 22);
        Text score = getUIFactory().newText("", Color.WHITE, 22);

        name.textProperty().bind(getGameState().stringProperty("name"));
        lives.textProperty().bind(getGameState().intProperty("lives").asString());
        score.textProperty().bind(getGameState().intProperty("score").asString());
    }

    public static void main(String[] args) {
        launch(args);
    }
}

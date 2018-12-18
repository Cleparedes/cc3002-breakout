package gui;

import com.almasb.fxgl.app.FXGL;
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
        Entity bar = newBar(getWidth() * 0.5, getHeight() * 0.9);
        Entity ball = newBall(getWidth() * 0.5, getHeight() * 0.8);
        Entity walls = newWalls();

        hw2.setCurrentLevel(hw2.newLevelWithBricksFull("Nivel 1",10,0.5, 0.5, 0));
        PositionHandler ph = new PositionHandler(getWidth(), getHeight());

        hw2.getBricks().forEach(brick -> {
            int[] p = ph.getPosition();
            getGameWorld().addEntity(newBrick(p[0], p[1], brick));
        });

        getGameState().setValue("name", hw2.getLevelName());

        getGameWorld().addEntities(background, bar, ball, walls);
    }

    @Override
    protected void initInput(){
        Input input = getInput();

        input.addAction(new UserAction("Move Right") {
            @Override
            protected void onAction() {
                getGameWorld().getEntitiesByType(Types.PLAYER)
                        .forEach(p -> p.getComponent(PlayerControl.class).right());
            }
        }, KeyCode.D);

        input.addAction(new UserAction("Move Left") {
            @Override
            protected void onAction() {
                getGameWorld().getEntitiesByType(Types.PLAYER)
                        .forEach(p -> p.getComponent(PlayerControl.class).left());
            }
        }, KeyCode.A);

        input.addAction(new UserAction("Reset Ball") {
            @Override
            protected void onAction() {
                getGameWorld().getEntitiesByType(Types.BALL)
                        .forEach(Entity::removeFromWorld);
                Entity ball = newBall(getWidth() * 0.5, getHeight() * 0.8);
                getGameWorld().addEntity(ball);
            }
        }, KeyCode.R);
    }

    @Override
    protected void initPhysics(){
        getPhysicsWorld().setGravity(0, 0);

        getPhysicsWorld().addCollisionHandler(new CollisionHandler(Types.BALL, Types.WALL) {
            @Override
            protected void onHitBoxTrigger(Entity ball, Entity wall, HitBox boxBall, HitBox boxWall) {
                if(boxWall.getName().equals("BOT")) {
                    ball.removeFromWorld();
                    if(hw2.dropBall() > 0) {
                        ball = newBall(getWidth() * 0.5, getHeight() * 0.8);
                        getGameWorld().addEntity(ball);
                    }
                    else {
                        FXGL.getNotificationService().pushNotification("GAME OVER");
                        getGameWorld()
                                .getEntitiesByType(Types.PLAYER)
                                .forEach(Entity::removeFromWorld);
                    }
                    getGameState().setValue("lives", hw2.getBallsLeft());
                }
            }
        });

        getPhysicsWorld().addCollisionHandler(new CollisionHandler(Types.BRICK, Types.BALL) {
            @Override
            protected void onHitBoxTrigger(Entity brick, Entity ball, HitBox boxBrick, HitBox boxBall) {
                brick.getComponent(BrickControl.class).onHit();
                getGameState().setValue("score", hw2.getCurrentPoints());
                getGameState().setValue("name", hw2.getLevelName());
                getGameState().setValue("lives", hw2.getBallsLeft());
                if(hw2.winner()){
                    FXGL.getNotificationService().pushNotification("YOU WON");
                    getGameWorld()
                            .getEntitiesByType(Types.PLAYER)
                            .forEach(Entity::removeFromWorld);
                }
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
        Text name = getUIFactory().newText("", Color.WHITE, 30);
        Text lives = getUIFactory().newText("", Color.WHITE, 20);
        Text score = getUIFactory().newText("", Color.WHITE, 20);

        Text livesT = getUIFactory().newText("Balls: ", Color.WHITE, 20);
        Text scoreT = getUIFactory().newText("Score: ", Color.WHITE, 20);

        name.setTranslateX(250);
        name.setTranslateY(30);

        livesT.setTranslateX(10);
        livesT.setTranslateY(20);
        lives.setTranslateX(80);
        lives.setTranslateY(20);

        scoreT.setTranslateX(450);
        scoreT.setTranslateY(20);
        score.setTranslateX(550);
        score.setTranslateY(20);

        name.textProperty().bind(getGameState().stringProperty("name"));
        lives.textProperty().bind(getGameState().intProperty("lives").asString());
        score.textProperty().bind(getGameState().intProperty("score").asString());

        getGameScene().addUINode(name);
        getGameScene().addUINode(livesT);
        getGameScene().addUINode(lives);
        getGameScene().addUINode(scoreT);
        getGameScene().addUINode(score);
    }

    public static void main(String[] args) {
        launch(args);
    }
}

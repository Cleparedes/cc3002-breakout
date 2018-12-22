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
        hw2 = new HomeworkTwoFacade();

        hw2.setCurrentLevel(hw2.newLevelWithBricksFull("Nivel 1",20,0.5, 0.5, 0));
        hw2.addPlayingLevel(hw2.newLevelWithBricksFull("Nivel 2", 20, 0.8, 1, 0));

        placeEntities();

        getGameState().setValue("name", hw2.getLevelName());
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

        input.addAction(new UserAction("Shoot ball") {
            @Override
            protected void onAction() {
                getGameWorld()
                        .getEntitiesByType(Types.BALL)
                        .forEach(ball -> ball.getComponent(BallControl.class).release());
            }
        }, KeyCode.SPACE);

        input.addAction(new UserAction("Reset Game") {
            @Override
            protected void onAction() {
                getGameWorld().clear();
                initGame();

                getGameState().setValue("name", hw2.getLevelName());
                getGameState().setValue("lives", hw2.getBallsLeft());
                getGameState().setValue("score", hw2.getCurrentPoints());
                getGameState().setValue("levelScore", hw2.getCurrentLevelPoints());
                getGameState().setValue("levelsPlayed", 0);
                getGameState().setValue("remainingLevels", 1);
            }
        }, KeyCode.R);
    }

    @Override
    protected void initPhysics(){
        getPhysicsWorld().setGravity(0, 0);

        getPhysicsWorld().addCollisionHandler(new CollisionHandler(Types.PLAYER, Types.BALL) {
            @Override
            protected void onHitBoxTrigger(Entity bar, Entity ball, HitBox boxBar, HitBox boxBall) {
                ball.getComponent(BallControl.class).onHit();
            }
        });

        getPhysicsWorld().addCollisionHandler(new CollisionHandler(Types.BALL, Types.WALL) {
            @Override
            protected void onHitBoxTrigger(Entity ball, Entity wall, HitBox boxBall, HitBox boxWall) {
                if(boxWall.getName().equals("BOT")) {
                    ball.removeFromWorld();
                    if(hw2.dropBall() > 0)
                        spawnBall();
                    else {
                        FXGL.getNotificationService().pushNotification("GAME OVER");
                        getGameWorld()
                                .getEntitiesByType(Types.PLAYER)
                                .forEach(Entity::removeFromWorld);
                    }
                    getGameState().setValue("lives", hw2.getBallsLeft());
                }
                else{
                    ball.getComponent(BallControl.class).onHit();
                }
            }
        });

        getPhysicsWorld().addCollisionHandler(new CollisionHandler(Types.BRICK, Types.BALL) {
            @Override
            protected void onHitBoxTrigger(Entity brick, Entity ball, HitBox boxBrick, HitBox boxBall) {
                BrickControl brickControl = brick.getComponent(BrickControl.class);

                ball.getComponent(BallControl.class).onHit();

                if(brickControl.isGlassBrick())
                    getAudioPlayer().playSound("hitGlass.mp3"); //Cortar
                /*else{
                    if(brickControl.isWoodenBrick())
                        getAudioPlayer().playSound("hitGlass.mp3"); //hitWood
                    else
                        getAudioPlayer().playSound("hitGlass.mp3"); //hitMetal
                }*/

                boolean newLevel = false;
                if(hw2.getCurrentLevelPoints() + brickControl.getScore() == hw2.getLevelPoints())
                    newLevel = true;

                brick.getComponent(BrickControl.class).onHit();
                getGameState().setValue("score", hw2.getCurrentPoints());
                getGameState().setValue("levelScore", hw2.getCurrentLevelPoints());

                if(brickControl.isMetalBrick())
                    getGameState().setValue("lives", hw2.getBallsLeft());

                if(newLevel) {
                    getGameWorld().clear();
                    placeEntities();
                    getGameState().setValue("name", hw2.getLevelName());
                    getGameState().increment("levelsPlayed", 1);
                    getGameState().increment("remainingLevels", -1);
                }

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
        vars.put("levelScore", hw2.getCurrentLevelPoints());
        vars.put("levelsPlayed", 0);
        vars.put("remainingLevels", 1);
    }

    @Override
    protected void initUI(){
        Text name = getUIFactory().newText("", Color.WHITE, 25);
        Text lives = getUIFactory().newText("", Color.WHITE, 20);
        Text score = getUIFactory().newText("", Color.WHITE, 15);
        Text levelScore = getUIFactory().newText("", Color.WHITE, 20);
        Text levelsPlayed = getUIFactory().newText("", Color.WHITE, 15);
        Text remainingLevels = getUIFactory().newText("", Color.WHITE, 15);

        Text livesT = getUIFactory().newText("Balls: ", Color.WHITE, 20);
        Text scoreT = getUIFactory().newText("Global Score: ", Color.WHITE, 15);
        Text levelScoreT = getUIFactory().newText("Score: ", Color.WHITE, 20);
        Text levelsPlayedT = getUIFactory().newText("Lvl Played: ", Color.WHITE, 15);
        Text remainingLevelsT = getUIFactory().newText("Remaining Lvl: ", Color.WHITE, 15);

        name.setTranslateX(250);
        name.setTranslateY(30);

        livesT.setTranslateX(10);
        livesT.setTranslateY(20);
        lives.setTranslateX(120);
        lives.setTranslateY(20);

        levelsPlayedT.setTranslateX(10);
        levelsPlayedT.setTranslateY(35);
        levelsPlayed.setTranslateX(120);
        levelsPlayed.setTranslateY(35);

        remainingLevelsT.setTranslateX(10);
        remainingLevelsT.setTranslateY(50);
        remainingLevels.setTranslateX(120);
        remainingLevels.setTranslateY(50);

        levelScoreT.setTranslateX(450);
        levelScoreT.setTranslateY(20);
        levelScore.setTranslateX(550);
        levelScore.setTranslateY(20);

        scoreT.setTranslateX(450);
        scoreT.setTranslateY(35);
        score.setTranslateX(550);
        score.setTranslateY(35);


        name.textProperty().bind(getGameState().stringProperty("name"));
        lives.textProperty().bind(getGameState().intProperty("lives").asString());
        levelsPlayed.textProperty().bind(getGameState().intProperty("levelsPlayed").asString());
        remainingLevels.textProperty().bind(getGameState().intProperty("remainingLevels").asString());
        levelScore.textProperty().bind(getGameState().intProperty("levelScore").asString());
        score.textProperty().bind(getGameState().intProperty("score").asString());

        getGameScene().addUINode(name);
        getGameScene().addUINode(livesT);
        getGameScene().addUINode(lives);
        getGameScene().addUINode(levelsPlayedT);
        getGameScene().addUINode(levelsPlayed);
        getGameScene().addUINode(remainingLevelsT);
        getGameScene().addUINode(remainingLevels);
        getGameScene().addUINode(levelScoreT);
        getGameScene().addUINode(levelScore);
        getGameScene().addUINode(scoreT);
        getGameScene().addUINode(score);
    }

    private void spawnBall(){
        final Entity[] bar = new Entity[1];
        getGameWorld().getEntitiesByType(Types.PLAYER).forEach(p -> bar[0] = p);
        Entity ball = newBall(bar[0].getX(), bar[0].getY());
        getGameWorld().addEntity(ball);
    }

    private void placeEntities(){
        Entity background = newBackground();
        Entity walls = newWalls();
        Entity bar = newBar(getWidth() * 0.5, getHeight() * 0.9);
        Entity ball = newBall(bar.getX(), bar.getY());

        getGameWorld().addEntities(background, walls, bar, ball);

        PositionHandler ph = new PositionHandler(getWidth(), getHeight());
        hw2.getBricks().forEach(brick -> {
            int[] p = ph.getPosition();
            getGameWorld().addEntity(newBrick(p[0], p[1], brick));
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}

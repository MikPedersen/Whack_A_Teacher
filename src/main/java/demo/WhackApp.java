package demo;

import com.almasb.fxgl.animation.Interpolators;
import com.almasb.fxgl.app.*;
import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import com.almasb.fxgl.scene.*;
import java.util.Map;

import static com.almasb.fxgl.dsl.FXGL.*;

public  class WhackApp extends GameApplication {

    @Override
    protected void initSettings(GameSettings gameSettings) {
        //set the size of the game based on the background
        //Set the title in the top
        gameSettings.setHeight(768);
        gameSettings.setWidth(1024);
        gameSettings.setTitle("Whack a teacher");
        gameSettings.setVersion("1.0");
        gameSettings.setMenuEnabled(true);
        gameSettings.setSceneFactory(new SceneFactory() {
            @Override
            public FXGLMenu newMainMenu(){
                return new WhackMainMenu();
            }
            @Override
            public FXGLMenu newGameMenu(){
                return new WhackGameMenu();
            }});}

    @Override
    protected void onUpdate(double tpf){
        if (geti("score") >= 3000) {
            //Ends the current game when player reaches 3000 points
            showGameOver();
            //Stops music player since game has ended
            stopMusic();
        }
    }

    @Override
    protected void initGameVars(Map<String, Object> vars) {
        //initialises the score counter
        vars.put("score",0);}

    @Override
    protected void initGame() {
        getSettings().setGlobalMusicVolume(0.1);
        getSettings().setGlobalSoundVolume(0.4);

        getGameWorld().addEntityFactory(new GameEntityFactory());
        spawn("Background");
        spawn("Hammer");

        // creates a timer that runs spawn() parameter is set to decide picture
        run(() -> {
            Entity e = getGameWorld().create("Anders", new SpawnData(
                    FXGLMath.random(200, getAppWidth() - 100), FXGLMath.random(150, getAppHeight() - 200)));
            spawnWithScale(e, Duration.seconds(0.75), Interpolators.BOUNCE.EASE_OUT());
        }, Duration.seconds(3));
        run(() -> {
            Entity e = getGameWorld().create("Andras", new SpawnData(
                    FXGLMath.random(200, getAppWidth() - 100), FXGLMath.random(150, getAppHeight() - 200)));
            spawnWithScale(e, Duration.seconds(0.75), Interpolators.BOUNCE.EASE_OUT());
        }, Duration.seconds(2));
        run(() -> {
            Entity e = getGameWorld().create("Karsten", new SpawnData(
                    FXGLMath.random(200, getAppWidth() - 100), FXGLMath.random(150, getAppHeight() - 200)));
            spawnWithScale(e, Duration.seconds(0.75), Interpolators.BOUNCE.EASE_OUT());
        }, Duration.seconds(6));
        // loop background music located in /resources/assets/music/
        loopBGM("BHT.mp3");
    }

    @Override
    protected void initPhysics() {
        // code in this block is called when there is a collision between Type.HAMMER and teachers
        // remove the collided mole from the game
        // play a sound effect located in /resources/assets/sounds/
        //increases score on hit
        onCollisionBegin(EntityType.HAMMER, EntityType.ANDERS, (hammer, anders) -> {
           anders.removeFromWorld();
            play("ree.wav");
            inc("score", +100); });

        onCollisionBegin(EntityType.HAMMER, EntityType.ANDRAS, (hammer, andras) -> {
            andras.removeFromWorld();
            play("baby.wav");
            inc("score", +50); });

        onCollisionBegin(EntityType.HAMMER, EntityType.KARSTEN, (hammer, karsten) -> {
            karsten.removeFromWorld();
            play("cyka.wav");
            inc("score", +150); });}
    @Override
    protected void initUI() {
        //Adds score text and counter
        addVarText("score",30, 50).setFill(Color.RED);
        addText("Score:", 30, 20).setFill(Color.RED);
    }
    private void showGameOver() {
        //A way to end the game
        getDisplay().showMessageBox("You beat the game. Thanks for playing!", getGameController()::gotoMainMenu);
    }
    public void stopMusic(){
        getAudioPlayer().stopMusic(loopBGM("BHT.mp3"));
    }

    public static void main(String[] args) {
        launch(args);
    }
}

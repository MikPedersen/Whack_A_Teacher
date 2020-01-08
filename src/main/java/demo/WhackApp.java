package demo;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.components.OffscreenCleanComponent;
import com.almasb.fxgl.dsl.components.RandomMoveComponent;
import com.almasb.fxgl.entity.Entity;
import javafx.geometry.Rectangle2D;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import java.util.Map;
import static com.almasb.fxgl.dsl.FXGL.*;

public  class WhackApp extends GameApplication {
     // Types of entities in this game.
    public enum Type {MOLE, HAMMER}

    @Override
    protected void initSettings(GameSettings gameSettings) {
        //set the size of the game based on the background
        //Set the title in the top
        gameSettings.setHeight(768);
        gameSettings.setWidth(1024);
        gameSettings.setTitle("Whack a teacher");
        gameSettings.setVersion("1.0"); }

    @Override
    protected void initGameVars(Map<String, Object> vars) {
        //initialises the score counter
        vars.put("score",0);}

    @Override
    protected void initGame() {
        getSettings().setGlobalMusicVolume(0.1);
        getSettings().setGlobalSoundVolume(0.3);
        spawnBackground();
        spawnHammer();
        // creates a timer that runs spawnMole() parameter is set to decide picture
        run(() -> spawnMole("anders.jpg"), Duration.seconds(2));
        run(() -> spawnMole("andras.jpg"), Duration.seconds(3));
        //since Karsten is very hard to find his spawn is longer
        run(() -> spawnMole("karsten.jpg"), Duration.seconds(6));
        // loop background music located in /resources/assets/music/
        loopBGM("BHT.mp3"); }
    @Override
    protected void initPhysics() {
        onCollisionBegin(Type.HAMMER, Type.MOLE, (hammer, mole) -> {
            // code in this block is called when there is a collision between Type.HAMMER and Type.MOLE
            // remove the collided mole from the game
            mole.removeFromWorld();
            // play a sound effect located in /resources/assets/sounds/
            play("slap.wav");
            //increases score on hit
            inc("score", +100);
        });}
    @Override
    protected void initUI() {
        //Adds score text and counter
        addVarText("score",30, 50).setFill(Color.RED);
        addText("Score:", 30, 20).setFill(Color.RED);
    }
    private void spawnHammer() {
        // build an entity with Type.HAMMER
        // at the position X = getAppWidth() / 2 and Y = getAppHeight() - 200
        // with a view "hammer3.png", which is an image located in /resources/assets/textures/
        // also create a bounding box from that view, make the entity collidable
        // finally, complete building and attach to the game world
        Entity hammer = entityBuilder()
                .type(Type.HAMMER)
                .at(getAppWidth() / 2, getAppHeight() - 200)
                .viewWithBBox("hammer3.jpg")
                .collidable()
                .buildAndAttach();
        // bind hammer's X value to mouse X
        hammer.xProperty().bind(getInput().mouseXWorldProperty());
        // bind hammer's Y value to mouse Y
        hammer.yProperty().bind(getInput().mouseYWorldProperty());
    }
    private void spawnMole(String picture) {
        Entity mole = entityBuilder()
                //Builds a mole of the type picture which is entered when calling the method
                .type(Type.MOLE)
                .at(FXGLMath.random(0, getAppWidth() - 24), FXGLMath.random(0, getAppHeight() - 24))
                .viewWithBBox(picture)
                .with(new RandomMoveComponent(new Rectangle2D(0,0, getAppWidth(), getAppHeight()), 150))
                .with(new OffscreenCleanComponent())
                .collidable()
                .buildAndAttach(); }
    private void spawnBackground() {
        entityBuilder()
                //Creates the background
                .viewWithBBox("background.png")
                .buildAndAttach();
    }
    public static void main(String[] args) {
        launch(args);
    }
}

package demo;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.entity.Entity;
import javafx.beans.value.ObservableValue;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.*;

public  class WhackApp extends GameApplication {
    /**
     * Types of entities in this game.
     */
    public enum Type {
        MOLE, HAMMER
    }


    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setHeight(800);
        gameSettings.setWidth(800);
        gameSettings.setTitle("Whack a teacher");
        gameSettings.setVersion("1.0");
    }

    @Override
    protected void initGame() {
      spawnHammer();

        // creates a timer that runs spawnMole() parameter is set to decide picture

        run(() -> spawnMole("anders.jpg"), Duration.seconds(2));
        run(() -> spawnMole("andras.jpg"), Duration.seconds(3));
        run(() -> spawnMole("karsten.jpg"), Duration.seconds(6));
        // loop background music located in /resources/assets/music/
        loopBGM("BHT.mp3");
    }

    @Override
    protected void initPhysics() {
        onCollisionBegin(Type.HAMMER, Type.MOLE, (hammer, mole) -> {

            // code in this block is called when there is a collision between Type.BUCKET and Type.DROPLET

            // remove the collided droplet from the game
            mole.removeFromWorld();

            // play a sound effect located in /resources/assets/sounds/
            play("slap.wav");
        });
    }

    @Override
    protected void onUpdate(double tpf) {

        // for each entity of Type.DROPLET translate (move) it down
        getGameWorld().getEntitiesByType(Type.MOLE).forEach(mole -> mole.translateY(150 * tpf));
    }

    private void spawnHammer() {
        // build an entity with Type.HAMMER
        // at the position X = getAppWidth() / 2 and Y = getAppHeight() - 200
        // with a view "bucket.png", which is an image located in /resources/assets/textures/
        // also create a bounding box from that view
        // make the entity collidable
        // finally, complete building and attach to the game world

        Entity hammer = entityBuilder()
                .type(Type.HAMMER)
                .at(getAppWidth() / 2, getAppHeight() - 200)
                .viewWithBBox("hammer.jpg")
                .collidable()
                .buildAndAttach();

        // bind hammer's X value to mouse X
        hammer.xProperty().bind(getInput().mouseXWorldProperty());
    }
    private void spawnMole(String picture) {
        entityBuilder()
                .type(Type.MOLE)
                .at(FXGLMath.random(0, getAppWidth() - 64), 0)
                .viewWithBBox(picture)
                .collidable()
                .buildAndAttach();

    }
    public static void main(String[] args) {
        launch(args);
    }
}

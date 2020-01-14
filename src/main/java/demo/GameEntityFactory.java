package demo;

import com.almasb.fxgl.dsl.components.OffscreenCleanComponent;
import com.almasb.fxgl.dsl.components.RandomMoveComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import javafx.geometry.Rectangle2D;

import static com.almasb.fxgl.dsl.FXGL.*;


public class GameEntityFactory implements EntityFactory {

    @Spawns("Hammer")
    public Entity newHammer(SpawnData data){
        // build an entity with Type.HAMMER
        // with a view "hammer3.png", which is an image located in /resources/assets/textures/
        // also create a bounding box from that view, make the entity collidable
        // finally, complete building and attach to the game world
        Entity hammer = entityBuilder()
                .type(EntityType.HAMMER)
                .from(data)
                .at(getAppWidth() / 2, getAppHeight() - 200)
                .viewWithBBox("hammer3.jpg")
                .collidable()
                .build();
        // bind hammer's X value to mouse X
        hammer.xProperty().bind(getInput().mouseXWorldProperty());
        // bind hammer's Y value to mouse Y
        hammer.yProperty().bind(getInput().mouseYWorldProperty());
        return hammer;
    }

    @Spawns("Anders")
    public Entity newAnders(SpawnData data){
        return entityBuilder()
                //Builds an Anders which is entered when calling the method
                .type(EntityType.ANDERS)
                .from(data)
                .viewWithBBox("anders.jpg")
                .with(new RandomMoveComponent(new Rectangle2D(0,0, getAppWidth(), getAppHeight()), 150))
                .with(new OffscreenCleanComponent())
                .collidable()
                .build();
    }
    @Spawns("Andras")
    public Entity newAndras(SpawnData data){
        return entityBuilder()
                //Builds an Andras which is entered when calling the method
                .type(EntityType.ANDRAS)
                .from(data)
                .viewWithBBox("andras.jpg")
                .with(new RandomMoveComponent(new Rectangle2D(0,0, getAppWidth(), getAppHeight()), 150))
                .with(new OffscreenCleanComponent())
                .collidable()
                .build();
    }
    @Spawns("Karsten")
    public Entity newKarsten(SpawnData data){
        return entityBuilder()
                //Builds a Karsten which is entered when calling the method
                .type(EntityType.KARSTEN)
                .from(data)
                .viewWithBBox("karsten.jpg")
                .with(new RandomMoveComponent(new Rectangle2D(0,0, getAppWidth(), getAppHeight()), 150))
                .with(new OffscreenCleanComponent())
                .collidable()
                .build();
    }
    @Spawns("Background")
    public Entity newBackground (SpawnData data){
        return entityBuilder()
                //Creates the background
                .from(data)
                .viewWithBBox("background.png")
                .build();
    }
}

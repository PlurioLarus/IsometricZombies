package com.zombies.game.physics;

import com.zombies.game.entity.Entity;
import com.zombies.main.Game;
import com.zombies.utils.Box;
import com.zombies.utils.IntVector;

import java.util.List;

public class Physics {

    private final Game game;

    public Physics(Game game) {
        this.game = game;
    }

    public boolean overlapBox(Box box, Entity ignoreEntity) {
        for (int x = Math.round(box.getLeft()); x <= Math.round(box.getRight()); x++) {
            for (int y = Math.round(box.getBottom()); y <= Math.round(box.getTop()); y++) {
                List<Entity> entities = game.getTileMap().getEntitiesOnTile(new IntVector(x, y));
                boolean collidesEntity = entities.stream()
                                                 .filter(e -> e.hasCollider() && e.isNotTrigger() && e != ignoreEntity)
                                                 .anyMatch(e -> e.getBox().collidesWith(box));
                //TODO Change this so TileObjects are not required to be collidable.
                boolean collidesTileObject = game.getTileMap().getTile(new IntVector(x, y)).getTileObject() != null;
                if (collidesEntity || collidesTileObject) {
                    return true;
                }
            }
        }
        return false;
    }


}

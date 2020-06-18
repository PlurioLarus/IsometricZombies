package com.zombies.game.entity.behaviours;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.zombies.game.entity.Entity;
import com.zombies.utils.IntVector;
import com.zombies.utils.Vector;

public class CameraFollowBehaviour implements IBehaviour {
    @Override
    public void update(float deltaTime, Entity entity) {
        if (!entity.isLocalPlayer()) return;
        Vector screenPos = entity.getPosition().toScreenCoords();
        //Vector realPos = screenPos.plus(new Vector(0, entity.getTile().getHeight()));
        entity.getGame().setCameraPosition(screenPos);
    }

    @Override
    public void draw(Batch batch, Entity entity) {

    }
}

package com.zombies.game.entity.behaviours;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.zombies.game.entity.Entity;

public class CameraFollowBehaviour implements IBehaviour {
    @Override
    public void update(float deltaTime, Entity entity) {
        if (!entity.isLocalPlayer()) return;
        entity.getGame().setCameraPosition(entity.getPosition().toScreenCoords());
    }

    @Override
    public void draw(Batch batch, Entity entity) {

    }
}

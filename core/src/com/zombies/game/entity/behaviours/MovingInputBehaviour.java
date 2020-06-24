package com.zombies.game.entity.behaviours;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.zombies.events.entitymanager.PlayerMovedEvent;
import com.zombies.game.entity.Entity;
import com.zombies.input.Input;
import com.zombies.utils.Vector;

public class MovingInputBehaviour implements IBehaviour {

    private static final float SPEED = 6;

    @Override
    public void update(float deltaTime, Entity entity) {
        if (!entity.isLocalPlayer()) return;
        Vector movingVector = Vector.zero;
        if (Input.keyDown(Input.Keys.W)) {
            movingVector = movingVector.plus(new Vector(1, 1));
        }
        if (Input.keyDown(Input.Keys.S)) {
            movingVector = movingVector.plus(new Vector(-1, -1));
        }
        if (Input.keyDown(Input.Keys.D)) {
            movingVector = movingVector.plus(new Vector(1, -1));
        }
        if (Input.keyDown(Input.Keys.A)) {
            movingVector = movingVector.plus(new Vector(-1, 1));
        }
        movingVector = movingVector.normalize().times(deltaTime * SPEED);
        if (movingVector.getSquaredLength() == 0) return;
        entity.transformPosition(movingVector);
        entity.getGame().getEntityManager().sendEventToServer(new PlayerMovedEvent(movingVector, entity.getID()));
    }

    @Override
    public void draw(Batch batch, Entity entity) {

    }
}

package com.zombies.game.entity.behaviours;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.zombies.game.entity.Entity;

public interface IBehaviour {

    void update(float deltaTime, Entity entity);

    void draw(Batch batch, Entity entity);

}

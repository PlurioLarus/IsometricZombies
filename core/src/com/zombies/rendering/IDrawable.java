package com.zombies.rendering;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.zombies.utils.Vector;

public interface IDrawable {
    Vector getPosition();

    void draw(Batch batch);
}

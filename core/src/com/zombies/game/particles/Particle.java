package com.zombies.game.particles;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.zombies.rendering.IDrawable;
import com.zombies.rendering.TextureRegistry;
import com.zombies.utils.IntVector;
import com.zombies.utils.Vector;

public abstract class Particle implements IDrawable {

    protected final IntVector tilePos;
    protected IntVector drawingOffset = new IntVector(0, 0);
    protected Vector position;
    protected float height;
    protected String texture;

    public Particle(IntVector tilePos, Vector position, float height, String texture) {
        this.tilePos = tilePos;
        this.position = position;
        this.height = height;
        this.texture = texture;
    }

    @Override
    public Vector getPosition() {
        return null;
    }

    @Override
    public void draw(Batch batch) {
        Vector screen = position.toScreenCoords().plus(new Vector(0, height));
        batch.draw(TextureRegistry.get(texture), screen.x - drawingOffset.x, screen.y - drawingOffset.y);
    }

    public abstract boolean update(float deltaTime);

}

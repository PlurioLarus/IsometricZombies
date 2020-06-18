package com.zombies.game.entity.behaviours;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.zombies.game.entity.Entity;
import com.zombies.rendering.TextureRegistry;
import com.zombies.utils.Vector;

public class SpriteRenderBehaviour implements IBehaviour {
    Texture tex;
    Vector offset;

    public SpriteRenderBehaviour(String imagePath, Vector offset) {
        tex = TextureRegistry.get(imagePath);
        this.offset = offset;
    }

    @Override
    public void update(float deltaTime, Entity entity) {

    }

    @Override
    public void draw(Batch batch, Entity entity) {
        Vector renderPosition = entity.getPosition().toScreenCoords().minus(new Vector(offset.x * tex.getWidth(), offset.y * tex.getHeight()));
        batch.draw(tex, renderPosition.x, renderPosition.y + entity.getTile().getHeight());
    }
}

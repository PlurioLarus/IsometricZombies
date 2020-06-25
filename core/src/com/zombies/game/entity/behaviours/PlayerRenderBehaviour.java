package com.zombies.game.entity.behaviours;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.zombies.game.entity.Entity;
import com.zombies.rendering.TextureRegistry;
import com.zombies.utils.IntVector;
import com.zombies.utils.Vector;

public class PlayerRenderBehaviour implements IBehaviour {
    Texture texture;
    Vector relativeOffset;
    IntVector absoluteOffset;

    public PlayerRenderBehaviour(Vector relativeOffset, IntVector absoluteOffset) {
        texture = TextureRegistry.get("characterBR");
        this.relativeOffset = relativeOffset;
        this.absoluteOffset = absoluteOffset;
    }

    @Override
    public void update(float deltaTime, Entity entity) {
    }

    @Override
    public void draw(Batch batch, Entity entity) {
        switch (entity.getDirection()) {
            case BOTTOM_LEFT:
                texture = TextureRegistry.get("characterBL");
                break;
            case BOTTOM_RIGHT:
                texture = TextureRegistry.get("characterBR");
                break;
            default:
        }

        Vector renderPosition = entity.getPosition()
                                      .toScreenCoords()
                                      .minus(new Vector(relativeOffset.x * texture.getWidth(),
                                                        relativeOffset.y * texture.getHeight()));
        batch.draw(texture,
                   renderPosition.x - absoluteOffset.x,
                   renderPosition.y + entity.getTile().getHeight() - absoluteOffset.y);
    }
}

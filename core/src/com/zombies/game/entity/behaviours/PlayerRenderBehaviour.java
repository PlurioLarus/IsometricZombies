package com.zombies.game.entity.behaviours;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.zombies.game.entity.Entity;
import com.zombies.game.entity.EntityPlayer;
import com.zombies.rendering.TextureRegistry;
import com.zombies.utils.Vector;

public class PlayerRenderBehaviour implements IBehaviour {
    Texture texture;
    Vector offset;

    public PlayerRenderBehaviour(Vector offset) {
        texture = TextureRegistry.get("characterBR");
        this.offset = offset;
    }

    @Override
    public void update(float deltaTime, Entity entity) {
    }

    @Override
    public void draw(Batch batch, Entity entity) {
        switch (entity.getDirection()){
            case BOTTOM_LEFT:   texture = TextureRegistry.get("characterBL");
                                break;
            case BOTTOM_RIGHT:  texture = TextureRegistry.get("characterBR");
                                break;
            default:
        }

        Vector renderPosition = entity.getPosition().toScreenCoords().minus(new Vector(offset.x * texture.getWidth(), offset.y * texture.getHeight()));
        batch.draw(texture, renderPosition.x, renderPosition.y + entity.getTile().getHeight());
    }
}

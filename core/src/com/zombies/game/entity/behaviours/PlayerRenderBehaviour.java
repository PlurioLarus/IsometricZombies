package com.zombies.game.entity.behaviours;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polygon;
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
            case TOP:           texture = TextureRegistry.get("characterT");
                                break;
            case TOP_LEFT:      texture = TextureRegistry.get("characterTL");
                                break;
            case TOP_RIGHT:     texture = TextureRegistry.get("characterTR");
                                break;
            case BOTTOM:        texture = TextureRegistry.get("characterB");
                                break;
            case BOTTOM_LEFT:   texture = TextureRegistry.get("characterBL");
                                break;
            case BOTTOM_RIGHT:  texture = TextureRegistry.get("characterBR");
                                break;
            default:
        }

        Vector renderPosition = entity.getPosition().toScreenCoords().minus(new Vector(offset.x * texture.getWidth(), offset.y * texture.getHeight()));
        batch.draw(texture, renderPosition.x, renderPosition.y + entity.getTile().getHeight());


        Vector move = entity.getLastMove();
        TextureRegion rect = new TextureRegion(TextureRegistry.get("red"));

        int dx = (int) (entity.getPosition().toScreenCoords().x - entity.getLastPosition().toScreenCoords().x);
        int dy = (int) (entity.getPosition().toScreenCoords().y - entity.getLastPosition().toScreenCoords().y);
        //float dist = (float)Math.sqrt(dx*dx + dy*dy);
        float rad = (float)Math.atan2(dy, dx);
        batch.draw(rect, entity.getPosition().toScreenCoords().x, entity.getPosition().toScreenCoords().y+ entity.getTile().getHeight()+8,0,0, 64, 1, 1, 1, (float)(rad*360/(2*Math.PI)));




    }
}

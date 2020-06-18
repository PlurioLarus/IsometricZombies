package com.zombies.game.tile.objects;

import com.badlogic.gdx.graphics.Texture;
import com.zombies.rendering.TextureRegistry;

public abstract class TileObject {
    Texture texture;

    public TileObject(String textureId) {
        this.texture = TextureRegistry.get(textureId);
    }

    public Texture getTexture(){
        return texture;
    }
}

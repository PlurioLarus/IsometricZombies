package com.zombies.game.tile;

import com.badlogic.gdx.graphics.Texture;
import com.zombies.rendering.TextureRegistry;

public abstract class Tile {
    Texture texture;

    public Tile(String texturePath) {
        this.texture = TextureRegistry.get(texturePath);
    }

}

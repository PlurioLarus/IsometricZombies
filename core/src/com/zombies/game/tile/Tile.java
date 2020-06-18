package com.zombies.game.tile;

import com.badlogic.gdx.graphics.Texture;
import com.zombies.rendering.TextureRegistry;

public abstract class Tile {
    Texture texture;
    int height;

    public Tile(String texturePath, int height) {
        this.texture = TextureRegistry.get(texturePath);
        this.height = height;
    }

    public int getHeight() {
        return height;
    }
}

package com.zombies.game.tile;

import com.badlogic.gdx.graphics.Texture;
import com.zombies.game.tile.objects.TileObject;
import com.zombies.rendering.TextureRegistry;

public abstract class Tile {
    Texture texture;
    TileObject tileObject;
    int height;

    public Tile(String texturePath, TileObject tileObject, int height) {
        this.texture = TextureRegistry.get(texturePath);
        this.tileObject = tileObject;
        this.height = height;
    }

    public Tile(String texturePath, TileObject tileObject) {
        this.texture = TextureRegistry.get(texturePath);
        this.tileObject = tileObject;
        this.height = 0;
    }

    public Tile(String texturePath, int height) {
        this.texture = TextureRegistry.get(texturePath);
        this.height = height;
    }

    public Tile(String texturePath) {
        this.texture = TextureRegistry.get(texturePath);
        this.height = 0;
    }

    public int getHeight() {
        return height;
    }

    public TileObject getTileObject(){
        return tileObject;
    }
}

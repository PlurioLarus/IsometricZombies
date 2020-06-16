package com.zombies.game.tile;

import com.badlogic.gdx.graphics.Texture;

public abstract class Tile {
    Texture texture;

    public Tile(String texturePath) {
        this.texture = new Texture(texturePath);
    }

}

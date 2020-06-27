package com.zombies.game.tile.structure;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.zombies.main.Game;
import com.zombies.rendering.TextureRegistry;
import com.zombies.utils.IntVector;

public abstract class Structure {
    final Game game;
    IntVector position;
    IntVector size;
    Texture wall = TextureRegistry.get("wall");
    Component[][] components;

    public Structure(Game game, IntVector position, IntVector size){
        this.game = game;
        this.position = position;
        this.size = size;
        components = new Component[size.x][size.y];
        Component dummy = new Component(game);
        for (int x = 0; x < size.x; x++) {
            for (int y = 0; y < size.y; y++) {
                components[x][y] = dummy;
            }
        }
    }

    public void drawComponent(Batch batch, IntVector position){
        IntVector relPos = position.minus(this.position);
        components[relPos.x][relPos.y].draw(batch, position);
    }
}

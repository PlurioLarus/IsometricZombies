package com.zombies.game.tile.structure;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.zombies.main.Game;
import com.zombies.rendering.TextureRegistry;
import com.zombies.utils.IntVector;


class Component {
    Game game;
    Texture[] walls = new Texture[4];
    public Component(Game game){
        this.game = game;
        walls[1] = TextureRegistry.get("wall");
    }

    public void draw(Batch batch, IntVector position){
        for (int i = 0; i < 4; i++) {
            if (walls[i] != null){
                batch.draw(walls[i], position.toScreenCoords().x, position.toScreenCoords().y - 8 + game.getWorld().getHeight(position)); //+ tiles[x][y].height
                System.out.println(position);
            }
        }
    }
}

package com.zombies.game.tile;

import com.badlogic.gdx.graphics.Texture;
import com.zombies.game.tile.objects.TileObject;
import com.zombies.game.world.World;
import com.zombies.main.Game;
import com.zombies.rendering.TextureRegistry;
import com.zombies.utils.IntVector;

public abstract class Tile {
    Game game;
    Texture texture;
    TileObject tileObject;
    IntVector position;
    int height;


    public Tile(Game game, IntVector position, int height, String texturePath) {
        this.game = game;
        this.position = position;
        this.height = height;
        if(isLowerAsFront()){
            this.texture = TextureRegistry.get("shadowTile");
        }else {
            this.texture = TextureRegistry.get(texturePath);
        }

    }

    public int getHeight() {
        return height;
    }

    public boolean isLowerAsFront(){
        World world = game.getWorld();
        return (world.getHeight(position) < world.getHeight(position.minus(new IntVector(1,1))));
            //|| world.getHeight(position) < world.getHeight(position.minus(new IntVector(1,0)))
            //|| world.getHeight(position) < world.getHeight(position.minus(new IntVector(0,1))));
    }

    public TileObject getTileObject(){
        return tileObject;
    }
}

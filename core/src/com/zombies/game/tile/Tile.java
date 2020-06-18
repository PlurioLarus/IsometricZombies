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
    Texture shadow;
    TileObject tileObject;
    IntVector position;
    int height;


    public Tile(Game game, IntVector position, int height, String textureId) {
        this.game = game;
        this.position = position;
        this.height = height;
        this.texture = TextureRegistry.get(textureId);
        setShadows();

    }

    public int getHeight() {
        return height;
    }

    public void setShadows(){
        World world = game.getWorld();
        boolean rightFrontHigher = world.getHeight(position) < world.getHeight(position.minus(new IntVector(0,1)));
        boolean leftFrontHigher = world.getHeight(position) < world.getHeight(position.minus(new IntVector(1,0)));
        if(leftFrontHigher){
            if(rightFrontHigher){
                shadow = TextureRegistry.get("shadow");
            }else{
                shadow = TextureRegistry.get("leftShadow");
            }
        }else if (rightFrontHigher){
            shadow = TextureRegistry.get("rightShadow");
        }
    }

    public TileObject getTileObject(){
        return tileObject;
    }
}

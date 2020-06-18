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
        int rightFrontHigher = world.getHeight(position.minus(new IntVector(0,1))) - world.getHeight(position);
        int leftFrontHigher = world.getHeight(position.minus(new IntVector(1,0))) - world.getHeight(position);
        int rightShadow = rightFrontHigher > 0 ? (int)Math.min(rightFrontHigher/2.5, 3) : 0;
        int leftShadow = leftFrontHigher > 0 ? (int)Math.min(leftFrontHigher/2.5, 3) : 0;
        System.out.println(rightShadow + " : " +leftShadow);
        shadow = TextureRegistry.get("shadow".concat(Integer.toString(rightShadow)).concat(Integer.toString(leftShadow)));
/*
        if(leftFrontHigher>0){
            if(rightFrontHigher>0){
                shadow = TextureRegistry.get("shadow");
            }else{
                shadow = TextureRegistry.get("leftShadow");
            }
        }else if (rightFrontHigher>0){
            shadow = TextureRegistry.get("rightShadow");
        }*/
    }

    public TileObject getTileObject(){
        return tileObject;
    }
}

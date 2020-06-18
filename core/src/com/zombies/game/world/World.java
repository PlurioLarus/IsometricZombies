package com.zombies.game.world;

import com.zombies.utils.IntVector;
import com.zombies.utils.OpenSimplexNoise;

public class World {
    public OpenSimplexNoise height;
    public OpenSimplexNoise forest;

    public World(){
        height = new OpenSimplexNoise();
    }

    public int getHeight(IntVector tilePosition){
        double randomness = 1.0f/7;
        double scale = 25;
        return (int) (scale *height.eval((float)(tilePosition.x)*randomness,(float)((tilePosition.y))*randomness));
    }
}

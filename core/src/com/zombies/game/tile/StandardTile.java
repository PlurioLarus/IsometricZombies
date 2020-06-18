package com.zombies.game.tile;

import com.zombies.utils.OpenSimplexNoise;

public class StandardTile extends Tile {
    OpenSimplexNoise n = new OpenSimplexNoise();
    public StandardTile(int x, int y) {
        super(new OpenSimplexNoise().eval((float)x/10,(float)y/10) > 0 ? "fancyTile" : "fancyOtherTile");
    }
}

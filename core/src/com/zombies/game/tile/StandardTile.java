package com.zombies.game.tile;

import com.zombies.game.tile.objects.TileObject;
import com.zombies.game.tile.objects.TileObjectTree;
import com.zombies.utils.OpenSimplexNoise;

public class StandardTile extends Tile {
    OpenSimplexNoise n = new OpenSimplexNoise();
    public StandardTile(int x, int y, int height) {
        super("fancyTile", height);
        //super(new OpenSimplexNoise().eval((float)x/20,(float)y/20) > 0 ? "fancyTile" : "fancyOtherTile", height);
        if (new OpenSimplexNoise(516735132).eval((float)x/20,(float)y/20) > 0.25
                && Math.random() > 0.5){
            this.tileObject = new TileObjectTree();
        }
    }
}

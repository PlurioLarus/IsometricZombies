package com.zombies.game.tile;

import com.zombies.game.tile.objects.TileObjectTree;
import com.zombies.game.tile.structure.House;
import com.zombies.game.tile.structure.Structure;
import com.zombies.main.Game;
import com.zombies.utils.IntVector;
import com.zombies.utils.OpenSimplexNoise;

public class StandardTile extends Tile {
    OpenSimplexNoise n = new OpenSimplexNoise();

    public StandardTile(Game game, IntVector position, int height) {
        super(game, position, height, "fancyTile");

        //super(new OpenSimplexNoise().eval((float)x/20,(float)y/20) > 0 ? "fancyTile" : "fancyOtherTile", height);
        if (new OpenSimplexNoise(516735132).eval((float) position.x / 20, (float) position.y / 20) > 0.25
                && n.eval((float) position.x * 200, (float) position.y * 200) > 0.5) {
            this.tileObject = new TileObjectTree();
        }
        if (position.x >= 0 && position.x < 5 && position.y==0){
            this.structure = new House(game, position, new IntVector(5,1));
        }
    }
}

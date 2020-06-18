package com.zombies.game.tile;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.zombies.utils.IntVector;

public class Chunk {

    public final IntVector chunkPosition;

    public final Tile[][] tiles;

    public Chunk(IntVector chunkPosition) {
        this.chunkPosition = chunkPosition;
        this.tiles = new Tile[32][32];
        for (int x = 0; x < this.tiles.length; x++) {
            for (int y = 0; y < this.tiles[x].length; y++) {
                this.tiles[x][y] = new StandardTile(32*chunkPosition.x + x, 32*chunkPosition.y + y);
            }
        }
    }

    public void draw(Batch batch) {
        for (int x = 0; x < tiles.length; x++) {
            for (int y = 0; y < this.tiles[x].length; y++) {
                IntVector position = chunkPosition.times(32).plus(new IntVector(x, y)).toScreenCoords();
                batch.draw(this.tiles[x][y].texture, position.x - 16, position.y - 16);
            }
        }
    }
}

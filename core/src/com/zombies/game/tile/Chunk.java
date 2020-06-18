package com.zombies.game.tile;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.zombies.utils.IntVector;
import com.zombies.utils.OpenSimplexNoise;

public class Chunk {

    public final IntVector chunkPosition;

    public final Tile[][] tiles;

    public Chunk(IntVector chunkPosition) {
        this.chunkPosition = chunkPosition;
        this.tiles = new Tile[32][32];
        for (int x = 0; x < this.tiles.length; x++) {
            for (int y = 0; y < this.tiles[x].length; y++) {
                double randomness = 1.0f/7;
                double scale = 25;
                int height = (int) (scale * new OpenSimplexNoise().eval((float)((chunkPosition.x*32+x))*randomness,(float)((chunkPosition.y*32+y))*randomness));
                this.tiles[x][y] = new StandardTile(32*chunkPosition.x + x, 32*chunkPosition.y + y,height);
            }
        }
    }

    public Tile getTile(IntVector relativeTilePos){
        return tiles[relativeTilePos.x][relativeTilePos.y];
    }

    public void draw(Batch batch) {
        //for (int x = 0; x < tiles.length; x++) {
        for (int x = tiles.length-1; x >=0 ; x--) {
            //for (int y = 0; y < this.tiles[x].length; y++) {
            for (int y = this.tiles[x].length-1; y >=0 ; y--) {
                IntVector position = chunkPosition.times(32).plus(new IntVector(x, y)).toScreenCoords();
                batch.draw(this.tiles[x][y].texture, position.x - 16, position.y - 24 + tiles[x][y].height);
            }
        }
    }
}

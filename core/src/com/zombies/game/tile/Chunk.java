package com.zombies.game.tile;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.zombies.game.entity.Entity;
import com.zombies.game.tile.objects.TileObject;
import com.zombies.main.Game;
import com.zombies.utils.IntVector;
import com.zombies.utils.OpenSimplexNoise;

import java.util.List;
import java.util.stream.Collectors;

public class Chunk {
    private final Game game;
    public final IntVector chunkPosition;

    public final Tile[][] tiles;

    public Chunk(Game game, IntVector chunkPosition) {
        this.game = game;
        this.chunkPosition = chunkPosition;
        this.tiles = new Tile[32][32];
        for (int x = 0; x < this.tiles.length; x++) {
            for (int y = 0; y < this.tiles[x].length; y++) {
                int height = game.getWorld().getHeight(chunkPosition.times(32).plus(new IntVector(x,y)));
                this.tiles[x][y] = new StandardTile(game,new IntVector(32*chunkPosition.x + x, 32*chunkPosition.y + y),height);
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

                if (this.tiles[x][y].shadow != null) {
                    batch.draw(this.tiles[x][y].shadow, position.x - 16, position.y - 24 + tiles[x][y].height);
                }

                TileObject tileObject = this.tiles[x][y].getTileObject();
                if (tileObject != null){
                    batch.draw(tileObject.getTexture(), position.x - 16, position.y - 8 + tiles[x][y].height);
                }

                tiles[x][y].getEntities().forEach(e-> e.draw(batch));
            }
        }
    }
}

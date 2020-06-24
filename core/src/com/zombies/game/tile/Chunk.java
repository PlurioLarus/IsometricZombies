package com.zombies.game.tile;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.zombies.game.entity.Entity;
import com.zombies.game.tile.objects.TileObject;
import com.zombies.main.Game;
import com.zombies.main.IsometricZombies;
import com.zombies.utils.IntVector;

import java.util.ArrayList;
import java.util.List;

public class Chunk {
    private final Game game;
    public final IntVector chunkPosition;
    //private final List<Entity> chunkEntities;
    private final List<Entity>[][] chunkEntities;

    public final Tile[][] tiles;

    public Chunk(Game game, IntVector chunkPosition) {
        this.game = game;
        this.chunkPosition = chunkPosition;
        this.tiles = new Tile[IsometricZombies.CHUNK_SIZE][IsometricZombies.CHUNK_SIZE];
        for (int x = 0; x < this.tiles.length; x++) {
            for (int y = 0; y < this.tiles[x].length; y++) {
                int height = game.getWorld()
                                 .getHeight(chunkPosition.times(IsometricZombies.CHUNK_SIZE).plus(new IntVector(x, y)));
                this.tiles[x][y] = new StandardTile(game,
                                                    new IntVector(IsometricZombies.CHUNK_SIZE * chunkPosition.x + x,
                                                                  IsometricZombies.CHUNK_SIZE * chunkPosition.y + y),
                                                    height);
            }
        }
        chunkEntities = new List[IsometricZombies.CHUNK_SIZE][IsometricZombies.CHUNK_SIZE];
        for (int x = 0; x < this.tiles.length; x++) {
            for (int y = 0; y < this.tiles[x].length; y++) {
                chunkEntities[x][y] = new ArrayList<>();
            }
        }
    }

    public Tile getTile(IntVector relativeTilePos) {
        return tiles[relativeTilePos.x][relativeTilePos.y];
    }

    /**
     * @param entity
     * @param position Position in chunk space
     */
    public void addEntity(Entity entity, IntVector position) {
        chunkEntities[position.x][position.y].add(entity);
    }

    /**
     * @param entity
     * @param position Position in chunk space
     */
    public void removeEntity(Entity entity, IntVector position) {
        chunkEntities[position.x][position.y].remove(entity);
    }

    public void draw(Batch batch) {
        //game.getLogger().printInfo("Entities on " + chunkPosition + ":" + chunkEntities.size());
        //for (int x = 0; x < tiles.length; x++) {

        for (int x = tiles.length - 1; x >= 0; x--) {
            //for (int y = 0; y < this.tiles[x].length; y++) {
            for (int y = this.tiles[x].length - 1; y >= 0; y--) {
                IntVector position = chunkPosition.times(IsometricZombies.CHUNK_SIZE)
                                                  .plus(new IntVector(x, y))
                                                  .toScreenCoords();

                batch.draw(this.tiles[x][y].texture, position.x - 16, position.y - 24 + tiles[x][y].height);

                if (this.tiles[x][y].shadow != null) {
                    batch.draw(this.tiles[x][y].shadow, position.x - 16, position.y - 24 + tiles[x][y].height);
                }

                TileObject tileObject = this.tiles[x][y].getTileObject();
                if (tileObject != null) {
                    batch.draw(tileObject.getTexture(), position.x - 16, position.y - 8 + tiles[x][y].height);
                }
                int finalX = x;
                int finalY = y;
                chunkEntities[x][y].stream()
                                   .filter(e -> e.getPosition()
                                                 .roundToIntVector()
                                                 .toChunkOffset()
                                                 .equals(new IntVector(finalX, finalY)))
                                   .forEach(e -> e.draw(batch));
            }
        }
    }

    public List<Entity> getEntitiesOnTile(IntVector position) {
        return chunkEntities[position.x][position.y];
    }
}

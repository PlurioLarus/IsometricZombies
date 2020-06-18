package com.zombies.game.tile;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.zombies.main.Game;
import com.zombies.networking.NetworkedManager;

import java.util.*;

public class TileMap extends NetworkedManager implements ITileMap {

    public static final int NET_ID = 1000000;

    final Game game;

    final Map<IntVector,Chunk> loadedChunks = new HashMap<>();
    final List<Entity> loadedEntities = new ArrayList<>();

    public TileMap(Game game) {
        super(game, NET_ID);
        this.game = game;
    }

    public Map<IntVector,Chunk> getLoadedChunks(){
        return loadedChunks;
    }

    public Tile getTile(Vector position){
        return getTile(position.roundToIntVector());
    }

    public Tile getTile(IntVector position){
        IntVector chunkPosition = position.toChunkPos();
        Chunk chunk = getChunk(chunkPosition);
        if (chunk == null){
            //TODO auf jedenfall fixen
            return new StandardTile(0,0,0);
        }else{
            return chunk.getTile(position.minus(chunkPosition.times(32)));
        }
    }

    public Chunk getChunk(IntVector chunkPosition){
        return loadedChunks.get(chunkPosition);
    }


    //Entity Stuff
    public void spawnEntity(Entity entity, Vector position) {
        if (loadedEntities.contains(entity)) {
            //Maybe Throw an exception here
            return;
        }
        loadedEntities.add(entity);
        entity.setPosition(position);
    }

    @Override
    public void act(float delta) {
        loadedEntities.forEach(e -> e.update(delta));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        List<IntVector> chunkPositions = new ArrayList<>(loadedChunks.keySet());
        chunkPositions.sort(Comparator.comparingInt(a -> -a.toScreenCoords().y));
        chunkPositions.forEach(e -> loadedChunks.get(e).draw(batch));
        loadedEntities.forEach(e -> e.draw(batch));
    }

    @Override
    protected void handleEvent(Object event) {

    }

    @Override
    public void update(float deltaTime) {
    }


}

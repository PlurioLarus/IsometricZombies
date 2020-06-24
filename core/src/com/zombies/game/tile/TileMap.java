package com.zombies.game.tile;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.zombies.events.PlayerConnectedEvent;
import com.zombies.events.tilemap.ChunkEvent;
import com.zombies.game.entity.Entity;
import com.zombies.main.Game;
import com.zombies.networking.NetworkedManager;
import com.zombies.utils.IntVector;
import com.zombies.utils.Vector;

import java.util.*;

import static com.zombies.main.IsometricZombies.CHUNK_SIZE;


public class TileMap extends NetworkedManager implements ITileMap {

    public static final int NET_ID = 1000000;

    final Game game;

    final Map<IntVector, Chunk> loadedChunks = new HashMap<>();

    public TileMap(Game game) {
        super(game, NET_ID);
        this.game = game;
    }

    public void loadChunk(IntVector chunkPosition) {
        if (game.getNetworking().isServer()) {
            loadedChunks.put(chunkPosition, new Chunk(game, chunkPosition));
            sendEventToClients(new ChunkEvent(chunkPosition, true));
        }
    }

    public void unloadChunk(IntVector chunkPos) {
        if (game.getNetworking().isServer()) {
            loadedChunks.remove(chunkPos);
            sendEventToClients(new ChunkEvent(chunkPos, false));
        }
    }

    public Map<IntVector, Chunk> getLoadedChunks() {
        return loadedChunks;
    }

    public Tile getTile(Vector position) {
        return getTile(position.roundToIntVector());
    }

    public Tile getTile(IntVector position) {
        IntVector chunkPosition = position.toChunkPos();
        Chunk chunk = getChunk(chunkPosition);
        if (chunk == null) {
            //TODO auf jedenfall fixen
            return new StandardTile(game, new IntVector(0, 0), 0);
        } else {
            return chunk.getTile(position.minus(chunkPosition.times(CHUNK_SIZE)));
        }
    }

    public Chunk getChunk(IntVector chunkPosition) {
        return loadedChunks.get(chunkPosition);
    }

    public void addEntityToTile(Entity entity, IntVector vector) {
        IntVector chunk = vector.toChunkPos();
        getChunk(chunk).addEntity(entity, vector.minus(chunk.times(CHUNK_SIZE)));
    }

    public void removeEntityFromTile(Entity entity, IntVector position) {
        IntVector chunk = position.toChunkPos();
        getChunk(chunk).removeEntity(entity, position.minus(chunk.times(CHUNK_SIZE)));
    }

    @Override
    public void draw(Batch batch) {
        List<IntVector> chunkPositions = new ArrayList<>(loadedChunks.keySet());
        chunkPositions.sort(Comparator.comparingInt(a -> -a.toScreenCoords().y));
        chunkPositions.forEach(e -> loadedChunks.get(e).draw(batch));
    }

    @Override
    protected void handleServerEvent(Object event) {
        if (event instanceof PlayerConnectedEvent) {
            handlePlayerConnectedEvent((PlayerConnectedEvent) event);
        }
    }

    @Override
    protected void handleClientEvent(Object event) {
        if (event instanceof ChunkEvent) {
            handleChunkEvent((ChunkEvent) event);
        }
    }

    private void handlePlayerConnectedEvent(PlayerConnectedEvent event) {
        loadedChunks.forEach((a, b) -> sendEventToClient(new ChunkEvent(a, true), event.connection));
    }

    private void handleChunkEvent(ChunkEvent event) {
        if (event.loadedEvent) {
            loadedChunks.put(event.chunkPosition, new Chunk(game, event.chunkPosition));
        } else {
            loadedChunks.remove(event.chunkPosition);
        }

    }

    @Override
    public void update(float deltaTime) {
    }


    @Override
    public void rpcLoadChunk(IntVector position) {

    }


    public List<Entity> getEntitiesOnTile(IntVector tilePos) {
        IntVector chunk = tilePos.toChunkPos();
        return getChunk(chunk).getEntitiesOnTile(tilePos.minus(chunk.times(CHUNK_SIZE)));
    }
}

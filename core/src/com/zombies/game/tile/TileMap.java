package com.zombies.game.tile;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.zombies.events.PlayerConnectedEvent;
import com.zombies.events.tilemap.ChunkLoadedEvent;
import com.zombies.main.Game;
import com.zombies.main.IsometricZombies;
import com.zombies.networking.NetworkedManager;
import com.zombies.utils.IntVector;
import com.zombies.utils.Vector;

import java.util.*;


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
            System.out.println("[SERVER] Loaded Chunk " + chunkPosition);
            loadedChunks.put(chunkPosition, new Chunk(game, chunkPosition));
            sendEventToClients(new ChunkLoadedEvent(chunkPosition));
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
            return chunk.getTile(position.minus(chunkPosition.times(IsometricZombies.CHUNK_SIZE)));
        }
    }

    public Chunk getChunk(IntVector chunkPosition) {
        return loadedChunks.get(chunkPosition);
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
        if (event instanceof ChunkLoadedEvent) {
            handleChunkLoadedEvent((ChunkLoadedEvent) event);
        }
    }

    private void handlePlayerConnectedEvent(PlayerConnectedEvent event) {
        loadedChunks.forEach((a, b) -> sendEventToClient(new ChunkLoadedEvent(a), event.connection));
    }

    private void handleChunkLoadedEvent(ChunkLoadedEvent event) {
        System.out.println("[CLIENT] Loaded Chunk " + event.chunkPosition);
        loadedChunks.put(event.chunkPosition, new Chunk(game, event.chunkPosition));
    }

    @Override
    public void update(float deltaTime) {
    }


    @Override
    public void rpcLoadChunk(IntVector position) {

    }
}

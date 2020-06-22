package com.zombies.game.tile;

import com.zombies.game.entity.EntityPlayer;
import com.zombies.game.player.Player;
import com.zombies.main.Game;
import com.zombies.utils.IntVector;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChunkLoader {
    final Game game;
    final TileMap tileMap;
    final Map<IntVector, Chunk> loadedChunks;
    final Map<IntVector, Integer> playerRequireChunk = new HashMap<>();


    public ChunkLoader(Game game) {
        this.game = game;
        this.tileMap = game.getTileMap();
        this.loadedChunks = tileMap.getLoadedChunks();
    }

    public void loadChunk(IntVector chunkPos) {
        if (!loadedChunks.containsKey(chunkPos)) {
            game.getTileMap().loadChunk(chunkPos);
            //loadedChunks.sort(Comparator.comparingInt(a -> -a.chunkPosition.toScreenCoords().y));
        } else {
            //throw new ChunkAlreadyLoadedException();
        }
    }

    public void unloadChunk(IntVector chunkPos) {
        if (loadedChunks.containsKey(chunkPos)) {
            game.getTileMap().unloadChunk(chunkPos);
            //loadedChunks.sort(Comparator.comparingInt(a -> -a.chunkPosition.toScreenCoords().y));
        } else {
            //throw new ChunkAlreadyLoadedException();
        }
    }

    public void loadSurroundingChunks(IntVector centerPos) {
        for (int x = centerPos.x - 1; x <= centerPos.x + 1; x++) {
            for (int y = centerPos.y - 1; y <= centerPos.y + 1; y++) {
                IntVector chunkPos = new IntVector(x, y);
                Integer newValue = playerRequireChunk.computeIfPresent(chunkPos, (pos, value) -> value + 1);
                if (newValue == null) {
                    playerRequireChunk.put(chunkPos, 1);
                    loadChunk(chunkPos);
                }

            }
        }
    }

    public void unloadSurroundingChunks(IntVector centerPos) {
        for (int x = centerPos.x - 1; x <= centerPos.x + 1; x++) {
            for (int y = centerPos.y - 1; y <= centerPos.y + 1; y++) {
                IntVector chunkPos = new IntVector(x, y);
                Integer newValue = playerRequireChunk.computeIfPresent(chunkPos, (pos, value) -> value - 1);
                if (newValue == null || newValue == 0) {
                    playerRequireChunk.remove(chunkPos);
                    unloadChunk(chunkPos);
                }

            }
        }
    }

    public void fixedUpdate() {
        if (game.getNetworking().isClient()) return;
        List<Player> players = game.getPlayerManager().getPlayers();
        for (Player player : players) {
            EntityPlayer p = player.getEntity();
            IntVector playerChunk = p.getPosition().toChunkPos();
            IntVector lastChunk = p.getLastFixedPosition().toChunkPos();
            int deltaX = playerChunk.x - lastChunk.x;
            int deltaY = playerChunk.y - lastChunk.y;
            if (deltaX != 0 || deltaY != 0) {
                loadSurroundingChunks(playerChunk);
                unloadSurroundingChunks(lastChunk);
            }
        }
    }
}

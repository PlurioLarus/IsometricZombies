package com.zombies.game.tile;

import com.zombies.exceptions.ChunkAlreadyLoadedException;
import com.zombies.game.entity.Entity;
import com.zombies.game.entity.EntityPlayer;
import com.zombies.main.Game;
import com.zombies.utils.IntVector;
import com.zombies.utils.Vector;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChunkLoader {
    final Game game;
    final TileMap tileMap;
    final List<Chunk> loadedChunks;
    final Map<IntVector,Boolean> loadedChunksLog;


    public ChunkLoader(Game game){
         this.game = game;
         this.tileMap = game.getTileMap();
         this.loadedChunks = tileMap.getLoadedChunks();
         this.loadedChunksLog = new HashMap<>();
    }

    public void loadChunk(IntVector chunkPos) {
        if (!loadedChunksLog.containsKey(chunkPos)) {
        loadedChunks.add(new Chunk(chunkPos));
        loadedChunksLog.put(chunkPos, true);
        loadedChunks.sort(Comparator.comparingInt(a -> a.chunkPosition.toScreenCoords().y));
        }else{
            throw new ChunkAlreadyLoadedException();
        }
    }

    public void loadSurroundingChunks(IntVector centerPos){
        for (int x=centerPos.x-1; x<=centerPos.x+1;x++){
            for (int y=centerPos.y-1; y<=centerPos.y+1;y++){
                loadChunk(new IntVector(x,y));
            }
        }
    }

    public void update(){
        List<EntityPlayer> players = game.getPlayers();
        for (EntityPlayer p:players) {
            IntVector playerChunk = p.getPosition().toChunkPos();
            int deltaX = playerChunk.x - p.getLastPosition().toChunkPos().x;
            int deltaY = playerChunk.y - p.getLastPosition().toChunkPos().y;
            if (deltaX > 0){
                loadChunk(new IntVector(playerChunk.x+1, playerChunk.y-1));
                loadChunk(new IntVector(playerChunk.x+1, playerChunk.y));
                loadChunk(new IntVector(playerChunk.x+1, playerChunk.y+1));
            }else if (deltaX < 0){
                loadChunk(new IntVector(playerChunk.x-1, playerChunk.y-1));
                loadChunk(new IntVector(playerChunk.x-1, playerChunk.y));
                loadChunk(new IntVector(playerChunk.x-1, playerChunk.y+1));
            }
            if(deltaY>0){
                loadChunk(new IntVector(playerChunk.x-1, playerChunk.y+1));
                loadChunk(new IntVector(playerChunk.x, playerChunk.y+1));
                loadChunk(new IntVector(playerChunk.x+1, playerChunk.y+1));
            }else if(deltaY<0){
                loadChunk(new IntVector(playerChunk.x-1, playerChunk.y-1));
                loadChunk(new IntVector(playerChunk.x, playerChunk.y-1));
                loadChunk(new IntVector(playerChunk.x+1, playerChunk.y-1));
            }
        }
    }
}

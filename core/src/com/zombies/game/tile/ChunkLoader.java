package com.zombies.game.tile;

import com.zombies.exceptions.ChunkAlreadyLoadedException;
import com.zombies.game.entity.Entity;
import com.zombies.game.entity.EntityPlayer;
import com.zombies.main.Game;
import com.zombies.utils.IntVector;

import java.util.Comparator;
import java.util.List;

public class ChunkLoader {
    final Game game;
    final TileMap tileMap;
    final List<Chunk> loadedChunks;


    public ChunkLoader(Game game){
         this.game = game;
         this.tileMap = game.getTileMap();
         this.loadedChunks = tileMap.getLoadedChunks();
    }

    public void loadChunk(IntVector chunkPos) {
        if (loadedChunks.stream().anyMatch(e -> e.chunkPosition.equals(chunkPos))) {
            //throw new ChunkAlreadyLoadedException();
        }else{
        loadedChunks.add(new Chunk(chunkPos));
        loadedChunks.sort(Comparator.comparingInt(a -> a.chunkPosition.toScreenCoords().y));}
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
            if (p.hasChangedChunk()){
                loadSurroundingChunks(p.getPosition().toChunkPos());
            }
        }
    }
}

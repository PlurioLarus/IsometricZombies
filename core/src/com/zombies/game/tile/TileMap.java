package com.zombies.game.tile;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.zombies.utils.IntVector;

import java.util.ArrayList;
import java.util.List;

public class TileMap extends Actor {

    List<Chunk> loadedChunks = new ArrayList<>();

    public void loadChunk(IntVector chunkPos) {
        if (loadedChunks.stream().anyMatch(e -> e.chunkPosition.equals(chunkPos))) {
            System.err.println("DON'T DO SIS");
            return;
        }
        loadedChunks.add(new Chunk(chunkPos));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        loadedChunks.forEach(e -> e.draw(batch));
    }
}

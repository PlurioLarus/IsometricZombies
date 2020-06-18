package com.zombies.events.tilemap;

import com.zombies.utils.IntVector;

public class ChunkLoadedEvent {
    public final IntVector chunkPosition;

    public ChunkLoadedEvent(IntVector chunkPosition) {
        this.chunkPosition = chunkPosition;
    }

    public ChunkLoadedEvent() {
        this.chunkPosition = null;
    }
}

package com.zombies.events.tilemap;

import com.zombies.utils.IntVector;

public class ChunkEvent {
    public final IntVector chunkPosition;
    public final boolean loadedEvent;

    public ChunkEvent(IntVector chunkPosition, boolean loadedEvent) {
        this.chunkPosition = chunkPosition;
        this.loadedEvent = loadedEvent;
    }

    public ChunkEvent() {
        this.chunkPosition = null;
        loadedEvent = false;
    }
}

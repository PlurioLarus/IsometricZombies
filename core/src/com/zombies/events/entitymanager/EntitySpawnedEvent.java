package com.zombies.events.entitymanager;

import com.zombies.utils.Vector;

public class EntitySpawnedEvent {
    public final int networkId;
    public final String entityIdentifier;
    public final Vector spawnPosition;

    public EntitySpawnedEvent() {
        this.networkId = 0;
        this.entityIdentifier = null;
        this.spawnPosition = null;
    }

    public EntitySpawnedEvent(int networkId, String entityIdentifier, Vector spawnPosition) {
        this.networkId = networkId;
        this.entityIdentifier = entityIdentifier;
        this.spawnPosition = spawnPosition;
    }
}

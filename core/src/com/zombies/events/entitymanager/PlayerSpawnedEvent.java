package com.zombies.events.entitymanager;

public class PlayerSpawnedEvent {
    public final int networkId;
    public final int localPlayerId;

    public PlayerSpawnedEvent(int networkId, int localPlayerId) {
        this.networkId = networkId;
        this.localPlayerId = localPlayerId;
    }

    public PlayerSpawnedEvent() {
        this.networkId = 0;
        this.localPlayerId = 0;
    }
}

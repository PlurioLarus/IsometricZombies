package com.zombies.events.entitymanager;

public class EntityDespawnedEvent {
    public final int networkId;

    public EntityDespawnedEvent(int networkId) {
        this.networkId = networkId;
    }

    public EntityDespawnedEvent() {
        this.networkId = 0;
    }
}

package com.zombies.events.entitymanager;

import com.zombies.utils.Vector;

public class PlayerMovedEvent {
    public final Vector deltaPosition;
    public final int entityPlayerNetId;

    public PlayerMovedEvent(Vector deltaPosition, int entityPlayerNetId) {
        this.deltaPosition = deltaPosition;
        this.entityPlayerNetId = entityPlayerNetId;
    }

    public PlayerMovedEvent() {
        this.deltaPosition = null;
        this.entityPlayerNetId = 0;
    }
}

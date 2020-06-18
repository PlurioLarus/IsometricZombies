package com.zombies.events;

import com.esotericsoftware.kryonet.Connection;

public class PlayerDisconnectedEvent {
    public final Connection connection;

    public PlayerDisconnectedEvent(Connection connection) {
        this.connection = connection;
    }
}

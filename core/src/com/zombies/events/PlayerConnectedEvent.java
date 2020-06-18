package com.zombies.events;

import com.esotericsoftware.kryonet.Connection;

public class PlayerConnectedEvent {
    public final Connection connection;

    public PlayerConnectedEvent(Connection connection) {
        this.connection = connection;
    }
}

package com.zombies.game.player;

import com.esotericsoftware.kryonet.Connection;
import com.zombies.game.entity.EntityPlayer;

public class Player {

    public final Connection connection;
    public final EntityPlayer entity;

    public Player(Connection connection, EntityPlayer entity) {
        this.connection = connection;
        this.entity = entity;
    }

    public EntityPlayer getEntity() {
        return entity;
    }
}

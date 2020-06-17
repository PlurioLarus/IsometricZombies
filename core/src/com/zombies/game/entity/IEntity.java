package com.zombies.game.entity;

import com.zombies.utils.Vector;

public interface IEntity {
    void cmdSetPosition(Vector vector);

    void rpcSetPosition(Vector vector);
}

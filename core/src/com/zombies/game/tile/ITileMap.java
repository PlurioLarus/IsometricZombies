package com.zombies.game.tile;

import com.zombies.game.entity.Entity;
import com.zombies.utils.Vector;

public interface ITileMap {

    void cmdSpawnEntity(Entity entity, Vector position);

    void rpcSpawnEntity(int id, Entity entity);

    void rpcSpawnPlayer(int id, int localPlayer);

}

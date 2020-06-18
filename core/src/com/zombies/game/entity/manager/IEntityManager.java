package com.zombies.game.entity.manager;

public interface IEntityManager {

    void rpcSpawnEntity(int netId, String entityId);

    void rpcSpawnPlayer(int id, int localPlayer);
}

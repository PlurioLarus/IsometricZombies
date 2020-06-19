package com.zombies.game.entity.manager;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.zombies.events.PlayerConnectedEvent;
import com.zombies.events.entitymanager.PlayerMovedEvent;
import com.zombies.game.entity.Entity;
import com.zombies.game.entity.EntityPlayer;
import com.zombies.game.entity.EntityRegistry;
import com.zombies.main.Game;
import com.zombies.networking.NetworkedManager;

import java.util.ArrayList;
import java.util.List;

public class EntityManager extends NetworkedManager implements IEntityManager {

    private static final int NET_ID = 230697;

    final List<Entity> loadedEntities = new ArrayList<>();

    public EntityManager(Game game) {
        super(game, NET_ID);
    }

    public List<Entity> getLoadedEntities() {
        return loadedEntities;
    }

    public EntityPlayer spawnPlayer(int localPlayer) {
        System.out.println("[SERVER] Spawn Player");
        List<IEntityManager> entityManagers = game.getNetworking().getClientRemoteObjects(NET_ID, IEntityManager.class);
        int netID = game.getNetworking().getNextRemoteObjectIndex();
        entityManagers.forEach(t -> t.rpcSpawnPlayer(netID, localPlayer));
        EntityPlayer playerEntity = EntityRegistry.<EntityPlayer>get("player", game, false, netID);
        game.getNetworking().registerRemoteObject(netID, playerEntity);
        loadedEntities.add(playerEntity);
        return playerEntity;
    }

    @Override
    public void rpcSpawnPlayer(int id, int localPlayer) {
        boolean spawnAsLocalPlayer = game.getNetworking().getID() == localPlayer;
        System.out.println("[CLIENT] Spawn Player (" + spawnAsLocalPlayer + ")");
        EntityPlayer e = EntityRegistry.<EntityPlayer>get("player", game, spawnAsLocalPlayer, id);
        game.getNetworking().registerRemoteObject(id, e);
        loadedEntities.add(e);
    }

    @Override
    public void rpcSpawnEntity(int netId, String entityId) {
        Entity e = EntityRegistry.<Entity>get(entityId, game, false, netId);
        game.getNetworking().registerRemoteObject(netId, e);
        loadedEntities.add(e);
    }

    @Override
    public void update(float deltaTime) {
        loadedEntities.forEach(e -> e.update(deltaTime));
    }

    @Override
    public void fixedUpdate(float fixedDeltaTime) {

        loadedEntities.forEach(Entity::fixedUpdate);
        super.fixedUpdate(fixedDeltaTime);
    }

    @Override
    public void draw(Batch batch) {
        //loadedEntities.forEach(e -> e.draw(batch));
    }

    @Override
    protected void handleServerEvent(Object event) {
        if (event instanceof PlayerConnectedEvent) {
            handlePlayerConnected((PlayerConnectedEvent) event);
        } else if (event instanceof PlayerMovedEvent) {
            handlePlayerMoved((PlayerMovedEvent) event);
        }
    }

    private void handlePlayerMoved(PlayerMovedEvent event) {
        //This is not very performant and should be removed in future releases TODO!
        game.getLogger().printEvent("Moved " + event.deltaPosition);
        for (int i = 0; i < loadedEntities.size(); i++) {
            if (loadedEntities.get(i).getID() == event.entityPlayerNetId) {
                loadedEntities.get(i).transformPosition(event.deltaPosition);
            }
        }
    }

    @Override
    protected void handleClientEvent(Object event) {

    }

    private void handlePlayerConnected(PlayerConnectedEvent event) {
        IEntityManager clientMap = game.getNetworking().getClientRemoteObject(NET_ID, event.connection, IEntityManager.class);
        loadedEntities.forEach(e -> clientMap.rpcSpawnEntity(e.getID(), e.getIdentifier()));
    }


}

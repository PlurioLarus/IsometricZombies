package com.zombies.game.entity.manager;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.zombies.events.PlayerConnectedEvent;
import com.zombies.events.PlayerDisconnectedEvent;
import com.zombies.events.entitymanager.EntityDespawnedEvent;
import com.zombies.events.entitymanager.EntitySpawnedEvent;
import com.zombies.events.entitymanager.PlayerMovedEvent;
import com.zombies.events.entitymanager.PlayerSpawnedEvent;
import com.zombies.game.entity.Entity;
import com.zombies.game.entity.EntityPlayer;
import com.zombies.game.entity.EntityRegistry;
import com.zombies.main.Game;
import com.zombies.networking.NetworkedManager;
import com.zombies.utils.IntVector;

import java.util.ArrayList;
import java.util.List;

public class EntityManager extends NetworkedManager {

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
        int netID = game.getNetworking().getNextRemoteObjectIndex();
        sendEventToClients(new PlayerSpawnedEvent(netID, localPlayer));
        EntityPlayer playerEntity = EntityRegistry.<EntityPlayer>get("player", game, false, netID);
        game.getNetworking().registerRemoteObject(netID, playerEntity);
        loadedEntities.add(playerEntity);
        return playerEntity;
    }

    private void addEntity(Entity entity) {
        IntVector chunk = entity.getPosition().toChunkPos();
        game.getTileMap().getChunk(chunk).addEntity(entity);
        loadedEntities.add(entity);
    }

    private void removeEntity(Entity entity) {
        IntVector chunk = entity.getPosition().toChunkPos();
        game.getTileMap().getChunk(chunk).removeEntity(entity);
        loadedEntities.remove(entity);
    }

    @Override
    public void update(float deltaTime) {
        loadedEntities.forEach(e -> e.update(deltaTime));
    }

    @Override
    public void fixedUpdate(float fixedDeltaTime) {
        loadedEntities.forEach(e -> {
            IntVector playerChunk = e.getPosition().toChunkPos();
            int deltaX = playerChunk.x - e.getLastFixedPosition().toChunkPos().x;
            int deltaY = playerChunk.y - e.getLastFixedPosition().toChunkPos().y;
            if (deltaX != 0 || deltaY != 0) {
                IntVector lastChunkPos = e.getLastFixedPosition().toChunkPos();
                game.getLogger().printEvent("Entity Moved Chunk (" + lastChunkPos + " - " + playerChunk + ")");
                game.getTileMap().getChunk(lastChunkPos).removeEntity(e);
                game.getTileMap().getChunk(playerChunk).addEntity(e);
            }
        });
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
        } else if (event instanceof PlayerDisconnectedEvent) {
            handlePlayerDisconnected((PlayerDisconnectedEvent) event);
        }
    }

    private void handlePlayerDisconnected(PlayerDisconnectedEvent event) {
        int networkID = event.player.getEntity().getID();
        removeEntity(event.player.getEntity());
        game.getNetworking().removeRemoteObject(networkID);
        sendEventToClients(new EntityDespawnedEvent(networkID));
    }

    private void handlePlayerMoved(PlayerMovedEvent event) {
        //This is not very performant and should be removed in future releases TODO!
        //game.getLogger().printEvent("Moved " + event.deltaPosition);
        for (int i = 0; i < loadedEntities.size(); i++) {
            if (loadedEntities.get(i).getID() == event.entityPlayerNetId) {
                loadedEntities.get(i).transformPosition(event.deltaPosition);
            }
        }
    }

    @Override
    protected void handleClientEvent(Object event) {
        if (event instanceof EntitySpawnedEvent) {
            handleEntitySpawned((EntitySpawnedEvent) event);
        } else if (event instanceof PlayerSpawnedEvent) {
            handlePlayerSpawned((PlayerSpawnedEvent) event);
        } else if (event instanceof EntityDespawnedEvent) {
            handleEntityDespawned((EntityDespawnedEvent) event);
        }
    }

    private void handleEntityDespawned(EntityDespawnedEvent event) {
        game.getLogger().printEvent("Entity despawned");
        Entity entity = loadedEntities.stream().filter(e -> e.getID() == event.networkId).findAny().orElse(null);
        if (entity != null) {
            removeEntity(entity);
        }
        game.getNetworking().removeRemoteObject(event.networkId);
    }

    private void handlePlayerSpawned(PlayerSpawnedEvent event) {
        boolean spawnAsLocalPlayer = game.getNetworking().getID() == event.localPlayerId;
        System.out.println("[CLIENT] Spawn Player (" + spawnAsLocalPlayer + ")");
        EntityPlayer e = EntityRegistry.<EntityPlayer>get("player", game, spawnAsLocalPlayer, event.networkId);
        game.getNetworking().registerRemoteObject(event.networkId, e);
        addEntity(e);
    }

    private void handleEntitySpawned(EntitySpawnedEvent event) {
        Entity e = EntityRegistry.<Entity>get(event.entityIdentifier, game, false, event.networkId);
        game.getNetworking().registerRemoteObject(event.networkId, e);
        e.setPosition(event.spawnPosition);
        addEntity(e);
    }

    private void handlePlayerConnected(PlayerConnectedEvent event) {
        loadedEntities.forEach(
                e -> sendEventToClient(new EntitySpawnedEvent(e.getID(), e.getIdentifier(), e.getPosition()),
                        event.connection));
    }


}

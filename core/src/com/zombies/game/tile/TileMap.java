package com.zombies.game.tile;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.zombies.events.PlayerConnectedEvent;
import com.zombies.exceptions.ChunkAlreadyLoadedException;
import com.zombies.game.entity.Entity;
import com.zombies.game.entity.EntityPlayer;
import com.zombies.game.entity.EntityRegistry;
import com.zombies.main.Game;
import com.zombies.networking.NetworkedManager;
import com.zombies.utils.IntVector;
import com.zombies.utils.Vector;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TileMap extends NetworkedManager implements ITileMap {

    public static final int NET_ID = 1000000;

    final Game game;

    final List<Chunk> loadedChunks = new ArrayList<>();
    final List<Entity> loadedEntities = new ArrayList<>();

    public TileMap(Game game) {
        super(game, NET_ID);
        this.game = game;
    }

    public List<Chunk> getLoadedChunks(){
        return loadedChunks;
    }

    //Entity Stuff
    public void spawnEntity(Entity entity, Vector position) {
        if (loadedEntities.contains(entity)) {
            //Maybe Throw an exception here
            return;
        }
        loadedEntities.add(entity);
        entity.setPosition(position);
    }


    @Override
    public void draw(Batch batch) {
        loadedChunks.forEach(e -> e.draw(batch));
        loadedEntities.forEach(e -> e.draw(batch));
    }

    @Override
    public void cmdSpawnEntity(Entity entity, Vector position) {
        /*loadedEntities.add(entity);
        entity.setPosition(position);
        List<ITileMap> tileMaps = game.getNetworking().getRemoteObjects(NET_ID, ITileMap.class);
        tileMaps.forEach(t -> rpcSpawnEntity(0, entity));*/
    }

    @Override
    public void rpcSpawnEntity(int netId, String entityId) {
        Entity e = EntityRegistry.<Entity>get(entityId, game, false, netId);
        game.getNetworking().registerRemoteObject(netId, e);
        loadedEntities.add(e);
    }


    public void spawnPlayer(int localPlayer) {
        System.out.println("[SERVER] Spawn Player");
        List<ITileMap> tileMaps = game.getNetworking().getClientRemoteObjects(NET_ID, ITileMap.class);
        int netID = game.getNetworking().getNextRemoteObjectIndex();
        tileMaps.forEach(t -> t.rpcSpawnPlayer(netID, localPlayer));
        EntityPlayer playerEntity = EntityRegistry.<EntityPlayer>get("player", game, false, netID);
        game.getNetworking().registerRemoteObject(netID, playerEntity);
        loadedEntities.add(playerEntity);
    }

    @Override
    public void rpcSpawnPlayer(int id, int localPlayer) {
        System.out.println("[CLIENT] Spawn Player");
        boolean spawnAsLocalPlayer = game.getNetworking().getID() == localPlayer;
        EntityPlayer e = EntityRegistry.<EntityPlayer>get("player", game, spawnAsLocalPlayer, id);
        game.getNetworking().registerRemoteObject(id, e);
        loadedEntities.add(e);
        game.addPlayer(e);
    }

    @Override
    public void onClientConnected(Connection clientConnection) {
        System.out.println("[SERVER:RECEIVED] OnClientConnected");
        addEvent(new PlayerConnectedEvent(clientConnection));
    }

    @Override
    public void onClientDisconnected(Connection clientConnection) {

    }

    @Override
    public void registerKryoObjects(Kryo kryo) {

    }

    @Override
    public void update(float deltaTime) {
        loadedEntities.forEach(e -> e.update(deltaTime));
    }


    @Override
    public void fixedUpdate(float fixedDeltaTime) {
        if (game.getNetworking().isClient()) return;
        while (!eventQueue.isEmpty()) {
            Object event = eventQueue.poll();
            if (event instanceof PlayerConnectedEvent) {
                handlePlayerConnected((PlayerConnectedEvent) event);
            }
        }
    }

    private void handlePlayerConnected(PlayerConnectedEvent event) {
        ITileMap clientMap = game.getNetworking().getClientRemoteObject(NET_ID, event.connection, ITileMap.class);
        loadedEntities.forEach(e -> clientMap.rpcSpawnEntity(e.getID(), e.getIdentifier()));
        spawnPlayer(event.connection.getID());
    }

}

package com.zombies.game.tile;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.zombies.exceptions.ChunkAlreadyLoadedException;
import com.zombies.game.entity.Entity;
import com.zombies.game.entity.EntityPlayer;
import com.zombies.game.entity.EntityRegistry;
import com.zombies.main.Game;
import com.zombies.utils.IntVector;
import com.zombies.utils.Vector;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TileMap extends Actor implements ITileMap {

    public static final int NET_ID = 1000000;

    final Game game;

    List<Chunk> loadedChunks = new ArrayList<>();
    List<Entity> loadedEntities = new ArrayList<>();

    public TileMap(Game game) {
        this.game = game;
    }

    public void loadChunk(IntVector chunkPos) {
        if (loadedChunks.stream().anyMatch(e -> e.chunkPosition.equals(chunkPos))) {
            throw new ChunkAlreadyLoadedException();
        }
        loadedChunks.add(new Chunk(chunkPos));
        loadedChunks.sort(Comparator.comparingInt(a -> a.chunkPosition.toScreenCoords().y));
    }

    public void spawnEntity(Entity entity, Vector position) {
        if (loadedEntities.contains(entity)) {
            //Maybe Throw an exception here
            return;
        }
        loadedEntities.add(entity);
        entity.setPosition(position);
    }

    @Override
    public void act(float delta) {
        //loadedEntities.forEach(e -> e.update(delta));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        loadedChunks.forEach(e -> e.draw(batch));
        //loadedEntities.forEach(e -> e.draw(batch));
    }

    @Override
    public void cmdSpawnEntity(Entity entity, Vector position) {
        /*loadedEntities.add(entity);
        entity.setPosition(position);
        List<ITileMap> tileMaps = game.getNetworking().getRemoteObjects(NET_ID, ITileMap.class);
        tileMaps.forEach(t -> rpcSpawnEntity(0, entity));*/
    }

    @Override
    public void rpcSpawnEntity(int id, Entity entity) {
        /*loadedEntities.add(entity);*/
    }

    public void spawnPlayer(int localPlayer) {
        System.out.println("[SERVER] Spawn Player");
        List<ITileMap> tileMaps = game.getNetworking().getRemoteObjects(NET_ID, ITileMap.class);
        tileMaps.forEach(t -> t.rpcSpawnPlayer(game.getNetworking().getNextRemoteObjectIndex(), localPlayer));
    }

    @Override
    public void rpcSpawnPlayer(int id, int localPlayer) {
        System.out.println("[CLIENT] Spawn Player");
        boolean spawnAsLocalPlayer = game.getNetworking().getID() == localPlayer;
        Entity e = EntityRegistry.<EntityPlayer>get("player", game, spawnAsLocalPlayer, id);
        //game.getNetworking().registerRemoteObject(id, e);
        //loadedEntities.add(e);
    }
}

package com.zombies.main;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.zombies.events.PlayerConnectedEvent;
import com.zombies.game.entity.manager.EntityManager;
import com.zombies.game.player.PlayerManager;
import com.zombies.game.tile.ChunkLoader;
import com.zombies.game.tile.TileMap;
import com.zombies.networking.Networking;
import com.zombies.rendering.TextureRegistry;
import com.zombies.utils.Vector;

import java.io.IOException;

public class Game extends Group {
    private final boolean isServer;
    public final OrthographicCamera camera;
    private Networking networking;
    private ChunkLoader chunkLoader;
    private PlayerManager playerManager;
    private TileMap tileMap;
    private EntityManager entityManager;

    public Game(boolean isServer, OrthographicCamera camera) throws IOException {
        this.isServer = isServer;
        this.camera = camera;
        initialize();
    }

    private void initialize() throws IOException {
        tileMap = new TileMap(this);
        chunkLoader = new ChunkLoader(this);
        entityManager = new EntityManager(this);
        playerManager = new PlayerManager(this);
        if (isServer) {
            networking = Networking.server(new Listener() {
                @Override
                public void connected(Connection connection) {
                    super.connected(connection);
                    PlayerConnectedEvent event = new PlayerConnectedEvent(connection);
                    entityManager.addEvent(event);
                    tileMap.addEvent(event);
                    playerManager.addEvent(event);
                }
            });
        } else {
            TextureRegistry.register("fancy-char", new Texture("fancyCharacter.png"));
            TextureRegistry.register("fancyTile", new Texture("fancyTile.png"));
            TextureRegistry.register("fancyTile2", new Texture("fancyTile2.png"));
            TextureRegistry.register("fancyOtherTile", new Texture("fancyOtherTile.png"));
            networking = Networking.client("localhost", null);
        }
        networking.registerRemoteObject(TileMap.NET_ID, tileMap);
        networking.registerRemoteObject(entityManager.getNetID(), entityManager);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        chunkLoader.update();
        tileMap.update(delta);
        entityManager.update(delta);
    }

    public void setCameraPosition(Vector screenPosition) {
        camera.position.set(screenPosition.x, screenPosition.y, 0);
    }


    public ChunkLoader getChunkLoader() {
        return chunkLoader;
    }

    public TileMap getTileMap() {
        return tileMap;
    }

    public Networking getNetworking() {
        return networking;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        tileMap.draw(batch);
        entityManager.draw(batch);
    }

    public void fixedUpdate() {
        tileMap.fixedUpdate(0.05f);
        entityManager.fixedUpdate(0.05f);
        playerManager.fixedUpdate();
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }
}

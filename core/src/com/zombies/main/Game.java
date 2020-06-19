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
import com.zombies.game.world.World;
import com.zombies.networking.Networking;
import com.zombies.rendering.TextureRegistry;
import com.zombies.utils.Logger;
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
    private final Logger logger;

    private final World world;

    public Game(boolean isServer, OrthographicCamera camera) throws IOException {
        this.isServer = isServer;
        this.camera = camera;
        world = new World();
        initialize();
        logger = new Logger(this);
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
            TextureRegistry.register("characterBL", new Texture("character/characterBL.png"));
            TextureRegistry.register("characterBR", new Texture("character/characterBR.png"));
            TextureRegistry.register("fancyTile", new Texture("fancyTile.png"));

            TextureRegistry.register("shadow01", new Texture("shadows/shadow01.png"));
            TextureRegistry.register("shadow02", new Texture("shadows/shadow02.png"));
            TextureRegistry.register("shadow03", new Texture("shadows/shadow03.png"));
            TextureRegistry.register("shadow10", new Texture("shadows/shadow10.png"));
            TextureRegistry.register("shadow11", new Texture("shadows/shadow11.png"));
            TextureRegistry.register("shadow12", new Texture("shadows/shadow12.png"));
            TextureRegistry.register("shadow13", new Texture("shadows/shadow13.png"));
            TextureRegistry.register("shadow20", new Texture("shadows/shadow20.png"));
            TextureRegistry.register("shadow21", new Texture("shadows/shadow21.png"));
            TextureRegistry.register("shadow22", new Texture("shadows/shadow22.png"));
            TextureRegistry.register("shadow23", new Texture("shadows/shadow23.png"));
            TextureRegistry.register("shadow30", new Texture("shadows/shadow30.png"));
            TextureRegistry.register("shadow31", new Texture("shadows/shadow31.png"));
            TextureRegistry.register("shadow32", new Texture("shadows/shadow32.png"));
            TextureRegistry.register("shadow33", new Texture("shadows/shadow33.png"));

            TextureRegistry.register("tree", new Texture("tree.png"));
            TextureRegistry.register("fancyOtherTile", new Texture("fancyOtherTile.png"));
            networking = Networking.client("localhost", null);
        }
        networking.registerRemoteObject(TileMap.NET_ID, tileMap);
        networking.registerRemoteObject(entityManager.getNetID(), entityManager);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        tileMap.update(delta);
        entityManager.update(delta);
    }

    public void setCameraPosition(Vector screenPosition) {
        camera.position.set(screenPosition.x, screenPosition.y, 0);
    }


    public ChunkLoader getChunkLoader() {
        return chunkLoader;
    }

    public Logger getLogger() {
        return logger;
    }

    public TileMap getTileMap() {
        return tileMap;
    }

    public Networking getNetworking() {
        return networking;
    }

    public World getWorld() {
        return world;
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
        chunkLoader.fixedUpdate();
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public boolean isServer() {
        return isServer;
    }
}

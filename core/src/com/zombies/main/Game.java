package com.zombies.main;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.zombies.game.entity.EntityPlayer;
import com.zombies.game.entity.EntityRegistry;
import com.zombies.game.tile.TileMap;
import com.zombies.networking.Networking;
import com.zombies.utils.Vector;

import java.io.IOException;

public class Game extends Group {
    private final boolean isServer;
    public final OrthographicCamera camera;
    private Networking networking;
    private TileMap map;

    public Game(boolean isServer, OrthographicCamera camera) throws IOException {
        this.isServer = isServer;
        this.camera = camera;
        initialize();
    }

    private void initialize() throws IOException {
        map = new TileMap(this);
        EntityRegistry.register("player", new EntityPlayer(this, false, 0));
        if (isServer) {
            networking = Networking.server(new Listener() {
                @Override
                public void connected(Connection connection) {
                    super.connected(connection);
                    map.spawnPlayer(connection.getID());
                }
            });
        } else {
            networking = Networking.client("localhost", null);
            addActor(map);
        }
        networking.registerRemoteObject(TileMap.NET_ID, map);
    }

    public void startServerGameLoop() {

    }

    public void setCameraPosition(Vector screenPosition) {
        camera.position.set(screenPosition.x, screenPosition.y, 0);
    }

    public Networking getNetworking() {
        return networking;
    }
}

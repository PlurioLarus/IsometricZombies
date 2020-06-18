package com.zombies.main;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.zombies.game.tile.TileMap;
import com.zombies.networking.Networking;
import com.zombies.rendering.TextureRegistry;
import com.zombies.utils.Vector;

import java.io.IOException;

public class Game extends Group {
    private final boolean isServer;
    public final OrthographicCamera camera;
    private Networking networking;
    private TileMap map;

    public TileMap getMap() {
        return map;
    }

    public Game(boolean isServer, OrthographicCamera camera) throws IOException {
        this.isServer = isServer;
        this.camera = camera;
        initialize();
    }

    private void initialize() throws IOException {
        map = new TileMap(this);
        if (isServer) {
            networking = Networking.server(new Listener() {
                @Override
                public void connected(Connection connection) {
                    super.connected(connection);
                    map.onClientConnected(connection);
                }
            });
        } else {
            TextureRegistry.register("fancy-char", new Texture("fancyCharacter.png"));
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

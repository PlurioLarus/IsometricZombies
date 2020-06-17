package com.zombies.networking;

import com.esotericsoftware.kryonet.Listener;
import com.zombies.exceptions.WrongNetworkTypeException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Networking {
    private final boolean isServer;
    private final GameServer server;
    private final GameClient client;
    private int remoteObjectIndex = -1;

    private Networking(boolean asServer, String hostname, Listener listener) throws IOException {
        this.isServer = asServer;
        if (this.isServer) {
            server = new GameServer(listener);
            client = null;
        } else {
            client = new GameClient(hostname, listener);
            server = null;
        }
    }

    public static Networking server(Listener listener) throws IOException {
        return new Networking(true, null, listener);
    }

    public static Networking client(String hostname, Listener listener) throws IOException {
        return new Networking(false, hostname, listener);
    }

    public boolean isServer() {
        return isServer;
    }

    public boolean isClient() {
        return !isServer;
    }

    public int getID() {
        if (isServer) {
            throw new WrongNetworkTypeException();
        } else {
            assert client != null;
            return client.getID();
        }
    }

    public int getNextRemoteObjectIndex() {
        return remoteObjectIndex--;
    }

    public void registerRemoteObject(int id, Object object) {
        if (isServer) {
            assert server != null;
            server.registerRemoteObject(id, object);
        } else {
            assert client != null;
            client.registerRemoteObject(id, object);
        }
    }

    public <T> List<T> getRemoteObjects(int id, Class<T> inface) {
        if (isServer) {
            assert server != null;
            return server.getRemoteObjects(id, inface);
        } else {
            assert client != null;
            return new ArrayList<T>(Collections.singleton(client.getRemoteObject(id, inface)));
        }
    }

}

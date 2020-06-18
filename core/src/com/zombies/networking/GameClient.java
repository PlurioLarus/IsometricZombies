package com.zombies.networking;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.rmi.ObjectSpace;
import com.esotericsoftware.kryonet.rmi.RemoteObject;

import java.io.IOException;

public class GameClient {
    private final Client client;
    private final ObjectSpace objectSpace;

    public GameClient(String host, Listener listener) throws IOException {
        client = new Client();
        client.start();
        KryoRegistry.apply(client.getKryo());
        objectSpace = new ObjectSpace();
        client.addListener(new GameClientListener(this));
        if (listener != null) client.addListener(listener);
        client.connect(5000, host, 54555, 54777);
    }

    public void registerRemoteObject(int id, Object object) {
        objectSpace.register(id, object);
    }

    public <T> T getRemoteObject(int id, Class<T> inface) {
        T object = ObjectSpace.getRemoteObject(client, id, inface);
        ((RemoteObject) object).setNonBlocking(true);
        return object;
    }

    public int getID() {
        return client.getID();
    }

    static class GameClientListener extends Listener {
        final GameClient client;

        GameClientListener(GameClient client) {
            this.client = client;
        }

        @Override
        public void connected(Connection connection) {
            super.connected(connection);
            client.objectSpace.addConnection(connection);
        }

        @Override
        public void disconnected(Connection connection) {
            super.disconnected(connection);
            client.objectSpace.removeConnection(connection);
        }
    }

}

package com.zombies.networking;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.kryonet.rmi.ObjectSpace;
import com.esotericsoftware.kryonet.rmi.RemoteObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameServer {
    private final Server server;
    private final ObjectSpace objectSpace;

    public GameServer(Listener listener) throws IOException {

        server = new Server();
        server.start();
        KryoRegistry.apply(server.getKryo());
        objectSpace = new ObjectSpace();
        server.addListener(new GameServerListener(this));
        if (listener != null) {
            server.addListener(listener);
        }
        server.bind(54555, 54777);
    }

    public void registerRemoteObject(int id, Object object) {
        objectSpace.register(id, object);
    }

    public <T> List<T> getRemoteObjects(int id, Class<T> inface) {
        Connection[] conns = server.getConnections();
        List<T> objs = new ArrayList<>();
        for (Connection conn : conns) {
            T object = ObjectSpace.getRemoteObject(conn, id, inface);
            ((RemoteObject) object).setNonBlocking(true);
            objs.add(object);
        }
        return objs;
    }

    public void removeRemoteObject(int networkID) {
        objectSpace.remove(networkID);
    }

    static class GameServerListener extends Listener {
        final GameServer server;

        GameServerListener(GameServer server) {
            this.server = server;
        }

        @Override
        public void connected(Connection connection) {
            super.connected(connection);
            server.objectSpace.addConnection(connection);
        }

        @Override
        public void disconnected(Connection connection) {
            super.disconnected(connection);
            server.objectSpace.removeConnection(connection);
        }
    }

}

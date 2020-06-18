package com.zombies.networking;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.zombies.main.Game;

import java.util.LinkedList;
import java.util.Queue;

public abstract class NetworkedManager implements INetworkedManager {

    protected Queue<Object> eventQueue = new LinkedList<>();
    protected final Game game;
    private final int netID;

    protected NetworkedManager(Game game, int netID) {
        this.game = game;
        this.netID = netID;
    }

    public abstract void onClientConnected(Connection clientConnection);

    public abstract void onClientDisconnected(Connection clientConnection);

    public void cmdOnEventReceived(Object event) {
        addEvent(event);
    }

    public void sendEventToServer(Object event) {
        INetworkedManager serverManager = game.getNetworking().getServerRemoteObject(netID, INetworkedManager.class);
        serverManager.cmdOnEventReceived(event);
    }

    public synchronized void addEvent(Object event) {
        if (game.getNetworking().isServer()) {
            eventQueue.add(event);
        }
    }

    public abstract void registerKryoObjects(Kryo kryo);

    public abstract void update(float deltaTime);

    public abstract void draw(Batch batch);

    public abstract void fixedUpdate(float fixedDeltaTime);


}

package com.zombies.networking;

import com.badlogic.gdx.graphics.g2d.Batch;
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

    public abstract void update(float deltaTime);

    public abstract void draw(Batch batch);

    protected abstract void handleEvent(Object event);

    public void fixedUpdate(float fixedDeltaTime) {
        if (game.getNetworking().isServer()) {
            while (!eventQueue.isEmpty()) {
                handleEvent(eventQueue.poll());
            }
        }
    }

    public int getNetID() {
        return netID;
    }
}

package com.zombies.game.entity;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.zombies.game.entity.behaviours.IBehaviour;
import com.zombies.main.Game;
import com.zombies.utils.Vector;

import java.util.ArrayList;
import java.util.List;

public abstract class Entity implements IEntity {
    protected Vector position = new Vector(0, 0);
    protected Vector lastPosition = new Vector(0, 0);
    protected List<IBehaviour> behaviours = new ArrayList<>();

    protected final Game game;
    private final boolean localPlayer;
    private final int netId;
    private final String identifier;

    public boolean isLocalPlayer() {
        return localPlayer;
    }

    protected Entity(Game game, boolean localPlayer, int netId, String identifier) {
        this.game = game;
        this.localPlayer = localPlayer;
        this.netId = netId;
        this.identifier = identifier;
    }

    protected void registerBehaviour(IBehaviour behaviour) {
        behaviours.add(behaviour);
    }

    public void update(float deltaTime) {
        lastPosition = position;
        behaviours.forEach(b -> b.update(deltaTime, this));
    }

    public void draw(Batch batch) {
        behaviours.forEach(b -> b.draw(batch, this));
    }

    public Vector getPosition() {
        return position;
    }

    public Vector getLastPosition() {
        return lastPosition;
    }

    public String getIdentifier() {
        return identifier;
    }

    public Game getGame() {
        return game;
    }

    public void setPosition(Vector position) {
        this.position = position;
        if (isLocalPlayer()) {
            System.out.println("[LOCAL] Send: " + position);
            game.getNetworking().getServerRemoteObject(netId, IEntity.class).cmdSetPosition(position);
        } else if (game.getNetworking().isServer()) {
            game.getNetworking().getClientRemoteObjects(netId, IEntity.class).forEach(e -> e.rpcSetPosition(position));
        }
    }

    @Override
    public void cmdSetPosition(Vector vector) {
        this.position = vector;
        game.getNetworking().getClientRemoteObjects(netId, IEntity.class).forEach(e -> e.rpcSetPosition(position));
    }

    @Override
    public void rpcSetPosition(Vector vector) {
        this.position = vector;
    }

    public void transformPosition(Vector vector) {
        setPosition(this.position.plus(vector));
    }

    public int getID() {
        return this.netId;
    }
}

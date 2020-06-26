package com.zombies.game.entity;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.zombies.game.entity.behaviours.IBehaviour;
import com.zombies.game.tile.Tile;
import com.zombies.main.Game;
import com.zombies.utils.Box;
import com.zombies.utils.Direction;
import com.zombies.utils.Vector;

import java.util.ArrayList;
import java.util.List;

public abstract class Entity implements IEntity {
    protected Box box;
    protected Vector lastPosition = new Vector(0, 0);
    protected Vector lastMove = new Vector(-1,-1); //TODO: fix initial values
    protected Vector lastFixedPosition = new Vector(0, 0);
    protected List<IBehaviour> behaviours = new ArrayList<>();

    protected final Game game;
    protected final boolean hasCollider;
    protected final boolean isTrigger;
    private final boolean localPlayer;
    private final int netId;
    private final String identifier;

    public boolean isLocalPlayer() {
        return localPlayer;
    }

    protected Entity(Game game, boolean localPlayer, int netId, String identifier, Vector size, boolean hasCollider, boolean isTrigger) {
        this.game = game;
        this.localPlayer = localPlayer;
        this.netId = netId;
        this.identifier = identifier;
        this.box = new Box(Vector.zero, size);
        this.hasCollider = hasCollider;
        this.isTrigger = isTrigger;
    }

    protected void registerBehaviour(IBehaviour behaviour) {
        behaviours.add(behaviour);
    }

    public void update(float deltaTime) {
        lastPosition = box.position;
        behaviours.forEach(b -> b.update(deltaTime, this));
    }

    public void fixedUpdate() {
        lastFixedPosition = box.position;

    }

    public void draw(Batch batch) {
        behaviours.forEach(b -> b.draw(batch, this));
    }

    public Vector getPosition() {
        return box.position;
    }

    public Box getBox() {
        return box;
    }

    public Box getLastFixedBox() {
        return box.withPosition(lastFixedPosition);
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
        this.box = this.box.withPosition(position);
        if (game.getNetworking().isServer()) {
            game.getNetworking().getClientRemoteObjects(netId, IEntity.class).forEach(e -> e.rpcSetPosition(position));
        }
    }

    public Tile getTile() {
        return game.getTileMap().getTile(box.position);
    }

    public Vector getLastMove(){
        Vector newMove = box.position.minus(lastPosition);
        if (!newMove.isZero()){
            lastMove = newMove;
        }
        return lastMove;
    }

    public Direction getDirection() {
        Vector move = getLastMove();
        return Direction.get(move);
    }

    @Override
    public void cmdSetPosition(Vector vector) {
        /*if (!collides(vector)) {
            this.position = vector;
            game.getNetworking().getClientRemoteObjects(netId, IEntity.class).forEach(e -> e.rpcSetPosition(position));
        } else {
            this.position = lastFixedPosition;
        }*/
    }

    @Override
    public void rpcSetPosition(Vector position) {
        if (!position.nearby(this.getPosition(), 0.1f) || !this.isLocalPlayer()) {
            setPosition(position);
        }
    }

    public void transformPosition(Vector vector) {
        Vector newPos = this.getPosition().plus(new Vector(vector.x, 0));
        if (collides(newPos)) {
            newPos = newPos.minus(new Vector(vector.x, 0));
        }
        newPos = newPos.plus(new Vector(0, vector.y));
        if (collides(newPos)) {
            newPos = newPos.minus(new Vector(0, vector.y));
        }
        if (!this.getPosition().equals(newPos)) {
            setPosition(newPos);
        }
    }

    private boolean collides(Vector newPosition) {
        Box box = this.box.withPosition(newPosition);


        return this.game.getPhysics().overlapBox(box, this);
    }

    public int getID() {
        return this.netId;
    }

    public Vector getLastFixedPosition() {
        return lastFixedPosition;
    }

    public boolean isNotTrigger() {
        return !isTrigger;
    }

    public boolean hasCollider() {
        return hasCollider;
    }
}

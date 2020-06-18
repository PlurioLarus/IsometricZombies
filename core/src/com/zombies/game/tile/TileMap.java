package com.zombies.game.tile;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.zombies.main.Game;
import com.zombies.networking.NetworkedManager;

import java.util.ArrayList;
import java.util.List;

public class TileMap extends NetworkedManager implements ITileMap {

    public static final int NET_ID = 1000000;

    final Game game;

    final List<Chunk> loadedChunks = new ArrayList<>();

    public TileMap(Game game) {
        super(game, NET_ID);
        this.game = game;
    }

    public List<Chunk> getLoadedChunks() {
        return loadedChunks;
    }

    @Override
    public void draw(Batch batch) {
        loadedChunks.forEach(e -> e.draw(batch));
    }

    @Override
    protected void handleEvent(Object event) {

    }

    @Override
    public void update(float deltaTime) {
    }


}

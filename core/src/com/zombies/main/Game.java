package com.zombies.main;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.zombies.game.entity.EntityPlayer;
import com.zombies.game.tile.Chunk;
import com.zombies.game.tile.ChunkLoader;
import com.zombies.game.tile.TileMap;
import com.zombies.networking.Networking;
import com.zombies.rendering.TextureRegistry;
import com.zombies.utils.IntVector;
import com.zombies.utils.Vector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Game extends Group {
    private final boolean isServer;
    public final OrthographicCamera camera;
    private Networking networking;
    private ChunkLoader chunkLoader;
    private TileMap tileMap;
    private final  List<EntityPlayer> players;

    public Game(boolean isServer, OrthographicCamera camera) throws IOException {
        this.isServer = isServer;
        this.camera = camera;
        players = new ArrayList<>();
        initialize();
    }

    private void initialize() throws IOException {
        tileMap = new TileMap(this);
        chunkLoader = new ChunkLoader(this);


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
            TextureRegistry.register("fancyTile", new Texture("fancyTile.png"));
            TextureRegistry.register("fancyOtherTile", new Texture("fancyOtherTile.png"));
            networking = Networking.client("localhost", null);
        }
        networking.registerRemoteObject(TileMap.NET_ID, tileMap);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        chunkLoader.update();
        map.update(delta);
    }

    public void setCameraPosition(Vector screenPosition) {
        camera.position.set(screenPosition.x, screenPosition.y, 0);
    }

    public List<EntityPlayer> getPlayers(){
        return players;
    }

    public ChunkLoader getChunkLoader(){
        return chunkLoader;
    }

    public TileMap getTileMap(){
        return tileMap;
    }
    public Networking getNetworking() {
        return networking;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        map.draw(batch);
    }

    public void fixedUpdate() {
        map.fixedUpdate(0.05f);
    }
    public void addPlayer(EntityPlayer player){
        //TODO: @NOAH
        //chunkLoader.loadSurroundingChunks(player.getPosition().toChunkPos());
        players.add(player);
    }
}

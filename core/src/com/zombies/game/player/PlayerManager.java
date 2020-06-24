package com.zombies.game.player;

import com.zombies.events.PlayerConnectedEvent;
import com.zombies.events.PlayerDisconnectedEvent;
import com.zombies.game.entity.EntityPlayer;
import com.zombies.main.Game;
import com.zombies.utils.IntVector;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Keeps track of all players
 * This is a Server Side only Manager
 */
public class PlayerManager {

    private final Game game;
    List<Player> players = new ArrayList<>();
    protected Queue<Object> eventQueue = new LinkedList<>();

    public PlayerManager(Game game) {
        this.game = game;
    }

    public synchronized void addEvent(Object event) {
        eventQueue.add(event);
    }

    public void fixedUpdate() {
        while (!eventQueue.isEmpty()) {
            handleEvent(eventQueue.poll());
        }
    }

    private void handleEvent(Object event) {
        if (event instanceof PlayerConnectedEvent) {
            PlayerConnectedEvent playerEvent = (PlayerConnectedEvent) event;
            EntityPlayer player = game.getEntityManager().spawnPlayer(playerEvent.connection.getID());
            players.add(new Player(playerEvent.connection, player));
            game.getChunkLoader().loadSurroundingChunks(IntVector.zero);

        } else if (event instanceof PlayerDisconnectedEvent) {
            PlayerDisconnectedEvent playerEvent = (PlayerDisconnectedEvent) event;
            game.getChunkLoader().unloadSurroundingChunks(playerEvent.player.getEntity().getPosition().toChunkPos());
            players.remove(playerEvent.player);
        }
    }

    public List<Player> getPlayers() {
        return players;
    }
}

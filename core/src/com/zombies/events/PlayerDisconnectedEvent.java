package com.zombies.events;

import com.zombies.game.player.Player;

public class PlayerDisconnectedEvent {
    public final Player player;

    public PlayerDisconnectedEvent(Player player) {
        this.player = player;
    }
}

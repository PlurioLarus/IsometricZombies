package com.zombies.game.tile;

public class StandardTile extends Tile {
    public StandardTile() {
        super(Math.random() > 0.5 ? "fancyTile" : "fancyOtherTile");
    }
}

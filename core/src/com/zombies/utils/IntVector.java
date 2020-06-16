package com.zombies.utils;

import com.zombies.main.IsometricZombies;

public class IntVector {
    public final int x, y;

    public IntVector(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public IntVector plus(IntVector vector) {
        return new IntVector(this.x + vector.x, this.y + vector.y);
    }

    public IntVector times(int multiplier) {
        return new IntVector(this.x * multiplier, this.y * multiplier);
    }

    /**
     * Converts to Screen Coords if this is a world coord!!!!
     *
     * @return your mom
     */
    public IntVector toScreenCoords() {
        int x = (IsometricZombies.TILE_SIZE / 2) * (this.x - this.y);
        int y = (IsometricZombies.TILE_SIZE / 4) * (this.x + this.y);
        return new IntVector(x, y);
    }

}

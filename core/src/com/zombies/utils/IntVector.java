package com.zombies.utils;

import com.zombies.main.IsometricZombies;

import java.util.Objects;

public class IntVector {
    public final int x, y;

    public IntVector(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public IntVector() {
        this.x = 0;
        this.y = 0;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IntVector intVector = (IntVector) o;
        return x == intVector.x &&
                y == intVector.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}

package com.zombies.utils;

import com.zombies.main.IsometricZombies;

import java.util.Objects;

public class IntVector {
    public static IntVector zero = new IntVector(0, 0);
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

    public IntVector minus(IntVector vector) {
        return new IntVector(this.x - vector.x, this.y - vector.y);
    }

    public IntVector times(int multiplier) {
        return new IntVector(this.x * multiplier, this.y * multiplier);
    }

    public int getSquaredLength() {
        return (x * x) + (y * y);
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

    public IntVector toChunkPos() {
        return new IntVector((int) Math.floor((double) this.x / IsometricZombies.CHUNK_SIZE), (int) Math.floor((double) this.y / IsometricZombies.CHUNK_SIZE));
    }

    /**
     * This Method transforms a worldposition vector to a position vector according to the chunk this vector points on
     *
     * @return
     */
    public IntVector toChunkOffset() {
        int newX = this.x % IsometricZombies.CHUNK_SIZE;
        int newY = this.y % IsometricZombies.CHUNK_SIZE;
        return new IntVector(newX < 0 ? newX + IsometricZombies.CHUNK_SIZE : newX, newY < 0 ? newY + IsometricZombies.CHUNK_SIZE : newY);
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

    @Override
    public String toString() {
        return "IntVector{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}

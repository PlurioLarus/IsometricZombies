package com.zombies.utils;

import com.zombies.main.IsometricZombies;

import java.util.Objects;

public class Vector {

    public static final Vector zero = new Vector(0, 0);

    public final float x, y;

    public Vector(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector plus(Vector vector) {
        return new Vector(this.x + vector.x, this.y + vector.y);
    }

    public Vector minus(Vector vector) {
        return new Vector(this.x - vector.x, this.y - vector.y);
    }

    public Vector times(float multiplier) {
        return new Vector(this.x * multiplier, this.y * multiplier);
    }

    /**
     * Converts to Screen Coords if this is a world coord!!!!
     *
     * @return your mom
     */
    public Vector toScreenCoords() {
        float x = (IsometricZombies.TILE_SIZE / 2f) * (this.x - this.y);
        float y = (IsometricZombies.TILE_SIZE / 4f) * (this.x + this.y);
        return new Vector(x, y);
    }

    /**
     * TODO: check round of this.x/this.y relative to their tile
     * @return
     */
    public IntVector toChunkPos(){
        return new IntVector((int)Math.floor(this.x/32), (int) Math.floor(this.y/32));
    }

    public float getLength() {
        return (float) Math.sqrt(x * x + y * y);
    }

    public Vector normalize() {
        float length = getLength();
        if (length == 0) {
            return Vector.zero;
        }
        return new Vector(x / length, y / length);
    }

    public IntVector roundToIntVector(){
        return new IntVector(Math.round(this.x), Math.round(this.y));
    }

    @Override
    public String toString() {
        return "Vector{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector vector = (Vector) o;
        return Float.compare(vector.x, x) == 0 &&
                Float.compare(vector.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}

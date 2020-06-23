package com.zombies.utils;

public class Box {
    public final Vector position;
    public final Vector size;


    public Box(Vector position, Vector size) {
        this.position = position;
        this.size = size;
    }

    public Box withPosition(Vector position) {
        return new Box(position, this.size);
    }

    public Vector getTopRight() {
        return position.plus(size.times(0.5f));
    }

    public float getLeft() {
        return position.x - (size.x * 0.5f);
    }

    public float getRight() {
        return position.x + (size.x * 0.5f);
    }

    public float getTop() {
        return position.y + (size.y * 0.5f);
    }

    public float getBottom() {
        return position.y - (size.y * 0.5f);
    }

    public Vector getTopLeft() {
        return position.plus(new Vector(-0.5f * size.x, 0.5f * size.y));
    }

    public Vector getBottomLeft() {
        return position.plus(new Vector(-0.5f * size.x, -0.5f * size.y));
    }

    public Vector getBottomRight() {
        return position.plus(new Vector(0.5f * size.x, -0.5f * size.y));
    }
}

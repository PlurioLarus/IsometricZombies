package com.zombies.actor;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class SpriteActor extends Actor {

    final OrthographicCamera camera;

    public SpriteActor(OrthographicCamera camera) {
        this.camera = camera;
        setY(6);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        Texture tex = new Texture("fancyCharacter.png");
        batch.draw(tex, getX(), getY());
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        camera.position.set(getX(), getY(), 0);
    }

    public void moveUp() {
        setY(getY() + 16);
    }

    public void moveDown() {
        setY(getY() - 16);
    }

    public void moveRight() {
        setX(getX() + 32);
    }

    public void moveLeft() {
        setX(getX() - 32);
    }

}

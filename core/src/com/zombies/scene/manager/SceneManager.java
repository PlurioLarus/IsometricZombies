package com.zombies.scene.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.zombies.scene.Scene;

public class SceneManager {

    private Scene scene;
    private InputMultiplexer multiplexer = new InputMultiplexer();

    public SceneManager() {
        Gdx.input.setInputProcessor(multiplexer);
    }

    public void changeScene(Scene scene) {
        if (this.scene != null) {
            this.scene.removeInputProcessor(multiplexer);
            this.scene.dispose();
        }
        this.scene = scene;
        this.scene.addInputProcessor(multiplexer);
        this.scene.initialize();
    }

    public void update() {
        if (scene != null) {
            scene.update(Gdx.graphics.getDeltaTime());
        }
    }

    public void draw() {
        if (scene != null) {
            scene.draw();
        }
    }

    public void dispose() {
        if (scene != null) {
            this.scene.removeInputProcessor(multiplexer);
            scene.dispose();
        }
    }

    public void resize(int width, int height) {
        if (scene != null) {
            scene.resize(width, height);
        }
    }
}

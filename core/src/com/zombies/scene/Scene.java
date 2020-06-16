package com.zombies.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FillViewport;

public abstract class Scene {

    protected OrthographicCamera camera = new OrthographicCamera();
    protected final Stage stage = new Stage(new FillViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera));

    public abstract void initialize();

    public void update(float deltaTime) {
        camera.update();
        stage.act(deltaTime);
    }

    public void draw() {
        stage.draw();
    }

    public void dispose() {
        stage.dispose();
    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
    }

    public void addInputProcessor(InputMultiplexer multiplexer) {
        multiplexer.addProcessor(stage);
    }

    public void removeInputProcessor(InputMultiplexer multiplexer) {
        multiplexer.removeProcessor(stage);
    }
}

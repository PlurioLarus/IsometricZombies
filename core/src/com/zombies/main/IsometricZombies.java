package com.zombies.main;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.zombies.scene.game.GameScene;
import com.zombies.scene.manager.SceneManager;


public class IsometricZombies extends ApplicationAdapter {
    public static final int TILE_SIZE = 32;
    SceneManager sM;


    @Override
    public void create() {
        sM = new SceneManager();
        sM.changeScene(new GameScene());

    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        sM.update();
        sM.draw();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        sM.resize(width, height);
    }

    @Override
    public void dispose() {
        sM.dispose();
    }
}

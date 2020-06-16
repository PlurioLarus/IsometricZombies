package com.zombies.scene.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.zombies.actor.SpriteActor;
import com.zombies.game.tile.TileMap;
import com.zombies.scene.Scene;
import com.zombies.utils.IntVector;

public class GameScene extends Scene {
    @Override
    public void initialize() {
        TileMap map = new TileMap();
        map.loadChunk(new IntVector(0, 0));
        map.loadChunk(new IntVector(0, 1));
        map.loadChunk(new IntVector(1, 0));
        map.loadChunk(new IntVector(1, 1));
        stage.addActor(map);
        SpriteActor sprite = new SpriteActor(camera);
        stage.addActor(sprite);
        camera.zoom = 0.5f;
        camera.position.set(0, 200, 0);
        stage.addListener(new InputListener() {


            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.W) {
                    sprite.moveUp();
                }
                if (keycode == Input.Keys.A) {
                    sprite.moveLeft();
                }
                if (keycode == Input.Keys.S) {
                    sprite.moveDown();
                }
                if (keycode == Input.Keys.D) {
                    sprite.moveRight();
                }
                return true;
            }
        });
    }
}

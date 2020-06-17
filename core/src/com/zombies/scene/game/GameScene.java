package com.zombies.scene.game;

import com.zombies.game.entity.EntityPlayer;
import com.zombies.game.entity.EntityRegistry;
import com.zombies.main.Game;
import com.zombies.scene.Scene;
import com.zombies.utils.Vector;

import java.io.IOException;

public class GameScene extends Scene {


    @Override
    public void initialize() {
        super.initialize();

        Game clientGame = null, serverGame = null;
        EntityRegistry.register("player", EntityPlayer::new);
        try {
            serverGame = new Game(true, camera);
            clientGame = new Game(false, camera);
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.addActor(clientGame);
        camera.zoom = 0.5f;
    }

    public void setCameraPosition(Vector screenPosition) {
        camera.position.set(screenPosition.x, screenPosition.y, 0);
    }

}

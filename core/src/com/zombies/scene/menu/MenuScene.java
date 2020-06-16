package com.zombies.scene.menu;

import com.zombies.actor.SpriteActor;
import com.zombies.scene.Scene;

public class MenuScene extends Scene {

    @Override
    public void initialize() {
        stage.addActor(new SpriteActor(camera));
    }
}

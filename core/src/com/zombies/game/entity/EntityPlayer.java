package com.zombies.game.entity;

import com.zombies.game.entity.behaviours.CameraFollowBehaviour;
import com.zombies.game.entity.behaviours.MovingInputBehaviour;
import com.zombies.game.entity.behaviours.SpriteRenderBehaviour;
import com.zombies.main.Game;
import com.zombies.utils.Vector;

public class EntityPlayer extends Entity {

    public EntityPlayer(Game game, boolean localPlayer, int id) {
        super(game, localPlayer, id, "player");
        registerBehaviour(new MovingInputBehaviour());
        if (game.getNetworking().isClient())
            registerBehaviour(new SpriteRenderBehaviour("fancy-char", new Vector(0.5f, 0)));
        game.setCameraPosition(position);
        registerBehaviour(new CameraFollowBehaviour());
    }
}

package com.zombies.game.entity;

import com.zombies.game.entity.behaviours.CameraFollowBehaviour;
import com.zombies.game.entity.behaviours.MovingInputBehaviour;
import com.zombies.game.entity.behaviours.PlayerRenderBehaviour;
import com.zombies.main.Game;
import com.zombies.utils.Vector;

public class EntityPlayer extends Entity {

    public EntityPlayer(Game game, boolean localPlayer, int id) {
        super(game, localPlayer, id, "player", new Vector(0.5f, 0.5f));
        registerBehaviour(new MovingInputBehaviour());
        if (game.getNetworking().isClient()) {
            registerBehaviour(new PlayerRenderBehaviour(new Vector(0.5f, 0)));
        }
        game.setCameraPosition(getPosition());
        registerBehaviour(new CameraFollowBehaviour());
    }
}

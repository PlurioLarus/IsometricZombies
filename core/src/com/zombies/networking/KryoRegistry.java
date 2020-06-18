package com.zombies.networking;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.rmi.ObjectSpace;
import com.zombies.game.entity.Entity;
import com.zombies.game.entity.EntityPlayer;
import com.zombies.game.entity.IEntity;
import com.zombies.game.entity.behaviours.CameraFollowBehaviour;
import com.zombies.game.entity.behaviours.MovingInputBehaviour;
import com.zombies.game.entity.behaviours.SpriteRenderBehaviour;
import com.zombies.game.tile.ITileMap;
import com.zombies.game.tile.TileMap;
import com.zombies.utils.Vector;

import java.util.ArrayList;

public class KryoRegistry {

    public static void apply(Kryo kryo) {
        kryo.register(ITileMap.class);
        kryo.register(TileMap.class);
        kryo.register(IEntity.class);
        kryo.register(Entity.class);
        kryo.register(EntityPlayer.class);
        kryo.register(ArrayList.class);
        kryo.register(CameraFollowBehaviour.class);
        kryo.register(MovingInputBehaviour.class);
        kryo.register(SpriteRenderBehaviour.class);
        kryo.register(Vector.class);
        ObjectSpace.registerClasses(kryo);
    }

}

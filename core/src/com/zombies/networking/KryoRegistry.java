package com.zombies.networking;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.rmi.ObjectSpace;
import com.zombies.events.entitymanager.EntityDespawnedEvent;
import com.zombies.events.entitymanager.EntitySpawnedEvent;
import com.zombies.events.entitymanager.PlayerMovedEvent;
import com.zombies.events.entitymanager.PlayerSpawnedEvent;
import com.zombies.events.tilemap.ChunkEvent;
import com.zombies.game.entity.Entity;
import com.zombies.game.entity.EntityPlayer;
import com.zombies.game.entity.IEntity;
import com.zombies.game.tile.ITileMap;
import com.zombies.game.tile.TileMap;
import com.zombies.utils.IntVector;
import com.zombies.utils.Vector;

import java.util.ArrayList;

public class KryoRegistry {

    public static void apply(Kryo kryo) {
        kryo.register(ITileMap.class);
        kryo.register(TileMap.class);
        kryo.register(IEntity.class);
        kryo.register(Entity.class);
        kryo.register(EntityPlayer.class);
        kryo.register(INetworkedManager.class);
        kryo.register(ChunkEvent.class);
        kryo.register(ArrayList.class);
        kryo.register(Vector.class);
        kryo.register(IntVector.class);
        kryo.register(PlayerMovedEvent.class);
        kryo.register(PlayerSpawnedEvent.class);
        kryo.register(EntitySpawnedEvent.class);
        kryo.register(EntityDespawnedEvent.class);
        ObjectSpace.registerClasses(kryo);
    }

}

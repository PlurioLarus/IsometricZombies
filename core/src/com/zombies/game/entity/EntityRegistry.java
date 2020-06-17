package com.zombies.game.entity;

import com.zombies.main.Game;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class EntityRegistry {

    private static final Map<String, Entity> entities = new HashMap<>();

    public static <T extends Entity> T get(String id, Game game, boolean localPlayer, int netId) {
        T obj = (T) entities.get(id).clone();

        try {
            return clazz.getConstructor(Game.class, boolean.class, int.class).newInstance(game, localPlayer, netId);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T extends Entity> void register(String key, T entity) {
        entities.put(key, entity);
    }


}

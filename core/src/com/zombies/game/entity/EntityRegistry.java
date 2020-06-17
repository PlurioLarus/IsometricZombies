package com.zombies.game.entity;

import com.zombies.main.Game;

import java.util.HashMap;
import java.util.Map;

public class EntityRegistry {

    private static final Map<String, EntityCreator> entities = new HashMap<>();

    public static <T extends Entity> T get(String id, Game game, boolean localPlayer, int netId) {
        EntityCreator creator = entities.get(id);
        return (T) creator.create(game, localPlayer, netId);
    }

    public static void register(String key, EntityCreator entity) {
        entities.put(key, entity);
    }

    @FunctionalInterface
    public static interface EntityCreator {
        Entity create(Game game, boolean localPlayer, int netId);
    }


}

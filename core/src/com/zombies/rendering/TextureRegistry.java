package com.zombies.rendering;

import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;
import java.util.Map;

public class TextureRegistry {

    private static final Map<String, Texture> textures = new HashMap<>();

    public static Texture get(String id) {
        return textures.get(id);
    }

    public static void register(String key, Texture texture) {
        textures.put(key, texture);
    }

}

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

    public static void registerCharacter(String name, String pathPrefix){
        register(name+"_T", new Texture(pathPrefix + "_T.png"));
        register(name+"_TR", new Texture(pathPrefix + "_TR.png"));
        register(name+"_R", new Texture(pathPrefix + "_R.png"));
        register(name+"_BR", new Texture(pathPrefix + "_BR.png"));
        register(name+"_B", new Texture(pathPrefix + "_B.png"));
        register(name+"_BL", new Texture(pathPrefix + "_BL.png"));
        register(name+"_L", new Texture(pathPrefix + "_L.png"));
        register(name+"_TL", new Texture(pathPrefix + "_TL.png"));
    }

}

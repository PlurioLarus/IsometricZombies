package com.zombies.utils;

import com.zombies.main.Game;

public class Logger {
    protected final Game game;

    private final String prefix;

    public Logger(Game game) {
        this.game = game;
        if (game.isServer()) {
            prefix = "[SERVER] ";
        } else {
            prefix = "[CLIENT] ";
        }
    }

    public void printEvent(String message) {
        System.out.println(prefix + message);
    }

    public void printInfo(String message) {
        System.out.println("[i] " + prefix + message);
    }

    public void printWarning(String message) {
        System.out.println("[!] " + prefix + message);
    }

}

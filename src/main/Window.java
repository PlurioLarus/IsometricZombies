package main;

import game.Game;
import mainMenu.MainMenu;
import pLib.graphics.GameWindow;

public class Window extends GameWindow {
	public MouseHandler mouseHandler;

	public Window(int sizeX, int sizeY) {
		super("Isometric Zombie Game", sizeX, sizeY, true);
		mouseHandler = new MouseHandler();
		addKeyListener(new KeyHandler());
		addMouseListener(mouseHandler);
		setMaxFPS(30);
		setEnableMaxFPS(true);
		Main.mainMenu = new MainMenu(this);
		scenes.add(Main.mainMenu);
		Main.game = new Game(this);
		scenes.add(Main.game);
	}

	@Override
	public void init() {

	}

}

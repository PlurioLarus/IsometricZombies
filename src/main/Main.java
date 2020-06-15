package main;

import java.awt.Dimension;
import java.awt.Toolkit;

import game.Game;
import mainMenu.MainMenu;
import pLib.buttons.ButtonImages;
import pLib.graphics.Font;

public class Main {
	public static int screenWidth;
	public static int screenHeight;
	public static Window window;
	public static Game game;
	public static MainMenu mainMenu;

	public static void main(String[] args) {
		ButtonImages.load();
		Font.load("textures/font.png");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		screenWidth = (int) screenSize.getWidth();
		screenHeight = (int) screenSize.getHeight();
		window = new Window(screenWidth, screenHeight);
	}

}

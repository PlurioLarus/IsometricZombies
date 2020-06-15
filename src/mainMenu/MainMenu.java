package mainMenu;

import java.awt.Color;
import java.awt.Graphics2D;

import buttons.StartButton;
import main.Main;
import main.Window;
import pLib.buttons.ButtonManager;
import pLib.graphics.Font;
import pLib.graphics.Scene;

public class MainMenu extends Scene {
	public ButtonManager bm;

	public MainMenu(Window window) {
		super(window);
		bm = new ButtonManager(window.mouseHandler);
		bm.addButton(new StartButton().activateAt(30, 100));
	}

	@Override
	public void update() {
		bm.update();
	}

	@Override
	public void draw(Graphics2D g) {
		Font.printText(g, "ZombieThing", (Main.screenWidth - Font.getWidthOfText("ZomnbieThing", 10)) / 2, 30, 10,
				Color.RED);
		bm.draw(g);
	}

}

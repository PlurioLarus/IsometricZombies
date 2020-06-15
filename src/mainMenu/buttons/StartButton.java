package mainMenu.buttons;

import main.Main;
import pLib.buttons.MenuButton;

public class StartButton extends MenuButton {

	public StartButton() {
		super(300, 50, "Start");
	}

	@Override
	public void releasedFunction() {
		Main.window.currentScene = 1;
	}

}

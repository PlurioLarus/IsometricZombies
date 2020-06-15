package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (Main.window.currentScene == 0) {
			if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				Main.window.close();
			}
		} else if (Main.window.currentScene == 1) {
			if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				Main.window.currentScene = 0;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

}

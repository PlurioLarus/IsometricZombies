package main;

import java.awt.event.MouseEvent;

import pLib.buttons.Buttoner;

public class MouseHandler extends Buttoner {

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			setLeftButton(true);
		}
		if (e.getButton() == MouseEvent.BUTTON3) {
			setRightButton(true);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (Main.window.currentScene == 0) {
			Main.mainMenu.bm.checkReleased(e.getX(), e.getY());
		}
		if (e.getButton() == MouseEvent.BUTTON1) {
			setLeftButton(false);
		}
		if (e.getButton() == MouseEvent.BUTTON3) {
			setRightButton(false);
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

}

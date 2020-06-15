package game.tiles;

import java.awt.image.BufferedImage;

import pLib.graphics.Animator;

public abstract class Tile {
	private Animator animator;

	public Tile(BufferedImage image) {
		setAnimator(new Animator(image, 1, 1, 1, true));
	}

	public Animator getAnimator() {
		return animator;
	}

	public void setAnimator(Animator animator) {
		this.animator = animator;
	}

}

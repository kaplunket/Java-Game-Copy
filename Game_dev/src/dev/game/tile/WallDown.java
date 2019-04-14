package dev.game.tile;

import java.awt.image.BufferedImage;

import dev.launcher.Assets;

public class WallDown extends Tile {

	public WallDown(int id) {
		super(Assets.wall_down, id);
		// TODO Auto-generated constructor stub
	}
	@Override
	public boolean isSolid() {
		//returns true if impassible
		return true;
	}

}

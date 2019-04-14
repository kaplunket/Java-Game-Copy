package dev.game.tile;

import java.awt.image.BufferedImage;

import dev.launcher.Assets;

public class WallUpTile extends Tile {

	public WallUpTile(int id) {
		super(Assets.wall_up, id);
		// TODO Auto-generated constructor stub
	}
	@Override
	public boolean isSolid() {
		//returns true if impassible
		return true;
	}

}

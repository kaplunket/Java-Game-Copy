package dev.game.tile;

import java.awt.image.BufferedImage;

import dev.launcher.Assets;

public class RockTile extends Tile{

	public RockTile(int id) {
		super(Assets.rock, id);
		// TODO Auto-generated constructor stub
	}
	@Override
	public boolean isSolid() {
		//returns true if impassible
		return true;
	}
}

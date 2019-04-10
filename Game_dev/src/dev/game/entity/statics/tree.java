package dev.game.entity.statics;

import java.awt.Graphics;

import dev.game.Handler;
import dev.game.tile.Tile;
import dev.launcher.Assets;

public class tree extends StaticEntity{
	public tree(Handler handler,float x, float y) {
	super(handler,x,y,Tile.TILEWIDTH/2,Tile.TILEHEIGHT);
	}
	@Override
	public void tick() {
		
	}
	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.rock,(int)(x-handler.getGameCamera().getxOffset()),(int)(y-handler.getGameCamera().getyOffset()),width,height,null);
	}
}

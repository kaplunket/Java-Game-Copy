package dev.game.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import dev.game.Handler;
import dev.game.HUD.HUD;
import dev.game.creatures.Player;
import dev.game.inventory.Weapons.Sword;
import dev.game.sound.Sounds;
import dev.game.tile.Tile;
import dev.game.worlds.World;
import dev.game.worlds.World.WorldType;
import dev.launcher.Assets;

public class GameState extends State{
	
	private World world;
	private HUD hud;
	private boolean paused=false,exit=false;
	//constructor
	public GameState(Handler handler) {
		super(handler);
		stateName="GameState";
		world = new World(handler , "Resources/worlds/world1.txt","Resources/entities/world1.txt",WorldType.NORMAL);
		handler.setWorld(world);
		hud=new HUD(handler);
		handler.setHUD(hud);
	}
	
	@Override
	public void tick() {
		// TODO Auto-generated method stub
		if(exit) {
			if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_ESCAPE)) {
				paused=false;
				exit=false;
			}else if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_ENTER)) {
				System.exit(0);	
			}
		}else if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_ESCAPE)) {
				paused=true;
				exit=true;
		}else if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_P)) {
				paused=!paused;
		}
		if(!paused) {
			if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_PAGE_DOWN)) {
				world.getEntityManager().clear1();
				world.getProjectileManager().clear2();
				world.getItemManager().clear();
				world.loadNewWorld("Resources/Reserve_Data/worlds/world5.txt","Resources/Reserve_Data/entities/world5.txt");
				world.getEntityManager().getPlayer().getWeapons().setPrimary(Sword.OP);
				world.getEntityManager().getPlayer().setCorruption(0);
				world.getEntityManager().getPlayer().setTransformable(true);
				world.getEntityManager().getPlayer().setHealth(world.getEntityManager().getPlayer().getMaxHealth());
			}
		world.tick();
		hud.tick();
		}
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		world.render(g);
		hud.render(g);
		if(paused) {
			g.setColor(Color.WHITE);
			g.fillRect((int) (handler.getWidth()/2-7*60), handler.getHeight()/2-50, 60*14, 90);
			Font f = new Font("Courier", Font.PLAIN,100);
			g.setColor(Color.BLACK);
			g.setFont(f);
			g.drawString("GAME IS PAUSED", (int) (handler.getWidth()/2-7*60), handler.getHeight()/2+30);
			if(exit) {
				g.setColor(Color.WHITE);
				g.fillRect((int) (handler.getWidth()/2-5*60), handler.getHeight()/2+65, 60*10, 35);
				f = new Font("Courier", Font.PLAIN,25);
				g.setColor(Color.BLACK);
				g.setFont(f);
				g.drawString("Press Enter to exit, Press ESC to cancel", (int) (handler.getWidth()/2-5*60), handler.getHeight()/2+90);
			}
		}
	}

}

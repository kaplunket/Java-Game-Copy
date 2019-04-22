package dev.game.worlds;

import java.awt.Graphics;
import java.io.IOException;

import dev.game.Handler;
import dev.game.creatures.Bull;
import dev.game.creatures.Frienemy;
import dev.game.creatures.Player;
import dev.game.creatures.Sheep;
import dev.game.creatures.Wizard;
import dev.game.entity.EntityManager;
import dev.game.entity.projectile.Arrow;
import dev.game.entity.projectile.Projectile;
import dev.game.entity.statics.Rock;
import dev.game.entity.statics.Tree;
import dev.game.item.ItemManager;
import dev.game.item.TransitionItem;
import dev.game.tile.Tile;
import dev.game.utils.Utils;

public class World {
	private Handler handler;
	private int width, height;
	private int entityNum;
	private int spawnX,spawnY;
	private EntityManager entityManager,projectileManager;
	private ItemManager itemManager;
	private int[][] tiles;
	public enum Direction{UP,DOWN,LEFT,RIGHT};
	private String pathWorldTemp,pathEntityTemp;
	private boolean flagToLoad=true,ticking=false;
	private TransitionItem world2,world1;
	private String currentWorldPath="Resources/worlds/world1.txt"
			,currentEntityPath="Resources/entities/world1.txt";
	//constructor
	public World(Handler handler, String worldPath,String entityPath) {
		this.handler = handler;
		projectileManager=new EntityManager(handler,new Sheep(handler,0,0,0,0));
		entityManager=new EntityManager(handler,new Player(handler,0,0,0,0));		
		itemManager= new ItemManager(handler);
		loadNewWorld(worldPath,entityPath);		
	}

	public void tick() {
		ticking=true;
		itemManager.tick();
		entityManager.tick();
		projectileManager.tick();
		ticking=false;
		if(flagToLoad) {
			loadNewWorld(pathWorldTemp,pathEntityTemp);
			pathWorldTemp="";
			pathEntityTemp="";
			flagToLoad=false;
		}
	}
	
	public void render (Graphics g) {
		//only render what is currently shown on screen
		int xStart=(int)Math.max(0, handler.getGameCamera().getxOffset()/Tile.TILEWIDTH);
		int xEnd=(int)Math.min(width, (handler.getGameCamera().getxOffset()+handler.getWidth())/Tile.TILEWIDTH + 1);
		int yStart=(int)Math.max(0, handler.getGameCamera().getyOffset()/Tile.TILEHEIGHT);
		int yEnd=(int)Math.min(height, (handler.getGameCamera().getyOffset()+handler.getHeight())/Tile.TILEHEIGHT + 1);
		for (int y=yStart;y<yEnd;y++) {
			for (int x=xStart;x<xEnd;x++) {
				getTile(x,y).render(g, (int)(x*Tile.TILEWIDTH-handler.getGameCamera().getxOffset()),(int)(y*Tile.TILEHEIGHT-handler.getGameCamera().getyOffset()));
			}
		}
		itemManager.render(g);
		entityManager.render(g);
		projectileManager.render(g);
	}
	
	public Tile getTile(int x, int y) {
		//check if coords is out of bounds to avoid throwing an error
		if(x < 0 || y < 0 || x >= width || y >= height) {
			//if out of bounds return a grass tile
			return Tile.grassTile;
		}
		//get the specific tile from the tile array
		Tile t = Tile.tiles[tiles[x][y]];
		
		//returns a grass tile if no tile in the tiles array
		if(t==null) {return Tile.grassTile;}
		
		
		return t;
	}
	public void loadNewWorld(String pathWorld,String pathEntity) {
		this.currentWorldPath=pathWorld;
		this.currentEntityPath=pathEntity;
		if (!ticking&&flagToLoad) {
			loadWorld(pathWorld);
			loadEntities(pathEntity);
			entityManager.getPlayer().setX(spawnX);
			entityManager.getPlayer().setY(spawnY);
			flagToLoad=false;
		}else {
			flagToLoad=true;
			pathWorldTemp=pathWorld;
			pathEntityTemp=pathEntity;
		}
	}
	private void loadWorld(String path) {
		String file = Utils.loadFileAsString(path);
		String[] tokens = file.split("\\s+");
		width = Utils.parseInt(tokens[0]);
		height = Utils.parseInt(tokens[1]);
		spawnX = Utils.parseInt(tokens[2]);
		spawnY = Utils.parseInt(tokens[3]);
		tiles = new int[width][height];

		for (int y=0;y<height;y++) {
			for (int x=0;x<width;x++) {
				tiles[x][y] = Utils.parseInt(tokens[(x+y*width)+4]);
			}
		}
	}
	
	public void saveWorld() {
		//save tile data may be useful later
		String data=width+" "+height+"\n"+spawnX+" "+spawnY+"\n";
		for (int y=0;y<height;y++) {
			for (int x=0;x<width;x++) {
				data=data+tiles[x][y]+" ";
			}
			data=data+"\n";
		}
		try {
			Utils.saveFileAsString(this.currentWorldPath,data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		data=""+(entityManager.getEntities().size()-1)+"\n";
		for(int y=0;y<entityManager.getEntities().size();y++) {
			data=data+((int)entityManager.getEntities().get(y).getId())+" "+((int)entityManager.getEntities().get(y).getX())+" "+((int)entityManager.getEntities().get(y).getY())+" 0\n";
		}
		try {
			Utils.saveFileAsString(this.currentEntityPath,data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private void loadEntities(String path) {
		String file = Utils.loadFileAsString(path);
		String[] tokens = file.split("\\s+");
		entityNum = Utils.parseInt(tokens[0]);
		int entityType;
		int entitySpawnX;
		int entitySpawnY;
		int entitySpawnDirectionInt;
		Direction entitySpawnDirection=Direction.DOWN;
		for (int y=0;y<entityNum;y++) {
			entityType=Utils.parseInt(tokens[(y*4)+1]);
			entitySpawnX=Utils.parseInt(tokens[(y*4)+2]);
			entitySpawnY=Utils.parseInt(tokens[(y*4)+3]);
			entitySpawnDirectionInt=Utils.parseInt(tokens[(y*4)+3]);
			
			if (entitySpawnDirectionInt==0) {
				entitySpawnDirection=Direction.UP;
			}else if (entitySpawnDirectionInt==1) {
				entitySpawnDirection=Direction.RIGHT;
			}else if (entitySpawnDirectionInt==2) {
				entitySpawnDirection=Direction.DOWN;
			}else if (entitySpawnDirectionInt==3) {
				entitySpawnDirection=Direction.LEFT;}
			
			if (entityType==0) {
				entityManager.addEntity(new Tree(handler,entitySpawnX,entitySpawnY));
			}else if(entityType==1) {
				Sheep sheep= new Sheep(handler,0,0, 100, 100);
				sheep.setX(entitySpawnX);
				sheep.setY(entitySpawnY);
				entityManager.addEntity(sheep);
			}else if(entityType==2) {
				Arrow arrow= new Arrow(handler,0,0);
				arrow.setDirection(entitySpawnDirection);
				arrow.setX(entitySpawnX);
				arrow.setY(entitySpawnY);
				projectileManager.addEntity(arrow);
			}else if(entityType==3) {
				Wizard wizard= new Wizard(handler,0,0,100,100,false);
				wizard.setX(entitySpawnX);
				wizard.setY(entitySpawnY);
				entityManager.addEntity(wizard);
			}else if(entityType==4) {
				Frienemy frienemy= new Frienemy(handler,0,0,100,100);
				frienemy.setX(entitySpawnX);
				frienemy.setY(entitySpawnY);
				entityManager.addEntity(frienemy);

			}else if(entityType==5) {
				entityManager.addEntity(new Rock(handler,entitySpawnX,entitySpawnY));
			}else if(entityType==6) {
				Bull bull= new Bull(handler,0,0,100,100,false);
				bull.setX(entitySpawnX);
				bull.setY(entitySpawnY);
				entityManager.addEntity(bull);
			}
			
			else if(entityType==92) {
				world2= new TransitionItem(handler,entitySpawnX,entitySpawnY,32,48,entityType,"Resources/worlds/world2.txt","Resources/entities/world2.txt");
				world2.setX(entitySpawnX);
				world2.setY(entitySpawnY);
				entityManager.addEntity(world2);
			}
			else if(entityType==91) {
				world1= new TransitionItem(handler,entitySpawnX,entitySpawnY,32,48,entityType,"Resources/worlds/world1.txt","Resources/entities/world1.txt");
				world1.setX(entitySpawnX);
				world1.setY(entitySpawnY);
				entityManager.addEntity(world1);
			}
		}
	}
	//getters and setters
	
	public int getWidth() {
		return width;
	}
	public EntityManager getProjectileManager() {
		return projectileManager;
	}

	public void setProjectileManager(EntityManager projectileManager) {
		this.projectileManager = projectileManager;
	}

	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public ItemManager getItemManager() {
		return itemManager;
	}

	public void setItemManager(ItemManager itemManager) {
		this.itemManager = itemManager;
	}

	public int getHeight() {
		return height;
	}
	public int getSpawnX() {
		return spawnX;
	}
	public void setSpawnX(int spawnX) {
		this.spawnX = spawnX;
	}
	public int getSpawnY() {
		return spawnY;
	}
	public void setSpawnY(int spawnY) {
		this.spawnY = spawnY;
	}
	public EntityManager getEntityManager() {
		return entityManager;
	}
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
}

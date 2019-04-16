package dev.launcher;

import java.awt.image.BufferedImage;

import dev.ImageLoader.Loader;

public class Assets {
	public static BufferedImage 
	tree,player,water,grass,drop,dirt,gameOver,bridge_left,bridge_right,rock,
	wall_left,wall_right,wall_right_down,wall_left_down,wall_down,wall_up,wall_right_up,wall_left_up,
	stone,stone_wall_left,stone_wall_right,stone_wall_up,stone_wall_down,stone_wall_right_down,stone_wall_left_down,stone_wall_left_up,stone_wall_right_up;
	public static BufferedImage[] player_down,player_left,player_right,player_up,player_die,spear;
	public static BufferedImage[] btn_start;
	private static final int width=100,height=100;//sprite sheet cell dimensions
	public static void init() {
		SpriteSheet sheet1= new SpriteSheet(Loader.loadImage("/Textures/gem.png"));
		SpriteSheet sheet2 = new SpriteSheet(Loader.loadImage("/Textures/GameOver1.png")); 
		SpriteSheet sheet3= new SpriteSheet(Loader.loadImage("/Textures/water.png"));
		SpriteSheet sheet4= new SpriteSheet(Loader.loadImage("/Textures/grass.png"));
		SpriteSheet sheet5= new SpriteSheet(Loader.loadImage("/Textures/PlayButton.png"));
		SpriteSheet sheet6= new SpriteSheet(Loader.loadImage("/Textures/PlayButtonSelected.png"));
		SpriteSheet sheet7= new SpriteSheet(Loader.loadImage("/Textures/rock.png"));
		SpriteSheet sheet13= new SpriteSheet(Loader.loadImage("/Textures/dirt.png"));
		SpriteSheet sheet14= new SpriteSheet(Loader.loadImage("/Textures/bridge_left.png"));
		SpriteSheet sheet15= new SpriteSheet(Loader.loadImage("/Textures/bridge_right.png"));
		SpriteSheet sheet17= new SpriteSheet(Loader.loadImage("/Textures/wall_left.png"));
		SpriteSheet sheet18= new SpriteSheet(Loader.loadImage("/Textures/wall_right.png"));
		SpriteSheet sheet19= new SpriteSheet(Loader.loadImage("/Textures/wall_down.png"));
		SpriteSheet sheet20= new SpriteSheet(Loader.loadImage("/Textures/wall_up.png"));
		SpriteSheet sheet21= new SpriteSheet(Loader.loadImage("/Textures/wall_left_down.png"));
		SpriteSheet sheet22= new SpriteSheet(Loader.loadImage("/Textures/wall_right_down.png"));
		SpriteSheet sheet23= new SpriteSheet(Loader.loadImage("/Textures/wall_left_up.png"));
		SpriteSheet sheet24= new SpriteSheet(Loader.loadImage("/Textures/wall_right_up.png"));
		SpriteSheet sheet25= new SpriteSheet(Loader.loadImage("/Sprite/tree.png"));
		SpriteSheet sheet26= new SpriteSheet(Loader.loadImage("/Textures/PlayerSpriteSheet.png")); 
		SpriteSheet sheet27= new SpriteSheet(Loader.loadImage("/Sprite/spear.png")); 
		SpriteSheet sheet28= new SpriteSheet(Loader.loadImage("/Textures/stone.png")); 
		SpriteSheet sheet29= new SpriteSheet(Loader.loadImage("/Textures/stone_wall_left.png")); 
		SpriteSheet sheet30= new SpriteSheet(Loader.loadImage("/Textures/stone_wall_right.png")); 
		SpriteSheet sheet31= new SpriteSheet(Loader.loadImage("/Textures/stone_wall_down.png"));  
		SpriteSheet sheet32= new SpriteSheet(Loader.loadImage("/Textures/stone_wall_up.png"));  
		SpriteSheet sheet33= new SpriteSheet(Loader.loadImage("/Textures/stone_wall_right_down.png"));  
		SpriteSheet sheet34= new SpriteSheet(Loader.loadImage("/Textures/stone_wall_left_down.png"));
		SpriteSheet sheet35= new SpriteSheet(Loader.loadImage("/Textures/stone_wall_right_up.png"));  
		SpriteSheet sheet36= new SpriteSheet(Loader.loadImage("/Textures/stone_wall_left_up.png"));  
		drop=sheet1.crop(0,0,100,100);
		gameOver=sheet2.crop(0, 0, 1024, 768);
		rock=sheet7.crop(0, 0, 100, 100);
		btn_start = new BufferedImage[2];
		btn_start[0]=sheet5.crop(0,0,550,70);
		btn_start[1]=sheet6.crop(0,0,550,70);
		player_down = new BufferedImage[4];
		player_up = new BufferedImage[4];
		player_right = new BufferedImage[4];
		player_left = new BufferedImage[4];
		player_die = new BufferedImage[4];
		player_down[1] = sheet26.crop(0,0, width, height);
		player_down[3] = sheet26.crop(0,0, width, height);
		player_right[1] = sheet26.crop(width,0, width, height);
		player_right[3] = sheet26.crop(width,0, width, height);
		player_left[1] = sheet26.crop(width*2,0, width, height);
		player_left[3] = sheet26.crop(width*2,0, width, height);
		player_up[1] = sheet26.crop(width*3,0, width, height);
		player_up[3] = sheet26.crop(width*3,0, width, height);
		player_down[0]=sheet26.crop(0,height,width,height);
		player_right[0]=sheet26.crop(width,height,width,height);
		player_left[0]=sheet26.crop(width*2,height,width,height);
		player_up[0]=sheet26.crop(width*3,height,width,height);
		player_down[2]=sheet26.crop(0,2*height,width,height);
		player_right[2]=sheet26.crop(width,2*height,width,height);
		player_left[2]=sheet26.crop(width*2,2*height,width,height);
		player_up[2]=sheet26.crop(width*3,2*height,width,height);
		player_die[0] = sheet26.crop(0,4*height, width, height);
		player_die[1] = sheet26.crop(width,4*height, width, height);
		player_die[2] = sheet26.crop(width*3,0, width, height);
		player_die[3] = sheet26.crop(width*2,4*height, width, height);
		player = sheet26.crop(0, 0, width, height);
		spear = new BufferedImage[4];
		spear[0] = sheet27.crop(0, 0, width, height);
		spear[1] = sheet27.crop(width, 0, width, height);
		spear[2] = sheet27.crop(width*2, 0, width, height);
		spear[3] = sheet27.crop(width*3, 0, width, height);
		water = sheet3.crop(0, 0, 1000, 1000);
		grass = sheet4.crop(0, 0, 1000, 1000);
		dirt = sheet13.crop(0, 0, 1000, 1000);
		bridge_left = sheet14.crop(0, 0, 100, 100);
		bridge_right = sheet15.crop(0, 0, 100, 100);
		wall_left = sheet17.crop(0, 0, 1000, 1000);
		wall_right = sheet18.crop(0, 0, 1000, 1000);
		wall_down = sheet19.crop(0, 0, 1000, 1000);
		wall_up = sheet20.crop(0, 0, 1000, 1000);
		wall_left_down = sheet21.crop(0, 0, 1000, 1000);
		wall_right_down = sheet22.crop(0, 0, 1000, 1000);
		wall_left_up = sheet23.crop(0, 0, 1000, 1000);
		wall_right_up = sheet24.crop(0, 0, 1000, 1000);
		tree = sheet25.crop(0,0,150,200);
		stone = sheet28.crop(0, 0, 100, 100);
		stone_wall_left = sheet29.crop(0, 0, 100, 100);
		stone_wall_right = sheet30.crop(0, 0, 100, 100);
		stone_wall_down = sheet31.crop(0, 0, 100, 100);
		stone_wall_up = sheet32.crop(1, 0, 99, 100);
		stone_wall_right_down = sheet33.crop(0, 0, 1000, 1000);
		stone_wall_left_down = sheet34.crop(0, 0, 1000, 1000);
		stone_wall_right_up = sheet35.crop(1, 0, 99, 100);
		stone_wall_left_up = sheet36.crop(0, 0, 100, 100);
	}
}

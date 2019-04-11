package dev.launcher;

import java.awt.image.BufferedImage;

import dev.ImageLoader.Loader;

public class Assets {
	public static BufferedImage sprite1,sprite2,sprite3,sprite4,player,rock,grass,drop;
	public static BufferedImage[] player_down,player_left,player_right,player_up;
	public static BufferedImage[] btn_start;
	private static final int width=100,height=100;//sprite sheet cell dimensions
	public static void init() {
		SpriteSheet sheet1= new SpriteSheet(Loader.loadImage("/Textures/K13.png"));
		SpriteSheet sheet2= new SpriteSheet(Loader.loadImage("/Textures/Player_standing_front.png"));
		SpriteSheet sheet3= new SpriteSheet(Loader.loadImage("/Textures/rock.png"));
		SpriteSheet sheet4= new SpriteSheet(Loader.loadImage("/Textures/grass.jpeg"));
		SpriteSheet sheet5= new SpriteSheet(Loader.loadImage("/Textures/Player_walking_back_1.png"));
		SpriteSheet sheet6= new SpriteSheet(Loader.loadImage("/Textures/Player_walking_back_2.png"));
		SpriteSheet sheet7= new SpriteSheet(Loader.loadImage("/Textures/Player_walking_front_1.png"));
		SpriteSheet sheet8= new SpriteSheet(Loader.loadImage("/Textures/Player_walking_front_2.png"));
		SpriteSheet sheet9= new SpriteSheet(Loader.loadImage("/Textures/Player_walking_right_1.png"));
		SpriteSheet sheet10= new SpriteSheet(Loader.loadImage("/Textures/Player_walking_right_2.png"));
		SpriteSheet sheet11= new SpriteSheet(Loader.loadImage("/Textures/Player_walking_left_1.png"));
		SpriteSheet sheet12= new SpriteSheet(Loader.loadImage("/Textures/Player_walking_left_2.png"));
		drop=sheet1.crop(0,0,1048,768);
		btn_start = new BufferedImage[2];
		btn_start[0]=sheet3.crop(0,0,width,height);
		btn_start[1]=sheet4.crop(0,0,width,height);
		player_down = new BufferedImage[2];
		player_up = new BufferedImage[2];
		player_right = new BufferedImage[2];
		player_left = new BufferedImage[2];
		player_up[0]=sheet5.crop(0,0,width,height);
		player_up[1]=sheet6.crop(0,0,width,height);
		player_down[0]=sheet7.crop(0,0,width,height);
		player_down[1]=sheet8.crop(0,0,width,height);
		player_right[0]=sheet9.crop(0,0,width,height);
		player_right[1]=sheet10.crop(0,0,width,height);
		player_left[0]=sheet11.crop(0,0,width,height);
		player_left[1]=sheet12.crop(0,0,width,height);
		sprite1 = sheet1.crop(0, 0, width, height);
		sprite2 = sheet1.crop(width, 0, width, height);
		sprite3 = sheet1.crop(width*2, 0, width, height);
		sprite4 = sheet1.crop(width*3, 0, width, height);
		player = sheet2.crop(0, 0, width, height);
		rock = sheet3.crop(0, 0, 259, 194);
		grass = sheet4.crop(0, 0, 275, 183);
	}
}

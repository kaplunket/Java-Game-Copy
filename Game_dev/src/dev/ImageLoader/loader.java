package dev.ImageLoader;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class loader {
	
	public static BufferedImage loadImage(String path) {
		try {
			return ImageIO.read(loader.class.getResource(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}
}

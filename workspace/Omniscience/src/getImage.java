import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class getImage {
	
	public getImage(Universe u) {
		BufferedImage buff = new BufferedImage(u.universe.length, u.universe[0].length, BufferedImage.TYPE_INT_RGB);
		int cellRGB = 0;
		for (int i = 0; i < u.universe.length; i++) {
			for (int j = 0; j < u.universe[0].length; j++) {
				
				if(u.universe[i][j][0] == 0) {
					cellRGB = 0xFFFFFF;
				} else {cellRGB = 0;}
				
				buff.setRGB(i, j, cellRGB);
			}
		}
		
		try {
			ImageIO.write(buff, "bmp", new File("Image.bmp"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}

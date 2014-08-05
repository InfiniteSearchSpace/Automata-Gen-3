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
				/*cellRGB = 0xFFFFFF;
				if(u.universe[i][j][0] == 0) {cellRGB = 0x444444;}
				if(u.universe[i][j][0] == 1) {cellRGB = 0x00FFFF;}*/
				
				float hue = (float)u.universe[i][j][0]/(float)u.maxVal;
        		float antihue = (float)u.universe[i][j][0]/(float)u.minVal;
        			        		
        		Color c;
        		
        		if(u.universe[i][j][0] < 0) {
        			cellRGB = Color.HSBtoRGB((float) antihue, (float)1, (float)1);
        		} else if(u.universe[i][j][0]==0) {
        			cellRGB = Color.HSBtoRGB((float) 0, (float)0, (float)0);
        		} else {
        			cellRGB = Color.HSBtoRGB((float) hue, (float)1, (float)1);
        		}
        		
        		
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

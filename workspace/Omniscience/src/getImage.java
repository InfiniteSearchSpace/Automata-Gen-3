import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;


public class getImage {
	
	public getImage(Universe u) {
		BufferedImage buff = new BufferedImage(u.universe.length, u.universe[0].length, BufferedImage.TYPE_INT_RGB);
		int cellRGB = 0;
		for (int i = 0; i < u.universe.length; i++) {
			for (int j = 0; j < u.universe[0].length; j++) {
	
	        	float ageLimit = 16;	
	        	float univ = (float)u.universe[i][j][0];
        		float mxv = (float)u.maxVal;
        		
        		if(mxv > ageLimit){ mxv = ageLimit;}
        		
	        	float hue = univ/mxv;
	        	float antihue = univ/(float)u.minVal;	
	        	
	        	
	        	
        		if(u.maxVal > ageLimit && univ > ageLimit) {
        			cellRGB = Color.HSBtoRGB(1, 0, 1);
        		} else if(u.universe[i][j][0] < 0) {
        			cellRGB = Color.HSBtoRGB((float) antihue, (float)1, (float)1);
        		} else if(u.universe[i][j][0]==0) {
        			cellRGB = Color.HSBtoRGB((float) 0, (float)0, (float)0);
        		} else if(mxv == 1 && univ > 0) {
        			cellRGB= Color.HSBtoRGB(1, 0, 1);
        		} else {
        			cellRGB = Color.HSBtoRGB((float) hue, (float)1, (float)1);
        		}
        		
        		
        		
				buff.setRGB(i, j, cellRGB);
			}
		}
		
		
		try {
			Random r = new Random();
			String imageName = "";
			for (int i = 0; i < 6; i++) {
				imageName = r.nextInt(10) + imageName;
			}
			
			ImageIO.write(buff, "bmp", new File(imageName+".bmp"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}

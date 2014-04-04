import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;


@SuppressWarnings("serial")
public class Surf extends JPanel {

	
	//reference coordinates
	int xx; 
	int yy;
	int zz;
	
	//Display colour contianers
    int col1 = 0;
    int col2 = 0;
    int col3 = 0; 						//RGB colour ints
    float[] floats = new float[3]; 		//holds rgb -> hsb conversion vals  
    double pGradient; 					//used for calculation of the gradient
    
    //config
    public boolean paused = true; 		//boolean that vetos display progression
    public boolean upaused = true; 		//boolean that vetos universe progression
    int zdraw = 0; 						//which z layer do I show, if z is a fixed reference?
    
    Universe u; 						//main logic container
    
    /***********************************************************************************************/
    
    
    //constructor
    public Surf(int myX, int myY, int myZ, int xloc, int yloc, Universe uni) { 

    	//set display reference
    	u = uni;
    	
    	//Define display size
    	xx=myX;
    	yy=myY;
    	zz=myZ;
    	
    	//set up the display
    	setSize(myX, myY);
    	setLocation(xloc, yloc);
    }
    

    //main display output
    private void doDrawing(Graphics g) { 
    	//Begin Draw configuration
    	
    	if(!upaused) {u.updateUniverse();} 	//perform a full cycle of logical iteration
    	
	    u.maxValAudit(zdraw); 				//only audit maxval for this z-value
	    Graphics2D g2d = (Graphics2D) g; 	//some sort of magic.
	    /**/int k=zdraw;/**/ 				//for fixed z-value, processes in layers
	       
	    for (int i = 0; i < xx; i++) {
	        for (int j = 0; j < yy; j++) {
	        	
	            //calculate positive-value gradient: white < grey < black
		        pGradient = 255-(((double) u.universe[i][j][k]/u.maxVal)*255);
		        col1=(int) pGradient;
		                    
		        //give individual colours to specific values
		        if(u.universe[i][j][k]==0) {col3=185; col2=195; col1=215;} else if(u.universe[i][j][k]<0) {col3=0; col2=0; col1=255;} else {col2=col1; col3=col1;}
		        if(u.universe[i][j][k]==1 && u.maxVal != 1) {col3=255; col2=255; col1=180;}
		                    
		        //prepare to draw
		        floats = Color.RGBtoHSB(col1, col2, col3, floats);
		        g2d.setColor(Color.getHSBColor(floats[0],floats[1],floats[2]));
		                    
		        //draw
		        g2d.drawLine(i,j,i,j);

	        }
	    }
	        
	    //draw and recurve
	    if(!paused && upaused) {
	    	paused = true;
	    	repaint();
	    } else { repaint(); }
       //End Drawing cycle
    }
   
    
    //repaint() calls this?
    @Override
    public void paintComponent(Graphics g) { 
    	//Begin paintComponent output
        if(!paused){super.paintComponent(g); 	//What is super?        
        doDrawing(g);}				//Perform/begin next update
        //System.out.println("End paintComponent");
    }
    
}
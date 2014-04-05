

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.JLabel;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class ml extends JPanel implements MouseListener {

	//create containers for reference objects
	Main m;
	Surf[] s;
	JLabel l;
	Point p;
	Point p2;
	Universe u[];
	
	int sfcnum = 0;			//index of current active/interactable surface
	int sfcmax;				//total number of surfaces to cycle through
	int myFunction = 1;  	//reference for current mouse function on click
	int functionType = 0;	//Determines subclass of functions to execute, like a menu.
	
	int fcnt = 5;			//total number of mouse functions to iterate through
	int fnctype = 5;		//tot number of function catagorys
	
	//constructor
    public ml(Main mm, Surf[] ss, JLabel ll, Universe uni[]) {
    	//System.out.println("ml");
    	m=mm;
    	s=ss;
    	l=ll;
    	u=uni;
    	
        m.addMouseListener(this);
        
        sfcmax=s.length;
        functionType = 0;
    }
    
    //do I need this?
    void eventOutput(String eventDescription, MouseEvent e) {
        System.out.println(eventDescription + " detected on "
                + e.getComponent().getClass().getName()
                + ".");
    }
    
    public void mousePressed(MouseEvent e) {
    	
    	//get location information from mouse & jframes
    	p = MouseInfo.getPointerInfo().getLocation();
    	p2 = m.getLocation();
    	int mx =(p.x - p2.x - 2 - s[sfcnum].getX());
    	int my =(p.y - p2.y - 24 - s[sfcnum].getY());
    	
    	//Left Click
    	 if(e.getButton() == MouseEvent.BUTTON1) {
    	
	    	//When subfunction 0 is active, always scroll through z/depth of this surface
	    	if (myFunction == 0) {  
				s[sfcnum].zdraw++;
				s[sfcnum].zdraw = s[sfcnum].zdraw % s[sfcnum].zz;
			}
	    	
	    	
	    	//Function type/catagory 0's options
	    	if(functionType == 0) {
	    		if (myFunction ==  1) { //Place One
	        		s[sfcnum].u.a.seed(mx, my, s[sfcnum].zdraw, 1, 0, 1);
	        	}
	    		
	    		if (myFunction ==  2) { //Place Block Small
	    	    	int num = 3;
	    			for(int i = 0; i < num*2; i++){
	    				s[sfcnum].u.a.placeLine(mx-num, (my+i)-num, s[sfcnum].zdraw, 1, mx+num, false, 1, 1);
	    			}
	        	}
	    		
	    		if (myFunction ==  3) { //Place block medium 
	    	    	int num = 8;
	    			for(int i = 0; i < num*2; i++){
	    				s[sfcnum].u.a.placeLine(mx-num, (my+i)-num, s[sfcnum].zdraw, 1, mx+num, false, 1, 1);
	    			}
	        	}
	    		
	    		if (myFunction ==  4) { //Place Block Big
	    	    	int num = 14;
	    			for(int i = 0; i < num*2; i++){
	    				s[sfcnum].u.a.placeLine(mx-num, (my+i)-num, s[sfcnum].zdraw, 1, mx+num, false, 1, 1);
	    			}
	        	}
	    	}
	
	    	//Function type/catagory 1's options
	    	if(functionType == 1) {
	    		if (myFunction ==  1) { //chance block random Light			
	    			int num = 16;
	    			for(int i = 0; i < num*2; i++){
	    	    		s[sfcnum].u.a.placeLine(mx-num, (my+i)-num, s[sfcnum].zdraw, 64, mx+num, false,1, 1);
	    	    	}
	    		}
	    		
	    		if (myFunction ==  2) { //chance block random Heavy
	    			int num = 16;
	    			for(int i = 0; i < num*2; i++){
	    	    		s[sfcnum].u.a.placeLine(mx-num, (my+i)-num, s[sfcnum].zdraw, 3, mx+num, false, 1, 1);
	    	    	}
	    		}
	    		
	    		if (myFunction ==  3) { //Place Big Block Horizontal 3-step Stripes
	    	    	int num = 24;
	    			for(int i = 0; i < num*2; i+=3){
	    				s[sfcnum].u.a.placeLine(mx-num, (my+i)-num, s[sfcnum].zdraw, 1, mx+num, false, 1, 1);
	    			}
	        	}
	    		
	    		if (myFunction ==  4) { //Place medium Block 2-step Stripes
	    	    	int num = 10;
	    			for(int i = 0; i < num*2; i+=2){
	    				s[sfcnum].u.a.placeLine(mx-num, (my+i)-num, s[sfcnum].zdraw, 1, mx+num, false, 1, 1);
	    			}
	        	}
	    	}
	    	
	    	//Function type/catagory 2's options
	    	if(functionType == 2) {
	    		if (myFunction == 1) { //Set to 0, one
	    			s[sfcnum].u.a.placeval(mx, my, s[sfcnum].zdraw, 1, 0);
	    		}
	    		
	    		if (myFunction ==  2) { //chance Set to 0, large
	    			int num = 12;
	    			for(int i = 0; i < num*2; i++){
	    		    	s[sfcnum].u.a.placeLine(mx-num, (my+i)-num, s[sfcnum].zdraw, 4, mx+num, false, 1, 1);
	    		    }
	    		}
	    		
	    		if (myFunction ==  3) { //Set to 0, medium
	    			int num = 6;
	    			for(int i = 0; i < num*2; i++){
	    	    		s[sfcnum].u.a.placeLine(mx-num, (my+i)-num, s[sfcnum].zdraw, 1, mx+num, false, 1, 1);
	    	    	}
	    		}
	    		
	    		if (myFunction ==  4) { //Set to 0, large
	    			int num = 16;
	    			for(int i = 0; i < num*2; i++){
	    	    		s[sfcnum].u.a.placeLine(mx-num, (my+i)-num, s[sfcnum].zdraw, 1, mx+num, false, 1, 1);
	    	    	}
	    		}
	    		
	    	}
	
	    	//Function type/catagory 3's options
			if(functionType == 3) {
				if (myFunction ==  1) { //big block, value 100
					int num = 16;
					for(int i = 0; i < num*2; i++){
						s[sfcnum].u.a.placeLine(mx-num, (my+i)-num, s[sfcnum].zdraw, 3, mx+num, false, 1, 100);
					}
				}
				
				if (myFunction ==  2) { //small block, value 100	
					int num = 3;
					for(int i = 0; i < num*2; i++){
						s[sfcnum].u.a.placeLine(mx-num, (my+i)-num, s[sfcnum].zdraw, 1, mx+num, false, 1, 100);
					}
				}
			}
		
			//Function type/catagory 4's options
			if(functionType == 4) {
				if (myFunction ==  1) { //Scroll through surfaces
					sfcnum++;
					sfcnum = sfcnum % sfcmax;
				}
				
				if (myFunction ==  2) { //Reset current z-layer
					s[sfcnum].u.resetArZ(0,s[sfcnum].zdraw);
				}
				
				if (myFunction ==  3) { //Change instruction set to a random instruction
					
					Random r = new Random();
					
					u[0].instructions = new int[][] {
						//Action,		Z,		RAND,					THRESHOLD,			Value
						{r.nextInt(24), -1, 	r.nextInt(64)+1, 		r.nextInt(64)+1},					//random function
	            		{-1, 			-1, 	r.nextInt(10000)+1, 	r.nextInt(64)+1, 	r.nextInt(3)}	//seed
	                }; 
					
				}
				
				if (myFunction == 4) { //Change instruction set to the next instruction
					
					Random r = new Random();
					int instr = u[0].instructions[0][0]; //get current instruction
					
					u[0].instructions = new int[][] {
						//Action,			Z,		RAND,					THRESHOLD,			Value
						{ (instr+1) % 24,	-1, 	r.nextInt(64)+1, 		r.nextInt(64)+1}
	                }; 
					
				}
			}
			
			//updates menu listing
			updateListing();
	    	
	    	//This makes blocks placed while paused visible
	    	if(s[sfcnum].upaused) {
	    		if(s[sfcnum].paused) { 
	    			s[sfcnum].paused = false; 
	    		} 
	    	}
	    	
	    	//refreshes elements that may have been altered
	    	s[sfcnum].repaint();
	    	
    	}
    	 
    	//Middle Click - Pause controller
     	if(e.getButton() == MouseEvent.BUTTON2) { 
     		functionType++;
     		functionType=functionType%fnctype;
     		updateListing();
     	}
    	 
    	//Right Click - Pause controller
    	if(e.getButton() == MouseEvent.BUTTON3) { 
    		toggleStart();
    	}
    }
    
    //identifies the mousefunction action that is highlighted
    public void updateListing(){
    	String tmps = "";
    	
    	//convert pause state to string
    	for(int i = 0; i < s.length; i++) {
    		if(s[i].paused == true) {tmps+="1";} else {tmps+="0";}
    		if(s[i].upaused == true) {tmps+="1";} else {tmps+="0";}
    	}
    	
    	//output as string the current menu state
    	l.setText(sfcnum + "." + s[sfcnum].zdraw + "."+ functionType + "." + myFunction + "." + tmps);
    	
    }
    
    //called during initial program configuration, or to pause/unpause on right click
    public void toggleStart() {
    	if(s[sfcnum].paused ) { s[sfcnum].paused = false; } else { s[sfcnum].paused = true; }
		if(s[sfcnum].upaused ) { s[sfcnum].upaused = false; } else { s[sfcnum].upaused = true; }
		
    	
    	updateListing();
    	s[sfcnum].repaint();
    	
    	if(s[sfcnum].upaused) {
    		if(s[sfcnum].paused) { 
    			s[sfcnum].paused = false; 
    		} 
    	}
    	
    	
    	
    }
    
    public void autoZ() {
        s[sfcnum].zdraw++;
    	s[sfcnum].zdraw = s[sfcnum].zdraw % s[sfcnum].zz;
    }
    
    //called from class mwl, scrolls subfunction on mousewheel event
    public void mouseWheelScrolled(int mw_rotation) {

    	if(mw_rotation == -1) { 
			myFunction++;
			myFunction=myFunction%fcnt; 
		}
    	
    	if(mw_rotation == 1) { 
			myFunction+=(fcnt-1);
			myFunction=myFunction%fcnt; 
		}

	    updateListing();
    }
    
    //implicit function storage
    ////////////////////////////////////////////
    public void mouseReleased(MouseEvent e) {    }
    public void mouseEntered(MouseEvent e) {    }
    public void mouseExited(MouseEvent e) {    }
    public void mouseClicked(MouseEvent e) {    }
    ////////////////////////////////////////////

}
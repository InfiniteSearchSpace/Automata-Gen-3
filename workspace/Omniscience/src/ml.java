

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
	
	int fcnt = 4;			//total number of mouse functions to iterate through
	int fnctype = 5;		//tot number of function catagorys
	
	int mwPos = 0;			//Everyone references the MW cycle
	int mwMax = fcnt;		//default position in the MW cycle, will change frequently
	int cycleNum = 0;		//for Mousewheel to identify which type to cycle
	
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
    	//refresh();
    	//get location information from mouse & jframes
    	p = MouseInfo.getPointerInfo().getLocation();
    	p2 = m.getLocation();
    	int mx =(p.x - p2.x - 2  - s[sfcnum].getX());
    	int my =(p.y - p2.y - 24 - m.getJMenuBar().getHeight() - s[sfcnum].getY());

    	//Left Click
    	 if(e.getButton() == MouseEvent.BUTTON1 && (mx > 0 && my > 0) && (mx < s[sfcnum].getWidth() && my < s[sfcnum].getHeight()) ) {
    	
	    	//When subfunction 0 is active, always scroll through z/depth of this surface
	    	/*if (myFunction == 0) {  
				s[sfcnum].zdraw++;
				s[sfcnum].zdraw = s[sfcnum].zdraw % s[sfcnum].zz;
			}	    	*/
	    	
	    	//Function type/catagory 0's options
	    	if(functionType == 0) {
	    		if (myFunction ==  0) { //Place One
	        		s[sfcnum].u.a.seed(mx, my, s[sfcnum].zdraw, 1, 0, 1);
	        	}
	    		
	    		if (myFunction ==  1) { //Place Block Small
	    	    	int num = 3;
		    	    if(mx-num > 0 && my-num > 0 && mx+num < s[sfcnum].getWidth() && my+num < s[sfcnum].getHeight()) {
	    			for(int i = 0; i < num*2; i++){
	    				s[sfcnum].u.a.placeLine(mx-num, (my+i)-num, s[sfcnum].zdraw, 1, mx+num, false, 1, 1);
	    			}
		    	    }
	        	}
	    		
	    		if (myFunction ==  2) { //Place block medium 
	    	    	int num = 8;
		    	    if(mx-num > 0 && my-num > 0 && mx+num < s[sfcnum].getWidth() && my+num < s[sfcnum].getHeight()) {
	    			for(int i = 0; i < num*2; i++){
	    				s[sfcnum].u.a.placeLine(mx-num, (my+i)-num, s[sfcnum].zdraw, 1, mx+num, false, 1, 1);
	    			}
		    	    }
	        	}
	    		
	    		if (myFunction ==  3) { //Place Block Big
	    	    	int num = 14;
		    	    if(mx-num > 0 && my-num > 0 && mx+num < s[sfcnum].getWidth() && my+num < s[sfcnum].getHeight()) {
	    			for(int i = 0; i < num*2; i++){
	    				s[sfcnum].u.a.placeLine(mx-num, (my+i)-num, s[sfcnum].zdraw, 1, mx+num, false, 1, 1);
	    			}
		    	    }
	        	}
	    	}
	
	    	//Function type/catagory 1's options
	    	if(functionType == 1) {
	    		if (myFunction ==  0) { //chance block random Light			
	    			int num = 16;
		    	    if(mx-num > 0 && my-num > 0 && mx+num < s[sfcnum].getWidth() && my+num < s[sfcnum].getHeight()) {
	    			for(int i = 0; i < num*2; i++){
	    	    		s[sfcnum].u.a.placeLine(mx-num, (my+i)-num, s[sfcnum].zdraw, 64, mx+num, false,1, 1);
	    	    	}
		    	    }
	    		}
	    		
	    		if (myFunction ==  1) { //chance block random Heavy
	    			int num = 16;
		    	    if(mx-num > 0 && my-num > 0 && mx+num < s[sfcnum].getWidth() && my+num < s[sfcnum].getHeight()) {
	    			for(int i = 0; i < num*2; i++){
	    	    		s[sfcnum].u.a.placeLine(mx-num, (my+i)-num, s[sfcnum].zdraw, 3, mx+num, false, 1, 1);
	    	    	}
		    	    }
	    		}
	    		
	    		if (myFunction ==  2) { //Place Big Block Horizontal 3-step Stripes
	    	    	int num = 24;
		    	    if(mx-num > 0 && my-num > 0 && mx+num < s[sfcnum].getWidth() && my+num < s[sfcnum].getHeight()) {
	    			for(int i = 0; i < num*2; i+=3){
	    				s[sfcnum].u.a.placeLine(mx-num, (my+i)-num, s[sfcnum].zdraw, 1, mx+num, false, 1, 1);
	    			}
		    	    }
	        	}
	    		
	    		if (myFunction ==  3) { //Place medium Block 2-step Stripes
	    	    	int num = 10;
		    	    if(mx-num > 0 && my-num > 0 && mx+num < s[sfcnum].getWidth() && my+num < s[sfcnum].getHeight()) {
	    			for(int i = 0; i < num*2; i+=2){
	    				s[sfcnum].u.a.placeLine(mx-num, (my+i)-num, s[sfcnum].zdraw, 1, mx+num, false, 1, 1);
	    			}
		    	    }
	        	}
	    	}
	    	
	    	//Function type/catagory 2's options
	    	if(functionType == 2) {
	    		if (myFunction == 1) { //Set to 0, one
	    			s[sfcnum].u.a.placeval(mx, my, s[sfcnum].zdraw, 1, 0);
	    		}
	    		
	    		if (myFunction ==  1) { //chance Set to 0, large
	    			int num = 12;
		    	    if(mx-num > 0 && my-num > 0 && mx+num < s[sfcnum].getWidth() && my+num < s[sfcnum].getHeight()) {
	    			for(int i = 0; i < num*2; i++){
	    		    	s[sfcnum].u.a.placeLine(mx-num, (my+i)-num, s[sfcnum].zdraw, 6, mx+num, false, 1, 0);
	    		    }
		    	    }
	    		}
	    		
	    		if (myFunction ==  2) { //Set to 0, medium
	    			int num = 6;
		    	    if(mx-num > 0 && my-num > 0 && mx+num < s[sfcnum].getWidth() && my+num < s[sfcnum].getHeight()) {
	    			for(int i = 0; i < num*2; i++){
	    	    		s[sfcnum].u.a.placeLine(mx-num, (my+i)-num, s[sfcnum].zdraw, 1, mx+num, false, 1, 0);
	    	    	}
		    	    }
	    		}
	    		
	    		if (myFunction ==  3) { //Set to 0, large
	    			int num = 16;
		    	    if(mx-num > 0 && my-num > 0 && mx+num < s[sfcnum].getWidth() && my+num < s[sfcnum].getHeight()) {
	    			for(int i = 0; i < num*2; i++){
	    	    		s[sfcnum].u.a.placeLine(mx-num, (my+i)-num, s[sfcnum].zdraw, 1, mx+num, false, 1, 0);
	    	    	}
		    	    }
	    		}
	    		
	    	}
	
	    	//Function type/catagory 3's options
			if(functionType == 3) {
				if (myFunction ==  0) { //big block, value 100
					int num = 2;
					if(mx-num > 0 && my-num > 0 && mx+num < s[sfcnum].getWidth() && my+num < s[sfcnum].getHeight()) {
						for(int i = 0; i < num*2; i++){
							s[sfcnum].u.a.placeLine(mx-num, (my+i)-num, s[sfcnum].zdraw, 1, mx+num, false, 1, 100);
						}
					}
				}
				
				if (myFunction ==  1) { //small block, value 100	
					int num = 8;
					if(mx-num > 0 && my-num > 0 && mx+num < s[sfcnum].getWidth() && my+num < s[sfcnum].getHeight()) {
						for(int i = 0; i < num*2; i++){
							s[sfcnum].u.a.placeLine(mx-num, (my+i)-num, s[sfcnum].zdraw, 3, mx+num, false, 1, 100);
						}
					}
				}
				
				if (myFunction ==  2) { //small block, value 100	
					int num = 14;
					if(mx-num > 0 && my-num > 0 && mx+num < s[sfcnum].getWidth() && my+num < s[sfcnum].getHeight()) {
						for(int i = 0; i < num*2; i++){
							s[sfcnum].u.a.placeLine(mx-num, (my+i)-num, s[sfcnum].zdraw, 1, mx+num, false, 1, 100);
						}
					}
				}
				
				if (myFunction ==  3) { //small block, value 100	
		    	    int num = 18;
		    	    if(mx-num > 0 && my-num > 0 && mx+num < s[sfcnum].getWidth() && my+num < s[sfcnum].getHeight()) {
			    		for(int i = 0; i < num*2; i+=3){
			    			s[sfcnum].u.a.placeLine(mx-num, (my+i)-num, s[sfcnum].zdraw, 1, mx+num, false, 1, 100);
			        	}
		    	    }
				}
			
			}
		
			//Function type/catagory 4's options
			if(functionType == 4) {
				if (myFunction ==  0) { //Scroll through surfaces
					sfcnum++;
					sfcnum = sfcnum % sfcmax;
				}

				if (myFunction ==  1) { //Change instruction set to a random instruction
					setRandom_3_InstructionWithSeed();
				}
				
			}

			
			//Function type/catagory 5's options
			/*if(functionType == 5) {
				if (myFunction ==  1) { //
					s[sfcnum].u.a.seedAll(4, 1);
				}
				
				if (myFunction ==  2) { //
					
				}
				
				if (myFunction ==  3) { //

				}
				
				if (myFunction == 4) { //

				}
			}*/
			
			
			
	    	
    	}
    	 
    	//Middle Click - Function Type
     	if(e.getButton() == MouseEvent.BUTTON2) { 
     		functionType++;
     		functionType=functionType%fnctype;
     		updateListing();
     	}
    	 
    	//Right Click - Pause controller
    	if(e.getButton() == MouseEvent.BUTTON3) { 
    		toggleStart();
    	}
    	
    	refresh();
    }
    
    //identifies the mousefunction action that is highlighted
    public void updateListing(){
    	String tmps = "";
    	String tmps2 = "";
    	//convert pause state to string
    	for(int i = 0; i < s.length; i++) {
    		if(s[i].paused == true) {tmps+="1";} else {tmps+="0";}
    		if(s[i].upaused == true) {tmps+="1";} else {tmps+="0";}
    	}
    	
    	
    	for(int i = 0; i < u[0].instructions.length; i++) {
    		tmps2 += u[0].instructions[i][0];
    		if(i < u[0].instructions.length-1) {tmps2 += ",";}
    	}
      	
    	//output as string the current menu state
    	l.setText(sfcnum + "." + s[sfcnum].zdraw + "."+ functionType + "." + myFunction + "." + tmps + " {" + tmps2 + "}");
    	
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
			mwPos++;
			mwPos=mwPos%mwMax; 
		}
    	
    	if(mw_rotation == 1) { 
			mwPos+=(mwMax-1);
			mwPos=mwPos%mwMax; 
		}
    	
    	setCycle();
    	
	    updateListing();
    }
    
    public void setCycle(){
    	if(cycleNum == 0) {myFunction = mwPos;}
    	if(cycleNum == 1) {s[sfcnum].zdraw = mwPos; refresh();}
    	if(cycleNum == 2) {setRule(mwPos);}
    }
   /* public void mouseWheelScrolled(int mw_rotation) {

    	if(mw_rotation == -1) { 
			myFunction++;
			myFunction=myFunction%fcnt; 
		}
    	
    	if(mw_rotation == 1) { 
			myFunction+=(fcnt-1);
			myFunction=myFunction%fcnt; 
		}

	    updateListing();
    }*/
    
    public void setRule(int ru) {
    	Random r = new Random();
		u[0].instructions = new int[][] {
			{ru, -1, r.nextInt(64)+1, r.nextInt(64)+1}
		};
    }
    
    
    public void eraseAll(){
    	for(int i = 0; i < s[sfcnum].zz; i++) {
    		s[sfcnum].u.resetArZ(0,i);
    	}
    }
    
    public void reseedAll(int rand, int val) {
    	eraseAll();
    	s[sfcnum].u.a.seedAll(4, 1);
    }
    
    public void refresh() {
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
    
    public void setFunctionType(int funct) {
     	functionType=funct;
     	updateListing();
    }
    
    public void setRandom_3_InstructionWithSeed(){
    	Random r = new Random();
		
		u[0].instructions = new int[][] {
			//Action,		Z,		RAND,					THRESHOLD,			Value
			{r.nextInt(24), -1, 	r.nextInt(64)+1, 		r.nextInt(64)+1},					//random function
			{r.nextInt(24), -1, 	r.nextInt(64)+1, 		r.nextInt(64)+1},					//random function
			{r.nextInt(24), -1, 	r.nextInt(64)+1, 		r.nextInt(64)+1},					//random function
    		{-1, 			-1, 	r.nextInt(100000)+1, 	r.nextInt(64)+1, 	r.nextInt(3)}	//seed
        }; 
    }
    
    public void setRandomRule(){
    	Random r = new Random();
		
		u[0].instructions = new int[][] {
			//Action,		Z,		RAND,					THRESHOLD,			Value
			{r.nextInt(24), -1, 	r.nextInt(64)+1, 		r.nextInt(64)+1}
        }; 
    }
    
    public void nextRule() {
		Random r = new Random();
		int instr = u[0].instructions[0][0]; //get current instruction
			
		u[0].instructions = new int[][] {
			//Action,			Z,		RAND,					THRESHOLD,			Value
			{ (instr+1) % 24,	-1, 	r.nextInt(64)+1, 		r.nextInt(64)+1}
        }; 
    }
    
    public void prevRule() {
		Random r = new Random();
		int instr = u[0].instructions[0][0]; //get current instruction
			
		u[0].instructions = new int[][] {
			//Action,			Z,		RAND,					THRESHOLD,			Value
			{ (instr+(24-1)) % 24,	-1, 	r.nextInt(64)+1, 		r.nextInt(64)+1}
        }; 
    }

    //implicit function storage
    ////////////////////////////////////////////
    public void mouseReleased(MouseEvent e) {    }
    public void mouseEntered(MouseEvent e) {    }
    public void mouseExited(MouseEvent e) {    }
    public void mouseClicked(MouseEvent e) {    }
    ////////////////////////////////////////////

}
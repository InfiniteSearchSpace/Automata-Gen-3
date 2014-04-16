

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
	Random r = new Random();
	
	int totalFunctions = 48+1;
	
	int sfcnum = 0;			//index of current active/interactable surface
	int sfcmax;				//total number of surfaces to cycle through
	
	int myFunction = 0;  	//reference for current mouse function on click
	int functionType = 0;	//Determines subclass of functions to execute, like a menu.
	
	int fcnt = 4;			//total number of mouse functions to iterate through
	int fnctype = 5;		//tot number of function catagorys
	
	int mwPos = 0;			//Everyone references the MW cycle
	int mwMax = fcnt;		//default position in the MW cycle, will change frequently
	int cycleNum = 0;		//for Mousewheel to identify which type to cycle
	
	int blockVal = 100;
	int seedVal = 1;
	int seedRand = 2;
	
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
	        		s[sfcnum].u.a.seed(mx, my-1, s[sfcnum].zdraw, 1, 0, 1);
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
	    		if (myFunction == 0) { //Set to 0, one
	    			s[sfcnum].u.a.placeval(mx, my-1, s[sfcnum].zdraw, 1, 0);
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
							s[sfcnum].u.a.placeLine(mx-num, (my+i)-num, s[sfcnum].zdraw, 1, mx+num, false, 1, blockVal);
						}
					}
				}
				
				if (myFunction ==  1) { //small block, value 100	
					int num = 8;
					if(mx-num > 0 && my-num > 0 && mx+num < s[sfcnum].getWidth() && my+num < s[sfcnum].getHeight()) {
						for(int i = 0; i < num*2; i++){
							s[sfcnum].u.a.placeLine(mx-num, (my+i)-num, s[sfcnum].zdraw, 3, mx+num, false, 1, blockVal);
						}
					}
				}
				
				if (myFunction ==  2) { //small block, value 100	
					int num = 14;
					if(mx-num > 0 && my-num > 0 && mx+num < s[sfcnum].getWidth() && my+num < s[sfcnum].getHeight()) {
						for(int i = 0; i < num*2; i++){
							s[sfcnum].u.a.placeLine(mx-num, (my+i)-num, s[sfcnum].zdraw, 1, mx+num, false, 1, blockVal);
						}
					}
				}
				
				if (myFunction ==  3) { //small block, value 100	
		    	    int num = 18;
		    	    if(mx-num > 0 && my-num > 0 && mx+num < s[sfcnum].getWidth() && my+num < s[sfcnum].getHeight()) {
			    		for(int i = 0; i < num*2; i+=3){
			    			s[sfcnum].u.a.placeLine(mx-num, (my+i)-num, s[sfcnum].zdraw, 1, mx+num, false, 1, blockVal);
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
    	l.setText(sfcnum + "." + s[sfcnum].zdraw + "."+ functionType + "." + myFunction + "." + tmps +  "." + cycleNum + " {" + tmps2 + "}");
    	
    }
    
    //called during initial program configuration, or to pause/unpause on right click
    public void toggleStart() {
    	if(s[sfcnum].paused ) { s[sfcnum].paused = false; } else { s[sfcnum].paused = true; }
		if(s[sfcnum].upaused ) { s[sfcnum].upaused = false; } else { s[sfcnum].upaused = true; }
		
    	
    	/*updateListing();
    	s[sfcnum].repaint();
    	
    	if(s[sfcnum].upaused) {
    		if(s[sfcnum].paused) { 
    			s[sfcnum].paused = false; 
    		} 
    	}*/
		
		refresh();
    	
    	
    	
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
    	
    	refresh();
    }
    
    public void setCycle(){
    	if(cycleNum == 0) {myFunction = mwPos;}
    	if(cycleNum == 1) {s[sfcnum].zdraw = mwPos;}
    	if(cycleNum == 2) {eraseAll();reseedAll(6,1);setRule(mwPos);}
    	refresh();
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
    	
		u[0].instructions = new int[][] {
			{ru, -1, r.nextInt(64)+1, r.nextInt(64)+1}
		};
		refresh();
    }
    
    
    public void eraseAll(){
    	for(int i = 0; i < s[sfcnum].zz; i++) {
    		s[sfcnum].u.resetArZ(0,i);
    	}
    	refresh();
    }
    
    public void reseedAll(int rand, int val) {
    	eraseAll();
    	s[sfcnum].u.a.seedAll(seedRand, seedVal);
    	refresh();
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
     	refresh();
    }
    
    public void setRandom_3_InstructionWithSeed(){
    	
		
		u[0].instructions = new int[][] {
			//Action,		Z,		RAND,					THRESHOLD,			Value
	    	{-1, 			-1, 	r.nextInt(100000)+1, 	r.nextInt(64)+1, 	r.nextInt(3)},	//seed
			{r.nextInt(totalFunctions), -1, 	r.nextInt(64)+1, 		r.nextInt(64)+1},					//random function
			{r.nextInt(totalFunctions), -1, 	r.nextInt(64)+1, 		r.nextInt(64)+1},					//random function
			{r.nextInt(totalFunctions), -1, 	r.nextInt(64)+1, 		r.nextInt(64)+1}					//random function
        }; 
		refresh();
    }
    
    public void setRandomRule(){
    	
		
		u[0].instructions = new int[][] {
			//Action,		Z,		RAND,					THRESHOLD,			Value
			{r.nextInt(totalFunctions), -1, 	r.nextInt(64)+1, 		r.nextInt(64)+1}
        }; 
		refresh();
    }

    public void dialogReseed(){
    	String str = JOptionPane.showInputDialog(m, "Seed chance (1/x):", String.valueOf(seedRand));
    	String str2 = JOptionPane.showInputDialog(m, "Enter value to place:", String.valueOf(seedVal));
    	if(str != null && str2 != null) {
    		seedRand = Integer.parseInt(str);
    		seedVal = Integer.parseInt(str2);
    		reseedAll(seedRand, seedVal);
    	}
    }

    public void dialogRule(){
    	String str = JOptionPane.showInputDialog(m, "Go to rule (0-"+(totalFunctions-1)+"):", "0");
    	if(str != null) {
    		int sVal = Integer.parseInt(str);
    		
    		u[0].instructions = new int[][] {
    			{sVal, -1, 	r.nextInt(64)+1, 		r.nextInt(64)+1}
    		};
    	}
    }
    
    public void dialogAddSeed(){
    	String str = JOptionPane.showInputDialog(m, "Seed chance (1/x):", String.valueOf(seedRand));
    	String str2 = JOptionPane.showInputDialog(m, "Enter value to place:", String.valueOf(seedVal));
    	if(str != null && str2 != null) {
    		seedRand = Integer.parseInt(str);
    		seedVal = Integer.parseInt(str2);
    		
    		int tmp[][] = new int[u[0].instructions.length+1][5];
    		int i;
    		for(i = 0; i< u[0].instructions.length; i++) {
    			tmp[i] = u[0].instructions[i];
    		}
    		
    		tmp[i] = new int[]{-1, -1, seedRand, 0, seedVal}; //seed
    		
    		u[0].instructions = tmp;
    	}
    }
    
    public void dialogAddRule(){
    	String str = JOptionPane.showInputDialog(m, "Add rule (0-"+(totalFunctions-1)+"):", "0");
    	if(str != null) {
    		int sVal = Integer.parseInt(str);
    		
    		int tmp[][] = new int[u[0].instructions.length+1][5];
    		int i;
    		for(i = 0; i< u[0].instructions.length; i++) {
    			tmp[i] = u[0].instructions[i];
    		}
    		tmp[i] = new int[]{sVal, -1, r.nextInt(64)+1, r.nextInt(64)+1}; //seed

    		u[0].instructions = tmp;
    	}
    }
    
    public void addRandomRule(){

    	int tmp[][] = new int[u[0].instructions.length+1][5];
    	int i;
    		
    	for(i = 0; i< u[0].instructions.length; i++) {
    		tmp[i] = u[0].instructions[i];
    	}
    		
    	tmp[i] = new int[]{r.nextInt(totalFunctions), -1, r.nextInt(64)+1, r.nextInt(64)+1};

    	u[0].instructions = tmp;
    }
    
    public void removeRule(){
    	int tmp[][] = new int[u[0].instructions.length-1][5];
    	for(int i = 0; i< u[0].instructions.length-1; i++) {
    		tmp[i] = u[0].instructions[i];
    	}
    	u[0].instructions = tmp;
    }
    
    
    
    public void resetRule(){
    	int tmp[][] = new int[1][5];
		tmp[0] = new int[]{0, -1, 1, 1};
		u[0].instructions = tmp;
    }
    
    public void dialogSetBlockVal() {
    	String str = JOptionPane.showInputDialog(m, "Block placement value:", "100");
    	if(str != null) {
    		blockVal = Integer.parseInt(str);
    	}
	}

    
    /*public void resize() {
		u[0] = new Universe(128, 	128,  1, a[0]);
    	u[0].instructions = new int[][] {{4,0}};
    	s[0] = new Surf(128, 	128, 	u[0].zlen,	0+2+2,	0, u[0]);
	}*/
    
    
    //implicit function storage
    ////////////////////////////////////////////
    public void mouseReleased(MouseEvent e) {    }
    public void mouseEntered(MouseEvent e) {    }
    public void mouseExited(MouseEvent e) {    }
    public void mouseClicked(MouseEvent e) {    }
    ////////////////////////////////////////////


	
}
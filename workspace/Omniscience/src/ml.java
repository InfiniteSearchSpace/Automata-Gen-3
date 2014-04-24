

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
	
	int totalFunctions = 67+1;
	
	int sfcnum = 0;			//index of current active/interactable surface
	int sfcmax;				//total number of surfaces to cycle through
	
	int myFunction = 0;  	//reference for current mouse function on click
	int functionType = 0;	//Determines subclass of functions to execute, like a menu.
	
	int fcnt = 4;			//total number of mouse functions to iterate through
	int fnctype = 4;		//tot number of function catagorys
	
	int mwPos = 0;			//Everyone references the MW cycle
	int mwMax = fcnt;		//default position in the MW cycle, will change frequently
	int cycleNum = 0;		//for Mousewheel to identify which type to cycle
	
	int blockVal = 1;
	int blockSize = 1;
	int seedVal = 1;
	int seedRand = 6;
	int rule = 0;
	int seedRndVar = 1;
	int toolRand = 1;
	int toolVar = 1;
	boolean resetVal = true;
	int[] params = {0,0,0,0};
	
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
    
    public void setU(Universe uni[]) {
    	u=uni;
    }
    
    public void mousePressed(MouseEvent e) {
    	//refresh();
    	//get location information from mouse & jframes
    	p = MouseInfo.getPointerInfo().getLocation();
    	p2 = m.getLocation();
    	int mx =(p.x - p2.x - 2  - s[sfcnum].getX());
    	int my =(p.y - p2.y - 24 - m.getJMenuBar().getHeight() - s[sfcnum].getY());

    	//Left Click
    	 if((e == null || e.getButton() == MouseEvent.BUTTON1) && (mx >= 0 && my > 0) && (mx < s[sfcnum].getWidth() && my < s[sfcnum].getHeight()+1) ) {

	    	if(functionType == 0) {
	    		
	    		if (myFunction == 0) { //Place Block Small
	    			if(blockSize < 0) {blockSize=1;}
	    			if(toolRand <= 0) {toolRand=1;}
	    			if(toolVar <= 0) {toolVar=1;}
	    			if(resetVal) {blockVal = 1;resetVal=false;}
	    			placeBlock(mx, my, 0);
	        	}
	    		
	    		if (myFunction ==  1) { //Place Block Small
	    	    	if(blockSize < 0) {blockSize=3;}
	    			if(toolRand <= 0) {toolRand=1;}
	    			if(toolVar <= 0) {toolVar=1;}
	    			if(resetVal) {blockVal = 1;resetVal=false;}
    				placeBlock(mx, my, 0);
	        	}
	    		
	    		if (myFunction ==  2) { //Place block medium 
	    	    	if(blockSize < 0) {blockSize=8;}
	    			if(toolRand <= 0) {toolRand=1;}
	    			if(toolVar <= 0) {toolVar=1;}
	    			if(resetVal) {blockVal = 1;resetVal=false;}
					placeBlock(mx, my, 0);
	        	}
	    		
	    		if (myFunction ==  3) { //Place Block Big
	    	    	if(blockSize < 0) {blockSize=14;}
	    			if(toolRand <= 0) {toolRand=1;}
	    			if(toolVar <= 0) {toolVar=1;}
	    			if(resetVal) {blockVal = 1;resetVal=false;}
					placeBlock(mx, my, 0);
	        	}
	    	}
	
	    	//Function type/catagory 1's options
	    	if(functionType == 1) {
	    		if (myFunction ==  0) { //chance block random Light			
	    			if(blockSize < 0) {blockSize=16;}
	    			if(toolRand <= 0) {toolRand=24;}
	    			if(toolVar <= 0) {toolVar=1;}
	    			if(resetVal) {blockVal=1;resetVal=false;}
					placeBlock(mx, my, 0);
	    		}
	    		
	    		if (myFunction ==  1) { //chance block random Heavy
	    			if(blockSize < 0) {blockSize= 16;}
	    			if(toolRand <= 0) {toolRand=12;}
	    			if(toolVar <= 0) {toolVar=32;}
	    			if(resetVal) {blockVal=-16;resetVal=false;}
					placeBlock(mx, my, 0);
	    		}
	    		
	    		if (myFunction ==  2) { //Place Big Block Horizontal 3-step Stripes
	    	    	if(blockSize < 0) {blockSize= 20;}
	    			if(toolRand <= 0) {toolRand=1;}
	    			if(toolVar <= 0) {toolVar=1;}
	    			if(resetVal) {blockVal = 1;resetVal=false;}
					placeStripe(mx, my, 0, 3);
	        	}
	    		
	    		if (myFunction ==  3) { //Place medium Block 2-step Stripes
	    	    	if(blockSize < 0) {blockSize= 20;}
	    			if(toolRand <= 0) {toolRand=1;}
	    			if(toolVar <= 0) {toolVar=1;}
	    			if(resetVal) {blockVal = 1;resetVal=false;}
	    	    	placeStripe(mx, my, 0, 2);
	        	}
	    	}
	    	
	    	//Function type/catagory 2's options
	    	if(functionType == 2) {
	    		if (myFunction == 0) { //Set to 0, one
	    			if(blockSize < 0) {blockSize= 1;}
	    			if(toolRand <= 0) {toolRand=1;}
	    			if(toolVar <= 0) {toolVar=1;}
	    			if(resetVal) {blockVal = 1;resetVal=false;}
					placeZero(mx, my, 0);
	    		}
	    		
	    		if (myFunction ==  1) { //chance Set to 0, large
	    			if(blockSize < 0) {blockSize= 6;}
	    			if(toolRand <= 0) {toolRand=1;}
	    			if(toolVar <= 0) {toolVar=1;}
	    			if(resetVal) {blockVal = 1;resetVal=false;}
	    			placeZero(mx, my, 0);
	    		}
	    		
	    		if (myFunction ==  2) { //Set to 0, medium
	    			if(blockSize < 0) {blockSize= 32;}
	    			if(toolRand <= 0) {toolRand=1;}
	    			if(toolVar <= 0) {toolVar=1;}
	    			if(resetVal) {blockVal = 1;resetVal=false;}
	    			placeZero(mx, my, 0);
	    		}
	    		
	    		if (myFunction ==  3) { //Set to 0, large
	    			if(toolRand <= 0) {toolRand=24;}
	    			if(blockSize < 0) {blockSize= 16;}
	    			if(toolVar <= 0) {toolVar=1;}
	    			if(resetVal) {blockVal = 1;resetVal=false;}
	    			placeZero(mx, my, 0);
	    		}
	    		
	    	}

			//Function type/catagory 4's options
			if(functionType == 3) {
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
     	/*if(e != null && e.getButton() == MouseEvent.BUTTON2) { 
     		functionType++;
     		functionType=functionType%fnctype;
     		updateListing();
     	}*/
    	 
    	//Right Click - Pause controller
    	if(e != null && e.getButton() == MouseEvent.BUTTON3) { 
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
    	
    	int i;
    	for(i = 0; i < u[0].instructions.length; i++) {
    		tmps2 += u[0].instructions[i][0];
    		if(i < u[0].instructions.length-1) {tmps2 += ",";}
    		if(i%6==5) {tmps2 += "<br>";}
    	}
    	
      	tmps2 += "}";
    	tmps2 = "<br>{" + tmps2;

    	l.setText(
    			
    			"<html><font size=1><font face=courier>" 		+ 
    			sfcnum 				+" Active Window<br>" 		+ 	 
    			s[sfcnum].zdraw 	+" Z-Layer<br>" 			+ 
    			tmps 				+" Pause Status<br>" 		+    
    			functionType 		+" Tool<br>"				+ 
    			myFunction 			+" SubTool<br>" 			+
    			mwPos 				+" MsWheel Raw#<br>" 		+  
    			cycleNum 			+" MouseWheel<br>" 			+ 
    			blockSize 			+" Block Size<br>" 			+ 
    			blockVal 			+" Block Value<br>" 		+ 
    			
    			"Ruleset:"			+ tmps2						+
    			
    			" </font></html>"	
    			
    	);
    	
    }
    
    //called during initial program configuration, or to pause/unpause on right click
    public void toggleStart() {
    	if(s[sfcnum].paused ) { s[sfcnum].paused = false; } else { s[sfcnum].paused = true; }
		if(s[sfcnum].upaused ) { s[sfcnum].upaused = false; } else { s[sfcnum].upaused = true; }
		
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
    	if(cycleNum == 0) {myFunction = mwPos;blockSize = -1;toolRand = -1;toolVar = -1;resetVal = true;}
    	if(cycleNum == 1) {s[sfcnum].zdraw = mwPos;}
    	if(cycleNum == 2) {cycleRules(); }
    	if(cycleNum == 3) {blockSize = mwPos;}
    	if(cycleNum == 4) {blockVal = ((mwPos+50)%100)-50;}
    	refresh();
    }
    
    private void cycleRules(){
    	s[sfcnum].u.resetArZ(0, s[sfcnum].zdraw);
    	s[sfcnum].u.a.seedZ(seedRand, s[sfcnum].zdraw, seedVal, seedRndVar);
    	addRule(mwPos);
    }
    
    private void addRule(int ru) {
    	rule=ru;
    	int tmp[][] = new int[u[0].instructions.length+1][5];
		int i;
		for(i = 0; i< u[0].instructions.length; i++) {
			tmp[i] = u[0].instructions[i];
		}
		
		if(i == 0) {
			tmp[i] = new int[]{rule, s[sfcnum].zdraw, seedRand, 0, seedVal};
			u[0].instructions = tmp;
			i++;		
		}
			
		if(tmp[i-1][1] != s[sfcnum].zdraw) {
			tmp[i] = new int[]{rule, s[sfcnum].zdraw, seedRand, 0, seedVal};
			u[0].instructions = tmp;
		 }else {
			int tmp2[][] = new int[u[0].instructions.length][5];
			for(int j = 0; j < u[0].instructions.length; j++) {
				tmp2[j] = u[0].instructions[j];
			}
			tmp2[i-1] = new int[]{rule, s[sfcnum].zdraw, seedRand, 0, seedVal};
			u[0].instructions = tmp2;
		}
		
		
    }

    
    public void setRule(int ru) {
    	
		u[0].instructions = new int[][] {
			{ru, s[sfcnum].zdraw, r.nextInt(64)+1, r.nextInt(64)+1}
		};
		refresh();
    }
    
    
    public void eraseAll(){
    	for(int i = 0; i < s[sfcnum].zz; i++) {
    		s[sfcnum].u.resetArZ(0,i);
    	}
    	refresh();
    }
    
    public void eraseLayer(){
    	for(int i = 0; i < s[sfcnum].zz; i++) {
    		s[sfcnum].u.resetArZ(0, s[sfcnum].zdraw);
    	}
    	refresh();
    }
    
    public void reseedAll(int rand, int val) {
    	eraseAll();
    	s[sfcnum].u.a.seedAll(seedRand, seedVal, seedRndVar);
    	refresh();
    }
    
    public void reseedLayer(int rand, int val) {
    	eraseLayer();
    	s[sfcnum].u.a.seedZ(rand, s[sfcnum].zdraw, val, seedRndVar);
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
			{r.nextInt(totalFunctions), s[sfcnum].zdraw, 	r.nextInt(64)+1, 		r.nextInt(64)+1},					//random function
			{r.nextInt(totalFunctions), s[sfcnum].zdraw, 	r.nextInt(64)+1, 		r.nextInt(64)+1},					//random function
			{r.nextInt(totalFunctions), s[sfcnum].zdraw, 	r.nextInt(64)+1, 		r.nextInt(64)+1}					//random function
        }; 
		refresh();
    }
    
    public void setRandomRule(){
    	
		
		u[0].instructions = new int[][] {
			//Action,		Z,		RAND,					THRESHOLD,			Value
			{r.nextInt(totalFunctions), s[sfcnum].zdraw, 	r.nextInt(64)+1, 		r.nextInt(64)+1}
        }; 
		refresh();
    }

    public void dialogReseed(){
    	String str = JOptionPane.showInputDialog(m, "Seed, with chance one out of:", String.valueOf(seedRand));
    	String str2 = JOptionPane.showInputDialog(m, "Set cell value:", String.valueOf(seedVal));
    	String str3 = JOptionPane.showInputDialog(m, "Set random variation in value:", seedRndVar);
    	if(str != null && str2 != null) {
    		seedRand = Integer.parseInt(str);
    		seedVal = Integer.parseInt(str2);
    		seedRndVar = Integer.parseInt(str3);
    		//reseedAll(seedRand, seedVal);
    		reseedLayer(seedRand,seedVal);
    	}
    }

    public void dialogRule(){
    	String str = JOptionPane.showInputDialog(m, "Go to rule (0-"+(totalFunctions-1)+"):", "0");
    	if(str != null) {
    		int sVal = Integer.parseInt(str);
    		
    		u[0].instructions = new int[][] {
    			{sVal, s[sfcnum].zdraw, 	r.nextInt(64)+1, 		r.nextInt(64)+1}
    		};
    	}
    }
    
    public void dialogAddSeed(){
    	String str = JOptionPane.showInputDialog(m, "Seed, with chance one out of:", String.valueOf(seedRand));
    	String str2 = JOptionPane.showInputDialog(m, "Enter value to place:", String.valueOf(seedVal));
    	if(str != null && str2 != null) {
    		int mySeedRand = Integer.parseInt(str);
    		int mySeedVal = Integer.parseInt(str2);
    		
    		int tmp[][] = new int[u[0].instructions.length+1][5];
    		int i;
    		for(i = 0; i< u[0].instructions.length; i++) {
    			tmp[i] = u[0].instructions[i];
    		}
    		
    		tmp[i] = new int[]{-1, s[sfcnum].zdraw, mySeedRand, 0, mySeedVal}; //seed
    		
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
    		tmp[i] = new int[]{sVal, s[sfcnum].zdraw, r.nextInt(64)+1, r.nextInt(64)+1}; //seed

    		u[0].instructions = tmp;
    	}
    }
    
    public void recastParams(){
    	
    	int tmp[][] = new int[u[0].instructions.length-1][5];
    	int inst = 0;
    	
    	if(u[0].instructions.length-1 == 0) {
    		System.out.println("Fail");
    		inst = u[0].instructions[0][0];
    	}
    	
    	for(int i = 0; i< u[0].instructions.length-1; i++) {
    		tmp[i] = u[0].instructions[i];
    		inst = u[0].instructions[i+1][0];
    	}
    	
    	
    	String str3 = "20";
    	String str4 = "1";

    	String str1 = JOptionPane.showInputDialog(m, "(-1 is All Layers) Target Z-Layer:", params[0]);
    	String str2 = JOptionPane.showInputDialog(m, "Random Chance:", params[1]);
    
    	if(inst == 18) {str3 = JOptionPane.showInputDialog(m, "(18 Only) Threshold:", params[2]);}
    	if(inst == -1) {str4 = JOptionPane.showInputDialog(m, "(-1 Only) Value Override", params[3]);}
    	
    	if(str1 != null && str2 != null) {
        	u[0].instructions = tmp;
    		
        	
    		params[0] = Integer.parseInt(str1);
    		params[1] = Integer.parseInt(str2);
    		params[2] = Integer.parseInt(str3);
    		params[3] = Integer.parseInt(str4);
    		
    		int tmp2[][] = new int[u[0].instructions.length+1][5];
    		
    		int i;
    		for(i = 0; i< u[0].instructions.length; i++) {
    			tmp2[i] = u[0].instructions[i];
    		}
    		tmp2[i] = new int[]{inst, params[0], params[1], params[2], params[3]};

    		u[0].instructions = tmp2;
    	}
    }
    
    public void addRandomRule(){

    	int tmp[][] = new int[u[0].instructions.length+1][5];
    	int i;
    		
    	for(i = 0; i< u[0].instructions.length; i++) {
    		tmp[i] = u[0].instructions[i];
    	}
    		
    	tmp[i] = new int[]{r.nextInt(totalFunctions), s[sfcnum].zdraw, r.nextInt(64)+1, r.nextInt(64)+1};

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
    	String str = JOptionPane.showInputDialog(m, "Tool Value:", blockVal);
    	if(str != null) {
    		blockVal = Integer.parseInt(str);
    	}
	}

    public void dialogSetBlockRand() {
    	String str = JOptionPane.showInputDialog(m, "Tool Placement - Random Distribution:", toolRand);
    	if(str != null) {
    		toolRand = Integer.parseInt(str);
    	}
	}
    
    public void dialogSetBlockVar() {
    	String str = JOptionPane.showInputDialog(m, "Tool Value - Random Variation:", toolVar);
    	if(str != null) {
    		toolVar = Integer.parseInt(str);
    	}
	}
    public void dialogSetBlockSize() {
    	String str = JOptionPane.showInputDialog(m, "Tool Size:", blockSize);
    	if(str != null) {
    		blockSize = Integer.parseInt(str);
    	}
    }
    
    public void placeBlock(int mx, int my, int mz){
	    if(mx-(blockSize/2) >= 0 && my-(blockSize/2) > 0 && mx+(blockSize/2) < s[sfcnum].getWidth() && my+(blockSize/2) < s[sfcnum].getHeight()+1) {
			for(int i = 0; i < blockSize; i++){
				s[sfcnum].u.a.plcLn(	
					mx-blockSize, 
					(my+i)-blockSize, 
					s[sfcnum].zdraw, 
					
					toolRand, 0, blockVal, 1, 0, mx+blockSize, blockSize, toolVar
				);
			} 
	    }
    }
    
    public void placeZero(int mx, int my, int mz){
	    if(mx-(blockSize/2) >= 0 && my-(blockSize/2) > 0 && mx+(blockSize/2) < s[sfcnum].getWidth() && my+(blockSize/2) < s[sfcnum].getHeight()+1) {
			for(int i = 0; i < blockSize; i++){
				s[sfcnum].u.a.plcLn(	
					mx-blockSize, 
					(my+i)-blockSize, 
					s[sfcnum].zdraw, 
					
					toolRand, 0, 0, 1, 0, mx+blockSize, blockSize, toolVar
				);
			} 
	    }
    }
    
    public void placeStripe(int mx, int my, int mz, int iMult){
	    if(mx-(blockSize/2) >= 0 && my-(blockSize/2) > 0 && mx+(blockSize/2) < s[sfcnum].getWidth() && my+(blockSize/2) < s[sfcnum].getHeight()+1) {
			for(int i = 0; i < blockSize; i+= iMult){
				s[sfcnum].u.a.plcLn(	
					mx-blockSize, 
					(my+i)-blockSize, 
					s[sfcnum].zdraw, 
					
					toolRand, 0, blockVal+r.nextInt(toolVar), 1, 0, mx+blockSize, blockSize, toolVar
				);
			} 
	    }
    }
    
    public void cycleUni(){
    	sfcnum++;
		sfcnum = sfcnum % sfcmax;
    }
    
    //implicit function storage
    ////////////////////////////////////////////
    public void mouseReleased(MouseEvent e) {    }
    public void mouseEntered(MouseEvent e) {    }
    public void mouseExited(MouseEvent e) {    }
    public void mouseClicked(MouseEvent e) {    }
    ////////////////////////////////////////////


	
}


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
	dataSources d = new dataSources();
	slider[] sl;
	
	int totalFunctions = 88			+1;
	int totalHoods;
	int totalColourSchemes = 4		+1;
	
	int sfcnum = 0;			//index of current active/interactable surface
	int sfcmax;				//total number of surfaces to cycle through
	
	int myFunction = 0;  	//reference for current mouse function on click
	int functionType = 0;	//Determines subclass of functions to execute, like a menu.
	
	int fcnt = 5;			//total number of mouse functions to iterate through
	int fnctype = 4;		//tot number of function catagorys
	
	int mwPos = 0;			//Everyone references the MW cycle
	int mwMax = fcnt;		//default position in the MW cycle, will change frequently
	int cycleNum = 0;		//for Mousewheel to identify which type to cycle
	
	int blockVal = 1;
	int blockSize = 16;
	int seedVal = 1;
	int seedRand = 6;
	int rule = 0;
	int seedRndVar = 1;
	int toolRand = 1;
	int toolVar = 1;
	int custNHood=0;
	int ROver = -1;
	int colourScheme = -1;
	
	int[] slideVal;
	int growDieThreshold = 0;
	
	int gui_toolRand = toolRand;
	int gui_toolVar = toolVar;
	int gui_blockVal = blockVal;
	int gui_blockSize = blockSize;
	int gui_nHood = custNHood;
	int gui_ROver = ROver;
	int gui_colourScheme = colourScheme;
	
	int copyPasteBlockSize = 16;
	
	boolean resetVal = true;
	int[] params = {0,0,0,0};
	
	int[][] customTF_NHood = {};
	
	//constructor
    public ml(Main mm, Surf[] ss, JLabel ll, Universe uni[], slider[] ssl) {
    	//System.out.println("ml");
    	m=mm;
    	s=ss;
    	l=ll;
    	u=uni;
    	sl=ssl;
    	slideVal = new int[sl.length];
        m.addMouseListener(this);
      
        sfcmax=s.length;
        functionType = 0;  
        
        totalHoods = u[0].a.n.hoodCount;
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
	    		
	    		if (myFunction ==  4) { //Place Block Bigger
	    	    	if(blockSize < 0) {blockSize=32;}
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
	    		
	    		if (myFunction ==  4) { 
	    			if(blockSize < 0) {blockSize=6;}
	    			if(toolRand <= 0) {toolRand=3;}
	    			if(toolVar <= 0) {toolVar=3;}
	    			if(resetVal) {blockVal=-1;resetVal=false;}
					placeBlock(mx, my, 0);
	        	}
	    		
	    	}
	    	
	    	//Function type/catagory 2's options
	    	if(functionType == 2) {
	    		
				

	    		if (myFunction ==  0) { //chance Set to 0, large
	    			if(blockSize < 0) {blockSize=1;}
	    			if(toolRand <= 0) {toolRand=1;}
	    			if(toolVar <= 0) {toolVar=1;}
	    			if(resetVal) {blockVal = 0;resetVal=false;}
					placeBlock(mx, my, 0);
	    		}
	    		
	    		if (myFunction ==  1) { //Set to 0, medium
	    			if(blockSize < 0) {blockSize=8;}
	    			if(toolRand <= 0) {toolRand=1;}
	    			if(toolVar <= 0) {toolVar=1;}
	    			if(resetVal) {blockVal = 0;resetVal=false;}
					placeBlock(mx, my, 0);
	    		}
	    		
	    		if (myFunction ==  2) { //Set to 0, large
	    			if(blockSize < 0) {blockSize=32;}
	    			if(toolRand <= 0) {toolRand=1;}
	    			if(toolVar <= 0) {toolVar=1;}
	    			if(resetVal) {blockVal = 0;resetVal=false;}
					placeBlock(mx, my, 0);
	    		}
	    		
	    		if (myFunction ==  3) { //Set to 0, large
	    			if(blockSize < 0) {blockSize=24;}
	    			if(toolRand <= 0) {toolRand=12;}
	    			if(toolVar <= 0) {toolVar=1;}
	    			if(resetVal) {blockVal = 0;resetVal=false;}
					placeBlock(mx, my, 0);
	    		}
	    		
	    		if (myFunction ==  4) { //Set to 0, large
	    			if(blockSize < 0) {blockSize=6;}
	    			if(toolRand <= 0) {toolRand=4;}
	    			if(toolVar <= 0) {toolVar=1;}
	    			if(resetVal) {blockVal = 0;resetVal=false;}
					placeBlock(mx, my, 0);
	    		}
	    		
	    	}

	    	if(functionType == 3) {
				if (myFunction ==  0) { 
					if(blockSize < 0) {blockSize= 16;}
	    			if(toolRand <= 0) {toolRand=12;}
	    			if(toolVar <= 0) {toolVar=32;}
	    			if(resetVal) {blockVal=-16;resetVal=false;}
					placeBlock(mx, my, 0);
				}

				if (myFunction ==  1) { 
					if(blockSize < 0) {blockSize=copyPasteBlockSize;}
	    			if(toolRand <= 0) {toolRand=1;}
	    			if(toolVar <= 0) {toolVar=1;}
	    			placeData(mx, my, 0);
				}
				
				if (myFunction ==  2) { 
					if(blockSize < 0) {blockSize=copyPasteBlockSize;}
	    			if(toolRand <= 0) {toolRand=1;}
	    			if(toolVar <= 0) {toolVar=1;}
	    			captureData(mx, my, 0);
				}
				
			}
	    	
			//Function type/catagory 4's options
			/*if(functionType == 3) {
				if (myFunction ==  0) { //Scroll through surfaces
					sfcnum++;
					sfcnum = sfcnum % sfcmax;
				}

				if (myFunction ==  1) { //Change instruction set to a random instruction
					setRandom_3_InstructionWithSeed();
				}
				
			}*/

			
			//Function type/catagory 5's options
			/*if(functionType == 5) {
				if (myFunction ==  1) { //
					s[sfcnum].u.a.seedAll(4, 1);
				}
				
				if (myFunction == 2) { //
					
				}
				
				if (myFunction == 3) { //

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
    	
    	//middle click: erase layer
    	if(e != null && e.getButton() == MouseEvent.BUTTON2){
    		eraseLayer();
    	}
    	
    	refresh();
    }
    
    public void ruleLenChanged(){
    	u[0].a.n.newCNAr(u[0].instructions.length);
    }
    
    public void ruleChanged(int rulenum){
    	if(rulenum == 87) {
    		sl[0].existential(true);
    		sl[1].existential(true);
    		sl[1].setMax(5000);
    	} else if(rulenum == 83) {
    		sl[0].existential(true);
    		sl[1].existential(true);
    		sl[1].setMax(10);
    	} else if(rulenum == 82) {
    		sl[0].existential(true);
    		sl[1].existential(true);
    		sl[1].setMax(7);
    	} else if(rulenum == 81) {
    		sl[0].existential(true);
    		sl[1].existential(true);
    		sl[1].setMax(8);
    	} else if(rulenum == 80) {
    		sl[0].existential(true);
    		sl[1].existential(true);
    		sl[1].setMax(9);
    	}else if(rulenum == 79) {
    		sl[0].existential(true);
    		sl[1].existential(true);
    		sl[1].setMax(33);
    	} else if(rulenum == 78) {
    		sl[0].existential(true);
    		sl[1].existential(true);
    		sl[1].setMax(150);
    	} else if(rulenum == 77) {
    		sl[0].existential(true);
    		sl[1].existential(true);
    		sl[1].setMax(8);
    	} else if(rulenum == 76) {
    		sl[0].existential(true);
    		sl[1].existential(true);
    		sl[1].setMax(16);
    	} else if(rulenum == 75) {
    		sl[0].existential(true);
    		sl[1].existential(true);
    		sl[1].setMax(48);
    	}else if(rulenum == 74) {
    		sl[0].existential(true);
    		sl[1].existential(true);
    		sl[1].setMax(6);
    	} else if(rulenum == 73) {
    		sl[0].existential(true);
    		sl[1].existential(true);
    		sl[1].setMax(8);
    	} else if(rulenum == 72) {
    		sl[0].existential(true);
    		sl[1].existential(true);
    		sl[1].setMax(25);
    	} else {
    		sl[0].existential(false);
    		sl[1].existential(false);
    	}
    	
    	updateListing();
    	//System.out.println("RULENUM"  +rulenum);
    }
   
    
    //identifies the mousefunction action that is highlighted
    public void updateListing(){
    	
    	
    	//convert pause state to string
    	String tmps = "";
    	for(int i = 0; i < s.length; i++) {
    		if(s[i].paused == true) {tmps+="1";} else {tmps+="0";}
    		if(s[i].upaused == true) {tmps+="1";} else {tmps+="0";}
    	}
    	
    	String tmps2 = "";
    	int i;
    	for(i = 0; i < u[0].instructions.length; i++) {
    		tmps2 += u[0].instructions[i][0];
    		if(i < u[0].instructions.length-1) {tmps2 += ",";}
    		if(i%6==5) {tmps2 += "<br>";}
    	}
    	
      	tmps2 += "}<br>";
    	tmps2 = "<br>{" + tmps2;

    	
    	
    	String tmps3 = "<br>[";
    	
    	for(i = 0; i < slideVal.length; i++) {
    		tmps3 += slideVal[i];
    		//tmps3 += slideVal[i+1];
    		if(i != slideVal.length-1){tmps3 += ",";}
    	}
    	tmps3 += "]<br>";
    	
    	
    	
    	String tmps4 = "<br>[";
    	
    	for(i = 0; i < s[sfcnum].u.a.min_grow_die[0].length; i+=2) {
    		tmps4 += s[sfcnum].u.a.min_grow_die[0][i];
    		tmps4 += "-";
    		tmps4 += s[sfcnum].u.a.min_grow_die[0][i+1];
    		if(i < s[sfcnum].u.a.min_grow_die[0].length-2){tmps4 += ";";}
    	}
    	tmps4 += "]";
    	
    	

    	String tmps5 = "";
    	for(i = 0; i < u[0].a.n.useCustNbrAr.length; i++) {
    		tmps5 += u[0].a.n.useCustNbrAr[i];
    		if(i < u[0].a.n.useCustNbrAr.length-1) {tmps5 += ",";}
    		if(i%6==5) {tmps5 += "<br>";}
    	}
      	tmps5 += "}<br>";
    	tmps5 = "<br>{" + tmps5;
    	
    	
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
    			u[0].a.n.useCustom	+" NbrHood Override<br>" 	+ 
    			u[0].a.instrOverride+" Rule Override<br>" 	+ 
    			
    			"Ruleset:"			+ tmps2						+
    			"Custom Neighbours:"+ tmps5						+
    			"Sliders:"			+ tmps3						+
    			"Slider Rule:"		+ tmps4						+

    			
    			
    			
    			" </font></html>"	
    			
    	);
    	
    }
    
    //called during initial program configuration, or to pause/unpause on right click
    public void toggleStart() {
    	
    	if(s[sfcnum].upaused ) { s[sfcnum].upaused = false; } else { s[sfcnum].upaused = true; }
		if(s[sfcnum].paused ) { s[sfcnum].paused = false; } else { s[sfcnum].paused = true; }
		
		//refresh();
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
    	if(cycleNum == 3) {blockSize = mwPos;gui_blockSize = mwPos;}
    	if(cycleNum == 4) {blockVal = ((mwPos+150)%300)-150;gui_blockVal = blockVal;}
    	if(cycleNum == 5) {cycleNeighbours(mwPos-2); }
    	if(cycleNum == 6) {cycleMidSpace(); }
    	if(cycleNum == 7) {cycleColourSchemes(); }
    	refresh();
    }
    
    private void cycleColourSchemes(){
    	s[sfcnum].colourScheme = mwPos-1;
    } 
    
    private void cycleMidSpace(){
    	u[0].a.instrOverride = mwPos-2;
    } 
    
    private void cycleNeighbours(int toNbr){
    	u[0].a.n.TFNbH = customTF_NHood;
    	u[0].a.n.useCustom = 1;
    	u[0].a.n.setNbrhoodAr(toNbr);
    } 
    
    public void eraseNbrAr() {
    	u[0].a.n.resetNbrAr();
    }
    
    public void toggleCustNbr() {
    	if(u[0].a.n.useCustom == 1){u[0].a.n.useCustom = 0;}
    	else {u[0].a.n.useCustom = 1;}
    	
    }
    
    private void cycleRules(){
    	s[sfcnum].u.resetArZ(0, s[sfcnum].zdraw);
    	s[sfcnum].u.a.seedZ(seedRand, s[sfcnum].zdraw, seedVal, seedRndVar);
    	addRule(mwPos);
    	ruleChanged(mwPos);
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
			{r.nextInt(totalFunctions+1)-1, s[sfcnum].zdraw, 	r.nextInt(64)+1, 		r.nextInt(64)+1, 		r.nextInt(64)+1},					//random function
			{r.nextInt(totalFunctions+1)-1, s[sfcnum].zdraw, 	r.nextInt(64)+1, 		r.nextInt(64)+1, 		r.nextInt(64)+1},					//random function
			{r.nextInt(totalFunctions+1)-1, s[sfcnum].zdraw, 	r.nextInt(64)+1, 		r.nextInt(64)+1, 		r.nextInt(64)+1}					//random function
        }; 
		
		
		ruleChanged(u[0].instructions[u[0].instructions.length-1][0]);
		
		for(int i = 0; i < u[0].instructions.length; i++) {
			//set last neighbour
	    	u[0].a.n.setIndexNbrhoodAr(r.nextInt(u[0].a.n.hoodCount)-2, i);
			//add blank neighbour mask
	    	ruleLenChanged();
		}
		
		refresh();
    }
    
    public void setRandomRule(){
    	
		
		u[0].instructions = new int[][] {
			//Action,		Z,		RAND,					THRESHOLD,			Value
			{r.nextInt(totalFunctions), s[sfcnum].zdraw, 	r.nextInt(64)+1, 		r.nextInt(64)+1}
        }; 
		ruleChanged(u[0].instructions[u[0].instructions.length-1][0]);
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
    
    
    public void dialogRuleBounds(){
    	String str = JOptionPane.showInputDialog(m, "Target Parmeter (0-5):", "0");
    	//String str2 = JOptionPane.showInputDialog(m, "Value:", "0");
    	if(str != null /*&& str2 != null*/) {
    		int sVal = Integer.parseInt(str);
    		growDieThreshold = sVal;
    	}
    }
    
    public void setRuleGDThresh(int slideNum){
    	if(slideNum == 0){
	    	growDieThreshold = slideVal[slideNum];
    	}
    	
    	if(slideNum == 1){
    		if(growDieThreshold%2 == 0) {
    			s[sfcnum].u.a.min_grow_die[0][growDieThreshold] = slideVal[slideNum];
    			
    			if(s[sfcnum].u.a.min_grow_die[0][growDieThreshold+1] < slideVal[slideNum]) {
    				s[sfcnum].u.a.min_grow_die[0][growDieThreshold+1] = slideVal[slideNum];
    			}
    			
    		} else if(growDieThreshold%2 == 1){
    			s[sfcnum].u.a.min_grow_die[0][growDieThreshold] = slideVal[slideNum];
    			if(s[sfcnum].u.a.min_grow_die[0][growDieThreshold-1] > slideVal[slideNum]) {
    				s[sfcnum].u.a.min_grow_die[0][growDieThreshold-1] = slideVal[slideNum];
    			}
    			
    		}
    		
	    	
	    	
    	}
    	updateListing();
    	System.out.println(growDieThreshold%2 + " GDT:"+growDieThreshold + "  " + "NBRT:"+slideVal[slideNum]);
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
    		ruleLenChanged();
    		ruleChanged(tmp[u[0].instructions.length-1][0]);
    		
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
    		tmp[i] = new int[]{sVal, s[sfcnum].zdraw, r.nextInt(64)+1, r.nextInt(64)+1, -1}; //seed

    		u[0].instructions = tmp;
    		ruleLenChanged();
    		ruleChanged(tmp[u[0].instructions.length-1][0]);
    		
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
    		
    	tmp[i] = new int[]{r.nextInt(totalFunctions), s[sfcnum].zdraw, r.nextInt(64)+1, r.nextInt(64)+1, r.nextInt(64)+1};

    	u[0].instructions = tmp;
    	ruleLenChanged();
    	ruleChanged(tmp[u[0].instructions.length-1][0]);
    	
    }
    
    public void removeRule(){
    	int tmp[][] = new int[u[0].instructions.length-1][5];
    	for(int i = 0; i< u[0].instructions.length-1; i++) {
    		tmp[i] = u[0].instructions[i];
    	}

    	u[0].instructions = tmp;
    	ruleLenChanged();
    	ruleChanged(tmp[u[0].instructions.length-1][0]);
    	
    }
    
    
    
    public void resetRule(){
    	int tmp[][] = new int[1][5];
		tmp[0] = new int[]{0, -1, 1, 1};
		u[0].instructions = tmp;
    }
    
    public void resetRuleDef(){
    	int tmp[][] = new int[1][5];
		tmp[0] = new int[]{0, 0, 1, 1};
		u[0].instructions = tmp;
    }
    public void snapToCustomTool() {
    	blockVal = gui_blockVal;
    	toolRand = gui_toolRand;
    	toolVar = gui_toolVar;
    	blockSize = gui_blockSize;
    	
    }
    
    public void dialogSetSlider() {
    	String str = JOptionPane.showInputDialog(m, "Slider Number (0-5:", 0);
    	if(str != null) {
    		
    	}
    	
    	str = JOptionPane.showInputDialog(m, "Slider Value", gui_blockVal);
    	if(str != null) {
    		//sl[0].setState(Integer.parseInt(str));
    	}
	}
    
    public void dialogSetBlockVal() {
    	String str = JOptionPane.showInputDialog(m, "Tool Value:", gui_blockVal);
    	if(str != null) {
    		blockVal = Integer.parseInt(str);
    		gui_blockVal = blockVal;
    	}
	}

    public void dialogSetBlockRand() {
    	String str = JOptionPane.showInputDialog(m, "Tool Placement - Random Distribution:", gui_toolRand);
    	if(str != null) {
    		toolRand = Integer.parseInt(str);
    		gui_toolRand = toolRand;
    	}
	}
    
    public void dialogSetBlockVar() {
    	String str = JOptionPane.showInputDialog(m, "Tool Value - Random Variation:", gui_toolVar);
    	if(str != null) {
    		toolVar = Integer.parseInt(str);
    		gui_toolVar = toolVar;
    	}
	}
    
    public void dialogSetBlockSize() {
    	String str = JOptionPane.showInputDialog(m, "Tool Size:", gui_blockSize);
    	if(str != null) {
    		blockSize = Integer.parseInt(str);
    		gui_blockSize = blockSize;
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
    
    public void placeData(int mx, int my, int mz){
	    if(mx-(blockSize/2) >= 0 && my-(blockSize/2) > 0 && mx+(blockSize/2) < s[sfcnum].getWidth() && my+(blockSize/2) < s[sfcnum].getHeight()+1) {
			for(int i = 0; i < blockSize; i++){
				s[sfcnum].u.a.plcDataLn(	
					mx-blockSize, 
					(my+i)-blockSize, 
					s[sfcnum].zdraw, 
					
					toolRand, 0, 0, 1, 0, mx+blockSize, blockSize, toolVar, d, 1
				);
			} 
			d.reset();
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
    
    public void captureData(int mx, int my, int mz){
		int[][][] resultAr;
		resultAr = new int[blockSize][blockSize][1];
		
		String ss = "";
		//use block placement decendent as capture device for u.universe
		
	    if(mx-(blockSize/2) >= 0 && my-(blockSize/2) > 0 && mx+(blockSize/2) < s[sfcnum].getWidth() && my+(blockSize/2) < s[sfcnum].getHeight()+1) {
			for(int i = 0; i < blockSize; i++){
				int[] intar = new int[blockSize];
				intar = s[sfcnum].u.a.getDataLn(	
					mx-blockSize, 
					(my+i)-blockSize, 
					s[sfcnum].zdraw, 
						
					toolRand, 0, 0, 1, 0, mx+blockSize, blockSize, toolVar
				);
				
				
				
				for (int j = 0; j < intar.length; j++){
					resultAr[j][i][0] = intar[j];
		    	    ss+=intar[j] + ",";
			    }
				ss+="\n";
			} 
			System.out.println(ss);
			 	
				
	    }
	    
	   
	    
	    
	    
	    
	    ss="";
	    for (int i = 0; i < resultAr.length; i++){
	    	for (int j = 0; j < resultAr[0].length; j++){
	    	    ss+=resultAr[i][j][0] + ",";
		    }
	    	ss+="\n";
	    }
	    System.out.println(ss);
	    
	    
	    d.setArray(resultAr);
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
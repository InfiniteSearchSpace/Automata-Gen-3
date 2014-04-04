import java.util.Random;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class Main extends JFrame {

	
	public Main(){
		//System.out.println("Main");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	public static void main(String[] args) {
		
        SwingUtilities.invokeLater(new Runnable() { 
            @Override
            public void run() { //breaks program's static dependance

            	//Define world's dimensional parameters
            	int xSize = 256; //size of each surface
            	int ySize = 256;
            	
            	int surfaces = 1;	//Total number of displays
            	int universes = 1;	//Total number of universes
            	
            	//instanciate essential program elements 
            	Main m = new Main(); 								//Main window/frame
            	JLabel l = new JLabel(); 							//Textbox for info display
            	Surf[] s = new Surf[surfaces];						//Array of surface displays
            	Universe[] u = new Universe[universes];				//Array of universes/worlds
            	automataLib[] a = new automataLib[universes];		//Performs all automata functions for the sim;
            	
            	//Instanciate automataLibs 
            	//This may be unnessessary?
            	a[0] = new automataLib(m);			
            	//a[1] = new automataLib(m);
            	//a[2] = new automataLib(m);
            	
            	//Instanciate Universes
            	//This can be used to run different simulations in different surfaces.
            	//still only runs one once cpu core per Main instance. 
            	//					Xsize	Ysize	Zsize	automataLib
            	u[0] = new Universe(xSize, 	ySize,  1, 	a[0]);			
            	//u[1] = new Universe(xSize, 	ySize, 	2, 	a[1]);
            	//u[2] = new Universe(xSize, 	ySize, 	2, 	a[2]);
            	
            	
            	/*This is a list of functions as they appear in automatalib.
            	 * If you need to know the function's parameter type and appropriate values,
            	 * look inside automatalib. This will need to be cleaned and standardised later.
            	 *
            	if(ins[0] == 0  seed(xx, yy, zz, 						Rand, Val);}
        		if(ins[0] == 1  conway(xx, yy, zz, 						ins[2], ins[3]);}
        		if(ins[0] == 2  dozIfVal(xx, yy, zz, 					val, z, NewVal, Rand);}
        		if(ins[0] == 3  dozDelMeIfNotLVal(xx, yy, zz, 			ins[2], ins[3], ins[4]);}
        		if(ins[0] == 4  quantum(xx,yy,zz,						Rand);}
        		if(ins[0] == 5  && (ins[1] == zz || ins[1] == -1)) {explorer(xx,yy,zz						);}
				if(ins[0] == 6  && (ins[1] == zz || ins[1] == -1)) {dozDelIfVal(xx,yy,zz, 					ins[2], ins[3]);}
				if(ins[0] == 7  && (ins[1] == zz || ins[1] == -1)) {dozDelIfNotVal(xx,yy,zz, 				ins[2], ins[3]);}
				if(ins[0] == 8  && (ins[1] == zz || ins[1] == -1)) {doz021(xx,yy,zz, 						ins[2]);}
				if(ins[0] == 9  && (ins[1] == zz || ins[1] == -1)) {dozDelMeIfVal(xx,yy,zz, 				ins[2], ins[3]);}
				if(ins[0] == 10 && (ins[1] == zz || ins[1] == -1)) {quantumMorE(xx,yy,zz,					ins[2], ins[3]);}
				if(ins[0] == 11 && (ins[1] == zz || ins[1] == -1)) {dozExclude(xx,yy,zz, 					ins[2], ins[3], ins[4]);}
				if(ins[0] == 12 && (ins[1] == zz || ins[1] == -1)) {dozIncrementIfVal(xx,yy,zz, 			ins[2], ins[3], ins[4], ins[5]);}
				if(ins[0] == 13 && (ins[1] == zz || ins[1] == -1)) {dozDelMeIfNotVal(xx,yy,zz, 				ins[2], ins[3], ins[4]);}
				if(ins[0] == 14 && (ins[1] == zz || ins[1] == -1)) {quantumWeight(xx,yy,zz,					ins[2]);}
				if(ins[0] == 15 && (ins[1] == zz || ins[1] == -1)) {twat(xx,yy,zz							);} //lol, typo for test
				if(ins[0] == 16 && (ins[1] == zz || ins[1] == -1)) {rule110(xx,yy,zz						);}
				if(ins[0] == 17 && (ins[1] == zz || ins[1] == -1)) {rain2(xx,yy,zz							);}
				if(ins[0] == 18 && (ins[1] == zz || ins[1] == -1)) {goop(xx,yy,zz							);}
				if(ins[0] == 19 && (ins[1] == zz || ins[1] == -1)) {internalAffairs(xx,yy,zz				);}
				if(ins[0] == 20 && (ins[1] == zz || ins[1] == -1)) {meekrochyp(xx,yy,zz						);}
				if(ins[0] == 21 && (ins[1] == zz || ins[1] == -1)) {diamondShuffle(xx,yy,zz					);}
				if(ins[0] == 22 && (ins[1] == zz || ins[1] == -1)) {rain(xx,yy,zz							);}
				if(ins[0] == 23 && (ins[1] == zz || ins[1] == -1)) {test2(xx,yy,zz,							ins[2]);}
				if(ins[0] == 24 && (ins[1] == zz || ins[1] == -1)) {actual3D(xx,yy,zz						);}
				if(ins[0] == 25 && (ins[1] == zz || ins[1] == -1)) {dozUnIncrementIfVal(xx,yy,zz, 			ins[2], ins[3], ins[4], ins[5]);}
				if(ins[0] == 26 && (ins[1] == zz || ins[1] == -1)) {Threads(xx,yy,zz 						);}
				if(ins[0] == 27 && (ins[1] == zz || ins[1] == -1)) {PointToCircle(xx,yy,zz 					);}
				if(ins[0] == 28 && (ins[1] == zz || ins[1] == -1)) {diffusion(xx,yy,zz, 					Threshold);}
				if(ins[0] == 29 && (ins[1] == zz || ins[1] == -1)) {Brownian(xx,yy,zz, 						Rand);}
				if(ins[0] == 30 && (ins[1] == zz || ins[1] == -1)) {warts(xx,yy,zz 							);}
        		/**/
            	
            	//Set Universal initial parameters
            	//This determines which automata functions to run in what order, and with which parameter values.
            	u[0].instructions = new int[][] {
            			//Fill out Integer instruction arrays here.
            			//{InstructionNumber, Z-layer, args[]...} 
            			{28,0,400},
            			{29,0,128}
            			
                };
            	
            	/*
            	
            	u[1].instructions = new int[][] {
            			
            	};
            	
            	u[2].instructions = new int[][] {
            	
            	};
            	
            	/**/
            	
            	/*
            	//Example of a instruction set that is an entirely random combination of rulesets and thresholds
            	u[0].instructions = new int[][] {
            			{r.nextInt(14), 0, r.nextInt(4), r.nextInt(4), r.nextInt(4), r.nextInt(4), r.nextInt(4), r.nextInt(4)},
            			{r.nextInt(14), 0, r.nextInt(4), r.nextInt(4), r.nextInt(4), r.nextInt(4), r.nextInt(4), r.nextInt(4)},
            			{r.nextInt(14), 0, r.nextInt(4), r.nextInt(4), r.nextInt(4), r.nextInt(4), r.nextInt(4), r.nextInt(4)},
            			{r.nextInt(14), 0, r.nextInt(4), r.nextInt(4), r.nextInt(4), r.nextInt(4), r.nextInt(4), r.nextInt(4)},
            			{r.nextInt(14), 0, r.nextInt(4), r.nextInt(4), r.nextInt(4), r.nextInt(4), r.nextInt(4), r.nextInt(4)}
            	};
            	*/
            	
            	
            	//dataSources d = new dataSources();					//used for seeding/creating predefined patterns
            	ml myml = new ml(m,s,l);							//Mouselistener for user input;
            	mwl mymwl = new mwl(m,myml);							//Mouselistener for user input;
            	
            	
            	
            	
            	
            	//Set parameters for each surface/display panel
            	//           	Width,	Height,	Depth,		X,			Y,			universe
            	s[0] = new Surf(xSize, 	ySize, 	u[0].zlen,	2, 			2, 			u[0]);
            	/*s[1] = new Surf(xSize, 	ySize, 	u[1].zlen, 	xSize+8, 	2, 			u[1]);
            	s[2] = new Surf(xSize, 	ySize, 	u[2].zlen, 	2, 			ySize+4, 	u[2]);
            	s[3] = new Surf(xSize, 	ySize, 	u[2].zlen, 	xSize+8, 	ySize+4, 	u[2]);
            	/**/
            	
            	//Textbox for info display
            	l.setLayout(null); 
            	l.setBounds(2, ySize*1+16, 320, 14); 
            	l.setVisible(true);
            	l.setText("");
            	
            	//Layout for Main window/frame
            	m.setLayout(null);
            	m.setSize((xSize*1)+16, (ySize*1)+30+32);
            	m.setVisible(true);
            	m.setLocation(32, 32);
            	m.setLocation(96, 32);
            	m.setResizable(false);
            	
            	//Assemble the components
            	m.add(l);
            	for(int i = 0; i < surfaces; i++) { m.add(s[i]); }
            	
            	System.out.println("Assembled");
            	
            	myml.updateListing(); 	//show menu on start
            	
            	myml.toggleStart(); //Plays a fram so the universe is visible, unpauses
            	myml.toggleStart(); //second toggle pauses
            	
            	u[0].runOnce();			//Kickstart u[0] with the action in class Universe. Usually a seeding.
            	
            }
        });
    }
    
}
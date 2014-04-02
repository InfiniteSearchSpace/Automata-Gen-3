import java.util.Random;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class Main extends JFrame {

	
	public Main(){
		System.out.println("Main");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	public static void main(String[] args) {
		
        SwingUtilities.invokeLater(new Runnable() { 
            @Override
            public void run() { //breaks program's static dependance

            	//Define world's dimensional parameters
            	int xSize = 256; //maximum size of each surface
            	int ySize = 256;
            	int surfaces = 1;	//Total number of displays
            	int universes = 1;	//Total number of universes
            	
            	//instanciate essential elements 
            	Main m = new Main(); 								//Main window/frame
            	JLabel l = new JLabel(); 							//Textbox for info display
            	Surf[] s = new Surf[surfaces];						//Array of surface displays
            	Universe[] u = new Universe[universes];				//Array of universes
            	automataLib[] a = new automataLib[universes];					//Performs all logical functions for the sim; **Shouldn't need u or d.**; these functions could be contained in u, therefore automatalLib is a subclass of u
            	
            	//Instanciate automataLibs
            	a[0] = new automataLib(m);			
            	//a[1] = new automataLib(m);
            	//a[2] = new automataLib(m);
            	
            	//Instanciate Universes
            	//					Xsize	Ysize	Zsize	automataLib
            	u[0] = new Universe(xSize, 	ySize,  2, 	a[0]);			
            	//u[1] = new Universe(xSize, 	ySize, 	2, 	a[1]);
            	//u[2] = new Universe(xSize, 	ySize, 	2, 	a[2]);
            	
            	//Set Universal initial parameters
            	
            	/*
            	if(ins[0] == 0  seed(xx, yy, zz, 						Rand, Val);}
        		if(ins[0] == 1  conway(xx, yy, zz, 						ins[2], ins[3]);}
        		if(ins[0] == 2  dozIfVal(xx, yy, zz, 					val, z, NewVal, Rand);}
        		if(ins[0] == 3  dozDelMeIfNotLVal(xx, yy, zz, 			ins[2], ins[3], ins[4]);}
        		if(ins[0] == 4  quantum(xx,yy,zz,						Rand);}
        		if(ins[0] == 5  explorer(xx,yy,zz						);}
        		if(ins[0] == 6  dozDelIfVal(xx,yy,zz, 					ins[2], ins[3]);}
        		if(ins[0] == 7  dozDelIfNotVal(xx,yy,zz, 				ins[2], ins[3]);}
        		if(ins[0] == 8  doz021(xx,yy,zz, 						ins[2]);}
        		if(ins[0] == 9  dozDelMeIfVal(xx,yy,zz, 				ins[2], ins[3]);}
        		if(ins[0] == 10 quantumLorE(xx,yy,zz,					ins[2], ins[3]);}
        		if(ins[0] == 11 dozExclude(xx,yy,zz, 					ins[2], ins[3], ins[4]);}
        		if(ins[0] == 12 dozIncrementIfVal(xx,yy,zz, 			ins[2], ins[3], ins[4], ins[5]);}
        		if(ins[0] == 13 dozDelMeIfNotVal(xx,yy,zz, 				ins[2], ins[3], ins[4]);}
        		if(ins[0] == 14 quantumWeight(xx,yy,zz,					ins[2]);}
        		if(ins[0] == 15 twat(xx,yy,zz							);}
        		if(ins[0] == 16 rule110(xx,yy,zz						);}
        		int xx, int yy, int zz, int rand, int len, boolean placeO, boolean horiz, int veto, int val
        		/**/
        		
            	u[0].instructions = new int[][] {
            			
            			//{InstructionNumber, Z-layer, args[]...} 
            			//{0, 0, 1024, 1}, //seed, first layer, on rand 1024, with value 1
            			//{1, 1, 0, 0} //Do Conway's Game of Life
            			
            			{26, 0}         
            			
                };
            	
            	/*u[1].instructions = new int[][] {
            			
            	};
            	
            	u[2].instructions = new int[][] {
            			{0, -1, 10000, 1},
            			{1, -1, 1, 0},
            			{0, -1, 100, 0}
            	};
            	
            	/**/
            	
            	/*Random r = new Random();
            	u[0].instructions = new int[][] {
            			{r.nextInt(14), 0, r.nextInt(4), r.nextInt(4), r.nextInt(4), r.nextInt(4), r.nextInt(4), r.nextInt(4)},
            			{r.nextInt(14), 0, r.nextInt(4), r.nextInt(4), r.nextInt(4), r.nextInt(4), r.nextInt(4), r.nextInt(4)},
            			{r.nextInt(14), 0, r.nextInt(4), r.nextInt(4), r.nextInt(4), r.nextInt(4), r.nextInt(4), r.nextInt(4)},
            			{r.nextInt(14), 0, r.nextInt(4), r.nextInt(4), r.nextInt(4), r.nextInt(4), r.nextInt(4), r.nextInt(4)},
            			{r.nextInt(14), 0, r.nextInt(4), r.nextInt(4), r.nextInt(4), r.nextInt(4), r.nextInt(4), r.nextInt(4)}
            	};*/
            	
            	
            	
            	/*
            	dataSources d = new dataSources();					//used for seeding/creating non-random, predefined patterns
            	/**/
            	ml myml = new ml(m,s,l);							//Mouselistener for user input; **Shoulden't need s ref.**
            	/**/
            	
            	
            	
            	
            	
            	
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
            	//u[0].runOnce();			//Kickstart u[0]
            	
            }
        });
    }
    
}
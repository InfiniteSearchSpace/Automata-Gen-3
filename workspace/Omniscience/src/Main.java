import java.util.Random;
 
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class Main extends JFrame {

	
	public Main(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		
        SwingUtilities.invokeLater(new Runnable() { 
            @Override
            public void run() { //breaks program's static dependance

            	//Define world's dimensional parameters
            	int xSize = 3*2*25+1; //size of each surface
            	int ySize = 3*2*25+1;
            	
            	int surfaces = 1;	//Total number of displays
            	int universes = 1;	//Total number of universes
            	
            	//instanciate essential program elements 
            	Main m = new Main(); 								//Main window/frame
            	JLabel l = new JLabel(); 							//Textbox for info display
            	Surf[] s = new Surf[surfaces];						//Array of surface displays
            	Universe[] u = new Universe[universes];				//Array of universes/worlds
            	automataLib[] a = new automataLib[universes];		//Performs all automata functions for the sim;
            	
            	//Instanciate automataLibs 
            	//This may be unnessessary
            	a[0] = new automataLib(m);	
            	
            	//Instanciate Universes
            	//This can be used to run different simulations in different surfaces.
            	//still only runs one once cpu core per Main instance. 
            	//number of z layers is determined here
            	
            	//					Xsize	Ysize	Zsize	automataLib
            	u[0] = new Universe(xSize, 	ySize,  1, 	a[0]);
            	
            	//u[1] = new Universe(xSize, 	ySize, 	2, 	a[1]);
            	
            	
            	/*This is a list of functions as they appear in automatalib.
            	 * If you need to know the function's parameter type and appropriate values,
            	 * look inside automatalib. This will need to be cleaned and standardised later.

            	-1 seed(xx, yy, zz, 						ins[2], ins[3], ins[4]);}
				0  conway(xx, yy, zz 						);}
				1  sierpenski(xx, yy, zz 					);}
				2  ConwayExtendedRange(xx, yy, zz 			);}
				3  quantum(xx,yy,zz,						ins[2]);}
				4  explorer(xx,yy,zz						);}
				5  quantumWeight(xx,yy,zz,					ins[2]);}
				6  probbilityGrowth(xx,yy,zz				);}
				7  rule110(xx,yy,zz							);}
				8  rain2(xx,yy,zz							);}
				9  goop(xx,yy,zz							);}
				10 internalAffairs(xx,yy,zz					);}
				11 meekrochyp(xx,yy,zz						);}
				12 diamondShuffle(xx,yy,zz					);}
				13 rain(xx,yy,zz							);}
				14 actual3D(xx,yy,zz						);}
				15 warts(xx,yy,zz							);}
				16 Threads(xx,yy,zz 						);}
				17 PointToCircle(xx,yy,zz 					);}
				18 diffusion(xx,yy,zz, 						ins[2], ins[3]);}
				19 Brownian(xx,yy,zz, 						ins[2]);}
				20 warts2(xx,yy,zz 							);}
				21 Wave(xx,yy,zz, 							ins[2]);}
				22 snapSierpenski(xx,yy,zz 					);}
				23 rope(xx,yy,zz 							);}
				24 Inverse110(xx,yy,zz 						);}
				
        		/**/
            	
            	//Set Universal initial parameters
            	//This determines which automata functions to run in what order, and with which parameter values.
            	
            	u[0].instructions = new int[][] {
            			//Fill out Integer instruction arrays here.
            			//{InstructionNumber, Z-layer, args[]...} 

            			{0,0}
            			
                };
            	
            	
            	//dataSources d = new dataSources();		//used for seeding/creating predefined patterns
            	
            	ml myml = new ml(m,s,l,u);					//Mouselistener for user input;
            	mwl mymwl = new mwl(m,myml);				//MouseWheelListener for user input;
            	jMenuMain jmen = new jMenuMain(myml);			//add Menu Gui

            	//Set parameters for each surface/display panel
            	for(int i = 0; i < surfaces; i++) {
                    //        	   (Width,	Height,	Depth,		X,						Y,						Assigned universe)
            		s[i] = new Surf(xSize, 	ySize, 	u[0].zlen,	(xSize*i)+(2*i)+2+2,	(ySize*i)+(2*i)+16+2, 	u[0]);
            	}
            	
            	//Textbox for info display
            	l.setLayout(null); 
            	l.setBounds(4, 2, 300, 14); 
            	l.setVisible(true);
            	l.setText("Menu");
            	
            	//Layout for Main window/frame
            	m.setLayout(null);
            	
            	int minMenuXLen = 250;
            	if(xSize < minMenuXLen) {m.setSize(minMenuXLen, (ySize+2) * surfaces + 42 + 24 + 2+8);} else {
            		m.setSize((xSize+2+2) * surfaces + 6 + 2 ,(ySize+2) * surfaces + 42 + 24 + 2+8);
            	}
            	
            	m.setVisible(true);
            	m.setResizable(false);
            	m.setLocation(32, 32);
            	m.setLocation(96, 32);
            	
            	//Assemble the components
            	m.add(l);
            	for(int i = 0; i < surfaces; i++) { m.add(s[i]); }
            	
            	//Configure initial state
            	jmen.setGUI(m);
            	
            	
            	myml.updateListing(); 	//show menu on start
            	
            	myml.toggleStart(); //Plays a fram so the universe is visible, unpauses
            	myml.toggleStart(); //second toggle pauses
            	
            	//makes sure first surface is visible
            	u[0].runOnce(1,0);
            	
            	//Kickstart u[0] with the action in class Universe. Usually a seeding. 
            	//Takes int: rand, val
            	u[0].runOnce(myml.seedRand,myml.seedVal);
            	
            	
            }
        });
    }
    
	
	
}
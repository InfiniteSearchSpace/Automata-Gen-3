import java.awt.Color;
import java.util.Random;
 
import javax.swing.JFrame;
import javax.swing.SwingConstants;
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
            	int xSize = 3*2*30+1; //size of each surface
            	int ySize = 3*2*30+1;
            	int zSize = 16;
            	
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
            	u[0] = new Universe(xSize, 	ySize,  zSize, 	a[0]);
            	
            	//u[1] = new Universe(xSize, 	ySize, 	2, 	a[1]);
            	
            	
            	/*There is a list of functions in automatalib.
            	 * If you need to know the function's parameter types and appropriate values,
            	 * look inside automatalib.
            	 */
            	
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
            	MouseMotionList mMot = new MouseMotionList(m, myml);				//MouseWheelListener for user input;
            	jMenuMain jmen = new jMenuMain(myml);			//add Menu Gui

            	//Set parameters for each surface/display panel
            	for(int i = 0; i < surfaces; i++) {
                    //        	   (Width,	Height,	Depth,		X,						Y,						Assigned universe)
            		s[i] = new Surf(xSize, 	ySize, 	u[0].zlen,	(xSize*i)+(2*i)+2+2,	(ySize*i)+(2*i)+16+2, 	u[0]);
            	}
            	
            	//Textbox for info display
            	l.setLayout(null); 
            	l.setBounds(xSize+16, 2, 80+40, 10*(10+4));
            	l.setVisible(true);
            	l.setText("Menu");
            	
            	//Layout for Main window/frame
            	m.setLayout(null);

            	int minMenuXLen = 300;
            	int minMenuYLen = 210;
            	
            	int xxsize = (xSize+2+2) * surfaces + 6 + 2 +120;
            	int yysize = (ySize+2) * surfaces + 42 + 24 + 2+8;
            	
            	if(xSize < minMenuXLen) {if(xxsize < minMenuXLen){xxsize = minMenuXLen;}}
				if(ySize < minMenuYLen) {if(yysize < minMenuYLen){yysize = minMenuYLen;}}
				m.setSize(xxsize, yysize);
            	
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
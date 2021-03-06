import java.awt.Color;
import java.awt.Component;
import java.util.Random;
 
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class Main extends JFrame {

	public Main(){
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		int x=200, y=200, z=16;
		
		String str = JOptionPane.showInputDialog(this, "Length", x);
    	if(str != null) {
    		x = Integer.parseInt(str);
    	}
    	
    	str = JOptionPane.showInputDialog(this, "Height", y);
    	if(str != null) {
    		y = Integer.parseInt(str);
    	}
    	
    	 str = JOptionPane.showInputDialog(this, "Depth", z);
    	if(str != null) {
    		z = Integer.parseInt(str);
    	}
    	
		setSizes(x, y, z);
	}
	
	public void setSizes(int xSize, int ySize, int zSize) {
				
		int minMenuXLen = 340;
        int minMenuYLen = 260;
            	
        int surfaces = 1;	//Total number of displays
        int universes = 1;	//Total number of universes
		
        JLabel l = new JLabel(); 							//Textbox for info display
    	Surf[] s = new Surf[surfaces];						//Array of surface displays
    	Universe[] u = new Universe[universes];				//Array of universes/worlds
    	automataLib[] a = new automataLib[universes];		//Performs all automata functions for the sim;
    	slider[] sl = new slider[2]; 
    	
    	//Instanciate automataLibs 
    	//This may be unnessessary
    	a[0] = new automataLib(this,xSize,ySize);	
    	
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
    			//{InstructionNumber, Z-layer, args[2-4]...} 

    			{0,0,1,1,1}
    			
        };
    	
    	int xxsize = (xSize+2+2) * surfaces + 6 + 2 +120 + 56;
    	int yysize = (ySize+2) * surfaces + 42 + 24 + 2+8;
    	
    	if(xSize < minMenuXLen) {if(xxsize < minMenuXLen){xxsize = minMenuXLen;}}
		if(ySize < minMenuYLen) {if(yysize < minMenuYLen){yysize = minMenuYLen;}}
		
		
    	
    	//dataSources d = new dataSources();		//used for seeding/creating predefined patterns
    	
    	ml myml = new ml(this,s,l,u,sl);					//Mouselistener for user input;
    	sl[0] = new slider(this,xSize,yysize,zSize, myml, 0, 5);
    	sl[1] = new slider(this,xSize,yysize,zSize, myml, 1, 25);
    	
    	//slider sl3 = new slider(m,xSize,ySize,zSize, myml, 2, 0);
    	
    	mwl mymwl = new mwl(this,myml);				//MouseWheelListener for user input;
    	MouseMotionList mMot = new MouseMotionList(this, myml);				//MouseWheelListener for user input;
    	jMenuMain jmen = new jMenuMain(myml);			//add Menu Gui

    	//Set parameters for each surface/display panel
    	for(int i = 0; i < surfaces; i++) {
            //        	   (Width,	Height,	Depth,		X,						Y,						Assigned universe)
    		s[i] = new Surf(xSize, 	ySize, 	u[0].zlen,	(xSize*i)+(2*i)+2+2,	(ySize*i)+(2*i)+16+2, 	u[0]);
    	}
    	
    	//Textbox for info display
    	l.setLayout(null); 
    	l.setBounds(xSize+16, 2, 80+40, 14*(10+4));
    	l.setVisible(true);
    	
    	l.setText("Menu");
    	
    	//Layout for Main window/frame
    	this.setLayout(null);

    	this.setSize(xxsize, yysize);
    	
    	this.setVisible(true);
    	this.setResizable(false);
    	this.setLocation(96, 32);
    	
    	//Assemble the components
    	this.add(l);
    	for(int i = 0; i < surfaces; i++) { this.add(s[i]); }
    	
    	//Configure initial state
    	jmen.setGUI(this);
    	
    	
    	myml.updateListing(); 	//show menu on start
    	
    	myml.toggleStart(); //Plays a fram so the universe is visible, unpauses
    	myml.toggleStart(); //second toggle pauses
    	
    	//makes sure first surface is visible
    	u[0].runOnce(1,0);
    	
    	//Kickstart u[0] with the action in class Universe. Usually a seeding. 
    	//Takes int: rand, val
    	u[0].runOnce(myml.seedRand,myml.seedVal);
    	
    	myml.ruleChanged(0);
    	
    	myml.refresh();
  
    	
	}
	
	public static void main(String[] args) {
		
        SwingUtilities.invokeLater(new Runnable() { 
            @Override
            public void run() { //breaks program's static dependance
            	new Main(); //Main window/frame
            }
        });
    }
    
	
	
}











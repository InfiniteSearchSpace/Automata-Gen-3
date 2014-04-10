
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
 
public class jMenuMain implements ActionListener {

    ml mML;
    Main m;
    
    //constructor, captures UI controller: ml
    public jMenuMain(ml myml) {
		mML = myml;
	}

    //instanciate & deploy menu bar
	public void setGUI(Main mm) {
    	m = mm;
    	
        jMenuMain menuMain = new jMenuMain(mML);
        m.setJMenuBar(menuMain.createMenuBar());
	}

	public JMenuBar createMenuBar() {
        JMenuBar menuBar;
        JMenu menu;
        JMenuItem menuItem;
 
        //menu bar
        menuBar = new JMenuBar();
 
        //create new menu
        menu = new JMenu("Universe");
        menuBar.add(menu);
 
        //Populate menu
        menuItem = new JMenuItem("Play/Pause");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        menuItem = new JMenuItem("Reseed All");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        menuItem = new JMenuItem("Erase All");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        //create new menu
        menu = new JMenu("Tools");
        menuBar.add(menu);
        
        //Populate menu
        menuItem = new JMenuItem("Place Solid Blocks");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        menuItem = new JMenuItem("Place Random Blocks");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        menuItem = new JMenuItem("Erase Blocks");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        menuItem = new JMenuItem("Place Value 100 Blocks");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        /*menuItem = new JMenuItem("Utilities");
        menuItem.addActionListener(this);
        menu.add(menuItem);*/

        //create new menu
        menu = new JMenu("Cycle");
        menuBar.add(menu);

        //feature use hint:
        menuItem = new JMenuItem("Select MouseWheel cycle target:");
        menu.add(menuItem);
        
        menu.addSeparator();
        
        //Populate menu
        menuItem = new JMenuItem("Subfunctions");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        menuItem = new JMenuItem("Z-layers");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        menuItem = new JMenuItem("Automata Rulesets");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        
        /*//submenu
        submenu = new JMenu("Submenu");
        menuItem = new JMenuItem("Item");
        menuItem.addActionListener(this);
        submenu.add(menuItem);
        menu.add(submenu);*/

        return menuBar;
    }
 
    public void actionPerformed(ActionEvent e) {
        JMenuItem source = (JMenuItem)(e.getSource());
        
        if(source.getText() == "Play/Pause") {mML.toggleStart();}
        if(source.getText() == "Erase All") {mML.eraseAll();}
        if(source.getText() == "Reseed All") {mML.reseedAll(4, 1);}
        
        if(source.getText() == "Place Solid Blocks") {mML.setFunctionType(0);}
        if(source.getText() == "Place Random Blocks") {mML.setFunctionType(1);}
        if(source.getText() == "Erase Blocks") {mML.setFunctionType(2);}
        if(source.getText() == "Place Value 100 Blocks") {mML.setFunctionType(3);}
        //if(source.getText() == "Utilities") {mML.setFunctionType(4);}
        
        if(source.getText() == "Subfunctions") 		{mML.mwPos = mML.myFunction; 				mML.mwMax = mML.fcnt; 				mML.cycleNum = 0;}
        if(source.getText() == "Z-layers") 			{mML.mwPos = mML.sfcnum; 					mML.mwMax = mML.s[mML.sfcnum].zz; 	mML.cycleNum = 1;}
        if(source.getText() == "Automata Rulesets") {mML.mwPos = mML.u[0].instructions[0][0]; 	mML.mwMax = 24; 					mML.cycleNum = 2;}

        mML.refresh();
    }
 
}

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

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
        //JMenu submenu;
        
        
        //menu bar
        menuBar = new JMenuBar();
        
        //create new menu
        menu = new JMenu("Universe");
        menuBar.add(menu);
 
        //Populate menu
        menuItem = new JMenuItem("Play/Pause");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
         menuItem = new JMenuItem("Reset To Default");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        menu.addSeparator();
        
        menuItem = new JMenuItem("Erase Layer");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        menuItem = new JMenuItem("Erase All");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        menu.addSeparator();

        menuItem = new JMenuItem("Reseed Layer");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        menuItem = new JMenuItem("Reseed All");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        menuItem = new JMenuItem("Set Seed");
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
        
        menu.addSeparator();
        
        menuItem = new JMenuItem("Change Block Size");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        menuItem = new JMenuItem("Change Block Value");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        
        
        
        menu = new JMenu("Ruleset");
        menuBar.add(menu);
        
    	 menuItem = new JMenuItem("Remove Last Rule");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        menu.addSeparator();
        
        menuItem = new JMenuItem("Add Rule To Ruleset");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        menuItem = new JMenuItem("Add Constant Seeding");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        menuItem = new JMenuItem("Add Random Rule");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        menuItem = new JMenuItem("Recast Rule Parameters");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        menu.addSeparator();
         
        menuItem = new JMenuItem("Combination Ruleset");
        menuItem.addActionListener(this);
        menu.add(menuItem); 
        
       
        
        

        //create new menu
        menu = new JMenu("MouseWheel");
        menuBar.add(menu);

        //Populate menu
        menuItem = new JMenuItem("Tool Subfunctions");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        menuItem = new JMenuItem("Z-layers");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        menuItem = new JMenuItem("Automata Rulesets");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        menuItem = new JMenuItem("Block Size");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        menuItem = new JMenuItem("Block Value");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        
        
        /*submenu = new JMenu("GoToRule");
        		menuItem = new JMenuItem("Item");
        		menuItem.addActionListener(this);
        	submenu.add(menuItem);
        menu.add(submenu);*/

        return menuBar;
    }
 
    public void actionPerformed(ActionEvent e) {
        JMenuItem source = (JMenuItem)(e.getSource());
        
        Random r = new Random();
        
        
        if(source.getText() == "Play/Pause") {mML.toggleStart();}

        if(source.getText() == "Erase Layer") {mML.eraseLayer();}
        if(source.getText() == "Erase All") {mML.eraseAll();}
        if(source.getText() == "Reset To Default") {
        	mML.eraseAll();
        	mML.resetRule();
        	mML.setFunctionType(0);
        	mML.mwMax = mML.fcnt;
        	mML.cycleNum = 0;
        	mML.myFunction=0;
        	mML.sfcnum=0;
        	mML.blockVal = 1;
        	mML.seedRand = 4;
        	mML.seedVal = 1;
        	mML.blockSize = 1;
        	mML.mwPos = 0;
        	mML.params = new int[] {0,0,0,0};
        	mML.seedRndVar = 1;
        }

        if(source.getText() == "Reseed Layer") 	{mML.reseedLayer(mML.seedRand, mML.seedVal);}
        if(source.getText() == "Reseed All") 	{mML.reseedAll(mML.seedRand, mML.seedVal);}
        if(source.getText() == "Set Seed") 		{mML.dialogReseed();}

        if(source.getText() == "Remove Last Rule") {mML.removeRule();}
        
        if(source.getText() == "Add Rule To Ruleset") 		{mML.dialogAddRule();}
        if(source.getText() == "Add Constant Seeding") 		{mML.dialogAddSeed();}
        if(source.getText() == "Add Random Rule") 			{mML.addRandomRule();}
        if(source.getText() == "Recast Rule Parameters") 	{mML.recastParams();}
        
        if(source.getText() == "Combination Ruleset") {mML.reseedAll(mML.seedRand, mML.seedVal); mML.setRandom_3_InstructionWithSeed();}
                
        if(source.getText() == "Place Solid Blocks") 	{mML.setFunctionType(0);setBlocks();mML.blockSize = -1;}
        if(source.getText() == "Place Random Blocks") 	{mML.setFunctionType(1);setBlocks();mML.blockSize = -1;}
        if(source.getText() == "Erase Blocks") 			{mML.setFunctionType(2);setBlocks();mML.blockSize = -1;}
        
        if(source.getText() == "Change Block Value") 	{mML.dialogSetBlockVal();}
        if(source.getText() == "Change Block Size") 	{mML.dialogSetBlockSize();}
        
        if(source.getText() == "Tool Subfunctions") {mML.mwPos = mML.myFunction; 				mML.mwMax = mML.fcnt; 				mML.cycleNum = 0;}
        if(source.getText() == "Z-layers") 			{mML.mwPos = mML.s[mML.sfcnum].zdraw; 		mML.mwMax = mML.s[mML.sfcnum].zz; 	mML.cycleNum = 1;}
        if(source.getText() == "Automata Rulesets") {mML.mwPos = mML.rule; 						mML.mwMax = mML.totalFunctions; 	mML.cycleNum = 2;}
        if(source.getText() == "Block Size") 		{mML.mwPos = mML.blockSize; 				mML.mwMax = 100; 					mML.cycleNum = 3;}
        if(source.getText() == "Block Value") 		{mML.mwPos = mML.blockVal; 					mML.mwMax = 100; 					mML.cycleNum = 4;}

        mML.refresh();
    }
 
    private void setBlocks(){
    	mML.mwPos = mML.myFunction;
    	mML.mwMax = mML.fcnt;
    	mML.cycleNum = 0;
    }
    
    
    
    
    
    
}
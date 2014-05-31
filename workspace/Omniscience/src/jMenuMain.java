
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
 
public class jMenuMain implements ActionListener {

    ml mML;
    Main m;
    ToggleFrame TF;
    
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
        
        
        
        menuItem = new JMenuItem("TEST");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        menuItem = new JMenuItem("TEST GET");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        menuItem = new JMenuItem("TEST SET");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        
        
        
        //Populate menu
        menuItem = new JMenuItem("Play/Pause");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        menuItem = new JMenuItem("Reset To Default");
        menuItem.addActionListener(this);
        menu.add(menuItem); 
        
        menuItem = new JMenuItem("Select Next Window");
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
        
        menuItem = new JMenuItem("Reapply Custom Tool");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        menuItem = new JMenuItem("Tool: Copy");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        menuItem = new JMenuItem("Tool: Paste");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        menu.addSeparator();
        
        menuItem = new JMenuItem("Set Tool Size");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        menuItem = new JMenuItem("Set Tool Value");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        menuItem = new JMenuItem("Set Tool Distribution");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        menuItem = new JMenuItem("Set Tool Value Variation");
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
        
        /*menuItem = new JMenuItem("Reassign Growth Function");
        menuItem.addActionListener(this);
        menu.add(menuItem);*/
        
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
        
        menuItem = new JMenuItem("Tool Size");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        menuItem = new JMenuItem("Tool Value");
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
        	mML.resetRuleDef();
        	mML.setFunctionType(0);
        	mML.mwMax = mML.fcnt;
        	mML.cycleNum = 0;
        	mML.myFunction=0;
        	mML.sfcnum=0;
        	mML.seedRand = 4;
        	mML.seedRndVar = 1;
        	mML.seedVal = 1;
        	mML.mwPos = 0;
        	mML.params = new int[] {0,0,0,0};
        	
        	mML.blockVal = 1;
        	mML.toolRand = 0;
        	mML.toolVar = 0;
        	mML.blockSize = 1;
        	
        	mML.gui_blockVal = mML.blockVal;
        	mML.gui_toolRand = mML.toolRand;
        	mML.gui_toolVar = mML.toolVar;
        	mML.gui_blockSize = mML.blockSize;
        	
        	mML.resetVal = true;
        	mML.slideVal = new int[3];
        	mML.growDieThreshold = 0;
        	mML.ruleChanged(0);
        	
        	
        	
        	mML.reseedAll(6, 1);
        	
        }
        
        if(source.getText() == "Select Next Window") {mML.cycleUni();}

        if(source.getText() == "Reseed Layer") 	{mML.reseedLayer(mML.seedRand, mML.seedVal);}
        if(source.getText() == "Reseed All") 	{mML.reseedAll(mML.seedRand, mML.seedVal);}
        if(source.getText() == "Set Seed") 		{mML.dialogReseed();}

        if(source.getText() == "Remove Last Rule") {mML.removeRule();}
        
        if(source.getText() == "Add Rule To Ruleset") 		{mML.dialogAddRule();}
        if(source.getText() == "Add Constant Seeding") 		{mML.dialogAddSeed();}
        if(source.getText() == "Add Random Rule") 			{mML.addRandomRule();}
        if(source.getText() == "Recast Rule Parameters") 	{mML.recastParams();}
        //if(source.getText() == "Reassign Growth Function") 	{mML.dialogRuleBounds();}
        
        if(source.getText() == "Combination Ruleset") {mML.reseedAll(mML.seedRand, mML.seedVal); mML.setRandom_3_InstructionWithSeed();}
                
        if(source.getText() == "Place Solid Blocks") 	{mML.setFunctionType(0);setBlocks();mML.blockSize = -1;mML.toolRand = -1;mML.toolVar = -1;mML.resetVal = true;}
        if(source.getText() == "Place Random Blocks") 	{mML.setFunctionType(1);setBlocks();mML.blockSize = -1;mML.toolRand = -1;mML.toolVar = -1;mML.resetVal = true;}
        if(source.getText() == "Erase Blocks") 			{mML.setFunctionType(2);setBlocks();mML.blockSize = -1;mML.toolRand = -1;mML.toolVar = -1;mML.resetVal = true;}

        if(source.getText() == "Reapply Custom Tool") 			{mML.snapToCustomTool();}

        
        if(source.getText() == "Tool: Copy") 					{mML.setFunctionType(3);mML.myFunction = 2;setBlocks();mML.blockSize = -1;mML.toolRand = -1;mML.toolVar = -1;mML.resetVal = true;}
        if(source.getText() == "Tool: Paste") 					{mML.setFunctionType(3);mML.myFunction = 1;setBlocks();mML.blockSize = -1;mML.toolRand = -1;mML.toolVar = -1;mML.resetVal = true;}
        
        if(source.getText() == "Set Tool Size") 				{mML.dialogSetBlockSize();}
        if(source.getText() == "Set Tool Value") 				{mML.dialogSetBlockVal();}
        if(source.getText() == "Set Tool Distribution") 		{mML.dialogSetBlockRand();}
        if(source.getText() == "Set Tool Value Variation") 		{mML.dialogSetBlockVar();}
        
        if(source.getText() == "Tool Subfunctions") {mML.mwPos = mML.myFunction; 				mML.mwMax = mML.fcnt; 				mML.cycleNum = 0;}
        if(source.getText() == "Z-layers") 			{mML.mwPos = mML.s[mML.sfcnum].zdraw; 		mML.mwMax = mML.s[mML.sfcnum].zz; 	mML.cycleNum = 1;}
        if(source.getText() == "Automata Rulesets") {mML.mwPos = mML.rule; 						mML.mwMax = mML.totalFunctions; 	mML.cycleNum = 2;}
        if(source.getText() == "Tool Size") 		{mML.mwPos = mML.gui_blockSize; 			mML.mwMax = 100; 					mML.cycleNum = 3;}
        if(source.getText() == "Tool Value") 		{mML.mwPos = mML.gui_blockVal; 				mML.mwMax = 100; 					mML.cycleNum = 4;}
        
        
        
        if(source.getText() == "TEST") 		{
        	TF = new ToggleFrame();
        }
        
        if(source.getText() == "TEST GET") 		{
        	if(TF != null) {
        		int[][] tmpInt = TF.getPanelStates();
        	} else {
        		System.out.println("NULL POINTER EXCEPTION");
        	}
		} 
		
        if(source.getText() == "TEST SET") 		{
        	if(TF != null) {
        		int[][][] intAr = mML.d.getArray();
        		for(int i = 0; i < TF.pp.length; i++){
        			for(int j = 0; j < TF.pp[i].length; j++){
            			TF.pp[j][i].setVal(intAr[j][i][0]);
            		}
        		}
        		
        	} else {
        		System.out.println("NULL POINTER EXCEPTION");
        	}
		} 	
        

        mML.refresh();
    }
 
    private void setBlocks(){
    	mML.mwPos = mML.myFunction;
    	mML.mwMax = mML.fcnt;
    	mML.cycleNum = 0;
    }
    
    
    
    
    
    
}
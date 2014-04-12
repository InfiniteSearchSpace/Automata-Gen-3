
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
        
        menu.addSeparator();
        
        menuItem = new JMenuItem("Erase All");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        menuItem = new JMenuItem("Reset To Default");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
       /* menuItem = new JMenuItem("Resize");
        menuItem.addActionListener(this);
        menu.add(menuItem);*/
        
        menu.addSeparator();
        
        menuItem = new JMenuItem("Quick Reseed");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        menuItem = new JMenuItem("Set Seed");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        menuItem = new JMenuItem("Add Constant Seeding");
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
        
        menuItem = new JMenuItem("Place Value x Blocks");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        menu.addSeparator();
        
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
        
        menuItem = new JMenuItem("Add Random Rule");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
         menu.addSeparator();
         
        menuItem = new JMenuItem("Set Rule");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        menuItem = new JMenuItem("Set Random Rule");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        menuItem = new JMenuItem("Combination Ruleset");
        menuItem.addActionListener(this);
        menu.add(menuItem); 
        
       
        
        
        
        
        
     
        
        
        
        
        /*menuItem = new JMenuItem("Utilities");
        menuItem.addActionListener(this);
        menu.add(menuItem);*/

        //create new menu
        menu = new JMenu("Cycle");
        menuBar.add(menu);

        //feature use hint:
        menuItem = new JMenuItem("MouseWheel Target:");
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

        if(source.getText() == "Reset To Default") {
        	mML.eraseAll();
        	mML.resetRule();
        	mML.setFunctionType(0);
        	mML.mwMax = mML.fcnt;
        	mML.cycleNum = 0;
        	mML.myFunction=0;
        	mML.sfcnum=0;
        	mML.blockVal = 100;
        	mML.seedRand = 2;
        	mML.seedVal = 1;
        }

        if(source.getText() == "Erase All") {mML.eraseAll();}
        //if(source.getText() == "Resize") {mML.resize();}
        
        
        if(source.getText() == "Quick Reseed") {mML.reseedAll(r.nextInt(48)+1, 1);}
        if(source.getText() == "Set Seed") {mML.dialogReseed();}
        if(source.getText() == "Add Constant Seeding") {mML.dialogAddSeed();}
        
        if(source.getText() == "Set Rule") {mML.dialogRule();}
        if(source.getText() == "Add Rule To Ruleset") {mML.dialogAddRule();}
        if(source.getText() == "Remove Last Rule") {mML.removeRule();}
        
        if(source.getText() == "Set Random Rule") {mML.reseedAll(8, 1); mML.setRandomRule();}
        if(source.getText() == "Add Random Rule") {mML.addRandomRule();}
        if(source.getText() == "Combination Ruleset") {mML.reseedAll(8, 1); mML.setRandom_3_InstructionWithSeed();}
                
        if(source.getText() == "Place Solid Blocks") {mML.setFunctionType(0);setBlocks();}
        if(source.getText() == "Place Random Blocks") {mML.setFunctionType(1);setBlocks();}
        if(source.getText() == "Erase Blocks") {mML.setFunctionType(2);setBlocks();}
        if(source.getText() == "Place Value x Blocks") {mML.setFunctionType(3);setBlocks();}
        if(source.getText() == "Change Block Value") {mML.dialogSetBlockVal();}
        //if(source.getText() == "Utilities") {mML.setFunctionType(4);}
        
        if(source.getText() == "Subfunctions") 		{mML.mwPos = mML.myFunction; 				mML.mwMax = mML.fcnt; 				mML.cycleNum = 0;}
        if(source.getText() == "Z-layers") 			{mML.mwPos = mML.sfcnum; 					mML.mwMax = mML.s[mML.sfcnum].zz; 	mML.cycleNum = 1;}
        if(source.getText() == "Automata Rulesets") {mML.mwPos = mML.u[0].instructions[0][0]; 	mML.mwMax = mML.totalFunctions; 					mML.cycleNum = 2;}

        mML.refresh();
    }
 
    private void setBlocks(){
    	mML.mwPos = mML.myFunction;
    	mML.mwMax = mML.fcnt;
    	mML.cycleNum = 0;
    }
    
    
    
    
    
    
}
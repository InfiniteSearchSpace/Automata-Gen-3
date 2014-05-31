
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
 
public class jMenuEditor implements ActionListener {

	ToggleFrame TF;
	ml ML;
    //constructor, captures UI controller: ml
    public jMenuEditor(ToggleFrame TFrame, ml mML) {
    	ML = mML;
    	TF = TFrame;
        TF.setJMenuBar(createMenuBar());
	}

	public JMenuBar createMenuBar() {

		JMenuBar menuBar;
        JMenu menu;
        JMenuItem menuItem;
        //JMenu submenu;
        
        //menu bar
        menuBar = new JMenuBar();
        
        //create new menu
        menu = new JMenu("Clipboard");
        menuBar.add(menu);
        
        //Populate menu
        menuItem = new JMenuItem("-> Paste From Clip");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        menuItem = new JMenuItem("Copy To Clip ->");
        menuItem.addActionListener(this);
        menu.add(menuItem); 
        
        return menuBar;
    }
 
    public void actionPerformed(ActionEvent e) {
    	
        JMenuItem source = (JMenuItem)(e.getSource());
        
        /*if(source.getText() == "Print Data") 		{
        	if(TF != null) {
        		int[][] tmpInt = TF.getPanelStates();
        	} else {
        		System.out.println("NULL POINTER EXCEPTION");
        	}
		} */
        
        
        if(source.getText() == "-> Paste From Clip") 		{
        	if(TF != null) {
        		int[][][] intAr = ML.d.getArray();
        		for(int i = 0; i < TF.pp.length; i++){
        			for(int j = 0; j < TF.pp[i].length; j++){
            			TF.pp[j][i].setVal(intAr[j][i][0]);
            		}
        		}
        		
        	} else {
        		System.out.println("NULL POINTER EXCEPTION");
        	}
		} 	
        
        
        if(source.getText() == "Copy To Clip ->") 		{
        	if(TF != null) {
        		int[][][] intAr = new int[TF.pp.length][TF.pp[0].length][1];
        		for(int i = 0; i < TF.pp.length; i++){
        			for(int j = 0; j < TF.pp[i].length; j++){
            			intAr[j][i][0] = TF.pp[j][i].val;
            		}
        		}
        		
        		ML.d.setArray(intAr);
        		
        	} else {
        		System.out.println("NULL POINTER EXCEPTION");
        	}
		} 	
        
        
    }
 
    
    
    
    
    
    
}
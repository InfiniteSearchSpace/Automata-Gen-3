
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
 
public class jMenuEditor implements ActionListener {

	ToggleFrame TF;
	ml ML;
	int[][] nbrhood;
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
        
        menu = new JMenu("Other");
        menuBar.add(menu);
        
        //Populate menu
        menuItem = new JMenuItem("-> Set Neighbourhood");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
      /*  menuItem = new JMenuItem("-> Set Rule(WIP)");
        menuItem.addActionListener(this);
        menu.add(menuItem); */
        
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

        if(source.getText() == "-> Set Neighbourhood") 		{
        	if(TF != null) {
        		int[][][] intAr = new int[TF.pp.length][TF.pp[0].length][1];
        		for(int i = 0; i < TF.pp.length; i++){
        			for(int j = 0; j < TF.pp[i].length; j++){
            			intAr[j][i][0] = TF.pp[j][i].val;
            		}
        		}
        		
        		/*String s = "";
        		for(int i = 0; i < TF.pp.length; i++){
        			for(int j = 0; j < TF.pp[i].length; j++){
            			s+=intAr[j][i][0];
            		}
        			s+="\n";
        		}
        		System.out.println(s);*/
        		
        		//get the centre cell (-1)
        		int ii=0; 
        		int jj=0;
        		
        		for(int i = 0; i < TF.pp.length; i++){
        			for(int j = 0; j < TF.pp[i].length; j++){
            			if(intAr[i][j][0] == -1) {ii=i;jj=j;}
            		}
        		}
        		
        		System.out.println("i:"+ii+" j:" +jj);
        		
        		//get number of == 1 cells
        		int nbrCount = 0;
        		for(int i = 0; i < TF.pp.length; i++){
        			for(int j = 0; j < TF.pp[i].length; j++){
            			if(intAr[j][i][0] == 1) {
            				nbrCount++;
            			}
            		}
        		}
        		
        		//get 1-cell locations
        		nbrhood = new int[nbrCount][4];
        		int placedNbr = nbrCount;
        		
        		for(int i = 0; i < TF.pp.length; i++){
        			for(int j = 0; j < TF.pp[i].length; j++){
            			if(intAr[i][j][0] == 1) {
            				nbrhood[nbrCount-placedNbr][0]=i;
            				nbrhood[nbrCount-placedNbr][1]=j;
            				nbrhood[nbrCount-placedNbr][2] = 0;
            				nbrhood[nbrCount-placedNbr][3] = nbrCount-placedNbr;
            				placedNbr--;
            			}
            		}
        		}
        		        		
        		//transform cells by (-ii, -jj)
        		for(int i = 0; i < nbrhood.length; i++){
        			nbrhood[i][0]-=ii;
        			nbrhood[i][1]-=jj;
        		}
        		
        		//set as neighbours
        		
        		
        		/*String s = "";
        		for(int i = 0; i < nbrhood.length; i++){
        			for(int j = 0; j < nbrhood[i].length; j++){
            			s+=nbrhood[i][j]+",";
            		}
        			s+="\n";
        		}
        		System.out.println(s);*/
        		
        		ML.customTF_NHood = nbrhood;
        		ML.u[0].a.n.TFNbH = nbrhood;
        		
        	} else {
        		System.out.println("NULL POINTER EXCEPTION");
        	}
        }

        if(source.getText() == "-> Set Rule(WIP)") 		{
        	
        }
        
        
    }
 
    
    
    
    
    
    
}
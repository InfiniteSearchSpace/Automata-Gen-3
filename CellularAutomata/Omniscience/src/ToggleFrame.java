
import javax.swing.JFrame;


public class ToggleFrame extends JFrame {
	
	ClickPanel[][] pp;
	
	int pXCount = 0;
	int pYCount = 0;
	int lastsize = 12;
	
	public ToggleFrame(ml ML) {
		initTF();
		jMenuEditor jmen = new jMenuEditor(this, ML);
	}
	
	public void initTF(){
		int panelSize = 10;
		int panels = lastsize;
		int frameSize = (panelSize+1)*panels;
		
		setLayout(null);
		
		int frx = frameSize+8+8;
		int fry = frameSize+30+22+8;

		if(frx < 96) { frx = 96; }
		if(fry < 96) { fry = 96; }
		
		setSize(frx, fry);
		
		setVisible(false);
		setResizable(false);
		setLocation(96, 32);
		
		int panelsX = (frameSize/(panelSize+1));
		int panelsY = (frameSize/(panelSize+1));
		pXCount = panelsX;
		pYCount = panelsY;
		
		pp = new ClickPanel[panelsX][panelsY];
		
		for(int i = 0; i < panelsX; i++) {
			for(int j = 0; j < panelsY; j++) {
	    		pp[i][j] = new ClickPanel(2, i, j, panelSize);
	    		add(pp[i][j]);
			}
		}
	}
	
	public void reinitTF(int blockSize){
		int panelSize = 10;
		int panels = blockSize;
		if(panels == -1) {panels = lastsize;} else {lastsize = panels;}
		int frameSize = (panelSize+1)*panels;
		
		int frx = frameSize+8+8;
		int fry = frameSize+30+22+8;

		if(frx < 96) { frx = 96; }
		if(fry < 96) { fry = 96; }
		
		setSize(frx, fry);
		
		setLocation(96, 32);
		
		for(int i = 0; i < pYCount; i++) {
			for(int j = 0; j < pXCount; j++) {
	    		remove(pp[i][j]);
			}
		}
		
		pp = null;
		
		int panelsX = (frameSize/(panelSize+1));
		int panelsY = (frameSize/(panelSize+1));
		pXCount = panelsX;
		pYCount = panelsY;
		pp = new ClickPanel[panelsX][panelsY];
		
		for(int i = 0; i < panelsX; i++) {
			for(int j = 0; j < panelsY; j++) {
	    		pp[i][j] = new ClickPanel(16, i, j, panelSize);
	    		add(pp[i][j]);
			}
		}
	}
	
	public void togShowTF() {
		if(this.isVisible()) {
			this.dispose();
		} else {
			setVisible(true);
		}
	}
	
	public void showTF() {
		setVisible(true);
	}
	
	public int [][] getPanelStates() {
		int[][] output = new int[pp.length][pp[0].length];
    	String ss = "";
    	
    	for(int j = 0; j < pp.length; j++) {
    		for(int i = 0; i < pp[j].length; i++) {
    			
        		output[i][j] = pp[i][j].val;
        		ss+=output[i][j] + ",";
        		
        	}
    		ss+="\n";
    	}
    	System.out.println(ss);
    	return output;
	}
	
	
}

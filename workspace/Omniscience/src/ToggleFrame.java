import javax.swing.JFrame;


public class ToggleFrame extends JFrame {
	
	ClickPanel[][] pp;
	
	public ToggleFrame() {
		initTF();
	}
	
	public void initTF(){
		int panelSize = 10;
		int panels = 12;
		int frameSize = (panelSize+1)*panels;
		
		setLayout(null);
		setSize(frameSize+8, frameSize+30);
		setVisible(true);
		setResizable(false);
		
		int panelsX = (frameSize/(panelSize+1));
		int panelsY = (frameSize/(panelSize+1));
		
		
		pp = new ClickPanel[panelsX][panelsY];
		
		for(int i = 0; i < panelsX; i++) {
			for(int j = 0; j < panelsY; j++) {
	    		pp[i][j] = new ClickPanel(2, i, j, panelSize);
	    		add(pp[i][j]);
			}
		}
	}
	
	public void togShowTF() {
		if(this.isVisible()) {
			setVisible(false);
		} else {
			setVisible(true);
		}
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

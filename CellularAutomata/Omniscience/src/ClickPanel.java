import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;

public class ClickPanel extends JPanel implements MouseListener{

	int val;
	int valMax;
	
	public ClickPanel(int valM, int i, int j, int panelSize) {
		setOpaque(true);
    	setBackground(new Color(170,205,170));
    	setVisible(true);
    	setSize(panelSize, panelSize);
    	setLocation(3+(i*panelSize) + (i), 3+(j*panelSize) + (j));
    	addMouseListener(this);
    	
    	valMax = valM;
    	val = 0;
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		//System.out.println("Click!");
		/*if(val%valMax == 1){ setBackground(Color.LIGHT_GRAY); }
		if(val%valMax == 0){ setBackground(Color.black); }*/
		
		if(e.getButton() == MouseEvent.BUTTON1) {val = (val+1)%valMax;}
		if(e.getButton() == MouseEvent.BUTTON2) {val = 0;}
		if(e.getButton() == MouseEvent.BUTTON3) {val = (val-1)%valMax;}
		
		setBackground(setColor());
	}

	public void setVal(int sval) {
		/*if(sval%valMax == 1){ setBackground(Color.black); }
		if(sval%valMax == 0){ setBackground(Color.LIGHT_GRAY); }*/
		val = sval;
		setBackground(setColor());
	}

	private Color setColor(){
		
		double d = ((1-Math.abs(( (double) val / (double) valMax )))*127)+127;
		
		int de = (int) d;
		
		int rr = 0;
		int gg = 0;
		int bb = 0;
		
		if(val == 0) 	{rr=170;gg=205;	bb=170;}
		if(val == 1) 	{rr=0;	gg=190;	bb=255;}
		if(val > 1) 	{rr=de;	gg=de;	bb=de;}
		
		if(val < 0) 	{
			de =(int) (((1-Math.abs(( (double) val / (double) valMax )))*191));
			rr=255;gg=255-de;	bb=191-de;
		}
		
		if(Math.abs(val) > valMax) {rr=0;gg=0;bb=0;}
		
		Color c = new Color(rr, gg, bb);
		
		System.out.println("D:" + d + " Val:" + val + " VM:" + valMax);
		
		return c;
	}
	
	
	
	
	@Override
	public void mouseReleased(MouseEvent arg0) {}
	@Override
	public void mouseClicked(MouseEvent arg0) {}
	@Override
	public void mouseEntered(MouseEvent arg0) {}
	@Override
	public void mouseExited(MouseEvent arg0) {}
}

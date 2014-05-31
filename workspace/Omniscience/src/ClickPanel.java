import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;

public class ClickPanel extends JPanel implements MouseListener{

	int val;
	int valMax;
	
	public ClickPanel(int valM, int i, int j, int panelSize) {
		setOpaque(true);
    	setBackground(Color.LIGHT_GRAY);
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
		if(val%valMax == 1){ setBackground(Color.LIGHT_GRAY); }
		if(val%valMax == 0){ setBackground(Color.black); }
		val = (val+1)%valMax;
	}

	public void setVal(int sval) {
		if(sval%valMax == 1){ setBackground(Color.black); }
		if(sval%valMax == 0){ setBackground(Color.LIGHT_GRAY); }
		val = sval;
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

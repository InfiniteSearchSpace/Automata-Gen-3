import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;

public class ClickPanel extends JPanel implements MouseListener{

	int val;
	int valMax;
	
	public ClickPanel(int valM, int i, int j, int panelSize) {
		setOpaque(true);
    	setBackground(Color.white);
    	setVisible(true);
    	setSize(panelSize, panelSize);
    	setLocation((i*panelSize) + (2*i), (j*panelSize) + (2*j));
    	addMouseListener(this);
    	
    	valMax = valM;
    	val = 0;
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		//System.out.println("Click!");
		if(val%valMax == 1){ setBackground(Color.white); }
		if(val%valMax == 0){ setBackground(Color.black); }
		val = (val+1)%valMax;
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

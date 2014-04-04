import java.awt.event.MouseWheelListener;
import java.awt.Point;
import java.awt.event.MouseWheelEvent;

import javax.swing.JLabel;

public class mwl implements MouseWheelListener{

	//create containers for reference objects
	Main m;
	Surf[] s;
	JLabel l;
	Point p;
	Point p2;
	ml hisMouseListener;
	
	//constructor
    public mwl(Main mm, ml hisML) {
    	System.out.println("mwl");
    	m=mm;
    	hisMouseListener=hisML;
    	
        m.addMouseWheelListener(this);
    }

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		// TODO Auto-generated method stub
		//System.out.println(e);
		hisMouseListener.mouseWheelScrolled(e.getWheelRotation());
	}
    
    
}
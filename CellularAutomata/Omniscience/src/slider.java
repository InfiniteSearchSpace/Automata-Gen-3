
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class slider implements ChangeListener {
	
	ml myml;
	int slideNum;
	JSlider mySlide;
	JLabel l;
	Main m;
	int xs;
	int ys;
	int zs;
	
	int[] xx;
	int[] yy;
	
	public slider(Main mm, int xSize, int ySize, int zSize, ml mymml, int sslideNum, int maxVal) {
	    m=mm;;
		myml = mymml;
	    slideNum=sslideNum;
	    xs=xSize;
		ys=ySize-64;
		zs=zSize;

		xx = new int[2];
		yy = new int[2];
		
		xx[0] = xs+140+((slideNum*4)+8);
		xx[1] = 2;
		
		yy[0] = 4;
		yy[1] = xs+16;
		
	    init(maxVal);
	    
	  /*  l=new JLabel();
	    l.setOpaque(true);
	    l.setBackground(Color.black);
	    l.setVisible(true);
	    l.setBounds(xx[0], yy[0], xx[1], yy[1]);
	    
	    m.add(l);*/
	    
	 }

	public void stateChanged(ChangeEvent e) {
		JSlider source = (JSlider)e.getSource();

	    int sldVal = (int)source.getValue();
	    myml.slideVal[slideNum] = sldVal;
	    myml.setRuleGDThresh(slideNum);
	}
	
	/*public void setState(int num) {
		int sldVal = num;
	    myml.slideVal[slideNum] = sldVal;
	    myml.setRuleGDThresh(slideNum);
	}*/

	
	public void setMax(int val) {
		m.remove(mySlide);
		mySlide = new JSlider(JSlider.VERTICAL, 0, val, 0);  
		mySlide.setVisible(true);
	    mySlide.setLayout(null); 
	    mySlide.setBounds(xs+105+(slideNum*28), 4, 14, ys);
	    mySlide.setVisible(true);
	    mySlide.setMinorTickSpacing(1);
	    mySlide.setPaintTicks(true);
	    mySlide.addChangeListener(this);
		m.add(mySlide);
	}
	
	public void init(int val) {
		mySlide = new JSlider(JSlider.VERTICAL, 0, val, 0);  
		mySlide.setVisible(true);
	    mySlide.setLayout(null); 
	    mySlide.setBounds(xs+105+(slideNum*28), 4, 14, ys);
	    mySlide.setVisible(true);
	    mySlide.setMinorTickSpacing(1);
	    mySlide.setPaintTicks(true);
	    mySlide.addChangeListener(this);
		m.add(mySlide);
	}
	
	public void existential(boolean b){
		mySlide.setEnabled(b);
		mySlide.setVisible(b);
	}
	
	public void setmajortick(boolean b){
		mySlide.setEnabled(b);
		mySlide.setVisible(b);
	}
	
}

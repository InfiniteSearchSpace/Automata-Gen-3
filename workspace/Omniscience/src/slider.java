
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class slider implements ChangeListener {
	
	ml myml;
	int slideNum;
	JSlider mySlide;
	Main m;
	int xs;
	int ys;
	int zs;
	public slider(Main mm, int xSize, int ySize, int zSize, ml mymml, int sslideNum, int maxVal) {
	    m=mm;;
		myml = mymml;
	    slideNum=sslideNum;
	    xs=xSize;
		ys=ySize;
		zs=zSize;
		
	    init(maxVal);
	 }

	public void stateChanged(ChangeEvent e) {
		JSlider source = (JSlider)e.getSource();

	    int sldVal = (int)source.getValue();
	    myml.slideVal[slideNum] = sldVal;
	    myml.setRuleGDThresh(slideNum);

	}

	public void setMax(int val) {
		m.remove(mySlide);
		mySlide = new JSlider(JSlider.VERTICAL, 0, val, 0);  
		mySlide.setVisible(true);
	    mySlide.setLayout(null); 
	    mySlide.setBounds(xs+105+(slideNum*28), 4, 14, xs+16);
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
	    mySlide.setBounds(xs+105+(slideNum*28), 4, 14, xs+16);
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
	
}

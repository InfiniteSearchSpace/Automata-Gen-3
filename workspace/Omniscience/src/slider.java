
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class slider implements ChangeListener {
	
	ml myml;
	int slideNum;
	int maxVal;
	public slider(Main m, int xSize, int ySize, int zSize, ml mymml, int sslideNum, int mmaxVal) {
	    myml = mymml;
	    slideNum=sslideNum;
		maxVal=mmaxVal;
		
		JSlider mySlide = new JSlider(JSlider.VERTICAL, 0, mmaxVal, 0);
	    mySlide.setVisible(true);
	    mySlide.setLayout(null); 
	    mySlide.setBounds(xSize+105+(slideNum*28), 4, 14, ySize+16);
	    mySlide.setVisible(true);
	    mySlide.setMajorTickSpacing(5);
	    mySlide.setMinorTickSpacing(1);
	    mySlide.setPaintTicks(true);
	        
	    mySlide.addChangeListener(this);

	    m.add(mySlide);
	 }

	public void stateChanged(ChangeEvent e) {
		JSlider source = (JSlider)e.getSource();

	    int sldVal = (int)source.getValue();
	    myml.slideVal[slideNum] = sldVal;
	    myml.setRuleGDThresh(slideNum);

	}

	

}

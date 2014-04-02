

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class ml extends JPanel implements MouseListener {

	//create containers for reference objects
	Main m;
	Surf[] s;
	JLabel l;
	Point p;
	Point p2;
	
	int sfcnum = 0;			//index of current active/interactable surface
	int sfcmax;				//total number of surfaces to cycle through
	int myFunction = 4;  	//reference for current mouse function on click
	int functionType = 5;	//Determines subclass of functions to execute, like a menu.
	
	int fcnt = myFunction;			//total number of mouse functions to iterate through
	int fnctype = functionType;		//
	
	//constructor
    public ml(Main mm, Surf[] ss, JLabel ll) {
    	System.out.println("ml");
    	m=mm;
    	s=ss;
    	l=ll;
    	
        m.addMouseListener(this);
        
        sfcmax=s.length;
        functionType = 0;
    }
    
    //do I need this?
    void eventOutput(String eventDescription, MouseEvent e) {
        System.out.println(eventDescription + " detected on "
                + e.getComponent().getClass().getName()
                + ".");
    }
    
    public void mousePressed(MouseEvent e) {
    	
    	//get locational information from mouse & jframes
    	p = MouseInfo.getPointerInfo().getLocation();
    	p2 = m.getLocation();
    	int mx =(p.x - p2.x - 2 - s[sfcnum].getX());
    	int my =(p.y - p2.y - 24 - s[sfcnum].getY());
    	
    	
    	if (myFunction == 0 && e.getButton() == MouseEvent.BUTTON1) { 
			s[sfcnum].zdraw++;
			s[sfcnum].zdraw = s[sfcnum].zdraw % s[sfcnum].zz;
		}
    	
    	
    	
    	if(functionType == 0) {
    		if (myFunction ==  1 && e.getButton() == MouseEvent.BUTTON1) { //Place One
        		s[sfcnum].u.a.seed(mx, my, s[sfcnum].zdraw, 1, 1);
        	}
    		
    		if (myFunction ==  2 && e.getButton() == MouseEvent.BUTTON1) { //Place Block Small
    	    	int num = 4;
    			for(int i = 0; i < num*2; i++){
    				s[sfcnum].u.a.placeLine(mx-num, (my+i)-num, s[sfcnum].zdraw, 1, mx+num, false, true, 1, 1);
    			}
        	}
    		
    		if (myFunction ==  3 && e.getButton() == MouseEvent.BUTTON1) { //Place Block Big
    	    	int num = 12;
    			for(int i = 0; i < num*2; i++){
    				s[sfcnum].u.a.placeLine(mx-num, (my+i)-num, s[sfcnum].zdraw, 1, mx+num, false, true, 1, 1);
    			}
        	}
    	}

    	if(functionType == 1) {
    		if (myFunction ==  1 && e.getButton() == MouseEvent.BUTTON1) { //chance block Light			
    			int num = 16;
    			for(int i = 0; i < num*2; i++){
    	    		s[sfcnum].u.a.placeLine(mx-num, (my+i)-num, s[sfcnum].zdraw, 64, mx+num, false, true, 1, 1);
    	    	}
    		}
    		
    		if (myFunction ==  2 && e.getButton() == MouseEvent.BUTTON1) { //chance block Heavy
    			int num = 16;
    			for(int i = 0; i < num*2; i++){
    	    		s[sfcnum].u.a.placeLine(mx-num, (my+i)-num, s[sfcnum].zdraw, 3, mx+num, false, true, 1, 1);
    	    	}
    		}
    		
    		if (myFunction ==  3 && e.getButton() == MouseEvent.BUTTON1) { //Place Block Big
    	    	int num = 24;
    			for(int i = 0; i < num*2; i+=3){
    				s[sfcnum].u.a.placeLine(mx-num, (my+i)-num, s[sfcnum].zdraw, 1, mx+num, false, true, 1, 1);
    			}
        	}
    	}
    	
    	if(functionType == 2) {
    		if (myFunction == 1 && e.getButton() == MouseEvent.BUTTON1) { //delete one
    			s[sfcnum].u.a.placeO(mx, my, s[sfcnum].zdraw);
    		}
    		
    		if (myFunction ==  2 && e.getButton() == MouseEvent.BUTTON1) { //chance delete
    			int num = 12;
    			for(int i = 0; i < num*2; i++){
    		    	s[sfcnum].u.a.placeLineO(mx-num, (my+i)-num, s[sfcnum].zdraw, 4, mx+num, false, true, 1);
    		    }
    		}
    		
    		if (myFunction ==  3 && e.getButton() == MouseEvent.BUTTON1) { //hard delete
    			int num = 6;
    			for(int i = 0; i < num*2; i++){
    	    		s[sfcnum].u.a.placeLineO(mx-num, (my+i)-num, s[sfcnum].zdraw, 1, mx+num, false, true, 1);
    	    	}
    		}
    	}

		if(functionType == 3) {
			if (myFunction ==  1 && e.getButton() == MouseEvent.BUTTON1) { //chance block	
				int num = 16;
				for(int i = 0; i < num*2; i++){
					s[sfcnum].u.a.placeLine(mx-num, (my+i)-num, s[sfcnum].zdraw, 3, mx+num, false, true, 1, 30);
				}
			}
			
			if (myFunction ==  2 && e.getButton() == MouseEvent.BUTTON1) { //chance block	
				int num = 3;
				for(int i = 0; i < num*2; i++){
					s[sfcnum].u.a.placeLine(mx-num, (my+i)-num, s[sfcnum].zdraw, 1, mx+num, false, true, 1, 30);
				}
			}
		}
	
		if(functionType == 4) {
			if (myFunction ==  1 && e.getButton() == MouseEvent.BUTTON1) { //clear
				sfcnum++;
				sfcnum = sfcnum % sfcmax;
			}
			
			if (myFunction ==  2 && e.getButton() == MouseEvent.BUTTON1) { //clear
				s[sfcnum].u.resetArZ(0,s[sfcnum].zdraw);
			}
			
			if (myFunction ==  3 && e.getButton() == MouseEvent.BUTTON1) { //clear
				if(s[sfcnum].upaused == true) {
					s[sfcnum].upaused = false;
				}else{
					s[sfcnum].upaused = true;
				}
			}
		}
		
		
		
		
		
		
		
		if (myFunction == fcnt && e.getButton() == MouseEvent.BUTTON1) { //MENU
			functionType++;
    		functionType=functionType%fnctype;
    		myFunction=fcnt;
		}
		
		if (myFunction ==  99 && e.getButton() == MouseEvent.BUTTON1) {
			//print a square out as an integer array
			//Read an integer array onto board
			//additional menus
		}

    	
    	//Right Click - Pause controller
    	if(e.getButton() == MouseEvent.BUTTON3) { 
    		if(s[sfcnum].paused ) { s[sfcnum].paused = false; } else { s[sfcnum].paused = true; }
    		if(s[sfcnum].upaused ) { s[sfcnum].upaused = false; } else { s[sfcnum].upaused = true; }
    		//System.out.println("S Pause Toggled");
    		//updateListing();
    		//s[sfcnum].repaint();
    	}
    	
    	//Middle Click - Scroll through Functions
    	if(e.getButton() == MouseEvent.BUTTON2) { 
    		
    		if(myFunction == fcnt-1){
    			myFunction = fcnt;
        	} else if(myFunction < fcnt) { 
    			myFunction++;
    			myFunction=myFunction%fcnt; 
    		} else {myFunction = 0;}
    		
	    	
    		
    	}
    	
    	updateListing();
    	s[sfcnum].repaint();
    	
    	if(s[sfcnum].upaused) {
    		if(s[sfcnum].paused) { 
    			s[sfcnum].paused = false; 
	    		//s[sfcnum].repaint();
	    		//if(!s[sfcnum].paused) { 
	    		//	s[sfcnum].paused = true; 
	    		//}
    		} 
    	} /*else {
    		if(s[sfcnum].paused = false) { 
    			s[sfcnum].paused = true; 
    		}
    	}     /**/
    	//if(s[0].u.a.getUni() != null) {System.out.println(s[0].u.a.getUni().toString().substring(12,15));}
    	 
    }
    
    //identifies the mousefunction action that is highlighted
    public void updateListing(){
    	String tmps = "";
    	
    	for(int i = 0; i < s.length; i++) {
    		if(s[i].paused == true) {tmps+="1";} else {tmps+="0";}
    		if(s[i].upaused == true) {tmps+="1";} else {tmps+="0";}
    	}
    	
    	//if(s[0].u.a.getUni() != null) {l.setText(sfcnum + "." + s[sfcnum].zdraw + "."+ functionType + "." + myFunction + "." + tmps + " " + s[0].u.a.getUni().toString().substring(12,15));} else {
    		l.setText(sfcnum + "." + s[sfcnum].zdraw + "."+ functionType + "." + myFunction + "." + tmps);
    	//}
    	
    }
    
    //implicit function storage
    ////////////////////////////////////////////
    public void mouseReleased(MouseEvent e) {
    	
    }
    
    public void mouseEntered(MouseEvent e) {

    }
    
    public void mouseExited(MouseEvent e) {

    }
    
    public void mouseClicked(MouseEvent e) {

    }
    
    
}
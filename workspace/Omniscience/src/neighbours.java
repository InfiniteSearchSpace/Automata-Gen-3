/*public class neighbours {
	int[][] NBH;
	//constructor
	public neighbours(int countNbr) {
		setVonn(1);
	}
	
	//Needs to accept both:
			//incremental patterns (moore, von, etc.) 
			//predifined patterns (user-input patterns)
	
	private void setVonn(int n) {
		int nSize = n*(n+1)*2;
		setNbrArray(nSize);

		setNBH(-1, -1, 0, 0);
		setNBH(1, -1, 0, 1);
		setNBH(-1, 1, 0, 2);
		setNBH(1, 1, 0, 3);
		
	}
	
	private void setNbrArray(int countNbr){
		NBH = new int[countNbr][3]; //initiate array with required number of neighbour slots, [x][3] is for x,y,z
	}

	//called to define a neighbour's position (x,y,z,neighbour id)
	private void setNBH(int xR, int yR, int zR, int nbr) {
		NBH[nbr][0] = xR;
		NBH[nbr][1] = yR;
		NBH[nbr][2] = zR;
	}


}*/


public class neighbours {
	int[][] NBH;
	
	//constructor
	public neighbours(int countNbr) {
	//System.out.println("neighbours");
		NBH = new int[countNbr][3]; //initiate array with required number of neighbour slots
	
	}
	//called to define a neighbour's position (x,y,z,neighbour id)
	public void setNBH(int xx, int yy, int zz, int nbr) {
		NBH[nbr][0] = xx;
		NBH[nbr][1] = yy;
		NBH[nbr][2] = zz;
	}
	
	
	
	public void newNbrhood(int countNbr) {
		NBH = new int[countNbr][3]; //initiate array with required number of neighbour slots
	}
	
	public void setNbrhood(int hood){
		
		if(hood == 0) {
			newNbrhood(4);
			/*setNBH(0, -1, 0, 0);
			setNBH(-1, 0, 0, 1);
			setNBH(0, 1, 0, 2);
			setNBH(1, 0, 0, 3);*/
			setNBH(0, -1, 0, 0);
			setNBH(1, 0, 0, 1);
			setNBH(0, 1, 0, 2);
			setNBH(-1, 0, 0, 3);
		
		}
		
		if(hood == 1) {
			newNbrhood(3);
			setNBH(-1, -1, 0, 0);
			setNBH(1, -1, 0, 1);
			setNBH(0, 1, 0, 2);
		}
		
		if(hood == 2) {
			newNbrhood(5);
			setNBH(-1, 1, 0, 0);
			setNBH(1, 1, 0, 1);
			setNBH(0, -1, 0, 2);
			setNBH(-2, -1, 0, 3);
			setNBH(2, -1, 0, 4);
		}
		
		
		if(hood == 3) {
			newNbrhood(24);
			setNBH( 0, -1, 0, 0);
			setNBH( -1, 0, 0, 1);
			setNBH( 1, 0, 0, 2);
			setNBH( 0, 1, 0, 3);
			setNBH( -1, -1, 0, 4);
			setNBH( 1, 1, 0, 5);
			setNBH( -1, 1, 0, 6);
			setNBH( 1, -1, 0, 7);
			
			setNBH( -2, 0, 0, 8);
			setNBH( 2, 0, 0, 9);
			setNBH( -2, 1, 0, 10);
			setNBH( 2, 1, 0, 11);
			setNBH( -2, -1, 0, 12);
			setNBH( 2, -1, 0, 13);
			
			setNBH( 0, -2, 0, 14);
			setNBH( 0, 2, 0, 15);
			setNBH( 1, -2, 0, 16);
			setNBH( 1, 2, 0, 17);
			setNBH( -1, -2, 0, 18);
			setNBH( -1, 2, 0, 19);
			
			setNBH( -2, -2, 0, 20);
			setNBH( 2, 2, 0, 21);
			setNBH( 2, -2, 0, 22);
			setNBH( -2, 2, 0, 23);
		}
		
		if(hood == 4) {
			newNbrhood(8);
			setNBH( 0, -1, 0, 0);
			setNBH( 1, -1, 0, 1);
			setNBH( 1, 0, 0, 2);
			setNBH( 1, 1, 0, 3);
			setNBH( 0, 1, 0, 4);
			setNBH( -1, 1, 0, 5);
			setNBH( -1, 0, 0, 6);
			setNBH( -1, -1, 0, 7);
		}
		
		
		if(hood == 5) {
			newNbrhood(4);
			setNBH(-1, -1, 0, 0);
			setNBH(1, -1, 0, 1);
			setNBH(-1, 1, 0, 2);
			setNBH(1, 1, 0, 3);
		}
		
		
		if(hood == 6) {
			newNbrhood(6);
			setNBH(-1, 0, 0, 0);
			setNBH(0, -1, 0, 1);
			setNBH(1, 0, 0, 2);
			setNBH(0, 1, 0, 3);
			setNBH(0, 0, -1, 4);
			setNBH(0, 0, 1, 5);
		}
		if(hood == 7) {
			newNbrhood(3);
			setNBH(-1, -1, 0, 0);
			setNBH(1, -1, 0, 1);
			setNBH(0, -1, 0, 2);
		}
		
		
		if(hood == 8) {
			newNbrhood(5);
			setNBH(-1, -1, 0, 0);
			setNBH(1, -1, 0, 1);
			setNBH(0, -1, 0, 2);
			setNBH(-1, 0, 0, 3);
			setNBH(1, 0, 0, 4);
		}
		
		
		if(hood == 9) {
			newNbrhood(2);
			setNBH(-1, -1, 0, 0);
			setNBH(1, -1, 0, 1);
		}
		/*
		if(hood == 1) {
			newNbrhood(3);
			
		}
		/*
		if(hood == 1) {
			newNbrhood(3);
			
		}
		/*
		if(hood == 1) {
			newNbrhood(3);
			
		}
		/*
		if(hood == 1) {
			newNbrhood(3);
			
		}
		/*
		if(hood == 1) {
			newNbrhood(3);
			
		}
		
		/**/
		
	}
	
}/*
*/
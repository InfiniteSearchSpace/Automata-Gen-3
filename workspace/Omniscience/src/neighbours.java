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

}/*
*/
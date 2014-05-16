public class neighbours {

	//define list of neighbours
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
	
}
/*


n = new neighbours(int nType, int nSize, int nFract);

n.setNBH(-1, 0, 0, 0);
n.setNBH(0, -1, 0, 1);
n.setNBH(1, 0, 0, 2);
n.setNBH(0, 1, 0, 3);




*/
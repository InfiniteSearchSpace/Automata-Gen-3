import java.util.Random;


public class automataLib {
	
	Random r = new Random();
	neighbours n;
	
	dataSources d;
	Universe u;
	Main m;
	
	int[][] instructions; //holds the actions to perform
	
	public automataLib(Main mm) {
		System.out.println("automataLib");
		m = mm;

		
	}
	
	public void setTargetUni(Universe uu, dataSources dd) {
		u=uu;
		d=dd;
		//System.out.println("Target Set: " + u + " " + d);
	}
	
	public Universe getUni() {
		return u;
	}
	
	////////////////////////////////////////////
	public void test(int xx, int yy, int zz){
		n = new neighbours(3);
		n.setNBH(-1, -1, 0, 0);
		n.setNBH(1, -1, 0, 1);
		n.setNBH(0, 1, 0, 2);
		//n.setNBH(1, 1, 0, 3);
		
		if(getval(xx,yy,zz) <= 0) {
			for(int i = 0; i < n.NBH.length; i++) {
				if(u.snapshotUniverse[getWrap(xx, n.NBH[i][0], u.universe.length)][getWrap(yy, n.NBH[i][1], u.universe[0].length)][getWrap(zz, n.NBH[i][2], u.universe[0][0].length)] == 1) {
					
					u.universe[xx][yy][zz]++;
					u.universe[getWrap(xx, n.NBH[i][0], u.universe.length)][getWrap(yy, n.NBH[i][1], u.universe[0].length)][getWrap(zz, n.NBH[i][2], u.universe[0][0].length)]--;
				}
			}
		}
		
	}
	////////////////////////////////////////////
	
	
	
	
	public void internalAffairs(int xx, int yy, int zz){					//TEST
		n = new neighbours(4);
		n.setNBH(0, -1, 0, 0);
		n.setNBH(-1, 0, 0, 1);
		n.setNBH(0, 1, 0, 2);
		n.setNBH(1, 0, 0, 3);
		
		
		int t = totalistic(xx,yy,zz,1);

		if (t == 2){u.universe[xx][yy][zz] = 1;}
		if(t < 2 || t > 3){u.universe[xx][yy][zz] = 0;}
		
	}
	
	public void meekrochyp(int xx, int yy, int zz){					//TEST
		n = new neighbours(4);
		n.setNBH(-1, -1, 0, 0);
		n.setNBH(1, -1, 0, 1);
		n.setNBH(-1, 1, 0, 2);
		n.setNBH(1, 1, 0, 3);
		

		int t = totalistic(xx,yy,zz,1);

		if (t == 2){u.universe[xx][yy][zz] = 1;}
		if(t < 2 || t > 4){u.universe[xx][yy][zz] = 0;}
		
	}
	
	public void diamondShuffle(int xx, int yy, int zz){					//TEST
		n = new neighbours(4);
		n.setNBH(-1, -1, 0, 0);
		n.setNBH(1, -1, 0, 1);
		n.setNBH(-1, 1, 0, 2);
		n.setNBH(1, 1, 0, 3);

		int t = totalistic(xx,yy,zz,1);

		if (t == 2){u.universe[xx][yy][zz] = 1;}
		if(t < 2 || t > 3){u.universe[xx][yy][zz] = 0;}
		
	}

public void goop(int xx, int yy, int zz){					//TEST
		
		n = new neighbours(5);
		n.setNBH(-1, 1, 0, 0);
		n.setNBH(1, 1, 0, 1);
		n.setNBH(0, -1, 0, 2);
		n.setNBH(-2, -1, 0, 3);
		n.setNBH(2, -1, 0, 4);

		int t = totalistic(xx,yy,zz,1);
		
		if (t < 2 || t > 4) {
			for(int i = 0; i < 5; i++) {
				//setneighbour(xx,yy,zz,i,0)
				u.universe[xx][yy][zz] = 0;
			}
		} 
		
		if (t == 3){u.universe[xx][yy][zz] = 1;}
		
	}
	
	
	public void setneighbour(int xx, int yy, int zz, int i, int val) {
		u.universe[getWrap(xx, n.NBH[i][0], u.universe.length)][getWrap(yy, n.NBH[i][1], u.universe[0].length)][getWrap(zz, n.NBH[i][2], u.universe[0][0].length)] = val;
	}
	
	public int totalistic(int xx, int yy, int zz, int valToCount){
		int isOne = 0;

		for(int i = 0; i < n.NBH.length; i++) {
			if(u.snapshotUniverse[getWrap(xx, n.NBH[i][0], u.universe.length)][getWrap(yy, n.NBH[i][1], u.universe[0].length)][getWrap(zz, n.NBH[i][2], u.universe[0][0].length)] == valToCount) {isOne++;}
		}
		
		return isOne;
	}
	
	public int totalisticSum(int xx, int yy, int zz){
		int isOne = 0;

		for(int i = 0; i < n.NBH.length; i++) {
			isOne += u.snapshotUniverse[getWrap(xx, n.NBH[i][0], u.universe.length)][getWrap(yy, n.NBH[i][1], u.universe[0].length)][getWrap(zz, n.NBH[i][2], u.universe[0][0].length)];
		}
		
		return isOne;
	}
	
	public int totalisticLess(int xx, int yy, int zz, int valToCount){
		int isOne = 0;

		for(int i = 0; i < n.NBH.length; i++) {
			if(u.snapshotUniverse[getWrap(xx, n.NBH[i][0], u.universe.length)][getWrap(yy, n.NBH[i][1], u.universe[0].length)][getWrap(zz, n.NBH[i][2], u.universe[0][0].length)] < valToCount) {isOne++;}
		}
		
		return isOne;
	}
	
	public int totalisticMore(int xx, int yy, int zz, int valToCount){
		int isOne = 0;

		for(int i = 0; i < n.NBH.length; i++) {
			if(u.snapshotUniverse[getWrap(xx, n.NBH[i][0], u.universe.length)][getWrap(yy, n.NBH[i][1], u.universe[0].length)][getWrap(zz, n.NBH[i][2], u.universe[0][0].length)] > valToCount) {isOne++;}
		}
		
		return isOne;
	}
	
	public int totalisticNotZero(int xx, int yy, int zz){
		int isOne = 0;

		for(int i = 0; i < n.NBH.length; i++) {
			if(u.snapshotUniverse[getWrap(xx, n.NBH[i][0], u.universe.length)][getWrap(yy, n.NBH[i][1], u.universe[0].length)][getWrap(zz, n.NBH[i][2], u.universe[0][0].length)] != 0) {isOne++;}
		}
		
		return isOne;
	}
	
	
	 public int totalNotZeroAll(int zz){
		 int isOne = 0;
		 
	    for(int i = 0; i < u.universe.length; i++) {
			for (int j = 0; j < u.universe[0].length; j++) {
					if(u.snapshotUniverse[i][j][zz] != 0) {isOne++;}
			}
		}
	    
	    return isOne;
	 }
	
	
	public int checkneighbour(int xx, int yy, int zz, int iNbr){
		return u.snapshotUniverse[getWrap(xx, n.NBH[iNbr][0], u.universe.length)][getWrap(yy, n.NBH[iNbr][1], u.universe[0].length)][getWrap(zz, n.NBH[iNbr][2], u.universe[0][0].length)];
	}
	
	public void explore2(int xx, int yy, int zz){					//TEST
		n = new neighbours(4);
		n.setNBH(-7, 0, 0, 0);
		n.setNBH(0, -7, 0, 1);
		n.setNBH(7, 0, 0, 2);
		n.setNBH(0, 7, 0, 3);
		
		if(getval(xx,yy,zz) <= 0) {
			for(int i = 0; i < n.NBH.length; i++) {
				if(u.snapshotUniverse[getWrap(xx, n.NBH[i][0], u.universe.length)][getWrap(yy, n.NBH[i][1], u.universe[0].length)][getWrap(zz, n.NBH[i][2], u.universe[0][0].length)] == 1) {
					
					u.universe[xx][yy][zz]++;
					u.universe[getWrap(xx, n.NBH[i][0], u.universe.length)][getWrap(yy, n.NBH[i][1], u.universe[0].length)][getWrap(zz, n.NBH[i][2], u.universe[0][0].length)]--;
				}
			}
		}
	}

	
	public void explorer(int xx, int yy, int zz){

		n = new neighbours(4);
		n.setNBH(-1, 0, 0, 0);
		n.setNBH(0, -1, 0, 1);
		n.setNBH(1, 0, 0, 2);
		n.setNBH(0, 1, 0, 3);
		
		if(getval(xx,yy,zz) <= 0) {
			for(int i = 0; i < n.NBH.length; i++) {
				if(u.snapshotUniverse[getWrap(xx, n.NBH[i][0], u.universe.length)][getWrap(yy, n.NBH[i][1], u.universe[0].length)][getWrap(zz, n.NBH[i][2], u.universe[0][0].length)] == 1) {
					//if(r.nextInt(8) == 0){
					u.universe[xx][yy][zz]++;
					u.universe[getWrap(xx, n.NBH[i][0], u.universe.length)][getWrap(yy, n.NBH[i][1], u.universe[0].length)][getWrap(zz, n.NBH[i][2], u.universe[0][0].length)]--;
					//}
				}
			}
		}
		
		/*if(i > 0) {if(i < mySize-1) {if(j > 0) {if(j < mySize-1) { 
			if((pointsArray[i][j] < 1 )){   
				if((pointsArray[i+1][j] <= 0 ) || ( pointsArray[i-1][j]<= 0 ) || ( pointsArray[i][j+1]<= 0 && pointsArray[i][j-1]<= 0 )){   
					if((pointsArray[i+1][j] > 0 )) { pointsArray[i][j]  = 1; pointsArray[i+1][j] -= 1;  } 
					if(( pointsArray[i-1][j]> 0 )) { pointsArray[i][j]  = 1; pointsArray[i-1][j] -= 1;  } 
					if(( pointsArray[i][j+1]> 0 )) { pointsArray[i][j]  = 1; pointsArray[i][j+1] -= 1;  } 
					if(( pointsArray[i][j-1]> 0 )) { pointsArray[i][j]  = 1; pointsArray[i][j-1] -= 1;  }
					}}
			}}}}  
		}*/
    }
	

	public void quantum(int xx, int yy, int zz, int rand){

		n = new neighbours(4);
		n.setNBH(-1, 0, 0, 0);
		n.setNBH(0, -1, 0, 1);
		n.setNBH(1, 0, 0, 2);
		n.setNBH(0, 1, 0, 3);
		
		if(getval(xx,yy,zz) == 0 && r.nextInt(rand) == 0) {
			for(int i = 0; i < n.NBH.length; i++) {
				if(u.snapshotUniverse[getWrap(xx, n.NBH[i][0], u.universe.length)][getWrap(yy, n.NBH[i][1], u.universe[0].length)][getWrap(zz, n.NBH[i][2], u.universe[0][0].length)] != 0) {
					//if(r.nextInt(8) == 0){
					
					u.universe[xx][yy][zz]=u.universe[getWrap(xx, n.NBH[i][0], u.universe.length)][getWrap(yy, n.NBH[i][1], u.universe[0].length)][getWrap(zz, n.NBH[i][2], u.universe[0][0].length)];
					
					u.universe[getWrap(xx, n.NBH[i][0], u.universe.length)][getWrap(yy, n.NBH[i][1], u.universe[0].length)][getWrap(zz, n.NBH[i][2], u.universe[0][0].length)]=0;
					//}
				}
			}
		}
		
		/*if(i > 0) {if(i < mySize-1) {if(j > 0) {if(j < mySize-1) { 
			if((pointsArray[i][j] < 1 )){   
				if((pointsArray[i+1][j] <= 0 ) || ( pointsArray[i-1][j]<= 0 ) || ( pointsArray[i][j+1]<= 0 && pointsArray[i][j-1]<= 0 )){   
					if((pointsArray[i+1][j] > 0 )) { pointsArray[i][j]  = 1; pointsArray[i+1][j] -= 1;  } 
					if(( pointsArray[i-1][j]> 0 )) { pointsArray[i][j]  = 1; pointsArray[i-1][j] -= 1;  } 
					if(( pointsArray[i][j+1]> 0 )) { pointsArray[i][j]  = 1; pointsArray[i][j+1] -= 1;  } 
					if(( pointsArray[i][j-1]> 0 )) { pointsArray[i][j]  = 1; pointsArray[i][j-1] -= 1;  }
					}}
			}}}}  
		}*/
    }
	

	
	public void quantumLorE(int xx, int yy, int zz, int val, int rand){

		n = new neighbours(4);
		n.setNBH(-1, 0, 0, 0);
		n.setNBH(0, -1, 0, 1);
		n.setNBH(1, 0, 0, 2);
		n.setNBH(0, 1, 0, 3);
		
		if(getval(xx,yy,zz) == 0 && r.nextInt(rand) == 0) {
			for(int i = 0; i < n.NBH.length; i++) {
				if(u.snapshotUniverse[getWrap(xx, n.NBH[i][0], u.universe.length)][getWrap(yy, n.NBH[i][1], u.universe[0].length)][getWrap(zz, n.NBH[i][2], u.universe[0][0].length)] <= val) {
					//if(r.nextInt(8) == 0){
					
					u.universe[xx][yy][zz]=u.universe[getWrap(xx, n.NBH[i][0], u.universe.length)][getWrap(yy, n.NBH[i][1], u.universe[0].length)][getWrap(zz, n.NBH[i][2], u.universe[0][0].length)];
					
					u.universe[getWrap(xx, n.NBH[i][0], u.universe.length)][getWrap(yy, n.NBH[i][1], u.universe[0].length)][getWrap(zz, n.NBH[i][2], u.universe[0][0].length)]=0;
					//}
				}
			}
		}

    }
	
	public void quantumWeight(int xx, int yy, int zz, int rand){

		n = new neighbours(4);
		n.setNBH(-1, 0, 0, 0);
		n.setNBH(0, -1, 0, 1);
		n.setNBH(1, 0, 0, 2);
		n.setNBH(0, 1, 0, 3);
		
		if(getval(xx,yy,zz) == 0 && r.nextInt(rand) == 0) {
			for(int i = 0; i < n.NBH.length; i++) {
				
				int theVal = u.snapshotUniverse[getWrap(xx, n.NBH[i][0], u.universe.length)][getWrap(yy, n.NBH[i][1], u.universe[0].length)][getWrap(zz, n.NBH[i][2], u.universe[0][0].length)];
				
				if(u.snapshotUniverse[getWrap(xx, n.NBH[i][0], u.universe.length)][getWrap(yy, n.NBH[i][1], u.universe[0].length)][getWrap(zz, n.NBH[i][2], u.universe[0][0].length)] != 0) {
					if(r.nextInt((theVal*theVal)+1) == 0 || r.nextInt(1024) == 0){
						u.universe[xx][yy][zz]=u.universe[getWrap(xx, n.NBH[i][0], u.universe.length)][getWrap(yy, n.NBH[i][1], u.universe[0].length)][getWrap(zz, n.NBH[i][2], u.universe[0][0].length)];
						u.universe[getWrap(xx, n.NBH[i][0], u.universe.length)][getWrap(yy, n.NBH[i][1], u.universe[0].length)][getWrap(zz, n.NBH[i][2], u.universe[0][0].length)]=0;
					}
				}
				
			}
		}

    }
	
	
	public boolean ifval(int xx, int yy, int zz, int val){ //checks if a cell is a value
		
		if(u.snapshotUniverse[xx][yy][zz] == val) {
			return true;
		} else { 
			return false;
		}
		
	}
	
	public int getval(int xx, int yy, int zz){ //returns cell value
		return u.snapshotUniverse[xx][yy][zz];
	}
	
	
	
	public void writeData(int[] dd) {
		for(int i = 0; i < u.universe.length; i++) {
			for (int j = 0; j < u.universe[0].length; j++) {
				for (int k = 0; k < u.universe[0][0].length; k++) {
					u.universe[i][j][k] = d.readNext(dd);
				}
			}
    	}
	}
	
    public int getWrap(int val, int mod, int len) { //takes array co-ord, modification int, array param length, and returns wrap position.

    	//it is possible that this code is slightly misaligned. Noticed deaths occuring when passing from bottom to top
    	
    	if((val+mod) % len < 0) {return len+((val+mod) % len);} else {return (val+mod) % len;}
    	
    }
	
    public void changeNBHTEST(int rand){
    	if (r.nextInt(rand) == 0) {
    		n.setNBH(r.nextInt(32)-16, r.nextInt(32)-16, 0, 0);
    	}
    }
    
	
	public void rain(int xx, int yy, int zz){//TEST
		
		n = new neighbours(3);
		n.setNBH(-1, -1, 0, 0);
		n.setNBH(1, -1, 0, 1);
		n.setNBH(0, -1, 0, 2);
		
		int isOne = 0;

		for(int i = 0; i < n.NBH.length; i++) {
			if(u.snapshotUniverse[getWrap(xx, n.NBH[i][0], u.universe.length)][getWrap(yy, n.NBH[i][1], u.universe[0].length)][getWrap(zz, n.NBH[i][2], u.universe[0][0].length)] == 1) {isOne++;}
		}
		

		
		if(isOne == 1) {
			u.universe[xx][yy][zz] = 0;
		}// else {u.universe[xx][yy][zz] = 0;}

		if(isOne == 2) {
			u.universe[xx][yy][zz] = 1;
		}


    }
	
public void rain2(int xx, int yy, int zz){//TEST
		
		n = new neighbours(5);
		n.setNBH(-1, -1, 0, 0);
		n.setNBH(1, -1, 0, 1);
		n.setNBH(0, -1, 0, 2);
		n.setNBH(-1, 0, 0, 3);
		n.setNBH(1, 0, 0, 4);
		
		int isOne = 0;

		for(int i = 0; i < n.NBH.length; i++) {
			if(u.snapshotUniverse[getWrap(xx, n.NBH[i][0], u.universe.length)][getWrap(yy, n.NBH[i][1], u.universe[0].length)][getWrap(zz, n.NBH[i][2], u.universe[0][0].length)] == 1) {isOne++;}
		}
		

		
		if(isOne != 2) {
			u.universe[xx][yy][zz] = 0;
		}// else {u.universe[xx][yy][zz] = 0;}

		if(isOne == 2) {
			u.universe[xx][yy][zz] = 1;
		}


    }
	
	public void sierpenski(int xx, int yy, int zz){// makes a sierpenski triangle lattice that appears to 'count' linearly
		n = new neighbours(2);
		n.setNBH(-1, -1, 0, 0);
		n.setNBH(1, -1, 0, 1);
		
		int isOne = 0;

		for(int i = 0; i < n.NBH.length; i++) {
			if(u.universe[getWrap(xx, n.NBH[i][0], u.universe.length)][getWrap(yy, n.NBH[i][1], u.universe[0].length)][getWrap(zz, n.NBH[i][2], u.universe[0][0].length)] == 1) {isOne++;}
			// IF u.universe, NOT u.snapshot. Weird.
			//if(u.snapshotUniverse[getWrap(xx, n.NBH[i][0], u.universe.length)][getWrap(yy, n.NBH[i][1], u.universe[0].length)][getWrap(zz, n.NBH[i][2], u.universe[0][0].length)] == 1) {isOne++;}
			
		}
		
		if(isOne == 1) {
			u.universe[xx][yy][zz] = 1;
		} else {u.universe[xx][yy][zz] = 0;}

    }
	
	public void snapSierpenski(int xx, int yy, int zz){// makes a sierpenski triangle lattice that appears to 'count' linearly
		n = new neighbours(2);
		n.setNBH(-1, -1, 0, 0);
		n.setNBH(1, -1, 0, 1);
		
		int isOne = 0;

		for(int i = 0; i < n.NBH.length; i++) {
			//if(u.universe[getWrap(xx, n.NBH[i][0], u.universe.length)][getWrap(yy, n.NBH[i][1], u.universe[0].length)][getWrap(zz, n.NBH[i][2], u.universe[0][0].length)] == 1) {isOne++;}
			// IF u.universe, NOT u.snapshot. Weird.
			if(u.snapshotUniverse[getWrap(xx, n.NBH[i][0], u.universe.length)][getWrap(yy, n.NBH[i][1], u.universe[0].length)][getWrap(zz, n.NBH[i][2], u.universe[0][0].length)] == 1) {isOne++;}
			
		}
		
		if(isOne == 1) {
			u.universe[xx][yy][zz] = 1;
		} else {u.universe[xx][yy][zz] = 0;}

    }
	
	public void conway(int xx, int yy, int zz, int val, int checkSelf){ //demonstration of conway's game of life
		
		n = new neighbours(8);
		n.setNBH(-1, -1, 0, 0);
		n.setNBH( 0, -1, 0, 1);	
		n.setNBH( 1, -1, 0, 2);
		n.setNBH(-1, 0, 0, 3);	
		n.setNBH( 1, 0, 0, 4);
		n.setNBH(-1, 1, 0, 5);	
		n.setNBH(0, 1, 0, 6);
		n.setNBH(1, 1, 0, 7);
		
		int isOne = totalisticNotZero(xx,yy,zz);
		
		if(checkSelf == 0 || (checkSelf == 1 && u.snapshotUniverse[xx][yy][zz] == val)) {
			if(isOne < 2)  { u.universe[xx][yy][zz] = 0; }
			if(isOne > 3)  { u.universe[xx][yy][zz] = 0; }
			if(isOne == 3) { u.universe[xx][yy][zz] = 1; }
		}
		
		
		/*if (isOne > 0) {
			if(checkSelf == 1 && u.snapshotUniverse[xx][yy][zz] == val) {
				if(isOne < 2)  { u.universe[xx][yy][zz] = 0; }
				if(isOne > 3)  { u.universe[xx][yy][zz] = 0; }
				if(isOne == 3) { u.universe[xx][yy][zz] = 1; }
			} else if (checkSelf == 0) {
				if(isOne < 2)  { u.universe[xx][yy][zz] = 0; }
				if(isOne > 3)  { u.universe[xx][yy][zz] = 0; }
				if(isOne == 3) { u.universe[xx][yy][zz] = 1; }
			}
		}*/
    }
	
	public void conway2(int xx, int yy, int zz){ //demonstration of conway's game of life
		
		n = new neighbours(8);
		n.setNBH(-1, -1, 0, 0);
		n.setNBH( 0, -1, 0, 1);	
		n.setNBH( 1, -1, 0, 2);
		n.setNBH(-1, 0, 0, 3);	
		n.setNBH( 1, 0, 0, 4);
		n.setNBH(-1, 1, 0, 5);	
		n.setNBH(0, 1, 0, 6);
		n.setNBH(1, 1, 0, 7);
		
		int isOne = totalisticLess(xx,yy,zz,4);
		
		if(isOne > 16)  { u.universe[xx][yy][zz] --; }
		if(isOne < 3) { u.universe[xx][yy][zz] ++; }

    }
	
	public void pissRandomly(int xx, int yy, int zz, int halfRand, int freq) { //chance to reset to random +/- halfrand

		if(r.nextInt(freq) == 0) {
			u.universe[xx][yy][zz] = r.nextInt(halfRand*2)-(halfRand);
			if(u.snapshotUniverse[xx][yy][zz] > u.maxVal) {u.maxVal = u.universe[xx][yy][zz];}
		}

    }
	
	public void pissPositive(int xx, int yy, int zz, int rand, int freq) { //chance to reset to something random, but positive

		if(r.nextInt(freq) == 0) {
			u.universe[xx][yy][zz] = r.nextInt(rand);
			if(u.snapshotUniverse[xx][yy][zz] > u.maxVal) {u.maxVal = u.universe[xx][yy][zz];}
		}

    }
    
    public void placeOne(int xx, int yy, int zz) { //set to 1
    	u.universe[xx][yy][zz] = 1;
    }
    
    public void placeval(int xx, int yy, int zz, int v) { //set to 1
    	u.universe[xx][yy][zz] = v;
    	//System.out.println("Val Placed: " + v + " @ " + xx + "." + yy + "." + zz);
    }
    
    public void placeO(int xx, int yy, int zz) { //set to 1
    	u.universe[xx][yy][zz] = 0;
    }
    
    public void rollO(int xx, int yy, int zz, int rand) { //set to 1
    	if(r.nextInt(rand) == 0) {
    		u.universe[xx][yy][zz] = 0;
    	}
    }
	
    public void transcribeBinaryData(int xx, int yy, int zz, boolean showO) {
    	if(!showO){	if(d.readNext(d.sources[0]) == 1) {u.universe[xx][yy][zz] = 1;} } else {
    		u.universe[xx][yy][zz] = d.readNext(d.sources[0]);
    	}
    }
    
    public void placeLine(int xx, int yy, int zz, int rand, int len, boolean placeO, boolean horiz, int veto, int val) { //draws a line with chance. Doesn't support z
    	
    	if(r.nextInt(veto) == 0) {
	    	if(horiz) {
		    	for(int i = 0; i < len-xx; i++) {
		    		if(r.nextInt(rand) == 0) {
		    			placeval(xx+i, yy, zz, val);
		    		} else if(placeO) {placeO(xx+i, yy, zz);}
		    	}
		    } else {
		    	for(int i = 0; i < len-yy; i++) {
		    		if(r.nextInt(rand) == 0) {
		    			placeval(xx+i, yy, zz, val);
		    		} else if(placeO) {placeO(xx, yy+i, zz);}
		    	}
	    	}
    	}
    }
    
public void placeLineO(int xx, int yy, int zz, int rand, int len, boolean placeO, boolean horiz, int veto) { //draws a line with chance. Doesn't support z
    	
    	if(r.nextInt(veto) == 0) {
	    	if(horiz) {
		    	for(int i = 0; i < len-xx; i++) {
		    		if(r.nextInt(rand) == 0) {
		    			placeO(xx+i, yy, zz);
		    		} else if(placeO) {placeOne(xx+i, yy, zz);}
		    	}
		    } else {
		    	for(int i = 0; i < len-yy; i++) {
		    		if(r.nextInt(rand) == 0) {
		    			placeO(xx, yy+i, zz);
		    		} else if(placeO) {placeOne(xx, yy+i, zz);}
		    	}
	    	}
    	}
    }
    
    public void seed(int xx, int yy, int zz, int rand, int val){ //chance to seed location
    	if(r.nextInt(rand) == 0) {placeval(xx, yy, zz, val);}
    	//System.out.println("Seeded");
    }
    
    public void startSierpenski(int xx, int yy) {
    	seed(xx, yy, 0, 1, 1);
    	seed(xx+2, yy, 0, 1, 1);
    }
    
    public void seedAll(int rand, int val){ //chance to seed every pixel
    	for(int i = 0; i < u.universe.length; i++) {
			for (int j = 0; j < u.universe[0].length; j++) {
				for (int k = 0; k < u.universe[0][0].length; k++) {
			    	seed(i,j,k,rand,val);
				}
			}
    	}
    }
    
    public void seedAllRespect(int rand, int val, int seedz){ //chance to seed every pixel
    	for(int i = 0; i < u.universe.length; i++) {
			for (int j = 0; j < u.universe[0].length; j++) {
				
				if(seedz == -1){
					for (int k = 0; k < u.universe[0][0].length; k++) {
				    	if(u.snapshotUniverse[i][j][k] == 0) {
				    		seed(i,j,k,rand,val);
				    	}
				    	
					}
				} else {
					if(u.snapshotUniverse[i][j][seedz] == 0) {
			    		seed(i,j,seedz,rand,val);
			    	}
				}
				
			}
    	}
    }
    
    public void seedAllO(int rand, int vetoVal, int zz){ //chance to seed every pixel
    	for(int i = 0; i < u.universe.length; i++) {
			for (int j = 0; j < u.universe[0].length; j++) {
				for (int k = 0; k < zz+1; k++) {
					if(u.snapshotUniverse[i][j][k] != vetoVal) {
						rollO(i,j,k,rand);
					}
				}
			}
    	}
    }
    
    public void seedZ(int rand, int zz){ //chance to seed every pixel
    	for(int i = 0; i < u.universe.length; i++) {
			for (int j = 0; j < u.universe[0].length; j++) {
			    seed(i,j,zz,rand, 1);
			}
    	}
    }

    public void doZ(int i, int j, int k) {
		if(u.snapshotUniverse[i][j][1] == 1/* && r.nextInt(4)==0*/) {
			u.universe[i][j][0]=1;
		}// else if(u.universe[i][j][0] > 0 && u.snapshotUniverse[i][j][1] == 0){u.universe[i][j][0]--;};

		explorer(i,j,k);
		//conway(i, j, k);

	}
    
    public void doZ3(int i, int j, int k) {

		if(u.snapshotUniverse[i][j][0] == 1) {
			u.universe[i][j][2]=1;
		} 
		
		if(u.snapshotUniverse[i][j][0] < 0) {
			u.universe[i][j][2]=0;
		}
		
		if(u.snapshotUniverse[i][j][1] == 1) {
			u.universe[i][j][2]=0;
		} 
		
				
		//rain(i,j,k);
		//explorer(i,j,k);
		//meekrochyp(i, j, k);
		//conway(i,j,k);
		//explorer(i,j,k);
		//diamondShuffle(i, j, k);
    }
    
    public void doZ4(int i, int j, int k) {

		if(u.snapshotUniverse[i][j][2] == 1) {
			u.universe[i][j][3]+=2;
		} 
		
		if(u.universe[i][j][3] > 0) {u.universe[i][j][3]--;}

    }
    
    public void doZ5(int i, int j, int k) {

  		if(u.snapshotUniverse[i][j][3] > 3 && r.nextInt(8) == 0) {
  			u.universe[i][j][4]++;
  		} 
  		
  		if(u.snapshotUniverse[i][j][1] == 1 && r.nextInt(8) == 0) {
  			u.universe[i][j][4]=0;
  		} 
  		
  		if(u.snapshotUniverse[i][j][0] == 1 & r.nextInt(8) == 0) {
  			u.universe[i][j][4]=0;
  		} 
  		
  		/*if(u.universe[i][j][3] > 0) {u.universe[i][j][3]--;}
  		
  		if(u.universe[i][j][3] > 32 && r.nextInt(256) == 0) {u.universe[i][j][1]=1;}
*/
      }
    
	public void doZ2(int i, int j, int k) {
		if(u.snapshotUniverse[i][j][1] == 1) {
			u.universe[i][j][0]++;
		} else if(u.universe[i][j][0] > 0 && u.snapshotUniverse[i][j][1] == 0){u.universe[i][j][0]--;};
		
		//conway2(i, j, k);
		explorer(i,j,k);
		
		if(u.universe[i][j][0] == 13) {
			u.universe[i][j][1] = 1;
		}
	}
    
	public void dozExclude(int i, int j, int k, int val, int z, int rand){
		if(u.snapshotUniverse[i][j][z] == val) {
			if(u.snapshotUniverse[i][j][z] == u.snapshotUniverse[i][j][k]){
				if(r.nextInt(rand) == 0){
					u.universe[i][j][k] = 0;
					//u.universe[i][j][z] = 0;
				}
			}
		}
	}
	
	public void dozDelIfVal(int i, int j, int k, int val, int z){
		if(u.snapshotUniverse[i][j][z] == val && u.snapshotUniverse[i][j][k] != 0) {
			u.universe[i][j][k] = 0;
			u.universe[i][j][z] = 0;
		}
	}
	
	public void dozDelMeIfVal(int i, int j, int k, int val, int z){
		if(u.snapshotUniverse[i][j][z] == val && u.snapshotUniverse[i][j][k] != 0) {
			u.universe[i][j][k] = 0;
		}
	}
	
	public void dozDelIfNotVal(int i, int j, int k, int val, int z){
		if(u.snapshotUniverse[i][j][z] == val && u.snapshotUniverse[i][j][k] != val) {
			u.universe[i][j][k] = 0;
		}
	}
	
	public void dozDelMeIfNotVal(int i, int j, int k, int val, int z, int rand){
		if(u.snapshotUniverse[i][j][z] != val && r.nextInt(rand) == 0) {
			u.universe[i][j][k] = 0;
		}
	}
	
	public void dozDelMeIfNotLVal(int i, int j, int k, int val, int z, int rand){
		if(u.snapshotUniverse[i][j][z] > val && r.nextInt(rand) == 0) {
			u.universe[i][j][k] = 0;
		}
	}
	
	public void doz021(int i, int j, int k, int z){
		if(u.snapshotUniverse[i][j][z] == 0) {
			u.universe[i][j][k] = 0;
		} else {u.universe[i][j][k]++;}
	}
	
	public void dozIfVal(int i, int j, int k, int val, int z, int newval, int rand) {
		if(r.nextInt(rand) == 0){
			if(u.snapshotUniverse[i][j][z] == val) {
				u.universe[i][j][k] = newval;
			}
		}
	}
	
	public void dozIncrementIfVal(int i, int j, int k, int val, int z, int newval, int rand) {
		if(r.nextInt(rand) == 0){
			if(u.snapshotUniverse[i][j][z] == val) {
				u.universe[i][j][k] += newval;
			}
		}
	}
	
	public void dozUnIncrementIfVal(int i, int j, int k, int val, int z, int newval, int rand) {
		if(r.nextInt(rand) == 0){
			if(u.snapshotUniverse[i][j][z] == 0 && u.snapshotUniverse[i][j][k] > 0) {
				u.universe[i][j][k] -= newval;
			}
		}
	}
	
	public void dozAddIfVal(int i, int j, int k, int val, int z) {
		if(u.snapshotUniverse[i][j][z] == val) {
			u.universe[i][j][k]++;
		} else {if(u.snapshotUniverse[i][j][k] > 0){u.universe[i][j][k]--;}}
	}
	
	public void dozwipe(int i, int j, int k) {
		u.universe[i][j][k] = 0;
	}
	
	public void twat(int i, int j, int k){
		n = new neighbours(r.nextInt(8)+1);
		for (int ii = 0; ii < n.NBH.length; ii++) {
			n.setNBH(r.nextInt(3)-1, r.nextInt(3)-1, 0, ii);
		}
		
		//check self
		//if(u.snapshotUniverse[i][j][k] == r.nextInt(33)-16) {
		if(u.snapshotUniverse[i][j][k] <= 1 ){
			//check neighbours
			int num = r.nextInt(33)-16;
			if(num != 0 && totalistic(i, j, k, num) > 1 && totalistic(i, j, k, num) <= 3) {
				//action
				if(r.nextInt(6) <= 4) {u.universe[i][j][k] = num;} else {u.universe[i][j][k] += r.nextInt(9)-4;}
			} 

			if(totalisticSum(i, j, k)*totalisticSum(i, j, k) < (totalistic(i, j, k, num)*num*num)  || totalistic(i, j, k, num) >= 7) {
				//action
				u.universe[i][j][k] = 0;
			}
		}
		//}
			
		
		
		
	/*
		int isOne = totalisticNotZero(xx,yy,zz);
		
		
		if(isOne > 0 || (checkSelf == 1 && u.snapshotUniverse[xx][yy][zz] + isOne == val)) {
		if(isOne < 2)  { u.universe[xx][yy][zz] = 0; }
		if(isOne > 3)  { u.universe[xx][yy][zz] = 0; }
		if(isOne == 3) { u.universe[xx][yy][zz] = 1; }*/
	}
	
	public void PointToCircle(int i, int j, int k, int val){
		/*n = new neighbours(r.nextInt(8)+1);
		for (int ii = 0; ii < n.NBH.length; ii++) {
			n.setNBH(r.nextInt(3)-1, r.nextInt(3)-1, 0, ii);
		}*/
		n = new neighbours(5);
		n.setNBH(0, -1, 0, 0);
		n.setNBH( 0, 1, 0, 1);	
		n.setNBH( -1, 0, 0, 2);
		n.setNBH( 1, 0, 0, 3);
		n.setNBH( 0, 0, 0, 4);

		
		//check self
		//if(u.snapshotUniverse[i][j][k] == r.nextInt(33)-16) {
		int isOne = totalisticNotZero(i,j,k);
		
		//if(u.snapshotUniverse[i][j][k] == val) {
			if(isOne <= 3)  { u.universe[i][j][k] = 0; }
			if(isOne >= 5)  { u.universe[i][j][k] = 0; }
			if(isOne == 2 || isOne == 1)  { u.universe[i][j][k] = 1; }
		//}
		//}
			
		
		
		
	/*
		int isOne = totalisticNotZero(xx,yy,zz);
		
		
		if(isOne > 0 || (checkSelf == 1 && u.snapshotUniverse[xx][yy][zz] + isOne == val)) {
		if(isOne < 2)  { u.universe[xx][yy][zz] = 0; }
		if(isOne > 3)  { u.universe[xx][yy][zz] = 0; }
		if(isOne == 3) { u.universe[xx][yy][zz] = 1; }*/
	}
	
	public void Conway11(int i, int j, int k, int val){

		n = new neighbours(11);
		n.setNBH( -1, -1, 0, 0);
		n.setNBH( 0, -1, 0, 1);	
		n.setNBH( 1, -1, 0, 2);
		
		n.setNBH( -1, 0, 0, 3);
		n.setNBH( 0, 0, 0, 4);
		n.setNBH( 1, 0, 0, 5);
		
		n.setNBH( -1, 1, 0, 6);
		n.setNBH( 0, 1, 0, 7);
		n.setNBH( 1, 1, 0, 8);
		
		n.setNBH( 0, 2, 0, 9);
		n.setNBH( 0, -2, 0, 10);
		
		int isOne = totalisticNotZero(i,j,k);
		
		if(isOne <= 1)  { u.universe[i][j][k] = 0; }
		if(isOne == 3)  { u.universe[i][j][k] = 1; }
		if(isOne > 3)  { u.universe[i][j][k] = 0; }
	}
	
	public void rope(int i, int j, int k, int val){

		n = new neighbours(3);
		n.setNBH( -1, -1, 0, 0);
		n.setNBH( 0, -1, 0, 1);	
		n.setNBH( -1, 0, 0, 2);
		
		int isOne = totalisticNotZero(i,j,k);
		
		if(isOne == 1)  { u.universe[i][j][k] = 0; }
		if(isOne == 2)  { u.universe[i][j][k] = 1; }
		if(isOne > 2)  { u.universe[i][j][k] = 0; }
	}
	
	public void Conway3D(int i, int j, int k){

		n = new neighbours(24);
		
		n.setNBH( 0, -1, 0, 0);
		n.setNBH( -1, 0, 0, 1);
		n.setNBH( 1, 0, 0, 2);
		n.setNBH( 0, 1, 0, 3);
		n.setNBH( -1, -1, 0, 4);
		n.setNBH( 1, 1, 0, 5);
		n.setNBH( -1, 1, 0, 6);
		n.setNBH( 1, -1, 0, 7);
		
		n.setNBH( -2, 0, 0, 8);
		n.setNBH( 2, 0, 0, 9);
		n.setNBH( -2, 1, 0, 10);
		n.setNBH( 2, 1, 0, 11);
		n.setNBH( -2, -1, 0, 12);
		n.setNBH( 2, -1, 0, 13);
		
		n.setNBH( 0, -2, 0, 14);
		n.setNBH( 0, 2, 0, 15);
		n.setNBH( 1, -2, 0, 16);
		n.setNBH( 1, 2, 0, 17);
		n.setNBH( -1, -2, 0, 18);
		n.setNBH( -1, 2, 0, 19);
		
		n.setNBH( -2, -2, 0, 20);
		n.setNBH( 2, 2, 0, 21);
		n.setNBH( 2, -2, 0, 22);
		n.setNBH( -2, 2, 0, 23);
		
		
		int isOne = totalisticNotZero(i,j,k);
		
		
		if(isOne <= 3)  { u.universe[i][j][k] = 0; }
		if(isOne == 4)  { u.universe[i][j][k] = 1; }
		if(isOne >= 5)  { u.universe[i][j][k] = 0; }
		/*if(isOne == 6)  { u.universe[i][j][k] = 0; }
		if(isOne == 7)  { u.universe[i][j][k] = 0; }
		if(isOne == 8)  { u.universe[i][j][k] = 0; }
		//if(isOne == 2)  { u.universe[i][j][k] = 1; }*/
		//if(isOne > 3)  { u.universe[i][j][k] = 0; }
	}
	
	public void warts(int i, int j, int k, int val){

		n = new neighbours(4);
		
		n.setNBH(0, -1, 0, 0);
		n.setNBH(0, 1, 0, 1);
		n.setNBH(-1, 0, 0, 2);
		n.setNBH(1, 0, 0, 3);
		
		int isOne = totalisticNotZero(i,j,k);
		
		if(isOne > 0) {
			if(isOne <= 1)  { u.universe[i][j][k] = 0; }
			if(isOne == 2)  { u.universe[i][j][k] = 1; }
			if(isOne >= 3)  { u.universe[i][j][k] = 0; }
		}
		
	}
	
	public void Threads(int i, int j, int k){
		if(u.snapshotUniverse[i][j][k] != 30) { //30 is the "solid block" character from menu x.x.3.x.xx
			n = new neighbours(4);
			
			n.setNBH(1, -1, 0, 0);
			n.setNBH(-1, -1, 0, 1);
			n.setNBH(0, -1, 0, 2);
			n.setNBH(0, -2, 0, 3);
			
			int isOne = totalisticNotZero(i,j,k);
			
			if(isOne > 0) {
				if(isOne <= 1)  { u.universe[i][j][k] = 0; }
				if(isOne == 2)  { u.universe[i][j][k] = 1; }
				if(isOne >= 3)  { u.universe[i][j][k] = 0; }
			}
		}
	}
	
	public void Inverse110(int i, int j, int k, int val){
		n = new neighbours(2);
		
		n.setNBH(0, -1, 0, 0);
		n.setNBH(-1, 0, 0, 1);
		//n.setNBH(-1, 0, 0, 2);
		
		int isOne = totalisticNotZero(i,j,k);
		
		if(isOne > 0) {
			if(isOne <= 0)  { u.universe[i][j][k] = 0; }
			if(isOne == 1)  { u.universe[i][j][k] = 1; }
			if(isOne >= 2)  { u.universe[i][j][k] = 0; }
		}
		
	}
	
	public void test2(int i, int j, int k, int val){
		
		/*n = new neighbours(18);
		n.setNBH(0, 0, -1, 0);
		n.setNBH(0, -1, 0, 1);
		n.setNBH(-1, 0, 0, 2);
		n.setNBH(0, 0, 1, 3);
		n.setNBH(0, 1, 0, 4);
		n.setNBH(1, 0, 0, 5);
		
		n.setNBH(-1, -1, -1, 6);
		n.setNBH(-1, -1, 1, 7);
		n.setNBH(-1, 1, -1, 8);
		n.setNBH(-1, 1, 1, 9);
		
		n.setNBH(1, 1, 1, 10);
		n.setNBH(1, 1, -1, 11);
		n.setNBH(1, -1, 1, 12);
		n.setNBH(1, -1, -1, 13);
		
		n.setNBH(-1, -1, 0, 14);
		n.setNBH(-1, 1, 0, 15);
		n.setNBH(1, -1, 0, 16);
		n.setNBH(1, 1, 0, 17);*/
		
		n = new neighbours(26);
		n.setNBH(-1, -1, -1, 0);
		n.setNBH(-1, 0, -1, 1);
		n.setNBH(-1, 1, -1, 2);
		n.setNBH(0, -1, -1, 3);
		n.setNBH(0, 0, -1, 4);
		n.setNBH(0, 1, -1, 5);
		n.setNBH(1, -1, -1, 6);
		n.setNBH(1, 0, -1, 7);
		n.setNBH(1, 1, -1, 8);
		
		n.setNBH(-1, -1, 0, 9);
		n.setNBH(-1, 0, 0, 10);
		n.setNBH(-1, 1, 0, 11);
		n.setNBH(0, -1, 0, 12);
		//////////////////////
		n.setNBH(0, 1, 0, 13);
		n.setNBH(1, -1, 0, 14);
		n.setNBH(1, 0, 0, 15);
		n.setNBH(1, 1, 0, 16);
		
		n.setNBH(-1, -1, 1, 17);
		n.setNBH(-1, 0, 1, 18);
		n.setNBH(1, 1, 1, 19);
		n.setNBH(0, -1, 1, 20);
		n.setNBH(0, 0, 1, 21);
		n.setNBH(0, 1, 1, 22);
		n.setNBH(1, -1, 1, 23);
		n.setNBH(1, 0, 1, 24);
		n.setNBH(1, 1, 1, 25);
		
		/*n = new neighbours(26);
		n.setNBH(0, 0, 0, 0);
		n.setNBH(0, 0, 0, 1);
		n.setNBH(0, 0, 0, 2);
		n.setNBH(0, 0, 0, 3);
		n.setNBH(0, 0, 0, 4);
		n.setNBH(0, 0, 0, 5);
		n.setNBH(0, 0, 0, 6);
		n.setNBH(0, 0, 0, 7);
		n.setNBH(0, 0, 0, 8);
		n.setNBH(0, 0, 0, 9);
		n.setNBH(0, 0, 0, 10);
		n.setNBH(0, 0, 0, 11);
		n.setNBH(0, 0, 0, 12);
		n.setNBH(0, 0, 0, 13);
		n.setNBH(0, 0, 0, 14);
		n.setNBH(0, 0, 0, 15);
		n.setNBH(0, 0, 0, 16);
		n.setNBH(0, 0, 0, 17);
		n.setNBH(0, 0, 0, 18);
		n.setNBH(0, 0, 0, 19);
		n.setNBH(0, 0, 0, 20);
		n.setNBH(0, 0, 0, 21);
		n.setNBH(0, 0, 0, 22);
		n.setNBH(0, 0, 0, 23);
		n.setNBH(0, 0, 0, 24);
		n.setNBH(0, 0, 0, 25);/**/
		
		/*n = new neighbours(r.nextInt(19)+8);
		for (int ii = 0; ii < n.NBH.length; ii++) {
			n.setNBH(r.nextInt(3)-1, r.nextInt(3)-1, r.nextInt(3)-1, ii);
		}*/
		
		int isOne = totalisticNotZero(i,j,k);

		//if(isOne > 0) {
			if(isOne <= 2) { u.universe[i][j][k] = 0;}
			if(isOne == 3) {u.universe[i][j][k] = 1;}
			if(isOne >= 5) { u.universe[i][j][k] = 0;} /*else { u.universe[i][j][k] = 0; }*/
		//}
		//conway(i,j,k,1,0);
	}
	
public void actual3D(int i, int j, int k, int val){
		
		n = new neighbours(14);
		n.setNBH(0, 0, -1, 0);
		n.setNBH(0, -1, 0, 1);
		n.setNBH(-1, 0, 0, 2);
		n.setNBH(0, 0, 1, 3);
		n.setNBH(0, 1, 0, 4);
		n.setNBH(1, 0, 0, 5);
		
		n.setNBH(-1, -1, -1, 6);
		n.setNBH(-1, -1, 1, 7);
		n.setNBH(-1, 1, -1, 8);
		n.setNBH(-1, 1, 1, 9);
		
		n.setNBH(1, 1, 1, 10);
		n.setNBH(1, 1, -1, 11);
		n.setNBH(1, -1, 1, 12);
		n.setNBH(1, -1, -1, 13);
		
		int isOne = totalisticNotZero(i,j,k);

		//if(isOne > 0) {
			if(isOne <= 2) { u.universe[i][j][k] = 0;}
			if(isOne == 4){u.universe[i][j][k] = 1;}
			if(isOne >= 6) { u.universe[i][j][k] = 0;} /*else { u.universe[i][j][k] = 0; }*/
		//}
		//conway(i,j,k,1,0);
	}
	
	public void rule110(int xx, int yy, int zz){					//TEST
		n = new neighbours(3);
		
		//n.setNBH(0, 0, 0, 0);
		n.setNBH(-1, -1, 0, 0);
		n.setNBH(0, -1, 0, 1);
		n.setNBH(1, -1, 0, 2);
		//n.setNBH(0, 0, 0, 3);
		
		int[][] ar = new int[][] {{1,1,1,0},{1,1,0,1},{1,0,1,1},{1,0,0,0},{0,1,1,1},{0,1,0,1},{0,0,1,1},{0,0,0,0}};
		//int[][] ar = new int[][] {{1,1,1,0},{1,1,0,0},{1,0,1,0},{1,0,0,1},{0,1,1,1},{0,1,0,1},{0,0,1,1},{0,0,0,0}};
		int ar2[] = new int[] {0,0,0};

		for(int i = 0; i < n.NBH.length; i++) {
			if(u.snapshotUniverse[getWrap(xx, n.NBH[i][0], u.universe.length)][getWrap(yy, n.NBH[i][1], u.universe[0].length)][getWrap(zz, n.NBH[i][2], u.universe[0][0].length)] == 1) {ar2[i]=1;} else {ar2[i]=0;}
		}

		int isThis = 0;
		for(int i = 0; i < ar.length; i++) {
			isThis = 0;
			for(int j = 0; j < n.NBH.length; j++) {
				if(ar[i][j] == ar2[j]) {isThis++;}
			}
			if(isThis == 3) {u.universe[xx][yy][zz] = ar[i][3];break;}
		}
		//int t = totalistic(xx,yy,zz,1);

		/*if (t == 2){u.universe[xx][yy][zz] = 1;}
		if(t < 2 || t > 4){u.universe[xx][yy][zz] = 0;}*/
		
	}
	
	
	
	/////////////////////////////////////////////
	/////////Select Instruction to run///////////
	/////////////////////////////////////////////
	
	public void readInstructions(int[] ins, int xx, int yy, int zz) {
		if(ins[0] == 0  && (ins[1] == zz || ins[1] == -1)) {seed(xx, yy, zz, 						ins[2], ins[3]);}
		if(ins[0] == 1  && (ins[1] == zz || ins[1] == -1)) {conway(xx, yy, zz, 						ins[2], ins[3]);}
		if(ins[0] == 2  && (ins[1] == zz || ins[1] == -1)) {dozIfVal(xx, yy, zz, 					ins[2], ins[3], ins[4], ins[5]);}
		if(ins[0] == 3  && (ins[1] == zz || ins[1] == -1)) {dozDelMeIfNotLVal(xx, yy, zz, 			ins[2], ins[3], ins[4]);}
		if(ins[0] == 4  && (ins[1] == zz || ins[1] == -1)) {quantum(xx,yy,zz,						ins[2]);}
		if(ins[0] == 5  && (ins[1] == zz || ins[1] == -1)) {explorer(xx,yy,zz						);}
		if(ins[0] == 6  && (ins[1] == zz || ins[1] == -1)) {dozDelIfVal(xx,yy,zz, 					ins[2], ins[3]);}
		if(ins[0] == 7  && (ins[1] == zz || ins[1] == -1)) {dozDelIfNotVal(xx,yy,zz, 				ins[2], ins[3]);}
		if(ins[0] == 8  && (ins[1] == zz || ins[1] == -1)) {doz021(xx,yy,zz, 						ins[2]);}
		if(ins[0] == 9  && (ins[1] == zz || ins[1] == -1)) {dozDelMeIfVal(xx,yy,zz, 				ins[2], ins[3]);}
		if(ins[0] == 10 && (ins[1] == zz || ins[1] == -1)) {quantumLorE(xx,yy,zz,					ins[2], ins[3]);}
		if(ins[0] == 11 && (ins[1] == zz || ins[1] == -1)) {dozExclude(xx,yy,zz, 					ins[2], ins[3], ins[4]);}
		if(ins[0] == 12 && (ins[1] == zz || ins[1] == -1)) {dozIncrementIfVal(xx,yy,zz, 			ins[2], ins[3], ins[4], ins[5]);}
		if(ins[0] == 13 && (ins[1] == zz || ins[1] == -1)) {dozDelMeIfNotVal(xx,yy,zz, 				ins[2], ins[3], ins[4]);}
		if(ins[0] == 14 && (ins[1] == zz || ins[1] == -1)) {quantumWeight(xx,yy,zz,					ins[2]);}
		if(ins[0] == 15 && (ins[1] == zz || ins[1] == -1)) {twat(xx,yy,zz							);} //lol, typo for test
		if(ins[0] == 16 && (ins[1] == zz || ins[1] == -1)) {rule110(xx,yy,zz						);}
		if(ins[0] == 17 && (ins[1] == zz || ins[1] == -1)) {rain2(xx,yy,zz							);}
		if(ins[0] == 18 && (ins[1] == zz || ins[1] == -1)) {goop(xx,yy,zz							);}
		if(ins[0] == 19 && (ins[1] == zz || ins[1] == -1)) {internalAffairs(xx,yy,zz				);}
		if(ins[0] == 20 && (ins[1] == zz || ins[1] == -1)) {meekrochyp(xx,yy,zz						);}
		if(ins[0] == 21 && (ins[1] == zz || ins[1] == -1)) {diamondShuffle(xx,yy,zz					);}
		if(ins[0] == 22 && (ins[1] == zz || ins[1] == -1)) {rain(xx,yy,zz							);}
		if(ins[0] == 23 && (ins[1] == zz || ins[1] == -1)) {test2(xx,yy,zz,							ins[2]);}
		if(ins[0] == 24 && (ins[1] == zz || ins[1] == -1)) {Conway3D(xx,yy,zz						);}
		if(ins[0] == 25 && (ins[1] == zz || ins[1] == -1)) {dozUnIncrementIfVal(xx,yy,zz, 			ins[2], ins[3], ins[4], ins[5]);}
		if(ins[0] == 26 && (ins[1] == zz || ins[1] == -1)) {Threads(xx,yy,zz 					);}
		
	}
	
}

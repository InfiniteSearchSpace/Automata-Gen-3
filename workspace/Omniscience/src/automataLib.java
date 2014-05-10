import java.util.Random;


public class automataLib {
	
	Random r = new Random();
	neighbours n;
	
	Universe u;
	Main m;
	//dataSources d;
	
	int[][] instructions; //holds the actions to perform
	
	int[][] min_grow_die = new int[1][6];
	
	//constructor
	public automataLib(Main mm) {
		m = mm;
	}
	
	//takes a universe and the dataSources object for writing
	public void setTargetUni(Universe uu) {
		u=uu;
	}

	
	/*  = = = = = = = = = = = = = = = = = = = = 
	 *  = = = = = = = = = = = = = = = = = = = = 
	 * 		 	Value setting & modifications
	 *  = = = = = = = = = = = = = = = = = = = = 
	 *  = = = = = = = = = = = = = = = = = = = = 
	 */ 
	

	//copy pattern into all layers & cells, tile.
	/*public void writeData() {
		for(int i = 0; i < u.universe.length; i++) {
			for (int j = 0; j < u.universe[0].length; j++) {
				for (int k = 0; k < u.universe[0][0].length; k++) {
					u.universe[i][j][k] = d.readNext();
				}
			}
    	}
	}*/
	

	//takes array co-ord, modification int, array param length, and returns wrap position.
    public int getWrap(int val, int mod, int len) { 
    	if((val+mod) % len < 0) {
    		return len+((val+mod) % len);
    	} else {
    		return (val+mod) % len;
    	}
    }

    //sets the specified cell to specified value
    public void placeval(int xx, int yy, int zz, int rand, int v) { 
    	if(r.nextInt(rand) == 0) {
    		u.universe[xx][yy][zz] = v;
    	}
    }
    
    public int getval(int xx, int yy, int zz, int rand, int v) { 
    	if(r.nextInt(rand) == 0) {
    		return u.universe[xx][yy][zz];
    	} else {
    		return 0;
    	}
    }
	
    //draws a line of val. Can be solid or rand, can be veto'd, optional overwrite with 0.  
    //OBSOLETE!
    public void placeLine(int xx, int yy, int zz, int rand, int len, boolean placeO, int veto, int val) {
    	
    	if(r.nextInt(veto) == 0) { //chance to not write the line
		    for(int i = 0; i < len-xx; i++) {
		    	if(r.nextInt(rand) == 0) {
		    		placeval(xx+i, yy, zz, 1, val);
		    	} else if(placeO) {placeval(xx+i, yy, zz, 1, 0);}
		    }
    	}
    	
    }
    
  //draws a line of val. Can be solid or rand, can be veto'd, optional overwrite with 0.    
    public void plcLn(int xx, int yy, int zz, int rand, int xnullx, int val, int veto, int placeO, int len, int blockSize, int toolVar) {
    	
    	if(r.nextInt(veto) == 0) { //chance to not write the line
		    for(int i = 1; i < ((len-xx)/2)+1; i++) {
		    	if(r.nextInt(rand) == 0) {
		    		placeval((xx+i)+((blockSize-1)/2), yy+((blockSize-1)/2), zz, 1, val+r.nextInt(toolVar));
		    	} else if(placeO > 0) {placeval((xx+i)+((blockSize-1)/2)-1, yy+((blockSize-1)/2), zz, 1, 0);}
		    }
    	}
    	
    }
    
    public void plcDataLn(int xx, int yy, int zz, int rand, int xnullx, int val, int veto, int placeO, int len, int blockSize, int toolVar, dataSources d, int softwrite) {
    	
    	if(r.nextInt(veto) == 0) { //chance to not write the line
		    for(int i = 1; i < ((len-xx)/2)+1; i++) {
		    	val = d.readNext();
		    	if(val != 0 || softwrite == 0) { //softwrite
			    	if(r.nextInt(rand) == 0) {
			    		placeval((xx+i)+((blockSize-1)/2), yy+((blockSize-1)/2), zz, 1, val+r.nextInt(toolVar));
			    	} else if(placeO > 0) {placeval((xx+i)+((blockSize-1)/2)-1, yy+((blockSize-1)/2), zz, 1, 0);}
		    	}
		    }
    	}
    	
    }
    
    
    public int[] getDataLn(int xx, int yy, int zz, int rand, int xnullx, int val, int veto, int placeO, int len, int blockSize, int toolVar) {
    	
    	int[] dataLine = new int[blockSize]; //may be innacurate
    	
    	if(r.nextInt(veto) == 0) { //chance to not get the line
		    for(int i = 1; i < ((len-xx)/2)+1; i++) {
		    	int dataInt = 0;
		    	
		    	if(r.nextInt(rand) == 0) {
		    		dataInt = getval((xx+i)+((blockSize-1)/2), yy+((blockSize-1)/2), zz, 1, val+r.nextInt(toolVar));
		    	} else if(placeO > 0) {dataInt = getval((xx+i)+((blockSize-1)/2)-1, yy+((blockSize-1)/2), zz, 1, 0);}
		    	
		    	dataLine[i-1]=dataInt;
		    }
    	}
    	
    	return dataLine;
    	
    }
    
    
    //with chance, set cell to val 
    public void seed(int xx, int yy, int zz, int rand, int xnullx, int val){ //chance to seed location
    	if(r.nextInt(rand) == 0) {placeval(xx, yy, zz, 1, val);}
    }
    
    //with chance, reset pixel to val, applies to whole universe
    public void seedAll(int rand, int val, int rndVar){
    	
    	for(int i = 0; i < u.universe.length; i++) {
			for (int j = 0; j < u.universe[0].length; j++) {
				for (int k = 0; k < u.universe[0][0].length; k++) {
			    	seed(i,j,k,rand,-1,val+r.nextInt(rndVar));
				}
			}
    	}
    } 
    
    //chance to seed every pixel on this layer
    public void seedZ(int rand, int zz, int val, int rndVar){ 
    	for(int i = 0; i < u.universe.length; i++) {
			for (int j = 0; j < u.universe[0].length; j++) {
			    seed(i,j,zz,rand, 0, val+r.nextInt(rndVar));
			}
    	}
    }

	//sets the neighbour at reference iNbr to val
	public void setNeighbourVal(int xx, int yy, int zz, int iNbr, int val) {
		u.universe[getWrap(xx, n.NBH[iNbr][0], u.universe.length)][getWrap(yy, n.NBH[iNbr][1], u.universe[0].length)][getWrap(zz, n.NBH[iNbr][2], u.universe[0][0].length)] = val;
	}
    
    
	/*  = = = = = = = = = = = = = = = = = = = = 
	 *  = = = = = = = = = = = = = = = = = = = = 
	 * 		 	Conditional Checks
	 *  = = = = = = = = = = = = = = = = = = = = 
	 *  = = = = = = = = = = = = = = = = = = = = 
	 */ 

	
	//returns the value of snapshot neighbour at iNbr
	public int getNbrVal(int xx, int yy, int zz, int iNbr){
		return u.snapshotUniverse[getWrap(xx, n.NBH[iNbr][0], u.universe.length)][getWrap(yy, n.NBH[iNbr][1], u.universe[0].length)][getWrap(zz, n.NBH[iNbr][2], u.universe[0][0].length)];
	}
	
	//Checks if specified snapshot cell is specified value, return T/F
	public boolean ifval(int xx, int yy, int zz, int val){
		
		if(u.snapshotUniverse[xx][yy][zz] == val) {
			return true;
		} else { 
			return false;
		}
		
	}
	
	//Return the value of the specified snapshot cell
	public int getval(int xx, int yy, int zz){ 
		return u.snapshotUniverse[xx][yy][zz];
	}
	
    //Counts how many neighbours are equal to val
	public int nbrCountVal(int xx, int yy, int zz, int val){
		int isOne = 0;

		for(int i = 0; i < n.NBH.length; i++) {
			if(u.snapshotUniverse[getWrap(xx, n.NBH[i][0], u.universe.length)][getWrap(yy, n.NBH[i][1], u.universe[0].length)][getWrap(zz, n.NBH[i][2], u.universe[0][0].length)] == val) {isOne++;}
		}
		
		return isOne;
	}
	
	//sums the value of all neighbours
	public int nbrSum(int xx, int yy, int zz){
		int isOne = 0;

		for(int i = 0; i < n.NBH.length; i++) {
			isOne += u.snapshotUniverse[getWrap(xx, n.NBH[i][0], u.universe.length)][getWrap(yy, n.NBH[i][1], u.universe[0].length)][getWrap(zz, n.NBH[i][2], u.universe[0][0].length)];
		}
		
		return isOne;
	}
	
	public int nbrAvg(int xx, int yy, int zz){
		//int isOne = 0;
		//if(nbrCountNotVal(xx,yy,zz,0) > 0) {
		int isOne = nbrSum(xx,yy,zz) / n.NBH.length;  //nbrCountNotVal(xx,yy,zz,0);
		//}
		return isOne;
	}
	
	//counts the neighbours less than val
	public int nbrCountLessThanVal(int xx, int yy, int zz, int val){
		int isOne = 0;

		for(int i = 0; i < n.NBH.length; i++) {
			if(u.snapshotUniverse[getWrap(xx, n.NBH[i][0], u.universe.length)][getWrap(yy, n.NBH[i][1], u.universe[0].length)][getWrap(zz, n.NBH[i][2], u.universe[0][0].length)] < val) {isOne++;}
		}
		
		return isOne;
	}
	
	//counts the neighbours more than val
	public int nbrCountMoreThanVal(int xx, int yy, int zz, int val){
		int isOne = 0;

		for(int i = 0; i < n.NBH.length; i++) {
			if(u.snapshotUniverse[getWrap(xx, n.NBH[i][0], u.universe.length)][getWrap(yy, n.NBH[i][1], u.universe[0].length)][getWrap(zz, n.NBH[i][2], u.universe[0][0].length)] > val) {isOne++;}
		}
		
		return isOne;
	}
	
	//counts the neighbours not equal to val
	public int nbrCountNotVal(int xx, int yy, int zz, int val){
		int isOne = 0;

		for(int i = 0; i < n.NBH.length; i++) {
			if(u.snapshotUniverse[getWrap(xx, n.NBH[i][0], u.universe.length)][getWrap(yy, n.NBH[i][1], u.universe[0].length)][getWrap(zz, n.NBH[i][2], u.universe[0][0].length)] != val) {isOne++;}
		}
		
		return isOne;
	}

	//returns the most common number among neighbours
	public int nbrGetMode(int xx, int yy, int zz, int val){
		return 0;
	}

		
	public int getNbrCounts(int xx, int yy, int zz) {
		return nbrCountNotVal(xx, yy, zz,0);
	}
	 
	 
	
	/*  = = = = = = = = = = = = = = = = = = = = 
	 *  = = = = = = = = = = = = = = = = = = = = 
	 * 		 	Automata
	 *  = = = = = = = = = = = = = = = = = = = = 
	 *  = = = = = = = = = = = = = = = = = = = = 
	 */ 
	
	
	/*		Parameters
	 * Default: xx, yy, zz
	 * RandomProc
	 * Threshold
	 * 
	 * 
	 */
	
	public void warts2(int xx, int yy, int zz){
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
	
	public void internalAffairs(int xx, int yy, int zz){					
		n = new neighbours(4);
		n.setNBH(0, -1, 0, 0);
		n.setNBH(-1, 0, 0, 1);
		n.setNBH(0, 1, 0, 2);
		n.setNBH(1, 0, 0, 3);
		
		
		int t = nbrCountNotVal(xx,yy,zz,0);

		if (t == 2){u.universe[xx][yy][zz] = 1;}
		if(t < 2 || t > 3){u.universe[xx][yy][zz] = 0;}
		
	}
	
	public void meekrochyp(int xx, int yy, int zz){					
		n = new neighbours(4);
		n.setNBH(-1, -1, 0, 0);
		n.setNBH(1, -1, 0, 1);
		n.setNBH(-1, 1, 0, 2);
		n.setNBH(1, 1, 0, 3);
		

		int t = nbrCountNotVal(xx,yy,zz,0);

		if (t == 2){u.universe[xx][yy][zz] = 1;}
		if(t < 2 || t > 4){u.universe[xx][yy][zz] = 0;}
		
	}
	
	public void meekrochypFr(int xx, int yy, int zz){					
		n = new neighbours(4);
		n.setNBH(-1, -1, 0, 0);
		n.setNBH(1, -1, 0, 1);
		n.setNBH(-1, 1, 0, 2);
		n.setNBH(1, 1, 0, 3);
		
		int sum = 0;
		for(int i = 0; i < n.NBH.length; i++){
			sum += getNbrCounts(getWrap(xx, n.NBH[i][0], u.universe.length), getWrap(yy, n.NBH[i][1], u.universe[0].length), getWrap(zz, n.NBH[i][2], u.universe[0][0].length));
		}

		if (sum <= 4){u.universe[xx][yy][zz] = 0;}
		if (sum == 4){u.universe[xx][yy][zz] = 1;}
		if (sum >= 8){u.universe[xx][yy][zz] = 0;}
		
	}
	
	public void diamondShuffle(int xx, int yy, int zz){					
		n = new neighbours(4);
		n.setNBH(-1, -1, 0, 0);
		n.setNBH(1, -1, 0, 1);
		n.setNBH(-1, 1, 0, 2);
		n.setNBH(1, 1, 0, 3);

		int t = nbrCountNotVal(xx,yy,zz,0);

		if (t == 2){u.universe[xx][yy][zz] = 1;}
		if(t < 2 || t > 3){u.universe[xx][yy][zz] = 0;}
		
	}

	public void goop(int xx, int yy, int zz){					
		
		n = new neighbours(5);
		n.setNBH(-1, 1, 0, 0);
		n.setNBH(1, 1, 0, 1);
		n.setNBH(0, -1, 0, 2);
		n.setNBH(-2, -1, 0, 3);
		n.setNBH(2, -1, 0, 4);

		int t = nbrCountNotVal(xx,yy,zz,0);
		
		if (t < 2 || t > 4) {
			for(int i = 0; i < 5; i++) {
				u.universe[xx][yy][zz] = 0;
			}
		} 
		
		if (t == 3){u.universe[xx][yy][zz] = 1;}
		
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
					u.universe[xx][yy][zz]++;
					u.universe[getWrap(xx, n.NBH[i][0], u.universe.length)][getWrap(yy, n.NBH[i][1], u.universe[0].length)][getWrap(zz, n.NBH[i][2], u.universe[0][0].length)]--;
				}
			}
		}
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
					
					u.universe[xx][yy][zz]=u.universe[getWrap(xx, n.NBH[i][0], u.universe.length)][getWrap(yy, n.NBH[i][1], u.universe[0].length)][getWrap(zz, n.NBH[i][2], u.universe[0][0].length)];
					
					u.universe[getWrap(xx, n.NBH[i][0], u.universe.length)][getWrap(yy, n.NBH[i][1], u.universe[0].length)][getWrap(zz, n.NBH[i][2], u.universe[0][0].length)]=0;
				}
			}
		}

    }
	
	public void Wave(int xx, int yy, int zz, int rand){

		n = new neighbours(4);
		n.setNBH(-1, 0, 0, 0);
		n.setNBH(0, -1, 0, 1);
		n.setNBH(1, 0, 0, 2);
		n.setNBH(0, 1, 0, 3);
		
		if(u.snapshotUniverse[xx][yy][zz] > 1) {
			if(r.nextInt(rand) == 0) {
				for(int i = 0; i < n.NBH.length; i++) {
					if(u.snapshotUniverse[xx][yy][zz] > 4) {
						if(u.universe[getWrap(xx, n.NBH[i][0], u.universe.length)][getWrap(yy, n.NBH[i][1], u.universe[0].length)][getWrap(zz, n.NBH[i][2], u.universe[0][0].length)] < u.snapshotUniverse[xx][yy][zz]) {
							u.universe[getWrap(xx, n.NBH[i][0], u.universe.length)][getWrap(yy, n.NBH[i][1], u.universe[0].length)][getWrap(zz, n.NBH[i][2], u.universe[0][0].length)]+=1;
							u.universe[xx][yy][zz]-=1;
						}
						
					}
				}
			} 
		} else /*if(u.snapshotUniverse[xx][yy][zz] == 1)*/ {
			if(r.nextInt(rand) == 0) {
				int proc = r.nextInt(n.NBH.length);
				for(int i = 0; i < n.NBH.length; i++) {
					if(proc == i) {
						u.universe[getWrap(xx, n.NBH[i][0], u.universe.length)][getWrap(yy, n.NBH[i][1], u.universe[0].length)][getWrap(zz, n.NBH[i][2], u.universe[0][0].length)]+=u.snapshotUniverse[xx][yy][zz];
						u.universe[xx][yy][zz]-=u.snapshotUniverse[xx][yy][zz];
					}
				}
			}
		
		}

    }
	
	public void Brownian(int xx, int yy, int zz, int rand){

		if( r.nextInt(rand) == 0) {
			n = new neighbours(6);
			n.setNBH(-1, 0, 0, 0);
			n.setNBH(0, -1, 0, 1);
			n.setNBH(1, 0, 0, 2);
			n.setNBH(0, 1, 0, 3);
			n.setNBH(0, 0, -1, 4);
			n.setNBH(0, 0, 1, 5);
			
			int proc = r.nextInt(n.NBH.length);
			
			for(int i = 0; i < n.NBH.length; i++) {
				if(proc == i) {
					u.universe[getWrap(xx, n.NBH[i][0], u.universe.length)][getWrap(yy, n.NBH[i][1], u.universe[0].length)][getWrap(zz, n.NBH[i][2], u.universe[0][0].length)]+=u.snapshotUniverse[xx][yy][zz];
					u.universe[xx][yy][zz]-=u.snapshotUniverse[xx][yy][zz];
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

	public void rain(int xx, int yy, int zz){
		
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
	
	public void rain2(int xx, int yy, int zz){
		
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
	
	public void sierpenski(int xx, int yy, int zz){
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
	
	public void snapSierpenski(int xx, int yy, int zz){
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
	
	public void conway(int xx, int yy, int zz){
		
		n = new neighbours(8);
		n.setNBH(-1, -1, 0, 0);
		n.setNBH( 0, -1, 0, 1);	
		n.setNBH( 1, -1, 0, 2);
		n.setNBH(-1, 0, 0, 3);	
		n.setNBH( 1, 0, 0, 4);
		n.setNBH(-1, 1, 0, 5);	
		n.setNBH(0, 1, 0, 6);
		n.setNBH(1, 1, 0, 7);
		
		int isOne = nbrCountNotVal(xx,yy,zz,0);
		
		if(isOne < 2)  { u.universe[xx][yy][zz] = 0; }
		if(isOne > 3)  { u.universe[xx][yy][zz] = 0; }
		if(isOne == 3) { u.universe[xx][yy][zz] = 1; }

    }

	public void probbilityGrowth(int xx, int yy, int zz){
		n = new neighbours(r.nextInt(8)+1);
		for (int ii = 0; ii < n.NBH.length; ii++) {
			n.setNBH(r.nextInt(3)-1, r.nextInt(3)-1, 0, ii);
		}
		
		if(u.snapshotUniverse[xx][yy][zz] <= 1 ){
			int num = r.nextInt(33)-16;
			if(num != 0 && nbrCountVal(xx,yy,zz, num) > 1 && nbrCountVal(xx,yy,zz, num) <= 3) {
				if(r.nextInt(6) <= 4) {u.universe[xx][yy][zz] = num;} else {u.universe[xx][yy][zz] += r.nextInt(9)-4;}
			} 

			if(nbrSum(xx,yy,zz)*nbrSum(xx,yy,zz) < (nbrCountVal(xx,yy,zz, num)*num*num)  || nbrCountVal(xx,yy,zz, num) >= 7) {
				u.universe[xx][yy][zz] = 0;
			}
		}
	}
	
	public void PointToCircle(int xx, int yy, int zz){

		n = new neighbours(5);
		n.setNBH(0, -1, 0, 0);
		n.setNBH( 0, 1, 0, 1);	
		n.setNBH( -1, 0, 0, 2);
		n.setNBH( 1, 0, 0, 3);
		n.setNBH( 0, 0, 0, 4);

		int isOne = nbrCountNotVal(xx,yy,zz,0);
		
		if(isOne <= 3)  { u.universe[xx][yy][zz] = 0; }
		if(isOne >= 5)  { u.universe[xx][yy][zz] = 0; }
		if(isOne == 2 || isOne == 1)  { u.universe[xx][yy][zz] = 1; }

	}
		
	public void rope(int xx, int yy, int zz){

		n = new neighbours(3);
		n.setNBH( -1, -1, 0, 0);
		n.setNBH( 0, -1, 0, 1);	
		n.setNBH( -1, 0, 0, 2);
		
		int isOne = nbrCountNotVal(xx,yy,zz,0);
		
		if(isOne == 1)  { u.universe[xx][yy][zz] = 0; }
		if(isOne == 2)  { u.universe[xx][yy][zz] = 1; }
		if(isOne > 2)  { u.universe[xx][yy][zz] = 0; }
	}
	
	
	public void extendedRangeCustom(int xx, int yy, int zz){

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
		
		
		int isOne = nbrCountNotVal(xx,yy,zz,0);
		
		if(isOne >= min_grow_die[0][0] && isOne <= min_grow_die[0][1]/* && isOne <= min_grow_die[0][3]*/)  { u.universe[xx][yy][zz] = 0; }
		if(isOne >= min_grow_die[0][2] && isOne <= min_grow_die[0][3]/* && isOne <= min_grow_die[0][4]*/)  { u.universe[xx][yy][zz] = 1; }
		if(isOne >= min_grow_die[0][4] && isOne <= min_grow_die[0][5]/* && isOne <= min_grow_die[0][5]*/)  { u.universe[xx][yy][zz] = 0; }
	}
	
	public void extendedRangeCustom2(int xx, int yy, int zz){

		n = new neighbours(8);
		
		n.setNBH( 0, -1, 0, 0);
		n.setNBH( -1, 0, 0, 1);
		n.setNBH( 1, 0, 0, 2);
		n.setNBH( 0, 1, 0, 3);
		n.setNBH( -1, -1, 0, 4);
		n.setNBH( 1, 1, 0, 5);
		n.setNBH( -1, 1, 0, 6);
		n.setNBH( 1, -1, 0, 7);
		
		int isOne = nbrCountNotVal(xx,yy,zz,0);
		
		if(isOne >= min_grow_die[0][0] && isOne <= min_grow_die[0][1]/* && isOne <= min_grow_die[0][3]*/)  { u.universe[xx][yy][zz] = 0; }
		if(isOne >= min_grow_die[0][2] && isOne <= min_grow_die[0][3]/* && isOne <= min_grow_die[0][4]*/)  { u.universe[xx][yy][zz] = 1; }
		if(isOne >= min_grow_die[0][4] && isOne <= min_grow_die[0][5]/* && isOne <= min_grow_die[0][5]*/)  { u.universe[xx][yy][zz] = 0; }
	}
	
	public void extendedRangeCustom4(int xx, int yy, int zz){

		n = new neighbours(48);
		
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
		
		
		n.setNBH( -3, -3, 0, 24);
		n.setNBH( -2, -3, 0, 25);
		n.setNBH( -1, -3, 0, 26);
		n.setNBH( 0, -3, 0, 27);
		n.setNBH( 1, -3, 0, 28);
		n.setNBH( 2, -3, 0, 29);
		n.setNBH( 3, -3, 0, 30);
		
		n.setNBH( -3, 3, 0, 31);
		n.setNBH( -2, 3, 0, 32);
		n.setNBH( -1, 3, 0, 33);
		n.setNBH( 0, 3, 0, 34);
		n.setNBH( 1, 3, 0, 35);
		n.setNBH( 2, 3, 0, 36);
		n.setNBH( 3, 3, 0, 37);
		
		n.setNBH( -3, -2, 0, 38);
		n.setNBH( -3, -1, 0, 39);
		n.setNBH( -3, 0, 0, 40);
		n.setNBH( -3, 1, 0, 41);
		n.setNBH( -3, 2, 0, 42);
		
		n.setNBH( 3, -2, 0, 43);
		n.setNBH( 3, -1, 0, 44);
		n.setNBH( 3, 0, 0, 45);
		n.setNBH( 3, 1, 0, 46);
		n.setNBH( 3, 2, 0, 47);
		
		int isOne = nbrCountNotVal(xx,yy,zz,0);
		
		if(isOne >= min_grow_die[0][0] && isOne <= min_grow_die[0][1]/* && isOne <= min_grow_die[0][3]*/)  { u.universe[xx][yy][zz] = 0; }
		if(isOne >= min_grow_die[0][2] && isOne <= min_grow_die[0][3]/* && isOne <= min_grow_die[0][4]*/)  { u.universe[xx][yy][zz] = 1; }
		if(isOne >= min_grow_die[0][4] && isOne <= min_grow_die[0][5]/* && isOne <= min_grow_die[0][5]*/)  { u.universe[xx][yy][zz] = 0; }
	}
	

	
	
	
	public void extendedRangeCustom3(int xx, int yy, int zz){

		n = new neighbours(6);
		n.setNBH(-1, 0, 0, 0);
		n.setNBH(0, -1, 0, 1);
		n.setNBH(1, 0, 0, 2);
		n.setNBH(0, 1, 0, 3);
		n.setNBH(0, 0, -1, 4);
		n.setNBH(0, 0, 1, 5);
		
		int isOne = nbrCountNotVal(xx,yy,zz,0);
		
		if(isOne >= min_grow_die[0][0] && isOne <= min_grow_die[0][1]/* && isOne <= min_grow_die[0][3]*/)  { u.universe[xx][yy][zz] = 0; }
		if(isOne >= min_grow_die[0][2] && isOne <= min_grow_die[0][3]/* && isOne <= min_grow_die[0][4]*/)  { u.universe[xx][yy][zz] = 1; }
		if(isOne >= min_grow_die[0][4] && isOne <= min_grow_die[0][5]/* && isOne <= min_grow_die[0][5]*/)  { u.universe[xx][yy][zz] = 0; }
	}
	
	public void extendedRangeCustom5(int xx, int yy, int zz){

		n = new neighbours(4);
		n.setNBH(-1, 0, 0, 0);
		n.setNBH(0, -1, 0, 1);
		n.setNBH(1, 0, 0, 2);
		n.setNBH(0, 1, 0, 3);
		
		int isOne = 0;
		for(int i = 0; i < n.NBH.length; i++){
			isOne += getNbrCounts(getWrap(xx, n.NBH[i][0], u.universe.length), getWrap(yy, n.NBH[i][1], u.universe[0].length), getWrap(zz, n.NBH[i][2], u.universe[0][0].length));
		}
		
		if(isOne >= min_grow_die[0][0] && isOne <= min_grow_die[0][1]/* && isOne <= min_grow_die[0][3]*/)  { u.universe[xx][yy][zz] = 0; }
		if(isOne >= min_grow_die[0][2] && isOne <= min_grow_die[0][3]/* && isOne <= min_grow_die[0][4]*/)  { u.universe[xx][yy][zz] = 1; }
		if(isOne >= min_grow_die[0][4] && isOne <= min_grow_die[0][5]/* && isOne <= min_grow_die[0][5]*/)  { u.universe[xx][yy][zz] = 0; }
	}
	
	public void extendedRangeCustom6(int xx, int yy, int zz){

		n = new neighbours(6);
		
		n.setNBH( -3, -1, 0, 0);
		n.setNBH( 3, -1, 0, 1);
		n.setNBH( -2, -2, 0, 2);
		n.setNBH( 2, -2, 0, 3);
		n.setNBH( -1, -1, 0, 4);
		n.setNBH( -1, -1, 0, 5);
		
		/*int isOne = 0;
		for(int i = 0; i < n.NBH.length; i++){
			isOne += getNbrCounts(getWrap(xx, n.NBH[i][0], u.universe.length), getWrap(yy, n.NBH[i][1], u.universe[0].length), getWrap(zz, n.NBH[i][2], u.universe[0][0].length));
		}*/
		int isOne = nbrCountNotVal(xx,yy,zz,0);
		
		if(isOne >= min_grow_die[0][0] && isOne <= min_grow_die[0][1]/* && isOne <= min_grow_die[0][3]*/)  { u.universe[xx][yy][zz] = 0; }
		if(isOne >= min_grow_die[0][2] && isOne <= min_grow_die[0][3]/* && isOne <= min_grow_die[0][4]*/)  { u.universe[xx][yy][zz] = 1; }
		if(isOne >= min_grow_die[0][4] && isOne <= min_grow_die[0][5]/* && isOne <= min_grow_die[0][5]*/)  { u.universe[xx][yy][zz] = 0; }
	}
	
	public void ConwayExtendedRange(int xx, int yy, int zz){

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
		
		
		int isOne = nbrCountNotVal(xx,yy,zz,0);
		
		
		if(isOne <= 3)  { u.universe[xx][yy][zz] = 0; }
		if(isOne == 4)  { u.universe[xx][yy][zz] = 1; }
		if(isOne >= 5)  { u.universe[xx][yy][zz] = 0; }
		/*if(isOne == 6)  { u.universe[xx][yy][zz] = 0; }
		if(isOne == 7)  { u.universe[xx][yy][zz] = 0; }
		if(isOne == 8)  { u.universe[xx][yy][zz] = 0; }
		//if(isOne == 2)  { u.universe[xx][yy][zz] = 1; }*/
		//if(isOne > 3)  { u.universe[xx][yy][zz] = 0; }
	}
	
	public void ConwayExtendedRange2(int xx, int yy, int zz){

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
		
		
		int isOne = nbrCountNotVal(xx,yy,zz,0);
		
		
		/*if(isOne <= 3)  { u.universe[xx][yy][zz] = 0; }
		if(isOne == 4)  { u.universe[xx][yy][zz] = 1; }
		if(isOne >= 5)  { u.universe[xx][yy][zz] = 0; }
		if(isOne == 6)  { u.universe[xx][yy][zz] = 1; }*/
		
		if(isOne <=0)  { u.universe[xx][yy][zz] = 0; }
		if(isOne == 5)  { u.universe[xx][yy][zz] = 1; }
		if(isOne >= 7)  { u.universe[xx][yy][zz] = 0; }
		
		/*if(isOne == 7)  { u.universe[xx][yy][zz] = 0; }
		if(isOne == 8)  { u.universe[xx][yy][zz] = 0; }*/
		//if(isOne == 2)  { u.universe[xx][yy][zz] = 1; }
		//if(isOne > 3)  { u.universe[xx][yy][zz] = 0; }
	}
	
	public void ConwayExtendedRange3(int xx, int yy, int zz){

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
		
		
		int sum = nbrCountNotVal(xx,yy,zz,0);
		
		if(sum <= 3)  { u.universe[xx][yy][zz] = 0; }
		if(sum == 5)  { u.universe[xx][yy][zz] = 1; }
		if(sum > 5)  { u.universe[xx][yy][zz] = 0; }/**/
		
		/*if(sum <= 7)  { u.universe[xx][yy][zz] = 0; }
		if(sum == 5)  { u.universe[xx][yy][zz] = 1; }
		if(sum > 99)  { u.universe[xx][yy][zz] = 0; }*/
	}
	
	public void ConwayExtendedRange4(int xx, int yy, int zz){

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
		
		
		int sum = nbrCountNotVal(xx,yy,zz,0);
	
		if(sum <= 1)  			 	{ u.universe[xx][yy][zz] = 0; } //min
		if(sum >= 10 && sum <= 11) 	{ u.universe[xx][yy][zz] = 1; } //balance
		if(sum >= 12 && sum <= 99) 	{ u.universe[xx][yy][zz] = 0; }	//max

	}
	
	public void ConwayExtendedRange5(int xx, int yy, int zz){

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
		
		
		int sum = nbrCountNotVal(xx,yy,zz,0);

		if(sum <= 1)  			 	{ u.universe[xx][yy][zz] = 0; } //max
		if(sum >= 5 && sum <= 5) 	{ u.universe[xx][yy][zz] = 1; } //balance
		if(sum >= 6 && sum <= 99) 	{ u.universe[xx][yy][zz] = 0; }	//min
		
	}
	
	public void ConwayExtendedRange6(int xx, int yy, int zz){

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
		
		
		int sum = nbrCountNotVal(xx,yy,zz,0);
		
		if(sum <= 5)  			 	{ u.universe[xx][yy][zz] = 0; } //max
		if(sum >= 13 && sum <= 18) 	{ u.universe[xx][yy][zz] = 1; } //balance
		if(sum >= 7 && sum <= 7) 	{ u.universe[xx][yy][zz] = 0; }	//min
		
	
	}
	
	public void ConwayExtendedRange7(int xx, int yy, int zz){

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
		
		
		int sum = nbrCountNotVal(xx,yy,zz,0);

		if(sum <= 6)  { u.universe[xx][yy][zz] = 0; }
		if(sum == 8)  { u.universe[xx][yy][zz] = 1; }
		if(sum == 12) {u.universe[xx][yy][zz] = 0;}
		
	}
	
	public void ConwayExtendedRange8(int xx, int yy, int zz){

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
		
		
		int sum = nbrCountNotVal(xx,yy,zz,0);

		if(sum <= 5)  { u.universe[xx][yy][zz] = 0; }
		if(sum == 8)  { u.universe[xx][yy][zz] = 1; }
		if(sum == 11) {u.universe[xx][yy][zz] = 0;}
		
	}
	
	public void ConwayExtendedRange9(int xx, int yy, int zz){

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
		
		
		int sum = nbrCountNotVal(xx,yy,zz,0);
	
		if(sum <= 5)  { u.universe[xx][yy][zz] = 0; }
		if(sum == 6)  { u.universe[xx][yy][zz] = 1; }
		if(sum == 7) {u.universe[xx][yy][zz] = 0;}
		
	}
	
	public void ConwayExtendedRange10(int xx, int yy, int zz){

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
		
		
		int sum = nbrCountNotVal(xx,yy,zz,0);
	
		if(sum <= 1)  			 	{ u.universe[xx][yy][zz] = 0; } //min
		if(sum >= 10 && sum <= 11) 	{ u.universe[xx][yy][zz] = 1; } //balance
		if(sum >= 12 && sum <= 99) 	{ u.universe[xx][yy][zz] = 0; }	//max
		
		
		if(sum <= 7)  { u.universe[xx][yy][zz] = 0; }
		if(sum == 4)  { u.universe[xx][yy][zz] = 1; }
		if(sum == 15) {u.universe[xx][yy][zz] = 0;}
		
	}
	
	public void ConwayExtendedRange11(int xx, int yy, int zz){

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
		
		
		int sum = nbrCountNotVal(xx,yy,zz,0);
	
		if(sum <= 12)  { u.universe[xx][yy][zz] = 0; }
		if(sum < 13)  { u.universe[xx][yy][zz] = 0; }
		if(sum == 4)  { u.universe[xx][yy][zz] = 1; }
		if(sum >= 13)  { u.universe[xx][yy][zz] = 1; }
		
	}
	
	public void ConwayExtendedRange12(int xx, int yy, int zz){

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
		
		
		int sum = nbrCountNotVal(xx,yy,zz,0);
	
		if(sum <= 12)  { u.universe[xx][yy][zz] = 0; }
		if(sum == 4)  { u.universe[xx][yy][zz] = 1; }
		if(sum > 99)  { u.universe[xx][yy][zz] = 0; }
	}
	
	public void ConwayExtendedRange13(int xx, int yy, int zz){

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
		
		
		int sum = nbrCountNotVal(xx,yy,zz,0);
	
		if(sum <= 8)  { u.universe[xx][yy][zz] = 0; }
		if(sum == 4)  { u.universe[xx][yy][zz] = 1; }
		if(sum > 99)  { u.universe[xx][yy][zz] = 0; }
	}
	
	public void ConwayExtendedRange13_2(int xx, int yy, int zz){

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
		
		
		int sum = nbrCountNotVal(xx,yy,zz,0);
	
		if(sum <= 6)  				{ u.universe[xx][yy][zz] = 0; }
		if(sum == 4)  				{ u.universe[xx][yy][zz] = 1; }
		if(sum >= 9 && sum <= 11)  	{ u.universe[xx][yy][zz] = 0; }
	}
	
	public void ConwayExtendedRange14(int xx, int yy, int zz){

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
		
		
		int sum = nbrCountNotVal(xx,yy,zz,0);
		if(sum <= 7)  { u.universe[xx][yy][zz] = 0; }
		if(sum == 5)  { u.universe[xx][yy][zz] = 1; }
		if(sum > 99)  { u.universe[xx][yy][zz] = 0; }
	}
	
	public void warts(int xx, int yy, int zz){

		n = new neighbours(4);
		
		n.setNBH(0, -1, 0, 0);
		n.setNBH(0, 1, 0, 1);
		n.setNBH(-1, 0, 0, 2);
		n.setNBH(1, 0, 0, 3);
		
		int isOne = nbrCountNotVal(xx,yy,zz,0);
		
		if(isOne > 0) {
			if(isOne <= 1)  { u.universe[xx][yy][zz] = 0; }
			if(isOne == 2)  { u.universe[xx][yy][zz] = 1; }
			if(isOne >= 3)  { u.universe[xx][yy][zz] = 0; }
		}
		
	}
	
	public void Threads(int xx, int yy, int zz){
			n = new neighbours(4);
			
			n.setNBH(1, -1, 0, 0);
			n.setNBH(-1, -1, 0, 1);
			n.setNBH(0, -1, 0, 2);
			n.setNBH(0, -2, 0, 3);
			
			int isOne = nbrCountNotVal(xx,yy,zz,0);
			
			if(isOne > 0) {
				if(isOne <= 1)  { u.universe[xx][yy][zz] = 0; }
				if(isOne == 2)  { u.universe[xx][yy][zz] = 1; }
				if(isOne >= 3)  { u.universe[xx][yy][zz] = 0; }
			}
	}
	
	public void Inverse110(int xx, int yy, int zz){
		n = new neighbours(2);
		
		n.setNBH(0, -1, 0, 0);
		n.setNBH(-1, 0, 0, 1);
		
		int isOne = nbrCountNotVal(xx,yy,zz,0);
		
		if(isOne > 0) {
			//if(isOne == 0)  { u.universe[xx][yy][zz] = 0; }
			if(isOne == 1)  { u.universe[xx][yy][zz] = 1; }
			if(isOne == 2)  { u.universe[xx][yy][zz] = 0; }
		}
		
		
	}
	
	public void Inverse110_2(int xx, int yy, int zz){
		n = new neighbours(4);
		
		n.setNBH(0, -2, 0, 0);
		n.setNBH(-2, 0, 0, 1);
		n.setNBH(-1, -1, 0, 2);
		n.setNBH(-2, -2, 0, 3);
		
		int isOne = nbrCountNotVal(xx,yy,zz,0);
		
		if(isOne > 0) {
			if(isOne <= 1)  { u.universe[xx][yy][zz] = 0; }
			if(isOne == 2)  { u.universe[xx][yy][zz] = 1; }
			if(isOne >= 3)  { u.universe[xx][yy][zz] = 0; }
		}
	}
	
	public void Inverse110_leopard(int xx, int yy, int zz){
		n = new neighbours(3);
		
		n.setNBH(0, -2, 0, 0);
		n.setNBH(-2, 0, 0, 1);
		n.setNBH(-1, -1, 0, 2);
		//n.setNBH(-2, -2, 0, 3);
		
		int isOne = nbrCountNotVal(xx,yy,zz,0);
		
		if(isOne > 0) {
			if(isOne <= 0)  { u.universe[xx][yy][zz] = 0; }
			if(isOne == 1)  { u.universe[xx][yy][zz] = 1; }
			if(isOne >= 2)  { u.universe[xx][yy][zz] = 0; }
		}

	}

	
	public void actual3D(int xx, int yy, int zz){
		
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
		
		int isOne = nbrCountNotVal(xx,yy,zz,0);

		//if(isOne > 0) {
			if(isOne <= 2) { u.universe[xx][yy][zz] = 0;}
			if(isOne == 5){u.universe[xx][yy][zz] = 1;}
			if(isOne >= 7) { u.universe[xx][yy][zz] = 0;} /*else { u.universe[xx][yy][zz] = 0; }*/
		//}
		//conway(i,j,k,1,0);
	}

	public void rule110(int xx, int yy, int zz){					
		n = new neighbours(3);
		
		//n.setNBH(0, 0, 0, 0);
		n.setNBH(-1, -1, 0, 0);
		n.setNBH(0, -1, 0, 1);
		n.setNBH(1, -1, 0, 2);
		//n.setNBH(0, 0, 0, 3);
		
		int isOne = nbrCountNotVal(xx,yy,zz,0);
		
		//u.universe[xx][yy][zz] = 0;
		
		if(isOne > 0) {
			
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
			
			
		}
		

		//int t = totalistic(xx,yy,zz,1);

		/*if (t == 2){u.universe[xx][yy][zz] = 1;}
		if(t < 2 || t > 4){u.universe[xx][yy][zz] = 0;}*/
		
	}
	
	public void diffusion(int xx, int yy, int zz, int rand, int thresh){
		
		if(u.universe[xx][yy][zz] > thresh) {
			n = new neighbours(6);
			n.setNBH(-1, 0, 0, 0);
			n.setNBH(0, -1, 0, 1);
			n.setNBH(1, 0, 0, 2);
			n.setNBH(0, 1, 0, 3);
			n.setNBH(0, 0, -1, 4);
			n.setNBH(0, 0, 1, 5);
			
	
			for(int i = 0; i < n.NBH.length; i++) {
				u.universe[getWrap(xx, n.NBH[i][0], u.universe.length)][getWrap(yy, n.NBH[i][1], u.universe[0].length)][getWrap(zz, n.NBH[i][2], u.universe[0][0].length)]+=1;
				u.universe[xx][yy][zz]-=1;
			}
		}
		
	}
	
	public void avgVonNew(int xx, int yy, int zz){
		
		n = new neighbours(8);
		n.setNBH(-1, 0, 0, 0);
		n.setNBH(0, -1, 0, 1);
		n.setNBH(1, 0, 0, 2);
		n.setNBH(0, 1, 0, 3);
		n.setNBH(-1, 1, 0, 4);
		n.setNBH(1, -1, 0, 5);
		n.setNBH(-1, -1, 0, 6);
		n.setNBH(1, 1, 0, 7);
		
		u.universe[xx][yy][zz]=nbrAvg(xx,yy,zz);
	
	}
	
	public void fractalCount(int xx, int yy, int zz){
		
		n = new neighbours(4);
		n.setNBH(-1, 0, 0, 0);
		n.setNBH(0, -1, 0, 1);
		n.setNBH(1, 0, 0, 2);
		n.setNBH(0, 1, 0, 3);
		
		int sum = 0;
		for(int i = 0; i < n.NBH.length; i++){
			sum += getNbrCounts(getWrap(xx, n.NBH[i][0], u.universe.length), getWrap(yy, n.NBH[i][1], u.universe[0].length), getWrap(zz, n.NBH[i][2], u.universe[0][0].length));
		}
		
		if(sum > 0) {
			if(sum <= 0)  { u.universe[xx][yy][zz] = 0; }
			if(sum == 1)  { u.universe[xx][yy][zz] = 1; }
			if(sum >= 2)  { u.universe[xx][yy][zz] = 0; }
		}
	}

	public void fractalGun(int xx, int yy, int zz){
		
		n = new neighbours(4);
		n.setNBH(-1, 0, 0, 0);
		n.setNBH(0, -1, 0, 1);
		n.setNBH(1, 0, 0, 2);
		n.setNBH(0, 1, 0, 3);
		
		int sum = 0;
		for(int i = 0; i < n.NBH.length; i++){
			sum += getNbrCounts(getWrap(xx, n.NBH[i][0], u.universe.length), getWrap(yy, n.NBH[i][1], u.universe[0].length), getWrap(zz, n.NBH[i][2], u.universe[0][0].length));
		}
		
		
		if(sum <= 2)  { u.universe[xx][yy][zz] = 0; }
		if(sum == 3)  { u.universe[xx][yy][zz] = 1; }
		if(sum >= 9)  { u.universe[xx][yy][zz] = 0; }
		
	}
	
	public void fractalPhase(int xx, int yy, int zz){
		
		n = new neighbours(4);
		n.setNBH(-1, 0, 0, 0);
		n.setNBH(0, -1, 0, 1);
		n.setNBH(1, 0, 0, 2);
		n.setNBH(0, 1, 0, 3);
		
		int sum = 0;
		for(int i = 0; i < n.NBH.length; i++){
			sum += getNbrCounts(getWrap(xx, n.NBH[i][0], u.universe.length), getWrap(yy, n.NBH[i][1], u.universe[0].length), getWrap(zz, n.NBH[i][2], u.universe[0][0].length));
		}
		
		
		if(sum <= 3)  { u.universe[xx][yy][zz] = 0; }
		if(sum == 4)  { u.universe[xx][yy][zz] = 1; }
		if(sum >= 11)  { u.universe[xx][yy][zz] = 0; }
		
	}
	
	public void fractalChyp(int xx, int yy, int zz){
		
		n = new neighbours(4);
		n.setNBH(-1, 0, 0, 0);
		n.setNBH(0, -1, 0, 1);
		n.setNBH(1, 0, 0, 2);
		n.setNBH(0, 1, 0, 3);
		
		int sum = 0;
		for(int i = 0; i < n.NBH.length; i++){
			sum += getNbrCounts(getWrap(xx, n.NBH[i][0], u.universe.length), getWrap(yy, n.NBH[i][1], u.universe[0].length), getWrap(zz, n.NBH[i][2], u.universe[0][0].length));
		}
		
		
		if(sum <= 3)  { u.universe[xx][yy][zz] = 0; }
		if(sum == 5)  { u.universe[xx][yy][zz] = 1; }
		if(sum >= 12)  { u.universe[xx][yy][zz] = 0; }
		
	}
	
	public void fractalShip(int xx, int yy, int zz){
		
		n = new neighbours(4);
		n.setNBH(-1, 0, 0, 0);
		n.setNBH(0, -1, 0, 1);
		n.setNBH(1, 0, 0, 2);
		n.setNBH(0, 1, 0, 3);
		
		int sum = 0;
		for(int i = 0; i < n.NBH.length; i++){
			sum += getNbrCounts(getWrap(xx, n.NBH[i][0], u.universe.length), getWrap(yy, n.NBH[i][1], u.universe[0].length), getWrap(zz, n.NBH[i][2], u.universe[0][0].length));
		}
		
		
		if(sum <= 4)  { u.universe[xx][yy][zz] = 0; }
		if(sum == 5)  { u.universe[xx][yy][zz] = 1; }
		if(sum >= 11)  { u.universe[xx][yy][zz] = 0; }
		
	}
	
	public void fractal1D(int xx, int yy, int zz){
		
		n = new neighbours(3);
		n.setNBH(-1, -1, 0, 0);
		n.setNBH(0, -1, 0, 1);
		n.setNBH(1, -1, 0, 2);
		
		int sum = 0;
		for(int i = 0; i < n.NBH.length; i++){
			sum += getNbrCounts(getWrap(xx, n.NBH[i][0], u.universe.length), getWrap(yy, n.NBH[i][1], u.universe[0].length), getWrap(zz, n.NBH[i][2], u.universe[0][0].length));
		}
		
		//if(sum > 0) {
		if(sum <= 1)  { u.universe[xx][yy][zz] = 0; }
		if(sum == 3)  { u.universe[xx][yy][zz] = 1; }
		if(sum >= 5)  { u.universe[xx][yy][zz] = 0; }
		//}
		
	}
	
	public void fractal1D2(int xx, int yy, int zz){
			
			n = new neighbours(3);
			n.setNBH(-1, -1, 0, 0);
			n.setNBH(0, -1, 0, 1);
			n.setNBH(1, -1, 0, 2);
			
			int sum = 0;
			for(int i = 0; i < n.NBH.length; i++){
				sum += getNbrCounts(getWrap(xx, n.NBH[i][0], u.universe.length), getWrap(yy, n.NBH[i][1], u.universe[0].length), getWrap(zz, n.NBH[i][2], u.universe[0][0].length));
			}
			
			//if(sum > 0) {
			if(sum <= 1)  { u.universe[xx][yy][zz] = 0; }
			if(sum == 3)  { u.universe[xx][yy][zz] = 1; }
			if(sum >= 9)  { u.universe[xx][yy][zz] = 0; }
			//}
			
		}
	
	public void fractal1D3(int xx, int yy, int zz){
		
		n = new neighbours(3);
		n.setNBH(-1, 0, 0, 0);
		n.setNBH(0, -1, 0, 1);
		n.setNBH(1, 0, 0, 2);
		
		int sum = 0;
		for(int i = 0; i < n.NBH.length; i++){
			sum += getNbrCounts(getWrap(xx, n.NBH[i][0], u.universe.length), getWrap(yy, n.NBH[i][1], u.universe[0].length), getWrap(zz, n.NBH[i][2], u.universe[0][0].length));
		}
		
		//if(sum > 0) {
		if(sum <= 2)  { u.universe[xx][yy][zz] = 0; }
		if(sum == 3)  { u.universe[xx][yy][zz] = 1; }
		if(sum >= 4)  { u.universe[xx][yy][zz] = 0; }
		//}
		
		
	}
	
public void fractal1D4(int xx, int yy, int zz){
		
		n = new neighbours(3);
		n.setNBH(-1, 0, 0, 0);
		n.setNBH(0, -1, 0, 1);
		n.setNBH(1, 0, 0, 2);
		
		int sum = 0;
		for(int i = 0; i < n.NBH.length; i++){
			sum += getNbrCounts(getWrap(xx, n.NBH[i][0], u.universe.length), getWrap(yy, n.NBH[i][1], u.universe[0].length), getWrap(zz, n.NBH[i][2], u.universe[0][0].length));
		}
		
		//if(sum > 0) {
		if(sum <= 1)  { u.universe[xx][yy][zz] = 0; }
		if(sum == 3)  { u.universe[xx][yy][zz] = 1; }
		if(sum >= 7)  { u.universe[xx][yy][zz] = 0; }
		//}
		
		
	}

	public void fractal1D5(int xx, int yy, int zz){
		
		n = new neighbours(3);
		n.setNBH(-1, -1, 0, 0);
		n.setNBH(0, 1, 0, 1);
		n.setNBH(1, -1, 0, 2);
		
		int sum = 0;
		for(int i = 0; i < n.NBH.length; i++){
			sum += getNbrCounts(getWrap(xx, n.NBH[i][0], u.universe.length), getWrap(yy, n.NBH[i][1], u.universe[0].length), getWrap(zz, n.NBH[i][2], u.universe[0][0].length));
		}
		
		//if(sum > 0) {
		if(sum <= 2)  { u.universe[xx][yy][zz] = 0; }
		if(sum == 2)  { u.universe[xx][yy][zz] = 1; }
		if(sum >= 7)  { u.universe[xx][yy][zz] = 0; }
		//}
		
		
	}
	
	public void fractal1D6(int xx, int yy, int zz){
		
		n = new neighbours(3);
		n.setNBH(-1, -1, 0, 0);
		n.setNBH(0, 1, 0, 1);
		n.setNBH(1, -1, 0, 2);
		
		int sum = 0;
		for(int i = 0; i < n.NBH.length; i++){
			sum += getNbrCounts(getWrap(xx, n.NBH[i][0], u.universe.length), getWrap(yy, n.NBH[i][1], u.universe[0].length), getWrap(zz, n.NBH[i][2], u.universe[0][0].length));
		}
		
		//if(sum > 0) {
		if(sum <= 1)  { u.universe[xx][yy][zz] = 0; }
		if(sum == 3)  { u.universe[xx][yy][zz] = 1; }
		if(sum >= 6)  { u.universe[xx][yy][zz] = 0; }
		//}
		
		
	}
	
public void fractalTime(int xx, int yy, int zz){
		
		n = new neighbours(8);
		n.setNBH(-1, -1, 0, 0);
		n.setNBH( 0, -1, 0, 1);	
		n.setNBH( 1, -1, 0, 2);
		n.setNBH(-1, 0, 0, 3);	
		n.setNBH( 1, 0, 0, 4);
		n.setNBH(-1, 1, 0, 5);	
		n.setNBH(0, 1, 0, 6);
		n.setNBH(1, 1, 0, 7);
		
		int sum = 0;
		for(int i = 0; i < n.NBH.length; i++){
			sum += getNbrCounts(getWrap(xx, n.NBH[i][0], u.universe.length), getWrap(yy, n.NBH[i][1], u.universe[0].length), getWrap(zz, n.NBH[i][2], u.universe[0][0].length));
		}
		
		//if(sum > 0) {
		if(sum <= 30)  { u.universe[xx][yy][zz] = 0; }
		if(sum == 6)  { u.universe[xx][yy][zz] = 1; }
		if(sum >= 99)  { u.universe[xx][yy][zz] = 0; }
		//}
		
		
	}

public void fractalMetacell(int xx, int yy, int zz){
	
	n = new neighbours(8);
	n.setNBH(-1, -1, 0, 0);
	n.setNBH( 0, -1, 0, 1);	
	n.setNBH( 1, -1, 0, 2);
	n.setNBH(-1, 0, 0, 3);	
	n.setNBH( 1, 0, 0, 4);
	n.setNBH(-1, 1, 0, 5);	
	n.setNBH(0, 1, 0, 6);
	n.setNBH(1, 1, 0, 7);
	
	int sum = 0;
	for(int i = 0; i < n.NBH.length; i++){
		sum += getNbrCounts(getWrap(xx, n.NBH[i][0], u.universe.length), getWrap(yy, n.NBH[i][1], u.universe[0].length), getWrap(zz, n.NBH[i][2], u.universe[0][0].length));
	}
	
	//if(sum > 0) {
	if(sum <= 33)  { u.universe[xx][yy][zz] = 0; }
	if(sum == 6)  { u.universe[xx][yy][zz] = 1; }
	if(sum >= 99)  { u.universe[xx][yy][zz] = 0; }
	//}
	
	
}

public void fractalMetacell2(int xx, int yy, int zz){
	
	n = new neighbours(8);
	n.setNBH(-1, -1, 0, 0);
	n.setNBH( 0, -1, 0, 1);	
	n.setNBH( 1, -1, 0, 2);
	n.setNBH(-1, 0, 0, 3);	
	n.setNBH( 1, 0, 0, 4);
	n.setNBH(-1, 1, 0, 5);	
	n.setNBH(0, 1, 0, 6);
	n.setNBH(1, 1, 0, 7);
	
	int sum = 0;
	for(int i = 0; i < n.NBH.length; i++){
		sum += getNbrCounts(getWrap(xx, n.NBH[i][0], u.universe.length), getWrap(yy, n.NBH[i][1], u.universe[0].length), getWrap(zz, n.NBH[i][2], u.universe[0][0].length));
	}
	
	//if(sum > 0) {
	if(sum <= 15)  { u.universe[xx][yy][zz] = 0; }
	if(sum == 9)  { u.universe[xx][yy][zz] = 1; }
	if(sum >= 26)  { u.universe[xx][yy][zz] = 0; }
	//}
	
	
}

public void fractalMetacell3(int xx, int yy, int zz){
	
	n = new neighbours(8);
	n.setNBH(-1, -1, 0, 0);
	n.setNBH( 0, -1, 0, 1);	
	n.setNBH( 1, -1, 0, 2);
	n.setNBH(-1, 0, 0, 3);	
	n.setNBH( 1, 0, 0, 4);
	n.setNBH(-1, 1, 0, 5);	
	n.setNBH(0, 1, 0, 6);
	n.setNBH(1, 1, 0, 7);
	
	int sum = 0;
	for(int i = 0; i < n.NBH.length; i++){
		sum += getNbrCounts(getWrap(xx, n.NBH[i][0], u.universe.length), getWrap(yy, n.NBH[i][1], u.universe[0].length), getWrap(zz, n.NBH[i][2], u.universe[0][0].length));
	}
	
	//if(sum > 0) {
	if(sum <= 16)  { u.universe[xx][yy][zz] = 0; }
	if(sum == 9)  { u.universe[xx][yy][zz] = 1; }
	if(sum >= 26)  { u.universe[xx][yy][zz] = 0; }
	//}
	
	
}

public void fractalMetacell4(int xx, int yy, int zz){
	
	n = new neighbours(8);
	n.setNBH(-1, -1, 0, 0);
	n.setNBH( 0, -1, 0, 1);	
	n.setNBH( 1, -1, 0, 2);
	n.setNBH(-1, 0, 0, 3);	
	n.setNBH( 1, 0, 0, 4);
	n.setNBH(-1, 1, 0, 5);	
	n.setNBH(0, 1, 0, 6);
	n.setNBH(1, 1, 0, 7);
	
	int sum = 0;
	for(int i = 0; i < n.NBH.length; i++){
		sum += getNbrCounts(getWrap(xx, n.NBH[i][0], u.universe.length), getWrap(yy, n.NBH[i][1], u.universe[0].length), getWrap(zz, n.NBH[i][2], u.universe[0][0].length));
	}
	
	//if(sum > 0) {
	if(sum <= 10)  { u.universe[xx][yy][zz] = 0; }
	if(sum == 5)  { u.universe[xx][yy][zz] = 1; }
	if(sum >= 22)  { u.universe[xx][yy][zz] = 0; }
	//}
	
	
}

public void fractalMetacell5(int xx, int yy, int zz){
	
	n = new neighbours(8);
	n.setNBH(-1, -1, 0, 0);
	n.setNBH( 0, -1, 0, 1);	
	n.setNBH( 1, -1, 0, 2);
	n.setNBH(-1, 0, 0, 3);	
	n.setNBH( 1, 0, 0, 4);
	n.setNBH(-1, 1, 0, 5);	
	n.setNBH(0, 1, 0, 6);
	n.setNBH(1, 1, 0, 7);
	
	int sum = 0;
	for(int i = 0; i < n.NBH.length; i++){
		sum += getNbrCounts(getWrap(xx, n.NBH[i][0], u.universe.length), getWrap(yy, n.NBH[i][1], u.universe[0].length), getWrap(zz, n.NBH[i][2], u.universe[0][0].length));
	}
	
	//if(sum > 0) {
	if(sum <= 16)  { u.universe[xx][yy][zz] = 0; }
	if(sum == 8)  { u.universe[xx][yy][zz] = 1; }
	if(sum >= 26)  { u.universe[xx][yy][zz] = 0; }
	//}
	
	
}

public void fractalMetacell5_2(int xx, int yy, int zz){
	
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
	
	int sum = 0;
	for(int i = 0; i < n.NBH.length; i++){
		sum += getNbrCounts(getWrap(xx, n.NBH[i][0], u.universe.length), getWrap(yy, n.NBH[i][1], u.universe[0].length), getWrap(zz, n.NBH[i][2], u.universe[0][0].length));
	}
	
	/*if(sum > 0 && sum <= 135)  { u.universe[xx][yy][zz] = 0; }
	if(sum >= 74 && sum <= 85)  { u.universe[xx][yy][zz] = 1; }
	if(sum >= 165 && sum < 180)  { u.universe[xx][yy][zz] = 0; }*/

	/*if(sum >= 0 && sum <= 48)  { u.universe[xx][yy][zz] = 0; }
	if(sum >= 55 && sum <= 56)  { u.universe[xx][yy][zz] = 1; }
	if(sum >= 65 && sum <= 69)  { u.universe[xx][yy][zz] = 0; }
	if(sum >= 76 && sum <= 78)  { u.universe[xx][yy][zz] = 1; }
	if(sum >= 100 && sum <= 999)  { u.universe[xx][yy][zz] = 0; }*/

	
	if(sum > 0)  				{ u.universe[xx][yy][zz] = 0; }
	if(sum >= 140 && sum <= 230)  { u.universe[xx][yy][zz] = 1; }
	
	//======================
	//if(sum == 1)  	{ u.universe[xx][yy][zz] = 1; } //dots;					vonn dot-replication mandala growth
	//if(sum == 2)  	{ u.universe[xx][yy][zz] = 1; } //dots;					square dot-replication mandala growth
	//if(sum == 3)  	{ u.universe[xx][yy][zz] = 1; } //dots;					octagonal dot-replication mandala growth
	//if(sum == 4)  	{ u.universe[xx][yy][zz] = 1; } //dots, lines, 			circular mandala growth
	//if(sum == 5)  	{ u.universe[xx][yy][zz] = 1; } //dots;					vonn dot-replication mandala growth
	//if(sum == 8)  	{ u.universe[xx][yy][zz] = 1; } //dots, lines; 			mandala growth
	//if(sum == 9)  	{ u.universe[xx][yy][zz] = 1; } //dots, lines;			replicators
	//if(sum == 11)  	{ u.universe[xx][yy][zz] = 1; } //dots, 				replicators
	//if(sum == 12)  	{ u.universe[xx][yy][zz] = 1; } //dots, lines;			blooms, repeaters, replicators
	//if(sum == 13)  	{ u.universe[xx][yy][zz] = 1; } //dots, 				repeaters
	//if(sum == 15)  	{ u.universe[xx][yy][zz] = 1; } //dots, 				replicators
	//if(sum == 16)  	{ u.universe[xx][yy][zz] = 1; } //dots, 				blooms, replicators
	//if(sum == 24)  	{ u.universe[xx][yy][zz] = 1; } //dots;					blooms, still life
	//if(sum == 30)  	{ u.universe[xx][yy][zz] = 1; } //dots;					still life
	//if(sum == 31)  	{ u.universe[xx][yy][zz] = 1; } //dots;					repeaters
	//if(sum == 32)  	{ u.universe[xx][yy][zz] = 1; } //dots, solids;			still life, replicators
	//======================
	
	//======================
	//if(sum >= 39 && sum <= 43)  	{ u.universe[xx][yy][zz] = 1; } //dots, lines; 			blooms, still life, gliders
	//if(sum >= 44 && sum <= 50)  	{ u.universe[xx][yy][zz] = 1; } //dots, lines; 			blooms, still life, gliders
	//if(sum >= 51 && sum <= 59)  	{ u.universe[xx][yy][zz] = 1; } //dots, lines, solids; 	blooms, still life, repeaters
	//if(sum >= 60 && sum <= 73)  	{ u.universe[xx][yy][zz] = 1; } //dots, lines; 			blooms, still life
	//if(sum >= 74 && sum <= 96)  	{ u.universe[xx][yy][zz] = 1; } //lines, solids; 		blooms, still life
	//if(sum >= 97 && sum <= 139)  	{ u.universe[xx][yy][zz] = 1; } //lines, solids; 		blooms, still life
	//if(sum >= 140 && sum <= 230)  { u.universe[xx][yy][zz] = 1; } //solids; 				still life, repeaters, gliders
	//if(sum >= 230 && sum <= 475)  { u.universe[xx][yy][zz] = 1; } //solids; 				repeaters, explosive growth
	//======================
	//if(sum >= 288)  				{ u.universe[xx][yy][zz] = 1; } //All growth values above 288, exactly 50% of the growth rulespace causes a solid perfect 50/50 split when seeded with rand(2). Strangely, 288 = (24^2)/2 (where 24^2 represents a period-2 fractal neighbourhood)
	//======================
	
}

public void fractalMetacell6(int xx, int yy, int zz){
	
	n = new neighbours(8);
	n.setNBH(-1, -1, 0, 0);
	n.setNBH( 0, -1, 0, 1);	
	n.setNBH( 1, -1, 0, 2);
	n.setNBH(-1, 0, 0, 3);	
	n.setNBH( 1, 0, 0, 4);
	n.setNBH(-1, 1, 0, 5);	
	n.setNBH(0, 1, 0, 6);
	n.setNBH(1, 1, 0, 7);
	
	int sum = 0;
	for(int i = 0; i < n.NBH.length; i++){
		sum += getNbrCounts(getWrap(xx, n.NBH[i][0], u.universe.length), getWrap(yy, n.NBH[i][1], u.universe[0].length), getWrap(zz, n.NBH[i][2], u.universe[0][0].length));
	}
	
	//if(sum > 0) {
	if(sum <= 0)  { u.universe[xx][yy][zz] = 0; }
	if(sum == 4)  { u.universe[xx][yy][zz] = 1; }
	if(sum >= 99)  { u.universe[xx][yy][zz] = 0; }
	//}
	
	
}

public void fractal1(int xx, int yy, int zz){
	
	n = new neighbours(6);
	n.setNBH(0, -1, 0, 0);
	n.setNBH(-1, 0, 0, 1);	
	n.setNBH(0, 1, 0, 2);
	n.setNBH(1, 0, 0, 3);	
	n.setNBH(-1, -1, 0, 4);
	n.setNBH(1, 1, 0, 5);
	
	int sum = 0;
	for(int i = 0; i < n.NBH.length; i++){
		sum += getNbrCounts(getWrap(xx, n.NBH[i][0], u.universe.length), getWrap(yy, n.NBH[i][1], u.universe[0].length), getWrap(zz, n.NBH[i][2], u.universe[0][0].length));
	}
	
	if(sum <= 99)  { u.universe[xx][yy][zz] = 0; }
	if(sum > 0 && sum < 4)  { u.universe[xx][yy][zz] = 1; }
	
	
}

public void hex1(int xx, int yy, int zz){
	
	n = new neighbours(6);
	n.setNBH(0, -1, 0, 0);
	n.setNBH(-1, 0, 0, 1);	
	n.setNBH(0, 1, 0, 2);
	n.setNBH(1, 0, 0, 3);	
	n.setNBH(-1, -1, 0, 4);
	n.setNBH(1, 1, 0, 5);
	
	int sum = nbrCountNotVal(xx, yy, zz, 0);
	
	if(sum <= 1)  { u.universe[xx][yy][zz] = 0; }
	if(sum >= 3)  { u.universe[xx][yy][zz] = 1; }
	if(sum >= 5)  { u.universe[xx][yy][zz] = 0; }
	
	
}


	public void avgRand(int xx, int yy, int zz, int rand){
		
			n = new neighbours(8);
			n.setNBH(-1, 0, 0, 0);
			n.setNBH(0, -1, 0, 1);
			n.setNBH(1, 0, 0, 2);
			n.setNBH(0, 1, 0, 3);
			n.setNBH(-1, 1, 0, 4);
			n.setNBH(1, -1, 0, 5);
			n.setNBH(-1, -1, 0, 6);
			n.setNBH(1, 1, 0, 7);
			
			if(r.nextInt(rand) == 0) {u.universe[xx][yy][zz]=nbrAvg(xx,yy,zz);}
		
	}
	
	
	public void VonnFractal(int xx, int yy, int zz){
		
		n = new neighbours(6);
		n.setNBH(-1, 0, 0, 0);
		n.setNBH( 1, 0, 0, 1);	
		n.setNBH( 0,-1, 0, 2);
		n.setNBH( 0, 1, 0, 3);	
		n.setNBH( 0, 0,-1, 4);
		n.setNBH( 0, 0, 1, 5);
		
		int sum = 0;
		for(int i = 0; i < n.NBH.length; i++){
			sum += getNbrCounts(getWrap(xx, n.NBH[i][0], u.universe.length), getWrap(yy, n.NBH[i][1], u.universe[0].length), getWrap(zz, n.NBH[i][2], u.universe[0][0].length));
		}
		
		if(sum <= 6*6)  { u.universe[xx][yy][zz] = 0; }
		if(sum >= 8 && sum <= 10 || (sum == 12) || (sum >= 25))  { u.universe[xx][yy][zz] = 1; } //{5,8}{5,9,13}{5,11,12,:3}{6,7,18,:11}{7,8,10,:7}{5,10,13,16,:10}{5,10,13,16,18,:13}{5,11,12,15,:10}{}{}

		
		
		
		//if(sum >= 9 && sum <= 13)  { u.universe[xx][yy][zz] = 1; }
		//if(sum >= 6 && sum <= 6)  { u.universe[xx][yy][zz] = 1; }
		//if(sum >= /*17*/ /*18*/ 19 /*20*/)  { u.universe[xx][yy][zz] = 1; }
		
		
	}
	
	public void MooreFractal(int xx, int yy, int zz){
		
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
		
		int sum = 0;
		for(int i = 0; i < n.NBH.length; i++){
			sum += getNbrCounts(getWrap(xx, n.NBH[i][0], u.universe.length), getWrap(yy, n.NBH[i][1], u.universe[0].length), getWrap(zz, n.NBH[i][2], u.universe[0][0].length));
		}
		
		if(sum <= 14*14*2)  { u.universe[xx][yy][zz] = 0; }
		if(sum <= 128 && sum > 0)  { 
			if(sum % 11 == 0){u.universe[xx][yy][zz] = 1;}else{u.universe[xx][yy][zz] = 0;}
		}
	}
	
		public void MooreFractalColour(int xx, int yy, int zz){
			
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
			
			int sum = 0;
			for(int i = 0; i < n.NBH.length; i++){
				sum += getNbrCounts(getWrap(xx, n.NBH[i][0], u.universe.length), getWrap(yy, n.NBH[i][1], u.universe[0].length), getWrap(zz, n.NBH[i][2], u.universe[0][0].length));
			}
			
			if(sum <= 14*14*2)  { u.universe[xx][yy][zz] = 0; }
			if(sum <= 47 && sum > 0)  { 
				
				if(sum % 2 == 0){u.universe[xx][yy][zz] = 1;}
				if(sum % 3 == 0){u.universe[xx][yy][zz] = 2;}
				if(sum % 5 == 0){u.universe[xx][yy][zz] = 3;}
				if(sum % 7 == 0){u.universe[xx][yy][zz] = 4;}
				if(sum % 11 == 0){u.universe[xx][yy][zz] = 5;}
				if(sum % 13 == 0){u.universe[xx][yy][zz] = 6;}
				if(sum % 17 == 0){u.universe[xx][yy][zz] = 7;}
				if(sum % 19 == 0){u.universe[xx][yy][zz] = 8;}
				if(sum % 23 == 0){u.universe[xx][yy][zz] = 9;}
				if(sum % 29 == 0){u.universe[xx][yy][zz] = 10;}
				if(sum % 31 == 0){u.universe[xx][yy][zz] = 11;}
				if(sum % 37 == 0){u.universe[xx][yy][zz] = 12;}
				if(sum % 41 == 0){u.universe[xx][yy][zz] = 13;}
				if(sum % 43 == 0){u.universe[xx][yy][zz] = 14;}
				if(sum % 47 == 0){u.universe[xx][yy][zz] = 15;}
			}
		
		//if(sum >= 9 && sum <= 13)  { u.universe[xx][yy][zz] = 1; }
		//if(sum >= 6 && sum <= 6)  { u.universe[xx][yy][zz] = 1; }
		//if(sum >= /*17*/ /*18*/ 19 /*20*/)  { u.universe[xx][yy][zz] = 1; }
		
		
	}
	
		public void mapPrev(int xx, int yy, int zz, int val){
			
			n = new neighbours(1);
			n.setNBH(0, 0, -1, 0);
			
			int sum = 0;
			sum = u.snapshotUniverse[getWrap(xx, n.NBH[0][0], u.universe.length)][getWrap(yy, n.NBH[0][1], u.universe[0].length)][getWrap(zz, n.NBH[0][2], u.universe[0][0].length)];
			
			if(sum == 1) {u.universe[xx][yy][zz]+=val;}
		}
	
	 
		
		public void LangtonsAnt(int xx, int yy, int zz){
			n = new neighbours(4);
			n.setNBH(0, -1, 0, 0);
			n.setNBH(1, 0, 0, 1);
			n.setNBH(0, 1, 0, 2);
			n.setNBH(-1, 0, 0, 3);
			
			if(u.snapshotUniverse[xx][yy][zz] < 0) {
				
				int block_memory = -1;
				int newDir = -1;
				int newSelf;

				//Get my true value (0-7):
				int direction_to_go = (Math.abs(u.snapshotUniverse[xx][yy][zz])-1)%8;
				
				//This cell was x before I was here
				if(direction_to_go >= 0 && direction_to_go < 4) 	{block_memory = 0;}
				if(direction_to_go >= 4 && direction_to_go < 8)		{block_memory = 1;}
				
				//My predecessor went direction (0-3):
				int dir = direction_to_go%4;
				
				//Set my future direction (Rotate)
				if(block_memory == 0) {		newDir = (dir+1)%4;			}
				if(block_memory == 1) {		newDir = ((dir+3)%4);		}
					
				//Flip this block's value
				if(block_memory == 0) {u.universe[xx][yy][zz] = 1;} //lows trail 1s
				if(block_memory == 1) {u.universe[xx][yy][zz] = 0;} //highs trail 0s
					
				//encode nbr's value & direction				
				//0-3 encodes 0; 4-7 encodes 1;
				int nbr = u.snapshotUniverse[getWrap(xx, n.NBH[newDir][0], u.universe.length)][getWrap(yy, n.NBH[newDir][1], u.universe[0].length)][getWrap(zz, n.NBH[newDir][2], u.universe[0][0].length)];

				if(nbr == 1) {newSelf = 4;} else {newSelf = 0;}
					
					
				//add direction info & make negative
				newSelf += newDir;
				newSelf = -newSelf;
					
				//set my new direction's neighbour to my encoded data 
				u.universe[getWrap(xx, n.NBH[newDir][0], u.universe.length)][getWrap(yy, n.NBH[newDir][1], u.universe[0].length)][getWrap(zz, n.NBH[newDir][2], u.universe[0][0].length)] = newSelf-1;
			
			}
		}
		
		public void TuringCliff(int xx, int yy, int zz){
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
			
			if(u.snapshotUniverse[xx][yy][zz] < 0) {
				
				int block_memory = -1;
				int newDir = -1;
				int newSelf;
				int nbrsTtl = n.NBH.length;
				
				//Get my true value (0-7):
				int direction_to_go = (Math.abs(u.snapshotUniverse[xx][yy][zz])-1)%(nbrsTtl*2);
				
				//This cell was x before I was here
				if(direction_to_go >= 0 && direction_to_go < nbrsTtl) 	  		{block_memory = 0;}
				if(direction_to_go >= nbrsTtl && direction_to_go < nbrsTtl*2)	{block_memory = 1;}
				
				//My predecessor went direction (0-3):
				int dir = direction_to_go%nbrsTtl;
				
				//Set my future direction (Rotate)
				if(block_memory == 0) {		newDir = (dir+1)%nbrsTtl;			}
				if(block_memory == 1) {		newDir = ((dir+nbrsTtl)%nbrsTtl);		}
					
				//Flip this block's value
				int isOne = nbrCountMoreThanVal(xx,yy,zz,0);
				if(isOne >= min_grow_die[0][0] && isOne <= min_grow_die[0][1]/* && isOne <= min_grow_die[0][3]*/)  { u.universe[xx][yy][zz] = 0; }
				if(isOne >= min_grow_die[0][2] && isOne <= min_grow_die[0][3]/* && isOne <= min_grow_die[0][4]*/)  { u.universe[xx][yy][zz] = 1; }
				if(isOne >= min_grow_die[0][4] && isOne <= min_grow_die[0][5]/* && isOne <= min_grow_die[0][5]*/)  { u.universe[xx][yy][zz] = 0; }
				
				//encode nbr's value & direction				
				//0-3 encodes 0; 4-7 encodes 1;
				int nbr = u.snapshotUniverse[getWrap(xx, n.NBH[newDir][0], u.universe.length)][getWrap(yy, n.NBH[newDir][1], u.universe[0].length)][getWrap(zz, n.NBH[newDir][2], u.universe[0][0].length)];

				if(nbr == 1) {newSelf = nbrsTtl;} else {newSelf = 0;}
					
					
				//add direction info & make negative
				newSelf += newDir;
				newSelf = -newSelf;
					
				//set my new direction's neighbour to my encoded data 
				u.universe[getWrap(xx, n.NBH[newDir][0], u.universe.length)][getWrap(yy, n.NBH[newDir][1], u.universe[0].length)][getWrap(zz, n.NBH[newDir][2], u.universe[0][0].length)] = newSelf-1;
			
			}
		}
		
		
		public void CyclicDaemon(int xx, int yy, int zz){
			n = new neighbours(4);
				
			n.setNBH( 0, -1, 0, 0);
			n.setNBH( -1, 0, 0, 1);
			n.setNBH( 1, 0, 0, 2);
			n.setNBH( 0, 1, 0, 3);

				
			int found = 0;
			int cyc = 12;
				
			for(int i = 0; i < n.NBH.length; i++) {
				if(u.snapshotUniverse[xx][yy][zz]%cyc == (u.snapshotUniverse[getWrap(xx, n.NBH[i][0], u.universe.length)][getWrap(yy, n.NBH[i][1], u.universe[0].length)][getWrap(zz, n.NBH[i][2], u.universe[0][0].length)]+(cyc-1))%cyc) {
					found++;
				}
			}
				
			if(found != 0) {
				u.universe[xx][yy][zz]=(u.universe[xx][yy][zz]+1)%cyc;
			} 
				
		}        
			
	
		public void traffic2(int xx, int yy, int zz){
			n = new neighbours(2);
			n.setNBH( 0, -1, 0, 0);
			n.setNBH( -1, 0, 0, 1);
			
			
			
			/*if(u.snapshotUniverse[xx][yy][zz] == 2) {
				u.universe[xx][yy][zz] = -2;
			}
		
			if(u.snapshotUniverse[getWrap(xx, n.NBH[1][0], u.universe.length)][getWrap(yy, n.NBH[1][1], u.universe[0].length)][getWrap(zz, n.NBH[1][2], u.universe[0][0].length)] == 2) {
				if(u.snapshotUniverse[xx][yy][zz] == 0) {u.universe[xx][yy][zz] = -2;}
			} else if(u.snapshotUniverse[getWrap(xx, n.NBH[1][0], u.universe.length)][getWrap(yy, n.NBH[1][1], u.universe[0].length)][getWrap(zz, n.NBH[1][2], u.universe[0][0].length)] == 0) {
				u.universe[xx][yy][zz] = 0;
			}
			
			if(u.snapshotUniverse[xx][yy][zz] == -2) {
				u.universe[xx][yy][zz] = 2;
			}/**/
			
			
			
			
			
			if(u.snapshotUniverse[xx][yy][zz] == 1) {
				u.universe[xx][yy][zz] = -1;
			}
			if(u.snapshotUniverse[getWrap(xx, n.NBH[0][0], u.universe.length)][getWrap(yy, n.NBH[0][1], u.universe[0].length)][getWrap(zz, n.NBH[0][2], u.universe[0][0].length)] == 1) {
				if(u.snapshotUniverse[xx][yy][zz] == 0) {
					u.universe[xx][yy][zz] = -1;
				}
			}
			if(u.snapshotUniverse[getWrap(xx, n.NBH[0][0], u.universe.length)][getWrap(yy, n.NBH[0][1], u.universe[0].length)][getWrap(zz, n.NBH[0][2], u.universe[0][0].length)] == 0) {
				if(Math.abs(u.snapshotUniverse[xx][yy][zz]) == 1) {
					u.universe[xx][yy][zz] = 0;
				}
			}
			if(u.snapshotUniverse[xx][yy][zz] == -1) {
				u.universe[xx][yy][zz] = 1;
			}/**/
			
			
			if(u.snapshotUniverse[xx][yy][zz] == 2) {
				u.universe[xx][yy][zz] = -2;
			}
			if(u.snapshotUniverse[getWrap(xx, n.NBH[1][0], u.universe.length)][getWrap(yy, n.NBH[1][1], u.universe[0].length)][getWrap(zz, n.NBH[1][2], u.universe[0][0].length)] == 2) {
				if(u.snapshotUniverse[xx][yy][zz] == 0) {
					u.universe[xx][yy][zz] = -2;
				}
			}
			if(u.snapshotUniverse[getWrap(xx, n.NBH[1][0], u.universe.length)][getWrap(yy, n.NBH[1][1], u.universe[0].length)][getWrap(zz, n.NBH[1][2], u.universe[0][0].length)] == 0) {
				if(Math.abs(u.snapshotUniverse[xx][yy][zz]) == 2) {
					u.universe[xx][yy][zz] = 0;
				}
			}
			if(u.snapshotUniverse[xx][yy][zz] == -2) {
				u.universe[xx][yy][zz] = 2;
			}/**/
			
			
				/*if(u.snapshotUniverse[xx][yy][zz] == 1) {
					u.universe[xx][yy][zz] = -1;
				}
			
				if(u.snapshotUniverse[getWrap(xx, n.NBH[0][0], u.universe.length)][getWrap(yy, n.NBH[0][1], u.universe[0].length)][getWrap(zz, n.NBH[0][2], u.universe[0][0].length)] == 1) {
					if(u.snapshotUniverse[xx][yy][zz] == 0) {u.universe[xx][yy][zz] = -1;}
				} else if(u.snapshotUniverse[getWrap(xx, n.NBH[0][0], u.universe.length)][getWrap(yy, n.NBH[0][1], u.universe[0].length)][getWrap(zz, n.NBH[0][2], u.universe[0][0].length)] == 0) {
					u.universe[xx][yy][zz] = 0;
				}
				
				if(u.snapshotUniverse[xx][yy][zz] == -1) {
					u.universe[xx][yy][zz] = 1;
				}*/

		
			/*if(u.snapshotUniverse[getWrap(xx, n.NBH[0][0], u.universe.length)][getWrap(yy, n.NBH[0][1], u.universe[0].length)][getWrap(zz, n.NBH[0][2], u.universe[0][0].length)] == 1) {
				if(u.snapshotUniverse[xx][yy][zz] == 0) {u.universe[xx][yy][zz] = 1;}
			} else if(u.snapshotUniverse[getWrap(xx, n.NBH[0][0], u.universe.length)][getWrap(yy, n.NBH[0][1], u.universe[0].length)][getWrap(zz, n.NBH[0][2], u.universe[0][0].length)] == 0) {
				u.universe[xx][yy][zz] = 0;
			}*/

			
				
		}
		
		public void traffic(int xx, int yy, int zz){
			n = new neighbours(2);
			n.setNBH( 0, -1, 0, 0);
			n.setNBH( -1, 0, 0, 1);
		
			
			if(u.snapshotUniverse[xx][yy][zz] == 1) {
				u.universe[xx][yy][zz] = -1;
			}
			if(u.snapshotUniverse[getWrap(xx, n.NBH[0][0], u.universe.length)][getWrap(yy, n.NBH[0][1], u.universe[0].length)][getWrap(zz, n.NBH[0][2], u.universe[0][0].length)] == 1) {
				if(u.snapshotUniverse[xx][yy][zz] == 0) {
					u.universe[xx][yy][zz] = -1;
				}
			}
			if(u.snapshotUniverse[getWrap(xx, n.NBH[0][0], u.universe.length)][getWrap(yy, n.NBH[0][1], u.universe[0].length)][getWrap(zz, n.NBH[0][2], u.universe[0][0].length)] == 0) {
				if(u.snapshotUniverse[xx][yy][zz] == 1) {
					u.universe[xx][yy][zz] = 0;
				}
			}
			if(u.snapshotUniverse[xx][yy][zz] == -1) {
				u.universe[xx][yy][zz] = 1;
			}/**/
			
			
			if(u.snapshotUniverse[xx][yy][zz] == 2) {
				u.universe[xx][yy][zz] = -2;
			}
			if(u.snapshotUniverse[getWrap(xx, n.NBH[1][0], u.universe.length)][getWrap(yy, n.NBH[1][1], u.universe[0].length)][getWrap(zz, n.NBH[1][2], u.universe[0][0].length)] == 2) {
				if(u.snapshotUniverse[xx][yy][zz] == 0) {
					u.universe[xx][yy][zz] = -2;
				}
			}
			if(u.snapshotUniverse[getWrap(xx, n.NBH[1][0], u.universe.length)][getWrap(yy, n.NBH[1][1], u.universe[0].length)][getWrap(zz, n.NBH[1][2], u.universe[0][0].length)] == 0) {
				if(u.snapshotUniverse[xx][yy][zz] == 2) {
					u.universe[xx][yy][zz] = 0;
				}
			}
			if(u.snapshotUniverse[xx][yy][zz] == -2) {
				u.universe[xx][yy][zz] = 2;
			}/**/
			
			
				
		}
		
		
		public void platform(int xx, int yy, int zz){
			n = new neighbours(9);
			n.setNBH( 0, 0, 0, 0);
			n.setNBH( 1, 0, 0, 1);
			n.setNBH( -1, 0, 0, 2);
			n.setNBH( 0, 1, 0, 3);
			n.setNBH( 0, -1, 0, 4);

			n.setNBH( 1, 0, 0, 5);
			n.setNBH( -1, 0, 0, 6);
			n.setNBH( 0, 1, 0, 7);
			n.setNBH( 0, -1, 0, 8);
			
			if(u.snapshotUniverse[getWrap(xx, n.NBH[0][0], u.universe.length)][getWrap(yy, n.NBH[0][1], u.universe[0].length)][getWrap(zz, n.NBH[0][2], u.universe[0][0].length)] >= 1) {
				
				int cnt = nbrCountNotVal(xx, yy, zz, 0);
				
				if(cnt == 5){
					u.universe[xx][yy][zz]+=2;
				}
				
				if(cnt > 0){
					u.universe[xx][yy][zz]--;
				}
				
			}	
				
		}
		
	/////////////////////////////////////////////
	/////////Select Instruction to run///////////
	/////////////////////////////////////////////
																								  //Random, Threshold, Value
	public void readInstructions(int[] ins, int xx, int yy, int zz) {
		//		Utility functions / other
		if(ins[0] == -1 && (ins[1] == zz || ins[1] == -1)) {seed(xx, yy, zz, 						ins[2], ins[3], ins[4]);}
		if(ins[0] == 1  && (ins[1] == zz || ins[1] == -1)) {quantum(xx,yy,zz,						ins[2]);}
		if(ins[0] == 2  && (ins[1] == zz || ins[1] == -1)) {quantumWeight(xx,yy,zz,					ins[2]);}
		if(ins[0] == 3  && (ins[1] == zz || ins[1] == -1)) {probbilityGrowth(xx,yy,zz				);}
		if(ins[0] == 4 && (ins[1] == zz || ins[1] == -1)) {avgVonNew(xx,yy,zz						);}
		if(ins[0] == 5 && (ins[1] == zz || ins[1] == -1)) {mapPrev(xx,yy,zz,						ins[2]);}
		if(ins[0] == 68 && (ins[1] == zz || ins[1] == -1)) {platform(xx,yy,zz						);}

		
		//1D / Single Point starter required
		if(ins[0] == 6  && (ins[1] == zz || ins[1] == -1)) {sierpenski(xx, yy, zz 					);}
		if(ins[0] == 7  && (ins[1] == zz || ins[1] == -1)) {explorer(xx,yy,zz						);}
		if(ins[0] == 8  && (ins[1] == zz || ins[1] == -1)) {rule110(xx,yy,zz						);}
		if(ins[0] == 9 && (ins[1] == zz || ins[1] == -1)) {snapSierpenski(xx,yy,zz 					);}
		if(ins[0] == 10 && (ins[1] == zz || ins[1] == -1)) {hex1(xx,yy,zz							);}
		if(ins[0] == 54 && (ins[1] == zz || ins[1] == -1)) {LangtonsAnt(xx,yy,zz					);}
		if(ins[0] == 77 && (ins[1] == zz || ins[1] == -1)) {TuringCliff(xx,yy,zz					);}
		
		
		//		2D Neighbourhoods
		if((ins[0] == 11 || ins[0] == 0) && (ins[1] == zz || ins[1] == -1)) {conway(xx, yy, zz 						);}
		if(ins[0] == 12 && (ins[1] == zz || ins[1] == -1)) {ConwayExtendedRange(xx, yy, zz 			);}
		if(ins[0] == 13  && (ins[1] == zz || ins[1] == -1)) {ConwayExtendedRange2(xx, yy, zz 		);}
		if(ins[0] == 55  && (ins[1] == zz || ins[1] == -1)) {ConwayExtendedRange3(xx, yy, zz 		);}
		if(ins[0] == 56  && (ins[1] == zz || ins[1] == -1)) {ConwayExtendedRange4(xx, yy, zz 		);}
		if(ins[0] == 57  && (ins[1] == zz || ins[1] == -1)) {ConwayExtendedRange5(xx, yy, zz 		);}
		if(ins[0] == 58  && (ins[1] == zz || ins[1] == -1)) {ConwayExtendedRange6(xx, yy, zz 		);}
		if(ins[0] == 59  && (ins[1] == zz || ins[1] == -1)) {ConwayExtendedRange7(xx, yy, zz 		);}
		if(ins[0] == 60  && (ins[1] == zz || ins[1] == -1)) {ConwayExtendedRange8(xx, yy, zz 		);}
		if(ins[0] == 61  && (ins[1] == zz || ins[1] == -1)) {ConwayExtendedRange9(xx, yy, zz 		);}
		if(ins[0] == 62  && (ins[1] == zz || ins[1] == -1)) {ConwayExtendedRange10(xx, yy, zz 		);}
		if(ins[0] == 63  && (ins[1] == zz || ins[1] == -1)) {ConwayExtendedRange11(xx, yy, zz 		);}
		if(ins[0] == 64  && (ins[1] == zz || ins[1] == -1)) {ConwayExtendedRange12(xx, yy, zz 		);}
		if(ins[0] == 65  && (ins[1] == zz || ins[1] == -1)) {ConwayExtendedRange13(xx, yy, zz 		);}
		if(ins[0] == 70  && (ins[1] == zz || ins[1] == -1)) {ConwayExtendedRange13_2(xx, yy, zz 		);}
		if(ins[0] == 66  && (ins[1] == zz || ins[1] == -1)) {ConwayExtendedRange14(xx, yy, zz 		);}
		if(ins[0] == 14  && (ins[1] == zz || ins[1] == -1)) {rain2(xx,yy,zz							);}
		if(ins[0] == 15  && (ins[1] == zz || ins[1] == -1)) {goop(xx,yy,zz							);}
		if(ins[0] == 16 && (ins[1] == zz || ins[1] == -1)) {internalAffairs(xx,yy,zz				);}
		if(ins[0] == 17 && (ins[1] == zz || ins[1] == -1)) {meekrochyp(xx,yy,zz						);}
		if(ins[0] == 18 && (ins[1] == zz || ins[1] == -1)) {diamondShuffle(xx,yy,zz					);}
		if(ins[0] == 19 && (ins[1] == zz || ins[1] == -1)) {rain(xx,yy,zz							);}
		if(ins[0] == 20 && (ins[1] == zz || ins[1] == -1)) {warts(xx,yy,zz							);}
		if(ins[0] == 21 && (ins[1] == zz || ins[1] == -1)) {Threads(xx,yy,zz 						);}
		if(ins[0] == 22 && (ins[1] == zz || ins[1] == -1)) {warts2(xx,yy,zz 						);}
		if(ins[0] == 23 && (ins[1] == zz || ins[1] == -1)) {Wave(xx,yy,zz, 							ins[2]);}
		if(ins[0] == 24 && (ins[1] == zz || ins[1] == -1)) {rope(xx,yy,zz 							);}
		if(ins[0] == 25 && (ins[1] == zz || ins[1] == -1)) {Inverse110(xx,yy,zz 					);}
		if(ins[0] == 26 && (ins[1] == zz || ins[1] == -1)) {Inverse110_2(xx,yy,zz 					);}
		if(ins[0] == 27 && (ins[1] == zz || ins[1] == -1)) {Inverse110_leopard(xx,yy,zz 			);}
		if(ins[0] == 28 && (ins[1] == zz || ins[1] == -1)) {PointToCircle(xx,yy,zz 					);}
		if(ins[0] == 67 && (ins[1] == zz || ins[1] == -1)) {CyclicDaemon(xx,yy,zz 					);}

		//Rules that can have their rules customised
		if(ins[0] == 72 && (ins[1] == zz || ins[1] == -1)) {extendedRangeCustom(xx,yy,zz 			);}
		if(ins[0] == 73 && (ins[1] == zz || ins[1] == -1)) {extendedRangeCustom2(xx,yy,zz 			);}
		if(ins[0] == 74 && (ins[1] == zz || ins[1] == -1)) {extendedRangeCustom3(xx,yy,zz 			);}
		if(ins[0] == 75 && (ins[1] == zz || ins[1] == -1)) {extendedRangeCustom4(xx,yy,zz 			);}
		if(ins[0] == 76 && (ins[1] == zz || ins[1] == -1)) {extendedRangeCustom5(xx,yy,zz 			);}
		
		
		
		//2-step Fractal Neighbourhoods
		if(ins[0] == 29 && (ins[1] == zz || ins[1] == -1)) {fractalShip(xx,yy,zz					);}
		if(ins[0] == 30 && (ins[1] == zz || ins[1] == -1)) {fractalChyp(xx,yy,zz					);}
		if(ins[0] == 31 && (ins[1] == zz || ins[1] == -1)) {fractalPhase(xx,yy,zz					);}
		if(ins[0] == 32 && (ins[1] == zz || ins[1] == -1)) {fractalGun(xx,yy,zz						);}
		if(ins[0] == 33 && (ins[1] == zz || ins[1] == -1)) {fractalCount(xx,yy,zz					);}
		if(ins[0] == 34 && (ins[1] == zz || ins[1] == -1)) {fractal1D(xx,yy,zz						);}
		if(ins[0] == 35 && (ins[1] == zz || ins[1] == -1)) {fractal1D2(xx,yy,zz						);}
		if(ins[0] == 36 && (ins[1] == zz || ins[1] == -1)) {fractal1D3(xx,yy,zz						);}
		if(ins[0] == 37 && (ins[1] == zz || ins[1] == -1)) {fractal1D4(xx,yy,zz						);}
		if(ins[0] == 38 && (ins[1] == zz || ins[1] == -1)) {fractal1D5(xx,yy,zz						);}
		if(ins[0] == 39 && (ins[1] == zz || ins[1] == -1)) {fractal1D6(xx,yy,zz						);}
		if(ins[0] == 41 && (ins[1] == zz || ins[1] == -1)) {fractalTime(xx,yy,zz					);}
		if(ins[0] == 40 && (ins[1] == zz || ins[1] == -1)) {fractalMetacell(xx,yy,zz				);}
		if(ins[0] == 42 && (ins[1] == zz || ins[1] == -1)) {fractalMetacell2(xx,yy,zz				);}
		if(ins[0] == 43 && (ins[1] == zz || ins[1] == -1)) {fractalMetacell3(xx,yy,zz				);}
		if(ins[0] == 44 && (ins[1] == zz || ins[1] == -1)) {fractalMetacell4(xx,yy,zz				);}
		if(ins[0] == 45 && (ins[1] == zz || ins[1] == -1)) {fractalMetacell5(xx,yy,zz				);}
		if(ins[0] == 71 && (ins[1] == zz || ins[1] == -1)) {fractalMetacell5_2(xx,yy,zz				);}
		if(ins[0] == 46 && (ins[1] == zz || ins[1] == -1)) {fractalMetacell6(xx,yy,zz				);}
		if(ins[0] == 47 && (ins[1] == zz || ins[1] == -1)) {fractal1(xx,yy,zz						);}
		if(ins[0] == 69 && (ins[1] == zz || ins[1] == -1)) {meekrochypFr(xx,yy,zz					);}
		
		//3D Neighbourhoods
		if(ins[0] == 48 && (ins[1] == zz || ins[1] == -1)) {diffusion(xx,yy,zz, 					ins[2], ins[3]);}
		if(ins[0] == 49 && (ins[1] == zz || ins[1] == -1)) {Brownian(xx,yy,zz, 						ins[2]);}
		
		if(ins[0] == 50 && (ins[1] == zz || ins[1] == -1)) {actual3D(xx,yy,zz						);}
		if(ins[0] == 51 && (ins[1] == zz || ins[1] == -1)) {VonnFractal(xx,yy,zz					);}
		if(ins[0] == 52 && (ins[1] == zz || ins[1] == -1)) {MooreFractal(xx,yy,zz					);}
		if(ins[0] == 53 && (ins[1] == zz || ins[1] == -1)) {MooreFractalColour(xx,yy,zz				);}
		
		
		
		
		
		
		

	}

	
}

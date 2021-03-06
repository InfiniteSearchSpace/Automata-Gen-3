import java.util.Random;


public class automataLib {
	
	Random r = new Random();
	neighbours n;
	
	Universe u;
	Main m;
	int m_xSize;
	int m_ySize;
	int instrOverride = -1;
	int modClock = 0;
	int gen = 0;
	//dataSources d;
	
	int[][] instructions; //holds the actions to perform
	
	int[][] min_grow_die = new int[1][6];
	
	//constructor
	public automataLib(Main mm, int xSize, int ySize) {
		m = mm;
		n= new neighbours(0);
		m_xSize=xSize;
		m_ySize=ySize;
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
	
	public void incrGen(){
		gen++;
	}

	private void incrementMe(int xx, int yy, int zz){
		u.universe[xx][yy][zz]++;
	}
	
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
    	
    	int[] dataLine = new int[blockSize]; 
    	
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
		n.setNbrhood(1);
		
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
		n.setNbrhood(0);
		
		
		int t = nbrCountNotVal(xx,yy,zz,0);

		if (t == 2){incrementMe(xx,yy,zz);}
		if(t < 2 || t > 3){u.universe[xx][yy][zz] = 0;}
		
	}
	
	public void meekrochyp(int xx, int yy, int zz){					
		n.setNbrhood(0);
		

		int t = nbrCountNotVal(xx,yy,zz,0);

		if (t == 2){incrementMe(xx,yy,zz);}
		if(t < 2 || t > 4){u.universe[xx][yy][zz] = 0;}
		
	}
	
	public void meekrochypFr(int xx, int yy, int zz){					
		n.setNbrhood(5);
		
		int sum = 0;
		for(int i = 0; i < n.NBH.length; i++){
			sum += getNbrCounts(getWrap(xx, n.NBH[i][0], u.universe.length), getWrap(yy, n.NBH[i][1], u.universe[0].length), getWrap(zz, n.NBH[i][2], u.universe[0][0].length));
		}

		if (sum <= 4){u.universe[xx][yy][zz] = 0;}
		if (sum == 4){incrementMe(xx,yy,zz);}
		if (sum >= 8){u.universe[xx][yy][zz] = 0;}
		
	}
	
	public void exposeC(int xx, int yy, int zz){					
		n.setNbrhood(4);
		
		int cellCount = nbrCountNotVal(xx,yy,zz,0);
				
		if (cellCount > 0){u.universe[xx][yy][zz] = 0;}
		if (cellCount == 1){incrementMe(xx,yy,zz);}
	}
	
	public void exposeJ(int xx, int yy, int zz){					
		n.setNbrhood(4);
		if (nbrCountNotVal(xx,yy,zz,0) == 1){incrementMe(xx,yy,zz);}
	}
	
	public void diamondShuffle(int xx, int yy, int zz){					
		n.setNbrhood(0);

		int t = nbrCountNotVal(xx,yy,zz,0);

		if (t == 2){incrementMe(xx,yy,zz);}
		if(t < 2 || t > 3){u.universe[xx][yy][zz] = 0;}
		
	}

	public void goop(int xx, int yy, int zz){					
		n.setNbrhood(2);

		int t = nbrCountNotVal(xx,yy,zz,0);
		
		if (t < 2 || t > 4) {
			for(int i = 0; i < 5; i++) {
				u.universe[xx][yy][zz] = 0;
			}
		} 
		
		if (t == 3){incrementMe(xx,yy,zz);}
		
	}

	public void explorer24(int xx, int yy, int zz){
		n.setNbrhood(3);
		
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
		n.setNbrhood(0);
		
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

		n.setNbrhood(0);
		
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

		
		n.setNbrhood(0);
		
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
			n.setNbrhood(6);
			
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

		n.setNbrhood(0);
		
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
		
		n.setNbrhood(7);
		
		int isOne = 0;

		for(int i = 0; i < n.NBH.length; i++) {
			if(u.snapshotUniverse[getWrap(xx, n.NBH[i][0], u.universe.length)][getWrap(yy, n.NBH[i][1], u.universe[0].length)][getWrap(zz, n.NBH[i][2], u.universe[0][0].length)] == 1) {isOne++;}
		}
		

		
		if(isOne == 1) {
			u.universe[xx][yy][zz] = 0;
		}// else {u.universe[xx][yy][zz] = 0;}

		if(isOne == 2) {incrementMe(xx,yy,zz);}


    }
	
	public void rain2(int xx, int yy, int zz){
		
		n.setNbrhood(8);
		
		int isOne = 0;

		for(int i = 0; i < n.NBH.length; i++) {
			if(u.snapshotUniverse[getWrap(xx, n.NBH[i][0], u.universe.length)][getWrap(yy, n.NBH[i][1], u.universe[0].length)][getWrap(zz, n.NBH[i][2], u.universe[0][0].length)] == 1) {isOne++;}
		}
		

		
		if(isOne != 2) {
			u.universe[xx][yy][zz] = 0;
		}// else {u.universe[xx][yy][zz] = 0;}

		if(isOne == 2) {incrementMe(xx,yy,zz);}


    }
	
	public void sierpenski(int xx, int yy, int zz){
		n.setNbrhood(9);
		
		int isOne = 0;

		for(int i = 0; i < n.NBH.length; i++) {
			if(u.universe[getWrap(xx, n.NBH[i][0], u.universe.length)][getWrap(yy, n.NBH[i][1], u.universe[0].length)][getWrap(zz, n.NBH[i][2], u.universe[0][0].length)] == 1) {isOne++;}
			// IF u.universe, NOT u.snapshot. Weird.
			//if(u.snapshotUniverse[getWrap(xx, n.NBH[i][0], u.universe.length)][getWrap(yy, n.NBH[i][1], u.universe[0].length)][getWrap(zz, n.NBH[i][2], u.universe[0][0].length)] == 1) {isOne++;}
			
		}
		
		if(isOne == 1) {incrementMe(xx,yy,zz);} else {u.universe[xx][yy][zz] = 0;}

    }
	
	public void snapSierpenski(int xx, int yy, int zz){

		n.setNbrhood(9);
		int isOne = 0;

		for(int i = 0; i < n.NBH.length; i++) {
			//if(u.universe[getWrap(xx, n.NBH[i][0], u.universe.length)][getWrap(yy, n.NBH[i][1], u.universe[0].length)][getWrap(zz, n.NBH[i][2], u.universe[0][0].length)] == 1) {isOne++;}
			// IF u.universe, NOT u.snapshot. Weird.
			if(u.snapshotUniverse[getWrap(xx, n.NBH[i][0], u.universe.length)][getWrap(yy, n.NBH[i][1], u.universe[0].length)][getWrap(zz, n.NBH[i][2], u.universe[0][0].length)] == 1) {isOne++;}
			
		}
		
		if(isOne == 1) {incrementMe(xx,yy,zz);} else {u.universe[xx][yy][zz] = 0;}

    }
	
	public void conway(int xx, int yy, int zz){

		n.setNbrhood(4);
		
		int isOne = nbrCountNotVal(xx,yy,zz,0);
		
		if(isOne < 2)  { u.universe[xx][yy][zz] = 0; }
		if(isOne > 3)  { u.universe[xx][yy][zz] = 0; }
		if(isOne == 3) { u.universe[xx][yy][zz]++; }

    }
	
	public void conway_twinPrime(int xx, int yy, int zz){
		int isOne=0;
		
		/*n.setNbrhood(27);
		isOne = nbrCountNotVal(xx,yy,zz,0);
		if(isOne > 19)  { u.universe[xx][yy][zz] = 0; }
		if(isOne == 19) { u.universe[xx][yy][zz]++; }
		if(isOne < 0)  { u.universe[xx][yy][zz] = 0; }
		/**/
		/*n.setNbrhood(29);
		isOne = nbrCountNotVal(xx,yy,zz,0);
		if(isOne > 13)  { u.universe[xx][yy][zz] = 0; }
		if(isOne == 13) { u.universe[xx][yy][zz]++; }
		if(isOne < 4)  { u.universe[xx][yy][zz] = 0; }
		/**/
		n.setNbrhood(3);
		isOne = nbrCountNotVal(xx,yy,zz,0);
		if(isOne >= 8)  { u.universe[xx][yy][zz] = 0; }
		if(isOne == 7) { u.universe[xx][yy][zz]++; }
		if(isOne <= 3)  { u.universe[xx][yy][zz] = 0; }
		
		n.setNbrhood(4);
		isOne = nbrCountNotVal(xx,yy,zz,0);
		if(isOne >= 4)  { u.universe[xx][yy][zz] = 0; }
		if(isOne == 3) { u.universe[xx][yy][zz]++; }
		if(isOne <= 1)  { u.universe[xx][yy][zz] = 0; }

    }

	public void probbilityGrowth(int xx, int yy, int zz){
		
		n.setNbrhood(31);
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

		n.setNbrhood(10);
		int isOne = nbrCountNotVal(xx,yy,zz,0);
		
		if(isOne <= 3)  { u.universe[xx][yy][zz] = 0; }
		if(isOne >= 5)  { u.universe[xx][yy][zz] = 0; }
		if(isOne == 2 || isOne == 1)  {incrementMe(xx,yy,zz);}

	}
		
	public void rope(int xx, int yy, int zz){
		n.setNbrhood(12);
		
		int isOne = nbrCountNotVal(xx,yy,zz,0);
		
		if(isOne == 1)  { u.universe[xx][yy][zz] = 0; }
		if(isOne == 2)  {incrementMe(xx,yy,zz);}
		if(isOne > 2)  { u.universe[xx][yy][zz] = 0; }
	}
	
	
	public void extendedRangeCustom(int xx, int yy, int zz){

		n.setNbrhood(3);
		
		
		int isOne = nbrCountNotVal(xx,yy,zz,0);
		
		if(isOne >= min_grow_die[0][0] && isOne <= min_grow_die[0][1]/* && isOne <= min_grow_die[0][3]*/)  { u.universe[xx][yy][zz] = 0; }
		if(isOne >= min_grow_die[0][2] && isOne <= min_grow_die[0][3]/* && isOne <= min_grow_die[0][4]*/)  {incrementMe(xx,yy,zz);}
		if(isOne >= min_grow_die[0][4] && isOne <= min_grow_die[0][5]/* && isOne <= min_grow_die[0][5]*/)  { u.universe[xx][yy][zz] = 0; }
	}
	
	public void extendedRangeCustom2(int xx, int yy, int zz){

		n.setNbrhood(4);
		
		int isOne = nbrCountNotVal(xx,yy,zz,0);
		
		if(isOne >= min_grow_die[0][0] && isOne <= min_grow_die[0][1]/* && isOne <= min_grow_die[0][3]*/)  { u.universe[xx][yy][zz] = 0; }
		if(isOne >= min_grow_die[0][2] && isOne <= min_grow_die[0][3]/* && isOne <= min_grow_die[0][4]*/)  {incrementMe(xx,yy,zz);}
		if(isOne >= min_grow_die[0][4] && isOne <= min_grow_die[0][5]/* && isOne <= min_grow_die[0][5]*/)  { u.universe[xx][yy][zz] = 0; }
	}
	
	public void extendedRangeCustom4(int xx, int yy, int zz){

		n.setNbrhood(29);
		
		int isOne = nbrCountNotVal(xx,yy,zz,0);
		
		if(isOne >= min_grow_die[0][0] && isOne <= min_grow_die[0][1]/* && isOne <= min_grow_die[0][3]*/)  { u.universe[xx][yy][zz] = 0; }
		if(isOne >= min_grow_die[0][2] && isOne <= min_grow_die[0][3]/* && isOne <= min_grow_die[0][4]*/) 	{incrementMe(xx,yy,zz);}
		if(isOne >= min_grow_die[0][4] && isOne <= min_grow_die[0][5]/* && isOne <= min_grow_die[0][5]*/)  { u.universe[xx][yy][zz] = 0; }
	}
	

	public void extendedRangeCustom7(int xx, int yy, int zz){

		n.setNbrhood(27);
		
		int isOne = nbrCountNotVal(xx,yy,zz,0);
		
		if(isOne >= min_grow_die[0][0] && isOne <= min_grow_die[0][1]/* && isOne <= min_grow_die[0][3]*/)  { u.universe[xx][yy][zz] = 0; }
		if(isOne >= min_grow_die[0][2] && isOne <= min_grow_die[0][3]/* && isOne <= min_grow_die[0][4]*/)  {incrementMe(xx,yy,zz);}
		if(isOne >= min_grow_die[0][4] && isOne <= min_grow_die[0][5]/* && isOne <= min_grow_die[0][5]*/)  { u.universe[xx][yy][zz] = 0; }
	}
	
	
	
	public void extendedRangeCustom3(int xx, int yy, int zz){

		n.setNbrhood(22);
		
		int isOne = nbrCountNotVal(xx,yy,zz,0);
		
		if(isOne >= min_grow_die[0][0] && isOne <= min_grow_die[0][1]/* && isOne <= min_grow_die[0][3]*/)  { u.universe[xx][yy][zz] = 0; }
		if(isOne >= min_grow_die[0][2] && isOne <= min_grow_die[0][3]/* && isOne <= min_grow_die[0][4]*/)  {incrementMe(xx,yy,zz);}
		if(isOne >= min_grow_die[0][4] && isOne <= min_grow_die[0][5]/* && isOne <= min_grow_die[0][5]*/)  { u.universe[xx][yy][zz] = 0; }
	}
	
	public void ThreadsCustom(int xx, int yy, int zz){
		n.setNbrhood(26);
		
		int isOne = nbrCountNotVal(xx,yy,zz,0);
		
		if(isOne >= min_grow_die[0][0] && isOne <= min_grow_die[0][1]/* && isOne <= min_grow_die[0][3]*/)  { u.universe[xx][yy][zz] = 0; }
		if(isOne >= min_grow_die[0][2] && isOne <= min_grow_die[0][3]/* && isOne <= min_grow_die[0][4]*/)  {incrementMe(xx,yy,zz);}
		if(isOne >= min_grow_die[0][4] && isOne <= min_grow_die[0][5]/* && isOne <= min_grow_die[0][5]*/)  { u.universe[xx][yy][zz] = 0; }
	}
	
	public void ThreadsCustom2(int xx, int yy, int zz){
		n.setNbrhood(25);
		
		int isOne = nbrCountNotVal(xx,yy,zz,0);
		
		if(isOne >= min_grow_die[0][0] && isOne <= min_grow_die[0][1]/* && isOne <= min_grow_die[0][3]*/)  { u.universe[xx][yy][zz] = 0; }
		if(isOne >= min_grow_die[0][2] && isOne <= min_grow_die[0][3]/* && isOne <= min_grow_die[0][4]*/)  {incrementMe(xx,yy,zz);}
		if(isOne >= min_grow_die[0][4] && isOne <= min_grow_die[0][5]/* && isOne <= min_grow_die[0][5]*/)  { u.universe[xx][yy][zz] = 0; }
	}
	
	public void ThreadsCustom3(int xx, int yy, int zz){
		n.setNbrhood(18);
		
		int isOne = nbrCountNotVal(xx,yy,zz,0);
		
		if(isOne >= min_grow_die[0][0] && isOne <= min_grow_die[0][1]/* && isOne <= min_grow_die[0][3]*/)  { u.universe[xx][yy][zz] = 0; }
		if(isOne >= min_grow_die[0][2] && isOne <= min_grow_die[0][3]/* && isOne <= min_grow_die[0][4]*/)  {incrementMe(xx,yy,zz);}
		if(isOne >= min_grow_die[0][4] && isOne <= min_grow_die[0][5]/* && isOne <= min_grow_die[0][5]*/)  { u.universe[xx][yy][zz] = 0; }
	}
	
	public void ThreadsCustom4(int xx, int yy, int zz){
		n.setNbrhood(19);
		
		int isOne = nbrCountNotVal(xx,yy,zz,0);
		
		if(isOne >= min_grow_die[0][0] && isOne <= min_grow_die[0][1]/* && isOne <= min_grow_die[0][3]*/)  { u.universe[xx][yy][zz] = 0; }
		if(isOne >= min_grow_die[0][2] && isOne <= min_grow_die[0][3]/* && isOne <= min_grow_die[0][4]*/)  {incrementMe(xx,yy,zz);}
		if(isOne >= min_grow_die[0][4] && isOne <= min_grow_die[0][5]/* && isOne <= min_grow_die[0][5]*/)  { u.universe[xx][yy][zz] = 0; }
	}
	
	
	public void extendedRangeCustom5(int xx, int yy, int zz){

		n.setNbrhood(0);
		
		int isOne = 0;
		for(int i = 0; i < n.NBH.length; i++){
			isOne += getNbrCounts(getWrap(xx, n.NBH[i][0], u.universe.length), getWrap(yy, n.NBH[i][1], u.universe[0].length), getWrap(zz, n.NBH[i][2], u.universe[0][0].length));
		}
		
		if(isOne >= min_grow_die[0][0] && isOne <= min_grow_die[0][1]/* && isOne <= min_grow_die[0][3]*/)  { u.universe[xx][yy][zz] = 0; }
		if(isOne >= min_grow_die[0][2] && isOne <= min_grow_die[0][3]/* && isOne <= min_grow_die[0][4]*/)  {incrementMe(xx,yy,zz);}
		if(isOne >= min_grow_die[0][4] && isOne <= min_grow_die[0][5]/* && isOne <= min_grow_die[0][5]*/)  { u.universe[xx][yy][zz] = 0; }
	}
	
	public void extendedRangeCustom6(int xx, int yy, int zz){

		n.setNbrhood(20);
		
		int isOne = nbrCountNotVal(xx,yy,zz,0);
		
		if(isOne >= min_grow_die[0][0] && isOne <= min_grow_die[0][1]/* && isOne <= min_grow_die[0][3]*/)  { u.universe[xx][yy][zz] = 0; }
		if(isOne >= min_grow_die[0][2] && isOne <= min_grow_die[0][3]/* && isOne <= min_grow_die[0][4]*/)  {incrementMe(xx,yy,zz);}
		if(isOne >= min_grow_die[0][4] && isOne <= min_grow_die[0][5]/* && isOne <= min_grow_die[0][5]*/)  { u.universe[xx][yy][zz] = 0; }
	}
	
	public void ConwayExtendedRange(int xx, int yy, int zz){

		n.setNbrhood(3);
		
		
		int isOne = nbrCountNotVal(xx,yy,zz,0);
		
		
		if(isOne <= 3)  { u.universe[xx][yy][zz] = 0; }
		if(isOne == 4)  {incrementMe(xx,yy,zz);}
		if(isOne >= 5)  { u.universe[xx][yy][zz] = 0; }
	}
	
	public void ConwayExtendedRange2(int xx, int yy, int zz){

		n.setNbrhood(3);
		
		
		int isOne = nbrCountNotVal(xx,yy,zz,0);
		
		
		/*if(isOne <= 3)  { u.universe[xx][yy][zz] = 0; }
		if(isOne == 4)  { u.universe[xx][yy][zz] = 1; }
		if(isOne >= 5)  { u.universe[xx][yy][zz] = 0; }
		if(isOne == 6)  { u.universe[xx][yy][zz] = 1; }*/
		
		if(isOne <=0)  { u.universe[xx][yy][zz] = 0; }
		if(isOne == 5)  {incrementMe(xx,yy,zz);}
		if(isOne >= 7)  { u.universe[xx][yy][zz] = 0; }
		
		/*if(isOne == 7)  { u.universe[xx][yy][zz] = 0; }
		if(isOne == 8)  { u.universe[xx][yy][zz] = 0; }*/
		//if(isOne == 2)  { u.universe[xx][yy][zz] = 1; }
		//if(isOne > 3)  { u.universe[xx][yy][zz] = 0; }
	}
	
	public void ConwayExtendedRange3(int xx, int yy, int zz){

		n.setNbrhood(3);
		
		
		int sum = nbrCountNotVal(xx,yy,zz,0);
		
		if(sum <= 3)  { u.universe[xx][yy][zz] = 0; }
		if(sum == 5)  {incrementMe(xx,yy,zz);}
		if(sum > 5)  { u.universe[xx][yy][zz] = 0; }/**/
		
		/*if(sum <= 7)  { u.universe[xx][yy][zz] = 0; }
		if(sum == 5)  { u.universe[xx][yy][zz] = 1; }
		if(sum > 99)  { u.universe[xx][yy][zz] = 0; }*/
	}
	
	public void ConwayExtendedRange4(int xx, int yy, int zz){

		n.setNbrhood(3);
		
		
		int sum = nbrCountNotVal(xx,yy,zz,0);
	
		if(sum <= 1)  			 	{ u.universe[xx][yy][zz] = 0; } //min
		if(sum >= 10 && sum <= 11) 	{incrementMe(xx,yy,zz);} //balance
		if(sum >= 12 && sum <= 99) 	{ u.universe[xx][yy][zz] = 0; }	//max

	}
	
	public void ConwayExtendedRange5(int xx, int yy, int zz){

		n.setNbrhood(3);
		
		
		int sum = nbrCountNotVal(xx,yy,zz,0);

		if(sum <= 1)  			 	{ u.universe[xx][yy][zz] = 0; } //max
		if(sum >= 5 && sum <= 5) 	{incrementMe(xx,yy,zz);} //balance
		if(sum >= 6 && sum <= 99) 	{ u.universe[xx][yy][zz] = 0; }	//min
		
	}
	
	public void ConwayExtendedRange6(int xx, int yy, int zz){

		n.setNbrhood(3);
		
		
		int sum = nbrCountNotVal(xx,yy,zz,0);
		
		if(sum <= 5)  			 	{ u.universe[xx][yy][zz] = 0; } //max
		if(sum >= 13 && sum <= 18) 	{incrementMe(xx,yy,zz);} //balance
		if(sum >= 7 && sum <= 7) 	{ u.universe[xx][yy][zz] = 0; }	//min
		
	
	}
	
	public void ConwayExtendedRange7(int xx, int yy, int zz){

		n.setNbrhood(3);
		
		
		int sum = nbrCountNotVal(xx,yy,zz,0);

		if(sum <= 6)  { u.universe[xx][yy][zz] = 0; }
		if(sum == 8)  {incrementMe(xx,yy,zz);}
		if(sum == 12) {u.universe[xx][yy][zz] = 0;}
		
	}
	
	public void ConwayExtendedRange8(int xx, int yy, int zz){

		n.setNbrhood(3);
		
		
		int sum = nbrCountNotVal(xx,yy,zz,0);

		if(sum <= 5)  { u.universe[xx][yy][zz] = 0; }
		if(sum == 8)  {incrementMe(xx,yy,zz);}
		if(sum == 11) {u.universe[xx][yy][zz] = 0;}
		
	}
	
	public void ConwayExtendedRange9(int xx, int yy, int zz){

		n.setNbrhood(3);
		
		int sum = nbrCountNotVal(xx,yy,zz,0);
	
		if(sum <= 5)  { u.universe[xx][yy][zz] = 0; }
		if(sum == 6)  {incrementMe(xx,yy,zz);}
		if(sum == 7) {u.universe[xx][yy][zz] = 0;}
		
	}
	
	public void ConwayExtendedRange10(int xx, int yy, int zz){
		n.setNbrhood(3);
		int sum = nbrCountNotVal(xx,yy,zz,0);
	
		if(sum <= 1)  			 	{ u.universe[xx][yy][zz] = 0; } //min
		if(sum >= 10 && sum <= 11) 	{incrementMe(xx,yy,zz);} //balance
		if(sum >= 12 && sum <= 99) 	{ u.universe[xx][yy][zz] = 0; }	//max
		
		
		if(sum <= 7)  { u.universe[xx][yy][zz] = 0; }
		if(sum == 4)  {incrementMe(xx,yy,zz);}
		if(sum == 15) {u.universe[xx][yy][zz] = 0;}
		
	}
	
	public void ConwayExtendedRange11(int xx, int yy, int zz){

		n.setNbrhood(3);
		
		
		int sum = nbrCountNotVal(xx,yy,zz,0);
	
		if(sum <= 12)  { u.universe[xx][yy][zz] = 0; }
		if(sum < 13)  { u.universe[xx][yy][zz] = 0; }
		if(sum == 4)  {incrementMe(xx,yy,zz);}
		if(sum >= 13)  {incrementMe(xx,yy,zz);}
		
	}
	
	public void ConwayExtendedRange12(int xx, int yy, int zz){

		n.setNbrhood(3);
		
		int sum = nbrCountNotVal(xx,yy,zz,0);
	
		if(sum <= 12)  { u.universe[xx][yy][zz] = 0; }
		if(sum == 4)  {incrementMe(xx,yy,zz);}
		if(sum > 99)  { u.universe[xx][yy][zz] = 0; }
	}
	
	public void ConwayExtendedRange13(int xx, int yy, int zz){
		n.setNbrhood(3);
		
		
		int sum = nbrCountNotVal(xx,yy,zz,0);
	
		if(sum <= 8)  { u.universe[xx][yy][zz] = 0; }
		if(sum == 4)  {incrementMe(xx,yy,zz);}
		if(sum > 99)  { u.universe[xx][yy][zz] = 0; }
	}
	
	public void ConwayExtendedRange13_2(int xx, int yy, int zz){

		n.setNbrhood(3);
		
		
		int sum = nbrCountNotVal(xx,yy,zz,0);
	
		if(sum <= 6)  				{ u.universe[xx][yy][zz] = 0; }
		if(sum == 4)  				{incrementMe(xx,yy,zz);}
		if(sum >= 9 && sum <= 11)  	{ u.universe[xx][yy][zz] = 0; }
	}
	
	public void ConwayExtendedRange14(int xx, int yy, int zz){

		n.setNbrhood(3);
		
		int sum = nbrCountNotVal(xx,yy,zz,0);
		if(sum <= 7)  { u.universe[xx][yy][zz] = 0; }
		if(sum == 5)  {incrementMe(xx,yy,zz);}
		if(sum > 99)  { u.universe[xx][yy][zz] = 0; }
	}
	
	public void warts(int xx, int yy, int zz){

		n.setNbrhood(0);
		
		int isOne = nbrCountNotVal(xx,yy,zz,0);
		
		if(isOne > 0) {
			if(isOne <= 1)  { u.universe[xx][yy][zz] = 0; }
			if(isOne == 2)  {incrementMe(xx,yy,zz);}
			if(isOne >= 3)  { u.universe[xx][yy][zz] = 0; }
		}
		
	}
	
	public void Threads(int xx, int yy, int zz){
			n.setNbrhood(16);
			
			int isOne = nbrCountNotVal(xx,yy,zz,0);
			
			if(isOne > 0) {
				if(isOne <= 1)  { u.universe[xx][yy][zz] = 0; }
				if(isOne == 2) {incrementMe(xx,yy,zz);}
				if(isOne >= 3)  { u.universe[xx][yy][zz] = 0; }
			}
	}
	
	public void Inverse110(int xx, int yy, int zz){
		n.setNbrhood(11);
		
		int isOne = nbrCountNotVal(xx,yy,zz,0);
		
		if(isOne > 0) {
			//if(isOne == 0)  { u.universe[xx][yy][zz] = 0; }
			if(isOne == 1)  {incrementMe(xx,yy,zz);}
			if(isOne == 2)  { u.universe[xx][yy][zz] = 0; }
		}
		
		
	}
	
	public void Inverse110_2(int xx, int yy, int zz){
		n.setNbrhood(17);
		
		int isOne = nbrCountNotVal(xx,yy,zz,0);
		
		if(isOne > 0) {
			if(isOne <= 1)  { u.universe[xx][yy][zz] = 0; }
			if(isOne == 2)  {incrementMe(xx,yy,zz);}
			if(isOne >= 3)  { u.universe[xx][yy][zz] = 0; }
		}
	}
	
	public void Inverse110_leopard(int xx, int yy, int zz){
		n.setNbrhood(15);
		
		int isOne = nbrCountNotVal(xx,yy,zz,0);
		
		if(isOne > 0) {
			if(isOne <= 0)  { u.universe[xx][yy][zz] = 0; }
			if(isOne == 1)  {incrementMe(xx,yy,zz);}
			if(isOne >= 2)  { u.universe[xx][yy][zz] = 0; }
		}

	}

	
	public void actual3D(int xx, int yy, int zz){
		n.setNbrhood(24);
		int isOne = nbrCountNotVal(xx,yy,zz,0);

		//if(isOne > 0) {
			if(isOne <= 2) { u.universe[xx][yy][zz] = 0;}
			if(isOne == 5){incrementMe(xx,yy,zz);}
			if(isOne >= 7) { u.universe[xx][yy][zz] = 0;} /*else { u.universe[xx][yy][zz] = 0; }*/
		//}
		//conway(i,j,k,1,0);
	}

	public void rule110(int xx, int yy, int zz){					
		n.setNbrhood(13);
		
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
			n.setNbrhood(22);
			
	
			for(int i = 0; i < n.NBH.length; i++) {
				u.universe[getWrap(xx, n.NBH[i][0], u.universe.length)][getWrap(yy, n.NBH[i][1], u.universe[0].length)][getWrap(zz, n.NBH[i][2], u.universe[0][0].length)]+=1;
				u.universe[xx][yy][zz]-=1;
			}
		}
		
	}
	
	public void avgVonNew(int xx, int yy, int zz){

		n.setNbrhood(4);
		
		u.universe[xx][yy][zz]=nbrAvg(xx,yy,zz);
	
	}
	
	public void fractalCount(int xx, int yy, int zz){

		n.setNbrhood(0);
		
		int sum = 0;
		for(int i = 0; i < n.NBH.length; i++){
			sum += getNbrCounts(getWrap(xx, n.NBH[i][0], u.universe.length), getWrap(yy, n.NBH[i][1], u.universe[0].length), getWrap(zz, n.NBH[i][2], u.universe[0][0].length));
		}
		
		if(sum > 0) {
			if(sum <= 0)  { u.universe[xx][yy][zz] = 0; }
			if(sum == 1)  {incrementMe(xx,yy,zz);}
			if(sum >= 2)  { u.universe[xx][yy][zz] = 0; }
		}
	}

	public void fractalGun(int xx, int yy, int zz){

		n.setNbrhood(0);
		int sum = 0;
		for(int i = 0; i < n.NBH.length; i++){
			sum += getNbrCounts(getWrap(xx, n.NBH[i][0], u.universe.length), getWrap(yy, n.NBH[i][1], u.universe[0].length), getWrap(zz, n.NBH[i][2], u.universe[0][0].length));
		}
		
		
		if(sum <= 2)  { u.universe[xx][yy][zz] = 0; }
		if(sum == 3)  {incrementMe(xx,yy,zz);}
		if(sum >= 9)  { u.universe[xx][yy][zz] = 0; }
		
	}
	
	public void fractalPhase(int xx, int yy, int zz){
		
		n.setNbrhood(0);
		
		int sum = 0;
		for(int i = 0; i < n.NBH.length; i++){
			sum += getNbrCounts(getWrap(xx, n.NBH[i][0], u.universe.length), getWrap(yy, n.NBH[i][1], u.universe[0].length), getWrap(zz, n.NBH[i][2], u.universe[0][0].length));
		}
		
		
		if(sum <= 3)  { u.universe[xx][yy][zz] = 0; }
		if(sum == 4)  {incrementMe(xx,yy,zz);}
		if(sum >= 11)  { u.universe[xx][yy][zz] = 0; }
		
	}
	
	public void fractalChyp(int xx, int yy, int zz){

		n.setNbrhood(0);
		
		int sum = 0;
		for(int i = 0; i < n.NBH.length; i++){
			sum += getNbrCounts(getWrap(xx, n.NBH[i][0], u.universe.length), getWrap(yy, n.NBH[i][1], u.universe[0].length), getWrap(zz, n.NBH[i][2], u.universe[0][0].length));
		}
		
		
		if(sum <= 3)  { u.universe[xx][yy][zz] = 0; }
		if(sum == 5)  {incrementMe(xx,yy,zz);}
		if(sum >= 12)  { u.universe[xx][yy][zz] = 0; }
		
	}
	
	public void fractalShip(int xx, int yy, int zz){

		n.setNbrhood(0);
		
		int sum = 0;
		for(int i = 0; i < n.NBH.length; i++){
			sum += getNbrCounts(getWrap(xx, n.NBH[i][0], u.universe.length), getWrap(yy, n.NBH[i][1], u.universe[0].length), getWrap(zz, n.NBH[i][2], u.universe[0][0].length));
		}
		
		
		if(sum <= 4)  { u.universe[xx][yy][zz] = 0; }
		if(sum == 5)  {incrementMe(xx,yy,zz);}
		if(sum >= 11)  { u.universe[xx][yy][zz] = 0; }
		
	}
	
	public void fractal1D(int xx, int yy, int zz){
		
		n.setNbrhood(13);
		
		int sum = 0;
		for(int i = 0; i < n.NBH.length; i++){
			sum += getNbrCounts(getWrap(xx, n.NBH[i][0], u.universe.length), getWrap(yy, n.NBH[i][1], u.universe[0].length), getWrap(zz, n.NBH[i][2], u.universe[0][0].length));
		}
		
		//if(sum > 0) {
		if(sum <= 1)  { u.universe[xx][yy][zz] = 0; }
		if(sum == 3)  {incrementMe(xx,yy,zz);}
		if(sum >= 5)  { u.universe[xx][yy][zz] = 0; }
		//}
		
	}
	
	public void fractal1D2(int xx, int yy, int zz){
			
			n.setNbrhood(13);
			
			int sum = 0;
			for(int i = 0; i < n.NBH.length; i++){
				sum += getNbrCounts(getWrap(xx, n.NBH[i][0], u.universe.length), getWrap(yy, n.NBH[i][1], u.universe[0].length), getWrap(zz, n.NBH[i][2], u.universe[0][0].length));
			}
			
			//if(sum > 0) {
			if(sum <= 1)  { u.universe[xx][yy][zz] = 0; }
			if(sum == 3)  {incrementMe(xx,yy,zz);}
			if(sum >= 9)  { u.universe[xx][yy][zz] = 0; }
			//}
			
		}
	
	public void fractal1D3(int xx, int yy, int zz){
		
		n.setNbrhood(14);
		
		int sum = 0;
		for(int i = 0; i < n.NBH.length; i++){
			sum += getNbrCounts(getWrap(xx, n.NBH[i][0], u.universe.length), getWrap(yy, n.NBH[i][1], u.universe[0].length), getWrap(zz, n.NBH[i][2], u.universe[0][0].length));
		}
		
		//if(sum > 0) {
		if(sum <= 2)  { u.universe[xx][yy][zz] = 0; }
		if(sum == 3)  {incrementMe(xx,yy,zz);}
		if(sum >= 4)  { u.universe[xx][yy][zz] = 0; }
		//}
		
		
	}
	
public void fractal1D4(int xx, int yy, int zz){
		
		n.setNbrhood(14);
		
		int sum = 0;
		for(int i = 0; i < n.NBH.length; i++){
			sum += getNbrCounts(getWrap(xx, n.NBH[i][0], u.universe.length), getWrap(yy, n.NBH[i][1], u.universe[0].length), getWrap(zz, n.NBH[i][2], u.universe[0][0].length));
		}
		
		//if(sum > 0) {
		if(sum <= 1)  { u.universe[xx][yy][zz] = 0; }
		if(sum == 3)  {incrementMe(xx,yy,zz);}
		if(sum >= 7)  { u.universe[xx][yy][zz] = 0; }
		//}
		
		
	}

	public void fractal1D5(int xx, int yy, int zz){
		n.setNbrhood(1);
		
		int sum = 0;
		for(int i = 0; i < n.NBH.length; i++){
			sum += getNbrCounts(getWrap(xx, n.NBH[i][0], u.universe.length), getWrap(yy, n.NBH[i][1], u.universe[0].length), getWrap(zz, n.NBH[i][2], u.universe[0][0].length));
		}
		
		//if(sum > 0) {
		if(sum <= 2)  { u.universe[xx][yy][zz] = 0; }
		if(sum == 2)  {incrementMe(xx,yy,zz);}
		if(sum >= 7)  { u.universe[xx][yy][zz] = 0; }
		//}
		
		
	}
	
	public void fractal1D6(int xx, int yy, int zz){
		n.setNbrhood(1);
		
		int sum = 0;
		for(int i = 0; i < n.NBH.length; i++){
			sum += getNbrCounts(getWrap(xx, n.NBH[i][0], u.universe.length), getWrap(yy, n.NBH[i][1], u.universe[0].length), getWrap(zz, n.NBH[i][2], u.universe[0][0].length));
		}
		
		//if(sum > 0) {
		if(sum <= 1)  { u.universe[xx][yy][zz] = 0; }
		if(sum == 3)  {incrementMe(xx,yy,zz);}
		if(sum >= 6)  { u.universe[xx][yy][zz] = 0; }
		//}
		
		
	}
	
public void fractalTime(int xx, int yy, int zz){

		n.setNbrhood(4);
		
		int sum = 0;
		for(int i = 0; i < n.NBH.length; i++){
			sum += getNbrCounts(getWrap(xx, n.NBH[i][0], u.universe.length), getWrap(yy, n.NBH[i][1], u.universe[0].length), getWrap(zz, n.NBH[i][2], u.universe[0][0].length));
		}
		
		//if(sum > 0) {
		if(sum <= 30)  { u.universe[xx][yy][zz] = 0; }
		if(sum == 6)  {incrementMe(xx,yy,zz);}
		if(sum >= 99)  { u.universe[xx][yy][zz] = 0; }
		//}
		
		
	}

public void fractalMetacell(int xx, int yy, int zz){

	n.setNbrhood(4);
	
	int sum = 0;
	for(int i = 0; i < n.NBH.length; i++){
		sum += getNbrCounts(getWrap(xx, n.NBH[i][0], u.universe.length), getWrap(yy, n.NBH[i][1], u.universe[0].length), getWrap(zz, n.NBH[i][2], u.universe[0][0].length));
	}
	
	//if(sum > 0) {
	if(sum <= 33)  { u.universe[xx][yy][zz] = 0; }
	if(sum == 6)  {incrementMe(xx,yy,zz);}
	if(sum >= 99)  { u.universe[xx][yy][zz] = 0; }
	//}
	
	
}

public void fractalMetacell2(int xx, int yy, int zz){

	n.setNbrhood(4);
	
	int sum = 0;
	for(int i = 0; i < n.NBH.length; i++){
		sum += getNbrCounts(getWrap(xx, n.NBH[i][0], u.universe.length), getWrap(yy, n.NBH[i][1], u.universe[0].length), getWrap(zz, n.NBH[i][2], u.universe[0][0].length));
	}
	
	//if(sum > 0) {
	if(sum <= 15)  { u.universe[xx][yy][zz] = 0; }
	if(sum == 9)  {incrementMe(xx,yy,zz);}
	if(sum >= 26)  { u.universe[xx][yy][zz] = 0; }
	//}
	
	
}

public void fractalMetacell3(int xx, int yy, int zz){

	n.setNbrhood(4);
	
	int sum = 0;
	for(int i = 0; i < n.NBH.length; i++){
		sum += getNbrCounts(getWrap(xx, n.NBH[i][0], u.universe.length), getWrap(yy, n.NBH[i][1], u.universe[0].length), getWrap(zz, n.NBH[i][2], u.universe[0][0].length));
	}
	
	//if(sum > 0) {
	if(sum <= 16)  { u.universe[xx][yy][zz] = 0; }
	if(sum == 9)  {incrementMe(xx,yy,zz);}
	if(sum >= 26)  { u.universe[xx][yy][zz] = 0; }
	//}
	
	
}

public void fractalMetacell4(int xx, int yy, int zz){

	n.setNbrhood(4);
	
	int sum = 0;
	for(int i = 0; i < n.NBH.length; i++){
		sum += getNbrCounts(getWrap(xx, n.NBH[i][0], u.universe.length), getWrap(yy, n.NBH[i][1], u.universe[0].length), getWrap(zz, n.NBH[i][2], u.universe[0][0].length));
	}
	
	//if(sum > 0) {
	if(sum <= 10)  { u.universe[xx][yy][zz] = 0; }
	if(sum == 5)  {incrementMe(xx,yy,zz);}
	if(sum >= 22)  { u.universe[xx][yy][zz] = 0; }
	//}
	
	
}

public void fractalMetacell5(int xx, int yy, int zz){

	n.setNbrhood(4);
	
	int sum = 0;
	for(int i = 0; i < n.NBH.length; i++){
		sum += getNbrCounts(getWrap(xx, n.NBH[i][0], u.universe.length), getWrap(yy, n.NBH[i][1], u.universe[0].length), getWrap(zz, n.NBH[i][2], u.universe[0][0].length));
	}
	
	//if(sum > 0) {
	if(sum <= 16)  { u.universe[xx][yy][zz] = 0; }
	if(sum == 8)  {incrementMe(xx,yy,zz);}
	if(sum >= 26)  { u.universe[xx][yy][zz] = 0; }
	//}
	
	
}

public void fractalMetacell5_2(int xx, int yy, int zz){
	
	n.setNbrhood(3);
	
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
	if(sum >= 140 && sum <= 230)  {incrementMe(xx,yy,zz);}
	
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
	
	
	n.setNbrhood(4);
	
	int sum = 0;
	for(int i = 0; i < n.NBH.length; i++){
		sum += getNbrCounts(getWrap(xx, n.NBH[i][0], u.universe.length), getWrap(yy, n.NBH[i][1], u.universe[0].length), getWrap(zz, n.NBH[i][2], u.universe[0][0].length));
	}
	
	//if(sum > 0) {
	if(sum <= 0)  { u.universe[xx][yy][zz] = 0; }
	if(sum == 4)  {incrementMe(xx,yy,zz);}
	if(sum >= 99)  { u.universe[xx][yy][zz] = 0; }
	//}
	
	
}

public void fractal1(int xx, int yy, int zz){
	n.setNbrhood(21);
	
	int sum = 0;
	for(int i = 0; i < n.NBH.length; i++){
		sum += getNbrCounts(getWrap(xx, n.NBH[i][0], u.universe.length), getWrap(yy, n.NBH[i][1], u.universe[0].length), getWrap(zz, n.NBH[i][2], u.universe[0][0].length));
	}
	
	if(sum <= 99)  { u.universe[xx][yy][zz] = 0; }
	if(sum > 0 && sum < 4)  {incrementMe(xx,yy,zz);}
	
	
}

public void hex1(int xx, int yy, int zz){
	
	n.setNbrhood(21);
	
	int sum = nbrCountNotVal(xx, yy, zz, 0);
	
	if(sum <= 1)  { u.universe[xx][yy][zz] = 0; }
	if(sum >= 3)  {incrementMe(xx,yy,zz);}
	if(sum >= 5)  { u.universe[xx][yy][zz] = 0; }
	
	
}


	public void avgRand(int xx, int yy, int zz, int rand){
		

		n.setNbrhood(4);
			
			if(r.nextInt(rand) == 0) {u.universe[xx][yy][zz]=nbrAvg(xx,yy,zz);}
		
	}
	
	
	public void VonnFractal(int xx, int yy, int zz){
		n.setNbrhood(22);
		
		int sum = 0;
		for(int i = 0; i < n.NBH.length; i++){
			sum += getNbrCounts(getWrap(xx, n.NBH[i][0], u.universe.length), getWrap(yy, n.NBH[i][1], u.universe[0].length), getWrap(zz, n.NBH[i][2], u.universe[0][0].length));
		}
		
		if(sum <= 6*6)  { u.universe[xx][yy][zz] = 0; }
		if(sum >= 8 && sum <= 10 || (sum == 12) || (sum >= 25))  {incrementMe(xx,yy,zz);} //{5,8}{5,9,13}{5,11,12,:3}{6,7,18,:11}{7,8,10,:7}{5,10,13,16,:10}{5,10,13,16,18,:13}{5,11,12,15,:10}{}{}

		
		
		
		//if(sum >= 9 && sum <= 13)  { u.universe[xx][yy][zz] = 1; }
		//if(sum >= 6 && sum <= 6)  { u.universe[xx][yy][zz] = 1; }
		//if(sum >= /*17*/ /*18*/ 19 /*20*/)  { u.universe[xx][yy][zz] = 1; }
		
		
	}
	
	public void MooreFractal(int xx, int yy, int zz){
		
		n.setNbrhood(24);
		
		int sum = 0;
		for(int i = 0; i < n.NBH.length; i++){
			sum += getNbrCounts(getWrap(xx, n.NBH[i][0], u.universe.length), getWrap(yy, n.NBH[i][1], u.universe[0].length), getWrap(zz, n.NBH[i][2], u.universe[0][0].length));
		}
		
		if(sum <= 14*14*2)  { u.universe[xx][yy][zz] = 0; }
		if(sum <= 128 && sum > 0)  { 
			if(sum % 11 == 0){incrementMe(xx,yy,zz);}else{u.universe[xx][yy][zz] = 0;}
		}
	}
	
		public void MooreFractalColour(int xx, int yy, int zz){
			
			n.setNbrhood(24);
			
			int sum = 0;
			for(int i = 0; i < n.NBH.length; i++){
				sum += getNbrCounts(getWrap(xx, n.NBH[i][0], u.universe.length), getWrap(yy, n.NBH[i][1], u.universe[0].length), getWrap(zz, n.NBH[i][2], u.universe[0][0].length));
			}
			
			if(sum <= 14*14*2)  { u.universe[xx][yy][zz] = 0; }
			if(sum <= 47 && sum > 0)  { 
				
				if(sum % 2 == 0){u.universe[xx][yy][zz] = 1;}
				if(sum % 3 == 0){u.universe[xx][yy][zz] = -2;}
				if(sum % 5 == 0){u.universe[xx][yy][zz] = 3;}
				if(sum % 7 == 0){u.universe[xx][yy][zz] = 4;}
				if(sum % 11 == 0){u.universe[xx][yy][zz] = 5;}
				if(sum % 13 == 0){u.universe[xx][yy][zz] = 6;}
				if(sum % 17 == 0){u.universe[xx][yy][zz] = 7;}
				if(sum % 19 == 0){u.universe[xx][yy][zz] = 8;}
				if(sum % 23 == 0){u.universe[xx][yy][zz] = -9;}
				if(sum % 29 == 0){u.universe[xx][yy][zz] = 10;}
				if(sum % 31 == 0){u.universe[xx][yy][zz] = 11;}
				if(sum % 37 == 0){u.universe[xx][yy][zz] = 12;}
				if(sum % 41 == 0){u.universe[xx][yy][zz] = 13;}
				if(sum % 43 == 0){u.universe[xx][yy][zz] = -14;}
				if(sum % 47 == 0){u.universe[xx][yy][zz] = 15;}
			}
		
		//if(sum >= 9 && sum <= 13)  { u.universe[xx][yy][zz] = 1; }
		//if(sum >= 6 && sum <= 6)  { u.universe[xx][yy][zz] = 1; }
		//if(sum >= /*17*/ /*18*/ 19 /*20*/)  { u.universe[xx][yy][zz] = 1; }
		
		
	}
	
		public void mapPrev(int xx, int yy, int zz, int val){
			
			n.setNbrhood(23);
			
			int sum = 0;
			sum = u.snapshotUniverse[getWrap(xx, n.NBH[0][0], u.universe.length)][getWrap(yy, n.NBH[0][1], u.universe[0].length)][getWrap(zz, n.NBH[0][2], u.universe[0][0].length)];
			
			if(sum == 1) {u.universe[xx][yy][zz]+=val;}
		}
	
	 
		
		public void LangtonsAnt(int xx, int yy, int zz){

			n.setNbrhood(0);
			
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

			n.setNbrhood(4);
			
			
			
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
		
		public void TuringCliffBig(int xx, int yy, int zz){
			n.setNbrhood(30);
			
			
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

			n.setNbrhood(0);

				
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
		
		public void single_rotation(int xx, int yy, int zz){
			modClock++;
			/*modClock%5 /*1D, so makes diagonal lines, applied in serial. Need 2D version.*/ 
			
			//Perform 8 cell operations every 32 generations
			if( (xx*xx+yy*yy)%64 == gen%32 ){ 
				incrementMe(xx,yy,zz);
			}
			
			//Perform 8 cell operations every 32 generations
			/*if((modClock+gen)%8 == gen%32){ //if my cell in the list of cells is the  X'th one 
				incrementMe(xx,yy,zz);
			}*/
			
			/*
			int md = 3;			
			int genMod = gen%md;
			if( (xx)%md == genMod && (yy)%md == genMod) {
				
				int sum=0;
				for(int i = 0; i < 4; i++) {
					n.setNbrhood(40+i);
					sum += nbrCountNotVal(xx, yy, zz, 0);
				}
				
				if(sum == 1)  {incrementMe(xx,yy,zz);} else {
					u.universe[xx][yy][zz] = 0;
				}
				
				/*n.setNbrhood(40+modClock%4);
				int sum = nbrCountNotVal(xx, yy, zz, 0);
				if(sum == 2)  {incrementMe(xx,yy,zz);}else {
					u.universe[xx][yy][zz] = 0;
				}*/
				
			//}
			//}
		}    
		
		
		public void area_modclock(int xx, int yy, int zz){
			//modClock+=55;
			
			if( (xx*xx+yy*yy)%min_grow_die[0][0] == gen%128 ){ 
				incrementMe(xx,yy,zz);
			}
			
		}   
		
		public void area_modclock2(int xx, int yy, int zz){
			modClock++;
			
			if( (xx*xx+yy*yy+xx*yy)%min_grow_die[0][0] == gen%128 ){ 
				incrementMe(xx,yy,zz);
			}
			
		}    
		
		
		public void traffic2(int xx, int yy, int zz){

			n.setNbrhood(11);
			
			
			
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

			n.setNbrhood(11);
		
			
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
			n.setNbrhood(28);
			
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
		
		
		
		int insInstr = ins[0];
		int insZlay = ins[1];
		int insPar2 = ins[2];
		int insPar3 = ins[3];
		int insPar4 = ins[4];

		//big block in the middle
		if(instrOverride > -1) {
			if((xx < (m_xSize/2)+(m_xSize/4)) && (xx > (m_xSize/2)-(m_xSize/4)) ) {
				if((yy < (m_ySize/2)+(m_ySize/4)) && (yy > (m_ySize/2)-(m_ySize/4)) ) {
					
					//if(xx%32<=8 && yy%32<=8) {
					insInstr = instrOverride;
					//}
				}
			}
		}
		
		if(instrOverride == -2) {
			n.setNbrhood(29);
			insInstr = (nbrCountNotVal(xx,yy,zz,0)%50)+10;
			
		}
		
		//		Utility functions / other
		if((insInstr == 11 || insInstr == 0) && (insZlay == zz || insZlay == -1)) {conway(xx, yy, zz 						);}
		if(insInstr == -1 && (insZlay == zz || insZlay == -1)) {seed(xx, yy, zz, 						insPar2, insPar3, insPar4);}
		if(insInstr == 1  && (insZlay == zz || insZlay == -1)) {quantum(xx,yy,zz,						insPar2);}
		if(insInstr == 2  && (insZlay == zz || insZlay == -1)) {quantumWeight(xx,yy,zz,					insPar2);}
		if(insInstr == 3  && (insZlay == zz || insZlay == -1)) {probbilityGrowth(xx,yy,zz				);}
		if(insInstr == 4 && (insZlay == zz || insZlay == -1)) {avgVonNew(xx,yy,zz						);}
		if(insInstr == 5 && (insZlay == zz || insZlay == -1)) {mapPrev(xx,yy,zz,						insPar2);}
		if(insInstr == 68 && (insZlay == zz || insZlay == -1)) {platform(xx,yy,zz						);}
		if(insInstr == 85 && (insZlay == zz || insZlay == -1)) {exposeC(xx,yy,zz						);}
		if(insInstr == 86 && (insZlay == zz || insZlay == -1)) {exposeJ(xx,yy,zz						);}
		if(insInstr == 87 && (insZlay == zz || insZlay == -1)) {area_modclock(xx,yy,zz					);}
		if(insInstr == 88 && (insZlay == zz || insZlay == -1)) {conway_twinPrime(xx,yy,zz				);}
		
		
		
		//1D / Single Point starter required
		if(insInstr == 6  && (insZlay == zz || insZlay == -1)) {sierpenski(xx, yy, zz 					);}
		if(insInstr == 7  && (insZlay == zz || insZlay == -1)) {explorer(xx,yy,zz						);}
		if(insInstr == 84  && (insZlay == zz || insZlay == -1)) {explorer24(xx,yy,zz					);}
		//if(insInstr == 8  && (insZlay == zz || insZlay == -1)) {rule110(xx,yy,zz						);}
		if(insInstr == 9 && (insZlay == zz || insZlay == -1)) {snapSierpenski(xx,yy,zz 					);}
		if(insInstr == 10 && (insZlay == zz || insZlay == -1)) {hex1(xx,yy,zz							);}
		if(insInstr == 54 && (insZlay == zz || insZlay == -1)) {LangtonsAnt(xx,yy,zz					);}
		if(insInstr == 77 && (insZlay == zz || insZlay == -1)) {TuringCliff(xx,yy,zz					);}
		if(insInstr == 79 && (insZlay == zz || insZlay == -1)) {TuringCliffBig(xx,yy,zz					);}
		
		
		//		2D Neighbourhoods
		if((insInstr == 11 || insInstr == 0) && (insZlay == zz || insZlay == -1)) {conway(xx, yy, zz 						);}
		if(insInstr == 12 && (insZlay == zz || insZlay == -1)) {ConwayExtendedRange(xx, yy, zz 			);}
		if(insInstr == 13  && (insZlay == zz || insZlay == -1)) {ConwayExtendedRange2(xx, yy, zz 		);}
		if(insInstr == 55  && (insZlay == zz || insZlay == -1)) {ConwayExtendedRange3(xx, yy, zz 		);}
		if(insInstr == 56  && (insZlay == zz || insZlay == -1)) {ConwayExtendedRange4(xx, yy, zz 		);}
		if(insInstr == 57  && (insZlay == zz || insZlay == -1)) {ConwayExtendedRange5(xx, yy, zz 		);}
		if(insInstr == 58  && (insZlay == zz || insZlay == -1)) {ConwayExtendedRange6(xx, yy, zz 		);}
		if(insInstr == 59  && (insZlay == zz || insZlay == -1)) {ConwayExtendedRange7(xx, yy, zz 		);}
		if(insInstr == 60  && (insZlay == zz || insZlay == -1)) {ConwayExtendedRange8(xx, yy, zz 		);}
		if(insInstr == 61  && (insZlay == zz || insZlay == -1)) {ConwayExtendedRange9(xx, yy, zz 		);}
		if(insInstr == 62  && (insZlay == zz || insZlay == -1)) {ConwayExtendedRange10(xx, yy, zz 		);}
		if(insInstr == 63  && (insZlay == zz || insZlay == -1)) {ConwayExtendedRange11(xx, yy, zz 		);}
		if(insInstr == 64  && (insZlay == zz || insZlay == -1)) {ConwayExtendedRange12(xx, yy, zz 		);}
		if(insInstr == 65  && (insZlay == zz || insZlay == -1)) {ConwayExtendedRange13(xx, yy, zz 		);}
		if(insInstr == 70  && (insZlay == zz || insZlay == -1)) {ConwayExtendedRange13_2(xx, yy, zz 		);}
		if(insInstr == 66  && (insZlay == zz || insZlay == -1)) {ConwayExtendedRange14(xx, yy, zz 		);}
		if(insInstr == 14  && (insZlay == zz || insZlay == -1)) {rain2(xx,yy,zz							);}
		if(insInstr == 15  && (insZlay == zz || insZlay == -1)) {goop(xx,yy,zz							);}
		if(insInstr == 16 && (insZlay == zz || insZlay == -1)) {internalAffairs(xx,yy,zz				);}
		if(insInstr == 17 && (insZlay == zz || insZlay == -1)) {meekrochyp(xx,yy,zz						);}
		if(insInstr == 18 && (insZlay == zz || insZlay == -1)) {diamondShuffle(xx,yy,zz					);}
		if(insInstr == 19 && (insZlay == zz || insZlay == -1)) {rain(xx,yy,zz							);}
		if(insInstr == 20 && (insZlay == zz || insZlay == -1)) {warts(xx,yy,zz							);}
		if(insInstr == 21 && (insZlay == zz || insZlay == -1)) {Threads(xx,yy,zz 						);}
		if(insInstr == 22 && (insZlay == zz || insZlay == -1)) {warts2(xx,yy,zz 						);}
		if(insInstr == 23 && (insZlay == zz || insZlay == -1)) {Wave(xx,yy,zz, 							insPar2);}
		if(insInstr == 24 && (insZlay == zz || insZlay == -1)) {rope(xx,yy,zz 							);}
		if(insInstr == 25 && (insZlay == zz || insZlay == -1)) {Inverse110(xx,yy,zz 					);}
		if(insInstr == 26 && (insZlay == zz || insZlay == -1)) {Inverse110_2(xx,yy,zz 					);}
		if(insInstr == 27 && (insZlay == zz || insZlay == -1)) {Inverse110_leopard(xx,yy,zz 			);}
		if(insInstr == 28 && (insZlay == zz || insZlay == -1)) {PointToCircle(xx,yy,zz 					);}
		if(insInstr == 67 && (insZlay == zz || insZlay == -1)) {CyclicDaemon(xx,yy,zz 					);}

		//Rules that can have their rules customised
		if(insInstr == 72 && (insZlay == zz || insZlay == -1)) {extendedRangeCustom(xx,yy,zz 			);}
		if(insInstr == 73 && (insZlay == zz || insZlay == -1)) {extendedRangeCustom2(xx,yy,zz 			);}
		if(insInstr == 74 && (insZlay == zz || insZlay == -1)) {extendedRangeCustom3(xx,yy,zz 			);}
		if(insInstr == 75 && (insZlay == zz || insZlay == -1)) {extendedRangeCustom4(xx,yy,zz 			);}
		if(insInstr == 76 && (insZlay == zz || insZlay == -1)) {extendedRangeCustom5(xx,yy,zz 			);}
		if(insInstr == 78 && (insZlay == zz || insZlay == -1)) {extendedRangeCustom7(xx,yy,zz 			);}
		if(insInstr == 80 && (insZlay == zz || insZlay == -1)) {ThreadsCustom(xx,yy,zz 					);}
		if(insInstr == 81 && (insZlay == zz || insZlay == -1)) {ThreadsCustom2(xx,yy,zz 				);}
		if(insInstr == 82 && (insZlay == zz || insZlay == -1)) {ThreadsCustom3(xx,yy,zz 				);}
		if(insInstr == 83 && (insZlay == zz || insZlay == -1)) {ThreadsCustom4(xx,yy,zz 				);}
		
		
		
		//2-step Fractal Neighbourhoods
		if(insInstr == 29 && (insZlay == zz || insZlay == -1)) {fractalShip(xx,yy,zz					);}
		if(insInstr == 30 && (insZlay == zz || insZlay == -1)) {fractalChyp(xx,yy,zz					);}
		if(insInstr == 31 && (insZlay == zz || insZlay == -1)) {fractalPhase(xx,yy,zz					);}
		if(insInstr == 32 && (insZlay == zz || insZlay == -1)) {fractalGun(xx,yy,zz						);}
		if(insInstr == 33 && (insZlay == zz || insZlay == -1)) {fractalCount(xx,yy,zz					);}
		if(insInstr == 34 && (insZlay == zz || insZlay == -1)) {fractal1D(xx,yy,zz						);}
		if(insInstr == 35 && (insZlay == zz || insZlay == -1)) {fractal1D2(xx,yy,zz						);}
		if(insInstr == 36 && (insZlay == zz || insZlay == -1)) {fractal1D3(xx,yy,zz						);}
		if(insInstr == 37 && (insZlay == zz || insZlay == -1)) {fractal1D4(xx,yy,zz						);}
		if(insInstr == 38 && (insZlay == zz || insZlay == -1)) {fractal1D5(xx,yy,zz						);}
		if(insInstr == 39 && (insZlay == zz || insZlay == -1)) {fractal1D6(xx,yy,zz						);}
		if(insInstr == 41 && (insZlay == zz || insZlay == -1)) {fractalTime(xx,yy,zz					);}
		if(insInstr == 40 && (insZlay == zz || insZlay == -1)) {fractalMetacell(xx,yy,zz				);}
		if(insInstr == 42 && (insZlay == zz || insZlay == -1)) {fractalMetacell2(xx,yy,zz				);}
		if(insInstr == 43 && (insZlay == zz || insZlay == -1)) {fractalMetacell3(xx,yy,zz				);}
		if(insInstr == 44 && (insZlay == zz || insZlay == -1)) {fractalMetacell4(xx,yy,zz				);}
		if(insInstr == 45 && (insZlay == zz || insZlay == -1)) {fractalMetacell5(xx,yy,zz				);}
		if(insInstr == 71 && (insZlay == zz || insZlay == -1)) {fractalMetacell5_2(xx,yy,zz				);}
		if(insInstr == 46 && (insZlay == zz || insZlay == -1)) {fractalMetacell6(xx,yy,zz				);}
		if(insInstr == 47 && (insZlay == zz || insZlay == -1)) {fractal1(xx,yy,zz						);}
		if(insInstr == 69 && (insZlay == zz || insZlay == -1)) {meekrochypFr(xx,yy,zz					);}
		
		//3D Neighbourhoods
		if(insInstr == 48 && (insZlay == zz || insZlay == -1)) {diffusion(xx,yy,zz, 					insPar2, insPar3);}
		if(insInstr == 49 && (insZlay == zz || insZlay == -1)) {Brownian(xx,yy,zz, 						insPar2);}
		
		if(insInstr == 50 && (insZlay == zz || insZlay == -1)) {actual3D(xx,yy,zz						);}
		if(insInstr == 51 && (insZlay == zz || insZlay == -1)) {VonnFractal(xx,yy,zz					);}
		if(insInstr == 52 && (insZlay == zz || insZlay == -1)) {MooreFractal(xx,yy,zz					);}
		if(insInstr == 53 && (insZlay == zz || insZlay == -1)) {MooreFractalColour(xx,yy,zz				);}
		
		
		
		
		
		
		

	}

	
}

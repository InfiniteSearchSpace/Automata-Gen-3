import java.util.Random;


public class automataLib {
	
	Random r = new Random();
	neighbours n;
	
	dataSources d;
	Universe u;
	Main m;
	
	int[][] instructions; //holds the actions to perform
	
	//constructor
	public automataLib(Main mm) {
		m = mm;
	}
	
	//takes a universe and the dataSources object for writing
	public void setTargetUni(Universe uu, dataSources dd) {
		u=uu;
		d=dd;
	}
	
	
	
	/*  = = = = = = = = = = = = = = = = = = = = 
	 *  = = = = = = = = = = = = = = = = = = = = 
	 * 		 	Value setting & modifications
	 *  = = = = = = = = = = = = = = = = = = = = 
	 *  = = = = = = = = = = = = = = = = = = = = 
	 */ 
	

	//copy pattern into all layers & cells, tile.
	public void writeData(int[] dd) {
		for(int i = 0; i < u.universe.length; i++) {
			for (int j = 0; j < u.universe[0].length; j++) {
				for (int k = 0; k < u.universe[0][0].length; k++) {
					u.universe[i][j][k] = d.readNext(dd);
				}
			}
    	}
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
	
    //draws a line of val. Can be solid or rand, can be veto'd, optional overwrite wit 0.    
    public void placeLine(int xx, int yy, int zz, int rand, int len, boolean placeO, int veto, int val) {
    	
    	if(r.nextInt(veto) == 0) { //chance to not write the line
		    for(int i = 0; i < len-xx; i++) {
		    	if(r.nextInt(rand) == 0) {
		    		placeval(xx+i, yy, zz, 1, val);
		    	} else if(placeO) {placeval(xx+i, yy, zz, 1, 0);}
		    }
    	}
    	
    }
    
    //with chance, set cell to val 
    public void seed(int xx, int yy, int zz, int rand, int xnullx, int val){ //chance to seed location
    	if(r.nextInt(rand) == 0) {placeval(xx, yy, zz, 1, val);}
    }
    
    //with chance, reset pixel to val, applies to whole universe
    public void seedAll(int rand, int val){
    	
    	for(int i = 0; i < u.universe.length; i++) {
			for (int j = 0; j < u.universe[0].length; j++) {
				for (int k = 0; k < u.universe[0][0].length; k++) {
			    	seed(i,j,k,rand,-1,val);
				}
			}
    	}
    }
    
    //chance to seed every pixel on this layer
    public void seedZ(int rand, int zz, int val){ 
    	for(int i = 0; i < u.universe.length; i++) {
			for (int j = 0; j < u.universe[0].length; j++) {
			    seed(i,j,zz,rand, 0, val);
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
	
	//returns the average value among neighbours
	public int nbrGetAverage(int xx, int yy, int zz, int val){
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
		
		
		if(isOne <= 3)  { u.universe[xx][yy][zz] = 0; }
		if(isOne == 4)  { u.universe[xx][yy][zz] = 1; }
		if(isOne >= 5)  { u.universe[xx][yy][zz] = 0; }
		if(isOne == 6)  { u.universe[xx][yy][zz] = 1; }
		/*if(isOne == 7)  { u.universe[xx][yy][zz] = 0; }
		if(isOne == 8)  { u.universe[xx][yy][zz] = 0; }
		//if(isOne == 2)  { u.universe[xx][yy][zz] = 1; }*/
		//if(isOne > 3)  { u.universe[xx][yy][zz] = 0; }
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
	
	
	
	
	
	
	
	
	
	
	/////////////////////////////////////////////
	/////////Select Instruction to run///////////
	/////////////////////////////////////////////
																								  //Random, Threshold, Value
	public void readInstructions(int[] ins, int xx, int yy, int zz) {
		if(ins[0] == -1 && (ins[1] == zz || ins[1] == -1)) {seed(xx, yy, zz, 						ins[2], ins[3], ins[4]);}
		if(ins[0] == 3  && (ins[1] == zz || ins[1] == -1)) {quantum(xx,yy,zz,						ins[2]);}
		if(ins[0] == 5  && (ins[1] == zz || ins[1] == -1)) {quantumWeight(xx,yy,zz,					ins[2]);}
		if(ins[0] == 18 && (ins[1] == zz || ins[1] == -1)) {diffusion(xx,yy,zz, 					ins[2], ins[3]);}
		if(ins[0] == 19 && (ins[1] == zz || ins[1] == -1)) {Brownian(xx,yy,zz, 						ins[2]);}
		
		if(ins[0] == 1  && (ins[1] == zz || ins[1] == -1)) {sierpenski(xx, yy, zz 					);}
		if(ins[0] == 4  && (ins[1] == zz || ins[1] == -1)) {explorer(xx,yy,zz						);}
		if(ins[0] == 7  && (ins[1] == zz || ins[1] == -1)) {rule110(xx,yy,zz						);}
		if(ins[0] == 22 && (ins[1] == zz || ins[1] == -1)) {snapSierpenski(xx,yy,zz 				);}
		
		if(ins[0] == 0  && (ins[1] == zz || ins[1] == -1)) {conway(xx, yy, zz 						);}
		if(ins[0] == 25 && (ins[1] == zz || ins[1] == -1)) {ConwayExtendedRange(xx, yy, zz 			);}
		if(ins[0] == 2  && (ins[1] == zz || ins[1] == -1)) {ConwayExtendedRange2(xx, yy, zz 		);}
		if(ins[0] == 8  && (ins[1] == zz || ins[1] == -1)) {rain2(xx,yy,zz							);}
		if(ins[0] == 9  && (ins[1] == zz || ins[1] == -1)) {goop(xx,yy,zz							);}
		if(ins[0] == 10 && (ins[1] == zz || ins[1] == -1)) {internalAffairs(xx,yy,zz				);}
		if(ins[0] == 11 && (ins[1] == zz || ins[1] == -1)) {meekrochyp(xx,yy,zz						);}
		if(ins[0] == 12 && (ins[1] == zz || ins[1] == -1)) {diamondShuffle(xx,yy,zz					);}
		if(ins[0] == 13 && (ins[1] == zz || ins[1] == -1)) {rain(xx,yy,zz							);}
		if(ins[0] == 15 && (ins[1] == zz || ins[1] == -1)) {warts(xx,yy,zz							);}
		if(ins[0] == 16 && (ins[1] == zz || ins[1] == -1)) {Threads(xx,yy,zz 						);}
		if(ins[0] == 20 && (ins[1] == zz || ins[1] == -1)) {warts2(xx,yy,zz 						);}
		if(ins[0] == 21 && (ins[1] == zz || ins[1] == -1)) {Wave(xx,yy,zz, 							ins[2]);}
		if(ins[0] == 23 && (ins[1] == zz || ins[1] == -1)) {rope(xx,yy,zz 							);}
		if(ins[0] == 24 && (ins[1] == zz || ins[1] == -1)) {Inverse110(xx,yy,zz 					);}
		if(ins[0] == 26 && (ins[1] == zz || ins[1] == -1)) {Inverse110_2(xx,yy,zz 					);}
		if(ins[0] == 27 && (ins[1] == zz || ins[1] == -1)) {Inverse110_leopard(xx,yy,zz 			);}
		if(ins[0] == 17 && (ins[1] == zz || ins[1] == -1)) {PointToCircle(xx,yy,zz 					);}
		if(ins[0] == 6  && (ins[1] == zz || ins[1] == -1)) {probbilityGrowth(xx,yy,zz				);}
		if(ins[0] == 28 && (ins[1] == zz || ins[1] == -1)) {avgVonNew(xx,yy,zz						);}
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
		if(ins[0] == 46 && (ins[1] == zz || ins[1] == -1)) {fractalMetacell6(xx,yy,zz				);}
		if(ins[0] == 47 && (ins[1] == zz || ins[1] == -1)) {fractal1(xx,yy,zz						);}
		if(ins[0] == 48 && (ins[1] == zz || ins[1] == -1)) {hex1(xx,yy,zz						);}
		
		if(ins[0] == 14 && (ins[1] == zz || ins[1] == -1)) {actual3D(xx,yy,zz						);}
		
		
		
		
		
		
		
		

	}

	
}

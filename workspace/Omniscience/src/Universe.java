
public class Universe {

	//define the container of the universe array 
	public int[][][] universe; 				//Dimensions considered x, y, z
	public int[][][] snapshotUniverse;		//Stores a unchanged copy of the whole exact universal state
	
	public int[][] instructions;			//Contains the data for this universe's actions
	
	public int maxVal; //for strictly display purposes according to philosophy. Knowing or referencing this value would cause universal nonlocality.
	
	int xlen;
	int ylen;
	int zlen; 
	
	public boolean paused = false;
	
	automataLib a;
	dataSources d;
	
	//set the universal array
	public Universe(int x, int y, int z, automataLib aa/*, int[][] instr*/) { 
		System.out.println("Universe" + this);
		xlen = x;
		ylen = y;
		zlen = z;
		
		a=aa;
		
		universe = new int[x][y][z];
		snapshotUniverse = new int[x][y][z];
		
		//instructions = instr;
		
		resetAr(0); 			//sets each x,y,z to 0; instanciating the array.
		System.out.println("Universe" + this + "set to 0");
		//runOnce(); 				//for actions to be run only once and before anything else in the sim. Garden of Eden maker.
		System.out.println("Run Once Done");
	}
	
	//hard resets all the xyz values to val, resets maxval = 1.
	public void resetAr(int val) { 

		
		for(int i = 0; i < universe.length; i++) {
			for (int j = 0; j < universe[0].length; j++) {
				for (int k = 0; k < universe[0][0].length; k++) {
					universe[i][j][k] = val;
				}
			}
		}
		
		maxVal = val;
		if(maxVal < 1) {maxVal = 1;}
		
	}
	
	//resets the z-layer only, to this value
	public void resetArZ(int val, int zz) { 

		
		for(int i = 0; i < universe.length; i++) {
			for (int j = 0; j < universe[0].length; j++) {
					universe[i][j][zz] = val;
			}
		}
		
		maxVal = val;
		if(maxVal < 1) {maxVal = 1;}
		
	}

	
	//Snapshots the universe in it's current state, storing all values exactly as is, in snapshotUniverse[][][]
	public void snap() {
		for(int i = 0; i < universe.length; i++) {
			for (int j = 0; j < universe[0].length; j++) {
				for (int k = 0; k < universe[0][0].length; k++) {
					snapshotUniverse[i][j][k] = universe[i][j][k]; 	//copy the array
				}
			}
		}
	}
	
	//Takes the Z frame and resets it to the maxval for just that frame
	public /*int*/void maxValAudit(int k) {
		maxVal = 1;
		//int total = 0;
		for(int i = 0; i < universe.length; i++) {
			for (int j = 0; j < universe[0].length; j++) {
				if(universe[i][j][k] > maxVal) { maxVal = universe[i][j][k]; }
				//total+=universe[i][j][k];
			}
		}
		//return total;
	}
	
	/////////////////////////////////////////////
	//            UNIVERSE CONTROL             //
	/////////////////////////////////////////////
	
    //Garden of Eden, absolute seed for t=0
    public void runOnce(int rand, int val) {
    	a.setTargetUni(this, d);
    	a.seedAll(rand,val);
    }
    	
    //handles the universal calculations 
    public void updateUniverse() { 
    	if (!paused) {
    		
	    	snap(); 					//snapshots the board to u.snapshotUniverse to ensure all functions have equal execution options
	    	a.setTargetUni(this, d);	//Ensures that the logic controller acts apon THIS universe
	    	
	    	
	    	for(int i = 0; i < xlen; i++) {
				for (int j = 0; j < ylen; j++) {
					for (int k = 0; k < zlen; k++) {
						 for (int ii = 0; ii < instructions.length; ii++) {
							 
							 a.readInstructions(instructions[ii], i, j, k);
							 
						 }

						 	//There's no point trying to explain this CA below, it's arbatrary code combinations. 
							//If you want to test it, it runs on 8 z-layers
						 	//Kept here as an example of how to hard-code a function for a big speed boost
					/*	if(k==0) {a.dozIfVal(i, j, k, 4, 6, 1, 1); a.dozDelMeIfNotLVal(i, j, k, 0, 5, 1); a.quantum(i,j,k,16);}
						if(k==1) {a.quantum(i,j,k,512); a.explorer(i, j, k); a.dozDelIfVal(i, j, k, 1, 0); a.dozDelIfNotVal(i, j, k, 1, 0); a.dozIfVal(i, j, k, 1, 0, 1, 10000);}
						if(k==2) {a.doz021(i, j, k, 1);}
						if(k==3) {a.dozIfVal(i, j, k, 1, 0, 1, 1024); a.dozIfVal(i, j, k, 2, 2, 1, 1);  a.dozDelMeIfVal(i, j, k, 1, 0); a.dozDelMeIfVal(i, j, k, 7, 2); a.conway(i, j, k, 1, 1);}
						if(k==4) {a.dozDelMeIfVal(i, j, k, 1, 0);a.quantumLorE(i,j,k,0,64);a.explorer(i, j, k); a.dozIfVal(i, j, k, 1, 3, 1, 512); a.dozExclude(i, j, k, 1, 0, 1);}  
						if(k==5) {a.dozIncrementIfVal(i, j, k, 1, 4, 1, 1); a.dozDelMeIfNotVal(i, j, k, 1, 4, 16);}
						if(k==6) {a.dozDelMeIfVal(i, j, k, 1, 3);a.dozIncrementIfVal(i, j, k, 4, 5, 1, 1); a.dozIncrementIfVal(i, j, k, 8, 5, 1, 1); a.quantumWeight(i,j,k,4);}
						if(k==7) {a.dozIfVal(i, j, k, 1, 0, 1, 1); a.quantum(i,j,k,512); a.explorer(i, j, k); a.dozDelIfVal(i, j, k, 1, 0); a.dozDelIfNotVal(i, j, k, 1, 0); a.dozIfVal(i, j, k, 1, 0, 1, 10000);}
					/**/
						 
					}
				}
	    	}
	    	
    	}
    	
    	
    }

	
}

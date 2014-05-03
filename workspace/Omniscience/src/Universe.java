
public class Universe {

	//define the container of the universe array 
	public int[][][] universe; 				//Dimensions considered x, y, z
	public int[][][] snapshotUniverse;		//Stores a unchanged copy of the whole exact universal state
	
	public int[][] instructions;			//Contains the data for this universe's actions
	
	public int maxVal; //for strictly display purposes according to philosophy. Knowing or referencing this value would cause universal nonlocality.
	public int minVal;
	
	int xlen;
	int ylen;
	int zlen; 
	
	public boolean paused = false;
	
	automataLib a;
	dataSources d;
	
	//set the universal array
	public Universe(int x, int y, int z, automataLib aa) { 
		System.out.println("Universe" + this);
		xlen = x;
		ylen = y;
		zlen = z;
		
		a=aa;
		
		universe = new int[x][y][z];
		snapshotUniverse = new int[x][y][z];

		resetAr(0); 			//sets each x,y,z to 0; instanciating the array.
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
		minVal = -1;
		//int total = 0;
		for(int i = 0; i < universe.length; i++) {
			for (int j = 0; j < universe[0].length; j++) {
				if(universe[i][j][k] > maxVal) { maxVal = universe[i][j][k]; }
				if(universe[i][j][k] < minVal) { minVal = universe[i][j][k]; }
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
    	a.setTargetUni(this);
    	a.seedAll(rand,val,1);
    }
    	
    //handles the universal calculations 
    public void updateUniverse() { 
    	if (!paused) {
    		
	    	snap(); 					//snapshots the board to u.snapshotUniverse to ensure all functions have equal execution options
	    	a.setTargetUni(this);	//Ensures that the logic controller acts apon THIS universe

	    	for(int i = 0; i < xlen; i++) {
				for (int j = 0; j < ylen; j++) {
					for (int k = 0; k < zlen; k++) {
						
						for (int ii = 0; ii < instructions.length; ii++) {
							 //select the instruction from the array, and execute the function
							 a.readInstructions(instructions[ii], i, j, k);
						 }
					 
					}
				}
	    	}
	    	
    	}
    }

	
}

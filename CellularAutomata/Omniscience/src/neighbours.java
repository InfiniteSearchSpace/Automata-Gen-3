import java.util.Random;



public class neighbours {
	int[][] NBH;
	Random r = new Random();
	int hoodCount = 42+3;
	int custHood = 0;
	int instrNum=0;
	int[] useCustNbrAr = {-1};
	int useCustom = 0;
	int[][] TFNbH;
	//constructor
	public neighbours(int countNbr) {
	//System.out.println("neighbours");
		NBH = new int[countNbr][3]; //initiate array with required number of neighbour slots
		TFNbH = new int[0][0];
	}
	//called to define a neighbour's position (x,y,z,neighbour id)
	public void setNBH(int xx, int yy, int zz, int nbr) {
		NBH[nbr][0] = xx;
		NBH[nbr][1] = yy;
		NBH[nbr][2] = zz;
	}
	
	public void newCNAr(int instrCount){
		int oldLen = useCustNbrAr.length;
		
		int[] newAr = new int[instrCount];
		
		int newLen = 0;
		if(oldLen < instrCount) {newLen = oldLen;}
		else {newLen = instrCount;}
		
		System.out.println(oldLen + ", " + instrCount + " ~ " + newLen);
		
		if(newLen == oldLen && oldLen < instrCount) {
			for(int i = 0; i < newLen+1; i++) {
				newAr[i]=-1;
			}
		}
		
		for(int i = 0; i < newLen; i++) {
			newAr[i]=useCustNbrAr[i];
		}
		
		useCustNbrAr = newAr;
	}
	
	public void resetNbrAr(){
		for(int i = 0; i < useCustNbrAr.length; i++) {
			useCustNbrAr[i]=-1;
		}
	}
	
	public void setNbrhoodAr(int nbhood) {
		useCustNbrAr[useCustNbrAr.length-1] = nbhood;
	}
	
	public void setIndexNbrhoodAr(int nbhood, int ruleIndex) {
		useCustNbrAr[ruleIndex] = nbhood;
	}
	
	public void newNbrhood(int countNbr) {
		NBH = new int[countNbr][3]; //initiate array with required number of neighbour slots
	}
	
	public void setNbrhood(int hood){
		
		
		if(useCustom == 1 && useCustNbrAr[instrNum] != -1) {hood = useCustNbrAr[instrNum];}
		
		if(hood == -2) {
			
			newNbrhood(TFNbH.length);
			
			for (int i = 0; i < TFNbH.length; i++){
				setNBH(TFNbH[i][0], TFNbH[i][1], TFNbH[i][2], i);
			}
			
		}
		
		if(hood == 0) {
			newNbrhood(4);
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
		
		if(hood == 10) {
			newNbrhood(5);
			setNBH(0, -1, 0, 0);
			setNBH( 0, 1, 0, 1);	
			setNBH( -1, 0, 0, 2);
			setNBH( 1, 0, 0, 3);
			setNBH( 0, 0, 0, 4);
		}
		
		if(hood == 11) {
			newNbrhood(2);
			setNBH(0, -1, 0, 0);
			setNBH(-1, 0, 0, 1);
		}
		
		
		if(hood == 12) {
			newNbrhood(3);
			setNBH( -1, -1, 0, 0);
			setNBH( 0, -1, 0, 1);	
			setNBH( -1, 0, 0, 2);
		}
		
		if(hood == 13) {
			newNbrhood(3);
			setNBH(-1, -1, 0, 0);
			setNBH(0, -1, 0, 1);
			setNBH(1, -1, 0, 2);
		}
		
		if(hood == 14) {
			newNbrhood(3);
			setNBH(-1, 0, 0, 0);
			setNBH(0, -1, 0, 1);
			setNBH(1, 0, 0, 2);
		}
		
		
		if(hood == 15) {
			newNbrhood(3);
			setNBH(0, -2, 0, 0);
			setNBH(-2, 0, 0, 1);
			setNBH(-1, -1, 0, 2);
		}
		
		
		if(hood == 16) {
			newNbrhood(4);
			setNBH(1, -1, 0, 0);
			setNBH(-1, -1, 0, 1);
			setNBH(0, -1, 0, 2);
			setNBH(0, -2, 0, 3);
		}
		
		if(hood == 17) {
			newNbrhood(4);
			setNBH(0, -2, 0, 0);
			setNBH(-2, 0, 0, 1);
			setNBH(-1, -1, 0, 2);
			setNBH(-2, -2, 0, 3);
		}
		
		
		if(hood == 18) {
			newNbrhood(5);
			setNBH(-1, -1, 0, 0);
			setNBH(1, -1, 0, 1);
			setNBH(0, -2, 0, 2);
			setNBH(-1, -2, 0, 3);
			setNBH(1, -2, 0, 4);	
		}
		
		
		if(hood == 19) {
			newNbrhood(9);
			setNBH(-1, -3, 0, 0);
			setNBH(1, -3, 0, 1);
			setNBH(-2, -3, 0, 2);
			setNBH(2, -3, 0, 3);
			setNBH(0, -3, 0, 4);
			setNBH(-1, -2, 0, 5);
			setNBH(0, -2, 0, 6);
			setNBH(1, -2, 0, 7);
			setNBH(0, -1, 0, 8);
		}
		
		
		if(hood == 20) {
			newNbrhood(6);
			setNBH( -3, -1, 0, 0);
			setNBH( 3, -1, 0, 1);
			setNBH( -2, -2, 0, 2);
			setNBH( 2, -2, 0, 3);
			setNBH( -1, -1, 0, 4);
			setNBH( -1, -1, 0, 5);
		}
		
		
		if(hood == 21) {
			newNbrhood(6);
			setNBH(0, -1, 0, 0);
			setNBH(-1, 0, 0, 1);	
			setNBH(0, 1, 0, 2);
			setNBH(1, 0, 0, 3);	
			setNBH(-1, -1, 0, 4);
			setNBH(1, 1, 0, 5);
		}
		
		
		if(hood == 22) {
			newNbrhood(6);
			setNBH(-1, 0, 0, 0);
			setNBH( 1, 0, 0, 1);	
			setNBH( 0,-1, 0, 2);
			setNBH( 0, 1, 0, 3);	
			setNBH( 0, 0,-1, 4);
			setNBH( 0, 0, 1, 5);
		}
	
		
		if(hood == 23) {
			newNbrhood(1);
			setNBH(0, 0, -1, 0);
		}
		
		
		if(hood == 24) {
			newNbrhood(14);
			setNBH(0, 0, -1, 0);
			setNBH(0, -1, 0, 1);
			setNBH(-1, 0, 0, 2);
			setNBH(0, 0, 1, 3);
			setNBH(0, 1, 0, 4);
			setNBH(1, 0, 0, 5);
			
			setNBH(-1, -1, -1, 6);
			setNBH(-1, -1, 1, 7);
			setNBH(-1, 1, -1, 8);
			setNBH(-1, 1, 1, 9);
			
			setNBH(1, 1, 1, 10);
			setNBH(1, 1, -1, 11);
			setNBH(1, -1, 1, 12);
			setNBH(1, -1, -1, 13);
		}

		
		if(hood == 25) {
			newNbrhood(7);
			setNBH(1, -1, 0, 0);
			setNBH(-1, -1, 0, 1);
			setNBH(0, -1, 0, 2);
			setNBH(2, -1, 0, 5);
			setNBH(-2, -1, 0, 6);
			setNBH(1, -2, 0, 3);
			setNBH(-1, -2, 0, 4);
		}
		
		
		if(hood == 26) {
			newNbrhood(8);
			setNBH(1, -1, 0, 0);
			setNBH(-1, -1, 0, 1);
			setNBH(0, -1, 0, 2);
			setNBH(2, -1, 0, 5);
			setNBH(-2, -1, 0, 6);
			setNBH(1, -2, 0, 3);
			setNBH(-1, -2, 0, 4);
			setNBH(0, 0, 0, 7);
		}
		
		
		if(hood == 27) {
			newNbrhood(80);
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
			
			
			setNBH( -3, -3, 0, 24);
			setNBH( -2, -3, 0, 25);
			setNBH( -1, -3, 0, 26);
			setNBH( 0, -3, 0, 27);
			setNBH( 1, -3, 0, 28);
			setNBH( 2, -3, 0, 29);
			setNBH( 3, -3, 0, 30);
			
			setNBH( -3, 3, 0, 31);
			setNBH( -2, 3, 0, 32);
			setNBH( -1, 3, 0, 33);
			setNBH( 0, 3, 0, 34);
			setNBH( 1, 3, 0, 35);
			setNBH( 2, 3, 0, 36);
			setNBH( 3, 3, 0, 37);
			
			setNBH( -3, -2, 0, 38);
			setNBH( -3, -1, 0, 39);
			setNBH( -3, 0, 0, 40);
			setNBH( -3, 1, 0, 41);
			setNBH( -3, 2, 0, 42);
			
			setNBH( 3, -2, 0, 43);
			setNBH( 3, -1, 0, 44);
			setNBH( 3, 0, 0, 45);
			setNBH( 3, 1, 0, 46);
			setNBH( 3, 2, 0, 47);
			
			setNBH(0,-4,0,48);
			setNBH(1,-4,0,49);
			setNBH(2,-4,0,50);
			setNBH(3,-4,0,51);
			setNBH(4,-4,0,52);
			setNBH(4,-3,0,53);
			setNBH(4,-2,0,54);
			setNBH(4,-1,0,55);
			setNBH(4,0,0,56);
			setNBH(4,1,0,57);
			setNBH(4,2,0,58);
			setNBH(4,3,0,59);
			setNBH(4,4,0,60);
			setNBH(3,4,0,61);
			setNBH(2,4,0,62);
			setNBH(1,4,0,63);
			setNBH(0,4,0,64);
			setNBH(-1,4,0,65);
			setNBH(-2,4,0,66);
			setNBH(-3,4,0,67);
			setNBH(-4,4,0,68);
			setNBH(-4,3,0,69);
			setNBH(-4,2,0,70);
			setNBH(-4,1,0,71);
			setNBH(-4,0,0,72);
			setNBH(-4,-1,0,73);
			setNBH(-4,-2,0,74);
			setNBH(-4,-3,0,75);
			setNBH(-4,-4,0,76);
			setNBH(-3,-4,0,77);
			setNBH(-2,-4,0,78);
			setNBH(-1,-4,0,79);
		}
		
		
		if(hood == 28) {
			newNbrhood(9);
			setNBH( 0, 0, 0, 0);
			setNBH( 1, 0, 0, 1);
			setNBH( -1, 0, 0, 2);
			setNBH( 0, 1, 0, 3);
			setNBH( 0, -1, 0, 4);

			setNBH( 1, 0, 0, 5);
			setNBH( -1, 0, 0, 6);
			setNBH( 0, 1, 0, 7);
			setNBH( 0, -1, 0, 8);
		}
		
		
		if(hood == 29) {
			newNbrhood(48);
		
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
			
			
			setNBH( -3, -3, 0, 24);
			setNBH( -2, -3, 0, 25);
			setNBH( -1, -3, 0, 26);
			setNBH( 0, -3, 0, 27);
			setNBH( 1, -3, 0, 28);
			setNBH( 2, -3, 0, 29);
			setNBH( 3, -3, 0, 30);
			
			setNBH( -3, 3, 0, 31);
			setNBH( -2, 3, 0, 32);
			setNBH( -1, 3, 0, 33);
			setNBH( 0, 3, 0, 34);
			setNBH( 1, 3, 0, 35);
			setNBH( 2, 3, 0, 36);
			setNBH( 3, 3, 0, 37);
			
			setNBH( -3, -2, 0, 38);
			setNBH( -3, -1, 0, 39);
			setNBH( -3, 0, 0, 40);
			setNBH( -3, 1, 0, 41);
			setNBH( -3, 2, 0, 42);
			
			setNBH( 3, -2, 0, 43);
			setNBH( 3, -1, 0, 44);
			setNBH( 3, 0, 0, 45);
			setNBH( 3, 1, 0, 46);
			setNBH( 3, 2, 0, 47);
		}
		
		
		
		if(hood == 30) {
			newNbrhood(32);
			setNBH(0,-4,0,0);
			setNBH(1,-4,0,1);
			setNBH(2,-4,0,2);
			setNBH(3,-4,0,3);
			setNBH(4,-4,0,4);
			setNBH(4,-3,0,5);
			setNBH(4,-2,0,6);
			setNBH(4,-1,0,7);
			setNBH(4,0,0,8);
			setNBH(4,1,0,9);
			setNBH(4,2,0,10);
			setNBH(4,3,0,11);
			setNBH(4,4,0,12);
			setNBH(3,4,0,13);
			setNBH(2,4,0,14);
			setNBH(1,4,0,15);
			setNBH(0,4,0,16);
			setNBH(-1,4,0,17);
			setNBH(-2,4,0,18);
			setNBH(-3,4,0,19);
			setNBH(-4,4,0,20);
			setNBH(-4,3,0,21);
			setNBH(-4,2,0,22);
			setNBH(-4,1,0,23);
			setNBH(-4,0,0,24);
			setNBH(-4,-1,0,25);
			setNBH(-4,-2,0,26);
			setNBH(-4,-3,0,27);
			setNBH(-4,-4,0,28);
			setNBH(-3,-4,0,29);
			setNBH(-2,-4,0,30);
			setNBH(-1,-4,0,31);
		}
		
		

		if(hood == 31) {
			int n = r.nextInt(8)+1;
			newNbrhood(n);
			
			for (int ii = 0; ii < NBH.length; ii++) {
				setNBH(r.nextInt(3)-1, r.nextInt(3)-1, 0, ii);
			}
		}
		

		if(hood == 32) {
			int n = r.nextInt(64)+1;
			newNbrhood(n);
			
			for (int ii = 0; ii < NBH.length; ii++) {
				setNBH(r.nextInt(9)-4, r.nextInt(9)-4, 0, ii);
			}
		}
		
		if(hood == 33) {
			newNbrhood(12);
			setNBH( 0, -1, 0, 0);
			setNBH( 1, -1, 0, 1);
			setNBH( 1, 0, 0, 2);
			setNBH( 1, 1, 0, 3);
			setNBH( 0, 1, 0, 4);
			setNBH( -1, 1, 0, 5);
			setNBH( -1, 0, 0, 6);
			setNBH( -1, -1, 0, 7);
			setNBH( 0, 2, 0, 8);
			setNBH( 0, -2, 0, 9);
			setNBH( 2, 0, 0, 10);
			setNBH( -2, 0, 0, 11);
		}
		
		if(hood == 34) {
			newNbrhood(20);
			setNBH( 0, -1, 0, 0);
			setNBH( 1, -1, 0, 1);
			setNBH( 1, 0, 0, 2);
			setNBH( 1, 1, 0, 3);
			setNBH( 0, 1, 0, 4);
			setNBH( -1, 1, 0, 5);
			setNBH( -1, 0, 0, 6);
			setNBH( -1, -1, 0, 7);
			
			setNBH( 0, 2, 0, 8);
			setNBH( 0, -2, 0, 9);
			setNBH( 2, 0, 0, 10);
			setNBH( -2, 0, 0, 11);

			setNBH( 2, 1, 0, 12);
			setNBH( 2, -1, 0, 13);
			setNBH( 1, 2, 0, 14);
			setNBH( -1, 2, 0, 15);
			setNBH( -2, 1, 0, 16);
			setNBH( -2, -1, 0, 17);
			setNBH( 1, -2, 0, 18);
			setNBH( -1, -2, 0, 19);
		}
		
		
		if(hood == 35 ) {
			newNbrhood(17);
			setNBH( -1, -2, 0, 0);
			setNBH( 0, -2, 0, 1);
			setNBH( 0, -1, 0, 2);
			setNBH( 1, -2, 0, 3);
			setNBH( 2, -2, 0, 4);
			setNBH( 2, -1, 0, 5);
			setNBH( 2, 0, 0, 6);
			setNBH( 2, 1, 0, 7);
			setNBH( 2, 2, 0, 8);
			setNBH( 1, 2, 0, 9);
			setNBH( 0, 2, 0, 10);
			setNBH( -1, 2, 0, 11);
			setNBH( -2, 2, 0, 12);
			setNBH( -2, 1, 0, 13);
			setNBH( -1, 0, 0, 14);
			setNBH( -2, 0, 0, 15);
			setNBH( -2, -1, 0, 16);
		}
		

		if(hood == 36 ) {
			newNbrhood(8);
			setNBH( 1, -2, 0, 0);
			setNBH( 2, -1, 0, 1);
			setNBH( 2, 1, 0, 2);
			setNBH( 1, 2, 0, 3);
			setNBH( -1, 2, 0, 4);
			setNBH( -2, 1, 0, 5);
			setNBH( -2, -1, 0, 6);
			setNBH( -1, -2, 0, 7);
		}

		
		if(hood == 37 ) {
			newNbrhood(12);
			setNBH( 0, -2, 0, 0);
			setNBH( 1, -2, 0, 1);
			
			setNBH( 1, -1, 0, 2);
			setNBH( 2, 0, 0, 3);
			setNBH( 2, 1, 0, 4);
			setNBH( 1, 1, 0, 5);
			setNBH( 0, 2, 0, 6);
			setNBH( -1, 2, 0, 7);
			setNBH( -1, 1, 0, 8);
			setNBH( -2, 0, 0, 9);
			setNBH( -2, -1, 0, 10);
			setNBH( -1, -1, 0, 11);
		}
		
		
		if(hood == 38 ) {
			newNbrhood(24);
			setNBH( 0, -1, 0, 0);
			setNBH( 1, -1, 0, 1);
			setNBH( 1, 0, 0, 2);
			setNBH( 1, 1, 0, 3);
			setNBH( 0, 1, 0, 4);
			setNBH( -1, 1, 0, 5);
			setNBH( -1, 0, 0, 6);
			setNBH( -1, -1, 0, 7);
			
			setNBH( 0, -3, 0, 8);
			setNBH( 1, -3, 0, 9);
			setNBH( 2, -2, 0, 10);
			setNBH( 3, -1, 0, 11);
			setNBH( 3, 0, 0, 12);
			setNBH( 3, 1, 0, 13);
			setNBH( 2, 2, 0, 14);
			setNBH( 1, 3, 0, 15);
			setNBH( 0, 3, 0, 16);
			setNBH( -1, 3, 0, 17);
			setNBH( -2, 2, 0, 18);
			setNBH( -3, 1, 0, 19);
			setNBH( -3, 0, 0, 20);
			setNBH( -3, -1, 0, 21);
			setNBH( -2, -2, 0, 22);
			setNBH( -1, -3, 0, 23);
		}
		
		if(hood == 39) {
			newNbrhood(16);
			setNBH( 0, -1, 0, 0);
			setNBH( 1, -1, 0, 1);
			setNBH( 1, 0, 0, 2);
			setNBH( 1, 1, 0, 3);
			setNBH( 0, 1, 0, 4);
			setNBH( -1, 1, 0, 5);
			setNBH( -1, 0, 0, 6);
			setNBH( -1, -1, 0, 7);
			
			setNBH( 0, 2, 0, 8);
			setNBH( 0, -2, 0, 9);
			setNBH( 2, 0, 0, 10);
			setNBH( -2, 0, 0, 11);

			setNBH( 2, 1, 0, 12);
			//setNBH( 2, -1, 0, 13);
			setNBH( 1, 2, 0, 13);
			//setNBH( -1, 2, 0, 15);
			//setNBH( -2, 1, 0, 16);
			setNBH( -2, -1, 0, 14);
			//setNBH( 1, -2, 0, 18);
			setNBH( -1, -2, 0, 15);
		}
		
		/*if(hood == 40) {
			newNbrhood(4);
			setNBH( 0, 0, 0, 0);
			setNBH( 1, 0, 0, 1);
			setNBH( 0, 1, 0, 2);
			setNBH( 1, 1, 0, 3);
		}
		
		if(hood == 41) {
			newNbrhood(4);
			setNBH( 0, 0, 0, 0);
			setNBH( -1, 0, 0, 1);
			setNBH( 0, -1, 0, 2);
			setNBH( -1, -1, 0, 3);
		}
		
		if(hood == 42) {
			newNbrhood(4);
			setNBH( 0, 0, 0, 0);
			setNBH( -1, 0, 0, 1);
			setNBH( 0, 1, 0, 2);
			setNBH( -1, 1, 0, 3);
		}
		
		if(hood == 43) {
			newNbrhood(4);
			setNBH( 0, 0, 0, 0);
			setNBH( 1, 0, 0, 1);
			setNBH( 0, -1, 0, 2);
			setNBH( 1, -1, 0, 3);
		}*/
		
		if(hood == 40) {
			newNbrhood(2);
			setNBH( 1, 0, 0, 0);
			setNBH( 0, 1, 0, 1);
		}
		
		if(hood == 42) {
			newNbrhood(2);
			setNBH( -1, 0, 0, 0);
			setNBH( 0, -1, 0, 1);
		}
		
		if(hood == 41) {
			newNbrhood(2);
			setNBH( -1, 0, 0, 0);
			setNBH( 0, 1, 0, 1);
		}
		
		if(hood == 43) {
			newNbrhood(2);
			setNBH( 1, 0, 0, 0);
			setNBH( 0, -1, 0, 1);
		}
		
		if(hood == 44) {
			int n = r.nextInt(88)+1;
			newNbrhood(n);
			
			for (int ii = 0; ii < NBH.length; ii++) {
				setNBH(r.nextInt(3)-1, r.nextInt(3)-1, 0, ii);
			}
		}
		
		
		/*
		if(hood == 3) {
			newNbrhood(xxx);
			
		}
		
		/*
		if(hood == 3) {
			newNbrhood(xxx);
			
		}
		
		/*
		if(hood == 3) {
			newNbrhood(xxx);
			
		}
		
		/*
		if(hood == 3) {
			newNbrhood(xxx);
			
		}
		
		/*
		if(hood == 3) {
			newNbrhood(xxx);
			
		}
		
		/*
		if(hood == 3) {
			newNbrhood(xxx);
			
		}
		
		/*
		if(hood == 3) {
			newNbrhood(xxx);
			
		}
		
		
		/**/
		
	}
	
}/*
*/

public class dataSources {
	
	public int[][][] sources;
	
	int readx = 0;
	int ready = 0;
	int readz = 0;
	
	int maxx=0;
	int maxy=0;
	int maxz=0;
	
	public dataSources(int[][][] ar) {
		setArray(ar);
	}
	
	public int readNext() {
		readx++;
		
		if(readx >= maxx) {
			readx = readx % maxx;
			ready++;
		}
		
		if(ready >= maxy) {
			ready = ready % maxy;
			readz++;
		}
		
		if(readz >= maxz) {
			readz = readz % maxz;
			readx=0;
			ready=0;
			readz=0;
		}
		
		
		
		return sources[readx][ready][readz];
	}

	public void setArray(int[][][] ar){
		maxx = ar.length;
		maxy = ar[0].length;
		maxz = ar[0][0].length;
		sources = ar;
	}
	
}

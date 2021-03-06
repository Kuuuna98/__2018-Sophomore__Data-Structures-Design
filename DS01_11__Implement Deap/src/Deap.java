
public class Deap {
	int[] deap;
	int n = 0; //deap에 있는 원소의 개수; 루트는 비어 있다.

	public Deap(int maxSize) {
		deap = new int[maxSize]; 
	}

	//deap의 크기를 i로 증가시키고 기존의 원소를 복사한다.  
	private void increaseheap(int i){
		int[] newdeap = new int[i]; 
		System.arraycopy(deap, 0, newdeap, 0, n+1);
		deap = newdeap;
	}

	//인덱스 i가 min-heap에 위치해 있으면 true를 리턴하고, 그렇지 않으면 false를 리턴한다
	private boolean inMinHeap(int i) {

		int idx = i;
		
		while(true) {
			if(idx == 1) return true;
			else if(idx == 2) return false;
			idx = (idx-1) / 2;
		}
	}

	//인덱스 i가 min-heap에 위치해 있을때 max partner의 인덱스를 리턴한다
	private int maxPartner(int i) {
		int j = i + (int)(Math.pow(2, (Math.floor(Math.log(i+1)/Math.log(2))-1))) ;
		if(j > n) j = (j-1)/2;
		return j;
	}

	//인덱스 i가 max-heap에 위치해 있을때 min partner의 인덱스를 리턴한다
	private int minPartner(int i) {
		int j = (int) (i - Math.pow(2, (Math.floor(Math.log(i+1)/Math.log(2))-1))) ;
		return j;
	}

	//min-heap에 있는 인덱스 at 위치에 key를 삽입
	private void minInsert(int at, int key) {
		deap[at] = key;
		int meidx = at;
		while(0 < (meidx-1)/2 && deap[meidx] < deap[(meidx-1)/2]) {
			deap[meidx] = deap[(meidx-1)/2];
			deap[(meidx-1)/2] = key;
			meidx = (meidx-1)/2;
		}
	}

	//max-heap에 있는 인덱스 at 위치에 key를 삽입
	private void maxInsert(int at, int key) {
		deap[at] = key;
		int meidx = at;
		while(0 < (meidx-1)/2 && deap[meidx] > deap[(meidx-1)/2]) {
			deap[meidx] = deap[(meidx-1)/2];
			deap[(meidx-1)/2] = key;
			meidx = (meidx-1)/2;
		}

	}

	//max 값을 삭제하여 리턴한다
	public int deleteMax() {
		if(n==0) return 0; //들어있는것이 없을때 
		if(n==1) return deap[n--]; //들어있는것이 하나일때
		if(n==2) return deap[n--]; //들어있는것이 두개일때 

		int temp = deap[n--];
		int idx = 2;
		int maxvalue = deap[2];

		while(idx <= (n-1)/2) {
			int j = (2*idx)+1;
			if(j+1 <= n ) {
				if(deap[j] < deap[j+1]) j++;
			}
			deap[idx] =deap[j];
			idx = j;
		}

		int xidx = minPartner(idx);
		int x = deap[xidx];

		while(xidx <= (n-1)/2) {
			xidx = (2*xidx)+1;
			if(xidx+1 <= n ) {
				if(deap[xidx] < deap[xidx+1]) xidx++;
			}
			x = deap[xidx];
		}
		
		if(temp < x) {
			deap[idx] = x;
			minInsert(xidx,temp);
		}
		else {
			deap[idx] = temp;
		}
		return maxvalue;
	}


	//min 값을 삭제하여 리턴한다
	public int deleteMin() {
		if(n==0) return 0;
		if(n==1) return deap[n--];

		int temp = deap[n--];
		int idx = 1;
		int minvalue = deap[1];

		while(idx <= (n-1)/2) {
			int j = (2*idx)+1;
			if(j+1 <= n ) {
				if(deap[j] > deap[j+1]) j++;
			}
			deap[idx] =deap[j];
			idx = j;
		}

		int xidx = maxPartner(idx);
		int x = deap[xidx];

		if(temp > x) {
	
			deap[idx] = x;
			maxInsert(xidx, temp);

		}
		else {
			deap[idx] = temp;
		}

		return minvalue;
	}

	//x를 삽입한다
	public void insert(int x) {

		if (n == deap.length - 1) {
			System.out.println("The heap is full. The heap size is doubled");
			increaseheap(deap.length*2);
		}
		n++;

		if (n == 1) {
			deap[1] = x;
			return;
		}
		if (inMinHeap(n)) {
			int i = maxPartner(n);

			if (x > deap[i]) {
				deap[n] = deap[i];
				maxInsert(i, x);
			} else {
				minInsert(n, x);
			}
		} else {				
			int i = minPartner(n);
			if (x < deap[i]) {
				deap[n] = deap[i];
				minInsert(i, x);
			} else {
				maxInsert(n, x);
			}			
		}
	}

	//deap을 프린트한다
	public void print() {
		int levelNum = 2;
		int thisLevel = 0;
		int gap = 8;
		for (int i = 1; i <= n; i++) {
			for (int j = 0; j < gap-1; j++) {
				System.out.print(" ");
			}
			if (thisLevel != 0) {
				for (int j = 0; j < gap-1; j++) {
					System.out.print(" ");
				}
			}
			if (Integer.toString(deap[i]).length() == 1) {
				System.out.print(" ");
			}
			System.out.print(deap[i]);
			thisLevel++;
			if (thisLevel == levelNum) {
				System.out.println();
				thisLevel = 0;
				levelNum *= 2;
				gap/=2;
			}
		}
		System.out.println();
		if (thisLevel != 0) {
			System.out.println();
		}
	}

	public static void main(String[] argv) {
		Deap a = new Deap(10);
		int i=0;
		int[] data = { 4, 65, 8, 9, 48, 55, 10, 19, 20, 30, 15, 25, 50 };
		for (i = 0; i < 9; i++) {
			a.insert(data[i]);
		}

		System.out.println("initial Deap");
		a.print();

		for (   ; i < data.length; i++) {
			a.insert(data[i]);
		}

		System.out.println("enlarged Deap");
		a.print();

		System.out.println("delete Min");
		a.deleteMin();
		a.print();

		System.out.println("delete Min");
		a.deleteMin();
		a.print();

		System.out.println("delete Min");
		a.deleteMin();
		a.print();

		System.out.println("delete Max");
		a.deleteMax();
		a.print();

		System.out.println("delete Max");
		a.deleteMax();
		a.print();

		System.out.println("delete Max");
		a.deleteMax();
		a.print();

	}
}

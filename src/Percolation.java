public class Percolation {
  
  // Create N-by-N grid, with all sites blocked
  public Percolation(int n){
    private int N = n;
    private int[][] multi = new int[N][N];;
    private WeightedQuickUnionUF uf = new WeightedQuickUnionUF(N*N);; 
   }  
  
  // Open site (row i, column j) if it is not open already
  public void open(int i, int j){
    if (i<0 || j<0|| i>=N || j>=N) { 
      throw new IndexOutOfBoundsException("IndexError: Row/Column Index is not between 0 and " + N);
      }
    else {
      if(multi[i][j]!=1){
        multi[i][j]=1;
        if(i < N-1)  if(isOpen(i+1, j)) {uf.union(id(i, j), id(i+1, j));}
        if(i > 0)  if(isOpen(i-1, j)) {uf.union(id(i, j), id(i-1, j));}
        if(j < N-1)  if(isOpen(i, j+1)) {uf.union(id(i, j), id(i, j+1));}
        if(j > 0)  if(isOpen(i, j-1)) {uf.union(id(i, j), id(i, j-1));}
      }
    }
  }
    
  private int id(int i, int j) {
    /*
     * Take care of the edge cases 
     */
    if(i<0)  {i = 0;}
    if(j<0)  {j = 0;}
    if(i>=N) {i = N-1;}
    if(i>=N) {j = N-1;}
    return N*i + j;
  }
  
  //is site (row i, column j) open?
  public boolean isOpen(int i, int j){
    return multi[i][j] == 1;}     
  
  //is site (row i, column j) full?
  public boolean isFull(int i, int j){
    boolean full = false;
    for(int k=0;k<N;k++) {
//      StdOut.println(k);
      if(uf.connected(id(i,j), k)) {
        full = true;
        break;
      }
    }
    return full;
  }
  
  public boolean percolates() { // Does the system percolate?
    boolean perc = false;
    for(int i=N-1, j=0; j<N; j++) {
      if(isFull(i,j)) {
        return true;
        }
    }
    return perc;
  }             
  
  public static void main(String[] args) { // Test client (optional)
  //     StdOut.println("Testing, Testing, 1,2,3, ... ");
  int N = StdIn.readInt();
  //     StdOut.println("N: "+N);
  Percolation p = new Percolation(N);
  while(true) {
    int i = StdRandom.uniform(N);
    int j = StdRandom.uniform(N);
    //       StdOut.println(i+"  "+j+"  "+p.id(i, j));
    p.open(i,j);
    if(p.percolates()) {
      float P=0;
      for(int ii=0;ii<N; ii++)
        for(int jj=0;jj<N;jj++) {
          P+= p.multi[ii][jj];
        }
      P/=(N*N);
      StdOut.println("P* = "+(P));
      break;
      }
    }     
  }   
}

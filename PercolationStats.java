public class PercolationStats {
  private static double[] thresh;
  private static int N;
  private static int T;
  public PercolationStats(int N, int T) { // perform T independent experiments on an N-by-N grid
    this.N = N;
    this.T = T;
    this.thresh = new double [T];
  }
  
  public double mean(){ // sample mean of percolation threshold
    return StdStats.mean(thresh);
  }
  public double stddev(){ // sample standard deviation of percolation threshold
    return StdStats.stddev(thresh);
  }
  public double confidenceLo(){ // low  endpoint of 95% confidence interval
    int a = thresh.length;
    return mean() - 1.96*stddev()/Math.sqrt(a);
  }
  public double confidenceHi(){ // high endpoint of 95% confidence interval
    int a = thresh.length;
    return mean() + 1.96*stddev()/Math.sqrt(a);
  }
  
  private static void monteCarloSim(int N, int T){
    while(T>0){
      float P=0;
      Percolation p = new Percolation(N);
      while(!p.percolates()) {
        P=0;
        int i = StdRandom.uniform(N);
        int j = StdRandom.uniform(N);
        p.open(i,j);
        for(int ii=0;ii<N; ii++)
          for(int jj=0;jj<N;jj++) {
            P+= p.multi[ii][jj];
          }
        P/=(N*N);
      }        
      thresh[--T]=P;
    }           
  } 

  public static void main(String[] args) { // test client (described below)
     if(args.length<1){
       int N=10;
       int T = 10000;
     }

     int N=Integer.parseInt(args[0]);
     int T=Integer.parseInt(args[1]);
     PercolationStats PS = new PercolationStats(N,T);
     monteCarloSim(N,T);
     StdOut.println("mean                    = "+PS.mean());
     StdOut.println("stddev                  = "+PS.stddev());
     StdOut.println("95% confidence interval = "+PS.confidenceLo()+", "+PS.confidenceHi());
  }
}

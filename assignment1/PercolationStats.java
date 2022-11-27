import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] thresholds;
    private int t;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        t = trials;
        if ((n <= 0) || (trials <= 0))
            throw new IllegalArgumentException();
        else {
            thresholds = new double[trials];
            Percolation p;

            for (int i = 0; i < trials; i++) {
                p = new Percolation(n);
                while (!p.percolates()) {
                    p.open(StdRandom.uniform(1, n + 1), StdRandom.uniform(1, n + 1));
                }
                thresholds[i] = ((double) p.numberOfOpenSites() / (n * n));
            }
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(thresholds);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(thresholds);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - 1.96 * stddev() / Math.sqrt(t);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + 1.96 * stddev() / Math.sqrt(t);
    }

    // test client (see below)
    public static void main(String[] args) {
        PercolationStats s = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        System.out.printf("mean = %f\n", s.mean());
        System.out.printf("stddev = %f\n", s.stddev());
        System.out.printf("95%% confidence interval = [%f, %f]\n", s.confidenceLo(), s.confidenceHi());
    }

}

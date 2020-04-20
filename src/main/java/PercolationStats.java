import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private int experimentsCount;
    private final double[] fractions;

    /**
     * Performs numOfRuns independent computational experiments on an size-by-size grid.
     */
    public PercolationStats(int size, int numOfRuns) {
        Percolation pr;
        if (size <= 0 || numOfRuns <= 0) {
            throw new IllegalArgumentException("Given size <= 0 || numOfRuns <= 0");
        }
        experimentsCount = numOfRuns;
        fractions = new double[experimentsCount];
        for (int expNum = 0; expNum < experimentsCount; expNum++) {
            pr = new Percolation(size);
            int openedSites = 0;
            while (!pr.percolates()) {
                int i = StdRandom.uniform(1, size + 1);
                int j = StdRandom.uniform(1, size + 1);
                if (!pr.isOpen(i, j)) {
                    pr.open(i, j);
                    openedSites++;
                }
            }
            double fraction = (double) openedSites / (size * size);
            fractions[expNum] = fraction;
        }
    }

    /**
     * Sample mean of percolation threshold.
     */
    public double mean() {
        return StdStats.mean(fractions);
    }

    /**
     * Sample standard deviation of percolation threshold.
     */
    public double stddev() {
        return StdStats.stddev(fractions);
    }

    /**
     * Returns lower bound of the 95% confidence interval.
     */
    public double confidenceLo() {
        return mean() - ((1.96 * stddev()) / Math.sqrt(experimentsCount));
    }

    /**
     * Returns upper bound of the 95% confidence interval.
     */
    public double confidenceHi() {
        return mean() + ((1.96 * stddev()) / Math.sqrt(experimentsCount));
    }

    public static void main(String[] args) {
        int size = Integer.parseInt(args[0]);
        int numOfRuns = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(size, numOfRuns);

        String confidence = ps.confidenceLo() + ", " + ps.confidenceHi();
        StdOut.println("mean                    = " + ps.mean());
        StdOut.println("stddev                  = " + ps.stddev());
        StdOut.println("95% confidence interval = " + confidence);
    }
}

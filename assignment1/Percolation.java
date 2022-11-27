import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdRandom;

public class Percolation {
    private int[][] gridcells; // whether cells are open or closed
    private WeightedQuickUnionUF grid;
    private int size; // n
    private int open; // number of open sites

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Grid size must be positive");
        } else {
            size = n;
            open = 0;
            gridcells = new int[n][n];

            // all cells closed
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    gridcells[i][j] = 0;
                }
            }

            // create grid with 2 extra cells to link to top and bottom rows
            int gridSize = n * n + 2;
            grid = new WeightedQuickUnionUF(gridSize);
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if ((row > 0) && (row <= size) && (col > 0) && (col <= size)) {
            if (!isOpen(row, col)) {
                gridcells[row - 1][col - 1] = 1;
                open++;

                int elem = size * (row - 1) + col;
                int adj[] = { elem - size, elem - 1, elem + 1, elem + size };

                if (col == 1) {
                    adj[1] = elem + 1;
                } else if (col == size) {
                    adj[2] = elem - 1;
                }

                if (row == 1)
                    grid.union(0, elem);

                if (row == size)
                    grid.union(size * size + 1, elem);

                for (int e : adj) {
                    if ((e > 0) & (e < size * size + 1)) {
                        int r = (e % size == 0) ? (e / size) : (e / size + 1);
                        int c = (e % size == 0) ? size : (e % size);
                        if (isOpen(r, c)) {
                            grid.union(elem, e);
                        }
                    }
                }
            }
        } else
            throw new IllegalArgumentException("Out of bounds");
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if ((row > 0) && (row <= size) && (col > 0) && (col <= size))
            return gridcells[row - 1][col - 1] == 1;
        else
            throw new IllegalArgumentException("Out of bounds");
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        // Bug: If the system percolates, then the function
        // returns full for every cell connected to the bottom.
        if (((row > 0) && (row <= size) && (col > 0) && (col <= size))) {
            return grid.find(0) == grid.find(size * (row - 1) + col);
        } else
            throw new IllegalArgumentException("Out of bounds");
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return open;
    }

    // does the system percolate?
    public boolean percolates() {
        return grid.find(0) == grid.find(size * size + 1);
    }

    // test client (optional)
    public static void main(String[] args) {
        int n = 200;
        Percolation p = new Percolation(n);
        while (!p.percolates()) {
            p.open(StdRandom.uniform(1, n + 1), StdRandom.uniform(1, n + 1));
        }
        System.out.println((float) p.numberOfOpenSites() / (n * n));
    }
}
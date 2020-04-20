import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final int rowLength;
    private final int topIndex;
    private final int bottomIndex;
    private final int gridSize;
    private final boolean[] grid;
    private final WeightedQuickUnionUF uf;
    private boolean percolates;
    private int openSites;


    public Percolation(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException(
                    "size must be larger than 0"
            );
        }
        rowLength = size;
        gridSize = size * size;
        uf = new WeightedQuickUnionUF(gridSize + 2);
        grid = new boolean[gridSize];
        topIndex = gridSize;
        bottomIndex = gridSize + 1;
        percolates = false;
    }

    public static void main(String[] args) {
        Percolation p = new Percolation(4);
        p.open(1, 1);
        p.open(2, 1);
        p.open(2, 2);
        p.open(3, 2);
        p.open(3, 3);
        if (p.percolates()) throw new AssertionError();
    }

    // open site (row i, column j) if it is not already
    public void open(int iOne, int jOne) {
        checkInput(iOne, jOne);

        // Change indexes to start at 1, not 0
        final int i = iOne - 1;
        final int j = jOne - 1;

        final int index = getIndex(i, j);

        if (!grid[index]) {
            grid[index] = true;
            ++openSites;

            // If the spot we just opened has any open neighbors, connect them
            int n; // Neighbor's index
            boolean hasN = false;
            for (int d = 0; d < 4; d++) {
                n = getNeighborIndex(i, j, d);
                if (-1 != n && isOpen(n)) {
                    uf.union(index, n);
                    hasN = true;
                }
            }

            // If it is in the top row, connect it with the top node
            if (0 == i) {
                uf.union(index, topIndex);
            }
            if (hasN) {
                // check if this made any of the bottom nodes connected
                // to the top
                for (int b = gridSize - 1; b >= gridSize - rowLength; b--) {
                    if (isOpen(b) && isConnected(topIndex, b)) {
                        uf.union(b, bottomIndex);
                        break;
                    }
                }
            } else if (1 == gridSize) {
                uf.union(index, bottomIndex);
            }
        }
    }

    public int numberOfOpenSites() {
        return openSites;
    }

    private int getIndex(int i, int j) {
        return i * rowLength + j;
    }

    private int getNeighborIndex(int i, int j, int d) {
        if (0 > d || 3 < d) {
            throw new IllegalArgumentException(
                    "Direction must be between 0 and 3"
            );
        }
        switch (d) {
            case 0:  // UP
                if (0 == i) {
                    return -1;
                }
                return getIndex(i - 1, j);
            case 1:  // RIGHT
                if (j + 1 == rowLength) {
                    return -1;
                }
                return getIndex(i, j + 1);
            case 2:  // DOWN
                if (1 + i == rowLength) {
                    return -1;
                }
                return getIndex(i + 1, j);
            case 3:  // LEFT
                if (0 == j) {
                    return -1;
                }
                return getIndex(i, j - 1);
            default:
        }
        return -1;
    }

    private void checkInput(int i, int j) {
        if (1 > i || rowLength < i) {
            throw new IllegalArgumentException(
                    "i must be between 1 and " + rowLength
            );
        }
        if (1 > j || rowLength < j) {
            throw new IllegalArgumentException(
                    "j must be between 1 and " + rowLength
            );
        }
    }

    // is site at given index open
    private boolean isOpen(int index) {
        return grid[index];
    }

    public boolean isOpen(int i, int j) {
        checkInput(i, j);

        // Change indexes to start at 1, not 0
        return isOpen(getIndex(i - 1, j - 1));
    }

    public boolean isFull(int i, int j) {
        checkInput(i, j);

        // Change indexes to start at 1, not 0
        return isConnected(topIndex, getIndex(i - 1, j - 1));
    }

    // does the system percolate?
    public boolean percolates() {
        if (!percolates) {
            percolates = isConnected(topIndex, bottomIndex);
        }
        return percolates;
    }

    private boolean isConnected(int i, int j) {
        final int iVal = uf.find(i);
        final int jVal = uf.find(j);
        return iVal == jVal;
    }
}

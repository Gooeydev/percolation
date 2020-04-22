import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;


public class PercolationTests {

    @Test()
    public void shouldNotPercolateWithZeroCellsOpen() {
        Percolation p = new Percolation(4);
        assertFalse(p.percolates());
    }

    @Test()
    public void shouldPercolateWithASingleCellOpenInAOneByOneGrid() {
        Percolation p = new Percolation(1);
        p.open(1, 1);
        assertTrue(p.percolates());
    }


    @Test()
    public void shouldNotPercolateWithGridSize4() {
        Percolation p = new Percolation(4);
        p.open(1, 1);
        p.open(2, 1);
        p.open(2, 2);
        p.open(3, 2);
        p.open(3, 3);
        assertFalse(p.percolates());
    }

    @Test()
    public void shouldBeFullWithGridSize2() {
        Percolation p = new Percolation(2);
        p.open(1, 1);
        assertTrue(p.isFull(1, 1));
    }

    @Test()
    public void shouldBeFullWithGridSize10() {
        Percolation p = new Percolation(10);
        p.open(1, 1);
        p.open(2, 1);
        p.open(3, 1);
        p.open(3, 2);
        assertTrue(p.isFull(3, 2));
    }
}

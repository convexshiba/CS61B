/* SimpleBoard.java */

package hw6;

import java.math.BigInteger;
import java.util.Random;

/**
 * Simple class that implements an 8x8 game board with three possible values
 * for each cell:  0, 1 or 2.
 * <p/>
 * DO NOT CHANGE ANY PROTOTYPES IN THIS FILE.
 */

public class SimpleBoard {
    private final static int DIMENSION = 8;
    private int[][] grid;

    /**
     *  Invariants:
     *  (1) grid.length == DIMENSION.
     *  (2) for all 0 <= i < DIMENSION, grid[i].length == DIMENSION.
     *  (3) for all 0 <= i, j < DIMENSION, grid[i][j] >= 0 and grid[i][j] <= 2.
     **/

    /**
     * Construct a new board in which all cells are zero.
     */

    public SimpleBoard() {
        grid = new int[DIMENSION][DIMENSION];
    }

    /**
     * Set the cell (x, y) in the board to the given value mod 3.
     *
     * @param value to which the element should be set (normally 0, 1, or 2).
     * @param x     is the x-index.
     * @param y     is the y-index.
     * @throws ArrayIndexOutOfBoundsException is thrown if an invalid index
     *                                        is given.
     */

    public void setElementAt(int x, int y, int value) {
        grid[x][y] = value % 3;
        if (grid[x][y] < 0) {
            grid[x][y] = grid[x][y] + 3;
        }
    }

    /**
     * Get the valued stored in cell (x, y).
     *
     * @param x is the x-index.
     * @param y is the y-index.
     * @return the stored value (between 0 and 2).
     * @throws ArrayIndexOutOfBoundsException is thrown if an invalid index
     *                                        is given.
     */

    public int elementAt(int x, int y) {
        return grid[x][y];
    }

    /**
     * Returns true if "this" SimpleBoard and "board" have identical values in
     * every cell.
     *
     * @param board is the second SimpleBoard.
     * @return true if the boards are equal, false otherwise.
     */

    public boolean equals(Object board) {
        // Replace the following line with your solution.  Be sure to return false
        //   (rather than throwing a ClassCastException) if "board" is not
        //   a SimpleBoard.
        return toString().equals(((SimpleBoard) board).toString());
    }


    @Override
    public String toString() {
        String gridString = "";
        for (int y = 0; y < DIMENSION; y++) {
            for (int x = 0; x < DIMENSION; x++) {
                gridString += grid[x][y];
            }
        }
        return gridString;

//        return "010102020121212121220101012121212";
    }

    /**
     * Returns a hash code for this SimpleBoard.
     *
     * @return a number between Integer.MIN_VALUE and Integer.MAX_VALUE.
     */

    public int hashCode() {
        String gridString = toString();
        int hashCode = 0;
        for (int i = 0; i < gridString.length(); i++) {
            long power = (long) Math.pow(3, gridString.length() - i - 1);
            int digit = Character.getNumericValue(gridString.charAt(i));
            hashCode += power * digit;
        }
        return hashCode;
//        Replace the following line with your solution.
//        return (int)(hashCode % Integer.MAX_VALUE);
//        return toString().hashCode();
    }

    public static void main(String[] args) {
        SimpleBoard gameGrid = new SimpleBoard();
//        gameGrid.setElementAt(0, 0, 2);
//        System.out.println(gameGrid.hashCode());
//        gameGrid.setElementAt(0,1,2);
//        System.out.println(gameGrid.hashCode());
//        gameGrid.setElementAt(5, 2, 1);
//        System.out.println(gameGrid.hashCode());
//
//
//        SimpleBoard gameGrid2 = new SimpleBoard();
//        gameGrid2.setElementAt(0,0,2);
//        gameGrid2.setElementAt(0,1,2);
//        gameGrid2.setElementAt(5,2,1);
//
//        System.out.println(gameGrid.equals(gameGrid2));

        Random rand = new Random(345345);
        int i = 200;
        while (i > 0) {
            gameGrid.setElementAt(rand.nextInt(8), rand.nextInt(8), rand.nextInt(3));
            System.out.print(gameGrid.toString());
            System.out.println("  " + gameGrid.hashCode());
            i--;
        }
//        System.out.println(gameGrid.hashCode());

    }
}

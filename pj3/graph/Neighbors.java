/* Neighbors.java */

/* DO NOT CHANGE THIS FILE. */
/* YOUR SUBMISSION MUST WORK CORRECTLY WITH _OUR_ COPY OF THIS FILE. */

package pj3.graph;

import java.util.Arrays;

/**
 * The Neighbors class is provided solely as a way to allow the method
 * WUGraph.getNeighbors() to return two arrays at once.  Do NOT use this class
 * for any other purpose.
 * <p/>
 * Since this class is NOT an abstract data type, but is merely a collection of
 * data, all fields are public.
 */

public class Neighbors {
    public Object[] neighborList;
    public int[] weightList;

    @Override
    public String toString() {
        return Arrays.toString(neighborList) + "\n" + Arrays.toString(weightList);
    }

    public static void main(String[] args) {
        Neighbors n = new Neighbors();
        n.neighborList = new Object[]{"123", 123};
        System.out.println(n);
    }
}

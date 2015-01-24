/* Sorts.java */

package hw10.sort;

import java.util.Arrays;

public class Sorts {

    /**
     *  Place any final static fields you would like to have here.
     **/


    /**
     * countingSort() sorts an array of int keys according to the
     * values of _one_ of the base-16 digits of each key.  "whichDigit"
     * indicates which digit is the sort key.  A zero means sort on the least
     * significant (ones) digit; a one means sort on the second least
     * significant (sixteens) digit; and so on, up to a seven, which means
     * sort on the most significant digit.
     *
     * @param keys        is an array of ints.  Assume no key is negative.
     * @param whichDigit is a number in 0...7 specifying which base-16 digit
     *                   is the sort key.
     * @return an array of type int, having the same length as "keys"
     * and containing the same keys sorted according to the chosen digit.
     * <p/>
     * Note:  Return a _newly_ created array.  DO NOT CHANGE THE ARRAY keys.
     */
    public static int[] countingSort(int[] keys, int whichDigit) {
        int[] output = new int[keys.length];
        int[] digits = new int[keys.length];

        int[] counts = new int[16];

        for (int i = 0; i < keys.length; i++) {
            digits[i] = keys[i] >> whichDigit & 15;
            counts[digits[i]]++;
        }

        int total = 0;
        for (int i = 0; i < counts.length; i++) {
            int foo = counts[i];
            counts[i] = total;
            total += foo;
        }

        for (int i = 0; i < keys.length; i++) {
            output[counts[digits[i]]++] = keys[i];
        }

        // Replace the following line with your solution.
        return output;
    }

    /**
     * radixSort() sorts an array of int keys (using all 32 bits
     * of each key to determine the ordering).
     *
     * @param keys is an array of ints.  Assume no key is negative.
     * @return an array of type int, having the same length as "keys"
     * and containing the same keys in sorted order.
     * <p/>
     * Note:  Return a _newly_ created array.  DO NOT CHANGE THE ARRAY keys.
     */
    public static int[] radixSort(int[] keys) {
        int[] output = Arrays.copyOf(keys,keys.length);


        for (int i = 0; i < 32; i++) {
            output = countingSort(output, i);
        }
        // Replace the following line with your solution.
        return output;
    }

    /**
     * yell() prints an array of int keys.  Each key is printed in hexadecimal
     * (base 16).
     *
     * @param keys is an array of ints.
     */
    public static void yell(int[] keys) {
        System.out.print("keys are [ ");
        for (int i = 0; i < keys.length; i++) {
            System.out.print(Integer.toString(keys[i], 16) + " ");
        }
        System.out.println("]");
    }

    /**
     * main() creates and sorts a sample array.
     * We recommend you add more tests of your own.
     * Your test code will not be graded.
     */
    public static void main(String[] args) {
        int[] keys = {Integer.parseInt("60013879", 16),
                Integer.parseInt("11111119", 16),
                Integer.parseInt("2c735010", 16),
                Integer.parseInt("2c732010", 16),
                Integer.parseInt("7fffffff", 16),
                Integer.parseInt("4001387c", 16),
                Integer.parseInt("10111119", 16),
                Integer.parseInt("529a7385", 16),
                Integer.parseInt("1e635010", 16),
                Integer.parseInt("28905879", 16),
                Integer.parseInt("00011119", 16),
                Integer.parseInt("00000000", 16),
                Integer.parseInt("7c725010", 16),
                Integer.parseInt("1e630010", 16),
                Integer.parseInt("111111e5", 16),
                Integer.parseInt("61feed0c", 16),
                Integer.parseInt("3bba7387", 16),
                Integer.parseInt("52953fdb", 16),
                Integer.parseInt("40013879", 16)};

        yell(keys);
        keys = radixSort(keys);
        yell(keys);

        int[] foo = new int[] {10,7,6,5,4,3,2,1,6,5,4,7,3,4,3,4,5,6,7,8,9,7,11,2,33,44,55};
        System.out.println(Arrays.toString(radixSort(foo)));

    }

}

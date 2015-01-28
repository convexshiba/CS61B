/* HashTableChained.java */

package hw6.dict;

import hw5.list.*;

import java.util.Random;

/**
 * HashTableChained implements a Dictionary as a hashList table with chaining.
 * All objects used as keys must have a valid hashCode() method, which is
 * used to determine which bucket of the hashList table an entry is stored in.
 * Each object's hashCode() is presumed to return an int between
 * Integer.MIN_VALUE and Integer.MAX_VALUE.  The HashTableChained class
 * implements only the compression function, which maps the hashList code to
 * a bucket in the table's range.
 * <p/>
 * DO NOT CHANGE ANY PROTOTYPES IN THIS FILE.
 */

public class HashTableChained implements Dictionary {

    /**
     * Place any data fields here.
     */
    public int hashSize;
    protected SList[] hashList;
    protected int entryNumber = 0;
    public int collision = 0;

    /**
     * Construct a new empty hashList table intended to hold roughly sizeEstimate
     * entries.  (The precise number of buckets is up to you, but we recommend
     * you use a prime number, and shoot for a load factor between 0.5 and 1.)
     */

    public HashTableChained(int sizeEstimate) {
        LOOP:
        for (int i = sizeEstimate; ; i++) {
            for (int j = 2; j < Math.sqrt(i); j++) {
                if (i % j == 0) {
                    continue LOOP;
                }
            }
            hashSize = i;
            break;
        }
        arrayInit();
        // Your solution here.
    }

    /**
     * Construct a new empty hashList table with a default size.  Say, a prime in
     * the neighborhood of 100.
     */

    public HashTableChained() {
        hashSize = 101;
        arrayInit();
        // Your solution here.
    }

    protected void arrayInit() {
        hashList = new SList[hashSize];
        for (int i = 0; i < hashSize; i++) {
            hashList[i] = new SList();
        }
    }

    /**
     * Converts a hashList code in the range Integer.MIN_VALUE...Integer.MAX_VALUE
     * to a value in the range 0...(size of hashList table) - 1.
     * <p/>
     * This function should have package protection (so we can test it), and
     * should be used by insert, find, and remove.
     */

    int compFunction(int code) {
//        long largePrime;
//        for (long i = (long)Math.pow(hashSize,3); ; i++) {
//            for (int j = 2; j < Math.sqrt(i); j++) {
//                if (i % j == 0) {
//                    break;
//                }
//            }
//            largePrime = i;
//            break;
//        }
//        // Replace the following line with your solution.
//        return Math.abs((int)(((long)23 * code + 47) % largePrime) % hashSize);
        return Math.abs(code % hashSize);
    }

    /**
     * Returns the number of entries stored in the dictionary.  Entries with
     * the same key (or even the same key and value) each still count as
     * a separate entry.
     *
     * @return number of entries in the dictionary.
     */

    public int size() {
        // Replace the following line with your solution.
        return entryNumber;
    }

    /**
     * Tests if the dictionary is empty.
     *
     * @return true if the dictionary has no entries; false otherwise.
     */

    public boolean isEmpty() {
        return entryNumber == 0;
    }

    /**
     * Create a new Entry object referencing the input key and associated value,
     * and insert the entry into the dictionary.  Return a reference to the new
     * entry.  Multiple entries with the same key (or even the same key and
     * value) can coexist in the dictionary.
     * <p/>
     * This method should run in O(1) time if the number of collisions is small.
     *
     * @param key   the key by which the entry can be retrieved.
     * @param value an arbitrary object.
     * @return an entry containing the key and value.
     */

    public Entry insert(Object key, Object value) {
        Entry item = new Entry();
        item.key = key;
        item.value = value;
        SList hashNode = hashList[compFunction(key.hashCode())];
        if (!hashNode.isEmpty()) {
            collision++;
        }
        hashNode.insertFront(item);
        entryNumber++;

        // Replace the following line with your solution.
        return item;
    }

    /**
     * Search for an entry with the specified key.  If such an entry is found,
     * return it; otherwise return null.  If several entries have the specified
     * key, choose one arbitrarily and return it.
     * <p/>
     * This method should run in O(1) time if the number of collisions is small.
     *
     * @param key the search key.
     * @return an entry containing the key and an associated value, or null if
     * no entry contains the specified key.
     */

    public Entry find(Object key) {
        SList hashNode = hashList[compFunction(key.hashCode())];
        ListNode currentNode = hashNode.front();
        while (currentNode.isValidNode()) {
            try {
                if (((Entry) currentNode.item()).key().equals(key)) {
                    return ((Entry) currentNode.item());
                } else {
                    currentNode = currentNode.next();
                }
            } catch (InvalidNodeException e) {
                e.printStackTrace();
            }
        }
        return null;
        // Replace the following line with your solution.
    }

    /**
     * Remove an entry with the specified key.  If such an entry is found,
     * remove it from the table and return it; otherwise return null.
     * If several entries have the specified key, choose one arbitrarily, then
     * remove and return it.
     * <p/>
     * This method should run in O(1) time if the number of collisions is small.
     *
     * @param key the search key.
     * @return an entry containing the key and an associated value, or null if
     * no entry contains the specified key.
     */

    public Entry remove(Object key) {
        SList hashNode = hashList[compFunction(key.hashCode())];
        ListNode currentNode = hashNode.front();
        while (currentNode.isValidNode()) {
            try {
                if (((Entry) currentNode.item()).key().equals(key)) {
                    currentNode.remove();
                    entryNumber--;
                    return ((Entry) currentNode.item());
                } else {
                    currentNode = currentNode.next();
                }
            } catch (InvalidNodeException e) {
                e.printStackTrace();
            }
        }
        return null;
        // Replace the following line with your solution.
    }

    /**
     * Remove all entries from the dictionary.
     */
    public void makeEmpty() {
        arrayInit();
        // Your solution here.
    }

    @Override
    public String toString() {
        String hashString = "";
        for (int i = 0; i < hashSize; i++) {
            if (!hashList[i].isEmpty()) {
                hashString += i + " :" + hashList[i] + "\n";
            }
        }
        return hashString;
    }

    public static double expectedRate(int n, int N) {
        return n - N + N * Math.pow(1 - 1 / (double) N, n);
    }

    public static void main(String[] args) {
        HashTableChained hash = new HashTableChained(100);
        Random rand = new Random(1);
        for (int i = 20; i > 0; i--) {
            hash.insert(Math.abs(rand.nextInt()), i);
        }


        System.out.println(hash);
        System.out.println(hash.collision);
        System.out.printf("%.2f", expectedRate(20, 100));
//
//        System.out.println("1".hashCode());
    }
}

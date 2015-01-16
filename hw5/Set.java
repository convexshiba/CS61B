/* Set.java */

package hw5;

import hw5.list.*;

/**
 * A Set is a collection of Comparable elements stored in sorted order.
 * Duplicate elements are not permitted in a Set.
 */
public class Set {
  /* Fill in the data fields here. */

    private List setList;
    /**
     * Set ADT invariants:
     *  1)  The Set's elements must be precisely the elements of the List.
     *  2)  The List must always contain Comparable elements, and those elements
     *      must always be sorted in ascending order.
     *  3)  No two elements in the List may be equal according to compareTo().
     **/

    /**
     * Constructs an empty Set.
     * <p/>
     * Performance:  runs in O(1) time.
     */
    public Set() {
        setList = new DList();
        // Your solution here.
    }

    /**
     * cardinality() returns the number of elements in this Set.
     * <p/>
     * Performance:  runs in O(1) time.
     */
    public int cardinality() {
        // Replace the following line with your solution.
        return setList.length();
    }

    /**
     * insert() inserts a Comparable element into this Set.
     * <p/>
     * Sets are maintained in sorted order.  The ordering is specified by the
     * compareTo() method of the java.lang.Comparable interface.
     * <p/>
     * Performance:  runs in O(this.cardinality()) time.
     */
    public void insert(Comparable c) {
        if (cardinality() == 0) {
            setList.insertFront(c);
        } else {
            ListNode node = setList.front();
            try {
                while (c.compareTo(node.item()) != 0) {
                    if (c.compareTo(node.item()) > 0) {
                        node = node.next();
                    } else {
                        node.insertBefore(c);
                        break;
                    }
                    if (!node.isValidNode()) {
                        setList.back().insertAfter(c);
                        break;
                    }
                }
            } catch (InvalidNodeException e) {
                e.printStackTrace();
            }
        }
        // Your solution here.
    }

    /**
     * union() modifies this Set so that it contains all the elements it
     * started with, plus all the elements of s.  The Set s is NOT modified.
     * Make sure that duplicate elements are not created.
     * <p/>
     * Performance:  Must run in O(this.cardinality() + s.cardinality()) time.
     * <p/>
     * Your implementation should NOT copy elements of s or "this", though it
     * will copy _references_ to the elements of s.  Your implementation will
     * create new _nodes_ for the elements of s that are added to "this", but
     * you should reuse the nodes that are already part of "this".
     * <p/>
     * DO NOT MODIFY THE SET s.
     * DO NOT ATTEMPT TO COPY ELEMENTS; just copy _references_ to them.
     */
    public void union(Set s) {
        ListNode thisSetNode = setList.front();
        ListNode sNode = s.setList.front();
        try {
            while (sNode.isValidNode()) {
                if (!thisSetNode.isValidNode()) {
                    setList.back().insertAfter(sNode.item());

                    sNode = sNode.next();
                    continue;
                }

                if (compareItem(sNode, thisSetNode) > 0) {
                    thisSetNode = thisSetNode.next();
                    continue;
                } else if (compareItem(sNode, thisSetNode) < 0) {
                    thisSetNode.insertBefore(sNode.item());
                    sNode = sNode.next();
                    continue;
                } else {
                    sNode = sNode.next();
                }
            }
        } catch (InvalidNodeException e) {
            e.printStackTrace();
        }
        // Your solution here.
    }

    private int compareItem(ListNode node1, ListNode node2) {
        try {
            if (((Comparable) node1.item()).compareTo((Comparable) node2.item()) == 0) {
                return 0;
            } else if (((Comparable) node1.item()).compareTo((Comparable) node2.item()) > 0) {
                return 1;
            } else {
                return -1;
            }
        } catch (InvalidNodeException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * intersect() modifies this Set so that it contains the intersection of
     * its own elements and the elements of s.  The Set s is NOT modified.
     * <p/>
     * Performance:  Must run in O(this.cardinality() + s.cardinality()) time.
     * <p/>
     * Do not construct any new ListNodes during the execution of intersect.
     * Reuse the nodes of "this" that will be in the intersection.
     * <p/>
     * DO NOT MODIFY THE SET s.
     * DO NOT CONSTRUCT ANY NEW NODES.
     * DO NOT ATTEMPT TO COPY ELEMENTS.
     */
    public void intersect(Set s) {
        ListNode thisNode = setList.front();
        ListNode sNode = s.setList.front();

        try {

            while (thisNode.isValidNode()) {
                if (!sNode.isValidNode()) {
                    if (!thisNode.next().isValidNode()) {
                        thisNode.remove();
                        break;
                    } else {
                        thisNode = thisNode.next();
                        thisNode.prev().remove();
                        continue;
                    }
                }

                if (compareItem(thisNode, sNode) == 0) {
                    thisNode = thisNode.next();
                    sNode = sNode.next();
                    continue;
                } else if (compareItem(thisNode, sNode) > 0) {
                    sNode = sNode.next();
                    continue;
                } else {
                    thisNode = thisNode.next();
                    thisNode.prev().remove();
                }
            }
        } catch (InvalidNodeException e) {
            e.printStackTrace();
        }

        // Your solution here.
    }

    /**
     * toString() returns a String representation of this Set.  The String must
     * have the following format:
     * {  } for an empty Set.  No spaces before "{" or after "}"; two spaces
     * between them.
     * {  1  2  3  } for a Set of three Integer elements.  No spaces before
     * "{" or after "}"; two spaces before and after each element.
     * Elements are printed with their own toString method, whatever
     * that may be.  The elements must appear in sorted order, from
     * lowest to highest according to the compareTo() method.
     * <p/>
     * WARNING:  THE AUTOGRADER EXPECTS YOU TO PRINT SETS IN _EXACTLY_ THIS
     * FORMAT, RIGHT UP TO THE TWO SPACES BETWEEN ELEMENTS.  ANY
     * DEVIATIONS WILL LOSE POINTS.
     */
    public String toString() {
        if (setList.isEmpty()) {
            return "{  }";
        } else {
            String setString = "{  ";
            try {
                ListNode node = setList.front();
                while (node.isValidNode()) {
                    setString += node.item() + "  ";
                    node = node.next();
                }
                setString += "}";
            } catch (InvalidNodeException e) {
                e.printStackTrace();
            }
            return setString;
        }
        // Replace the following line with your solution.
    }

    public static void main(String[] argv) {
        Set s = new Set();
        s.insert(new Integer(3));
        s.insert(new Integer(4));
        s.insert(new Integer(5));
        s.insert(new Integer(6));
        s.insert(new Integer(7));
        s.insert(new Integer(8));
        s.insert(new Integer(5));
        System.out.println("Set s = " + s);

        Set s2 = new Set();
        s2.insert(new Integer(1));
        s2.insert(new Integer(4));
        s2.insert(new Integer(5));
        System.out.println("Set s2 = " + s2);

        Set s3 = new Set();
        s3.insert(new Integer(5));
        s3.insert(new Integer(3));
        s3.insert(new Integer(8));
        System.out.println("Set s3 = " + s3);

        s.union(s2);
        System.out.println("After s.union(s2), s = " + s);

        s.intersect(s3);
        System.out.println("After s.intersect(s3), s = " + s);

        System.out.println("s.cardinality() = " + s.cardinality());

        Set s4 = new Set();
        System.out.println("Empty Set s4 = " + s4);

        System.out.println("s4.cardinality() = " + s4.cardinality());

        s4.union(s4);
        System.out.println("After s4.union(s4), s4 = " + s4);

        s4.intersect(s4);
        System.out.println("After s4.intersect(s4), s4 = " + s4);

        s3.union(s4);
        System.out.println("After s3.union(s4), s3 = " + s3);

        s3.intersect(s4);
        System.out.println("After s3.intersect(s4), s3 = " + s3);

        // You may want to add more (ungraded) test code here.
    }
}

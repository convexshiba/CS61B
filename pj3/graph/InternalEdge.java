package pj3.graph;

import hw5.list.InvalidNodeException;
import hw5.list.ListNode;

/**
 * This file is created by @Muffin_C on 1/28/15 17:48.
 * This file is part of Project CS61B.
 */
public class InternalEdge {
    ListNode node1;
    ListNode node2;
    VertexPair pair;
    boolean self = true;
    int weight;


    protected InternalEdge(Object o1, Object o2, int weight) {
        pair = new VertexPair(o1, o2);
        this.weight = weight;
    }

    public void deleteMe() {
        try {
            if (!self) {
                node1.remove();
                node2.remove();
            } else {
                node1.remove();
            }
        } catch (InvalidNodeException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int hashCode() {
        return pair.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return pair.equals(o);
    }

    @Override
    public String toString() {
        return "{" + pair.toString() + "," + weight + "}";
    }
}
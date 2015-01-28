package pj3.graph;

import hw5.list.DList;

/**
 * This file is created by @Muffin_C on 1/28/15 17:25.
 * This file is part of Project CS61B.
 */
public class InternalVertex {
    private Object name;
    private DList adjList;

    InternalVertex(Object vertex) {
        name = vertex;
        adjList = new DList();
    }

    public int getDegree() {
        return adjList.length();
    }

    public Object getVertecItem() {
        return name;
    }

    @Override
    public String toString() {
        return name.toString();
    }
}

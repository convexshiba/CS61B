/* WUGraph.java */

package pj3.graph;

import hw5.list.DList;
import hw5.list.InvalidNodeException;
import hw5.list.List;
import hw5.list.ListNode;

import java.util.Arrays;

/**
 * The WUGraph class represents a weighted, undirected graph.  Self-edges are
 * permitted.
 */

public class WUGraph {

    private HashTableAuto vertexTable = new HashTableAuto();
    private HashTableAuto edgeTable = new HashTableAuto();

    private DList vertexList = new DList();

    /**
     * WUGraph() constructs a graph having no vertices or edges.
     * <p/>
     * Running time:  O(1).
     */
    public WUGraph() {
    }

    /**
     * vertexCount() returns the number of vertices in the graph.
     * <p/>
     * Running time:  O(1).
     */
    public int vertexCount() {
        return vertexTable.size();
    }

    /**
     * edgeCount() returns the total number of edges in the graph.
     * <p/>
     * Running time:  O(1).
     */
    public int edgeCount() {
        return edgeTable.size();
    }

    private InternalVertex getInternal(Object vertex) {
        try {
            return (InternalVertex) ((ListNode) vertexTable.find(vertex).value()).item();
        } catch (InvalidNodeException e) {
            e.printStackTrace();
        }
        return null;
    }

    private InternalEdge getInternal(VertexPair pair) {
        return (InternalEdge) (edgeTable.find(pair).value());
    }

    /**
     * getVertices() returns an array containing all the objects that serve
     * as vertices of the graph.  The array's length is exactly equal to the
     * number of vertices.  If the graph has no vertices, the array has length
     * zero.
     * <p/>
     * (NOTE:  Do not return any internal data structure you use to represent
     * vertices!  Return only the same objects that were provided by the
     * calling application in calls to addVertex().)
     * <p/>
     * Running time:  O(|V|).
     */
    public Object[] getVertices() {
        Object[] vertices = new Object[vertexList.length()];
        ListNode node = vertexList.front();
        for (int i = 0; i < vertices.length; i++) {
            try {
                InternalVertex internalVertex = (InternalVertex) node.item();
                vertices[i] = internalVertex.getVertexObject();
                node = node.next();
            } catch (InvalidNodeException e) {
                e.printStackTrace();
            }
        }
        return vertices;
    }

    /**
     * addVertex() adds a vertex (with no incident edges) to the graph.
     * The vertex's "name" is the object provided as the parameter "vertex".
     * If this object is already a vertex of the graph, the graph is unchanged.
     * <p/>
     * Running time:  O(1).
     */
    public void addVertex(Object vertex) {
        if (vertexTable.find(vertex) == null) {
            vertexList.insertFront(new InternalVertex(vertex));
            vertexTable.insert(vertex, vertexList.front());
        }
    }

    /**
     * removeVertex() removes a vertex from the graph.  All edges incident on the
     * deleted vertex are removed as well.  If the parameter "vertex" does not
     * represent a vertex of the graph, the graph is unchanged.
     * <p/>
     * Running time:  O(d), where d is the degree of "vertex".
     */
    public void removeVertex(Object vertex) {
        if (vertexTable.find(vertex) == null) {
            return;
        } else {
            try {
                InternalVertex internalVertex = getInternal(vertex);
                while (!internalVertex.adjList.isEmpty()) {

                    VertexPair pair = (VertexPair) internalVertex.adjList.front().item();
                    getInternal(pair).deleteMe();
                    edgeTable.remove(pair);
                }
                ListNode vertexNode = (ListNode) vertexTable.find(vertex).value();
                vertexNode.remove();
                vertexTable.remove(vertex);
            } catch (InvalidNodeException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * isVertex() returns true if the parameter "vertex" represents a vertex of
     * the graph.
     * <p/>
     * Running time:  O(1).
     */
    public boolean isVertex(Object vertex) {
        return vertexTable.find(vertex) != null;
    }

    /**
     * degree() returns the degree of a vertex.  Self-edges add only one to the
     * degree of a vertex.  If the parameter "vertex" doesn't represent a vertex
     * of the graph, zero is returned.
     * <p/>
     * Running time:  O(1).
     */
    public int degree(Object vertex) {
        if (vertexTable.find(vertex) == null) {
            return 0;
        } else {
            return (getInternal(vertex)).getDegree();
        }
    }

    /**
     * getNeighbors() returns a new Neighbors object referencing two arrays.  The
     * Neighbors.neighborList array contains each object that is connected to the
     * input object by an edge.  The Neighbors.weightList array contains the
     * weights of the corresponding edges.  The length of both arrays is equal to
     * the number of edges incident on the input vertex.  If the vertex has
     * degree zero, or if the parameter "vertex" does not represent a vertex of
     * the graph, null is returned (instead of a Neighbors object).
     * <p/>
     * The returned Neighbors object, and the two arrays, are both newly created.
     * No previously existing Neighbors object or array is changed.
     * <p/>
     * (NOTE:  In the neighborList array, do not return any internal data
     * structure you use to represent vertices!  Return only the same objects
     * that were provided by the calling application in calls to addVertex().)
     * <p/>
     * Running time:  O(d), where d is the degree of "vertex".
     */
    public Neighbors getNeighbors(Object vertex) {
        if (vertexTable.find(vertex) == null || degree(vertex) == 0) {
            return null;
        } else {
            Neighbors neighbors = new Neighbors();
            neighbors.neighborList = new Object[degree(vertex)];
            neighbors.weightList = new int[degree(vertex)];

            InternalVertex internalVertex = getInternal(vertex);
            List pairList = internalVertex.adjList;
            ListNode node = pairList.front();

            for (int i = 0; i < degree(vertex); i++) {
                try {

                    VertexPair pair = (VertexPair) node.item();
                    Object neighbor = pair.theOther(vertex);
                    neighbors.neighborList[i] = neighbor;

                    InternalEdge edge = getInternal(pair);
                    neighbors.weightList[i] = edge.weight;
                    node = node.next();

                } catch (InvalidNodeException e) {
                    e.printStackTrace();
                }
            }

            return neighbors;
        }
    }

    /**
     * addEdge() adds an edge (u, v) to the graph.  If either of the parameters
     * u and v does not represent a vertex of the graph, the graph is unchanged.
     * The edge is assigned a weight of "weight".  If the graph already contains
     * edge (u, v), the weight is updated to reflect the new value.  Self-edges
     * (where u.equals(v)) are allowed.
     * <p/>
     * Running time:  O(1).
     */
    public void addEdge(Object u, Object v, int weight) {

        if (vertexTable.find(u) == null || vertexTable.find(v) == null) {
            return;
        }

        VertexPair pair = new VertexPair(u, v);

        if (edgeTable.find(pair) == null) {
            InternalEdge edge = new InternalEdge(u, v, weight);

            ListNode node1;
            ListNode node2;

            node1 = (getInternal(u)).addEdge(pair);
            edge.node1 = node1;

            if (!u.equals(v)) {
                node2 = (getInternal(v)).addEdge(pair);
                edge.node2 = node2;
                edge.self = false;
            }

            edgeTable.insert(pair, edge);
        } else {
            InternalEdge edge = (InternalEdge) edgeTable.find(pair).value();
            edge.weight = weight;
        }

    }

    /**
     * removeEdge() removes an edge (u, v) from the graph.  If either of the
     * parameters u and v does not represent a vertex of the graph, the graph
     * is unchanged.  If (u, v) is not an edge of the graph, the graph is
     * unchanged.
     * <p/>
     * Running time:  O(1).
     */
    public void removeEdge(Object u, Object v) {
        if (vertexTable.find(u) == null || vertexTable.find(v) == null) {
            return;
        } else {
            VertexPair pair = new VertexPair(u, v);

            if (edgeTable.find(pair) != null) {
                InternalEdge edge = getInternal(pair);
                edge.deleteMe();
                edgeTable.remove(pair);
            } else {
                return;
            }
        }
    }

    /**
     * isEdge() returns true if (u, v) is an edge of the graph.  Returns false
     * if (u, v) is not an edge (including the case where either of the
     * parameters u and v does not represent a vertex of the graph).
     * <p/>
     * Running time:  O(1).
     */
    public boolean isEdge(Object u, Object v) {
        return edgeTable.find(new VertexPair(u, v)) != null;
    }

    /**
     * weight() returns the weight of (u, v).  Returns zero if (u, v) is not
     * an edge (including the case where either of the parameters u and v does
     * not represent a vertex of the graph).
     * <p/>
     * (NOTE:  A well-behaved application should try to avoid calling this
     * method for an edge that is not in the graph, and should certainly not
     * treat the result as if it actually represents an edge with weight zero.
     * However, some sort of default response is necessary for missing edges,
     * so we return zero.  An exception would be more appropriate, but also more
     * annoying.)
     * <p/>
     * Running time:  O(1).
     */
    public int weight(Object u, Object v) {
        if (vertexTable.find(u) == null || vertexTable.find(v) == null || edgeTable.find(new VertexPair(u, v)) == null) {
            return 0;
        } else {
            return getInternal(new VertexPair(u, v)).weight;
        }
    }

    public static void main(String[] args) {
        WUGraph testDriver = new WUGraph();
        testDriver.addVertex("123");
        testDriver.addVertex("1");
        testDriver.addVertex("1");
        testDriver.addVertex("3");
        testDriver.addVertex("9");

        testDriver.addEdge("1", "1", 10);
        testDriver.addEdge("1", "1", 1);
        testDriver.addEdge("3", "123", 999999);
        testDriver.addEdge("1", "3", 1);
        testDriver.addEdge("3", "1", 1);
        testDriver.addEdge("1", "1", 1);
        testDriver.addEdge("1", "123", 999);
        System.out.println(testDriver.edgeTable);

        testDriver.removeVertex("1");
        System.out.println(Arrays.toString(testDriver.getVertices()));
        System.out.println(testDriver.edgeTable);
        System.out.println("end");
        System.out.println(testDriver.getNeighbors("9"));

//        System.out.println(testDriver.degree("1"));
    }

}

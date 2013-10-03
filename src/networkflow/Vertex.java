/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package networkflow;

import java.util.LinkedList;

/**
 *
 * @author Jacob
 */
public class Vertex {
    private String name;
    private int index;
    private LinkedList<Edge> edges;
    public Vertex(String name, int index) {
        this.name = name;
        this.index = index;
        edges = new LinkedList<>();
    }
    public String getName() {
        return name;
    }
    public int getIndex() {
        return index;
    }
    public Edge[] getEdges() {
        Edge[] arr = new Edge[edges.size()];
        return edges.toArray(arr);
    }
    public void addEdge(Vertex to, int capacity) {
        edges.add(new Edge(this, to, capacity));
    }
    @Override
    public String toString() {
        return getName();
    }
    @Override
    public boolean equals(Object that) {
        return that instanceof Vertex ?
                this.index == ((Vertex)that).index : false;
    }
    @Override
    public int hashCode() {
        return index;
    }
}

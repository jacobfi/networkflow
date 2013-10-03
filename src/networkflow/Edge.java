/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package networkflow;

/**
 *
 * @author Jacob
 */
public class Edge implements Comparable<Edge> {
    private Vertex from, to;
    private int capacity;
    public Edge(Vertex from, Vertex to, int capacity) {
        this.from = from;
        this.to = to;
        this.capacity = capacity > -1 ? capacity : Integer.MAX_VALUE;
    }
    public Vertex getFrom() {
        return from;
    }
    public Vertex getTo() {
        return to;
    }
    public int getCapacity() {
        return capacity;
    }
    public void addCapacity(int value) {
        if (capacity != Integer.MAX_VALUE)
            capacity += value;
    }
    public void subtractCapacity(int value) {
        if (capacity != Integer.MAX_VALUE)
            capacity -= value;
    }
    public Edge getInversion() {
        for (Edge e : to.getEdges())
            if (e.to.equals(this.from))
                return e;
        assert(false);
        return null;
    }
    @Override
    public int compareTo(Edge that) {
        return this.capacity - that.capacity;
    }
    @Override
    public String toString() {
        return from + "[" + from.getIndex() + "]"
                + " -> " + to + "[" + to.getIndex() + "]"
                + " (" + capacity + ")";
    }
}

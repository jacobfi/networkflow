/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package networkflow;

import java.io.*;
import java.util.*;

/**
 *
 * @author Jacob
 */
public class NetworkFlow {
    
    private static final String SOURCE = "ORIGINS";
    private static final String SINK = "DESTINATIONS";
    
    private Vertex[] G;
    private Vertex[] Gf;
    
    private Vertex Gf_source;
    
    private List<Edge> bottleneck;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        NetworkFlow problem = new NetworkFlow();
        problem.readInput("rail.txt");
        problem.FordFulkerson();
    }
    
    private void readInput(String filename) {
        try {
            Scanner scanner = new Scanner(new File(filename));
            int n = scanner.nextInt();
            G = new Vertex[n];
            Gf = new Vertex[n];
            for (int i = 0; i < n; i++) {
                String name = scanner.next();
                G[i] = new Vertex(name, i);
                Gf[i] = new Vertex(name, i);
                if (name.equals(SOURCE))
                    Gf_source = Gf[i];
            }
            int m = scanner.nextInt();
            for (int i = 0; i < m; i++) {
                int from = scanner.nextInt();
                int to = scanner.nextInt();
                int cap = scanner.nextInt();
                G[from].addEdge(G[to], cap);
                G[to].addEdge(G[from], cap);
                Gf[from].addEdge(Gf[to], cap);
                Gf[to].addEdge(Gf[from], cap);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
    }
    
    private void FordFulkerson() {
        int f, flow = 0;
        while ((f = augment()) != -1)
            flow += f;
        System.out.println("Max-flow: " + flow);
        System.out.println("Min-cut:");
        for (Edge e : bottleneck)
            System.out.println(e);
    }
    
    private int augment() {
        Stack<Edge> stack = new Stack<>();
        HashMap<Vertex, Edge> visited = new HashMap<>();
        for (Edge e : Gf_source.getEdges())
            stack.push(e);
        visited.put(Gf_source, null);
        while (!stack.empty()) {
            Edge edge = stack.pop();
            if (edge.getCapacity() == 0)
                continue;
            Vertex vertex = edge.getTo();
            if (vertex.getName().equals(SINK)) {
                LinkedList<Edge> path = new LinkedList<>();
                path.add(edge);
                while (!edge.getFrom().getName().equals(SOURCE))
                    path.add(edge = visited.get(edge.getFrom()));
                int flow = Collections.min(path).getCapacity();
                for (Edge e : path) {
                    e.subtractCapacity(flow);
                    e.getInversion().addCapacity(flow);
                }
                return flow;
            }
            if (visited.containsKey(vertex))
                continue;
            visited.put(vertex, edge);
            for (Edge e : vertex.getEdges())
                stack.push(e);
        }
        bottleneck = new LinkedList<>();
        for (Vertex v : visited.keySet())
            for (Edge e : G[v.getIndex()].getEdges())
                if (!visited.containsKey(e.getTo()))
                    bottleneck.add(e);
        return -1;
    }
}

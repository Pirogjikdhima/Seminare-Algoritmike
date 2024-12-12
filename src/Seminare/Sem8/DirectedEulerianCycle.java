package Seminare.Sem8;

import Seminare.Sem7.Stack;
import Seminare.Sem7.StdOut;

import java.util.Iterator;

public class DirectedEulerianCycle {
    private Stack<Integer> cycle = null;  // Eulerian cycle; null if no such cycle

    /**
     * Computes an Eulerian cycle in the specified digraph, if one exists.
     *
     * @param G the digraph
     */
    public DirectedEulerianCycle(Digraph G) {

        // must have at least one edge
        if (G.E() == 0) return;

        // necessary condition: indegree(v) = outdegree(v) for each vertex v
        // (without this check, DFS might return a path instead of a cycle)
        for (int v = 0; v < G.V(); v++)
            if (G.outdegree(v) != G.indegree(v))
                return;

        // create local view of adjacency lists, to iterate one vertex at a time
        Iterator<Integer>[] adj = (Iterator<Integer>[]) new Iterator[G.V()];
        for (int v = 0; v < G.V(); v++)
            adj[v] = G.adj(v).iterator();

        // initialize stack with any non-isolated vertex
        int s = nonIsolatedVertex(G);
        Stack<Integer> stack = new Stack<Integer>();
        stack.push(s);

        // greedily add to putative cycle, depth-first search style
        cycle = new Stack<Integer>();
        while (!stack.isEmpty()) {
            int v = stack.pop();
            while (adj[v].hasNext()) {
                stack.push(v);
                v = adj[v].next();
            }
            // add vertex with no more leaving edges to cycle
            cycle.push(v);
        }

        // check if all edges have been used
        // (in case there are two or more vertex-disjoint Eulerian cycles)
        if (cycle.size() != G.E() + 1)
            cycle = null;

    }

    /**
     * Returns the sequence of vertices on an Eulerian cycle.
     *
     * @return the sequence of vertices on an Eulerian cycle;
     * {@code null} if no such cycle
     */
    public Iterable<Integer> cycle() {
        return cycle;
    }

    /**
     * Returns true if the digraph has an Eulerian cycle.
     *
     * @return {@code true} if the digraph has an Eulerian cycle;
     * {@code false} otherwise
     */
    public boolean hasEulerianCycle() {
        return cycle != null;
    }

    // returns any non-isolated vertex; -1 if no such vertex
    private static int nonIsolatedVertex(Digraph G) {
        for (int v = 0; v < G.V(); v++)
            if (G.outdegree(v) > 0)
                return v;
        return -1;
    }


    private static void unitTest(Digraph G, String description) {
        StdOut.println(description);
        StdOut.println("-------------------------------------");
        StdOut.print(G);

        DirectedEulerianCycle euler = new DirectedEulerianCycle(G);

        StdOut.print("Eulerian cycle: ");
        if (euler.hasEulerianCycle()) {
            for (int v : euler.cycle()) {
                StdOut.print(v + " ");
            }
            StdOut.println();
        } else {
            StdOut.println("none");
        }
        StdOut.println();
    }


}

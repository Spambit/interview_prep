/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author sambit
 */
public class DirectedGraph {

    public static class Vertex {

        int data;
        ArrayList<Vertex> paths = new ArrayList();
        public boolean visited;

        private Vertex(int data) {
            this.data = data;
        }

        private static Vertex createVertexWithData(int data) {
            return new Vertex(data);
        }

        public String toString() {
            return "" + this.data;
        }

    }

    public static Vertex addVertexWithData(int data, Vertex from, boolean isOneWay) {
        Vertex vertex = Vertex.createVertexWithData(data);
        if (from != null) {
            from.paths.add(vertex);
        }
        if (!isOneWay) {
            vertex.paths.add(from);
        }
        return vertex;
    }

    public static Vertex addVertex(Vertex vertex, Vertex from, boolean isOneWay) {
        if (from != null) {
            from.paths.add(vertex);
        }
        if (!isOneWay) {
            vertex.paths.add(from);
        }
        return vertex;
    }

    private static ArrayList<Vertex> vertices = new ArrayList();

    public static Vertex createRandomGraphWithArray(int[] arr, int maxPathsFromOneVertex) {
        vertices.clear();

        int randomIndex = (int) RandomNumber.randNext(arr.length - 1);
        int randomData = arr[randomIndex];

        Vertex start = DirectedGraph.addVertexWithData(randomData, null, false);
        vertices.add(start);
        for (int i = 0; i < arr.length; i++) {
            Vertex v = null, from = start;
            int maxPathsrandom = (int) RandomNumber.randNext(maxPathsFromOneVertex);
            for (int j = 0; j < maxPathsrandom; j++) {
                int index = (int) RandomNumber.randNext(arr.length - 1);
                int data = arr[i];
                v = Vertex.createVertexWithData(data);
                if (vertices.size() > (int) arr.length / 2) {
                    from = vertices.get(index);
                }
                DirectedGraph.addVertex(v, from, false);
                vertices.add(v);
            }

        }
        return start;

    }

    public static Vertex createTestGraph() {
        vertices.clear();

        Vertex five = Vertex.createVertexWithData(5);
        Vertex seven = Vertex.createVertexWithData(7);
        Vertex six = Vertex.createVertexWithData(6);
        Vertex eight = Vertex.createVertexWithData(8);
        Vertex three = Vertex.createVertexWithData(3);
        Vertex nine = Vertex.createVertexWithData(9);

        DirectedGraph.addVertex(five, nine, true);
        DirectedGraph.addVertex(seven, five, true);
        DirectedGraph.addVertex(six, seven, false);
        DirectedGraph.addVertex(six, eight, true);
        DirectedGraph.addVertex(eight, seven, true);
        DirectedGraph.addVertex(nine, three, true);
        DirectedGraph.addVertex(six, nine, true);
        DirectedGraph.addVertex(eight, three, true);

        vertices.add(five);
        vertices.add(seven);
        vertices.add(nine);
        vertices.add(three);
        vertices.add(eight);
        vertices.add(six);

        return five;
    }

    private static void clearVisited() {
        for (Vertex vertice : vertices) {
            vertice.visited = false;
        }
    }

    private static int numberOfVertices() {
        return vertices.size();
    }

    private static Vertex search(int data) {
        for (Vertex vertice : vertices) {
            if (vertice.data == data) {
                return vertice;
            }
        }
        return null;
    }

    private static Vertex DFS(int from, int to) {
        clearVisited();
        Vertex fromV = search(from);
        return DFSInternal(fromV, to);
    }

    private static Vertex BFS(int from, int to) {
        clearVisited();
        Vertex fromV = search(from);
        LinkedList<Vertex> q = new LinkedList();
        return BFSInternal(fromV, to, q);
    }

    private static Vertex DFSInternal(Vertex vertex, int data) {

        if (vertex == null) {
            return null;
        }
        vertex.visited = true;
        if (vertex.data == data) {
            return vertex;
        }

        Vertex found = null;
        for (Vertex child : vertex.paths) {
            if (!child.visited) {
                found = DFSInternal(child, data);
                if (found != null) {
                    break;
                }
            }
        }

        return found;
    }

    private static Vertex BFSInternal(Vertex vertex, int data, LinkedList<Vertex> q) {

        if (vertex == null) {
            return null;
        }
        vertex.visited = true;
        if (vertex.data == data) {
            return vertex;
        }

        for (Vertex child : vertex.paths) {
            if (!child.visited) {
                q.add(child);
            }
        }
        Vertex found = BFSInternal(q.poll(), data, q);
        return found;
    }

    private static ArrayList<Vertex> allConnectedVertiesByDFS(Vertex vertex) {
        ArrayList<Vertex> list = new ArrayList();
        if (vertex == null || vertex.visited) {
            return list;
        }

        vertex.visited = true;
        list.add(vertex);

        for (Vertex child : vertex.paths) {
            if (!child.visited) {
                ArrayList<Vertex> visitedList = allConnectedVertiesByDFS(child);
                if (!visitedList.isEmpty()) {
                    list.addAll(visitedList);
                }
            }
        }
        return list;
    }

    public static boolean doesPathExists(int from, int to, boolean isDFS) {
        if (isDFS) {
            return DFS(from, to) != null;
        } else {
            return BFS(from, to) != null;
        }
    }

    public static boolean isCelebrity(int data) {
        ArrayList<Vertex> allConnectedVertices = allConnectedVertiesByDFS(vertices.get(0));
        for (Vertex vertex : allConnectedVertices) {
            if (vertex.data == data) {
                if (vertex.paths.isEmpty()) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean detectLoopInternal(Vertex currentlyVisiting, Vertex introducedBy, ArrayList<Vertex> visiting) {

        boolean loopDetected = false;
        if (currentlyVisiting == null) {
            return false;
        }

        if (visiting.indexOf(currentlyVisiting) != -1) {
            //Found an item that is already being visited. -- loop exists here.
            return true;
        } else {
            if (!currentlyVisiting.visited) {
                visiting.add(currentlyVisiting);
            }
        }

        boolean biDirectionalEdgeLoopOrSelfLoopExists = false;
        for (Vertex child : currentlyVisiting.paths) {
            if (child == currentlyVisiting || child == introducedBy) {
                //we shall not count bidirectional path which will give wrong result. That's not a loop. 
                //Also exclude self loop
                biDirectionalEdgeLoopOrSelfLoopExists = true;
                continue;
            }
            loopDetected = detectLoopInternal(child, currentlyVisiting, visiting);
            if(loopDetected) break;
        }
        if (!biDirectionalEdgeLoopOrSelfLoopExists && !loopDetected) {
            currentlyVisiting.visited = true; //all children are visited- so remove from visiting list
            visiting.remove(currentlyVisiting);
        }
        return loopDetected;
    }

    public static boolean doesExistsLoop() {
        ArrayList<Vertex> visiting = new ArrayList();
        boolean loop = detectLoopInternal(vertices.get(0), null, visiting);
        System.out.println(visiting);
        return loop;
    }
}

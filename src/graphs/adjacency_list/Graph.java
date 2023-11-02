package graphs.adjacency_list;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class Graph extends TreeSet<Vertex> {
    public Graph() {
        super();
    }
    
    public void addVertex(Vertex v) {
        this.add(v);
    }
    
    Vertex get(String vertexName) {
        Iterator it = this.iterator();
        while (it.hasNext()) {
            Vertex v = (Vertex) (it.next());
            if (v.name.equals(vertexName))
                return v;
        }
        return null;
    }
    
    public boolean addEdge(String nameFrom, String nameTo) {
        Vertex u = this.get(nameFrom);
        Vertex v = this.get(nameTo);
        if (u == null || v == null) 
            return false;
        
        Edge e = new Edge(u, v, 1);
        u.adjList.add(e);
        return true;
    }        
    
    public boolean addEdge(Vertex u, Vertex v) {
        if (u == null || v == null) 
            return false;
        
        Edge e = new Edge(u, v, 1);
        u.adjList.add(e);
        return true;
    }
    
    public boolean addEdge(String nameFrom, String nameTo, double w) {
        Vertex u = this.get(nameFrom);
        Vertex v = this.get(nameTo);
        if (u == null || v == null) 
            return false;
        
        Edge e = new Edge(u, v, w);
        u.adjList.add(e);
        return true;
    }
    
    public boolean addEdge(Vertex u, Vertex v, double w) {
        if (u == null || v == null) 
            return false;
       
        Edge e = new Edge(u, v, w);
        u.adjList.add(e);
        return true;
    }

    public Graph loadFromFile1(String fName) {
        Graph g = null;
        File f = new File(fName);
        if (!f.exists()) {
            System.out.println("File not found!");
            System.exit(0);
            return g;
        }
        
        try {
            FileReader fr = new FileReader(f);
            BufferedReader bf = new BufferedReader(fr);
            String line = null;
            StringTokenizer stk = null;
            line = bf.readLine();
            if (line != null) {
                g = new Graph();
                stk = new StringTokenizer(line, " ");
                while (stk.hasMoreTokens()) {
                    String name = stk.nextToken();
                    Vertex v = new Vertex(name);
                    g.add(v);
                }
            }
            while ((line = bf.readLine()) != null) { 
                stk = new StringTokenizer(line, " ");
                String nameFrom = stk.nextToken();
                Vertex u = g.get(nameFrom);
                while (stk.hasMoreTokens()) {
                    String nameTo = stk.nextToken();
                    Vertex v = g.get(nameTo);
                    g.addEdge(u, v);
                }
            }
            bf.close();
            fr.close();
        } catch (IOException e) {
            g = null;
        }
        return g;
    }
    
    @Override
    public String toString() {
    if (this == null || this.isEmpty()) {
        return "Empty Graph";
    }

    StringBuilder sb = new StringBuilder("Graph:\n");

    Iterator<Vertex> vertexIterator = this.iterator();
    while (vertexIterator.hasNext()) {
        Vertex u = vertexIterator.next();
        sb.append(u.getName()).append("\t");

        Iterator<Edge> edgeIterator = u.getAdjList().iterator();
        while (edgeIterator.hasNext()) {
            Edge e = edgeIterator.next();
            sb.append(e.toString()).append(" ");
        }
        sb.append("\n");
    }
    return sb.toString();
}
    
    protected int DFS(Vertex u, int order, ArrayList<Edge> edges) {
        int newOrder = order + 1;
        u.setNum(order);
        Iterator<Edge> it = u.getAdjList().iterator();
        while (it.hasNext()) {
            Edge e = it.next();
            Vertex v = e.getV();
            if (v.num == 0) {
                edges.add(e);
                newOrder = DFS(v, newOrder, edges);
            }
        }
        return newOrder;
    }
    
    public ArrayList<Edge> depthFirstSearch() {
        ArrayList result = new ArrayList<Edge>();
        Object[] vertices = this.toArray();
        for (int i = 0; i < vertices.length; i++) {
            Vertex v = (Vertex) vertices[i];
            v.num = 0;
        }
        
        Integer order = 1;
        for (int i = 0; i < vertices.length; i++) {
            Vertex v = (Vertex) vertices[i];
            if (v.num == 0) 
                order = DFS(v, order, result);
        }
        return !result.isEmpty()? result : null;
    }
    
    public ArrayList<Edge> breadthFirstSearch() {
        ArrayList result = new ArrayList<Edge>();
        MyQueue<Vertex> queue = new MyQueue<Vertex>();
        Object[] vertices = this.toArray();
        for (int i = 0; i < vertices.length; i++) {
            Vertex v = (Vertex) vertices[i];
            v.num = 0;
        }
        
        int order = 1;
        for (int i = 0; i < vertices.length; i++) {
            Vertex u = (Vertex) vertices[i];
            if (u.num == 0) {
                u.num = order++;
                queue.enqueue(u);
                while (!queue.isEmpty()) {
                    u = queue.dequeue();
                    Iterator<Edge> it = u.adjList.iterator();
                    while (it.hasNext()) {
                        Edge e = it.next();
                        Vertex v = e.getV();
                        if (v.num == 0) {
                            v.num = order++;
                            queue.enqueue(v);
                            result.add(e);
                        }
                    }
                }
            }
        }
        return result.size()> 0? result : null;
    }
    
    public void printEdges(ArrayList<Edge> edges, PrintStream pw) {
        if (edges == null || edges.isEmpty())
            pw.println("No edges");
        else {      
            pw.println("Set of edges: ");
            pw.println(edges);
        }
    }
}

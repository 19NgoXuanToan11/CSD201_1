package graphs.adjacency_list;

public class Edge implements Comparable {
    Vertex u;
    Vertex v;
    double weight;
    
    public Edge(Vertex u, Vertex v) {
        this.u = u;
        this.v = v;
        weight = 1;
    }
    
    public Edge(Vertex u, Vertex v, double w) {
        this.u = u;
        this.v = v;
        weight = w;
    }

    public Vertex getU() {
        return u;
    }

    public void setU(Vertex u) {
        this.u = u;
    }

    public Vertex getV() {
        return v;
    }

    public void setV(Vertex v) {
        this.v = v;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }                   

    @Override
    public String toString() {
        return "{" + u.name + ", " + v.name + ", " + weight + "}";
    }

    @Override
    public int compareTo(Object o) {
        Edge e = ((Edge) o);
        return (u.name + v.name).compareTo(e.u.name + e.v.name);
    }
}

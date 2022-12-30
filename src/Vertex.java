import java.io.File;
import java.util.ArrayList;

/**
 * Class which implements Vertex in a graph.
 */
public class Vertex {
    private final ArrayList<Vertex> outGoingEdges;

    /**
     * Getter for outgoing edges list.
     *
     * @return - outEdges.
     */
    public ArrayList<Vertex> getOutGoingEdges() {
        return outGoingEdges;
    }

    private final File data;

    /**
     * Getter for the File contained in Vertex
     *
     * @return - File data.
     */
    public File getData() {
        return data;
    }

    private boolean visited = false;

    /**
     * Setter for visited field.
     *
     * @param visited - State of Vertex (was it ever visited).
     */
    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    /**
     * Getter for visitedRightNow field.
     *
     * @return - visited - State of Vertex (was it ever visited).
     */
    public boolean isVisited() {
        return visited;
    }

    private boolean visitedRightNow = false;

    /**
     * Setter for visitedRightNow field.
     *
     * @param visitedRightNow - State of Vertex (is it currently visited).
     */
    public void setVisitedRightNow(boolean visitedRightNow) {
        this.visitedRightNow = visitedRightNow;
    }

    /**
     * Getter for visitedRightNow field.
     *
     * @return - visitedRightNow - State of Vertex (is it currently visited).
     */
    public boolean isVisitedRightNow() {
        return visitedRightNow;
    }

    /**
     * Constructor, creates vertex from file.
     *
     * @param file - File to store in vertex.
     */

    public Vertex(File file) {
        this.data = file;
        outGoingEdges = new ArrayList<>(0);
    }

    /**
     * Adds outgoing edge.
     *
     * @param vertex - End of the edge.
     */
    public void addEdge(Vertex vertex) {
        outGoingEdges.add(vertex);
    }

    /**
     * Override of equals to use in comparisons.
     *
     * @param other - Object to compare this object with.
     * @return - True if equal, False otherwise.
     */

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Vertex o)) {
            return false;
        }
        return this.data == o.data;
    }
}


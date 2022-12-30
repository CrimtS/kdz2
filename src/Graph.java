import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

/**
 * Class which defines graph and its algorithms (DFS and TopSort).
 */
public class Graph {
    private final ArrayList<Vertex> vertices;

    /**
     * @param vertex - Vertex visited right now.
     * @return - True if cycle is found, false if there are none.
     */
    private boolean hasCycles(Vertex vertex) {
        vertex.setVisitedRightNow(true);
        for (Vertex nearby : vertex.getOutGoingEdges()) {
            if (nearby.isVisitedRightNow() || (!nearby.isVisited() && hasCycles(nearby))) {
                throw new RuntimeException("File " + nearby.getData().getName() + " and file " + vertex.getData().getName() +
                        " have cycling requirements, please refactor them");
            }
        }
        vertex.setVisitedRightNow(false);
        vertex.setVisited(true);
        return false;
    }

    /**
     * Recursive function, which is part of topographic sorting algorithm.
     *
     * @param vertex - Current vertex, function is invoked for its neighbours and vertex gets added to sorted list.
     * @param sorted - Arraylist which contains vertices in sorted order.
     */
    private void topSortRecursive(Vertex vertex, ArrayList<Vertex> sorted) {
        vertex.setVisited(true);
        for (Vertex value : vertex.getOutGoingEdges()) {
            if (!value.isVisited())
                topSortRecursive(value, sorted);
        }

        sorted.add(0, vertex);
    }

    /**
     * Function which starts recursive algorithm and then collects result.
     * Root is removed from the list in the end as a temporary vertex.
     *
     * @return - Arraylist of sorted vertices.
     */
    private ArrayList<Vertex> sortedVertices() {
        for (Vertex v : vertices) {
            v.setVisited(false);
        }
        ArrayList<Vertex> sorted = new ArrayList<>();
        for (Vertex vertex : vertices) {
            if (!vertex.isVisited()) {
                topSortRecursive(vertex, sorted);
            }
        }
        sorted.remove(sorted.get(0));
        return sorted;
    }

    /**
     * Function which invokes the sorting algorithm to get sorted list then concatenates its contents
     * to answer.txt file.
     *
     * @param rootPath - Path where answer.txt is created (notice, that file is created OUTSIDE the working directory
     *                 to ensure repeatability).
     * @return - File .../answer.txt which contains concatenated files from working directory.
     */
    public File concatToFile(String rootPath) {
        ArrayList<Vertex> answer = sortedVertices();
        File result = new File(new File(rootPath).getParent() + "/" + "answer.txt");
        try {
            if (result.createNewFile()) {
                try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(result.getAbsolutePath())))) {
                    for (Vertex v : answer) {
                        try (FileIterator it = new FileIterator(v.getData().getAbsolutePath())) {
                            while (it.hasNext()) {
                                writer.write(it.next() + "\n");
                            }
                        }
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex.getMessage());
                }
            } else {
                Files.delete(Paths.get(result.getAbsolutePath()));
                concatToFile(rootPath);
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex.getMessage());
        }
        return result;
    }

    /**
     * Adds edges to graph using HashMap of dependencies.
     *
     * @param adjList - HashMap which contains requirements for each file/Vertex.
     */
    private void addEdges(HashMap<File, ArrayList<File>> adjList) {
        for (File key : adjList.keySet()) {
            for (File dependant : adjList.get(key)) {
                int keyIndex = -1, depIndex = -1;
                for (int i = 0; i < vertices.size(); i++) {
                    if (Objects.equals(vertices.get(i), new Vertex(key))) {
                        keyIndex = i;
                    }
                    if (Objects.equals(vertices.get(i), new Vertex(dependant))) {
                        depIndex = i;
                    }
                }
                vertices.get(keyIndex).addEdge(vertices.get(depIndex));
            }
        }
    }

    /**
     * Constructor. Firstly, a list of Vertices is created, then addEdges is invoked to "draw" the graph.
     * "Root" vertex is created to ensure the correct working of algorithms.
     *
     * @param adjList - HashMap of Files. .keySet() is turned to list of Vertices, then HashMap is passed to addEdges.
     */
    public Graph(HashMap<File, ArrayList<File>> adjList) {
        vertices = new ArrayList<>(0);
        for (File key : adjList.keySet()) {
            Vertex temp = new Vertex(key);
            vertices.add(temp);
        }
        addEdges(adjList);
        Vertex rootVertex = new Vertex(new File("root"));
        for (Vertex v : vertices) {
            rootVertex.addEdge(v);
        }
        vertices.add(0, rootVertex);
        hasCycles(rootVertex);
    }
}

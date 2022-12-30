import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class which creates a Hashmap of files and dependencies inside given directory.
 */
public class AdjacencyList {
    private final HashMap<File, ArrayList<File>> adjList;

    public AdjacencyList(ArrayList<File> files, String rootPath) {
        adjList = new HashMap<>();
        create(files, rootPath);
    }

    /**
     * Getter for adjList.
     *
     * @return - adjList.
     */
    public HashMap<File, ArrayList<File>> getAdjList() {
        return adjList;
    }

    /**
     * Method that creates the HashMap from ArrayList of files and stores it inside adjList.
     * File is a key to ArrayList of dependent files (vertex and edges from it).
     *
     * @param files    - ArrayList of files inside the working directory.
     * @param rootPath - Path to working directory.
     */
    private void create(ArrayList<File> files, String rootPath) {
        for (File file : files) {
            if (!adjList.containsKey(file)) {
                adjList.put(file, new ArrayList<>(0));
            }
        }
        for (File file : files) {
            try (FileIterator it = new FileIterator(file.getPath())) {
                while (it.hasNext()) {
                    String line = it.next();
                    if (line.startsWith("require ")) {
                        File temp = new File(rootPath + "/" + line.substring(9, line.length() - 1));
                        if (temp.exists()) {
                            if (adjList.get(temp).contains(file)) {
                                throw new RuntimeException("File " + file.getName() + " has double requirement of " +
                                        temp.getName() + ", please refactor it");
                            }
                            adjList.get(temp).add(file);
                        }
                    }
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex.getMessage());
            }
        }
    }
}

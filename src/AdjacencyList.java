import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class AdjacencyList {
    private final HashMap<File, ArrayList<File>> adjList;

    public AdjacencyList() {
        adjList = new HashMap<>();
    }

    public HashMap<File, ArrayList<File>> getAdjList() {
        return adjList;
    }

    public void create(ArrayList<File> files, String rootPath) {
        for (File file : files) {
            if (!adjList.containsKey(file)) {
                adjList.put(file, new ArrayList<>(0));
            }
            try (FileIterator it = new FileIterator(file.getPath())) {
                while (it.hasNext()) {
                    String line = it.next();
                    if (line.startsWith("require ")) {
                        File temp = new File(rootPath + "/" + line.substring(9, line.length() - 1));
                        if (temp.exists()) {
                            adjList.get(file).add(temp);
                        }
                    }
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex.getMessage());
            }
        }
    }

    public void print() {
        for (File key : adjList.keySet()) {
            System.out.print(key.getAbsolutePath() + " requires: ");
            for (File file : adjList.get(key)) {
                System.out.print(file.getAbsolutePath() + " ");
            }
            System.out.print("\n");
        }
    }

}


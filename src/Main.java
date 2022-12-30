import java.io.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Enter path to directory:");
        String path = new InputChecker().readUntilCorrect();
        Directory directory = new Directory(path);
        AdjacencyList adjList = new AdjacencyList(directory.getFiles(), directory.getPath());
        Graph graph = new Graph(adjList.getAdjList());
        File f = graph.concatToFile(directory.getPath());
        try (FileIterator it = new FileIterator(f.getAbsolutePath())) {
            while (it.hasNext()) {
                System.out.println(it.next());
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
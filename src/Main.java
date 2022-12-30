
public class Main {
    public static void main(String[] args) {
        System.out.println("Enter path to directory:");
        String path = new InputChecker().readUntilCorrect();
        Directory directory = new Directory(path);
        AdjacencyList adjList = new AdjacencyList();
        adjList.create(directory.getFiles(), directory.getPath());

        adjList.print();
    }
}
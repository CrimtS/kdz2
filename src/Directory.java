import java.io.File;
import java.util.ArrayList;

public class Directory {
    private final File dir;
    private ArrayList<File> files;

    Directory(String path) {
        dir = new File(path);
    }

    String getPath() {
        return dir.getAbsolutePath();
    }

    private void getFilesInDirectory(File dir) {
        if (dir.isFile()) {
            files.add(dir);
        } else {
            File[] filesInside = dir.listFiles((dir1, name) -> !name.equals(".DS_Store"));
            for (File file : filesInside) {
                getFilesInDirectory(file);
            }
        }
    }

    public ArrayList<File> getFiles() {
        files = new ArrayList<>(0);
        getFilesInDirectory(dir);
        return files;
    }

}

import java.io.File;
import java.util.ArrayList;

/**
 * Class which gets the path to directory and lets user get ArrayList of files inside it.
 */
public class Directory {
    private final File dir;
    private ArrayList<File> files;

    /**
     * Constructor, creates File instance from path.
     *
     * @param path - Absolute path to directory.
     */
    public Directory(String path) {
        dir = new File(path);
    }

    /**
     * Method to get path to directory.
     *
     * @return - Absolute path.
     */
    public String getPath() {
        return dir.getAbsolutePath();
    }

    /**
     * Recursive method to get all files from directory and store them in this.files.
     *
     * @param dir - directory with files.
     */
    private void getFilesInDirectory(File dir) {
        if (dir.isFile()) {
            files.add(dir);
        } else {
            File[] filesInside = dir.listFiles((dir1, name) -> !name.equals(".DS_Store"));
            if (filesInside == null) {
                return;
            }
            for (File file : filesInside) {
                getFilesInDirectory(file);
            }
        }
    }

    /**
     * Method which starts the recursive chain.
     *
     * @return - this.files which contains all files in directory and its subdirectories.
     */
    public ArrayList<File> getFiles() {
        files = new ArrayList<>(0);
        getFilesInDirectory(dir);
        return files;
    }

}

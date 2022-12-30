import java.io.*;
import java.util.Iterator;

/**
 * Class to iterate through strings in File.
 */
public class FileIterator implements Iterator<String>, AutoCloseable {
    private final BufferedReader reader;

    /**
     * Constructor which creates new BufferedReader from path.
     *
     * @param path - Path to file to lead.
     * @throws FileNotFoundException - If file at given directory does not exist.
     */

    public FileIterator(String path) throws FileNotFoundException {
        reader = new BufferedReader(new FileReader(path));
    }

    /**
     * Part of AutoClosable() interface.
     *
     * @throws IOException - Any exception in runtime.
     */

    @Override
    public void close() throws IOException {
        reader.close();
    }

    /**
     * Checks whether Iterator can read the next line.
     *
     * @return - true/false for being able/unable to read next line.
     */
    @Override
    public boolean hasNext() {
        try {
            return reader.ready();
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Method which returns the next line in a file.
     *
     * @return - The next line in file.
     */
    @Override
    public String next() {
        if (hasNext()) {
            try {
                return reader.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        throw new RuntimeException("Trying to read after EOF");
    }
}

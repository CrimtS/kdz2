import java.io.*;
import java.util.Iterator;

public class FileIterator implements Iterator<String>, AutoCloseable {
    private final BufferedReader reader;
    private Exception lastException;

    public Exception getLastException() {
        return lastException;
    }

    public FileIterator(String path) throws FileNotFoundException {
        reader = new BufferedReader(new FileReader(path));
    }

    @Override
    public void close() throws IOException {
        reader.close();
    }

    @Override
    public boolean hasNext() {
        try {
            return reader.ready();
        } catch (IOException e) {
            lastException = e;
            return false;
        }
    }

    @Override
    public String next() {
        if (hasNext()) {
            try {
                return reader.readLine();
            } catch (IOException e) {
                lastException = e;
                throw new RuntimeException(e);
            }
        }
        throw new RuntimeException("Trying to read after EOF");
    }
}

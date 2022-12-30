import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Class which checks entered directory and ensures its existence.
 */
public class InputChecker {
    /**
     * Reads line from System.in.
     *
     * @return - Read line.
     */
    private String read() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            return reader.readLine();
        } catch (IOException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    /**
     * Reads directory from console until it's correct.
     *
     * @return - Absolute path to directory.
     */
    public String readUntilCorrect() {
        File directory = new File(read());
        while (!directory.isDirectory()) {
            System.out.println("Entered directory does not exist, please re-enter the path");
            directory = new File(read());
        }
        return directory.getAbsolutePath();
    }
}

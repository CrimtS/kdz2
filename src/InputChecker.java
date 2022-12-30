import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class InputChecker {
    private String read() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        return reader.readLine();
    }

    public String readUntilCorrect() {
        try {
            File directory = new File(read());
            while (!directory.isDirectory()) {
                System.out.println("Entered directory does not exist, please re-enter the path");
                directory = new File(read());
            }
            return directory.getAbsolutePath();
        } catch (IOException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }
}

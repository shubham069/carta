
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TestUtils {

    // Utility method to capture program output
    public static String runVestingProgram(String csv, String targetDate, int precision) {
        // Save the original System.out to restore it later
        PrintStream originalOut = System.out;
        try {
            // Create a new ByteArrayOutputStream to capture program output
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PrintStream printStream = new PrintStream(outputStream);
            // Set the program output to the new PrintStream
            System.setOut(printStream);

            // Create an array of arguments to be passed to the main method
            String[] args;
            if (precision == -1) {
                args = new String[]{csv, targetDate};
            }  else {
                args = new String[]{csv, targetDate, String.valueOf(precision)};
            }

             VestingProgram_Stage3.main(args);

            // Return the captured program output as a string
            return outputStream.toString();
        } finally {
            // Restore the original System.out
            System.setOut(originalOut);
        }
    }
    public static String createTempCsvFile(String csvContent) throws IOException {
        Path tempDirectory = Files.createTempDirectory("temp_test");
        Path tempFile = Paths.get(tempDirectory.toString(), "temp_test.csv");
        Files.write(tempFile, csvContent.getBytes());
        return tempFile.toAbsolutePath().toString();
    }
}

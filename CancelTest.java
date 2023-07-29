package companies.carta;

import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CancelTest {

    @Test
    public void testCancelSingleEntry() throws IOException {
        String csv = "VEST,E004,John Doe,ISO-004,2022-01-01,1000\n" +
                     "CANCEL,E004,John Doe,ISO-004,2022-02-01,500";
        String targetDate = "2022-02-01";
        int precision = -1;

        String csvFilePath = TestUtils.createTempCsvFile(csv);
        String expectedOutput = "E004,John Doe,ISO-004,500\n";
        String actualOutput = TestUtils.runVestingProgram(csvFilePath, targetDate, precision);

        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testCancelMultipleEntriesForSameAward() throws IOException {
        String csv = "VEST,E006,James Brown,ISO-006,2022-01-01,500\n" +
                     "CANCEL,E006,James Brown,ISO-006,2022-02-01,300\n" +
                     "CANCEL,E006,James Brown,ISO-006,2022-03-01,100.25";
        String targetDate = "2022-03-01";
        int precision = 2;

        String csvFilePath = TestUtils.createTempCsvFile(csv);
        String expectedOutput = "E006,James Brown,ISO-006,99.75\n";
        String actualOutput = TestUtils.runVestingProgram(csvFilePath, targetDate, precision);

        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testCancelWithInvalidDateFormat() throws IOException {
        String csv = "CANCEL,E009,John Doe,ISO-009,20220101,50"; // Invalid date format (missing hyphens)
        String targetDate = "2022-01-01";
        int precision = 0;

        String csvFilePath = TestUtils.createTempCsvFile(csv);
        String expectedOutput = ""; // Empty output for invalid entry
        String actualOutput = TestUtils.runVestingProgram(csvFilePath, targetDate, precision);

        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testCancelWithNegativeQuantity() throws IOException {
        String csv = "CANCEL,E010,Jane Smith,ISO-010,2022-01-01,-25"; // Negative quantity (invalid)
        String targetDate = "2022-01-01";
        int precision = -1;

        String csvFilePath = TestUtils.createTempCsvFile(csv);
        String expectedOutput = ""; // Empty output for invalid entry
        String actualOutput = TestUtils.runVestingProgram(csvFilePath, targetDate, precision);

        assertEquals(expectedOutput, actualOutput);
    }
}

package companies.carta;

import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class VestTest {

    @Test
    public void testVestSingleEntry() throws IOException {
        String csv = "VEST,E001,Alice Smith,ISO-001,2022-01-01,1000";
        String targetDate = "2022-01-01";
        int precision = 0;

        String csvFilePath = TestUtils.createTempCsvFile(csv);
        String expectedOutput = "E001,Alice Smith,ISO-001,1000\n";
        String actualOutput = TestUtils.runVestingProgram(csvFilePath, targetDate, precision);

        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testVestMultipleEntriesForSameAward() throws IOException {
        String csv = "VEST,E003,Cat Helms,ISO-003,2022-01-01,300\n" +
                "VEST,E003,Cat Helms,ISO-003,2022-02-01,500.75\n" +
                "VEST,E003,Cat Helms,ISO-003,2022-03-01,200.25";
        String targetDate = "2022-03-01";
        int precision = 2;

        String csvFilePath = TestUtils.createTempCsvFile(csv);
        String expectedOutput = "E003,Cat Helms,ISO-003,1001.00\n";
        String actualOutput = TestUtils.runVestingProgram(csvFilePath, targetDate, precision);

        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testVestWithInvalidDateFormat() throws IOException {
        String csv = "VEST,E007,John Doe,ISO-007,20220101,200"; // Invalid date format (missing hyphens)
        String targetDate = "2022-01-01";
        int precision = 2;

        String csvFilePath = TestUtils.createTempCsvFile(csv);
        String expectedOutput = "E007,John Doe,ISO-007,200.00\n";
        String actualOutput = TestUtils.runVestingProgram(csvFilePath, targetDate, precision);

        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testVestWithNegativeQuantity() throws IOException {
        String csv = "VEST,E008,Jane Smith,ISO-008,2022-01-01,-100"; // Negative quantity (invalid)
        String targetDate = "2022-01-01";
        int precision = 0;

        String csvFilePath = TestUtils.createTempCsvFile(csv);
        String expectedOutput = ""; // Empty output for invalid entry
        String actualOutput = TestUtils.runVestingProgram(csvFilePath, targetDate, precision);

        assertEquals(expectedOutput, actualOutput);
    }
}

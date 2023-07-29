
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VestingProgramTest {
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
    public void testCancelWithNegativeQuantity() throws IOException {
        String csv = "CANCEL,E010,Jane Smith,ISO-010,2022-01-01,-25"; // Negative quantity (invalid)
        String targetDate = "2022-01-01";
        int precision = -1;

        String csvFilePath = TestUtils.createTempCsvFile(csv);
        String expectedOutput = ""; // Empty output for invalid entry
        String actualOutput = TestUtils.runVestingProgram(csvFilePath, targetDate, precision);

        assertEquals(expectedOutput, actualOutput);
    }
    @Test
    public void testVestSingleEntryFractionalShares() throws IOException {
        String csv = "VEST,E002,Bobby Jones,ISO-002,2022-01-01,1000.5";
        String targetDate = "2022-01-01";
        int precision = 1;

        String csvFilePath = TestUtils.createTempCsvFile(csv);
        String expectedOutput = "E002,Bobby Jones,ISO-002,1000.5\n";
        String actualOutput = TestUtils.runVestingProgram(csvFilePath, targetDate, precision);

        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testCancelSingleEntryFractionalShares() throws IOException {
        String csv = "VEST,E005,Jane Smith,ISO-005,2022-01-01,1000.5\n" +
                "CANCEL,E005,Jane Smith,ISO-005,2022-02-01,500.25";
        String targetDate = "2022-02-01";
        int precision = 1;

        String csvFilePath = TestUtils.createTempCsvFile(csv);
        String expectedOutput = "E005,Jane Smith,ISO-005,500.3\n";
        String actualOutput = TestUtils.runVestingProgram(csvFilePath, targetDate, precision);

        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testVestWithLargePrecision() throws IOException {
        String csv = "VEST,E011,Bob Johnson,ISO-011,2022-01-01,500";
        String targetDate = "2022-01-01";
        int precision = 6; // Large precision

        String csvFilePath = TestUtils.createTempCsvFile(csv);
        String expectedOutput = "E011,Bob Johnson,ISO-011,500.000000\n";
        String actualOutput = TestUtils.runVestingProgram(csvFilePath, targetDate, precision);

        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testVestWithZeroPrecision() throws IOException {
        String csv = "VEST,E012,Chris Lee,ISO-012,2022-01-01,123.456";
        String targetDate = "2022-01-01";
        int precision = 0; // Zero precision

        String csvFilePath = TestUtils.createTempCsvFile(csv);
        String expectedOutput = "E012,Chris Lee,ISO-012,123\n";
        String actualOutput = TestUtils.runVestingProgram(csvFilePath, targetDate, precision);

        assertEquals(expectedOutput, actualOutput);
    }
}

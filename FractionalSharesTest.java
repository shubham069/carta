package companies.carta;

import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FractionalSharesTest {

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

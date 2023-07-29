
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class VestingProgram_Stage3 {

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java VestingProgram <csv_file_path> <target_date>");
            return;
        }

        String csvFilePath = args[0];
        String targetDateStr = args[1];
        int precision=0;
        if(args.length >2 )
             precision = Integer.parseInt(args[2]);

        if (precision < 0 || precision > 6) {
            System.err.println("Invalid precision. Please provide a value between 0 and 6.");
            return;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date targetDate;
        try {
            targetDate = dateFormat.parse(targetDateStr);
        } catch (ParseException e) {
            System.out.println("Invalid target date format. Please use yyyy-MM-dd.");
            return;
        }

        Map<String, Double> vestedShares = new TreeMap<>();
        Map<String, String> employeeNames = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values[0].equals("VEST")) {
                    String employeeId = values[1];
                    String employeeName = values[2];
                    String awardId = values[3];
                    Date vestingDate = dateFormat.parse(values[4]);
                    double quantity = Double.parseDouble(values[5]);

                    // Store employee names in a separate map
                    employeeNames.put(employeeId, employeeName);

                    // Check if the vesting date is on or before the target date and add vested shares
                    if (!vestingDate.after(targetDate)) {
                        vestedShares.put(employeeId + awardId,
                                vestedShares.getOrDefault(employeeId + awardId, 0.0) + quantity);
                    } else { // To include unvested employee awards
                        if (!vestedShares.containsKey(employeeId + awardId)) {
                            vestedShares.put(employeeId + awardId, 0.0);
                        }
                    }
                } else if (values[0].equals("CANCEL")) {
                    Date vestingDate = dateFormat.parse(values[4]);
                    if (!vestingDate.after(targetDate)) {
                        String employeeId = values[1];
                        String awardId = values[3];
                        double quantity = Double.parseDouble(values[5]);
                        // Subtract canceled shares from vested shares for the corresponding award
                        if(vestedShares.containsKey(employeeId+awardId))
                            vestedShares.put(employeeId + awardId, vestedShares.get(employeeId + awardId) - quantity);
                        else{
                            System.err.println("Cannot cancel the vest as there is no vesting present");
                        }
                    }
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return;
        }

        // Print the output with employee names and format the decimal precision
        for (Map.Entry<String, Double> entry : vestedShares.entrySet()) {
            String employeeAward = entry.getKey();
            String employeeId = employeeAward.substring(0, 4);
            String awardId = employeeAward.substring(4);
            double totalVestedShares = entry.getValue();
            String employeeName = employeeNames.get(employeeId);
            String format = "%s,%s,%s,%." + precision + "f\n";
            System.out.printf(format, employeeId, employeeName, awardId, totalVestedShares);
        }
    }
}


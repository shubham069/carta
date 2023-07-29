package companies.carta;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class VestingProgram_Stage2 {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java VestingProgram <csv_file_path> <target_date>");
            return;
        }

        String csvFilePath = args[0];
        String targetDateStr = args[1];

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date targetDate;
        try {
            targetDate = dateFormat.parse(targetDateStr);
        } catch (ParseException e) {
            System.out.println("Invalid target date format. Please use yyyy-MM-dd.");
            return;
        }

        Map<String, Integer> vestedShares = new TreeMap<>();
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
                    int quantity = Integer.parseInt(values[5]);

                    employeeNames.put(employeeId, employeeName);

                    if (!vestedShares.containsKey(employeeId + awardId) && !vestingDate.after(targetDate)) {
                        vestedShares.put(employeeId + awardId,
                                vestedShares.getOrDefault(employeeId + awardId, 0) + quantity);
                    }else{ // to include unvested employee awards
                        if(!vestedShares.containsKey(employeeId+awardId))
                            vestedShares.put(employeeId + awardId, 0);
                    }
                }
                else if(values[0].equals("CANCEL")) {
                    Date vestingDate = dateFormat.parse(values[4]);
                    if (!vestingDate.after(targetDate)) {
                        String employeeId = values[1];
                        String awardId = values[3];
                        int quantity = Integer.parseInt(values[5]);
                        vestedShares.put(employeeId + awardId, vestedShares.get(employeeId + awardId) - quantity);
                    }
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return;
        }

        // Print the output with employee names
        for (Map.Entry<String, Integer> entry : vestedShares.entrySet()) {
            String employeeAward = entry.getKey();
            String employeeId = employeeAward.substring(0, 4);
            String awardId = employeeAward.substring(4);
            int totalVestedShares = entry.getValue();
            String employeeName = employeeNames.get(employeeId);
            System.out.println(employeeId + "," + employeeName + "," + awardId + "," + totalVestedShares);
        }
    }
}
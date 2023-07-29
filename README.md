# Vesting Program

This is a Java program that reads vesting events from a CSV file and calculates the total shares vested on or before a given target date. It also considers cancellation events and deducts canceled shares from vested shares.

## How to Run

To run the Vesting Program, follow the steps below:

1. Compile the Java source file:
   `javac VestingProgram_Stage3.java`

2. Execute the compiled program with the required arguments:

`Usage: java companies.carta.VestingProgram_Stage3 <csv_file_path> <target_date> <precision>`

- `<csv_file_path>`: The path to the CSV file containing vesting events.
- `<target_date>`: The target date in the format "yyyy-MM-dd".
- `<precision>`: The number of decimal places to consider in the output (0 to 6).

Example:
`java companies.carta.VestingProgram_Stage3 example1.csv 2022-01-01 2`


## CSV File Format

The input CSV file should have the following format:

`VEST,<<EMPLOYEE ID>>,<<EMPLOYEE NAME>>,<<AWARD ID>>,<<DATE>>,<<QUANTITY>>` <br>
`CANCEL,<<EMPLOYEE ID>>,<<EMPLOYEE NAME>>,<<AWARD ID>>,<<DATE>>,<<QUANTITY>>`


- VEST: Represents a vesting event where shares are vested.
- CANCEL: Represents a cancellation event where shares are canceled.

## Output Format

The output will contain the total shares vested on or before the target date for each employee and award. It will be displayed in the following format:

`<<EMPLOYEE ID>>, <<EMPLOYEE NAME>>, <<AWARD ID>>, <<TOTAL VESTED SHARES>>`



## Precision

The precision argument determines the number of decimal places to consider in the output. For example, if precision is set to 2, the output will have two decimal places (e.g., 1000.50). If precision is set to 0, the output will be an integer value (e.g., 1000).

Please compile the VestingProgram_Stage3.java file and run the program with the required arguments as explained in the README. The program will read the CSV file, process the vesting and cancellation events, and output the total shares vested for each employee and award.

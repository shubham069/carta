# Vesting Program

## Introduction
This repository contains the source code for the Vesting Program, which processes CSV files containing vesting events and calculates the total vested shares for each employee and award on or before a given target date.

## Prerequisites
- Java Development Kit (JDK) version 8 or above must be installed on your system.

## Running the Vesting Program
To run the Vesting Program, use the `vesting_program` script followed by the appropriate arguments. The program accepts three positional arguments:
1. CSV file path: The path to the CSV file containing the vesting events.
2. Target date: The target date in the format "yyyy-MM-dd".
3. Precision (optional): The number of decimal places for the fractional shares (default is 0).

### Examples:
- To calculate the total vested shares for each employee and award on or before the target date with precision 2:
`./vesting_program example3.csv 2021-01-01 2`

- To calculate the total vested shares for each employee and award on or before the target date with default precision (0):
`./vesting_program example2.csv 2021-01-01`

- To calculate the total vested shares for each employee and award on or before the target date and include unvested awards:
`./vesting_program example1.csv 2020-04-01`


## Test Results
Added few new test scenarios present in three files namely test1.csv, test2.csv and test3.csv
The expected output for each test scenario is as follows:
1. Running `./vesting_program test3.csv 2021-01-01 2` should output:
   ```
   E001,Alice Smith,ISO-001,1000.55   
   E001,Alice Smith,ISO-002,99.13    
   E002,Bobby Jones,ISO-001,50.50  
   E003,Cat Helms,ISO-003,0.00  
   ```
2. Running `./vesting_program test2.csv 2021-01-01 2` should output:
    ```
   E001,Alice Smith,ISO-001,500.00
   E001,Alice Smith,ISO-002,500.50
   E002,Bobby Jones,ISO-001,200.00
   E002,Bobby Jones,ISO-002,49.87
   E003,Cat Helms,ISO-001,300.50
   E004,David Johnson,ISO-001,1000.00
    ```

3. Running `./vesting_program test1.csv 2021-01-01 1` should output:
   ```
   E001,Alice Smith,ISO-001,299.8
   E002,Bobby Jones,ISO-002,801.0
   E003,Cat Helms,ISO-003,0.0
   ```

## Unit Testing with Vesting Program Test
Added few unit tests for the Vesting Program, use the `vesting_program_test` script. 
Go to the test folder and run the following script:

### Example:
`./vesting_program_test`

Feel free to run the Vesting Program and Vesting Program Tests using the provided instructions and check the output against the expected results.

If you have any questions or need further assistance, feel free to reach out!

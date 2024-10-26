# Pre-requisites

* Java 1.8
* Gradle 7.6.2

# How to run the code

We have provided scripts to execute the code.

Run `run.sh` if you are Linux/Unix/macOS Operating systems and `run.bat` if you are on Windows.  Both the files run the commands silently and prints only output from the input file `sample_input/input1.txt`. You are supposed to add the input commands in the file from the appropriate problem statement.

Internally both the scripts run the following commands

* `gradle clean build -x test --no-daemon` - This will create a jar file `geektrust.jar` in the `build/libs` folder.
* `java -jar build/libs/geektrust.jar sample_input/input1.txt` - This will execute the jar file passing in the sample input file as the command line argument

# Running the code for multiple test cases

 Please fill `input1.txt` and `input2.txt` with the input commands and use those files in `run.bat` or `run.sh`. Replace `java -jar build/libs/geektrust.jar sample_input/input1.txt` with `java -jar build/libs/geektrust.jar sample_input/input2.txt` to run the test case from the second file.

Each input file executes 1 test case.

# How to execute the unit tests

 `gradle clean test --no-daemon` will execute the unit test cases.

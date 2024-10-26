#!/bin/bash

gradle clean build --no-daemon
java -jar build/libs/geektrust.jar sample_input/input1.txt
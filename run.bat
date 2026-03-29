@echo off
echo Starting Smart Hospital Application...
echo This might take a few moments to compile and launch.
.\apache-maven-3.9.6\bin\mvn.cmd clean compile javafx:run
pause

@echo off
REM Compile
javac -cp "lib\mysql-connector-j-8.0.33.jar;src\main\java" src\main\java\utils\DBConnection.java src\main\java\utils\SessionManager.java src\main\java\ui\LoginForm.java

REM Run
java -cp "lib\mysql-connector-j-8.0.33.jar;src\main\java" ui.LoginForm
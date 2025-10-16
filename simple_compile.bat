@echo off
echo ===================================
echo Hotel Management System - Simple Build
echo ===================================

cd /d "d:\Sumit\College\SEM 3\JAVA\Project\HotelForge"

REM Create bin directory
if not exist bin mkdir bin

REM Clean previous build
if exist bin\* del /q bin\*

echo.
echo Compiling Java files...

REM Compile step by step to avoid command line length issues
echo Compiling database classes...
javac -cp "lib\mysql-connector-j-8.0.33.jar;src" -d bin src\com\hotel\database\*.java

echo Compiling model classes...
javac -cp "lib\mysql-connector-j-8.0.33.jar;src;bin" -d bin src\com\hotel\models\*.java

echo Compiling service classes...
javac -cp "lib\mysql-connector-j-8.0.33.jar;src;bin" -d bin src\com\hotel\services\*.java

echo Compiling exception classes...
javac -cp "lib\mysql-connector-j-8.0.33.jar;src;bin" -d bin src\com\hotel\exceptions\*.java

echo Compiling manager classes...
javac -cp "lib\mysql-connector-j-8.0.33.jar;src;bin" -d bin src\com\hotel\managers\*.java

echo Compiling UI classes...
javac -cp "lib\mysql-connector-j-8.0.33.jar;src;bin" -d bin src\com\hotel\ui\*.java

echo Compiling main classes...
javac -cp "lib\mysql-connector-j-8.0.33.jar;src;bin" -d bin src\com\hotel\main\*.java

echo Compiling room model classes...
javac -cp "lib\mysql-connector-j-8.0.33.jar;src;bin" -d bin src\main\model\*.java

echo Compiling DAO classes...
javac -cp "lib\mysql-connector-j-8.0.33.jar;src;bin" -d bin src\main\dao\*.java

echo Compiling room UI classes...
javac -cp "lib\mysql-connector-j-8.0.33.jar;src;bin" -d bin src\main\ui\*.java

echo Compiling main entry point...
javac -cp "lib\mysql-connector-j-8.0.33.jar;src;bin" -d bin src\main\*.java

echo.
echo Compilation completed!
echo.
echo Starting application...
java -cp "lib\mysql-connector-j-8.0.33.jar;bin" com.hotel.main.HotelManagementApplication

pause
